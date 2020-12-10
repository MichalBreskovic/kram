package WindowsControler;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import kram.storage.*;

public class SignUpPageController {
	private Stage stage;
	
	private UserDao userDao = UserDaoFactory.INSTATNCE.getUserDao();
	public SignUpPageController(Stage stage) {
		this.stage = stage;
	}
	
	
	
	public void setStage(Stage stage) {
		this.stage = stage;
	}



	public Stage getStage() {
		return stage;
	}
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
	@FXML
	void initialize() {
		username.textProperty().bindBidirectional(check.getUsernameProperty());
		name.textProperty().bindBidirectional(check.getNameProperty());
		surname.textProperty().bindBidirectional(check.getSurnameProperty());
		password.textProperty().bindBidirectional(check.getHesloProperty());
		password2.textProperty().bindBidirectional(check.getHeslo2Property());
		sign.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event)  {
				try {
					System.out.println("daco robime");
					if (check.getHeslo()!=check.getHeslo2()) {
						errorfield.setText("Passwords does not match");
					}
					if (check.getUsername()==null || check.getHeslo2()==null || check.getSurname()==null ||check.getName()==null || check.getHeslo()==null ||check.getUsername().trim().isEmpty() || check.getHeslo().trim().isEmpty() ) {
						errorfield.setText("You need to input values");
					}
					
				} catch (Exception e) {
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
			}
		
			
		});
		student.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				student.setTextFill(Color.RED);	
				student.setOpacity(1);
				teacher.setTextFill(Color.BLACK);	
				teacher.setOpacity(0.5);
				check.setTeacher(false);
			}
		
			
		});
	}
	
	
	
	
	
	
}
