package MVC.Views;




import javafx.embed.swing.SwingFXUtils;
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

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
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

    /**
    public void chooseImageButtonPushed(ActionEvent event) throws Exception{
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter jpg = new FileChooser.ExtensionFilter("jpg");
        FileChooser.ExtensionFilter JPG = new FileChooser.ExtensionFilter(("JPG"));
        FileChooser.ExtensionFilter png = new FileChooser.ExtensionFilter(("png"));
        FileChooser.ExtensionFilter PNG = new FileChooser.ExtensionFilter(("PNG"));

        fileChooser.getExtensionFilters().addAll(jpg,JPG,png,PNG);

        File file = fileChooser.showOpenDialog(null);

        BufferedImage buffImage = ImageIO.read(file);
        Image image = SwingFXUtils.toFXImage(buffImage, null);
        photoImageView.setImage(image);
    }
     */

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        genderChoiceBox.getItems().addAll("Male", "Female", "Other");

        Image contactIcon = new Image("file:./src/images/contact-icon.png");
        photoImageView.setImage(contactIcon);
    }
}
