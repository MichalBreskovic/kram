package WindowsControler.teacherPages;

import WindowsControler.UserPageProfileController;
import WindowsControler.WelcomePageControler;
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
import kram.storage.question.Question;
import kram.storage.subject.Subject;
import kram.storage.user.User;
import kram.storage.zameranie.Zameranie;

public class UserTeacherClassesController {
	private Stage stage;
	private User user;
	public UserTeacherClassesController(Stage stage, User user) {
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
    private ListView<?> listview;

    @FXML
    private Button viewclass;

    @FXML
    private Button tests;

    @FXML
    private Label errorfield;


	@FXML
	void initialize() {
		username.setText(user.getName() + " "+ user.getSurname());
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
					FXMLLoader fxmlLoader2 = new FXMLLoader(UserTeacherClassesController.class.getResource("UserTeacherClassesPage.fxml"));
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
}
}
