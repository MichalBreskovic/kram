package WindowsControler.userPages;


import WindowsControler.teacherPages.UserTeacherClassesController;
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
import kram.storage.course.Course;
import kram.storage.course.CourseDao;

import kram.storage.test.TestDao;
import kram.storage.user.User;


public class ApplyForCourseController {

	private Stage stage;
	private User user;

    @FXML
    private Label username;

    @FXML
    private Button apply;


    @FXML
    private ListView<Course> list;

    @FXML
    private TextField substr;

    @FXML
    private Button refresh;

    @FXML
    private Button bck;

    @FXML
    private Label errorfield;



	public ApplyForCourseController(Stage stage, User user) {
		this.stage = stage;
		this.user = user;
	}

	//private TestDao testDao = DaoFactory.INSTATNCE.getTestDao();
	private CourseDao courseDao = DaoFactory.INSTATNCE.getCourseDao();

	private ObjectProperty<Course> selectedCourse = new SimpleObjectProperty<Course>();

	@FXML
	void initialize() {
		username.setText(user.getName() + " " + user.getSurname());
		list.setItems(FXCollections.observableArrayList(courseDao.getAllRowMapperWithoutUser(user.getIdUser())));
		list.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Course>() {

			@Override
			public void changed(ObservableValue<? extends Course> observable, Course oldValue, Course newValue) {
				selectedCourse.setValue(newValue);
			}
		});
		selectedCourse.addListener(new ChangeListener<Course>() {

			@Override
			public void changed(ObservableValue<? extends Course> observable, Course oldValue, Course newValue) {
				if (newValue == null) {
					list.getSelectionModel().clearSelection();
				} else {
					list.getSelectionModel().select(newValue);
				}

			}
		});

		apply.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (selectedCourse != null) {
					errorfield.setText("");
					courseDao.addToCourse(selectedCourse.getValue().getIdCourse(), user.getIdUser());
					
				} else {
					errorfield.setTextFill(Color.RED);
					errorfield.setText("Choose course");
				}
			}
		});
		refresh.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				list.getItems().clear();
				list.setItems(FXCollections.observableArrayList(courseDao.getBySubstringWithoutU(substr.getText(), user.getIdUser())));
				
			}
		});

		bck.setOnAction(new EventHandler<ActionEvent>() {

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
					e.printStackTrace();
				}

			}

		});
		
	}
}
