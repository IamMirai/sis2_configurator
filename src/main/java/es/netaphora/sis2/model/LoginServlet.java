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

import es.netaphora.sis2.DbConnection.DbConnection;

/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		final String verifyWorkspace = "SELECT pkid_internal FROM tb_workspaces WHERE tb_workspaces=?";
        
        DbConnection openConnection = new DbConnection();
        
        Connection connection = null;
        
        try {
        	PrintWriter out = response.getWriter();
        	String user = request.getParameter("user");
            String workspace = request.getParameter("workspace");
            
            request.setAttribute("user", user);
        	
            connection = openConnection.openConnection();
            
        	
			PreparedStatement preparedStatement = connection.prepareStatement(verifyWorkspace);
			preparedStatement.setString(1, workspace);
			ResultSet resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {	
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("/jsp/configurator.jsp");
		        requestDispatcher.forward(request, response);
			}else {
				out.println("<h1>ERROR</h1>");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}        
    }

}





