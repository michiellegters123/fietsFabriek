package CustomerApp;

import Core.Order;
import Core.bike.BikeType;
import Core.bike.FrameType;
import Core.bike.SaddleType;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

public class MainController
{
	@FXML
	private ComboBox<BikeType> bikeType;
	@FXML
	private ComboBox<FrameType> frameType;
	@FXML
	private ComboBox<SaddleType> saddleType;
	@FXML
	private Button addOrder;

	private Order currentOrder = new Order();

	public void initialize()
	{
		bikeType.setItems(FXCollections.observableArrayList(BikeType.Man, BikeType.Woman));
		frameType.setItems(FXCollections.observableArrayList(FrameType.TypeA, FrameType.TypeB, FrameType.TypeC));
		saddleType.setItems(FXCollections.observableArrayList(SaddleType.Man, SaddleType.Woman));

		addOrder.setOnAction((actionEvent) ->
		{
			System.out.println(1);
		});
	}
}
