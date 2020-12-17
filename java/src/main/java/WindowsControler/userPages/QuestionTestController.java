package WindowsControler.userPages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import kram.storage.DaoFactory;
import kram.storage.option.Option;
import kram.storage.option.OptionDao;
import kram.storage.question.Question;
import kram.storage.question.QuestionDao;
import kram.storage.subject.Subject;
import kram.storage.subject.SubjectDao;
import kram.storage.test.KramTest;
import kram.storage.user.User;
import kram.storage.zameranie.Zameranie;
import kram.storage.zameranie.ZameranieDao;

public class QuestionTestController {
	private SubjectDao subjectDao = DaoFactory.INSTATNCE.getSubjectDao();
	private ZameranieDao zameranieDao = DaoFactory.INSTATNCE.getZameranieDao();
	private QuestionDao questionDao = DaoFactory.INSTATNCE.getQuestionDao();
	private OptionDao optionDao = DaoFactory.INSTATNCE.getOptionDao();

	private Stage stage;
	private User user;

	private Zameranie zameranie;
	private Question question;
	private KramTest kramTest;
	private int poradie; 

	public QuestionTestController(Stage stage, User user, KramTest kramtest, int poradie) {
		this.stage = stage;
		this.user = user;
		this.kramTest = kramtest;
		this.poradie=poradie;
	}

    @FXML
    private VBox box;

    @FXML
    private Label option;

    @FXML
    private Label option1;

    @FXML
    private Label tlt;

    @FXML
    private Button addquestion;

    @FXML
    private Button back;

    @FXML
    private Label errorfield2;

    @FXML
    private VBox box1;

    @FXML
    private CheckBox check;

    @FXML
    private CheckBox check1;

    @FXML
    private Label title;

	// private ObjectProperty<Subject> selectedSubject = new
	// SimpleObjectProperty<Subject>();
	// private ObjectProperty<Zameranie> selectedTopic = new
	// SimpleObjectProperty<Zameranie>();
	

	@FXML
	void initialize() {
		
		List<Integer> uzboli = new ArrayList<Integer>();
		// int pocet = 4;
		List<Label> options = new ArrayList<Label>();
		List<CheckBox> correct = new ArrayList<CheckBox>();
		boolean[] iscorrect = new boolean[8];
		options.add(option);
		options.add(option1);
		correct.add(check);
		correct.add(check1);
		check.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (iscorrect[0]) {
					iscorrect[0] = false;
				} else {
					iscorrect[0] = true;
				}

			}
		});

		check1.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (iscorrect[1]) {
					iscorrect[1] = false;
				} else {
					iscorrect[1] = true;
				}

			}
		});
		
		back.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					UserPageControler controller = new UserPageControler(stage, user);
					FXMLLoader fxmlLoader2 = new FXMLLoader(
							UserPageControler.class.getResource("UserPage.fxml"));
					fxmlLoader2.setController(controller);
					Parent rootPane = fxmlLoader2.load();
					Scene scene = new Scene(rootPane);
					stage.setTitle("Welcome");
					stage.setScene(scene);

				} catch (Exception e) {
					// TODO: handle exception
				}

			}
		});

		
		

	}

}
