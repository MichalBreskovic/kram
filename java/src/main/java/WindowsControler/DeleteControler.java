package WindowsControler;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import kram.appka.App;
import kram.storage.DaoFactory;
import kram.storage.subject.SubjectDao;
import kram.storage.user.User;
import kram.storage.user.UserDao;

public class DeleteControler {
	private User user;
	private Stage stage2;
	private Stage stage1;

	public DeleteControler(User user, Stage stage2, Stage stage1) {
		this.user = user;
		this.stage2 = stage2;
		this.stage1 = stage1;
	}

	@FXML
	private Label errorfield;

	@FXML
	private Button yes;

	@FXML
	private Button no;
	private UserDao userDao = DaoFactory.INSTATNCE.getUserDao();
	@FXML
	void initialize() {
		yes.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				stage2.close();
				userDao.deleteUser(user.getIdUser());
				try {
					
					
					FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("WelcomePageCurrent.fxml"));
					WelcomePageControler controller = new WelcomePageControler(stage1);
					fxmlLoader.setController(controller);
					Parent rootPane = fxmlLoader.load();
					Scene scene = new Scene(rootPane);
					stage1.setTitle("KRAM");
					stage1.setScene(scene);
					stage1.show();
					
				} catch (Exception e) {
					// TODO: handle exception
				}
			

			}
		});
		no.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				stage1.show();
				stage2.close();
				

			}
		});
		stage2.setOnCloseRequest(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent event) {
				stage1.show();
				
			}
		});
	}
}
