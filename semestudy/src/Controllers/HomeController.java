package Controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class HomeController {



    public void closeButtonAction(ActionEvent event) {
        Platform.exit();
    }
}
