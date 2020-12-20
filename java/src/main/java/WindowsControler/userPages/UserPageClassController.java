package WindowsControler.userPages;

import java.util.ArrayList;
import java.util.List;

import WindowsControler.UserPageProfileController;
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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import kram.storage.DaoFactory;
import kram.storage.course.Course;
import kram.storage.course.CourseDao;
import kram.storage.question.Question;
import kram.storage.subject.SubjectDao;
import kram.storage.test.KramTest;
import kram.storage.test.TestDao;
import kram.storage.user.User;
import kram.storage.zameranie.Zameranie;

public class UserPageClassController {

	private Stage stage;
	private User user;
	@FXML
	private Label username;

	@FXML
	private Button classes;

	@FXML
	private Button profile;

	@FXML
	private ChoiceBox<Course> courses;

	@FXML
	private Button newCourse;

	@FXML
	private Label errorField;

	@FXML
	private Button tests;

	@FXML
	private ListView<KramTest> compList;

	@FXML
	private ListView<KramTest> waitList;

	@FXML
	private Button viewTest;
	// private UserDao userDao = DaoFactory.INSTATNCE.getUserDao();

	public UserPageClassController(Stage stage, User user) {
		this.stage = stage;
		this.user = user;
	}

	private TestDao testDao = DaoFactory.INSTATNCE.getTestDao();
	private CourseDao courseDao = DaoFactory.INSTATNCE.getCourseDao();
	private ObjectProperty<KramTest> selectedDoneTest = new SimpleObjectProperty<KramTest>();
	private ObjectProperty<KramTest> selectedWaitingTest = new SimpleObjectProperty<KramTest>();
	private ObjectProperty<Course> selectedCourse = new SimpleObjectProperty<Course>();

	@FXML
	void initialize() {
		username.setText(user.getName() + " " + user.getSurname());
		courses.setItems(FXCollections.observableArrayList(courseDao.getAllByStudentId(user.getIdUser())));
		compList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<KramTest>() {

			@Override
			public void changed(ObservableValue<? extends KramTest> observable, KramTest oldValue, KramTest newValue) {
				selectedDoneTest.setValue(newValue);
				selectedWaitingTest.setValue(null);
				viewTest.setText("VIEW TEST");

			}
		});

		selectedDoneTest.addListener(new ChangeListener<KramTest>() {

			@Override
			public void changed(ObservableValue<? extends KramTest> observable, KramTest oldValue, KramTest newValue) {
				if (newValue == null) {
					compList.getSelectionModel().clearSelection();
				} else {
					compList.getSelectionModel().select(newValue);
				}
			}
		});

		waitList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<KramTest>() {

			@Override
			public void changed(ObservableValue<? extends KramTest> observable, KramTest oldValue, KramTest newValue) {
				selectedWaitingTest.setValue(newValue);
				selectedDoneTest.setValue(null);
				viewTest.setText("DO TEST");

			}
		});
		selectedWaitingTest.addListener(new ChangeListener<KramTest>() {

			@Override
			public void changed(ObservableValue<? extends KramTest> observable, KramTest oldValue, KramTest newValue) {
				if (newValue == null) {
					waitList.getSelectionModel().clearSelection();
				} else {
					waitList.getSelectionModel().select(newValue);
				}
			}
		});

		courses.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Course>() {

			@Override
			public void changed(ObservableValue<? extends Course> observable, Course oldValue, Course newValue) {
				selectedCourse.setValue(newValue);
				waitList.getItems().clear();
				waitList.setItems(FXCollections.observableArrayList());

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

		viewTest.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (selectedWaitingTest != null) {
					/*errorField.setTextFill(Color.DARKGREEN);
					errorField.setText("STARTING");
					//List<Question> otazky = questionDao.generateTestQuestions(Integer.parseInt(numString),
					//		selectedTopic.getValue().getIdZameranie());

					try {
						//TestController controller = new TestController(stage, user, otazky, selectedTopic.getValue(),false);
						FXMLLoader fxmlLoader2 = new FXMLLoader(UserPageControler.class.getResource("TestPage.fxml"));
						fxmlLoader2.setController(controller);
						Parent rootPane = fxmlLoader2.load();
						Scene scene = new Scene(rootPane);
						stage.setTitle("TEST");
						stage.setScene(scene);
					} catch (Exception e) {
						e.printStackTrace();
						System.out.println("fail again");
					}*/

				}if (selectedDoneTest!=null) {
					errorField.setTextFill(Color.DARKGREEN);
					errorField.setText("STARTING");
					//List<Question> otazky =new ArrayList<Question>(selectedDoneTest.getValue().getAnswers().keySet());

					try {
						TestController controller = new TestController(stage, user, selectedDoneTest.getValue(), true);
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

			}
		});

		tests.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					UserPageTestsController controller = new UserPageTestsController(stage, user);
					FXMLLoader fxmlLoader2 = new FXMLLoader(UserPageControler.class.getResource("UserPageTests.fxml"));
					fxmlLoader2.setController(controller);
					Parent rootPane = fxmlLoader2.load();
					Scene scene = new Scene(rootPane);
					stage.setTitle("Tests");
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
					UserPageClassController controller = new UserPageClassController(stage, user);
					FXMLLoader fxmlLoader2 = new FXMLLoader(
							UserPageControler.class.getResource("UserPageClasses.fxml"));
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
					stage.setTitle("Classes");
					stage.setScene(scene);
				} catch (Exception e) {
					// TODO: handle exception
				}

			}

		});

	}
}
