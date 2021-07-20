package apps.gam.desktop.core;

import java.io.IOException;

import apps.gam.desktop.database.DBConnection;
import apps.gam.desktop.frontend.Login;
import apps.gam.desktop.frontend.MainWindow;
import apps.gam.desktop.frontend.SceneLoader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

public class Main extends Application {

	private static SceneLoader	loginScene;
	private static SceneLoader	mainWindow;
	private static Stage 		primaryStage;
	
	@Override
	public void start(Stage stage) throws IOException {
		
		primaryStage = stage;
		
		loginScene = new SceneLoader("../frontend/login.fxml", new Login());
		mainWindow = new SceneLoader("../frontend/mainwindow.fxml", new MainWindow());
		
		stage.setScene(loginScene.GetScene());
		stage.setResizable(false);
		stage.setTitle("Welcome!");
		stage.show();
		
	}
	
	public static void SwitchToMainWindow(String username) {
		
		MyLogger.WriteMessage("Switch to Main Window...");
		
		primaryStage.hide();
		
		FXMLLoader loader = mainWindow.GetFXMLLoader();
		
		// Inject values from one scene to another
		MainWindow controller = loader.getController();
		controller.setUsername(username);
		
		Stage mainWindowStage = new Stage();
		
		mainWindowStage.setScene(mainWindow.GetScene());
		mainWindowStage.setResizable(false);
		mainWindowStage.setTitle("Game Manager - V1.0");
		mainWindowStage.show();
		
	}

	public void init() throws Exception {
		
		DBConnection.openConnection();
		
	}
	
	public void stop() throws Exception {
		
		DBConnection.closeConnection();
		
		MyLogger.WriteMessage("Bye...");
		
	}
	
	public static void main(String... args) {
		
		Application.launch(args);
		
	}
}
