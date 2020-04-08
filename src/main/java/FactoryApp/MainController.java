package FactoryApp;
import Core.Order;
import Core.OrderQueue;
import Core.bike.*;
import com.mysql.cj.x.protobuf.MysqlxCrud;
import com.sun.prism.MediaFrame;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

public class MainController
{
	@FXML
	private ListView<Order> orderList;
	private ObservableList<Order> orderObservable = FXCollections.observableArrayList();
	@FXML
	private ListView<Order> finished;
	private ObservableList<Order> finishedObservable = FXCollections.observableArrayList();


	@FXML
	private Button btnTake;
	@FXML
	private Button btnNext;
	@FXML
	private Button btnFinish;
	@FXML
	private Label frameMaat;
	@FXML
	private Label frameId;
	@FXML
	private Label lblCurrent;
	@FXML
	private Label lblTotaal;
	@FXML
	private Label bikeType;
	@FXML
	private Label brand;
	@FXML
	private Label saddleType;



	private int counter, totalBikes;
	private Order selectedOrder;
	private Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/fietsfabriek", "root", "");
	Frame frame;
	BikeType eBikeType;
	SaddleType eSaddleType;
	Order getOrder = new Order();
	int brandId;

	public MainController() throws SQLException
	{
	}

	public void initialize() throws SQLException
	{
		Statement stmt = conn.createStatement();
		ResultSet rsOrder = stmt.executeQuery("select * from orders");

		ArrayList<Integer> orderids = new ArrayList<>();
		ArrayList<Order> allOrders = new ArrayList<>();

		while(rsOrder.next())
		{
			if(rsOrder.getDate("date_completed") == null)
			{
				int orderId = rsOrder.getInt("order_id");
				getOrder.setId(orderId);
				orderids.add(orderId);
			}
		}

		int length = orderids.size();
		for (int i = 0; i < length; i++)
		{
			ResultSet rsBike = stmt.executeQuery("select * from bikes WHERE order_id = '" + orderids.get(i) + "'");
			while (rsBike.next())
			{
				brandId = rsBike.getInt("brand_id");
				System.out.println(brandId);
				String frameType = rsBike.getString("frame_type");
				FrameType EframeType;
				switch (frameType)
				{
					case "TypeA":
						EframeType = FrameType.TypeA;
						break;
					case "TypeB":
						EframeType = FrameType.TypeB;
						break;
					case "TypeC":
						EframeType = FrameType.TypeC;
						break;
					default:
						EframeType = FrameType.TypeA;
				}
				frame = new Frame(rsBike.getInt("frame_size"), rsBike.getInt("wheel_size"), EframeType);
				String biketype = rsBike.getString("bike_type");

				switch (biketype)
				{
					case "Man":
						eBikeType = BikeType.Man;
						break;
					case "Woman":
						eBikeType = BikeType.Woman;
						break;
					default:
						eBikeType = BikeType.Man;
						break;
				}

				String saddleType = rsBike.getString("bike_type");
				switch (saddleType)
				{
					case "Man":
						eSaddleType = SaddleType.Man;
						break;
					case "Woman":
						eSaddleType = SaddleType.Woman;
						break;
					default:
						eSaddleType = SaddleType.Man;
						break;
				}

			}

			System.out.println(brandId);
			ResultSet rsBrand = stmt.executeQuery("select * from brands WHERE brand_id ='"+ brandId +"'");
				while (rsBrand.next())
				{
					Brand brand = new Brand(rsBrand.getString("name"));
					Bike bike = new Bike(frame, eBikeType, brand, eSaddleType);
					getOrder.addBike(bike);
					orderObservable.add(getOrder);
					orderList.setItems(orderObservable);
					orderList.setCellFactory(x -> createAmountCell());
					getOrder.setId(orderids.get(i)-1);
					getOrder = new Order();
				}
		}

		//orderObservable.add(getOrder);


		finished.setItems(finishedObservable);
		finished.setCellFactory(x -> createAmountCell());
		btnFinish.setDisable(true);

		btnTake.setOnAction(event ->
		{
			getBike(counter);
			btnTake.setDisable(true);
			if(selectedOrder.getBikes().size() == 1)
			{
				btnNext.setDisable(true);
			}
			else
			{
				btnNext.setDisable(false);
			}
		});

		btnNext.setOnAction(event -> {
			if(counter < totalBikes)
			{
				counter++;
				lblCurrent.setText(Integer.toString(counter+1));
				getBike(counter);
			}
			else
			{
				System.out.println("Send to finished");
			}
		});
		btnFinish.setOnAction(event ->
		{
				finishedObservable.add(selectedOrder);
				btnFinish.setDisable(true);
				btnTake.setDisable(false);
				counter = 0;
				lblCurrent.setText("1");
			try
			{
				java.sql.Date date = new java.sql.Date(new java.util.Date().getTime());
				System.out.println(selectedOrder.getId());
				stmt.executeUpdate("UPDATE orders SET date_completed = '"+ date+"' WHERE order_id = '"+selectedOrder.getId()+"'");
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
			orderObservable.remove(selectedOrder);

		});

	}

	private void getBike(int index)
	{
		selectedOrder = orderList.getSelectionModel().getSelectedItem();
		totalBikes = selectedOrder.getBikes().size();

		lblTotaal.setText(Integer.toString(totalBikes));

		if(counter == totalBikes-1)
		{
			btnNext.setDisable(true);
			btnFinish.setDisable(false);
		}

		int size = selectedOrder.getBikes().get(index).getFrame().getSize();
		frameMaat.setText(Integer.toString(size));
		frameId.setText(selectedOrder.getBikes().get(counter).getFrame().getFrameType().toString());
		bikeType.setText(selectedOrder.getBikes().get(counter).getBikeType().toString());
		brand.setText(selectedOrder.getBikes().get(counter).getBrand().getName());
		saddleType.setText(selectedOrder.getBikes().get(counter).getSaddleType().toString());
	}

	private ListCell<Order> createAmountCell()
	{
		return new ListCell<Order>()
		{
			@Override
			protected void updateItem(Order order, boolean empty)
			{
				super.updateItem(order, empty);
				if (empty || order == null)
				{
					setText(null);
					return;
				}

				setText("Order id: " + order.getId());
			}
		};
	}
}
