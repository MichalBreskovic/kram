package WindowsControler;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import WindowsControler.teacherPages.UserTeacherClassesController;
import WindowsControler.teacherPages.UserTeacherPageControler;
import WindowsControler.teacherPages.UserTeacherQuestionsController;
import WindowsControler.userPages.UserPageClassController;
import WindowsControler.userPages.UserPageControler;
import WindowsControler.userPages.UserPageTestsController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import kram.appka.App;
import kram.storage.DaoFactory;
import kram.storage.subject.SubjectDao;
import kram.storage.user.User;
import kram.storage.user.UserDao;

public class UserPageProfileController {
	private SubjectDao subjectDao = DaoFactory.INSTATNCE.getSubjectDao();
	private Stage stage;
	private User user;

    @FXML
    private Button tests;

    @FXML
    private Label username;

    @FXML
    private Button classes;

    @FXML
    private Button profile;

    @FXML
    private Button CHANGE;

    @FXML
    private TextField heslo;

    @FXML
    private TextField heslo2;

    @FXML
    private Label name;

    @FXML
    private Label errorfield;

    @FXML
    private Button dlt;

    @FXML
    private Button out;
	    private UserDao userDao = DaoFactory.INSTATNCE.getUserDao();

	public UserPageProfileController(Stage stage, User user) {
		this.stage = stage;
		this.user = user;
	}
	boolean checkFormatPassword(String string) {
		for (char s : string.toCharArray()) {
			if (s == ' ' || s == '\n' || s == '\t') {
				return false;
			}
		}
		return true;
	}
	
	boolean checkPasswords(String psswd, String psswd2) {
		if (psswd.length()!=psswd2.length()) {
			return false;
		}
		for (int i = 0; i < psswd.length(); i++) {
			if (psswd.charAt(i)!=psswd2.charAt(i)) {
				return false;
			}
		}
		return true;
	}

	@FXML
	void initialize() {
		name.setText(user.getUsername());
		//heslo.setText(user.getHeslo());
		//heslo2.setText(user.getHeslo());
		heslo.setPromptText("Insert new password");
		heslo2.setPromptText("Insert new password");
		username.setText(user.getName() + " "+ user.getSurname());
		if (user.isTeacher()) {
			tests.setOpacity(0);
		}
        
		tests.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					if (user.isTeacher()) {
						UserTeacherQuestionsController controller = new UserTeacherQuestionsController(stage, user);
						FXMLLoader fxmlLoader2 = new FXMLLoader(UserTeacherPageControler.class.getResource("UserTeacherQuestionsPage.fxml"));
						fxmlLoader2.setController(controller);
						Parent rootPane = fxmlLoader2.load();
						Scene scene = new Scene(rootPane);
						stage.setTitle("Questions");
						stage.setScene(scene);
					}else {
						UserPageTestsController controller = new UserPageTestsController(stage, user);
						FXMLLoader fxmlLoader2 = new FXMLLoader(UserPageControler.class.getResource("UserPageTests.fxml"));
						fxmlLoader2.setController(controller);
						Parent rootPane = fxmlLoader2.load();
						Scene scene = new Scene(rootPane);
						stage.setTitle("Tests");
						stage.setScene(scene);
					}
					
					
				} catch (Exception e) {
					// TODO: handle exception
				}
				
			}
			
		});
		classes.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					if (user.isTeacher()) {
						UserTeacherClassesController controller = new UserTeacherClassesController(stage, user);
						FXMLLoader fxmlLoader2 = new FXMLLoader(UserTeacherPageControler.class.getResource("UserTeacherClassesPage.fxml"));
						fxmlLoader2.setController(controller);
						Parent rootPane = fxmlLoader2.load();
						Scene scene = new Scene(rootPane);
						stage.setTitle("Classes");
						stage.setScene(scene);
					}else {
						UserPageClassController controller = new UserPageClassController(stage, user);
						FXMLLoader fxmlLoader2 = new FXMLLoader(UserPageControler.class.getResource("UserPageClasses.fxml"));
						fxmlLoader2.setController(controller);
						Parent rootPane = fxmlLoader2.load();
						Scene scene = new Scene(rootPane);
						stage.setTitle("Classes");
						stage.setScene(scene);
					}
					
				} catch (Exception e) {
					e.printStackTrace();
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
					stage.setTitle("Classes");
					stage.setScene(scene);
				} catch (Exception e) {
					// TODO: handle exception
				}
				
			}
			
		});
		CHANGE.setOnAction(new EventHandler<ActionEvent>() {
			boolean done=false;
			@Override
			public void handle(ActionEvent event) {
				System.out.println(heslo.getText());
				if (heslo.getText()==null || heslo2.getText()==null || heslo.getText().trim().isEmpty() || heslo2.getText().trim().isEmpty()) {
					errorfield.setTextFill(Color.RED);
					errorfield.setText("Insert values into fields");
					done=false;
				}
				else {
					done = true;
				}
				if ((!done && !checkFormatPassword(heslo.getText())) || !checkPasswords(heslo.getText(), heslo2.getText()) ) {
					errorfield.setTextFill(Color.RED);
					errorfield.setText("Invalid values in password");
				}
				else {
					errorfield.setTextFill(Color.GREEN);
					errorfield.setText("You changed your password");
					user.setHeslo(heslo.getText());
					user=userDao.saveUser(user);
				}
				
			}
		});
		out.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("WelcomePageCurrent.fxml"));
					WelcomePageControler controller = new WelcomePageControler(stage);
					fxmlLoader.setController(controller);
					Parent rootPane = fxmlLoader.load();
					Scene scene = new Scene(rootPane);
					stage.setTitle("KRAM");
					stage.setScene(scene);
					stage.show();
				} catch (Exception e) {
					// TODO: handle exception
				}
				
			}
		});
		dlt.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				
				try {
					Stage stage2 = new Stage();
					FXMLLoader fxmlLoader = new FXMLLoader(DeleteControler.class.getResource("deleteprofile.fxml"));
					DeleteControler controller = new DeleteControler(user, stage2, stage);
					fxmlLoader.setController(controller);
					Parent rootPane = fxmlLoader.load();
					Scene scene = new Scene(rootPane);
					stage2.setTitle("DELETE PROFILE");
					stage2.setScene(scene);
					stage.close();
					stage2.show();
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		});

	

			
	}
}
