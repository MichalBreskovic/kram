package kram.appka;

import WindowsControler.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;



public class App extends Application {
	
	public void start(Stage stage) throws Exception {
		
		FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("WelcomePageCurrent.fxml"));
		WelcomePageControler controller = new WelcomePageControler(stage);
		fxmlLoader.setController(controller);
		Parent rootPane = fxmlLoader.load();
		Scene scene = new Scene(rootPane);
		stage.setTitle("KRAM");
		stage.setScene(scene);
		stage.show();
		
	}

	public static void main(String[] args) {
		launch(args);
	}
	public static class Run {
		public static void main(String[] args) {
			new Thread() {
				@Override
				public void run() {
					javafx.application.Application.launch(App.class);
				}
			}.start();
		}
	}
}
