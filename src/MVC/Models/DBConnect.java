package MVC.Models;

import java.sql.*;
import java.util.ArrayList;

public class DBConnect {
    private static String user = "root";
    private static String password = "root";

    public static ArrayList<String> getGender() throws SQLException {
        ArrayList<String> contacts = new ArrayList<>();
        ArrayList<String> name = new ArrayList<>();
        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try{
            //1. Connect to the database
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/phones?useSSL=false",
                    user, password);

            //2. create a Statement object
            statement = conn.createStatement();

            //3.  create the sql query
            resultSet = statement.executeQuery("SELECT gender, address FROM contacts.ContactList");

            //4. loop over the results and add it to the ArrayList
            while (resultSet.next())
            {
                contacts.add(resultSet.getString("gender"));
                contacts.add(resultSet.getString("address"));

            }
        }
        catch (SQLException e)
        {
            System.err.println(e);
        }
        finally {
            if (conn != null)
                conn.close();
            if (statement != null)
                statement.close();
            if (resultSet != null)
                resultSet.close();
        }
        return contacts;
    }
}
