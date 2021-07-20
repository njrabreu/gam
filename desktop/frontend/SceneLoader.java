package apps.gam.desktop.frontend;

import java.io.IOException;
import java.net.URL;

import apps.gam.desktop.core.Main;
import apps.gam.desktop.core.MyLogger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class SceneLoader {

	private Scene scene;
	private FXMLLoader fxmlLoader;

	public SceneLoader(String fileName, Login l) throws IOException {

		URL u = getClass().getResource(fileName);

		fxmlLoader = new FXMLLoader(u);

		fxmlLoader.setControllerFactory(Login -> l);

		Parent root = fxmlLoader.load();

		scene = new Scene(root);

		// Listen for key presses
		scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
			if (key.getCode() == KeyCode.ESCAPE) {
				MyLogger.WriteMessage("You pressed ESCAPE...");
				Platform.exit();
			}
		});

		// If the user presses ENTER, trigger login button click
		scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
			if (key.getCode() == KeyCode.ENTER) {
				MyLogger.WriteMessage("You pressed ENTER...");
				l.LoginButtonClick(new ActionEvent());
			}
		});

	}

	public SceneLoader(String fileName, MainWindow m) throws IOException {

		fxmlLoader = new FXMLLoader(Main.class.getResource(fileName));

		fxmlLoader.setControllerFactory(MainWindow -> m);

		Parent root = fxmlLoader.load();

		scene = new Scene(root);

		// Listen for key presses
		scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
			if (key.getCode() == KeyCode.ESCAPE) {
				MyLogger.WriteMessage("You pressed ESCAPE...");
				Platform.exit();
			}
		});

	}

	public Scene GetScene() {

		return scene;

	}

	public FXMLLoader GetFXMLLoader() {

		return fxmlLoader;

	}

}
