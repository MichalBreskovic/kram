package WindowsControler.teacherPages;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import kram.storage.DaoFactory;
import kram.storage.course.Course;
import kram.storage.course.CourseDao;
import kram.storage.option.Option;
import kram.storage.question.Question;
import kram.storage.question.QuestionDao;
import kram.storage.subject.Subject;
import kram.storage.subject.SubjectDao;
import kram.storage.test.KramTest;
import kram.storage.test.TestDao;
import kram.storage.user.User;
import kram.storage.user.UserDao;
import kram.storage.zameranie.Zameranie;
import kram.storage.zameranie.ZameranieDao;

public class AddTestToCourseController {
	private SubjectDao subjectDao = DaoFactory.INSTATNCE.getSubjectDao();
	private ZameranieDao zameranieDao = DaoFactory.INSTATNCE.getZameranieDao();
	private QuestionDao questionDao = DaoFactory.INSTATNCE.getQuestionDao();
	private CourseDao courseDao = DaoFactory.INSTATNCE.getCourseDao();
	private UserDao userDao = DaoFactory.INSTATNCE.getUserDao();
	private TestDao testDao = DaoFactory.INSTATNCE.getTestDao();
	private Stage stage;
	private User user;
	private Course course;

	public AddTestToCourseController(Stage stage, User user, Course course) {
		this.stage = stage;
		this.user = user;
		this.course = course;
	}
	  
	@FXML
    private TextField name;
	
    @FXML
    private Label username1;

    @FXML
    private Button addquestion;

    @FXML
    private ListView<Question> qstns;

    @FXML
    private Label errorfield;

    @FXML
    private ChoiceBox<Subject> subjectchoice;

    @FXML
    private ChoiceBox<Zameranie> topicchoice;

    @FXML
    private Button back;

    @FXML
    private Button createtest;

    @FXML
    private Button view;

    @FXML
    private ListView<Question> qstnsttt;
    
    @FXML
    private Button dltquestion;


	boolean zmena = false;
	private ObjectProperty<Subject> selectedSubject = new SimpleObjectProperty<Subject>();
	private ObjectProperty<Zameranie> selectedTopic = new SimpleObjectProperty<Zameranie>();
	private ObjectProperty<Question> selectedQuestion = new SimpleObjectProperty<Question>();
	private ObjectProperty<Question> selectedQuestionInTest = new SimpleObjectProperty<Question>();

