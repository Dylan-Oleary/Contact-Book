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
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class FormViewController implements Initializable {
    private static String user = "root";
    private static String password = "root";
    private String location;
    private File imageFile = new File("./src/images/contact-icon.png");

    private Alert errorAlert;
    private Boolean update;

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
    private TextField phoneNumberTextField;

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

     public void saveContactButtonPushed(ActionEvent event) throws SQLException, IOException {

        errorCheck();

        if(update == true){


            Person p = new Person(firstNameTextField.getText(), lastNameTextField.getText(), genderChoiceBox.getValue(),
                    birthdayDatePicker.getValue(), addressTextField.getText(), phoneNumberTextField.getText(), occupationTextField.getText(), imageFile);

            DBConnect db = new DBConnect();

            if(!firstNameTextField.getText().isEmpty() && !lastNameTextField.getText().isEmpty() && !genderChoiceBox.getValue().isEmpty() &&
                    !addressTextField.getText().isEmpty() && !phoneNumberTextField.getText().isEmpty() && !occupationTextField.getText().isEmpty())
            {
                db.addContactToDatabase(firstNameTextField.getText(), lastNameTextField.getText(), genderChoiceBox.getValue(),
                        birthdayDatePicker.getValue(), addressTextField.getText(), phoneNumberTextField.getText(), occupationTextField.getText(), imageFile.getPath());

                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setHeaderText("Contact Added!");
                successAlert.setContentText("Contact has been added successfully!\n");

                successAlert.getButtonTypes().clear();
                ButtonType viewContacts = new ButtonType("View Contacts");
                successAlert.getButtonTypes().add(viewContacts);

                successAlert.showAndWait();

                try{
                    viewContactsButtonPushed(event);
                }catch (Exception e){

                }
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

        imageFile = fc.showOpenDialog(null);

        location = (imageFile.getAbsoluteFile().toURI().toString());
        Image image = new Image(location);
        photoImageView.setImage(image);
    }

    public boolean errorCheck(){
        update = false;

        errorAlert = new Alert(Alert.AlertType.ERROR);

        errorAlert.setTitle("Error alert");
        errorAlert.setHeaderText("Failed to add contact");

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

        if(phoneNumberTextField.getText().isEmpty()){
            errorMessage += "Please enter a phone number. \n";
        }


        if(phoneNumberTextField.getText().length() > 10){
            errorMessage += "Phone numbers can't be greater than 10 numbers.\n";
        }


        // Validate that only numbers are in the phone number field

        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        int x = 1;

        for(int i = 0; i < alphabet.length(); i++){

            //If statement for when our counter gets to the end of the alphabet. We can't use SUBSTRING (i,x)
            if(i == alphabet.length() - 1){
                String check = alphabet.substring(i);

                if(phoneNumberTextField.getText().contains(check)){
                    errorMessage += "Only numbers are valid in phone number field.\n";
                }
            }

            String check = alphabet.substring(i,x);

            if(phoneNumberTextField.getText().contains(check)){
                errorMessage += "Only numbers are valid in phone number field.\n";
            }
            x++;
        }

        if(occupationTextField.getText().isEmpty()){
            errorMessage += "Please enter an occupation. \n";
        }

        errorAlert.setContentText(errorMessage);

        if(!errorMessage.isEmpty()){
            errorAlert.showAndWait();
            update = false;
        }else{
            update = true;
        }

        return update;
    }



    /**
     * Clears the fields and sets them to blank, as well as setting the image back to the default icon
     */

    public void clearFieldsButtonPushed(ActionEvent event){
        firstNameTextField.setText("");
        lastNameTextField.setText("");
        addressTextField.setText("");
        phoneNumberTextField.setText("");
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
