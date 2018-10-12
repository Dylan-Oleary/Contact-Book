package createContact;

import Models.DBConnect;
import Models.Person;
import Models.SceneChanger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.effect.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class FormViewController implements Initializable {
    private String location;
    private File imageFile = new File("./src/images/contact-icon.png");
    private Alert errorAlert;
    private Boolean update;
    private ArrayList<String> numbers = new ArrayList<>();

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
    private ChoiceBox<String> addressChoiceBox;

    @FXML
    private TextField phoneNumberTextField;

    @FXML
    private TextField occupationTextField;

    @FXML
    private RadioButton maleRadioButton;

    @FXML
    private RadioButton femaleRadioButton;

    @FXML
    private RadioButton otherRadioButton;

    @FXML
    private Button saveContactButton;

    @FXML
    private Button viewContactsButton;

    @FXML
    private Button clearFieldsButton;

     public void saveContactButtonPushed(ActionEvent event) throws SQLException, IOException {

        errorCheck();

        if(update == true){

            String gender = getGender();

            Person p = new Person(firstNameTextField.getText(), lastNameTextField.getText(), gender,
                    birthdayDatePicker.getValue(), (addressTextField.getText() + " " + addressChoiceBox.getValue()), phoneNumberTextField.getText(), occupationTextField.getText(), imageFile);

            DBConnect db = new DBConnect();

            if(!firstNameTextField.getText().isEmpty() && !lastNameTextField.getText().isEmpty() &&
                    !addressTextField.getText().isEmpty() && !phoneNumberTextField.getText().isEmpty() && !occupationTextField.getText().isEmpty())
            {
                db.addContactToDatabase(firstNameTextField.getText(), lastNameTextField.getText(), gender,
                        birthdayDatePicker.getValue(), (addressTextField.getText() + " " + addressChoiceBox.getValue()), phoneNumberTextField.getText(), occupationTextField.getText(), imageFile.getPath());

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
                    System.out.print(e.getMessage());
                }
            }
        }
    }

    public void viewContactsButtonPushed(ActionEvent event) throws Exception{
        SceneChanger.changeScenes(event, "../viewContacts/TableView.fxml", "Contacts" );
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

        InnerShadow errorShadow = new InnerShadow(12, Color.rgb(255,0,0));
        errorShadow.setBlurType(BlurType.ONE_PASS_BOX);

        errorAlert = new Alert(Alert.AlertType.ERROR);

        errorAlert.setTitle("Error alert");
        errorAlert.setHeaderText("Failed to add contact");

        String errorMessage = "";

        if(firstNameTextField.getText().isEmpty()){
            errorMessage += "First name is required. \n";
            firstNameTextField.setEffect(errorShadow);
        }

        if(lastNameTextField.getText().isEmpty()){
            errorMessage += "Last name is required. \n";
            lastNameTextField.setEffect(errorShadow);
        }

        if(birthdayDatePicker.getValue().isAfter(LocalDate.now())){
            errorMessage += "Birthday cannot be after today's date. \n";
            birthdayDatePicker.setEffect(errorShadow);
        }

        if(addressTextField.getText().isEmpty()){
            errorMessage += "Address cannot be empty. \n";
            addressTextField.setEffect(errorShadow);
        }

        if(phoneNumberTextField.getText().isEmpty()){
            errorMessage += "Please enter a phone number. \n";
            phoneNumberTextField.setEffect(errorShadow);
        }

        if(phoneNumberTextField.getText().length() > 12){
            errorMessage += "Phone numbers can't be greater than 10 numbers.\n";
            phoneNumberTextField.setEffect(errorShadow);
        }

        if(phoneNumberTextField.getText().length() < 12){
            errorMessage += "Phone numbers can't be less than 10 numbers.\n";
            phoneNumberTextField.setEffect(errorShadow);
        }

        // Validate that only numbers are in the phone number field

        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        int x = 1;

        for(int i = 0; i < alphabet.length(); i++){

            //If statement for when our counter gets to the end of the alphabet. We can't use SUBSTRING (i,x)
            if(i == alphabet.length() - 1){
                String check = alphabet.substring(i);

                if(phoneNumberTextField.getText().contains(check)){
                    errorMessage += "Only numbers are valid in phone number field. Remove any letters or special characters\n";
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
            occupationTextField.setEffect(errorShadow);
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

    public String getGender(){
         String gender = "";

         if(maleRadioButton.isSelected()){
             gender = "Male";
         }

         if(femaleRadioButton.isSelected()){
             gender = "Female";
         }

         if(otherRadioButton.isSelected()){
             gender = "Other";
         }

         return gender;
    }

    public void phoneNumberTextFieldValidation (KeyEvent event){
        if(phoneNumberTextField.getCaretPosition() > 12 || event.getCode().isLetterKey() || event.getCode() == KeyCode.SPACE
           || event.getCode() == KeyCode.MINUS){
            phoneNumberTextField.deletePreviousChar();
        }

        if(event.getCode() == KeyCode.BACK_SPACE){
            if(phoneNumberTextField.getCaretPosition() == 3){
                phoneNumberTextField.deletePreviousChar();
            }
            if(phoneNumberTextField.getCaretPosition() == 7){
                phoneNumberTextField.deletePreviousChar();
            }
        }else{
            if(event.getCode().isArrowKey()){

            }else if (phoneNumberTextField.getCaretPosition() == 3) {
                phoneNumberTextField.setText(phoneNumberTextField.getText() + "-");
                phoneNumberTextField.positionCaret(4);
            }else if (phoneNumberTextField.getCaretPosition() == 7) {
                phoneNumberTextField.setText(phoneNumberTextField.getText() + "-");
                phoneNumberTextField.positionCaret(8);
            }
        }
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

        firstNameTextField.setEffect(null);
        lastNameTextField.setEffect(null);
        birthdayDatePicker.setEffect(null);
        addressTextField.setEffect(null);
        phoneNumberTextField.setEffect(null);
        occupationTextField.setEffect(null);
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        addressChoiceBox.getItems().addAll( "Ave.", "Rd.", "St.","Way","Dr.", "Grv.", "Ln.", "Gdns.", "Pl.");

        Image contactIcon = new Image("file:./src/images/contact-icon.png");
        photoImageView.setImage(contactIcon);

        birthdayDatePicker.setValue(LocalDate.now());

        for(int i = 0; i < 10; i++){
            numbers.add(Integer.toString(i));
        }
    }
}
