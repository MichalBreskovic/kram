package WindowsControler;


import java.io.IOException;

import javax.mail.SendFailedException;
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
import javafx.stage.Stage;
import kram.appka.App;
import kram.storage.*;
import kram.storage.user.User;
import kram.storage.user.UserDao;
import kram.storage.user.UserFxModel;

public class SignUpPageController {
	private Stage stage;
	private boolean ucitel = false;
	private UserDao userDao = DaoFactory.INSTATNCE.getUserDao();

	@FXML
    private TextField email;
	@FXML
	private TextField username;
	@FXML
	private TextField name;
	@FXML
	private TextField surname;
	@FXML
	private Button sign;
	@FXML
	private Button back;
	@FXML
	private Label errorfield;
	@FXML
	private TextField password;
	@FXML
	private TextField password2;
	@FXML
	private Button teacher;
	@FXML
	private Button student;

	private UserFxModel check = new UserFxModel();
	
	public SignUpPageController(Stage stage) {
		this.stage = stage;
	}
	
	User user;
	
	public SignUpPageController(Stage stage, User user) {
		this.stage = stage;
		this.user = user;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public Stage getStage() {
		return stage;
	}


	boolean checkFormatUsername(String string) {
		for (char s : string.toCharArray()) {
			if (!Character.isLetterOrDigit(s)) {
				return false;
			}

		}
		return true;
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
		email.textProperty().bindBidirectional(check.getEmailProperty());
		username.textProperty().bindBidirectional(check.getUsernameProperty());
		name.textProperty().bindBidirectional(check.getNameProperty());
		surname.textProperty().bindBidirectional(check.getSurnameProperty());
		password.textProperty().bindBidirectional(check.getHesloProperty());
		password2.textProperty().bindBidirectional(check.getHeslo2Property());
		
		if(user != null) {
			System.out.println("tututut");
			email.setText(user.getEmail());
			name.setText(user.getName());
			username.setText(user.getUsername());
			surname.setText(user.getSurname());
		}
		
		sign.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					boolean mozeme = true;
					//System.out.println("daco robime");
					errorfield.setTextFill(Color.RED);
					if (check.getEmail() == null || check.getEmail().isBlank()
							|| check.getUsername() == null 
							|| check.getHeslo2() == null || check.getSurname() == null
							|| check.getName() == null || check.getHeslo() == null
							|| check.getUsername().trim().isEmpty() || check.getHeslo().trim().isEmpty()
							|| check.getName().trim().isEmpty() || check.getHeslo2().trim().isEmpty()
							|| check.getUsername().trim().isEmpty() || !ucitel) {
						mozeme = false;
						errorfield.setText("You need to input values");
					} else {
						mozeme = true;
						if (!checkPasswords(check.getHeslo(), check.getHeslo2())) {
							errorfield.setTextFill(Color.RED);
							errorfield.setText("Passwords does not match");
							mozeme = false;
						}
						if (checkPasswords(check.getHeslo(), check.getHeslo2())) {
							if (!checkFormatPassword(check.getHeslo())) {
								errorfield.setTextFill(Color.RED);
								errorfield.setText("Use no blank spaces in password");
								mozeme = false;
							}else {
								errorfield.setTextFill(Color.GREEN);
								errorfield.setText("Passwords ok");
								mozeme = true;
							}
							
						}
						
						if (check.getUsername()!=null && !checkFormatUsername(check.getUsername())) {
							errorfield.setText(errorfield.getText()+"\nFor username name use only alpha or digit characters");
							mozeme = false;
						}
					}
					if (mozeme) {
						try {
//							System.out.println("1 email " + check.getEmail());
							User registrate = new User(check.getName(), check.getUsername(), check.getSurname(), SHA256.getHash(check.getHeslo()), check.isTeacher(), check.getEmail());
//							System.out.println("2 email " + registrate.getEmail());
							String generatedCode = Mail.send(check.getEmail());
							EmailController emailController = new EmailController(getStage(), registrate, generatedCode);
							FXMLLoader fxmlLoader3 = new FXMLLoader(EmailController.class.getResource("EmailVerification.fxml"));
							fxmlLoader3.setController(emailController);
							Parent rootPane = fxmlLoader3.load();
							Scene scene = new Scene(rootPane);
							getStage().setTitle("Email verification");
							getStage().setScene(scene);
							

//							catch (Exception e) {
//							e.printStackTrace();
//							errorfield.setText("Username already taken");
						} catch (RuntimeException e) {
							errorfield.setTextFill(Color.RED);
				    		errorfield.setText("Incorrect email");
						} catch (IOException e) {
							System.out.println("chybiska");
						}
					}
					

				} catch (Exception e) {
					e.printStackTrace();;
					System.out.println("fail");
				}

			}
		});
		teacher.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				teacher.setTextFill(Color.RED);
				teacher.setOpacity(1);
				student.setTextFill(Color.BLACK);
				student.setOpacity(0.5);
				check.setTeacher(true);
				ucitel=true;
			}

		});
		student.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				student.setTextFill(Color.RED);
				student.setOpacity(1);
				teacher.setTextFill(Color.BLACK);
				teacher.setOpacity(0.5);
				ucitel=true;
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
				} catch (Exception e) {
					// TODO: handle exception
				}
				
			}

		});
	}

}
