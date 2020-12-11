package WindowsControler;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import kram.storage.user.User;

public class UserTeacherPageControler {
	private Stage stage;
	private User user;
	public UserTeacherPageControler(Stage stage, User user) {
		this.stage = stage;
		this.user = user;
	}

	@FXML
	private Label username;

	@FXML
	private ImageView settings;

	@FXML
	private Button getsubjects;


	@FXML
	void initialize() {
		
		username.setText(user.getName() + " " + user.getSurname());
		getsubjects.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
			
			}
		});
			

	}
}
