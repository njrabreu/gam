package apps.gam.desktop.frontend;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;

public abstract class Controller implements Initializable {
	
	@Override
    public abstract void initialize(URL url, ResourceBundle rb);
		
	public abstract boolean manageDBOperations(); 
	
}
