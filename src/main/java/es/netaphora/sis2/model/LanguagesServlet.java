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
import java.sql.*;
import java.util.ArrayList;
import java.util.Locale;
import java.util.TreeMap;

/**
 * Servlet implementation class LoginServlet
 */
public class LanguagesServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = LogManager.getLogger(LanguagesServlet.class);

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response){

        final String workspaceLanguages = "SELECT fk_language, default_language FROM tb_config_ws_languages WHERE fk_workspace = ? ORDER BY pkid_internal ASC";

        final String languagesNotInWorkspace = "SELECT ls.pk_language FROM tb_languages ls LEFT JOIN TB_CONFIG_WS_LANGUAGES cwls ON ls.pk_language = cwls.fk_language and cwls.fk_workspace = ? WHERE cwls.fk_language IS NULL ORDER BY ls.pk_language ASC";

        HttpSession session = request.getSession();

        Connection connection = null;

        DbConnection dbConnection = new DbConnection();

        PreparedStatement preparedStatement = null;

        ResultSet resultSet = null;

        Integer cont = 0;


        try {
            connection = dbConnection.openConnection();

            preparedStatement = connection.prepareStatement(workspaceLanguages);

            preparedStatement.setString(1, session.getAttribute("workspace").toString());

            resultSet = preparedStatement.executeQuery();

            getWorkspaceLanguages(resultSet, session, cont);

            preparedStatement = connection.prepareStatement(languagesNotInWorkspace);

            preparedStatement.setString(1, session.getAttribute("workspace").toString());

            resultSet = preparedStatement.executeQuery();

            getLanguagesNotInWorkspace(resultSet, session, cont);



            dbConnection.closeConnection(preparedStatement, connection);

            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/jsp/languages.jsp");
            requestDispatcher.forward(request, response);


        }catch (SQLException ex){
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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        final String insertLanguagesIntoWorkspace = "INSERT INTO tb_config_ws_languages (pkid_internal, id_public, fk_workspace, fk_language) values('a1.4','a1.4', ?, ?)";

        ArrayList<String> arrayList_languagesToAdd = new ArrayList<>();

        String languageToAdd = request.getParameter("languageToAdd");

        arrayList_languagesToAdd.add(getIsoCodeByLanguageName(languageToAdd));

        HttpSession session = request.getSession();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        DbConnection dbConnection = new DbConnection();

        try {
            connection = dbConnection.openConnection();

            preparedStatement = connection.prepareStatement(insertLanguagesIntoWorkspace);

            for(int i = 0; i< arrayList_languagesToAdd.size(); i++){

                preparedStatement.setString(1, (String)session.getAttribute("workspace"));
                preparedStatement.setString(2, arrayList_languagesToAdd.get(i));

                int rowInserted = preparedStatement.executeUpdate();
            }

            dbConnection.closeConnection(preparedStatement, connection);

            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/languages.jsp");
            dispatcher.forward(request, response);

        }catch (SQLException ex){
            LOGGER.error("Failed to execute SQL Statement");
            ex.printStackTrace();
        }

    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String removeLanguagesInWorkspace = "DELETE FROM tb_config_ws_languages WHERE fk_workspace= ? and fk_language = ?";

        ArrayList<String> arrayList_languagesToRemove = new ArrayList<>();

        String languageToRemove = request.getParameter("languageToRemove");

        arrayList_languagesToRemove.add(getIsoCodeByLanguageName(languageToRemove));

        System.out.println(arrayList_languagesToRemove);

        HttpSession session = request.getSession();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        DbConnection dbConnection = new DbConnection();

        try {
            connection = dbConnection.openConnection();

            preparedStatement = connection.prepareStatement(removeLanguagesInWorkspace);

            for(int i = 0; i< arrayList_languagesToRemove.size(); i++){
                preparedStatement.setString(1, session.getAttribute("workspace").toString());
                preparedStatement.setString(2, arrayList_languagesToRemove.get(i));

                int rowDeleted = preparedStatement.executeUpdate();
            }

            dbConnection.closeConnection(preparedStatement, connection);

            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/languages.jsp");
            dispatcher.forward(request, response);



        }catch (SQLException ex){
            LOGGER.error("Failed to execute SQL Statement");
            ex.printStackTrace();
        }
    }

    private void getLanguagesNotInWorkspace(ResultSet resultSet, HttpSession session, Integer cont) {

        TreeMap<Integer, ArrayList<String>> treeMap_languagesNotInWorkspace = new TreeMap<>();

        try {

            while(resultSet.next()){

                ArrayList<String> arrayList;

                arrayList = getLanguageNameByIsoCode(resultSet.getString(1));

                treeMap_languagesNotInWorkspace.put(cont, arrayList);
                cont++;
            }

            session.setAttribute("languagesNotInWorkspace", treeMap_languagesNotInWorkspace);

        }catch (SQLException ex){
            LOGGER.error("Failed to execute SQL Statement");
            ex.printStackTrace();
        }
    }

    private void getWorkspaceLanguages(ResultSet resultSet, HttpSession session, Integer cont) {

        TreeMap<Integer, ArrayList<String>> treeMap_languages = new TreeMap<>();

        try {
            while (resultSet.next()){

                ArrayList<String> arrayList_language;

                arrayList_language = getLanguageNameByIsoCode(resultSet.getString(1));

                arrayList_language.add(resultSet.getString(2));

                treeMap_languages.put(cont, arrayList_language);
                cont++;
            }

            session.setAttribute("languages", treeMap_languages);

        }catch (SQLException ex){
            LOGGER.error("Failed to execute SQL Statement");
            ex.printStackTrace();
        }

    }

    private ArrayList<String> getLanguageNameByIsoCode(String languageCode) {

        ArrayList<String> arrayList_language = new ArrayList<>();

        Locale[] availableLocales = Locale.getAvailableLocales();

        for (Locale locale : availableLocales) {
            if (languageCode.equals(locale.getISO3Language())) {
                arrayList_language.add(languageCode);
                arrayList_language.add(locale.getDisplayLanguage(Locale.ENGLISH));
                return arrayList_language;
            }
        }
        return arrayList_language;
    }

    public static String getIsoCodeByLanguageName(String language) {
        Locale locale = new Locale(language.substring(0,2).toLowerCase());

        return locale.getISO3Language();
    }
}
