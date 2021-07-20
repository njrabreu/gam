package apps.gam.desktop.database;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.properties.EncryptableProperties;

import apps.gam.desktop.core.MyLogger;
import javafx.application.Platform;

public class DBConnection {

	private static final int TIMEOUT = 2;
	private static Connection mainConnection = null;

	public static void openConnection() {

		String propFile = "desktop.properties";

		// Create the adequate encryptor for decrypting the values in our .properties
		// file
		StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
		encryptor.setPassword("gam");

		// Create our EncryptableProperties object and load it the usual way
		Properties properties = new EncryptableProperties(encryptor);
		ClassLoader loader = Thread.currentThread().getContextClassLoader();

		try (InputStream resourceStream = loader.getResourceAsStream(propFile)) {
			properties.load(resourceStream);
		} catch (Exception exception) {
			MyLogger.WriteMessage("Error reading " + propFile + ". Ending program...!");
			Platform.exit();
		}

		try {
			mainConnection = DriverManager.getConnection(properties.getProperty("db.url"),
					properties.getProperty("db.username"), properties.getProperty("db.password"));

			boolean result = mainConnection.isValid(TIMEOUT);

			if (result) {
				MyLogger.WriteMessage("Connection to Database is OK!");
			} else {
				MyLogger.WriteMessage("Connection to Database is KO!");
				mainConnection.close();
			}
		} catch (Exception exception) {
			MyLogger.WriteMessage("Connection to Database is KO. Ending program...!");
			Platform.exit();
		}

	}

	public static void closeConnection() {

		try {
			MyLogger.WriteMessage("Closing connection to Database...");
			mainConnection.close();
		} catch (Exception exception) {
			MyLogger.WriteMessage("Error when closing connection to Database. Ending program...!");
			Platform.exit();
		}

	}

	public static ResultSet readDatabase(String cmd) {

		PreparedStatement preparedStatement;
		ResultSet resultSet = null;

		try {
			preparedStatement = mainConnection.prepareStatement(cmd);
			resultSet = preparedStatement.executeQuery();
		} catch (Exception exception) {
			MyLogger.WriteMessage("Error when reading from database. Ending program...!");
			Platform.exit();
		}

		return resultSet;

	}

	public static boolean insertDatabase(String cmd) {

		PreparedStatement preparedStatement;
		int count = 0;

		try {
			preparedStatement = mainConnection.prepareStatement(cmd);
			count = preparedStatement.executeUpdate(cmd);
		} catch (Exception exception) {
			MyLogger.WriteMessage("Error when writing to database. Ending program...!");
			Platform.exit();
		}

		return count > 0 ? true : false;

	}

	public static boolean updateDatabase(String cmd) {

		insertDatabase(cmd);

		return true;
	}

}
