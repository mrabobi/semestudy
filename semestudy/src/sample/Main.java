package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage connectionStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../FXML/sample.fxml"));
        connectionStage.setTitle("SemeStudy");
        connectionStage.setScene(new Scene(root, 300, 275));
        connectionStage.getIcons().add(new Image("/media/icon.png"));
        connectionStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
