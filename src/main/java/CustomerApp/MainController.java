package CustomerApp;

import Core.Order;
import Core.bike.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class MainController
{
	@FXML
	private ListView<Bike> orderList;
	@FXML
	private ComboBox<BikeType> bikeType;
	@FXML
	private ComboBox<FrameType> frameType;
	@FXML
	private ComboBox<SaddleType> saddleType;
	@FXML
	private ComboBox<Brand> brands;
	@FXML
	private Button addOrder, orderButton, removeButton, clearButton;
	@FXML
	private TextField frameSize, wheelSize;

	private ObservableList<Brand> brandList = FXCollections.observableArrayList(new Brand("test"), new Brand("test2"));
	private ObservableList<Bike> bikeOrderList = FXCollections.observableArrayList();

	private Order currentOrder = new Order();

	public void initialize()
	{
		bikeType.setItems(FXCollections.observableArrayList(BikeType.Man, BikeType.Woman));
		frameType.setItems(FXCollections.observableArrayList(FrameType.TypeA, FrameType.TypeB, FrameType.TypeC));
		saddleType.setItems(FXCollections.observableArrayList(SaddleType.Man, SaddleType.Woman));
		brands.setItems(brandList);
		brands.setCellFactory(param -> createBrandCell());
		brands.setButtonCell(createBrandCell());
		orderList.setItems(bikeOrderList);
		orderList.setCellFactory(param -> createBikeCell());

		orderList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
		{
			previewBike(newValue);
		});

		orderButton.setOnAction(event ->
		{
			clearAll();
		});

		clearButton.setOnAction(event ->
		{
			clearAll();
		});

		removeButton.setOnAction(event ->
		{
			Bike bike = orderList.getSelectionModel().getSelectedItem();

			if(bike == null)
				return;

			removeBikeFromOrder(bike);
		});

		addOrder.setOnAction((actionEvent) ->
		{
			try
			{
				Brand brand = brands.getValue();
				FrameType frameTypeEnum = frameType.getValue();
				BikeType bikeTypeEnum = bikeType.getValue();
				SaddleType saddleTypeEnum = saddleType.getValue();

				if (frameTypeEnum == null || brand == null || bikeTypeEnum == null || saddleTypeEnum == null)
					throw new NullPointerException();

				int frameSizeInt = Integer.parseInt(frameSize.getText());
				int wheelSizeInt = Integer.parseInt(wheelSize.getText());

				Bike newBike = new Bike(new Frame(frameSizeInt, wheelSizeInt, frameTypeEnum), bikeTypeEnum, brand, saddleTypeEnum);
				addBikeToOrder(newBike);
			}
			catch (NullPointerException ex)
			{
				new Alert(Alert.AlertType.ERROR, "1 of meer invoervelden zijn niet ingevult").showAndWait();
			}
			catch (NumberFormatException ex)
			{
				new Alert(Alert.AlertType.ERROR, "Ongeldig nummer").showAndWait();
			}
			catch (IllegalArgumentException ex)
			{
				new Alert(Alert.AlertType.ERROR, ex.getMessage()).showAndWait();
			}

		});
	}

	private void addBikeToOrder(Bike bike)
	{
		currentOrder.getBikes().add(bike);
		bikeOrderList.add(bike);
	}

	private void removeBikeFromOrder(Bike bike)
	{
		currentOrder.getBikes().remove(bike);
		bikeOrderList.remove(bike);
	}

	private void previewBike(Bike bike)
	{
		if(bike == null)
			return;

		brands.getSelectionModel().select(bike.getBrand());
		frameSize.setText(bike.getFrame().getSize() + "");
		wheelSize.setText(bike.getFrame().getWheelSize() + "");
		saddleType.getSelectionModel().select(bike.getSaddleType());
		bikeType.getSelectionModel().select(bike.getBikeType());
		frameType.getSelectionModel().select(bike.getFrame().getFrameType());
	}

	private void clearAll()
	{
		currentOrder = new Order();
		bikeOrderList.clear();

		frameSize.setText("");
		wheelSize.setText("");
		brands.getSelectionModel().clearSelection();
		saddleType.getSelectionModel().clearSelection();
		bikeType.getSelectionModel().clearSelection();
		frameType.getSelectionModel().clearSelection();
	}

	private ListCell<Brand> createBrandCell()
	{
		return new ListCell<Brand>()
		{
			@Override
			protected void updateItem(Brand item, boolean empty)
			{
				super.updateItem(item, empty);
				if (empty || item == null)
				{
					setText(null);
					return;
				}

				setText(item.getName());
			}
		};
	}

	private ListCell<Bike> createBikeCell()
	{
		return new ListCell<Bike>()
		{
			@Override
			protected void updateItem(Bike item, boolean empty)
			{
				super.updateItem(item, empty);
				if (empty || item == null)
				{
					setText(null);
					return;
				}

				setText(item.getBrand().getName());
			}
		};
	}
}
