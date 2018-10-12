package Models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.sql.*;
import java.time.LocalDate;

public class DBConnect {
    private static String user = "b07210b32e0bec";
    private static String password = "75c900e6";

    public void addContactToDatabase(String firstName, String lastName, String gender, LocalDate birthday, String address, String phoneNumber, String occupation, String imageFile) throws SQLException{
        // Instantiate the SQL variables

        Connection conn = null;
        PreparedStatement statement = null;

        try{
            // Connect to the database
            conn = DriverManager.getConnection("jdbc:mysql://us-cdbr-iron-east-01.cleardb.net:3306/heroku_ec40e15f6df80a2",
                    user, password);


           // conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Contacts?useSSL=false",
             //       user, password);


            // create a Prepared Statement object
            statement = conn.prepareStatement("INSERT INTO contactlist (FirstName, LastName, Gender, Birthday, Address, PhoneNumber, Occupation, Image) " +
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

    public ObservableList<Person> getContactsFromDatabase (){
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        ObservableList<Person> data = FXCollections.observableArrayList();

        try{
            // Connect to the database
            conn = DriverManager.getConnection("jdbc:mysql://us-cdbr-iron-east-01.cleardb.net:3306/heroku_ec40e15f6df80a2",
                    user, password);

            statement = conn.prepareStatement("SELECT * FROM contactlist");

            resultSet = statement.executeQuery();

            while(resultSet.next()){
                data.add(new Person(resultSet.getString(2), resultSet.getString(3),resultSet.getString(4),
                        resultSet.getDate(5).toLocalDate(),resultSet.getString(6),resultSet.getString(7),
                        resultSet.getString(8), new File(resultSet.getString(9))));
            }
        }
        catch (SQLException e)
        {
            System.err.println(e);
        }

        return data;
    }


}
