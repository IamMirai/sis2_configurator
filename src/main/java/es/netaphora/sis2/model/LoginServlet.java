package es.netaphora.sis2.model;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import es.netaphora.sis2.DbConnection.DbConnection;

/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final Logger LOGGER = LogManager.getLogger(LoginServlet.class);
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		final String verifyWorkspace = "SELECT pkid_internal FROM tb_workspaces WHERE pkid_internal = ?";
        
        DbConnection openConnection = new DbConnection();
        
        Connection connection = null;
        
        try {
            connection = openConnection.openConnection();

            String workspace = request.getParameter("workspace");

			PreparedStatement preparedStatement = connection.prepareStatement(verifyWorkspace);
			preparedStatement.setString(1, workspace);
			ResultSet resultSet = preparedStatement.executeQuery();
			
			PrintWriter out = response.getWriter();
			
			if(resultSet.next()) {
				String user = request.getParameter("user");
				
				response.sendRedirect(request.getContextPath() + "/jsp/configurator.jsp?user=" + user);
			}else {
				out.println("<h1>ERROR, LOG IN FALLIDO</h1>");
			}
		} catch (SQLException e) {
			LOGGER.error("Failed to execute SQL Statement");
			e.printStackTrace();
		}        
    }

}





