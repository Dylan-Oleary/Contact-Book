package createContact;

import Models.ControllerClass;
import Models.DBConnect;
import Models.Person;
import Models.SceneChanger;
import javafx.embed.swing.SwingFXUtils;
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
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class FormViewController implements Initializable, ControllerClass {
    private String location;
    private File imageFile = new File("./src/images/contact-icon.png");
    private Alert errorAlert;
    private Boolean update;
    private ArrayList<String> numbers = new ArrayList<>();
    private Person person;

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

    @FXML
    private Label titleLabel;

    /**
     *This method saves a contact to the contact book. It runs the errorCheck() method. If that method sets the update
     * instance variable to true, the contact is added to the database. If the contact already exists in the database,
     * they are edited.
     */
    public void saveContactButtonPushed(ActionEvent event) throws SQLException, IOException {

        errorCheck();

        //errorCheck() has set update == true. This means no validation errors were found
        if(update == true){

            if(person == null){
                //The Person object takes gender as a string. We use getGender() to get the gender as a String variable.
                String gender = getGender();

                Person p = new Person(firstNameTextField.getText(), lastNameTextField.getText(), gender,
                        birthdayDatePicker.getValue(), (addressTextField.getText() + " " + addressChoiceBox.getValue()),
                        phoneNumberTextField.getText(), occupationTextField.getText(), imageFile);

                DBConnect db = new DBConnect();

                if(!firstNameTextField.getText().isEmpty() && !lastNameTextField.getText().isEmpty() &&
                        !addressTextField.getText().isEmpty() && !phoneNumberTextField.getText().isEmpty() &&
                        !occupationTextField.getText().isEmpty()) {

                    //The contact is added to the database
                    db.addContactToDatabase(firstNameTextField.getText(), lastNameTextField.getText(), gender,
                            birthdayDatePicker.getValue(), (addressTextField.getText() + " " +
                            addressChoiceBox.getValue()), phoneNumberTextField.getText(), occupationTextField.getText(),
                            imageFile.getPath());

                    //This creates the Information Alert to let the user know the addition was successful.
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                    successAlert.setHeaderText("Contact Added!");
                    successAlert.setContentText("Contact has been added successfully!\n");

                    successAlert.getButtonTypes().clear();
                    ButtonType viewContacts = new ButtonType("View Contacts");
                    successAlert.getButtonTypes().add(viewContacts);

                    successAlert.showAndWait();

                    try {
                        viewContactsButtonPushed(event);
                    } catch (Exception e) {
                        System.out.print(e.getMessage());
                    }
                }

            }else if(person != null){
                String updatedGender = getGender();

                DBConnect editDb = new DBConnect();

                editDb.editContactInDatabase(firstNameTextField.getText(), lastNameTextField.getText(), updatedGender, birthdayDatePicker.getValue(),
                        (addressTextField.getText() + " " + addressChoiceBox.getValue()), phoneNumberTextField.getText(), occupationTextField.getText(), imageFile.getPath(), person.getPersonID());

                //This creates the Information Alert to let the user know the update was successful.
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setHeaderText("Contact Updated!");
                successAlert.setContentText("Contact has been updated successfully!\n");

                successAlert.getButtonTypes().clear();
                ButtonType viewContacts = new ButtonType("View Contacts");
                successAlert.getButtonTypes().add(viewContacts);

                successAlert.showAndWait();

                try {
                    viewContactsButtonPushed(event);
                } catch (Exception e) {
                    System.out.print(e.getMessage());
                }

            }
        }
    }

    /**
     *This method calls the SceneChanger utility class and will take us to the TableView scene.
     */
    public void viewContactsButtonPushed(ActionEvent event) throws Exception{
        SceneChanger.changeScenes(event, "../viewContacts/TableView.fxml", "Contacts" );
    }

    /**
     *This method allows the user to choose a file from their computer, stores it as an Image variable, ready
     * to be put into the Person object that is used to create a contact. It also changes the photo that is shown
     * in the GUI to the photo that the user has chosen.
     */
    public void chooseImageButtonPushed(ActionEvent event){
        FileChooser fc = new javafx.stage.FileChooser();
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg"));

        imageFile = fc.showOpenDialog(null);

        location = (imageFile.getAbsoluteFile().toURI().toString());
        Image image = new Image(location);
        photoImageView.setImage(image);
    }

    /**
     *This method builds the error message if the user fails to fill out the form correctly. It also manipulates the
     * instance variable "update", which when true, allows the contact to be added to the database. If validation
     * errors are present, "update" is set to false.
     */
    public boolean errorCheck(){
        update = false;

        //Creates an object that gives a form field a red shadow
        InnerShadow errorShadow = new InnerShadow(12, Color.rgb(255,0,0));
        errorShadow.setBlurType(BlurType.ONE_PASS_BOX);

        //Create Alert object
        errorAlert = new Alert(Alert.AlertType.ERROR);

        errorAlert.setTitle("Error alert");
        errorAlert.setHeaderText("Failed to add contact");

        String errorMessage = "";

        //If any of these fields are empty or invalid, the error message is updated and the field is given the red
        //InnerShadow error

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

        //Set the error alert to show all of the error messages the program has gathered
        errorAlert.setContentText(errorMessage);

        //If there are no errors, update is set to false and the contact will not be able to be added to the database
        if(!errorMessage.isEmpty()){
            errorAlert.showAndWait();
            update = false;
        }else{
            update = true;
        }

        return update;
    }

    /**
     *This method determines which gender radio button is selected, sets the String variable gender to the value of
     * the selected button and returns the gender. The gender is passed as a String to the Person object in
     * saveContactButtonPushed();
     */
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

    /**
     * This method makes it so that the user cannot enter anything other than a valid phone number. It won't allow
     * letters, more than 10 numbers and automatically inserts the hyphens in a phone number
     */
    public void phoneNumberTextFieldValidation (KeyEvent event){

        //If any invalid keys are typed or if the caret position is greater than 12, the character is automatically deleted
        if(phoneNumberTextField.getCaretPosition() > 12 || event.getCode().isLetterKey() || event.getCode() == KeyCode.SPACE
           || event.getCode() == KeyCode.MINUS){
            phoneNumberTextField.deletePreviousChar();
        }

        //If backspace is pressed and the number is behind a hyphen, the hyphen and the number are deleted
        //Else, if the key is an arrow key, we have to move the caret ahead and print out the number. This is to stop
        // the arrow keys from triggering the addition of another hyphen
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

    /**
     *This method is used for when we update a contact. The fields are populated by the contacts data that exists within
     * the database. The title is changed to "Edit Contact" instead of "Create Contact"
     */
    @Override
    public void preloadData(Person person) {
        this.person = person;

        //Populate the text fields with the Person object variables
        firstNameTextField.setText(person.getFirstName());
        lastNameTextField.setText(person.getLastName());
        birthdayDatePicker.setValue(person.getBirthday());
        addressTextField.setText(person.getAddress());
        occupationTextField.setText(person.getOccupation());
        phoneNumberTextField.setText(person.getPhoneNumber());

        //Set the radio button to the gender assigned to the contact
        if(person.getGender().equals("Male")){
            maleRadioButton.setSelected(true);
        }else if(person.getGender().equals("Female")){
            femaleRadioButton.setSelected(true);
        }else{
            otherRadioButton.setSelected(true);
        }

        //Change the title to differentiate the scene from the "create contact" scene
        //Change "Save Contact" to "Update Contact"
        titleLabel.setText("Edit Contact");
        saveContactButton.setText("Update Contact");

        //Grab the Image File from the Person object and change it to an Image object that can be displayed in
        // ImageView
        try{
            String imgLocation = "./src/images/profileImages/" + person.getImageFile().getName();
            imageFile = new File(imgLocation);
            BufferedImage bufferedImage = ImageIO.read(imageFile);
            Image img = SwingFXUtils.toFXImage(bufferedImage, null);
            photoImageView.setImage(img);
        }catch(IOException e){
            Alert photoAlert = new Alert(Alert.AlertType.ERROR);
            photoAlert.setTitle("Ouch!");
            photoAlert.setHeaderText("Profile Photo Could Not Be Found");
            photoAlert.setContentText("Sorry! We couldn't find your profile photo, choose a new one!");

            photoAlert.show();
        }
    }
}
