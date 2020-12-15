package WindowsControler;
import kram.storage.*;
import kram.storage.user.User;
import kram.storage.user.UserDao;
import kram.storage.user.UserFxModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import WindowsControler.SignUpPageController;
import WindowsControler.teacherPages.UserTeacherPageControler;
import WindowsControler.userPages.UserPageControler;

public class WelcomePageControler {
	private Stage stage;

	public WelcomePageControler(Stage stage) {
		this.stage = stage;
	}
	
	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	private UserDao userDao = DaoFactory.INSTATNCE.getUserDao();

	@FXML
	private Button loginbutton;
	@FXML
	private Button signupbutton;
	@FXML
	private TextField MENO;
	@FXML
	private TextField HESLO;

	private UserFxModel check = new UserFxModel();
	@FXML
    private Label errorfield;
	
	@FXML
	void initialize() {
		MENO.textProperty().bindBidirectional(check.getUsernameProperty());
		HESLO.textProperty().bindBidirectional(check.getHesloProperty());

	    
		
		signupbutton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event)  {
				try {
					//getStage().setTitle("Sign Up");
					SignUpPageController control = new SignUpPageController(getStage());
					FXMLLoader fxmlLoader = new FXMLLoader(WelcomePageControler.class.getResource("SignUpUserPage.fxml"));
					fxmlLoader.setController(control);
					Parent rootPane2 = fxmlLoader.load();
					Scene scene = new Scene(rootPane2);
					getStage().setScene(scene);
					getStage().show();
					
				} catch (Exception e) {
					System.out.println("fail");
				}
				
				

			}
		});

		loginbutton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event)  {
				try {
					if (check.getUsername()==null || check.getHeslo()==null ||check.getUsername().trim().isEmpty() || check.getHeslo().trim().isEmpty() ) {
						errorfield.setText("No values in Username or Password");
					}else {
					errorfield.setText("");
					User logged = userDao.login(check.getUsername(), check.getHeslo());	
					if (logged.isTeacher()) {
						UserTeacherPageControler controller = new UserTeacherPageControler(getStage(), logged);
						FXMLLoader fxmlLoader2 = new FXMLLoader(UserTeacherPageControler.class.getResource("UserTeacherPage.fxml"));
						fxmlLoader2.setController(controller);
						Parent rootPane = fxmlLoader2.load();
						Scene scene = new Scene(rootPane);
						getStage().setTitle("Welcome "+logged.getSurname());
						getStage().setScene(scene);
					}else {
						UserPageControler controller = new UserPageControler(getStage(), logged);
						FXMLLoader fxmlLoader2 = new FXMLLoader(UserPageControler.class.getResource("UserPage.fxml"));
						fxmlLoader2.setController(controller);
						Parent rootPane = fxmlLoader2.load();
						Scene scene = new Scene(rootPane);
						getStage().setTitle("Welcome "+logged.getSurname());
						getStage().setScene(scene);
					}
					
					}
				} catch (Exception e) {
					errorfield.setText("There is no such user");
				
				} 
				
				

			}
		});
		
		
	}
}