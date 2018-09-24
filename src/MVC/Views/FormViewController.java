package MVC.Views;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class FormViewController implements Initializable {

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

    public void viewContactsButtonPushed(ActionEvent event) throws Exception{
        Parent contactTableParent=FXMLLoader.load(getClass().getResource("TableView.fxml"));
        Scene contactTableScene = new Scene(contactTableParent);

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

    /** Clears the fields and sets them to blank, as well as setting the image back to the default icon */

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
