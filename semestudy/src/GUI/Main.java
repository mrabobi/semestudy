package GUI;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class Main extends Application {

    private double posX,posY;

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../FXML/main.fxml"));
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setTitle("SemeStudy");
        stage.getIcons().add(new Image("/media/icon.png"));

        root.setOnMousePressed(e -> {
            posX = stage.getX() - e.getScreenX();
            posY = stage.getY() - e.getScreenY();
        });
        root.setOnMouseDragged(e -> {
            stage.setX(e.getScreenX() + posX);
            stage.setY(e.getScreenY() + posY);
        });

        stage.show();


      /*  try {
            Database.getInstance("ORAR", "bobibi");
        } catch (SQLException err) {
            if (err.getErrorCode() == 1017) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("LogIn Failed");
                alert.setContentText("Username or password invalid!");
                alert.setHeaderText(null);
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("An error has occurred!");
                alert.setContentText("Please contact support for help if this problem still appears!");
                alert.setHeaderText(null);
                alert.showAndWait();
            }
        }*/
    }

    public static void main(String[] args) {
        launch(args);
    }
}
