package es.netaphora.sis2.DbConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DbConnection {
	
	//VARIABLE DECLARATION	
	private Connection connectionToOpen = null;
	
	ResourceBundle resourceBundle = ResourceBundle.getBundle("DbConfig.properties");
	
	private final String url = resourceBundle.getString("url");
	private final String user = resourceBundle.getString("user");
	private final String password = resourceBundle.getString("password");
	
	private static final Logger LOGGER = LogManager.getLogger(DbConnection.class);

	//CONSTRUCTOR
	public DbConnection() {
		// TODO Auto-generated constructor stub
	}
	
	
	//CODE
	public Connection openConnection() {
        try {
        	
        	LOGGER.info("Trying DB connection to " + url + " with user: " + user);
        	connectionToOpen = DriverManager.getConnection(url,user,password); 
            
        } catch (SQLException ex) {
        	LOGGER.error("Connection failed to " + url);
        }
        return connectionToOpen;
    }
	
	public void closeConnection(PreparedStatement pstmt, Connection connectionToClose) {
        try {
        	
        	if(pstmt != null) {
        		pstmt.close();
        	}
        	
        	if(connectionToClose != null) {
        		LOGGER.info("Tying to close DB connection to " + url);
        		connectionToClose.close();
        	}
        	
        } catch (SQLException ex) {
        	LOGGER.error("Error closing connection to " + url);
        }
    }
}
