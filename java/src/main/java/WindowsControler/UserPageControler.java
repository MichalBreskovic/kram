package WindowsControler;

import kram.appka.App;
import kram.storage.*;
import kram.storage.subject.MysqlSubjectDao;
import kram.storage.subject.Subject;
import kram.storage.subject.SubjectDao;
import kram.storage.user.User;

import java.io.IOException;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class UserPageControler {
	private SubjectDao subjectDao = DaoFactory.INSTATNCE.getSubjectDao();
	private Stage stage;
	private User user;
	@FXML
	private Label username;

	@FXML
	private ImageView settings;

	@FXML
	private Button newtest;

    @FXML
    private Label viewtest;

    @FXML
    private ListView<Subject> listview;

	// private UserDao userDao = DaoFactory.INSTATNCE.getUserDao();

	public UserPageControler(Stage stage, User user) {
		this.stage = stage;
		this.user = user;
	}
	private ObjectProperty<Subject> selectedSubject = new SimpleObjectProperty<Subject>(); 
	
	
	@FXML
	void initialize() {
		selectedSubject.setValue(null);
		username.setText(user.getName() + " "+ user.getSurname());
		newtest.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
				NewTestGeneratorController control = new NewTestGeneratorController(stage, user);
				FXMLLoader fxmlLoader = new FXMLLoader(UserPageControler.class.getResource("NewTestGenerator.fxml"));
				fxmlLoader.setController(control);
				Parent rootPane2;
				rootPane2 = fxmlLoader.load();
				Scene scene = new Scene(rootPane2);
				stage.setScene(scene);
				stage.show();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//listview.setItems(FXCollections.observableArrayList(subjectDao.getAll()));
				//System.out.println(listview.getItems());
			}
		});
		listview.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Subject>() {

			@Override
			public void changed(ObservableValue<? extends Subject> observable, Subject oldValue, Subject newValue) {
				selectedSubject.setValue(newValue);
				System.out.println(newValue);
			}
			
		});
		selectedSubject.addListener(new ChangeListener<Subject>() {

			@Override
			public void changed(ObservableValue<? extends Subject> observable, Subject oldValue, Subject newValue) {
				if (newValue==null) {
					listview.getSelectionModel().clearSelection();
				}else {
					listview.getSelectionModel().select(newValue);
				}
				
			}
		});

			
	}
}
