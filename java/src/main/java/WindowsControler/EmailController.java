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
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import kram.storage.DaoFactory;
import kram.storage.Mail;
import kram.storage.user.User;
import kram.storage.user.UserDao;

public class EmailController {

    @FXML
    private Rectangle infoLabel;

    @FXML
    private Label titleLabel;
    
    @FXML
    private Label codeLabel;
	
    @FXML
    private Button check;

    @FXML
    private Button resend;

    @FXML
    private TextField codeField;

    @FXML
    private Button back;

    @FXML
    private Label errorField;

    @FXML
    private Label emailField;

    
    UserDao userDao = DaoFactory.INSTATNCE.getUserDao();
    
    Long lastTime = -10000L;
    
    Stage stage;
    User user;
    
    List<String> codes = new ArrayList<String>();
    
    public EmailController(Stage stage, User user, String code) {
		this.user = user;
		this.stage = stage;
		codes.add(code);
	}
    
	public Stage getStage() {
		return stage;
	}
	
	private StringProperty userCode = new SimpleStringProperty(); 
    
    @FXML
    void initialize() {
    	emailField.setText(user.getEmail());
    	codeField.textProperty().bindBidirectional(userCode);
    	
    	check.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println(codeField.getText());
				boolean codeNotFind = true;
				for (String code : codes) {
					if(code.equals(codeField.getText())) {
						codeNotFind = false;
						System.out.println("Funguje");
					}
				}
				if(codeNotFind) {
					errorField.setTextFill(Color.RED);
					errorField.setText("Incorrect registration code");
				} else {
					errorField.setTextFill(Color.GREEN);
					errorField.setText("Correct code");
			    	try {
			    		System.out.println(user);
			    		user = userDao.saveUser(user);
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
					SignUpPageController controller = new SignUpPageController(getStage(), user);
		    		FXMLLoader fxmlLoader2 = new FXMLLoader(SignUpPageController.class.getResource("SignUpUserPage.fxml"));
		    		fxmlLoader2.setController(controller);
		    		Parent rootPane = fxmlLoader2.load();
		    		Scene scene = new Scene(rootPane);
		    		getStage().setTitle("KRAM");
		    		getStage().setScene(scene);
				} catch (IOException e) {
					System.out.println("chybicka");
				}
			}
		});
    	
    	resend.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
		    		codes.add(Mail.send(user.getEmail()));
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
			}
		});
    	
//    	try {
//    		userDao.saveUser(user);
//	    	if (user.isTeacher()) {
//	    		UserTeacherPageControler controller = new UserTeacherPageControler(getStage(), user);
//	    		FXMLLoader fxmlLoader2 = new FXMLLoader(UserTeacherPageControler.class.getResource("UserTeacherPage.fxml"));
//	    		fxmlLoader2.setController(controller);
//	    		Parent rootPane = fxmlLoader2.load();
//	    		Scene scene = new Scene(rootPane);
//	    		getStage().setTitle("Welcome " + user.getSurname());
//	    		getStage().setScene(scene);
//	    	} else {
//	    		System.out.println("Som tu");
//	    		UserPageControler controller = new UserPageControler(getStage(), user);
//	    		FXMLLoader fxmlLoader2 = new FXMLLoader(UserPageControler.class.getResource("UserPage.fxml"));
//	    		fxmlLoader2.setController(controller);
//	    		Parent rootPane = fxmlLoader2.load();
//	    		Scene scene = new Scene(rootPane);
//	    		getStage().setTitle("Welcome " + user.getSurname());
//	    		getStage().setScene(scene);
//	    	}
//    	} catch(IOException e) {
//    		e.printStackTrace();
//    	}
    	
    }
    
}
