package viewContacts;

import Models.DBConnect;
import Models.Person;
import Models.SceneChanger;
import createContact.FormViewController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
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
    private TableColumn<Person, Integer> personIDColumn;

    @FXML
    private TextField searchTextField;

    @FXML
    private Button searchButton;

    @FXML
    private Button editContactButton;

    @FXML
    private Button createNewContactButton;

    public void createNewContactButtonPushed(ActionEvent event) throws Exception{
        SceneChanger.changeScenes(event, "../createContact/FormView.fxml", "Contacts" );
    }

    public void contactSelected(){
        editContactButton.setDisable(false);
    }

    public void editContactButtonPushed(ActionEvent event) throws IOException {
        SceneChanger sceneChanger = new SceneChanger();
        Person person = this.contactTable.getSelectionModel().getSelectedItem();
        FormViewController formViewController = new FormViewController();

        sceneChanger.changeScenes(event, "../createContact/FormView.fxml", "Edit Contact", person, formViewController);
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        DBConnect db = new DBConnect();

        data = db.getContactsFromDatabase();

        firstNameTableColumn.setCellValueFactory(new PropertyValueFactory<Person,String>("firstName"));
        lastNameTableColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("lastName"));
        genderTableColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("gender"));
        birthdayTableColumn.setCellValueFactory(new PropertyValueFactory<Person, LocalDate>("birthday"));
        addressTableColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("address"));
        phoneNumberTableColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("phoneNumber"));
        occupationTableColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("occupation"));
        imageTableColumn.setCellValueFactory(new PropertyValueFactory<Person, File>("imageFile"));

        contactTable.setItems(data);

        editContactButton.setDisable(true);
    }
}