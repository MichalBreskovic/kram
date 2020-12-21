package WindowsControler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import WindowsControler.teacherPages.UserTeacherPageControler;
import WindowsControler.userPages.UserPageControler;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import kram.appka.App;
import kram.storage.DaoFactory;
import kram.storage.Mail;
import kram.storage.SHA256;
import kram.storage.user.User;
import kram.storage.user.UserDao;

public class ForgottenPassword {

    @FXML
    private Rectangle infoLabel;

    @FXML
    private TextField usernameField;

    @FXML
    private Button resend;

    @FXML
    private Button back;

    @FXML
    private Label titleLabel;

    @FXML
    private Label errorField;
    
    @FXML
    private TextField tempPass;
    
    @FXML
    private Button check;
    
    UserDao userDao = DaoFactory.INSTATNCE.getUserDao();
    
    Long lastTime = -10000L;
    
    Stage stage;
    User user;

    List<String> codes = new ArrayList<String>();

    public ForgottenPassword(Stage stage) {
		this.stage = stage;
	}
    
	public Stage getStage() {
		return stage;
	}
	
	private StringProperty username = new SimpleStringProperty();
    
    @FXML
    void initialize() {
		usernameField.textProperty().bindBidirectional(username);
    	
		check.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				boolean codeNotFind = true;
				for (String code : codes) {
					if(code.equals(tempPass.getText())) {
						codeNotFind = false;
						System.out.println("Funguje");
					}
				}
				if(codeNotFind) {
					errorField.setTextFill(Color.RED);
					errorField.setText("Incorrect temporary password");
				} else {
					errorField.setTextFill(Color.GREEN);
					errorField.setText("Correct password");
			    	try {
			    		if(user != null) {
					    	if (user.isTeacher()) {
					    		UserTeacherPageControler controller = new UserTeacherPageControler(getStage(), user);
					    		FXMLLoader fxmlLoader2 = new FXMLLoader(UserTeacherPageControler.class.getResource("UserTeacherPage.fxml"));
					    		fxmlLoader2.setController(controller);
					    		Parent rootPane = fxmlLoader2.load();
					    		Scene scene = new Scene(rootPane);
					    		getStage().setTitle("Welcome " + user.getSurname());
					    		getStage().setScene(scene);
					    	} else {
					    		System.out.println("Som tu");
					    		UserPageControler controller = new UserPageControler(getStage(), user);
					    		FXMLLoader fxmlLoader2 = new FXMLLoader(UserPageControler.class.getResource("UserPage.fxml"));
					    		fxmlLoader2.setController(controller);
					    		Parent rootPane = fxmlLoader2.load();
					    		Scene scene = new Scene(rootPane);
					    		getStage().setTitle("Welcome " + user.getSurname());
					    		getStage().setScene(scene);
					    	}
			    		} else {
							errorField.setTextFill(Color.RED);
							errorField.setText("Email was not sent yet.");
			    		}
			    	} catch(IOException e) {
			    		e.printStackTrace();
			    	}
				}
			}
		});
		
    	back.setOnAction(new EventHandler<ActionEvent>() {

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
//					SignUpPageController controller = new SignUpPageController(getStage(), user);
//		    		FXMLLoader fxmlLoader2 = new FXMLLoader(SignUpPageController.class.getResource("SignUpUserPage.fxml"));
//		    		fxmlLoader2.setController(controller);
//		    		Parent rootPane = fxmlLoader2.load();
//		    		Scene scene = new Scene(rootPane);
//		    		getStage().setTitle("KRAM");
//		    		getStage().setScene(scene);
				} catch (IOException e) {
					System.out.println("chybicka");
				}
			}
		});
    	
    	resend.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(!userDao.checkUsername(username.getValue())) {
		    		errorField.setTextFill(Color.GREEN);
		    		errorField.setText("Sending email");
					try {
						user = userDao.getByUsername(username.getValue());
			    		codes.add(Mail.sendPassword(user.getEmail()));
			    	} catch(RuntimeException e) {
			    		System.out.println("Incorrect email");
			    		errorField.setTextFill(Color.RED);
			    		errorField.setText("Incorrect email");
			    	}
					int timeToWait = 60;
					Long nextTime = System.currentTimeMillis();
					if(nextTime - lastTime > timeToWait*1000) {
						lastTime = nextTime;
						errorField.setTextFill(Color.GREEN);
						errorField.setText("Email resend");
					} else {
						errorField.setTextFill(Color.RED);
						errorField.setText("You have to wait " + (timeToWait - (int)(nextTime - lastTime)/1000) + " seconds to next resend.");
					}
				} else {
		    		errorField.setTextFill(Color.RED);
		    		errorField.setText("Lol, neexistuješ");
				}
			}
		});
    	
    }
	
}
