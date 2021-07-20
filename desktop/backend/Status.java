package apps.gam.desktop.backend;

import java.sql.ResultSet;

import apps.gam.desktop.database.DBConnection;

public class Status extends Entity {

	@Override
	public ResultSet ReadFromDB() {
		
		return DBConnection.readDatabase("select description from status order by ID");
		
	}

	@Override
	public boolean WriteDB() {

		return true;
		
	}

}
