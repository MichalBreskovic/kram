package WindowsControler.userPages;

import java.sql.PseudoColumnUsage;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sound.midi.Soundbank;

import org.apache.commons.collections4.MultiMap;

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
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import kram.storage.DaoFactory;
import kram.storage.option.Option;
import kram.storage.option.OptionDao;
import kram.storage.question.Question;
import kram.storage.question.QuestionDao;
import kram.storage.subject.Subject;
import kram.storage.subject.SubjectDao;
import kram.storage.test.KramTest;
import kram.storage.test.MysqlTestDao;
import kram.storage.test.TestDao;
import kram.storage.user.User;
import kram.storage.zameranie.Zameranie;
import kram.storage.zameranie.ZameranieDao;

public class TestController {
	private SubjectDao subjectDao = DaoFactory.INSTATNCE.getSubjectDao();
	private ZameranieDao zameranieDao = DaoFactory.INSTATNCE.getZameranieDao();
	private QuestionDao questionDao = DaoFactory.INSTATNCE.getQuestionDao();
	private OptionDao optionDao = DaoFactory.INSTATNCE.getOptionDao();
	private TestDao testdao = DaoFactory.INSTATNCE.getTestDao();

	private Stage stage;
	private User user;
	private boolean justLook;
	private Zameranie zameranie;
	private List<Question> questions;
	private Question question;
	private KramTest kramTest;
	private int poradie;

	public TestController(Stage stage, User user, List<Question> questions, Zameranie zameranie, boolean justLook) {
		this.stage = stage;
		this.user = user;
		this.questions = questions;
		this.zameranie = zameranie;
		this.justLook = justLook;
	}

	public TestController(Stage stage, User user, KramTest kramTest, boolean justLook) {
		this.stage = stage;
		this.user = user;
		this.kramTest = kramTest;
		this.justLook = justLook;
	}

	@FXML
	private VBox panebix;

	@FXML
	private Label topic;
	// private ObjectProperty<Subject> selectedSubject = new
	// SimpleObjectProperty<Subject>();
	// private ObjectProperty<Zameranie> selectedTopic = new
	// SimpleObjectProperty<Zameranie>();

	int finalhodnotenie = 0;

