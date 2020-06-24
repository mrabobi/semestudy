package Controllers;

import Data.Models.*;
import Services.DBService;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

public class PreviewController implements Initializable {


    public TreeView<String> treeView;

    public void closeButtonAction(ActionEvent event) {
        Stage oldStage = (Stage) treeView.getScene().getWindow();
        oldStage.close();
    }

    public TreeItem<String> generateSchedule(Student student, Professor professor){
        TreeItem<String> schedule = new TreeItem<>("Orar");
        List<Assignment> scheduleEvents;

        if(student != null)
            scheduleEvents = DBService.getScheduleEventsSorted(student.getAssignmentList());
        else
            scheduleEvents = DBService.getScheduleEventsSorted(professor.getAssignmentList());
        HashMap<Integer,String> days = DBService.addDays();
        for(String day : days.values()){
            TreeItem<String> itemDay = new TreeItem<>(day);
            for(Assignment assignment : scheduleEvents){
                if(assignment.getDay().equals(day)) {
                    Event event = assignment.getEvent();
                    TreeItem<String> ora = new TreeItem<>(event.getName());

                    TreeItem<String> type = new TreeItem<>("Type: " + event.getEventType());
                    TreeItem<String> startHour = new TreeItem<>("Start time: " + assignment.getStartHour());
                    TreeItem<String> endHour = new TreeItem<>("End time: " + assignment.getEndHour());
                    TreeItem<String> location;
                    if(assignment.getResources() != null) {
                        if (assignment.getResources().replace("@"," ") != " ")
                            location = new TreeItem<>("Resources: " + assignment.getResources().replace("@"," "));
                        else
                            location = new TreeItem<>("Resources: none");
                    }
                    else {
                        location = new TreeItem<>("Resources: none");
                    }
                    ora.getChildren().addAll(type, startHour, endHour, location);

                    itemDay.getChildren().add(ora);
                }
            }
            schedule.getChildren().add(itemDay);
        }
        return schedule;
    }

    public void setTreeView(Timetable timetable) {

        TreeItem<String> treeItem = new TreeItem<>(timetable.getTitle());
        this.treeView.setRoot(treeItem);
        treeItem.setExpanded(true);

        TreeItem<String> treeItemId = new TreeItem<>(" ID: " + timetable.getId());
        TreeItem<String> treeItemDomain = new TreeItem<>(" Domain: " + timetable.getDomain());
        TreeItem<String> treeItemPublish = new TreeItem<>(" Publish: " + timetable.getPublish());
        TreeItem<String> treeItemBeginDate = new TreeItem<>(" Begin Date: " + timetable.getBeginDate());
        TreeItem<String> treeItemEndDate = new TreeItem<>(" End Date: " + timetable.getEndDate());

        //ForProfessors
        TreeItem<String> treeProfessors = new TreeItem<>("Professors");
        List<Professor> profList = timetable.getProfessorList();
        profList.sort(Comparator.comparing(Professor::getName));

        for (Professor professor : profList){
            TreeItem<String> prof = new TreeItem<>(professor.getName());
            TreeItem<String> pId = new TreeItem<>("ID: " + professor.getId());
            TreeItem<String> pPrefix;
            if(professor.getPrefix() != null)
                 pPrefix = new TreeItem<>("Grad: " + professor.getPrefix());
            else
                pPrefix = new TreeItem<>("Grad: none");
            TreeItem<String> schedule = generateSchedule(null,professor);

            prof.getChildren().addAll(pId, pPrefix, schedule);
            treeProfessors.getChildren().add(prof);
        }

        TreeItem<String> announcementsTree = new TreeItem<>("Announcements");
        List<Announcement> announcementList = timetable.getAnnouncementList();
        int counting = 1;
        for(Announcement announcement : announcementList) {
            TreeItem<String> ann = new TreeItem<>(String.valueOf(counting));
            counting += 1;
            TreeItem<String> header = new TreeItem<>("Header: " + announcement.getHeader());
            TreeItem<String> message = new TreeItem<>("Message: " + announcement.getMessage());
            TreeItem<String> end = new TreeItem<>("Available: " + announcement.getEnd());
            ann.getChildren().addAll(header, message, end);
            announcementsTree.getChildren().add(ann);
        }



        //ForStudents
        TreeItem<String> treeStudents = new TreeItem<>("Students");
        List<Student> studList = timetable.getStudentList();
        studList.sort(Comparator.comparing(Student::getAbbr));

        for (Student student : studList){
            TreeItem<String> stud = new TreeItem<>(student.getAbbr());
            TreeItem<String> sId = new TreeItem<>("ID: " + student.getId());
            TreeItem<String> sName = new TreeItem<>("Name: " + student.getName());
            TreeItem<String> schedule = generateSchedule(student,null);

            stud.getChildren().addAll(sId, sName, schedule);
            treeStudents.getChildren().add(stud);
        }

        treeItem.getChildren().addAll(treeItemId, treeItemDomain, treeItemPublish,
                treeItemBeginDate, treeItemEndDate, treeProfessors, treeStudents, announcementsTree);


    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
