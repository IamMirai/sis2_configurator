package es.netaphora.sis2.DbConnection;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DbConnection {
	
	//VARIABLE DECLARATION
	private Connection connectionToOpen = null;

	Properties properties = new Properties();
    InputStream propertiesInputStream = null;
    
    String path = null;
    
    private String url = null;
    private String user = null;
    private String password = null;
	
	private static final Logger LOGGER = LogManager.getLogger(DbConnection.class);

	//CONSTRUCTORS
	public DbConnection() {
		// TODO Auto-generated constructor stub
	}

	//CODE
	public Connection openConnection() {
        try {
        	Class.forName("org.postgresql.Driver");
        	setupDBCredentials();
        	
        	LOGGER.info("Trying DB connection to " + url + " with user: " + user);
        	connectionToOpen = DriverManager.getConnection(url,user,password); 
        	LOGGER.info("Connection established to " + url + " with user: " + user);
        } catch (SQLException ex) {
        	LOGGER.error("Connection failed to " + url);
        	ex.printStackTrace();
        } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	
	private void setupDBCredentials() {
		try {
			propertiesInputStream = getClass().getResourceAsStream("/DbConfig.properties");
			properties.load(propertiesInputStream);	

			url = properties.getProperty("url");
			user = properties.getProperty("user");
			password = properties.getProperty("password");
		} catch (NullPointerException ex) {
			LOGGER.error("propertiesInputStream is null");
			ex.printStackTrace();
		} catch (IOException ex) {
			LOGGER.error("setupDBCredentials failed");
			ex.printStackTrace();
		} finally {
			if (propertiesInputStream != null) {
				try {
					propertiesInputStream.close();
				} catch (IOException ex) {
					LOGGER.error("Failed to close input stream");
					ex.printStackTrace();
				}
			}
		}
	}
}
