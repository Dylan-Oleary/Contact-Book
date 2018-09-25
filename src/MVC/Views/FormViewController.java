package MVC.Views;

import MVC.Models.DBConnect;
import MVC.Models.Person;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class FormViewController implements Initializable {
    private static String user = "root";
    private static String password = "root";

    @FXML
    private ImageView photoImageView;

    @FXML
    private Button chooseImageButton;

    @FXML
    private TextField firstNameTextField;

    @FXML
    private TextField lastNameTextField;

    @FXML
    private DatePicker birthdayDatePicker;

    @FXML
    private TextField addressTextField;

    @FXML
    private TextField phoneNumberTextFieldAreaCode;

    @FXML
    private TextField phoneNumberTextFieldTwo;

    @FXML
    private TextField phoneNumberTextFieldThree;

    @FXML
    private TextField occupationTextField;

    @FXML
    private ChoiceBox<String> genderChoiceBox;

    @FXML
    private Button saveContactButton;

    @FXML
    private Button viewContactsButton;

    @FXML
    private Button clearFieldsButton;

    public void saveContactButtonPushed(ActionEvent event) throws SQLException{

        errorCheck();

        //Since there are 3 phone number fields, we must create the phone number before sending it to the constructor

        String phoneNumber = phoneNumberTextFieldAreaCode.getText() + phoneNumberTextFieldTwo.getText() + phoneNumberTextFieldThree.getText();

        Person p = new Person(firstNameTextField.getText(), lastNameTextField.getText(), genderChoiceBox.getValue(),
                birthdayDatePicker.getValue(), addressTextField.getText(), phoneNumber, occupationTextField.getText());

        DBConnect db = new DBConnect();

        if(!firstNameTextField.getText().isEmpty() && !lastNameTextField.getText().isEmpty() && !genderChoiceBox.getValue().isEmpty() &&
                !addressTextField.getText().isEmpty() && !phoneNumber.isEmpty() && !occupationTextField.getText().isEmpty())
        {
            db.addContactToDatabase(firstNameTextField.getText(), lastNameTextField.getText(), genderChoiceBox.getValue(),
                    birthdayDatePicker.getValue(), addressTextField.getText(), phoneNumber, occupationTextField.getText());

            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setHeaderText("Contact Added!");
            successAlert.setContentText("Contact has been added successfully!\nClick");

            successAlert.getButtonTypes().clear();
            ButtonType viewContacts = new ButtonType("View Contacts");
            successAlert.getButtonTypes().add(viewContacts);

            successAlert.showAndWait();

            try{
                viewContactsButtonPushed(event);
            }catch(Exception e){

            }
        }
    }

    public void viewContactsButtonPushed(ActionEvent event) throws Exception{
        Parent contactTableParent=FXMLLoader.load(getClass().getResource("TableView.fxml"));
        Scene contactTableScene = new Scene(contactTableParent);
        contactTableScene.getStylesheets().add("style/layout.css");

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(contactTableScene);
        window.show();
    }

    public void chooseImageButtonPushed(ActionEvent event){
        FileChooser fc = new javafx.stage.FileChooser();
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg"));

        File file = fc.showOpenDialog(null);

        String location = (file.getAbsoluteFile().toURI().toString());
        Image image = new Image(location);
        photoImageView.setImage(image);
    }

    public void errorCheck(){
        Alert alert = new Alert(Alert.AlertType.ERROR);

        alert.setTitle("Error alert");
        alert.setHeaderText("Failed to add contact");

        String errorMessage = "";

        if(firstNameTextField.getText().isEmpty()){
            errorMessage += "First name is required. \n";
        }

        if(lastNameTextField.getText().isEmpty()){
            errorMessage += "Last name is required. \n";
        }

        if(genderChoiceBox.getSelectionModel().isEmpty()){
            errorMessage += "Please specify your gender. \n";
        }

        if(birthdayDatePicker.getValue().isAfter(LocalDate.now())){
            errorMessage += "Birthday cannot be after today's date. \n";
        }

        if(addressTextField.getText().isEmpty()){
            errorMessage += "Address cannot be empty. \n";
        }

        if(phoneNumberTextFieldAreaCode.getText().isEmpty() && phoneNumberTextFieldTwo.getText().isEmpty() && phoneNumberTextFieldThree.getText().isEmpty()){
            errorMessage += "Please enter a phone number. \n";
        }

        if(occupationTextField.getText().isEmpty()){
            errorMessage += "Please enter an occupation. \n";
        }

        alert.setContentText(errorMessage);

        if(!errorMessage.isEmpty()){
            alert.showAndWait();
        }
    }


    /**
     * Clears the fields and sets them to blank, as well as setting the image back to the default icon
     */

    public void clearFieldsButtonPushed(ActionEvent event){
        firstNameTextField.setText("");
        lastNameTextField.setText("");
        addressTextField.setText("");
        phoneNumberTextFieldAreaCode.setText("");
        phoneNumberTextFieldTwo.setText("");
        phoneNumberTextFieldThree.setText("");
        occupationTextField.setText("");

        // Sets image back to default contact icon

        Image contactIcon = new Image("file:./src/images/contact-icon.png");
        photoImageView.setImage(contactIcon);

        // Sets date picker back to current date

        birthdayDatePicker.setValue(LocalDate.now());
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        genderChoiceBox.getItems().addAll("Male", "Female", "Other");

        Image contactIcon = new Image("file:./src/images/contact-icon.png");
        photoImageView.setImage(contactIcon);

        birthdayDatePicker.setValue(LocalDate.now());
    }
}
