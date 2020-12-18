package WindowsControler.teacherPages;

import java.util.ArrayList;
import java.util.List;

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
import javafx.stage.WindowEvent;
import kram.storage.DaoFactory;
import kram.storage.option.Option;
import kram.storage.option.OptionDao;
import kram.storage.question.Question;
import kram.storage.question.QuestionDao;
import kram.storage.subject.Subject;
import kram.storage.subject.SubjectDao;
import kram.storage.user.User;
import kram.storage.zameranie.Zameranie;
import kram.storage.zameranie.ZameranieDao;

public class AddTopicController {
	private SubjectDao subjectDao = DaoFactory.INSTATNCE.getSubjectDao();
	private ZameranieDao zameranieDao = DaoFactory.INSTATNCE.getZameranieDao();
	private QuestionDao questionDao = DaoFactory.INSTATNCE.getQuestionDao();
	 private OptionDao optionDao = DaoFactory.INSTATNCE.getOptionDao();

	private Stage stage;
	private Stage stage2;
	private User user;
	private Subject subject;
	  @FXML
	    private Label username1;

	    @FXML
	    private TextField title;

	    @FXML
	    private Button addtopic;




	public AddTopicController(Stage stage, Stage stage2, User user, Subject subject) {
		this.stage = stage;
		this.stage2 = stage2;
		this.user = user;
		this.subject = subject;
	}
	

	// private ObjectProperty<Subject> selectedSubject = new
	// SimpleObjectProperty<Subject>();
	// private ObjectProperty<Zameranie> selectedTopic = new
	// SimpleObjectProperty<Zameranie>();
	
	@FXML
	void initialize() {
		addtopic.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				stage2.close();
				if (title.getText()!=null || !title.getText().trim().isEmpty()) {
					Zameranie zameranie = new Zameranie(subject.getIdSubject(), title.getText().toLowerCase());
					zameranieDao.saveZameranie(zameranie);
				}

				
				stage.show();
				
			}
		});
		stage2.setOnCloseRequest(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent event) {
				stage.show();
				
			}
		});
	}

}
