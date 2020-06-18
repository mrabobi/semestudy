package Controllers;

import Data.DAO.TimetableDAO;
import Data.Models.*;
import Services.DBService;
import Services.FileService;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;
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

    private double posX,posY;
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
            pathField.setText(Path + "\\semestudy_export");
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
            showAlert("An error has occurred! Please contact support for help if this problem still appears!", Alert.AlertType.ERROR);
        }
    }

    public void generateFile() throws SQLException, TransformerException, ParserConfigurationException {

        if (semesterComboBox.getSelectionModel().getSelectedItem() == null) {
            showAlert("Please select a valid semester!", Alert.AlertType.ERROR);
        } else if (!xmlCheckbox.isSelected() && !jsonCheckbox.isSelected()) {
            showAlert("Please select the type of the output file!", Alert.AlertType.ERROR);
        } else if (Path == null) {
            showAlert("Please select the path before generating the file!", Alert.AlertType.ERROR);
        } else {
            semesterId = semesterMap.get(semesterComboBox.getSelectionModel().getSelectedItem());
            Timetable timetable = DBService.generateTimetable(semesterId);
            if (timetable == null)
                showAlert("The program wasn't able to generate the file. Please check if your database has the correct structure", Alert.AlertType.ERROR);
            else {
                if(xmlCheckbox.isSelected()){
                    FileService.exportXML(timetable, Path);
                }
                else {
                    FileService.exportJSON(timetable, Path);
                }
            }

        }
    }

    public void previewTimetable() throws IOException {
        if (semesterComboBox.getSelectionModel().getSelectedItem() == null) {
            showAlert("Please select a valid semester!", Alert.AlertType.ERROR);
        }
        else {
            semesterId = semesterMap.get(semesterComboBox.getSelectionModel().getSelectedItem());
            Timetable timetable = DBService.generateTimetable(semesterId);
            if (timetable == null)
                showAlert("The program wasn't able to generate the file. Please check if your database has the correct structure", Alert.AlertType.ERROR);
            else {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../FXML/Preview.fxml"));
                Parent root = (Parent) fxmlLoader.load();


                Stage oldStage = (Stage) xmlCheckbox.getScene().getWindow();
                PreviewController previewController = fxmlLoader.getController();
                previewController.setTreeView(timetable);

                Stage stage = new Stage();
                Scene scene = new Scene(root);
                scene.setFill(Color.TRANSPARENT);
                stage.setTitle("SemeStudy");
                stage.getIcons().add(new Image("/media/icon.png"));
                stage.setScene(scene);
                stage.initStyle(StageStyle.TRANSPARENT);
                stage.setX(oldStage.getX() * 1.9);
                stage.setY(oldStage.getY() * 1.3);

                root.setOnMousePressed(e -> {
                    posX = stage.getX() - e.getScreenX();
                    posY = stage.getY() - e.getScreenY();
                });
                root.setOnMouseDragged(e -> {
                    stage.setX(e.getScreenX() + posX);
                    stage.setY(e.getScreenY() + posY);
                });

                stage.show();

            }
        }
    }

    public void showAlert(String error, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle("SEMESTUDY INFO");
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
            showAlert("No semesters found in your database. The application will be closed!", Alert.AlertType.ERROR);
            Platform.exit();
        }
    }
}
