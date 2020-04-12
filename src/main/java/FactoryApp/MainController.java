package FactoryApp;
import Core.Order;
import Core.OrderQueue;
import Core.bike.*;
import com.mysql.cj.protocol.Resultset;
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
import java.util.Calendar;

import static javax.swing.JOptionPane.showMessageDialog;

public class MainController
{
	@FXML
	private ListView<Order> orderList;
	private ObservableList<Order> orderObservable = FXCollections.observableArrayList();
	@FXML
	private ListView<Order> finished;
	private ObservableList<Order> finishedObservable = FXCollections.observableArrayList();

	private ArrayList<Brand> selectedBrands = new ArrayList<>();

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

	private Order selectedOrder = new Order();

	private Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/fietsfabriek", "root", "");
	private ArrayList<Bike> bikes = new ArrayList<>();
	private ArrayList<Integer> orderId = new ArrayList<>();
	private Bike bike;
	private int counter = 0;
	Statement stmt = conn.createStatement();


	public MainController() throws SQLException
	{

	}

	public void initialize() throws SQLException
	{
		orderList.setItems(orderObservable);
		finished.setItems(finishedObservable);
		orderList.setCellFactory(param -> createAmountCell());
		finished.setCellFactory(param -> createAmountCell());
		loadOrders();
		btnFinish.setDisable(true);
		btnNext.setDisable(true);
		btnTake.setOnAction(event ->
		{
			counter = 1;
			lblCurrent.setText(Integer.toString(counter));
			selectedOrder = orderList.getSelectionModel().getSelectedItem();

			int bikeCount = selectedOrder.getBikes().size();
			lblTotaal.setText(Integer.toString(bikeCount));

			try
			{
				//Integer.parseInt(lblCurrent.getText())
				fillLabels(0);
				if(bikeCount != 1)
				{
					btnTake.setDisable(true);
					btnNext.setDisable(false);
				}
				else
				{
					btnTake.setDisable(true);
					btnFinish.setDisable(false);
				}

			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		});

		btnNext.setOnAction(event ->
		{
			try
			{
				fillLabels(Integer.parseInt(lblCurrent.getText()));
				int counter = Integer.parseInt(lblCurrent.getText());
				counter++;
				lblCurrent.setText(Integer.toString(counter));
				if(counter == selectedOrder.getBikes().size())
				{
					btnNext.setDisable(true);
					btnFinish.setDisable(false);
				}
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		});

		btnFinish.setOnAction(event ->
		{
			int orderid = selectedOrder.getId();

			try
			{
				Calendar cal = Calendar.getInstance();
				Date date = new Date(cal.getTime().getTime());
				stmt.executeUpdate("Update orders SET date_completed='"+ date +"' WHERE order_id = '"+ orderid+"'");

				finishedObservable.add(selectedOrder);
				orderObservable.remove(selectedOrder);

				showMessageDialog(null, "De fiets is afgerond!");
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}

		});

	}

	public void fillLabels(int count) throws SQLException
	{
		frameMaat.setText( Integer.toString(selectedOrder.getBikes().get(count).getFrame().getSize()));
		frameId.setText(selectedOrder.getBikes().get(count).getFrame().getFrameType().toString());
		bikeType.setText(selectedOrder.getBikes().get(count).getBikeType().toString());

		ResultSet brandResult = stmt.executeQuery("select * from brands");
		while(brandResult.next())
		{
			if (selectedOrder.getBikes().get(count).getBrandId() == brandResult.getInt("brand_id"))
			{
				brand.setText(brandResult.getString("name"));
			}
		}
		saddleType.setText(selectedOrder.getBikes().get(count).getSaddleType().toString());
		}

	public void loadOrders() throws SQLException
	{
		Order order = new Order();

		Statement stmt = conn.createStatement();
		ResultSet orderResult = stmt.executeQuery("select * from orders");
		while (orderResult.next())
		{
			order.setId(orderResult.getInt("order_id"));
			orderId.add(orderResult.getInt("order_id"));
			if(orderResult.getDate("date_completed") == null)
			{
				orderObservable.add(order);
			}
			else
			{
				finishedObservable.add(order);
			}
			order = new Order();
		}

		ResultSet bikeResult = stmt.executeQuery("select * from bikes");
		while(bikeResult.next())
		{
			FrameType frameType;
			BikeType bikeType;
			SaddleType saddleType;

			switch (bikeResult.getString("frame_type"))
			{
				case "Type-A":
					frameType = FrameType.TypeA;
					break;
				case "Type-B":
					frameType = FrameType.TypeB;
					break;
				default:
					frameType = FrameType.TypeC;
					break;
			}

			Frame frame = new Frame(bikeResult.getInt("frame_size"), bikeResult.getInt("wheel_size"), frameType);

			switch (bikeResult.getString("saddle_type"))
			{
				case "Man":
					saddleType = SaddleType.Man;
					break;
				default:
					saddleType = SaddleType.Woman;
					break;
			}

			switch (bikeResult.getString("bike_type"))
			{
				case "Man":
					bikeType = BikeType.Man;
					break;
				default:
					bikeType = BikeType.Woman;
					break;

			}
			bike = new Bike(frame, bikeType, saddleType);
			bike.addBrandId(bikeResult.getInt("brand_id"));
			bike.setId(bikeResult.getInt("order_id"));
			bikes.add(bike);
			for (int i = 0; i <orderObservable.size(); i++)
			{
				if(bike.getId() == orderObservable.get(i).getId())
				{
					orderObservable.get(i).addBike(bike);
				}
			}
		}
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
				int id = order.getId();
				setText("Order id: " + id);
			}
		};
	}
}
