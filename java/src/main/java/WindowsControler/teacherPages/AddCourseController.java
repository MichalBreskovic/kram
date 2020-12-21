package WindowsControler.teacherPages;


import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
	private User user;
	
	@FXML
    private Label username1;

    @FXML
    private TextField title;

    @FXML
    private Button addtopic;
	    
    @FXML
    private Button bck;

    @FXML
    private Label errorfield;

	public AddCourseController(Stage stage, User user) {
		this.stage = stage;
		this.user = user;
	}
		
	@FXML
	void initialize() {
		title.setText(null);
		addtopic.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (title.getText()!=null && !title.getText().trim().isEmpty()) {
					errorfield.setText("");
					courseDao.saveCourse(new Course(user.getIdUser(), title.getText().toString()));
					try {
						UserTeacherClassesController controller = new UserTeacherClassesController(stage, user);
						FXMLLoader fxmlLoader2 = new FXMLLoader(
								UserTeacherPageControler.class.getResource("UserTeacherClassesPage.fxml"));
						fxmlLoader2.setController(controller);
						Parent rootPane = fxmlLoader2.load();
						Scene scene = new Scene(rootPane);
						stage.setTitle("Classes");
						stage.setScene(scene);
						stage.show();
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else {
					System.err.println("Course doesn't have a name");
					errorfield.setText("Course doesn't have a name");
				}

				
				
			}
		});
		bck.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					UserTeacherClassesController controller = new UserTeacherClassesController(stage, user);
					FXMLLoader fxmlLoader2 = new FXMLLoader(
							UserTeacherPageControler.class.getResource("UserTeacherClassesPage.fxml"));
					fxmlLoader2.setController(controller);
					Parent rootPane = fxmlLoader2.load();
					Scene scene = new Scene(rootPane);
					stage.setTitle("Classes");
					stage.setScene(scene);
					stage.show();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		});
	}

}
