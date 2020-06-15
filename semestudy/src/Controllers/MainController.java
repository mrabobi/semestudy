package Controllers;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private VBox vbox;
    private Parent fxml;



    public void initialize(URL url, ResourceBundle resourceBundle) {
        TranslateTransition translateTransition =  new TranslateTransition(Duration.seconds(0.7), vbox);
        translateTransition.setToX(0);
        translateTransition.play();
        translateTransition.setOnFinished(
                e->{
                    try {
                        fxml = FXMLLoader.load(getClass().getResource("../FXML/Home.fxml"));
                        vbox.getChildren().removeAll();
                        vbox.getChildren().setAll(fxml);
                    }
                    catch (IOException ex){

                    }
                }
        );
    }


    public void showHomeFXML(javafx.event.ActionEvent actionEvent) {
        TranslateTransition translateTransition =  new TranslateTransition(Duration.seconds(0.7), vbox);
        translateTransition.setToX(0);
        translateTransition.play();
        translateTransition.setOnFinished(
                e->{
                    try {
                        fxml = FXMLLoader.load(getClass().getResource("../FXML/Home.fxml"));
                        vbox.getChildren().removeAll();
                        vbox.getChildren().setAll(fxml);
                    }
                    catch (IOException ex){

                    }
                }
        );
    }

    public void showSignInFXML(javafx.event.ActionEvent actionEvent) {
        TranslateTransition translateTransition =  new TranslateTransition(Duration.seconds(0.7), vbox);
        translateTransition.setToX(vbox.getLayoutX() * 11.5);
        translateTransition.play();
        translateTransition.setOnFinished(
                e->{
                    try {
                        fxml = FXMLLoader.load(getClass().getResource("../FXML/SignIn.fxml"));
                        vbox.getChildren().removeAll();
                        vbox.getChildren().setAll(fxml);
                    }
                    catch (IOException ex){

                    }
                }
        );
    }



}
