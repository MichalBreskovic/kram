package WindowsControler;

import kram.appka.App;
import kram.storage.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class UserPageControler {

	private Stage stage;
	private User user;
	@FXML
	private Label username;

	@FXML
	private ImageView settings;

	@FXML
	private Button newtest;

	

	// private UserDao userDao = UserDaoFactory.INSTATNCE.getUserDao();

	public UserPageControler(Stage stage, User user) {
		this.stage = stage;
		this.user = user;
	}



	@FXML
	void initialize() {
		username.setText(user.getName() + " "+ user.getSurname());

	}
}
