package Models;


import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneChanger {

    public static void changeScenes(ActionEvent event, String viewName, String title) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(new Object(){}.getClass().getResource(viewName));

        Parent root = loader.load();
        Scene scene = new Scene(root);

        scene.getStylesheets().add("style/layout.css");

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();

        stage.setScene(scene);
        stage.setTitle(title);
        stage.show();
    }

    public void changeScenes(ActionEvent event, String viewName, String title, Person person, ControllerClass controllerClass) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(viewName));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        scene.getStylesheets().add("style/layout.css");

        controllerClass = loader.getController();
        controllerClass.preloadData(person);

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();

        stage.setScene(scene);
        stage.setTitle(title);
        stage.show();
    }
}
