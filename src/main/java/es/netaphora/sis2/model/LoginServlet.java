package es.netaphora.sis2.model;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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
		
		final String verifyWorkspace = "SELECT pkid_internal, ws_purpose FROM tb_workspaces WHERE pkid_internal = ?";
        
		final String infoWorkspace = "SELECT ws_title, ws_subtitle FROM tb_config_ws_ui_data WHERE pkid_internal = ?";
        
		final String viewLanguageLiterals = "SELECT id_field_literal, field_literal FROM tb_literals WHERE id_app = 'a1' and id_view = 'a1'";
		
		final String menuData = "SELECT menu_id, fk_config_menus FROM tb_config_menus WHERE menu_id like 'a%' and f_available = true";
		
        DbConnection openConnection = new DbConnection();
        
        Connection connection = null;
        
        Integer cont = 0;
        
        try {
        	
        	connection = openConnection.openConnection();
            
            PreparedStatement preparedStatement = connection.prepareStatement(viewLanguageLiterals);
            ResultSet resultSet = preparedStatement.executeQuery();
            
            PrintWriter out = response.getWriter();
            
            getViewLanguageLiterals(resultSet, cont, request);
            
            preparedStatement = connection.prepareStatement(menuData);
            resultSet = preparedStatement.executeQuery();
            
            getMenuData(resultSet, cont, request);
			
            String workspace = request.getParameter("workspace");

			preparedStatement = connection.prepareStatement(verifyWorkspace);
			preparedStatement.setString(1, workspace);
			resultSet = preparedStatement.executeQuery();
			
			getWorkspaceVerification(connection, preparedStatement, infoWorkspace, workspace, resultSet, cont, request, response, out);
			
			openConnection.closeConnection(preparedStatement, connection);
			
		} catch (SQLException e) {
			LOGGER.error("Failed to execute SQL Statement");
			e.printStackTrace();
		}        
    }
	
	
	private void getWorkspaceVerification(Connection connection, PreparedStatement preparedStatement, String infoWorkspace , String workspace , ResultSet resultSet, Integer cont, HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
		
		try {
			if(resultSet.next()) {
				
				String ws_purpose = resultSet.getString(2);
				
				request.setAttribute("ws_purpose", ws_purpose);
				
				preparedStatement = connection.prepareStatement(infoWorkspace);
				preparedStatement.setString(1, workspace);
				resultSet = preparedStatement.executeQuery();
				
				if(resultSet.next()) {
					
					String ws_title = resultSet.getString(1);
					String ws_subtitle = resultSet.getString(2);
					
					request.setAttribute("ws_title", ws_title);
					request.setAttribute("ws_subtitle", ws_subtitle);
					
					RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/configurator.jsp");
					dispatcher.forward(request, response);
					
				}else {
					out.println("<h1>ERROR, LOG IN FALLIDO</h1>");
				}
			}else {
				out.println("<h1>ERROR, LOG IN FALLIDO</h1>");
			}
			
		} catch (SQLException ex) {
			LOGGER.error("Failed to execute SQL Statement");
			ex.printStackTrace();
		} catch (ServletException ex) {
			LOGGER.error("Failed to redirect to URL");
			ex.printStackTrace();
		} catch (IOException ex) {
			LOGGER.error("Dispatcher IO ERROR");
			ex.printStackTrace();
		}
		
	}


	private void getMenuData(ResultSet resultSet, Integer cont, HttpServletRequest request) {
		
		Map<Integer, ArrayList<String>> treeMap_menu_data = new TreeMap<>();
		
		try {
			while(resultSet.next()) {
				
				List<String> arrayList_menu_data = new ArrayList<String>();
				
				arrayList_menu_data.add(resultSet.getString(1));
				arrayList_menu_data.add(resultSet.getString(2));
				
				treeMap_menu_data.put(cont, (ArrayList<String>) arrayList_menu_data);
				cont++;
				
				if(treeMap_menu_data.size() != 0) {
					
					request.setAttribute("menu_data", treeMap_menu_data);
					
				}else {
					LOGGER.error("Failed to load menu data");
				}

			}
		} catch (SQLException ex) {
			LOGGER.error("Failed to execute SQL Statement");
			ex.printStackTrace();
		}
	}


	private void getViewLanguageLiterals(ResultSet resultSet, Integer cont, HttpServletRequest request) {
		
		Map<Integer, ArrayList<String>> treeMap_view_language_literals = new TreeMap<>();
        
		try {
			while(resultSet.next()) {
				
				List<String> arrayList_view_language_literals = new ArrayList<String>();
				
				arrayList_view_language_literals.add(resultSet.getString(1));
				arrayList_view_language_literals.add(resultSet.getString(2));
				
				treeMap_view_language_literals.put(cont, (ArrayList<String>) arrayList_view_language_literals);
				cont++;
				
				if(treeMap_view_language_literals.size() != 0) {
					
					request.setAttribute("view_language_literals", treeMap_view_language_literals);
					
				}else {
					LOGGER.error("Failed to load view literals");
				}

			}
		} catch (SQLException ex) {
			LOGGER.error("Failed to execute SQL Statement");
			ex.printStackTrace();
		}
	}
}





