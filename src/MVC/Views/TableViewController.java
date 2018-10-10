package MVC.Views;

import MVC.Models.DBConnect;
import MVC.Models.Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class TableViewController implements Initializable {
    private static String user = "root";
    private static String password = "root";
    private ObservableList<Person> data = FXCollections.observableArrayList();

    @FXML
    private TableView<Person> contactTable;

    @FXML
    private TableColumn<Person, String> firstNameTableColumn;

    @FXML
    private TableColumn<Person, String> lastNameTableColumn;

    @FXML
    private TableColumn<Person, String> genderTableColumn;

    @FXML
    private TableColumn<Person, LocalDate> birthdayTableColumn;

    @FXML
    private TableColumn<Person, String> addressTableColumn;

    @FXML
    private TableColumn<Person, String> phoneNumberTableColumn;

    @FXML
    private TableColumn<Person, String> occupationTableColumn;

    @FXML
    private TableColumn<Person, File> imageTableColumn;

    @FXML
    private TextField searchTextField;

    @FXML
    private Button searchButton;

    @FXML
    private Button editContactButton;

    @FXML
    private Button createNewContactButton;

    public void viewContactsButtonPushed(ActionEvent event) throws Exception{
        Parent createNewContactParent= FXMLLoader.load(getClass().getResource("FormView.fxml"));
        Scene createNewContactScene = new Scene(createNewContactParent);
        createNewContactScene.getStylesheets().add("style/layout.css");

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(createNewContactScene);
        window.show();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try{
            // Connect to the database
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/Contacts?useSSL=false",
                    user, password);

            statement = conn.createStatement();

            resultSet = statement.executeQuery("SELECT * FROM contactList");

            while(resultSet.next()){
                this.data.add(new Person(resultSet.getString(2), resultSet.getString(3),resultSet.getString(4),
                        resultSet.getDate(5).toLocalDate(),resultSet.getString(6),resultSet.getString(7),
                        resultSet.getString(8), new File(resultSet.getString(9))));
            }

        }
        catch (SQLException e)
        {
            System.err.println(e);
        }

        firstNameTableColumn.setCellValueFactory(new PropertyValueFactory<Person,String>("firstName"));
        lastNameTableColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("lastName"));
        genderTableColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("gender"));
        birthdayTableColumn.setCellValueFactory(new PropertyValueFactory<Person, LocalDate>("birthday"));
        addressTableColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("address"));
        phoneNumberTableColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("phoneNumber"));
        occupationTableColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("occupation"));
        imageTableColumn.setCellValueFactory(new PropertyValueFactory<Person, File>("imageFile"));


        contactTable.setItems(data);
    }
}
