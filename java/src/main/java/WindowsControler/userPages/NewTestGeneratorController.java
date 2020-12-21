package WindowsControler.userPages;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import WindowsControler.WelcomePageControler;
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
import kram.storage.question.Question;
import kram.storage.question.QuestionDao;
import kram.storage.subject.Subject;
import kram.storage.subject.SubjectDao;
import kram.storage.test.KramTest;
import kram.storage.user.User;
import kram.storage.zameranie.Zameranie;
import kram.storage.zameranie.ZameranieDao;

public class NewTestGeneratorController {
	private SubjectDao subjectDao = DaoFactory.INSTATNCE.getSubjectDao();
	private ZameranieDao zameranieDao = DaoFactory.INSTATNCE.getZameranieDao();
	private QuestionDao questionDao = DaoFactory.INSTATNCE.getQuestionDao();
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
//    @FXML
//    private Button refreshSubjectSelector;
    @FXML
    private TextField topicSelect;
//    @FXML
//    private Button refreshTopicSelector;
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
	private ObjectProperty<String> selectedString = new SimpleObjectProperty<String>();
	private ObjectProperty<String> selectedString2 = new SimpleObjectProperty<String>();
	
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
				selectedTopic.setValue(null);
//				System.out.println(newValue);
				topicview.getItems().clear();
				if (selectedSubject.getValue()!=null) {
					topicview.setItems(FXCollections.observableArrayList(zameranieDao.getAllBySubjectId(selectedSubject.getValue().getIdSubject())));
				}
				
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
				//selectedSubject.setValue(null);
				selectedTopic.setValue(newValue);
//				System.out.println(newValue);
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
		subjectSelect.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				selectedString.setValue(newValue);
				subjectview.setItems(FXCollections.observableArrayList(subjectDao.getBySubstring(selectedString.getValue())));
				selectedTopic.setValue(null);
				
				
			}
		});
		
		topicSelect.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (selectedSubject.getValue()!=null) {
					selectedString2.setValue(newValue);
					topicview.setItems(FXCollections.observableArrayList(zameranieDao.getBySubstringSubjectId(selectedString2.getValue(), selectedSubject.getValue().getIdSubject())));
					selectedTopic.setValue(null);
				}else {
					selectedString2.setValue(newValue);
					topicview.setItems(FXCollections.observableArrayList(zameranieDao.getBySubstring(selectedString2.getValue())));
					selectedTopic.setValue(null);
									
				}
			
			}
		});
		/*refreshSubjectSelector.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				String subjectString=subjectSelect.getText();
				selectedSubject.setValue(null);
				selectedTopic.setValue(null);
				subjectview.getItems().clear();
				subjectview.setItems(FXCollections.observableArrayList(subjectDao.getBySubstring(subjectString)));
				
			}
			
		});
		refreshTopicSelector.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				String topicString=topicSelect.getText();
				//selectedSubject.setValue(null);
				selectedTopic.setValue(null);
				topicview.getItems().clear();
				if (selectedSubject.getValue()!=null) {
					topicview.setItems(FXCollections.observableArrayList(zameranieDao.getBySubstringSubjectId(topicString, selectedSubject.getValue().getIdSubject())));
				}else {
					topicview.setItems(FXCollections.observableArrayList(zameranieDao.getBySubstring(topicString)));
				}
				
				
			}
			
		});*/
		start.setOnAction(new EventHandler<ActionEvent>() {
			boolean done = true;
			@Override
			public void handle(ActionEvent event) {
				errorfield.setText("");
				String numString=numberOfQuestions.getText();
				if (selectedTopic.getValue() == null ||  numString==null || numString.trim().isEmpty()) {
					//System.out.println(selectedSubject);
					errorfield.setTextFill(Color.RED);
					errorfield.setText("You need to choose item for starting test");
					done = false;
				}else {
					done=true;
				}
					
				
				if (done) {
					try {
						Integer.parseInt(numString);
						errorfield.setTextFill(Color.DARKGREEN);
						errorfield.setText("STARTING");
						List<Question> otazky = questionDao.generateTestQuestions(Integer.parseInt(numString), selectedTopic.getValue().getIdZameranie());
						if (otazky.size()==0) {
							errorfield.setTextFill(Color.RED);
							errorfield.setText("There are no questions in this topic");
						}else {
							try {
								TestController controller = new TestController(stage, user, otazky, selectedTopic.getValue(), false);
								FXMLLoader fxmlLoader2 = new FXMLLoader(UserPageControler.class.getResource("TestPage.fxml"));
								fxmlLoader2.setController(controller);
								Parent rootPane = fxmlLoader2.load();
								Scene scene = new Scene(rootPane);
								stage.setTitle("TEST");
								stage.setScene(scene);
							} catch (Exception e) {
								e.printStackTrace();
								System.out.println("fail again");
							}
						}
						
						
						
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
					FXMLLoader fxmlLoader2 = new FXMLLoader(UserPageControler.class.getResource("UserPage.fxml"));
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
