package apps.gam.desktop.backend;

import java.sql.ResultSet;

public abstract class Entity {
	
	public abstract ResultSet ReadFromDB();
	
	public abstract boolean WriteDB();
	
}
