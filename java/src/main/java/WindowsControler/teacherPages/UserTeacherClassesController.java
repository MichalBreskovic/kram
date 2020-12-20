package WindowsControler.teacherPages;

import java.util.List;

import WindowsControler.UserPageProfileController;
import WindowsControler.WelcomePageControler;
import WindowsControler.userPages.TestController;
import WindowsControler.userPages.UserPageControler;
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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import kram.storage.DaoFactory;
import kram.storage.course.Course;
import kram.storage.course.CourseDao;
import kram.storage.test.KramTest;
import kram.storage.test.TestDao;
import kram.storage.user.User;
import kram.storage.user.UserDao;
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
    private Button dismis1;

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
    @FXML
    private Button viewTests;
    @FXML
    private ChoiceBox<User> courseStudents;
    
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
	private ObjectProperty<User> selectedStudent = new SimpleObjectProperty<User>();
	private ObjectProperty<User> selectedStudentAccepted = new SimpleObjectProperty<User>();
	private ObjectProperty<User> selectedStudentWaiting = new SimpleObjectProperty<User>();
	private ObjectProperty<KramTest> selectedTest = new SimpleObjectProperty<KramTest>();

	public UserTeacherClassesController(Stage stage, User user) {
		this.stage = stage;
		this.user = user;
	}

//	List<User> accepted = userDao.getAllAcceptedInCourse(selectedCourse.getValue().getIdCourse());
	
	@FXML
	void initialize() {

		courseStudents.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<User>() {
			@Override
			public void changed(ObservableValue<? extends User> observable, User oldValue, User newValue) {
				selectedStudent.setValue(newValue);
				testView.getItems().clear();
				selectedTest.setValue(null);
				//students.getItems().clear();
				//waiting.getItems().clear();
				//if(selectedCourse.getValue() != null) students.setItems(FXCollections.observableArrayList(userDao.getAllAcceptedInCourse(selectedCourse.getValue().getIdCourse())));
				if(selectedCourse.getValue() != null && selectedStudent.getValue() != null) testView.setItems(FXCollections.observableArrayList(testDao.getAllByCourseTeacherUserId(selectedCourse.getValue().getIdCourse(), user.getIdUser(), selectedStudent.getValue().getIdUser())));
				//if(selectedCourse.getValue() != null) waiting.setItems(FXCollections.observableArrayList(userDao.getAllWaitingInCourse(selectedCourse.getValue().getIdCourse())));
			}
		});
		
		courses.setItems(FXCollections.observableArrayList(courseDao.getAllByTeacherId(user.getIdUser())));
		courses.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Course>() {

			@Override
			public void changed(ObservableValue<? extends Course> observable, Course oldValue, Course newValue) {
				selectedCourse.setValue(newValue);
				testView.getItems().clear();
				students.getItems().clear();
				waiting.getItems().clear();
				selectedStudentAccepted.setValue(null);
				selectedStudentWaiting.setValue(null);
				selectedTest.setValue(null);
				selectedStudent.setValue(null);
				List<User> users = userDao.getAllAcceptedInCourse(selectedCourse.getValue().getIdCourse());
				courseStudents.setItems(FXCollections.observableArrayList(users));
				if(selectedCourse.getValue() != null)students.setItems(FXCollections.observableArrayList(users));
				//if(selectedCourse.getValue() != null && selectedStudent.getValue() != null) testView.setItems(FXCollections.observableArrayList(testDao.getAllByCourseTeacherUserId(selectedCourse.getValue().getIdCourse(), user.getIdUser(), selectedStudent.getValue().getIdUser())));
				if(selectedCourse.getValue() != null)waiting.setItems(FXCollections.observableArrayList(userDao.getAllWaitingInCourse(selectedCourse.getValue().getIdCourse())));
				
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
		
		selectedStudent.addListener(new ChangeListener<User>() {
			
			@Override
			public void changed(ObservableValue<? extends User> observable, User oldValue, User newValue) {
				if (newValue == null) {
					courseStudents.getSelectionModel().clearSelection();
				} else {
					courseStudents.getSelectionModel().select(newValue);
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
					students.getItems().add(selectedStudentWaiting.getValue());
					//students.getItems().add(selectedStudentWaiting.getValue());
					
					waiting.getItems().remove(selectedStudentWaiting.getValue());
					selectedStudentWaiting.setValue(null);
					selectedStudentAccepted.setValue(null);
					
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
					students.getItems().remove(selectedStudentAccepted.getValue());
					selectedStudentWaiting.setValue(null);
					selectedStudentAccepted.setValue(null);
					
				}
				
			}
		});
		dismis1.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (selectedStudentWaiting.getValue()==null) {
					errorField.setTextFill(Color.RED);
					errorField.setText("Select waiting student you want to kick from course");
				}
				else {
					errorField.setText("");
					errorField.setText("");
					courseDao.acceptDismissStudent(0, selectedStudentWaiting.getValue().getIdUser(), selectedCourse.getValue().getIdCourse());
					waiting.getItems().remove(selectedStudentWaiting.getValue());
					selectedStudentWaiting.setValue(null);
					selectedStudentAccepted.setValue(null);
					
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
				if (selectedCourse.getValue()==null) {
					errorField.setText("Choose your course");
				}else {
					
				
				try {
					AddTestToCourseController controller = new AddTestToCourseController(stage, user, selectedCourse.getValue());
					FXMLLoader fxmlLoader2 = new FXMLLoader(UserTeacherPageControler.class.getResource("AddTestToCourse.fxml"));
					fxmlLoader2.setController(controller);
					Parent rootPane = fxmlLoader2.load();
					Scene scene = new Scene(rootPane);
					stage.setTitle("VIEWTEST");
					stage.setScene(scene);
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("fail again");
				}
				
			}
				
			}
		});
		viewTests.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (selectedTest==null) {
					errorField.setText("Choose test you want to view");
				}else {
					errorField.setText("");
					try {
						Stage stage2 = new Stage();
						TestController controller = new TestController(stage,stage2, selectedStudent.getValue(),user, selectedTest.getValue(), true);
						FXMLLoader fxmlLoader2 = new FXMLLoader(
								UserTeacherPageControler.class.getResource("UserTeacherQuestionsPage.fxml"));
						fxmlLoader2.setController(controller);
						Parent rootPane = fxmlLoader2.load();
						Scene scene = new Scene(rootPane);
						stage2.setTitle("ViewTest");
						stage2.setScene(scene);
						stage.close();
						stage2.show();
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
				
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

		
	}
}
