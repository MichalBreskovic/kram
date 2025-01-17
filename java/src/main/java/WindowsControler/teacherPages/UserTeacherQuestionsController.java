package WindowsControler.teacherPages;

import WindowsControler.UserPageProfileController;
import WindowsControler.WelcomePageControler;
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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import kram.storage.DaoFactory;
import kram.storage.question.Question;
import kram.storage.question.QuestionDao;
import kram.storage.subject.Subject;
import kram.storage.subject.SubjectDao;
import kram.storage.user.User;
import kram.storage.zameranie.Zameranie;
import kram.storage.zameranie.ZameranieDao;

public class UserTeacherQuestionsController {
	private Stage stage;
	private User user;

	public UserTeacherQuestionsController(Stage stage, User user) {
		this.stage = stage;
		this.user = user;
	}

	@FXML
	private Label username;

	@FXML
	private Button classes;
	@FXML
	private Button dlt;

	@FXML
	private Button profile;

	@FXML
	private ListView<Question> listview;

	@FXML
	private ChoiceBox<Subject> subjectchoice;

	@FXML
	private ChoiceBox<Zameranie> topicchoice;

	@FXML
	private Button editquestion;

	@FXML
	private Button tests;

	@FXML
	private Button addquestion;

	@FXML
	private Label errorfield;

	private QuestionDao questionDao = DaoFactory.INSTATNCE.getQuestionDao();
	private SubjectDao subjectDao = DaoFactory.INSTATNCE.getSubjectDao();
	private ZameranieDao zameranieDao = DaoFactory.INSTATNCE.getZameranieDao();
	private ObjectProperty<Subject> selectedSubject = new SimpleObjectProperty<Subject>();
	private ObjectProperty<Zameranie> selectedTopic = new SimpleObjectProperty<Zameranie>();
	private ObjectProperty<Question> selectedQuestion = new SimpleObjectProperty<Question>();

	@FXML
	void initialize() {
		username.setText(user.getName() + " " + user.getSurname());
		listview.setItems(FXCollections.observableArrayList(questionDao.getAllByUserId(user.getIdUser())));

		subjectchoice.setItems(FXCollections.observableArrayList(subjectDao.getAllForUser(user.getIdUser())));
		//topicchoice.setItems(FXCollections.observableArrayList(zameranieDao.getAllForTeacher(user.getIdUser())));
		
		listview.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Question>() {

			@Override
			public void changed(ObservableValue<? extends Question> observable, Question oldValue, Question newValue) {
				selectedQuestion.setValue(newValue);

			}
		});
		selectedQuestion.addListener(new ChangeListener<Question>() {

			@Override
			public void changed(ObservableValue<? extends Question> observable, Question oldValue, Question newValue) {
				if (newValue == null) {
					listview.getSelectionModel().clearSelection();
				} else {
					listview.getSelectionModel().select(newValue);
				}

			}
		});
		subjectchoice.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Subject>() {

			@Override
			public void changed(ObservableValue<? extends Subject> observable, Subject oldValue, Subject newValue) {
				selectedSubject.setValue(newValue);
				// topicchoice.getItems().clear();
				listview.getItems().clear();
				selectedTopic.setValue(null);
				try {
					topicchoice.setItems(FXCollections.observableArrayList(zameranieDao.getAllForTeacherBySubjectId(user.getIdUser(), selectedSubject.getValue().getIdSubject())));
				} catch (NullPointerException e) {
					// TODO Auto-generated catch block
					// e.printStackTrace();
				}
				listview.setItems(FXCollections.observableArrayList(
						questionDao.getBySubjectUserId(selectedSubject.getValue().getIdSubject(), user.getIdUser())));
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
				// System.out.println(newValue);
				listview.getItems().clear();
				//selectedSubject.set(null);
				try {
					listview.setItems(FXCollections.observableArrayList(
							questionDao.getByTopicUserId(selectedTopic.getValue().getIdZameranie(), user.getIdUser())));
				} catch (NullPointerException e) {
					// TODO Auto-generated catch block
					// e.printStackTrace();
				}
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
		tests.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					UserTeacherQuestionsController controller = new UserTeacherQuestionsController(stage, user);
					FXMLLoader fxmlLoader2 = new FXMLLoader(
							UserTeacherPageControler.class.getResource("UserTeacherQuestionsPage.fxml"));
					fxmlLoader2.setController(controller);
					Parent rootPane = fxmlLoader2.load();
					Scene scene = new Scene(rootPane);
					stage.setTitle("Questions");
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
					UserTeacherClassesController controller = new UserTeacherClassesController(stage, user);
					FXMLLoader fxmlLoader2 = new FXMLLoader(
							UserTeacherPageControler.class.getResource("UserTeacherClassesPage.fxml"));
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
					FXMLLoader fxmlLoader2 = new FXMLLoader(
							WelcomePageControler.class.getResource("UserPageProfile.fxml"));
					fxmlLoader2.setController(controller);
					Parent rootPane = fxmlLoader2.load();
					Scene scene = new Scene(rootPane);
					stage.setTitle("Profile");
					stage.setScene(scene);
				} catch (Exception e) {
					// TODO: handle exception
				}

			}

		});
		addquestion.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					CreateQuestionController controller = new CreateQuestionController(stage, user);
					FXMLLoader fxmlLoader2 = new FXMLLoader(
							UserTeacherQuestionsController.class.getResource("CreateQuestion.fxml"));
					fxmlLoader2.setController(controller);
					Parent rootPane = fxmlLoader2.load();
					Scene scene = new Scene(rootPane);
					stage.setTitle("Add Question");
					stage.setScene(scene);
				} catch (Exception e) {
					// TODO: handle exception
				}

			}
		});
		editquestion.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (selectedQuestion.getValue() == null) {
					errorfield.setTextFill(Color.RED);
					errorfield.setText("U need to choose question to edit");

				} else {

					try {
						AddModifyQuestionCotroller controller = new AddModifyQuestionCotroller(stage, user,
								selectedQuestion.getValue(), true);
						FXMLLoader fxmlLoader2 = new FXMLLoader(
								UserTeacherPageControler.class.getResource("QuestionAddModifyPage.fxml"));
						fxmlLoader2.setController(controller);
						Parent rootPane = fxmlLoader2.load();
						Scene scene = new Scene(rootPane);
						stage.setTitle("Add question");
						stage.setScene(scene);

					} catch (Exception e) {
						System.out.println("chybicka");
					}

				}
			}
		});
		dlt.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				if (selectedQuestion.getValue()==null) {
					errorfield.setTextFill(Color.RED);
					errorfield.setText("Choose question you want to delete");
				}else {
					errorfield.setText("");
					questionDao.deleteQuestion(selectedQuestion.getValue().getIdQuestion());
					listview.getItems().remove(selectedQuestion.getValue());
					if (listview.getItems().size()==0) {
						topicchoice.getItems().remove(selectedTopic.getValue());
						selectedTopic.setValue(null);
						
					}
					if (topicchoice.getItems().size()==0) {
						subjectchoice.getItems().remove(selectedSubject.getValue());
						selectedSubject.setValue(null);
					}
					selectedQuestion.setValue(null);
					
				}
				
			}
		});

	}
}
