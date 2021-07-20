package apps.gam.desktop.backend;

import java.sql.ResultSet;
import java.sql.SQLException;

import apps.gam.desktop.database.DBConnection;

public class User extends Entity {

	private String username;
	private int password;

	public User(String username, String password) throws SQLException {

		this.username = username;
		this.password = password.hashCode();	

	}

	@Override
	public ResultSet ReadFromDB() {

		return DBConnection.readDatabase("select 1 from user where ID='" + username + "' and Password = " + password);

	}

	@Override
	public boolean WriteDB() {
		
		String cmd = "insert into user (ID, Password) values ('" + this.username + "'," + this.password + ")";

		return DBConnection.insertDatabase(cmd);

	}

	public boolean IsValid() {

		try {
			return this.ReadFromDB().next();
		} catch (SQLException e) {

			e.printStackTrace();
		}
		
		return false;
		
	}
	
	public boolean UserAlreadyExists() {

		try {
			return DBConnection.readDatabase("select 1 from user where ID='" + username + "'").next();
		} catch (SQLException e) {

			e.printStackTrace();
		}
		
		return false;
		
	}

	public String getUsername() {

		return username;

	}

}
