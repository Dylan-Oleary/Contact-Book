package Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class FormViewController {

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

}
