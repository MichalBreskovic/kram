package WindowsControler.teacherPages;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import kram.storage.DaoFactory;
import kram.storage.course.Course;
import kram.storage.course.CourseDao;
import kram.storage.user.User;
public class AddCourseController {

	private CourseDao courseDao = DaoFactory.INSTATNCE.getCourseDao();


	private Stage stage;
	private Stage stage2;
	private User user;
	  @FXML
	    private Label username1;

	    @FXML
	    private TextField title;

	    @FXML
	    private Button addtopic;




	public AddCourseController(Stage stage, Stage stage2, User user) {
		this.stage = stage;
		this.stage2 = stage2;
		this.user = user;
	}
	

	// private ObjectProperty<Subject> selectedSubject = new
	// SimpleObjectProperty<Subject>();
	// private ObjectProperty<Zameranie> selectedTopic = new
	// SimpleObjectProperty<Zameranie>();
	
	@FXML
	void initialize() {
		addtopic.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				stage2.close();
				if (title.getText()!=null || !title.getText().trim().isEmpty()) {
					Course course = new Course(user.getIdUser(), title.getText().toString());
					courseDao.saveCourse(course);
				}

				
				stage.show();
				
			}
		});
		stage2.setOnCloseRequest(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent event) {
				stage.show();
				
			}
		});
	}

}
