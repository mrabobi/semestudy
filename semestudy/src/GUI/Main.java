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


    @Override
    public void start(Stage stage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../FXML/main.fxml"));
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setTitle("SemeStudy");
        stage.getIcons().add(new Image("/media/icon.png"));
        stage.show();
        try {
            Database.getInstance("ORAR", "bobibi");
//            ProfessorDAO professorDAO = new ProfessorDAO();
//            Professor ls = professorDAO.getProfessor(687);
//            System.out.println(ls.toString());

//            List<Professor> professorList = professorDAO.getProfessors();
//            for(Professor p : professorList){
//                System.out.println(p.toString());
//            }

//            TimetableDAO timetableDAO = new TimetableDAO();
//            Timetable timetable = timetableDAO.getTimetable(901);
//            System.out.println(timetable.toString());

//            List<Timetable> timetableList = timetableDAO.getTimetables();
//            for (Timetable t : timetableList){
//                System.out.println(t.toString());
//            }

            StudentDAO studentDAO = new StudentDAO();
//            List<Student> studentList = studentDAO.getStudents();
//
//            for (Student s : studentList){
//                System.out.println(s.toString());
//            }



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
