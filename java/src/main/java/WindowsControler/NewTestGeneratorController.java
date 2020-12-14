package WindowsControler;

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
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
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
    @FXML
    private Button back;
    @FXML
    private Label errorfield;
    @FXML
    private TextField numberOfQuestions;
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
		topicview.setItems(FXCollections.observableArrayList(zameranieDao.getAll()));
		
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
				
				String subjectString=subjectSelect.getText();
				//System.out.println(subjectString);
				subjectview.getItems().clear();
				subjectview.setItems(FXCollections.observableArrayList(subjectDao.getBySubstring(subjectString)));
				
			}
			
		});
		refreshTopicSelector.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				String topicString=topicSelect.getText();
				topicview.getItems().clear();
				topicview.setItems(FXCollections.observableArrayList(zameranieDao.getBySubstring(topicString)));
				
			}
			
		});
		start.setOnAction(new EventHandler<ActionEvent>() {
			boolean done = true;
			@Override
			public void handle(ActionEvent event) {
				String numString=numberOfQuestions.getText();
				if (selectedSubject == null || selectedTopic == null || numString==null || numString.trim().isEmpty()) {
					errorfield.setTextFill(Color.RED);
					errorfield.setText("You need to choose subject and item for starting test");
					done = false;
				}else {
					done=true;
				}
					
				
				if (done) {
					try {
						Integer.parseInt(numString);
						errorfield.setTextFill(Color.DARKGREEN);
						errorfield.setText("STARTING");
					} catch (NumberFormatException  e) {
						errorfield.setTextFill(Color.RED);
						errorfield.setText("Invalid value in number of questions");
					}
					
				}
			}
			
		});
		back.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					UserPageControler controller = new UserPageControler(stage, user);
					FXMLLoader fxmlLoader2 = new FXMLLoader(WelcomePageControler.class.getResource("UserPage.fxml"));
					fxmlLoader2.setController(controller);
					Parent rootPane = fxmlLoader2.load();
					Scene scene = new Scene(rootPane);
					stage.setTitle("Welcome "+user.getSurname());
					stage.setScene(scene);
				} catch (Exception e) {
					// TODO: handle exception
				}
				
				
			}
			
		});
	

			
	}
}