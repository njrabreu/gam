package apps.gam.desktop.frontend;

import java.net.URL;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import apps.gam.desktop.backend.Game;
import apps.gam.desktop.backend.Platform;
import apps.gam.desktop.backend.Rate;
import apps.gam.desktop.backend.Status;
import apps.gam.desktop.core.MyLogger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.StringConverter;

public class MainWindow extends Controller {
	
	private int currentGameID;

    @FXML
    private Tab tablist;

    @FXML
    private TableView<Game> listgames;

    @FXML
    private Tab tabupdate;

    @FXML
    private Button savebutton;

    @FXML
    private TextField titletext;

    @FXML
    private DatePicker datepicker;

    @FXML
    private ComboBox<String> statuscombo;

    @FXML
    private ComboBox<String> platformcombo;

    @FXML
    private ComboBox<String> ratecombo;

    @FXML
    private Label userlabel;
    
    @FXML
    private Label sqlmessagelabel;
    
    @FXML
    private CheckBox checkNew;
      
    @FXML
    void savegame(ActionEvent event) {
    	
    	if (!titletext.getText().isEmpty()) // Check how to use bindings later
    		manageDBOperations();
    	else
    	{
    		sqlmessagelabel.setVisible(true);
    		sqlmessagelabel.setText("At least Title is mandatory!");
    	}
    	
    }
    
	@Override
	public void initialize(URL url, ResourceBundle rb) {

		ObservableList<String> listStatuses = FXCollections.observableArrayList();
		ObservableList<String> listPlatforms = FXCollections.observableArrayList();
		ObservableList<String> listRates = FXCollections.observableArrayList();
		ResultSet results = null;

		// Load status
		Status status = new Status();
		results = status.ReadFromDB();
		
		try {
			while (results.next()) {
				listStatuses.add(results.getString("description"));				
			}
		} catch (Exception e) {
			
		}
		
		statuscombo.setItems(listStatuses);
		statuscombo.getSelectionModel().select(0); // Default value

		// Load platforms
		results = null;
		Platform platform = new Platform();
		results = platform.ReadFromDB();
		
		try {
			while (results.next()) {
				listPlatforms.add(results.getString("description"));				
			}
		} catch (Exception e) {
			
		}
		
		platformcombo.setItems(listPlatforms);
		platformcombo.getSelectionModel().select(0); // Default value
		
		// Load Rates
		results = null;
		Rate rate = new Rate();
		results = rate.ReadFromDB();
		
		try {
			while (results.next()) {
				listRates.add(results.getString("description"));				
			}
		} catch (Exception e) {
			
		}
		
		ratecombo.setItems(listRates);
		ratecombo.getSelectionModel().select(0); // Default value
		
		// Placeholder by default
		listgames.setPlaceholder(new Label("No games available"));
		
		// Hide label of SQL result as default
		sqlmessagelabel.setVisible(false);
		
		// New record enabled by default
		checkNew.setSelected(true);
		
		// Change date format to match SpringBoot and Database formats 
		datepicker.setConverter(new StringConverter<LocalDate>() {
		     DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		     @Override 
		     public String toString(LocalDate date) {
		         if (date != null) {
		             return dateFormatter.format(date);
		         } else {
		             return "";
		         }
		     }

		     @Override 
		     public LocalDate fromString(String string) {
		         if (string != null && !string.isEmpty()) {
		             return LocalDate.parse(string, dateFormatter);
		         } else {
		             return null;
		         }
		     }
		 });
		
		// Date of Completion is current day by default
		datepicker.setValue(LocalDate.now());		
					
		// Fill in list of games
		refreshListOfGames();
		
		// Refresh ID of current selected game in the table view
		// Code based in: https://stackoverflow.com/questions/44097537/javafx-tableview-get-selected-row-cell-values/47188421
		listgames.setOnMouseClicked((MouseEvent event) -> {
			
			 if (listgames.getSelectionModel().getSelectedItem() != null) {
				 Game g = listgames.getSelectionModel().getSelectedItem();
				 currentGameID = g.getID();
				 titletext.setText(g.getTitle());
				 MyLogger.WriteMessage("Current selected ID game is: " + currentGameID);
			 }
			 else {
				 MyLogger.WriteMessage("No game selected...");
			 }
			 
		});		
		
	}

	private void refreshListOfGames() {
		
		// Add columns to table view manually (easier this way)
		// Code based in: http://tutorials.jenkov.com/javafx/tableview.html
		TableColumn<Game, String> column1 = new TableColumn<>("ID");
		column1.setCellValueFactory(new PropertyValueFactory<>("ID"));

		TableColumn<Game, String> column2 = new TableColumn<>("Title");
		column2.setCellValueFactory(new PropertyValueFactory<>("Title"));
		
		TableColumn<Game, String> column3 = new TableColumn<>("Platform");
		column3.setCellValueFactory(new PropertyValueFactory<>("PlatformStr"));
		
		TableColumn<Game, String> column4 = new TableColumn<>("Rate");
		column4.setCellValueFactory(new PropertyValueFactory<>("RateStr"));
		
		TableColumn<Game, String> column5 = new TableColumn<>("Status");
		column5.setCellValueFactory(new PropertyValueFactory<>("StatusStr"));

		listgames.getColumns().add(column1);
		listgames.getColumns().add(column2);
		listgames.getColumns().add(column3);
		listgames.getColumns().add(column4);
		listgames.getColumns().add(column5);
		
		// Read games from database
		readfromDatabase();
	}

	private void readfromDatabase() {
		
		// Clear table view first
		listgames.getItems().clear();
		
		ResultSet fulllist = Game.ReadAllFromDB();
		
		try {
			while (fulllist.next()) {				
				listgames.getItems().add(new Game(Integer.valueOf(fulllist.getString("ID")),
						                          fulllist.getString("Title"),
						                          fulllist.getString("Platform"),
						                          fulllist.getString("Rate"),
						                          fulllist.getString("Status")));
			}
		} catch (Exception e) {
			
		}

	}

	@Override
	public boolean manageDBOperations() {
		
		boolean res;
		
		// Title, DateOfCompletion, Platform, Rate, Status
		Game newGame = new Game(titletext.getText(), datepicker.getValue(), platformcombo.getSelectionModel().getSelectedIndex(), 
				                ratecombo.getSelectionModel().getSelectedIndex(), statuscombo.getSelectionModel().getSelectedIndex());
		
		sqlmessagelabel.setVisible(true);	
		
		if (checkNew.isSelected())
		{
			res = newGame.WriteDB();
			
			if (res)
				sqlmessagelabel.setText("New Game saved successfully!");
		}
		else
		{
			res = newGame.UpdateDB(currentGameID);
			
			if (res)
				sqlmessagelabel.setText("Game updated successfully!");
		}
				
		
		if (!res)
			sqlmessagelabel.setText("Error when saving game. Try later!");
		else
			readfromDatabase();
		
		return res;
		
	}

	public void setUsername(String username) {
		
		userlabel.setText("User: " + username);
		
	}
	
}
