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

    /**
     * This method returns us to the create contact page by using the SceneChanger utility
     */
    public void createNewContactButtonPushed(ActionEvent event) throws Exception{
        SceneChanger.changeScenes(event, "../createContact/FormView.fxml", "Contacts" );
    }

    /**
     * This method allows the "Edit Contact" button to be clicked when called
     */
    public void contactSelected(){
        editContactButton.setDisable(false);
    }

    /**
     * This method takes us back to the "edit contact" version of our FormView.fxml. It passes the Person object selected
     * in the table and passes the controller to our sceneChanger object which will call the preloadData() method
     * to populate the form fields in FormView.fxml for editing
     */
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

        //Data is gathered and stored in an ObservableList object
        data = db.getContactsFromDatabase();

        //The table columns are set to each variable of the Person object we want to show to the user
        firstNameTableColumn.setCellValueFactory(new PropertyValueFactory<Person,String>("firstName"));
        lastNameTableColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("lastName"));
        genderTableColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("gender"));
        birthdayTableColumn.setCellValueFactory(new PropertyValueFactory<Person, LocalDate>("birthday"));
        addressTableColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("address"));
        phoneNumberTableColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("phoneNumber"));
        occupationTableColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("occupation"));

        // The contacts are pushed to the table
        contactTable.setItems(data);

        //The edit contacts button is disabled by default
        editContactButton.setDisable(true);
    }
}