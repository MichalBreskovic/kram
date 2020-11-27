package kram;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class App extends Application {
	
	public void start(Stage stage) throws Exception {
		Button button = new Button("stlaè ma!");
		AnchorPane rootPane = new AnchorPane();
		rootPane.getChildren().add(button);
		rootPane.setPrefSize(400, 300);
		Scene scene = new Scene(rootPane);
		stage.setTitle("Hello World");
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
