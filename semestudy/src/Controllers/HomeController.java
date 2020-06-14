package Controllers;

import Data.Database;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.SQLException;

public class HomeController {

    public TextField usernameField;

    public PasswordField passwordField;

    public void logIn(ActionEvent event) throws IOException {
        try{
            Database.getInstance(usernameField.getText(),passwordField.getText());
            Stage oldStage = (Stage) usernameField.getScene().getWindow();
            oldStage.close();

            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("../FXML/App.fxml"));
            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT);
            stage.setScene(scene);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.setTitle("SemeStudy");
            stage.getIcons().add(new Image("/media/icon.png"));
            stage.show();

        }
        catch (SQLException err){
            if(err.getErrorCode() == 1017) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("LogIn Failed");
                alert.setContentText("Username or password invalid!");
                alert.setHeaderText(null);
                alert.showAndWait();
            }
            else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("An error has occurred!");
                alert.setContentText("Please contact support for help if this problem still appears!");
                alert.setHeaderText(null);
                alert.showAndWait();
            }

        }
    }

    public void closeButtonAction(ActionEvent event) {
        Platform.exit();
    }
}
