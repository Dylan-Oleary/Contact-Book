package MVC.Views;

import MVC.Models.Person;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class TableViewController implements Initializable {

    @FXML
    private TableView<?> contactTable;

    @FXML
    private TableColumn<Person, String> firstNameTableColumn;

    @FXML
    private TableColumn<Person, String> lastNameTableColumn;

    @FXML
    private TableColumn<Person, String> genderTableColumn;

    @FXML
    private TableColumn<?, ?> birthdayTableColumn;

    @FXML
    private TableColumn<Person, String> addressTableColumn;

    @FXML
    private TableColumn<Person, String> phoneNumberTableColumn;

    @FXML
    private TableColumn<Person, String> occupationTableColumn;

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

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(createNewContactScene);
        window.show();
    }


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
}
