package kram;

import WindowsControler.WelcomePageControler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;



public class App extends Application {
	
	public void start(Stage stage) throws Exception {
		WelcomePageControler controller = new WelcomePageControler();
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("WelcomePageCurrent.fxml"));
		fxmlLoader.setController(controller);
		Parent rootPane = fxmlLoader.load();
		Scene scene = new Scene(rootPane);
		stage.setTitle("Hello World");
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
