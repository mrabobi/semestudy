package GUI;

import Data.DAO.EventDAO;
import Data.DAO.ProfessorDAO;
import Data.DAO.StudentDAO;
import Data.DAO.TimetableDAO;
import Data.Database;
import Data.Models.Event;
import Data.Models.Professor;
import Data.Models.Student;
import Data.Models.Timetable;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.sql.SQLException;
import java.util.List;

public class Main extends Application {

    private double posX,posY;

    @Override
    public void start(Stage stage) throws Exception{
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
        try {
            Database.getInstance("ORAR", "bobibi");


        }
        catch (SQLException err){
            if(err.getErrorCode() == 1017)
                System.out.println("ID SI PASS GRES");
            else
                System.out.println("CONTACTATI ADMINI");
        }

    }

    public static void main(String[] args) {
        launch(args);
    }
}
