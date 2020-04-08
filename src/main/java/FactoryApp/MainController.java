package FactoryApp;
import Core.Order;
import Core.OrderQueue;
import Core.bike.*;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
	public void initialize()
	{
		Frame frame = new Frame(50, 28, FrameType.TypeA);
		Frame frame2 = new Frame(46, 28, FrameType.TypeC);
		Brand brand = new Brand("Gazelle");
		Bike bike = new Bike(frame, BikeType.Man, brand, SaddleType.Man);
		Bike bike2 = new Bike(frame2, BikeType.Woman, brand, SaddleType.Woman);

		Order order = new Order();
		order.addBike(bike);
		order.addBike(bike2);
		Order order2 = new Order();
		order2.addBike(bike);


		//OrderQueue orderQueue = new OrderQueue();
		//orderQueue.addOrder(order);

		orderObservable.add(order);
		orderObservable.add(order2);

		orderList.setItems(orderObservable);
		orderList.setCellFactory(x -> createAmountCell());

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
				orderObservable.remove(selectedOrder);
				counter = 0;
				lblCurrent.setText("1");
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

				setText("Bike count: " + order.getBikes().size());
			}
		};
	}
}
