package apps.gam.desktop.frontend;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import apps.gam.desktop.backend.User;
import apps.gam.desktop.core.Main;
import apps.gam.desktop.core.MyLogger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class Login extends Controller {

	private static final int MAX_CHARACTERS = 10;

	@FXML
	private TextField idtextfield;

	@FXML
	private PasswordField passtextfield;

	@FXML
	private Label loginLabel;

	@FXML
	public void LoginButtonClick(ActionEvent event) {

		event.consume(); // Mandatory: to avoid propagation of button click

		MyLogger.WriteMessage("Click on Login button!");

		// Only check in database if user inserted values
		if (!idtextfield.getText().isEmpty() && !passtextfield.getText().isEmpty())
			this.manageDBOperations();

	}

	@FXML
	public void RegisterLinkClick(ActionEvent event) {

		event.consume(); // Mandatory: to avoid propagation of button click

		MyLogger.WriteMessage("Click on Register link!");

		if (!idtextfield.getText().isEmpty() && !passtextfield.getText().isEmpty())
			this.registerUser();

	}
	
	public void ClearTextBoxes() {
		idtextfield.clear();
		passtextfield.clear();
	}

	// From parent class
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		
		int maxCharacters = MAX_CHARACTERS;

		// Limit number of characters
		idtextfield.setOnKeyTyped(event -> {

			loginLabel.setText("");
			
			if (idtextfield.getText().length() >= maxCharacters)
				event.consume();
		});

		passtextfield.setOnKeyTyped(event -> {
			
			loginLabel.setText("");
			
			if (passtextfield.getText().length() >= maxCharacters)
				event.consume();
		});

		// Start with focus in login field
		Platform.runLater(() -> idtextfield.requestFocus());

	}

	// From parent class
	public boolean manageDBOperations() {

		User u = null;

		try {
			u = new User(idtextfield.getText(), passtextfield.getText());
		} catch (SQLException e) {

			e.printStackTrace();
		}

		if (u.IsValid()) {
			MyLogger.WriteMessage("Login OK!");

			Main.SwitchToMainWindow(u.getUsername());

			return true;
		} else {
			MyLogger.WriteMessage("Invalid login!");

			loginLabel.setText("Invalid Login!");
			
			ClearTextBoxes();

			return false;
		}

	}

	private void registerUser() {

		User u = null;

		try {
			u = new User(idtextfield.getText(), passtextfield.getText());
		} catch (SQLException e) {

			e.printStackTrace();
		}

		if (u.UserAlreadyExists()) {
			MyLogger.WriteMessage("Error: user already exists!");

			loginLabel.setText("User exists!");
			
			ClearTextBoxes();
		} else {

			if (u.WriteDB()) {
				MyLogger.WriteMessage("User registered!");
				
				loginLabel.setText("Registered!");
			} else {
				MyLogger.WriteMessage("Error when registering user!");

				loginLabel.setText("Error when registering user!");
				
				ClearTextBoxes();
			}
		}

	}

}
