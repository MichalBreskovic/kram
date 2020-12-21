package WindowsControler.teacherPages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import kram.storage.DaoFactory;
import kram.storage.option.Option;
import kram.storage.option.OptionDao;
import kram.storage.question.Question;
import kram.storage.question.QuestionDao;
import kram.storage.user.User;
import kram.storage.zameranie.Zameranie;

public class AddModifyQuestionCotroller {
	private QuestionDao questionDao = DaoFactory.INSTATNCE.getQuestionDao();
	private OptionDao optionDao = DaoFactory.INSTATNCE.getOptionDao();

	private Stage stage;
	private User user;

	private Zameranie zameranie;
	private Question question;
	private boolean edit;

	public AddModifyQuestionCotroller(Stage stage, User user, Zameranie zameranie, boolean edit) {
		this.stage = stage;
		this.user = user;
		this.zameranie = zameranie;
		this.edit = edit;
	}

	public AddModifyQuestionCotroller(Stage stage, User user, Question question, boolean edit) {
		this.stage = stage;
		this.user = user;
		this.question = question;
		this.edit = edit;
	}



	@FXML
	private TextField option;
	@FXML
	private AnchorPane pane;
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

	int pocet = 2;
	int correctAnswers = 0;

	@FXML
	void initialize() {
		List<TextField> options = new ArrayList<TextField>();
		List<CheckBox> correct = new ArrayList<CheckBox>();
		options.add(option);
		options.add(option1);
		correct.add(check);
		correct.add(check1);
		addquestion.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				boolean done = true;
				correctAnswers = 0;
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
				System.out.println("pocet moznosti je " + options.size());
				System.out.println(pocet);
				for (CheckBox c : correct) {
					if (c.isSelected()) {
						correctAnswers++;
					}
				}
				if (correctAnswers == 0) {
					errorfield2.setTextFill(Color.RED);
					done = false;
					errorfield2.setText("There is no correct answer, nonononono");
				}
				if (done) {

					errorfield2.setTextFill(Color.GREEN);
					errorfield2.setText("EWERYTHING OK");
					if (question == null) {
						question = new Question(title.getText(), zameranie.getIdZameranie(), user.getIdUser());
					} else {
						question.setTitle(title.getText());
						question.setOptions(new HashMap<Option, Boolean>());
					}

					for (CheckBox b : correct) {
//						System.out.println(b.isSelected());
					}

					int index = 0;
					for (TextField option : options) {
//						System.out.println(option.getText() + "\t" + correct.get(index).isSelected());
						question.addOption(optionDao.saveOption(new Option(option.getText())),
								correct.get(index).isSelected());
						index++;
					}
					questionDao.saveQuestion(question);
					if (edit) {
						try {
							UserTeacherQuestionsController controller = new UserTeacherQuestionsController(stage, user);
							FXMLLoader fxmlLoader2 = new FXMLLoader(
									UserTeacherPageControler.class.getResource("UserTeacherQuestionsPage.fxml"));
							fxmlLoader2.setController(controller);
							Parent rootPane = fxmlLoader2.load();
							Scene scene = new Scene(rootPane);
							stage.setTitle("Welcome");
							stage.setScene(scene);

							stage.show();

						} catch (Exception e) {
							stage.show();
							System.out.println("chybicka");
						}
					} else {
						try {

							AddModifyQuestionCotroller controller = new AddModifyQuestionCotroller(stage, user,
									zameranie, false);
							FXMLLoader fxmlLoader2 = new FXMLLoader(
									UserTeacherPageControler.class.getResource("QuestionAddModifyPage.fxml"));
							fxmlLoader2.setController(controller);
							Parent rootPane = fxmlLoader2.load();
							Scene scene = new Scene(rootPane);
							stage.setTitle("Add question");
							stage.setScene(scene);
							stage.show();

						} catch (Exception e) {
							stage.show();
							System.out.println("chybiöka, pa'Ëiùeæ stalo sa, prepaöte nam prosÌÌÌm");
						}
					}

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
					e.printStackTrace();
				}

			}
		});

		if (edit) {
			Map<Option, Boolean> moznosti = question.getOptions();
			addquestion.setText("EDIT QUESTION");
			tlt.setText("Edit question");
			qvs.setText("Your question");
			pocet = moznosti.size();
			int krok = 0;
			title.setText(question.getTitle());

			for (Map.Entry<Option, Boolean> entry : moznosti.entrySet()) {

				if (krok < 2) {
					if (krok == 0) {
						// System.out.println(entry.getKey().getTitle());
						option.setText(entry.getKey().getTitle());
						check.setSelected(entry.getValue());

					}
					if (krok == 1) {
						// System.out.println(entry.getKey().getTitle());
						option1.setText(entry.getKey().getTitle());
						check1.setSelected(entry.getValue());

					}
					options.get(krok).setText(entry.getKey().getTitle());

				} else {
//					System.out.println(entry.getKey().getTitle());
					TextField txt = new TextField();
					CheckBox chc = new CheckBox();

					txt.setText(entry.getKey().getTitle());

					chc.setSelected(entry.getValue());
					options.add(txt);
					correct.add(chc);
					box1.getChildren().add(chc);
					box.getChildren().add(txt);
				}

				krok++;
			}

			dlt.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					if (pocet > 2) {
						box.getChildren().remove(pocet - 1);
						box1.getChildren().remove(pocet - 1);
//						System.out.println(pocet);
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
						options.add(txt);

						correct.add(chc);
						box1.getChildren().add(chc);
						box.getChildren().add(txt);
						pocet++;

					}

				}
			});

		} else {

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
						options.add(txt);
						correct.add(chc);
						box1.getChildren().add(chc);
						box.getChildren().add(txt);
						pocet++;

					}

				}
			});

		}

	}

}
