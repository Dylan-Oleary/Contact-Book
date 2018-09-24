package MVC.Models;

import java.sql.SQLException;

public class DBTester {

    public static void main(String[] args)
    {
        try {
            System.out.println(DBConnect.getGender());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
