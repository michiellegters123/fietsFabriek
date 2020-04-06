package CustomerApp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class CustomerApp extends Application
{

	public static void main(String[] args)
	{
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws IOException
	{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/CustomerGui.fxml"));
		Parent root = loader.load();

		Scene scene = new Scene(root);

		primaryStage.setScene(scene);
		primaryStage.setTitle("Bestel een fiets");
		primaryStage.sizeToScene();
		primaryStage.show();
	}
}
