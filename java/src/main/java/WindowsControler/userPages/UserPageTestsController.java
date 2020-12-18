package WindowsControler.userPages;

import WindowsControler.UserPageProfileController;
import WindowsControler.WelcomePageControler;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.MultipleSelectionModel;
import javafx.stage.Stage;
import junit.framework.Test;
import kram.storage.DaoFactory;
import kram.storage.subject.Subject;
import kram.storage.subject.SubjectDao;
import kram.storage.test.KramTest;
import kram.storage.test.TestDao;
import kram.storage.user.User;
import kram.storage.zameranie.Zameranie;

public class UserPageTestsController {
	private SubjectDao subjectDao = DaoFactory.INSTATNCE.getSubjectDao();
	private TestDao testDao = DaoFactory.INSTATNCE.getTestDao();
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
    private ListView<KramTest> testview;

    @FXML
    private ChoiceBox<Subject> subjectchoice;

    @FXML
    private ChoiceBox<Zameranie> topicchoice;

    @FXML
    private Button newtest;

    @FXML
    private Button view;

    @FXML
    private Label errorfield;

	public UserPageTestsController(Stage stage, User user) {
		this.stage = stage;
		this.user = user;
	}
	private ObjectProperty<KramTest> selectedTest = new SimpleObjectProperty<KramTest>(); 
	private ObjectProperty<Subject> selectedSubject = new SimpleObjectProperty<Subject>(); 
	@FXML
	void initialize() {
		username.setText(user.getName() + " "+ user.getSurname());
		testview.setItems(FXCollections.observableArrayList(testDao.getByUserId(user.getIdUser())));
		testview.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<KramTest>() {

			@Override
			public void changed(ObservableValue<? extends KramTest> observable, KramTest oldValue, KramTest newValue) {
				selectedTest.setValue(newValue);
				
			}
			
		});
		selectedTest.addListener(new ChangeListener<KramTest>() {

			@Override
			public void changed(ObservableValue<? extends KramTest> observable, KramTest oldValue, KramTest newValue) {
				if (newValue==null) {
					testview.getSelectionModel().clearSelection();
					
				}else {
					testview.getSelectionModel().select(newValue);
				}
				
			}
		});
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
