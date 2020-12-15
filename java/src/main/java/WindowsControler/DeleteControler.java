package WindowsControler;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import kram.appka.App;
import kram.storage.user.User;

public class DeleteControler {
	private User user;
	private Stage stage2;
	private Stage stage1;

	public DeleteControler(User user, Stage stage2, Stage stage1) {
		this.user = user;
		this.stage2 = stage2;
		this.stage1 = stage1;
	}

	@FXML
	private Label errorfield;

	@FXML
	private Button yes;

	@FXML
	private Button no;

	@FXML
	void initialize() {
		yes.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// deleteuser
				try {
					stage2.close();
					FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("WelcomePageCurrent.fxml"));
					WelcomePageControler controller = new WelcomePageControler(stage1);
					fxmlLoader.setController(controller);
					Parent rootPane = fxmlLoader.load();
					Scene scene = new Scene(rootPane);
					stage1.setTitle("KRAM");
					stage1.setScene(scene);
					stage1.show();
				} catch (Exception e) {
					// TODO: handle exception
				}
			

			}
		});
		no.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
			stage2.close();
				

			}
		});
	}
}
