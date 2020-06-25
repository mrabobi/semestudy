package Services;

import Data.Models.*;
import javafx.scene.control.Alert;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


import javax.xml.parsers.ParserConfigurationException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class FileService {

    public static Element generateProfStudXML(Document document, Student student, Professor professor){
        Element orar = document.createElement("Orar");
        HashMap<Integer,String> days = DBService.addDays();
        for(String day : days.values()){
            Element dayElement = document.createElement("day");
            orar.appendChild(dayElement);

            Attr dayId = document.createAttribute("id");
            dayId.setValue(day);
            dayElement.setAttributeNode(dayId);
            List<Assignment> assignmentList;
            if(student != null) {
                assignmentList = student.getAssignmentList();
            }
            else {
                assignmentList = professor.getAssignmentList();
            }
            for(Assignment assignment: assignmentList){
                if (assignment.getDay().equals(day)){
                    Event event = assignment.getEvent();
                    Element materie = document.createElement("materie");
                    dayElement.appendChild(materie);

                    Attr materieId = document.createAttribute("id");
                    materieId.setValue(event.getName());
                    materie.setAttributeNode(materieId);

                    Element type = document.createElement("Type");
                    type.appendChild(document.createTextNode(event.getEventType()));
                    materie.appendChild(type);

                    Element startHour = document.createElement("StartTime");
                    startHour.appendChild(document.createTextNode(assignment.getStartHour().toString()));
                    materie.appendChild(startHour);

                    Element endHour = document.createElement("EndTime");
                    endHour.appendChild(document.createTextNode(assignment.getEndHour().toString()));
                    materie.appendChild(endHour);

                    Element resources = document.createElement("Resources");
                    resources.appendChild(document.createTextNode(assignment.getResources()));
                    materie.appendChild(resources);
                }
            }
        }
        return orar;
    }

    public static void exportXML(Timetable timetable, String path) throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
        Document document = documentBuilder.newDocument();

        Element root = document.createElement("schedule");
        Attr faculty = document.createAttribute("faculty");
        faculty.setValue(timetable.getDomain());
        root.setAttributeNode(faculty);
        document.appendChild(root);

        Element title = document.createElement("Title");
        title.appendChild(document.createTextNode(timetable.getTitle()));
        root.appendChild(title);

        Element beginDate = document.createElement("Beginning");
        beginDate.appendChild(document.createTextNode(timetable.getBeginDate().toString()));
        root.appendChild(beginDate);

        Element endDate = document.createElement("Ending");
        endDate.appendChild(document.createTextNode(timetable.getEndDate().toString()));
        root.appendChild(endDate);

        //Announcements

        Element announcementsElement = document.createElement("Announcements");
        int count = 1;
        List<Announcement> announcementList = timetable.getAnnouncementList();
        for (Announcement announcement:announcementList){
            Element ann = document.createElement("announce");
            announcementsElement.appendChild(ann);
            Attr attr = document.createAttribute("no");
            attr.setValue(String.valueOf(count));
            ann.setAttributeNode(attr);
            count += 1;
            Element headerElement = document.createElement("Title");
            headerElement.appendChild(document.createTextNode(announcement.getHeader()));
            ann.appendChild(headerElement);

            Element messageAnn = document.createElement("Message");
            messageAnn.appendChild(document.createTextNode(announcement.getMessage()));
            ann.appendChild(messageAnn);

            Element available = document.createElement("Available");
            if(announcement.getEnd() != null)
                available.appendChild(document.createTextNode(announcement.getEnd()));
            else
                available.appendChild(document.createTextNode("unknown"));
            ann.appendChild(available);

        }
        root.appendChild(announcementsElement);

        //Students
        Element students = document.createElement("Students");

        List<Student> studentList = timetable.getStudentList();
        studentList.sort(Comparator.comparing(Student::getAbbr));
        for (Student student:studentList){
            Element stud = document.createElement("student");
            students.appendChild(stud);

            Attr attr = document.createAttribute("id");
            attr.setValue(String.valueOf(student.getId()));
            stud.setAttributeNode(attr);

            Element name = document.createElement("Name");
            name.appendChild(document.createTextNode(student.getName()));
            stud.appendChild(name);

            Element abbr = document.createElement("Abbr");
            abbr.appendChild(document.createTextNode(student.getAbbr()));
            stud.appendChild(abbr);

            Element orar = generateProfStudXML(document,student,null);
            stud.appendChild(orar);

        }
        root.appendChild(students);

        //Students
        Element professors = document.createElement("Professors");

        List<Professor> profList = timetable.getProfessorList();
        profList.sort(Comparator.comparing(Professor::getName));
        for (Professor professor:profList){
            Element prof = document.createElement("professor");
            professors.appendChild(prof);

            Attr attr = document.createAttribute("id");
            attr.setValue(String.valueOf(professor.getId()));
            prof.setAttributeNode(attr);

            Element name = document.createElement("Name");
            name.appendChild(document.createTextNode(professor.getName()));
            prof.appendChild(name);

            Element grad = document.createElement("Grad");
            if(professor.getPrefix() != null)
                grad.appendChild(document.createTextNode(professor.getPrefix()));
            else
                grad.appendChild(document.createTextNode("none"));
            prof.appendChild(grad);

            Element orar = generateProfStudXML(document,null,professor);
            prof.appendChild(orar);

        }
        root.appendChild(professors);



        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource domSource = new DOMSource(document);
        StreamResult streamResult = new StreamResult(new File(path.concat("\\semstudy_export.xml")));


        transformer.transform(domSource, streamResult);

        showAlert("File generated successfully!", Alert.AlertType.INFORMATION);

    }

    public static JSONArray generateAnnouncementJson(List<Announcement> announcementList){
        JSONArray jsonArray = new JSONArray();
        for(Announcement announcement : announcementList) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("Header", announcement.getHeader());
            jsonObject.put("Message", announcement.getMessage());
            jsonObject.put("End", announcement.getEnd());
            jsonArray.add(jsonObject);
        }


        return  jsonArray;
    }

    public static JSONObject generateProfJson(List<Professor> profList){
        List<Assignment> scheduleEvents;
        JSONObject profJson = new JSONObject();
        for (Professor professor : profList){
            scheduleEvents = DBService.getScheduleEventsSorted(professor.getAssignmentList());
            JSONArray details = new JSONArray();
            JSONObject name = new JSONObject();
            JSONArray orar = new JSONArray();

            name.put("Name",professor.getName());
            name.put("ORAR", orar);

            HashMap<Integer,String> days = DBService.addDays();
            for(String day : days.values()) {
                JSONObject dayjs = new JSONObject();
                JSONArray tasks = new JSONArray();
                for (Assignment assignment : scheduleEvents){
                    if(assignment.getDay().equals(day)) {
                        JSONObject ora = new JSONObject();
                        JSONObject oraDetails = new JSONObject();
                        Event event = assignment.getEvent();
                        oraDetails.put("Type", event.getEventType());
                        oraDetails.put("Beginning", assignment.getStartHour().toString());
                        oraDetails.put("Ending",assignment.getEndHour().toString());
                        oraDetails.put("Resources", assignment.getResources());
                        oraDetails.put("Actors", assignment.getRelatedActors());
                        ora.put(event.getName(), oraDetails);
                        tasks.add(ora);
                    }
                }

                dayjs.put(day, tasks);
                orar.add(dayjs);
            }
            if(professor.getPrefix() == null){
                name.put("Grad","");
            }
            else {
                name.put("Grad", professor.getPrefix());
            }

            details.add(name);
//            details.add(orar);
            profJson.put(professor.getId(),details);
        }

        return profJson;
    }

    public static JSONObject generateStudJson(List<Student> studentList){
        List<Assignment> scheduleEvents;
        JSONObject studJson = new JSONObject();
        for (Student student : studentList){
            scheduleEvents = DBService.getScheduleEventsSorted(student.getAssignmentList());
            JSONArray details = new JSONArray();
            JSONObject name = new JSONObject();
            JSONArray orar = new JSONArray();

            name.put("Name",student.getName());
            name.put("ORAR", orar);
            name.put("id", student.getId());


            HashMap<Integer,String> days = DBService.addDays();
            for(String day : days.values()) {
                JSONObject dayjs = new JSONObject();
                JSONArray tasks = new JSONArray();
                for (Assignment assignment : scheduleEvents){
                    if(assignment.getDay().equals(day)) {
                        JSONObject ora = new JSONObject();
                        JSONObject oraDetails = new JSONObject();
                        Event event = assignment.getEvent();
                        oraDetails.put("Type", event.getEventType());
                        oraDetails.put("Beginning", assignment.getStartHour().toString());
                        oraDetails.put("Ending",assignment.getEndHour().toString());
                        oraDetails.put("Resources", assignment.getResources());
                        oraDetails.put("Actors", assignment.getRelatedActors());

                        ora.put(event.getName(), oraDetails);
                        tasks.add(ora);
                    }
                }

                dayjs.put(day, tasks);
                orar.add(dayjs);
            }


            details.add(name);
            studJson.put(student.getAbbr(),details);
        }

        return studJson;
    }


    public static void exportJSON(Timetable timetable, String path) {
        JSONObject jsonObject = new JSONObject();
        List<Announcement> announcements = timetable.getAnnouncementList();
        List<Professor> profList = timetable.getProfessorList();
        profList.sort(Comparator.comparing(Professor::getName));
        JSONObject profJson = generateProfJson(profList);
        jsonObject.put("Professors", profJson);

        List<Student> studentList = timetable.getStudentList();
        studentList.sort(Comparator.comparing(Student::getAbbr));
        JSONObject studJson = generateStudJson(studentList);
        JSONObject announcementJson = new JSONObject();
        jsonObject.put("Students", studJson);
        jsonObject.put("Announcements", generateAnnouncementJson(announcements));
        jsonObject.put("Title", timetable.getTitle());
        jsonObject.put("Domain",timetable.getDomain());
        jsonObject.put("Publish",timetable.getPublish());
        jsonObject.put("BeginDate", timetable.getBeginDate().toString());
        jsonObject.put("EndDate",timetable.getEndDate().toString());


        try (FileWriter file = new FileWriter(path+"\\semestudy_export.json")) {
            file.write(jsonObject.toJSONString());
            showAlert("File generated successfully!", Alert.AlertType.INFORMATION);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showAlert(String error, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle("SEMESTUDY INFO");
        alert.setContentText(error);
        alert.setHeaderText(null);

        alert.showAndWait();
    }
}
