package WindowsControler.userPages;

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
import junit.framework.Test;
import kram.storage.DaoFactory;
import kram.storage.subject.SubjectDao;
import kram.storage.user.User;

public class UserPageTestsController {
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
    private ListView<Test> testview;

    @FXML
    private ChoiceBox<Test> subjectchoice;

    @FXML
    private ChoiceBox<Test> topicchoice;

    @FXML
    private Button newtest;

	public UserPageTestsController(Stage stage, User user) {
		this.stage = stage;
		this.user = user;
	}
	@FXML
	void initialize() {
		username.setText(user.getName() + " "+ user.getSurname());
		tests.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					UserPageTestsController controller = new UserPageTestsController(stage, user);
					FXMLLoader fxmlLoader2 = new FXMLLoader(UserPageControler.class.getResource("UserPageTests.fxml"));
					fxmlLoader2.setController(controller);
					Parent rootPane = fxmlLoader2.load();
					Scene scene = new Scene(rootPane);
					stage.setTitle("Tests");
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
					UserPageClassController controller = new UserPageClassController(stage, user);
					FXMLLoader fxmlLoader2 = new FXMLLoader(UserPageControler.class.getResource("UserPageClasses.fxml"));
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
					stage.setTitle("Classes");
					stage.setScene(scene);
				} catch (Exception e) {
					// TODO: handle exception
				}
				
			}
			
		});
		newtest.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
				NewTestGeneratorController control = new NewTestGeneratorController(stage, user);
				FXMLLoader fxmlLoader = new FXMLLoader(NewTestGeneratorController.class.getResource("NewTestGenerator.fxml"));
				fxmlLoader.setController(control);
				Parent rootPane2;
				rootPane2 = fxmlLoader.load();
				Scene scene = new Scene(rootPane2);
				stage.setScene(scene);
				stage.show();
				} catch (Exception e) {
				}
				//listview.setItems(FXCollections.observableArrayList(subjectDao.getAll()));
				//System.out.println(listview.getItems());
			}
		});
	
	

	

			
	}
}
