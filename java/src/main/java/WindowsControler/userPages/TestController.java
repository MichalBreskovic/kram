package WindowsControler.userPages;

import java.sql.PseudoColumnUsage;
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
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
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

public class TestController {
	private SubjectDao subjectDao = DaoFactory.INSTATNCE.getSubjectDao();
	private ZameranieDao zameranieDao = DaoFactory.INSTATNCE.getZameranieDao();
	private QuestionDao questionDao = DaoFactory.INSTATNCE.getQuestionDao();
	private OptionDao optionDao = DaoFactory.INSTATNCE.getOptionDao();

	private Stage stage;
	private User user;

	private Zameranie zameranie;
	private List<Question> questions;
	private Question question;
	private KramTest kramTest;
	private int poradie; 

	public TestController(Stage stage, User user, List<Question> questions) {
		this.stage = stage;
		this.user = user;
		this.questions = questions;
	}

    @FXML
    private VBox panebix;

    @FXML
    private Label topic;
	// private ObjectProperty<Subject> selectedSubject = new
	// SimpleObjectProperty<Subject>();
	// private ObjectProperty<Zameranie> selectedTopic = new
	// SimpleObjectProperty<Zameranie>();
	

	@FXML
	void initialize() {
		topic.setText("This is your test");
		topic.setFont(Font.font(25));
		for (Question question : questions) {
			HBox otazkaBox = new HBox();
			VBox chosen = new VBox();
			chosen.setPadding(new Insets(2, 20, 2, 5));
			VBox moznosti = new VBox();
			Label txt = new Label();
			txt.setText(question.getTitle());
			txt.setFont(Font.font(20));
			panebix.getChildren().add(txt);
			Map<Option, Boolean> option = question.getOptions();
			for (Map.Entry<Option, Boolean> entry : option.entrySet()) {
				
				CheckBox check = new CheckBox();
				check.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						// TODO Auto-generated method stub
						
					}
				});
				chosen.getChildren().add(check);
				Label text = new Label();
				text.setText(entry.getKey().getTitle());
				text.setAlignment(Pos.CENTER_LEFT);
				text.setFont(Font.font(16));
				text.setTextFill(Color.DODGERBLUE);
				moznosti.getChildren().add(text);
				
			}
			otazkaBox.getChildren().add(chosen);
			otazkaBox.getChildren().add(moznosti);
			
			
			panebix.getChildren().add(otazkaBox);
		}
		//panebix.getChildren().add(otazkaBox);

		
		

	}

}
