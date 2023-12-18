package es.netaphora.sis2.model;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
	protected void doPost(HttpServletRequest request, HttpServletResponse response){
		
		final String verifyWorkspace = "SELECT pkid_internal, ws_purpose FROM tb_workspaces WHERE pkid_internal = ?";
        
		final String infoWorkspace = "SELECT ws_title, ws_subtitle FROM tb_config_ws_ui_data WHERE pkid_internal = ?";
        
		final String viewLanguageLiterals = "SELECT l.id_field_literal, l.field_literal, cfgm.menu_id, cfgm.f_available, cfgm.fk_config_menus, cfgm.fk_literals, cfgm.menu_url FROM tb_literals l LEFT JOIN tb_config_menus cfgm ON l.id_field_literal = cfgm.fk_literals WHERE l.fk_language = ? and (cfgm.f_available = true or l.id_view = 'a1') ORDER BY LENGTH(id_field_literal), id_field_literal";
        
		final String languages = "SELECT pk_language FROM TB_LANGUAGES WHERE f_available";
		
		HttpSession session = request.getSession();
		
		Connection connection = null;
		
		DbConnection openConnection = new DbConnection();
		
		PreparedStatement preparedStatement = null;
		
		ResultSet resultSet = null;
        
        PrintWriter out = null;
        
        Integer cont = 0;
        
        Boolean activeWorkspace = true;
        
        String language = null;
        
        try {
        	out = response.getWriter();

			connection = openConnection.openConnection();
			
			if(session.isNew()) {
				
	            String workspace = request.getParameter("workspace");

	            preparedStatement = connection.prepareStatement(verifyWorkspace);
				preparedStatement.setString(1, workspace);
				resultSet = preparedStatement.executeQuery();
				
				activeWorkspace = getWorkspaceVerification(connection, preparedStatement, resultSet, workspace, infoWorkspace, cont, out, session);
			}

			if(activeWorkspace) {
				preparedStatement = connection.prepareStatement(languages);
				resultSet = preparedStatement.executeQuery();
				
				preparedStatement = connection.prepareStatement(viewLanguageLiterals);

				language = "eng";

				session.setAttribute("language", language);

				preparedStatement.setString(1, language);
				
				resultSet = preparedStatement.executeQuery();
	          
	          	getViewLanguageLiterals(resultSet, cont, session);
			}
          	
			openConnection.closeConnection(preparedStatement, connection);
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/configurator.jsp");
			dispatcher.forward(request, response);
			
		} catch (SQLException e) {
			LOGGER.error("Failed to execute SQL Statement");
			e.printStackTrace();
		} catch (ServletException ex) {
			LOGGER.error("Failed to redirect to URL");
			ex.printStackTrace();
		} catch (IOException ex) {
			LOGGER.error("Dispatcher IO ERROR");
			ex.printStackTrace();
		}     
    }


	private Boolean getWorkspaceVerification(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet, String workspace, String infoWorkspace, Integer cont, PrintWriter out, HttpSession session) {
		try {
			if(resultSet.next()) {
				
				String ws_purpose = resultSet.getString(2);
				
				session.setAttribute("ws_purpose", ws_purpose);
				
				preparedStatement = connection.prepareStatement(infoWorkspace);
				preparedStatement.setString(1, workspace);
				resultSet = preparedStatement.executeQuery();
				
				if(resultSet.next()) {
					
					String ws_title = resultSet.getString(1);
					String ws_subtitle = resultSet.getString(2);

					session.setAttribute("workspace", workspace);
					session.setAttribute("ws_title", ws_title);
					session.setAttribute("ws_subtitle", ws_subtitle);
				}else {
					out.println("<h1>ERROR, LOG IN FALLIDO</h1>");
					return false;
				}
			}else {
				return false;
			}
			
		} catch (SQLException ex) {
			LOGGER.error("Failed to execute SQL Statement");
			ex.printStackTrace();
		}
		return true;
	}

	private void getViewLanguageLiterals(ResultSet resultSet, Integer cont, HttpSession session) {
		
		Map<Integer, ArrayList<String>> treeMap_view_language_literals = new TreeMap<>();
        
		try {
			while(resultSet.next()) {
				
				List<String> arrayList_view_language_literals = new ArrayList<String>();
				
				arrayList_view_language_literals.add(resultSet.getString(1).trim());
				arrayList_view_language_literals.add(resultSet.getString(2));
				arrayList_view_language_literals.add(resultSet.getString(3));
				arrayList_view_language_literals.add(String.valueOf(resultSet.getBoolean(4)));
				arrayList_view_language_literals.add(resultSet.getString(5));
				arrayList_view_language_literals.add(resultSet.getString(6));
				arrayList_view_language_literals.add(resultSet.getString(7));
				
				treeMap_view_language_literals.put(cont, (ArrayList<String>) arrayList_view_language_literals);
				cont++;
			}
			
			if(treeMap_view_language_literals.size() != 0) {
				
				session.setAttribute("view_language_literals", treeMap_view_language_literals);
				
			}else {
				LOGGER.error("Failed to load view literals");
			}
		} catch (SQLException ex) {
			LOGGER.error("Failed to execute SQL Statement");
			ex.printStackTrace();
		}
	}
}





