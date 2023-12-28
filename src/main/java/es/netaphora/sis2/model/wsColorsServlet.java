package es.netaphora.sis2.model;

import es.netaphora.sis2.DbConnection.DbConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class wsColorsServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = LogManager.getLogger(LoginServlet.class);

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        final String wsActualColors = "SELECT ws_principal_color, ws_secundary_color, ws_third_color FROM tb_config_ws_ui_data WHERE fk_workspace = ?";

        HttpSession session = request.getSession();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        DbConnection dbConnection = new DbConnection();

        try {
            connection = dbConnection.openConnection();

            preparedStatement = connection.prepareStatement(wsActualColors);

            preparedStatement.setString(1, (String)session.getAttribute("workspace"));

            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){

                String colors = resultSet.getString(1);

                colors += resultSet.getString(2);

                colors += resultSet.getString(3);

                session.setAttribute("wsActualColors", colors);
            }

            dbConnection.closeConnection(preparedStatement, connection);

        }catch (SQLException ex){
            LOGGER.error("Failed to execute SQL Statement");
            ex.printStackTrace();
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/wsColors.jsp");
        dispatcher.forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String colors = request.getParameter("colors");

        if(checkStringPattern(colors)){
            final String wsColorsToAdd = "UPDATE tb_config_ws_ui_data SET ws_principal_color = ?, ws_secundary_color = ?, ws_third_color = ? WHERE fk_workspace = ?";

            HttpSession session = request.getSession();

            Connection connection = null;
            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null;

            DbConnection dbConnection = new DbConnection();

            try {
                connection = dbConnection.openConnection();

                preparedStatement = connection.prepareStatement(wsColorsToAdd);

                preparedStatement.setString(1, colors.substring(0, 7));
                preparedStatement.setString(2, colors.substring(7,14));
                preparedStatement.setString(3, colors.substring(14));
                preparedStatement.setString(4, (String)session.getAttribute("workspace"));

                preparedStatement.executeUpdate();

                dbConnection.closeConnection(preparedStatement, connection);

            }catch (SQLException ex){
                LOGGER.error("Failed to execute SQL Statement");
                ex.printStackTrace();
            }
        }
    }

    private boolean checkStringPattern(String value){

        return value.length() == 21 && value.matches("^[a-zA-Z0-9#]+$");
    }
}
