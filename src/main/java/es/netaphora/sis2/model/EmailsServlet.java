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

public class EmailsServlet  extends HttpServlet {


    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = LogManager.getLogger(LoginServlet.class);

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        final String wsActualEmail = "SELECT default_email FROM tb_config_ws_emails WHERE fk_workspace = ?";

        HttpSession session = request.getSession();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        DbConnection dbConnection = new DbConnection();

        try {
            connection = dbConnection.openConnection();

            preparedStatement = connection.prepareStatement(wsActualEmail);

            preparedStatement.setString(1, (String)session.getAttribute("workspace"));

            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){

                String email = resultSet.getString(1);

                session.setAttribute("wsActualDefaultEmail", email);
            }

            dbConnection.closeConnection(preparedStatement, connection);

        }catch (SQLException ex){
            LOGGER.error("Failed to execute SQL Statement");
            ex.printStackTrace();
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/emails.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String email = request.getParameter("email");

        if(checkStringPattern(email)){
            final String wsAddDefaultEmail = "UPDATE tb_config_ws_emails SET default_email = ? WHERE fk_workspace = ?";

            HttpSession session = request.getSession();

            Connection connection = null;
            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null;

            DbConnection dbConnection = new DbConnection();

            try {
                connection = dbConnection.openConnection();

                preparedStatement = connection.prepareStatement(wsAddDefaultEmail);

                preparedStatement.setString(1, email);
                preparedStatement.setString(2, (String)session.getAttribute("workspace"));

                preparedStatement.executeUpdate();

                dbConnection.closeConnection(preparedStatement, connection);

            }catch (SQLException ex){
                LOGGER.error("Failed to execute SQL Statement");
                ex.printStackTrace();
            }
        }
    }

    private boolean checkStringPattern(String value){

        return value.length() < 51 && value.matches("^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$");
    }
}