	@FXML
	void initialize() {

		// https://www.javatpoint.com/java-get-current-date
		if (!justLook) {

			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			LocalDateTime now = LocalDateTime.now();
			// https://www.javatpoint.com/java-get-current-date
			KramTest test = new KramTest(user.getIdUser(), zameranie.getIdZameranie(), dtf.format(now));
			boolean[][] pole = new boolean[questions.size()][8];
			panebix.setAlignment(Pos.CENTER);
			panebix.setSpacing(20);
			topic.setText("This is test from " + zameranie.getTitle());
			topic.setTextFill(Color.DODGERBLUE);
			topic.setFont(Font.font("System", FontWeight.BOLD, 24));
			int krok = 0;
			for (Question question : questions) {
				HBox otazkaBox = new HBox();
				// otazkaBox.setId(""+krok);
				otazkaBox.setSpacing(18);
				otazkaBox.setAlignment(Pos.CENTER);
				VBox chosen = new VBox();
				chosen.setAlignment(Pos.CENTER);
				chosen.setSpacing(9);
				VBox moznosti = new VBox();
				moznosti.setAlignment(Pos.CENTER_LEFT);
				Label txt = new Label();
				Label qstn = new Label();
				qstn.setText("Question number " + krok);
				qstn.setFont(Font.font("System", FontWeight.BOLD, 20));
				qstn.setTextFill(Color.DARKCYAN);
				txt.setTextFill(Color.LIGHTSLATEGRAY);
				txt.setText(question.getTitle());
				// txt.setFont(Font.font(20));
				txt.setFont(Font.font("System", FontWeight.BOLD, 20));
				txt.setTextFill(Color.LIGHTSLATEGRAY);
				// panebix.getChildren().add(qstn);
				panebix.getChildren().add(qstn);
				panebix.getChildren().add(txt);
				Map<Option, Boolean> option = question.getOptions();
				int id = 0;
				for (Map.Entry<Option, Boolean> entry : option.entrySet()) {

					CheckBox check = new CheckBox();
					check.setId(krok + " " + id);
					check.setLayoutY(20);
					check.setOnAction(new EventHandler<ActionEvent>() {

						@Override
						public void handle(ActionEvent event) {
							String[] ids = check.getId().split(" ");
							if (pole[Integer.parseInt(ids[0])][Integer.parseInt(ids[1])]) {
								pole[Integer.parseInt(ids[0])][Integer.parseInt(ids[1])] = false;
							} else {
								pole[Integer.parseInt(ids[0])][Integer.parseInt(ids[1])] = true;
							}

						}
					});
					chosen.getChildren().add(check);
					Label text = new Label();
					text.setText(entry.getKey().getTitle());
					text.setAlignment(Pos.CENTER_LEFT);
					text.setFont(Font.font(16));
					text.setTextFill(Color.DODGERBLUE);
					moznosti.getChildren().add(text);
					id++;
				}
				otazkaBox.getChildren().add(chosen);
				otazkaBox.getChildren().add(moznosti);

				krok++;
				panebix.getChildren().add(otazkaBox);
			}
			Button finish = new Button();
			finish.setText("FINISH");
			finish.setFont(Font.font("System", FontWeight.BOLD, 20));
			finish.setTextFill(Color.DODGERBLUE);
			panebix.getChildren().add(finish);

			finish.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {

					int otazka = 0;

					for (Question question : questions) {
						int moznost = 0;
						int hodnotenie = 0;
						for (Map.Entry<Option, Boolean> entry : question.getOptions().entrySet()) {
							if (pole[otazka][moznost]) {
								// System.out.println(question.getTitle());
								// System.out.println(test.getAnswers());
								// System.out.println(entry.getKey());
								test.getAnswers().put(question, entry.getKey());
							}

							if (entry.getValue() == pole[otazka][moznost]) {
								System.out.println(entry.getValue() + "	" + finalhodnotenie);
								hodnotenie++;

							}

							moznost++;
						}
						if (hodnotenie == question.getOptions().size()) {
							finalhodnotenie++;
						}
						otazka++;
					}
					System.out.println(questions.size());
					test.setHodnotenie((int) ((double) (finalhodnotenie) / questions.size() * 100));
					System.out.println((int) ((double) (finalhodnotenie) / questions.size() * 100) + "%");

					LocalDateTime now2 = LocalDateTime.now();
					test.setEnd(dtf.format(now2));
					testdao.saveTest(test);
					try {
						TestController controller = new TestController(stage, user, test, true);
						FXMLLoader fxmlLoader2 = new FXMLLoader(UserPageControler.class.getResource("TestPage.fxml"));
						fxmlLoader2.setController(controller);
						Parent rootPane = fxmlLoader2.load();
						Scene scene = new Scene(rootPane);
						stage.setTitle("VIEWTEST");
						stage.setScene(scene);
					} catch (Exception e) {
						e.printStackTrace();
						System.out.println("fail again");
					}
				}
			});
		} else {
			panebix.setAlignment(Pos.CENTER);
			panebix.setSpacing(20);
			topic.setText(
					"Yout answers from generated test from " + zameranieDao.getById(kramTest.getIdTopic()).getTitle());
			topic.setTextFill(Color.DODGERBLUE);
			topic.setFont(Font.font("System", FontWeight.BOLD, 24));
			VBox moznosti = new VBox();
			int krok = 1;
			Long aktualId = 0l;
			System.out.println(kramTest.getAnswers().keys());
			for (Question entry : kramTest.getAnswers().keySet()) {
				HBox otazkaBox = new HBox();
				otazkaBox.setSpacing(18);
				otazkaBox.setAlignment(Pos.CENTER);
				Label txt = new Label();
				Label qstn = new Label();
				qstn.setText("Question number " + krok);
				qstn.setFont(Font.font("System", FontWeight.BOLD, 20));
				qstn.setTextFill(Color.DARKCYAN);
				txt.setTextFill(Color.LIGHTSLATEGRAY);
				txt.setText(entry.getTitle());
				txt.setFont(Font.font("System", FontWeight.BOLD, 20));
				txt.setTextFill(Color.LIGHTSLATEGRAY);
				panebix.getChildren().add(qstn);
				panebix.getChildren().add(txt);
				krok++;
				moznosti = new VBox();
				moznosti.setAlignment(Pos.CENTER);
				List<Option> selected = FXCollections.observableArrayList(kramTest.getAnswers().get(question));
				for (Map.Entry<Option, Boolean> moznost : entry.getOptions().entrySet()) {
					for (Option option : selected) {
						Label text = new Label();
						text.setTextFill(Color.DODGERBLUE);
						text.setText(moznost.getKey().getTitle());
						if (moznost.getKey().getIdOption()==option.getIdOption()) {
							
							if (moznost.getValue()) {
								text.setTextFill(Color.GREEN);
								text.setText("✓  " + moznost.getKey().getTitle());
								text.setAlignment(Pos.CENTER_LEFT);
								text.setFont(Font.font(16));
							}if (!moznost.getValue()) {
								text.setTextFill(Color.RED);
								text.setText("✕  " + moznost.getKey().getTitle());
								text.setAlignment(Pos.CENTER_LEFT);
								text.setFont(Font.font(16));
							}
							
						}
						moznosti.getChildren().add(text);
					}

				}
				otazkaBox.getChildren().add(moznosti);
				panebix.getChildren().add(otazkaBox);

			}
			Button finish = new Button();
			finish.setText("CLOSE");
			finish.setFont(Font.font("System", FontWeight.BOLD, 20));
			finish.setTextFill(Color.DODGERBLUE);
			panebix.getChildren().add(finish);
			finish.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					try {
						UserPageControler controller = new UserPageControler(stage, user);
						FXMLLoader fxmlLoader2 = new FXMLLoader(UserPageControler.class.getResource("UserPage.fxml"));
						fxmlLoader2.setController(controller);
						Parent rootPane = fxmlLoader2.load();
						Scene scene = new Scene(rootPane);
						stage.setTitle("VIEWTEST");
						stage.setScene(scene);
					} catch (Exception e) {
						e.printStackTrace();
						System.out.println("fail again");
					}

				}
			});
		}
	}

}