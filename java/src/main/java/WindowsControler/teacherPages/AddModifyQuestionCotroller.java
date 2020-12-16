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

public class AddModifyQuestionCotroller {
	private SubjectDao subjectDao = DaoFactory.INSTATNCE.getSubjectDao();
	private ZameranieDao zameranieDao = DaoFactory.INSTATNCE.getZameranieDao();
	private QuestionDao questionDao = DaoFactory.INSTATNCE.getQuestionDao();
	 private OptionDao optionDao = DaoFactory.INSTATNCE.getOptionDao();

	private Stage stage;
	private User user;
	private Subject subject;
	private Zameranie zameranie;
	private Question question;
	private boolean edit;

	public AddModifyQuestionCotroller(Stage stage, User user, Subject subject, Zameranie zameranie, boolean edit) {
		this.stage = stage;
		this.user = user;
		this.subject = subject;
		this.zameranie = zameranie;
		this.edit = edit;
	}

	public AddModifyQuestionCotroller(Stage stage, Question question, boolean edit) {
		this.stage = stage;
		this.question = question;
		this.edit = edit;
	}

	@FXML
	private TextField option;
	@FXML
	private TextField option1;
	@FXML
	private Button add;
	@FXML
	private Button dlt;
	@FXML
	private Button addquestion;
	@FXML
	private Button back;
	@FXML
	private Label errorfield2;
	@FXML
	private TextField title;
	@FXML
	private VBox box1;
	@FXML
	private VBox box;
	@FXML
	private CheckBox check;
	@FXML
	private CheckBox check1;
	@FXML
	private Label tlt;
	@FXML
	private Label qvs;

	// private ObjectProperty<Subject> selectedSubject = new
	// SimpleObjectProperty<Subject>();
	// private ObjectProperty<Zameranie> selectedTopic = new
	// SimpleObjectProperty<Zameranie>();
	int pocet = 2;
	int correctAnswers = 0;
	@FXML
	void initialize() {
		// int pocet = 4;
		List<TextField> options = new ArrayList<TextField>();
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
		
		if (edit) {
			addquestion.setText("EDIT QUESTION");
			tlt.setText("Edit question");
			qvs.setText("Your question");
			pocet = question.getOptions().size();
			if (pocet > 2) {
				int generuj = pocet - 2;
				for (int i = 0; i < generuj; i++) {
					TextField txt = new TextField();
					CheckBox chc = new CheckBox();
					
					txt.setId("option" + pocet);
					chc.setId("check" + pocet);
					box1.getChildren().add(chc);
					box.getChildren().add(txt);

				}
			}
			for (int i = 0; i < pocet; i++) {
				question.getOptions();
			}

		}else {
			dlt.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					if (pocet > 2) {
						box.getChildren().remove(pocet - 1);
						box1.getChildren().remove(pocet - 1);
						options.remove(pocet - 1);
						correct.remove(pocet - 1);
						pocet--;
					}

				}
			});
			add.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					if (pocet < 8) {
						TextField txt = new TextField();
						CheckBox chc = new CheckBox();
						txt.setId("option" + pocet);
						options.add(txt);
						chc.setId("check" + pocet);
						chc.setOnAction(new EventHandler<ActionEvent>() {

							@Override
							public void handle(ActionEvent event) {
								if (iscorrect[pocet-1]) {
									iscorrect[pocet-1] = false;
								} else {
									iscorrect[pocet-1] = true;
								}

								
							}
						});
						correct.add(chc);
						box1.getChildren().add(chc);
						box.getChildren().add(txt);
						pocet++;
						
					}

				}
			});
			
			addquestion.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					boolean done = true;
					correctAnswers=0;
					if (title.getText() == null || title.getText().trim().isEmpty()) {
						errorfield2.setTextFill(Color.RED);
						errorfield2.setText("Input title of your question");
						done = false;
					}
					for (TextField option : options) {
						if (option.getText() == null || option.getText().trim().isEmpty()) {
							errorfield2.setTextFill(Color.RED);
							errorfield2.setText("One of the options is empty");
							
							done = false;
						}
					}
					System.out.println("pocet moznosti je "+ options.size());
					System.out.println(pocet);
					for (int i = 0; i < pocet; i++) {
						System.out.println(iscorrect[i]);
						if (iscorrect[i]) {
							correctAnswers++;
						}
						
					}
					if (correctAnswers==0) {
						errorfield2.setTextFill(Color.RED);
						done = false;
						errorfield2.setText("There is no correct answer");
					}
					if (done) {
						errorfield2.setTextFill(Color.GREEN);
						errorfield2.setText("EWERYTHING OK");
						Question question = new Question(title.getText(), zameranie.getIdSubject(), user.getIdUser());
						int index = 0;
						for (TextField option : options) {
							
							question.addOption(optionDao.saveOption(new Option(option.getText())), iscorrect[index]);
							index++;
						}
						questionDao.saveQuestion(question);

					}

				}
			});

			back.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					try {
						UserTeacherPageControler controller = new UserTeacherPageControler(stage, user);
						FXMLLoader fxmlLoader2 = new FXMLLoader(
								UserTeacherPageControler.class.getResource("UserTeacherPage.fxml"));
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

}