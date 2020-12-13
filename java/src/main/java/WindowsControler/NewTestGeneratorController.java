package WindowsControler;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import kram.storage.DaoFactory;
import kram.storage.subject.Subject;
import kram.storage.subject.SubjectDao;
import kram.storage.user.User;
import kram.storage.zameranie.Zameranie;
import kram.storage.zameranie.ZameranieDao;

public class NewTestGeneratorController {
	private SubjectDao subjectDao = DaoFactory.INSTATNCE.getSubjectDao();
	private ZameranieDao zameranieDao = DaoFactory.INSTATNCE.getZameranieDao();
	private Stage stage;
	private User user;
    @FXML
    private TextField subjectSelect;
    @FXML
    private Label username;
    @FXML
    private Button start;
    @FXML
    private ListView<Subject> subjectview;
    @FXML
    private Label viewtest;
    @FXML
    private ListView<Zameranie> topicview;
    @FXML
    private Label viewtest1;
    @FXML
    private Button refreshSubjectSelector;
    @FXML
    private TextField topicSelect;
    @FXML
    private Button refreshTopicSelector;
	// private UserDao userDao = DaoFactory.INSTATNCE.getUserDao();

	public NewTestGeneratorController(Stage stage, User user) {
		this.stage = stage;
		this.user = user;
	}
	private ObjectProperty<Subject> selectedSubject = new SimpleObjectProperty<Subject>(); 
	private ObjectProperty<Zameranie> selectedTopic = new SimpleObjectProperty<Zameranie>(); 
	
	@FXML
	void initialize() {
		selectedSubject.setValue(null);
		selectedTopic.setValue(null);
		subjectview.setItems(FXCollections.observableArrayList(subjectDao.getAll()));
		//topicview.setItems(FXCollections.observableArrayList(zameranieDao.getAllBySubjectId(1)));
		
		subjectview.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Subject>() {

			@Override
			public void changed(ObservableValue<? extends Subject> observable, Subject oldValue, Subject newValue) {
				selectedSubject.setValue(newValue);
				System.out.println(newValue);
				topicview.getItems().clear();
				topicview.setItems(FXCollections.observableArrayList(zameranieDao.getAllBySubjectId(selectedSubject.getValue().getIdSubject())));
			}
			
		});
		selectedSubject.addListener(new ChangeListener<Subject>() {

			@Override
			public void changed(ObservableValue<? extends Subject> observable, Subject oldValue, Subject newValue) {
				if (newValue==null) {
					subjectview.getSelectionModel().clearSelection();
				}else {
					subjectview.getSelectionModel().select(newValue);
				}
				
			}
		});
		topicview.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Zameranie>() {

			@Override
			public void changed(ObservableValue<? extends Zameranie> observable, Zameranie oldValue, Zameranie newValue) {
				selectedTopic.setValue(newValue);
				System.out.println(newValue);
			}
			
		});
		selectedTopic.addListener(new ChangeListener<Zameranie>() {

			@Override
			public void changed(ObservableValue<? extends Zameranie> observable, Zameranie oldValue, Zameranie newValue) {
				if (newValue==null) {
					topicview.getSelectionModel().clearSelection();
				}else {
					topicview.getSelectionModel().select(newValue);
				}
				
			}
		});
		
		refreshSubjectSelector.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				
			}
			
		});
		refreshTopicSelector.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				
			}
			
		});
		start.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (selectedSubject != null && selectedTopic != null) {
					
				}
				
			}
			
		});
	

			
	}
}
