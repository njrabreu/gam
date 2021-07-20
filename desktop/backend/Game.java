package apps.gam.desktop.backend;

import java.sql.ResultSet;
import java.time.LocalDate;

import apps.gam.desktop.database.DBConnection;

public class Game extends Entity {
	
	private int 		ID;
	private String 		Title;
	private LocalDate 	Completion;
	private int 		Platform;
	private String		PlatformStr;
	private int 		Rate;
	private String		RateStr;
	private int 		Status;
	private String		StatusStr;
	
	// Normal constructor
	public Game(String Title, LocalDate DateOfCompletion, int Platform, int Rate, int Status) {
		
		this.Title = Title;
		this.Completion = DateOfCompletion;
		
		// Combo box entries are 0 based
		this.Platform = Platform + 1;
		this.Rate = Rate + 1;
		this.Status = Status + 1;
		
	}	

	// Special constructor for the table view in the main window
	public Game(int ID, String Title, String Platform, String Rate, String Status) {
		
		this.ID = ID;
		this.Title = Title;		
		this.PlatformStr = Platform;
		this.RateStr = Rate;
		this.StatusStr = Status;		
	}
		
	@Override
	public ResultSet ReadFromDB() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean WriteDB() {
		
		String cmd = "insert into game (Title, Completion, Platform, Rate, Status) values ('" + 
				     this.Title + "','" +
				     this.Completion.toString() + "'," +
				     this.Platform + "," +
				     this.Rate + "," +
				     this.Status + ")";
		
		return DBConnection.insertDatabase(cmd);
		
	}
	
	public static ResultSet ReadAllFromDB() {
		
		return DBConnection.readDatabase("select * from all_games order by ID");
		
	}

	public boolean UpdateDB(int ID) {

		String cmd = "update game set Title='" + this.Title + "'," +
				     "Completion='" + this.Completion.toString() + "'," +
			         "Platform=" + this.Platform + "," +
			         "Rate=" + this.Rate + "," +
			         "Status=" + this.Status + " " +
			         "Where ID=" + ID;
	
		return DBConnection.updateDatabase(cmd);
		
	}

	public int getID() {
		
		return this.ID;
		
	}

	public void setID(int iD) {
		
		this.ID = iD;
		
	}
	
	public String getTitle() {
		
		return Title;
		
	}

	public void setTitle(String t) {
		
		this.Title = t;
		
	}
	
	public String getPlatformStr() {
		
		return this.PlatformStr;
		
	}

	public void setPlatformStr(String p) {
		
		this.PlatformStr = p;
		
	}
	
	public String getRateStr() {
		
		return this.RateStr;
		
	}

	public void setRate(String r) {
		
		this.RateStr = r;
		
	}
	
	public String getStatusStr() {
		
		return this.StatusStr;
		
	}

	public void setStatusStr(String s) {
		
		this.StatusStr = s;
		
	}

}
