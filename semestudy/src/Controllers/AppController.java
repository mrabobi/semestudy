package Controllers;

import Data.DAO.ActorDAO;
import Data.DAO.AssignmentDAO;
import Data.DAO.TimetableDAO;
import Data.Models.*;
import Services.DBService;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

public class AppController implements Initializable {

    public CheckBox xmlCheckbox;
    public CheckBox jsonCheckbox;
    public TextField pathField;
    public ComboBox<String> semesterComboBox;
    public String Path;
    public int semesterId;
    public HashMap<Integer, Professor> professorHashMap;
    public HashMap<Integer, Student> studentHashMap;
    private HashMap<String, Integer> semesterMap;

    public void checkBoxXML() {
        if (xmlCheckbox.isSelected()) {
            jsonCheckbox.setSelected(false);
        }
    }

    public void checkBoxJSON() {
        if (jsonCheckbox.isSelected()) {
            xmlCheckbox.setSelected(false);
        }
    }

    public void savePath() {
        final DirectoryChooser directoryChooser = new DirectoryChooser();
        Stage stage = (Stage) pathField.getScene().getWindow();
        File file = directoryChooser.showDialog(stage);

        if (file != null) {
            Path = file.getAbsolutePath();
            pathField.setText(Path);
        }
    }

    public void loadSemesters() {
        semesterComboBox.getItems().clear();
        semesterMap = new HashMap<>();
        try {

            TimetableDAO timetableDAO = new TimetableDAO();

            List<Timetable> timetableList = timetableDAO.getTimetables();
            for (Timetable t : timetableList) {
                semesterMap.put(t.getTitle(), t.getId());
                semesterComboBox.getItems().add(t.getTitle());
            }
        } catch (SQLException err) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("An error has occurred!");
            alert.setContentText("Please contact support for help if this problem still appears!");
            alert.setHeaderText(null);
            alert.showAndWait();
        }
    }

    public void generateFile() throws SQLException {

        if (semesterComboBox.getSelectionModel().getSelectedItem() == null) {
            showAlert("Please select a valid semester!");
        } else if (!xmlCheckbox.isSelected() && !jsonCheckbox.isSelected()) {
            showAlert("Please select the type of the output file!");
        } else if (Path == null) {
            showAlert("Please select the path before generating the file!");
        } else {
            semesterId = semesterMap.get(semesterComboBox.getSelectionModel().getSelectedItem());

            Timetable timetable = DBService.getTimetable(semesterId);
            System.out.println(timetable.toString());

            try {
                TimetableDAO timetableDAO = new TimetableDAO();
                Timetable primaryTimetable = timetableDAO.getTimetable(semesterId);

                AssignmentDAO assignmentDAO = new AssignmentDAO();
                ActorDAO actorDAO = new ActorDAO();
                List<Assignment> assignmentList = assignmentDAO.getAssignments(semesterId);
                assignmentList = DBService.setDateTime(primaryTimetable.getHours(), assignmentList);
                studentHashMap = new HashMap<>();
                professorHashMap = new HashMap<>();

                for (Assignment a : assignmentList) {
                    if (a.getEventRelatedActorsId() != null) {
                        List<String> actorLists = DBService.generateActorLists(a.getEventRelatedActorsId());
                        String test = " ";
                        if (!actorLists.contains("null")) {
                            for (String actorId : actorLists) {

                                if(professorHashMap.containsKey(Integer.parseInt(actorId))){
                                    Professor professor = professorHashMap.get(Integer.parseInt(actorId));
                                    professor.setAssignment(a);
                                }
                                else if(studentHashMap.containsKey(Integer.parseInt(actorId))){
                                    Student student = studentHashMap.get(Integer.parseInt(actorId));
                                    student.addAssignment(a);
                                }
                                else {
                                    Actor actor = actorDAO.getActor(Integer.parseInt(actorId));
                                    if (actor != null) {
                                        if (DBService.isProfessor(actor)) {
                                            Professor professor = new Professor();
                                            professor.setProfessor(actor.getId(), actor.getName(), actor.getAbbr(), actor.getPrefix());
                                            professor.setAssignment(a);
                                            professorHashMap.put(actor.getId(), professor);
                                        }


                                        if (DBService.isStudent(actor)) {
                                            Student student = new Student();
                                            student.setStudent(actor.getId(), actor.getName(), actor.getAbbr());
                                            student.addAssignment(a);
                                            studentHashMap.put(student.getId(), student);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                if(professorHashMap.isEmpty() && studentHashMap.isEmpty()){
                    showAlert("The program wasn't able to generate the file. Please check if your database has the correct structure");
                }
                else {
                    for(Professor i : professorHashMap.values()){
                        System.out.println(i.toString());
                    }
                }

            } catch (SQLException err) {
                System.out.println(err.getSQLState());
            }

        }
    }

    public void showAlert(String error) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Generating error");
        alert.setContentText(error);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    public void closeButtonAction(ActionEvent event) {
        Platform.exit();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadSemesters();
        if (semesterComboBox.getItems().isEmpty()) {
            showAlert("No semesters found in your database. The application will be closed!");
            Platform.exit();
        }
    }
}
