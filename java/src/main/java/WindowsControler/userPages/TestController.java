package WindowsControler.userPages;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import kram.storage.DaoFactory;
import kram.storage.option.Option;
import kram.storage.question.Question;
import kram.storage.test.KramTest;
import kram.storage.test.TestDao;
import kram.storage.user.User;
import kram.storage.zameranie.Zameranie;
import kram.storage.zameranie.ZameranieDao;

public class TestController {

	private ZameranieDao zameranieDao = DaoFactory.INSTATNCE.getZameranieDao();
	private TestDao testdao = DaoFactory.INSTATNCE.getTestDao();

	private Stage stage;
	private User user;
	private boolean justLook;
	private Zameranie zameranie;
	private List<Question> questions;
	private KramTest kramTest;

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

	int finalhodnotenie = 0;

	@FXML
	void initialize() {

		if (!justLook) {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			LocalDateTime now = LocalDateTime.now();
			// https://www.javatpoint.com/java-get-current-date
			KramTest test = new KramTest(user.getIdUser(), zameranie.getIdZameranie(), dtf.format(now));
			CheckBox[][] checkboxes = new CheckBox[questions.size()][8];
			panebix.setAlignment(Pos.CENTER);
			panebix.setSpacing(20);
			topic.setText("This is test from " + zameranie.getTitle());
			topic.setTextFill(Color.DODGERBLUE);
			topic.setFont(Font.font("System", FontWeight.BOLD, 24));
			int krok = 0;
			for (Question question : questions) {
				HBox otazkaBox = new HBox();

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
				txt.setFont(Font.font("System", FontWeight.BOLD, 20));
				txt.setTextFill(Color.LIGHTSLATEGRAY);

				panebix.getChildren().add(qstn);
				panebix.getChildren().add(txt);
				Map<Option, Boolean> option = question.getOptions();
				int id = 0;
				for (Map.Entry<Option, Boolean> entry : option.entrySet()) {

					CheckBox check = new CheckBox();
					// check.setId(krok + " " + id);
					// check.setLayoutY(20);
					/*
					 * check.setOnAction(new EventHandler<ActionEvent>() {
					 * 
					 * @Override public void handle(ActionEvent event) { String[] ids =
					 * check.getId().split(" "); if
					 * (pole[Integer.parseInt(ids[0])][Integer.parseInt(ids[1])]) {
					 * pole[Integer.parseInt(ids[0])][Integer.parseInt(ids[1])] = false; } else {
					 * pole[Integer.parseInt(ids[0])][Integer.parseInt(ids[1])] = true; }
					 * 
					 * } });
					 */
					chosen.getChildren().add(check);
					checkboxes[krok][id] = check;

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
							if (checkboxes[otazka][moznost] != null) {
								if (checkboxes[otazka][moznost].isSelected() ) {
									test.getAnswers().put(question, entry.getKey());
								}

							}
							

							if (entry.getValue() == checkboxes[otazka][moznost].isSelected()) {
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
			System.out.println(kramTest);
			System.out.println(kramTest.getIdTopic());
			System.out.println(zameranieDao.getById(kramTest.getIdTopic()));
			topic.setText(
					"Your answers from generated test from " + zameranieDao.getById(kramTest.getIdTopic()).getTitle());
			topic.setTextFill(Color.DODGERBLUE);
			topic.setFont(Font.font("System", FontWeight.BOLD, 24));
			VBox moznosti = new VBox();
			int krok = 1;
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
				
				List<Option> selected = new ArrayList<Option>(kramTest.getAnswers().get(entry));
				System.out.println(entry);
				//System.out.println(kramTest.getAnswers().get(entry));
				System.out.println(selected);
				for (Map.Entry<Option, Boolean> moznost : entry.getOptions().entrySet()) {
					Label text = new Label();
					text.setTextFill(Color.DODGERBLUE);
					text.setText(moznost.getKey().getTitle());
					text.setAlignment(Pos.CENTER_LEFT);
					text.setFont(Font.font(16));
					if (moznost.getValue()) {
						text.setTextFill(Color.DARKORANGE);
						text.setText(moznost.getKey().getTitle());
					}
					for (Option option : selected) {
						//System.out.println(option.getIdOption() + "  " + moznost.getKey().getIdOption());
						if (moznost.getKey().getIdOption().equals( option.getIdOption())) {
							System.out.println("rovnaju sa");
							if (moznost.getValue()) {
								text.setTextFill(Color.GREEN);
								text.setText("✓  " + moznost.getKey().getTitle());
								text.setAlignment(Pos.CENTER_LEFT);
								text.setFont(Font.font(16));
							}
							if (!moznost.getValue()) {
								text.setTextFill(Color.RED);
								text.setText("✕  " + moznost.getKey().getTitle());
								text.setAlignment(Pos.CENTER_LEFT);
								text.setFont(Font.font(16));
							}

						}

					} /*
						 * Label text = new Label(); text.setTextFill(Color.GREEN); text.setText("✓  " +
						 * moznost.getKey().getTitle()); text.setAlignment(Pos.CENTER_LEFT);
						 * text.setFont(Font.font(16)); System.out.println(moznost);
						 * moznosti.getChildren().add(text);
						 */
					moznosti.getChildren().add(text);
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
