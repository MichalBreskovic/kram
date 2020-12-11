package WindowsControler;

import kram.appka.App;
import kram.storage.*;
import kram.storage.subject.MysqlSubjectDao;
import kram.storage.subject.Subject;
import kram.storage.subject.SubjectDao;
import kram.storage.subject.SubjectDaoFactory;
import kram.storage.user.User;
import kram.storage.user.UserDao;
import kram.storage.user.UserDaoFactory;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class UserPageControler {
	private SubjectDao subjectDao = SubjectDaoFactory.INSTATNCE.getSubjectDao();
	private Stage stage;
	private User user;
	@FXML
	private Label username;

	@FXML
	private ImageView settings;

	@FXML
	private Button newtest;

    @FXML
    private ListView<Subject> listview;


	// private UserDao userDao = UserDaoFactory.INSTATNCE.getUserDao();

	public UserPageControler(Stage stage, User user) {
		this.stage = stage;
		this.user = user;
	}


	@FXML
	void initialize() {
		username.setText(user.getName() + " "+ user.getSurname());
		newtest.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				listview.setItems(FXCollections.observableArrayList(subjectDao.getAll()));
			}
		});
			
	}
}
