package WindowsControler.teacherPages;

import WindowsControler.UserPageProfileController;
import WindowsControler.WelcomePageControler;
import WindowsControler.userPages.UserPageControler;
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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import kram.storage.DaoFactory;
import kram.storage.question.Question;
import kram.storage.question.QuestionDao;
import kram.storage.subject.Subject;
import kram.storage.subject.SubjectDao;
import kram.storage.user.User;
import kram.storage.user.UserDao;
import kram.storage.zameranie.Zameranie;
import kram.storage.zameranie.ZameranieDao;

public class UserTeacherQuestionsController {
	private Stage stage;
	private User user;
	public UserTeacherQuestionsController(Stage stage, User user) {
		this.stage = stage;
		this.user = user;
	}

    @FXML
    private Label username;

    @FXML
    private Button classes;

    @FXML
    private Button profile;

    @FXML
    private ListView<Question> listview;

    @FXML
    private ChoiceBox<Subject> subjectchoice;

    @FXML
    private ChoiceBox<Zameranie> topicchoice;

    @FXML
    private Button editquestion;

    @FXML
    private Button tests;

    @FXML
    private Button addquestion;

    @FXML
    private Label errorfield;

    private QuestionDao questionDao = DaoFactory.INSTATNCE.getQuestionDao();
    private SubjectDao subjectDao = DaoFactory.INSTATNCE.getSubjectDao();
    private ZameranieDao zameranieDao = DaoFactory.INSTATNCE.getZameranieDao();
	@FXML
	void initialize() {
		username.setText(user.getName() + " "+ user.getSurname());
		listview.setItems(FXCollections.observableArrayList(questionDao.getAllByUserId(user.getIdUser())));
		subjectchoice.setItems(FXCollections.observableArrayList(subjectDao.getAllForTeacher(user.getIdUser())));
		topicchoice.setItems(FXCollections.observableArrayList(zameranieDao.getAllForTeacher(user.getIdUser())));
		/*subjectchoice.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Subject>() {

			@Override
			public void changed(ObservableValue<? extends Subject> observable, Subject oldValue, Subject newValue) {
				subjectchoice.setValue(newValue);
				System.out.println(newValue);
				listview.getItems().clear();
				listview.setItems(FXCollections.observableArrayList(questionDao.get(subjectchoice.getValue().getIdSubject())));
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
		});*/
		tests.setOnAction(new EventHandler<ActionEvent>() {
		
			@Override
			public void handle(ActionEvent event) {
				try {
					UserTeacherQuestionsController controller = new UserTeacherQuestionsController(stage, user);
					FXMLLoader fxmlLoader2 = new FXMLLoader(UserTeacherPageControler.class.getResource("UserTeacherQuestionsPage.fxml"));
					fxmlLoader2.setController(controller);
					Parent rootPane = fxmlLoader2.load();
					Scene scene = new Scene(rootPane);
					stage.setTitle("Questions");
					stage.setScene(scene);
				} catch (Exception e) {
					// TODO: handle exception
				}
				
			}
			
		});
		classes.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					UserTeacherClassesController controller = new UserTeacherClassesController(stage, user);
					FXMLLoader fxmlLoader2 = new FXMLLoader(UserTeacherPageControler.class.getResource("UserTeacherClassesPage.fxml"));
					fxmlLoader2.setController(controller);
					Parent rootPane = fxmlLoader2.load();
					Scene scene = new Scene(rootPane);
					stage.setTitle("Classes");
					stage.setScene(scene);
				} catch (Exception e) {
					// TODO: handle exception
				}
				
			}
			
		});
		profile.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					UserPageProfileController controller = new UserPageProfileController(stage, user);
					FXMLLoader fxmlLoader2 = new FXMLLoader(WelcomePageControler.class.getResource("UserPageProfile.fxml"));
					fxmlLoader2.setController(controller);
					Parent rootPane = fxmlLoader2.load();
					Scene scene = new Scene(rootPane);
					stage.setTitle("Profile");
					stage.setScene(scene);
				} catch (Exception e) {
					// TODO: handle exception
				}
				
			}
			
		});
		addquestion.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					CreateQuestionController controller = new CreateQuestionController(stage, user);
					FXMLLoader fxmlLoader2 = new FXMLLoader(UserTeacherQuestionsController.class.getResource("CreateQuestion.fxml"));
					fxmlLoader2.setController(controller);
					Parent rootPane = fxmlLoader2.load();
					Scene scene = new Scene(rootPane);
					stage.setTitle("Add Question");
					stage.setScene(scene);
				} catch (Exception e) {
					// TODO: handle exception
				}
				
			}
		});
		
}
}
