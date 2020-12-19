package WindowsControler.teacherPages;

import WindowsControler.UserPageProfileController;
import WindowsControler.WelcomePageControler;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import kram.storage.DaoFactory;
import kram.storage.course.Course;
import kram.storage.course.CourseDao;
import kram.storage.question.Question;
import kram.storage.question.QuestionDao;
import kram.storage.subject.Subject;
import kram.storage.subject.SubjectDao;
import kram.storage.test.KramTest;
import kram.storage.test.TestDao;
import kram.storage.user.User;
import kram.storage.user.UserDao;
import kram.storage.zameranie.Zameranie;
import kram.storage.zameranie.ZameranieDao;

public class UserTeacherClassesController {

    @FXML
    private Label username;

    @FXML
    private Button classes;

    @FXML
    private Button profile;

    @FXML
    private ListView<KramTest> testView;

    @FXML
    private Button addTest;

    @FXML
    private ListView<User> students;

    @FXML
    private Button dismis;

    @FXML
    private ListView<User> waiting;

    @FXML
    private Button add;

    @FXML
    private ChoiceBox<Course> courses;

    @FXML
    private Button addCourse;

    @FXML
    private Label errorField;

    @FXML
    private Button tests;
	private Stage stage;
	private User user;
	//private QuestionDao questionDao = DaoFactory.INSTATNCE.getQuestionDao();
	//private SubjectDao subjectDao = DaoFactory.INSTATNCE.getSubjectDao();
	//private ZameranieDao zameranieDao = DaoFactory.INSTATNCE.getZameranieDao();
	private TestDao testDao = DaoFactory.INSTATNCE.getTestDao();
	private UserDao userDao = DaoFactory.INSTATNCE.getUserDao();
	private CourseDao courseDao = DaoFactory.INSTATNCE.getCourseDao();


	//private ObjectProperty<Subject> selectedSubject = new SimpleObjectProperty<Subject>();
	//private ObjectProperty<Zameranie> selectedTopic = new SimpleObjectProperty<Zameranie>();
	//private ObjectProperty<Question> selectedQuestion = new SimpleObjectProperty<Question>();
	private ObjectProperty<Course> selectedCourse = new SimpleObjectProperty<Course>();
	private ObjectProperty<User> selectedStudentAccepted = new SimpleObjectProperty<User>();
	private ObjectProperty<User> selectedStudentWaiting = new SimpleObjectProperty<User>();
	private ObjectProperty<KramTest> selectedTest = new SimpleObjectProperty<KramTest>();

	public UserTeacherClassesController(Stage stage, User user) {
		this.stage = stage;
		this.user = user;
	}

	@FXML
	void initialize() {

		courses.setItems(FXCollections.observableArrayList(courseDao.getAllByTeacherId(user.getIdUser())));
		courses.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Course>() {

			@Override
			public void changed(ObservableValue<? extends Course> observable, Course oldValue, Course newValue) {
				selectedCourse.setValue(newValue);
				testView.getItems().clear();
				students.getItems().clear();
				waiting.getItems().clear();
				testView.setItems(FXCollections.observableArrayList(testDao.getByCourseTeacherId(selectedCourse.getValue().getIdCourse(), user.getIdUser())));
				students.setItems(FXCollections.observableArrayList(userDao.getAllAcceptedInCourse(selectedCourse.getValue().getIdCourse())));
				waiting.setItems(FXCollections.observableArrayList(userDao.getAllWaitingInCourse(selectedCourse.getValue().getIdCourse())));
			}

		});
		