	@FXML
	void initialize() {
		//KramTest test = new KramTest(idUser, idTopic, start);
		selectedSubject.setValue(null);
		selectedTopic.setValue(null);
		subjectchoice.setItems(FXCollections.observableArrayList(subjectDao.getAll()));
		topicchoice.setItems(FXCollections.observableArrayList(zameranieDao.getAll()));
		qstns.setItems(FXCollections.observableArrayList(questionDao.getAll()));
		subjectchoice.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Subject>() {

			@Override
			public void changed(ObservableValue<? extends Subject> observable, Subject oldValue, Subject newValue) {
				selectedSubject.setValue(newValue);
				topicchoice.getItems().clear();
				topicchoice.setItems(FXCollections.observableArrayList(zameranieDao.getAllBySubjectId(selectedSubject.getValue().getIdSubject())));
				qstns.getItems().clear();
				qstns.setItems(FXCollections.observableArrayList(questionDao.getBySubjectId(selectedSubject.getValue().getIdSubject())));
			}

		});
		selectedSubject.addListener(new ChangeListener<Subject>() {

			@Override
			public void changed(ObservableValue<? extends Subject> observable, Subject oldValue, Subject newValue) {
				if (newValue == null) {
					subjectchoice.getSelectionModel().clearSelection();
				} else {
					subjectchoice.getSelectionModel().select(newValue);
				}

			}
		});
		topicchoice.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Zameranie>() {

			@Override
			public void changed(ObservableValue<? extends Zameranie> observable, Zameranie oldValue,
					Zameranie newValue) {
				selectedTopic.setValue(newValue);
				qstns.getItems().clear();
				qstns.setItems(FXCollections.observableArrayList(questionDao.getByTopicId(selectedTopic.getValue().getIdSubject())));
				//System.out.println(selectedTopic.get().getIdZameranie());
			}

		});
		selectedTopic.addListener(new ChangeListener<Zameranie>() {

			@Override
			public void changed(ObservableValue<? extends Zameranie> observable, Zameranie oldValue,
					Zameranie newValue) {
				if (newValue == null) {
					topicchoice.getSelectionModel().clearSelection();
				} else {
					topicchoice.getSelectionModel().select(newValue);
				}

			}
		});
		qstns.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Question>() {

			@Override
			public void changed(ObservableValue<? extends Question> observable, Question oldValue,
					Question newValue) {
				selectedQuestion.setValue(newValue);
			}

		});
		selectedQuestion.addListener(new ChangeListener<Question>() {

			@Override
			public void changed(ObservableValue<? extends Question> observable, Question oldValue,
					Question newValue) {
				if (newValue == null) {
					qstns.getSelectionModel().clearSelection();
				} else {
					qstns.getSelectionModel().select(newValue);
				}

			}
		});
		qstnsttt.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Question>() {

			@Override
			public void changed(ObservableValue<? extends Question> observable, Question oldValue,
					Question newValue) {
				selectedQuestionInTest.setValue(newValue);
			}

		});
		selectedQuestionInTest.addListener(new ChangeListener<Question>() {

			@Override
			public void changed(ObservableValue<? extends Question> observable, Question oldValue,
					Question newValue) {
				if (newValue == null) {
					qstnsttt.getSelectionModel().clearSelection();
				} else {
					qstnsttt.getSelectionModel().select(newValue);
				}

			}
		});
		
		addquestion.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (selectedQuestion.getValue()!=null) {
					qstnsttt.getItems().add(selectedQuestion.getValue());
					System.out.println(qstnsttt.getItems());
				}
				
				
			}
		});
		dltquestion.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				qstnsttt.getItems().remove(selectedQuestionInTest.getValue());
				System.out.println(qstnsttt.getItems());
				
			}
		});
		back.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					UserTeacherClassesController controller = new UserTeacherClassesController(stage, user);
					FXMLLoader fxmlLoader2 = new FXMLLoader(
							UserTeacherClassesController.class.getResource("UserTeacherClassesPage.fxml"));
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
		createtest.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (qstnsttt.getItems().size() == 0 || name.getText() == null || name.getText().isBlank()) {
					errorfield.setTextFill(Color.RED);
					errorfield.setText("You have no questions or there is no name in your test");
				} else {
					List<Question> showing = new ArrayList<Question>(qstnsttt.getItems());
					System.out.println(showing);
					for (User student : userDao.getAllAcceptedInCourse(course.getIdCourse())) {
						System.out.println(student.getIdUser());
						System.out.println(student.getName());
						KramTest test = new KramTest(student.getIdUser());
						test.setQuestions(showing);
						test.setName(name.getText());
						System.out.println(test.getAnswers().keys());
						testDao.saveTestToCourse(test, course.getIdCourse());
					}
					
					try {
						UserTeacherClassesController controller = new UserTeacherClassesController(stage, user);
						FXMLLoader fxmlLoader2 = new FXMLLoader(
								UserTeacherClassesController.class.getResource("UserTeacherClassesPage.fxml"));
						fxmlLoader2.setController(controller);
						Parent rootPane = fxmlLoader2.load();
						Scene scene = new Scene(rootPane);
						stage.setTitle("Classes");
						stage.setScene(scene);
					} catch (Exception e) {
						// TODO: handle exception
					}
					
				}
				
			}
		});
		view.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (selectedQuestion.getValue() == null) {
					errorfield.setTextFill(Color.RED);
					errorfield.setText("U need to choose question to edit");

				} else {
					AnchorPane pane =new AnchorPane();
					pane.setPrefSize(400, 300);
					VBox box = new VBox();
					box.setAlignment(Pos.TOP_CENTER);
				
					Label label = new Label();
					label.setText(selectedQuestion.getName());
					box.getChildren().add(label);
					for (Entry<Option, Boolean> entry : selectedQuestion.getValue().getOptions().entrySet()) {
						Label label2 = new Label();
						if (entry.getValue()) {
							label.setTextFill(Color.GREEN);
						}
						label2.setText(entry.getKey().getTitle());
						box.getChildren().add(label2);
					}
					pane.getChildren().add(box);
					Scene scene = new Scene(pane);
					Stage stage2 = new Stage();
					stage2.setScene(scene);
					stage2.show();
				
				}
			}
		});
	}

}
