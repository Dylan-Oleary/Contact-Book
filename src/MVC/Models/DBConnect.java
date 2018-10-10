package MVC.Models;

import java.io.File;
import java.sql.*;
import java.time.LocalDate;

public class DBConnect {
    private static String user = "root";
    private static String password = "root";

    public void addContactToDatabase(String firstName, String lastName, String gender, LocalDate birthday, String address, String phoneNumber, String occupation, String imageFile) throws SQLException{
        // Instantiate the SQL variables

        Connection conn = null;
        PreparedStatement statement = null;

        try{
            // Connect to the database
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Contacts?useSSL=false",
                    user, password);

            // create a Prepared Statement object
            statement = conn.prepareStatement("INSERT INTO contactList (FirstName, LastName, Gender, Birthday, Address, PhoneNumber, Occupation, Image) " +
                    "VALUE (?,?,?,?,?,?,?,?)");

            //Set SQL inout values to reflect the Person object values

            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, gender);
            statement.setString(4, birthday.toString());
            statement.setString(5, address);
            statement.setString(6, phoneNumber);
            statement.setString(7, occupation);
            statement.setString(8, imageFile);

            // Execute the Statement
            statement.execute();
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
        }
    }

}