		selectedCourse.addListener(new ChangeListener<Course>() {
			
			@Override
			public void changed(ObservableValue<? extends Course> observable, Course oldValue, Course newValue) {
				if (newValue == null) {
					courses.getSelectionModel().clearSelection();
				} else {
					courses.getSelectionModel().select(newValue);
				}
			}
			
		});
		students.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<User>() {

			@Override
			public void changed(ObservableValue<? extends User> observable, User oldValue, User newValue) {
				selectedStudentAccepted.setValue(newValue);
				System.out.println(selectedStudentAccepted);
			}
		});
		selectedStudentAccepted.addListener(new ChangeListener<User>() {

			@Override
			public void changed(ObservableValue<? extends User> observable, User oldValue, User newValue) {
				if (newValue == null) {
					students.getSelectionModel().clearSelection();
				} else {
					students.getSelectionModel().select(newValue);
				}
				
			}
		});
		
		waiting.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<User>() {

			@Override
			public void changed(ObservableValue<? extends User> observable, User oldValue, User newValue) {
				selectedStudentWaiting.setValue(newValue);
				System.out.println(selectedStudentWaiting);
			}
		});
		selectedStudentWaiting.addListener(new ChangeListener<User>() {

			@Override
			public void changed(ObservableValue<? extends User> observable, User oldValue, User newValue) {
				if (newValue == null) {
					waiting.getSelectionModel().clearSelection();
				} else {
					waiting.getSelectionModel().select(newValue);
				}
				
			}
		});
		
		testView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<KramTest>() {

			@Override
			public void changed(ObservableValue<? extends KramTest> observable, KramTest oldValue, KramTest newValue) {
				selectedTest.setValue(newValue);
				System.out.println(selectedTest);
				
			}
		});
		
		selectedTest.addListener(new ChangeListener<KramTest>() {

			@Override
			public void changed(ObservableValue<? extends KramTest> observable, KramTest oldValue, KramTest newValue) {
				if (newValue == null) {
					testView.getSelectionModel().clearSelection();
				} else {
					testView.getSelectionModel().select(newValue);
				}
				
			}
		
		});
		add.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (selectedStudentWaiting.getValue()==null) {
					errorField.setTextFill(Color.RED);
					errorField.setText("Select student you want to accept in course");
				}
				else {
					errorField.setText("");
					courseDao.acceptDismissStudent(1, selectedStudentWaiting.getValue().getIdUser(), selectedCourse.getValue().getIdCourse());
					selectedStudentWaiting.setValue(null);
					selectedStudentAccepted.setValue(null);
					students.getItems().clear();
					students.setItems(FXCollections.observableArrayList(userDao.getAllAcceptedInCourse(selectedCourse.getValue().getIdCourse())));
					waiting.getItems().clear();
					waiting.setItems(FXCollections.observableArrayList(userDao.getAllWaitingInCourse(selectedCourse.getValue().getIdCourse())));
					//userDao.saveUser(selectedStudentWaiting.getValue())
					//accept student
				}
				
			}
		});
		dismis.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (selectedStudentAccepted.getValue()==null) {
					errorField.setTextFill(Color.RED);
					errorField.setText("Select student you want to kick from course");
				}
				else {
					errorField.setText("");
					errorField.setText("");
					courseDao.acceptDismissStudent(0, selectedStudentAccepted.getValue().getIdUser(), selectedCourse.getValue().getIdCourse());
					selectedStudentWaiting.setValue(null);
					selectedStudentAccepted.setValue(null);
					students.getItems().clear();
					students.setItems(FXCollections.observableArrayList(userDao.getAllAcceptedInCourse(selectedCourse.getValue().getIdCourse())));
					waiting.getItems().clear();
					waiting.setItems(FXCollections.observableArrayList(userDao.getAllWaitingInCourse(selectedCourse.getValue().getIdCourse())));
				}
				
			}
		});
		addCourse.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					AddCourseController controller = new AddCourseController(stage, user);
					FXMLLoader fxmlLoader2 = new FXMLLoader(
							UserTeacherPageControler.class.getResource("AddCourse.fxml"));
					fxmlLoader2.setController(controller);
					Parent rootPane = fxmlLoader2.load();
					Scene scene = new Scene(rootPane);
					stage.setTitle("ADD COURSE");
					stage.setScene(scene);
					stage.show();
				} catch (Exception e) {
					// TODO: handle exception
				}
				
			}
		});
		addTest.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// pridaj test do kurzu
				
			}
		});
		
	

		username.setText(user.getName() + " " + user.getSurname());
		tests.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					UserTeacherQuestionsController controller = new UserTeacherQuestionsController(stage, user);
					FXMLLoader fxmlLoader2 = new FXMLLoader(
							UserTeacherPageControler.class.getResource("UserTeacherQuestionsPage.fxml"));
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
					FXMLLoader fxmlLoader2 = new FXMLLoader(
							UserTeacherClassesController.class.getResource("UserTeacherClassesPage.fxml"));
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
					FXMLLoader fxmlLoader2 = new FXMLLoader(
							WelcomePageControler.class.getResource("UserPageProfile.fxml"));
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
		System.out.println(stage.isShowing());
	}
}
