package Services;

import Data.DAO.ActorDAO;
import Data.DAO.AssignmentDAO;
import Data.DAO.TimetableDAO;
import Data.Models.*;

import java.sql.SQLException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DBService {

    public static Timetable getTimetable(int id) throws SQLException {
        TimetableDAO timetableDAO = new TimetableDAO();
        Timetable timetable = timetableDAO.getTimetable(id);

        return timetable;
    }

    public static List<String> generateActorLists(String resources) {
        List<String> resourceList = new LinkedList<>();
        Pattern p = Pattern.compile("\\d+");
        Matcher m = p.matcher(resources);
        while (m.find()) {
            resourceList.add(m.group());
        }

        if(resourceList.isEmpty()){
            resourceList.add("null");
        }
        return resourceList;
    }

    public static boolean isProfessor(Actor actor) {
        return actor.getProf().equals("Y") && actor.getStud().equals("N");
    }

    public static boolean isStudent(Actor actor) {
        return actor.getStud().equals("Y") && actor.getProf().equals("N");
    }

    public static HashMap<Integer, String> addDays() {
        HashMap<Integer,String> days = new HashMap<>();

        days.put(1, "Luni");
        days.put(2, "Marti");
        days.put(3, "Miercuri");
        days.put(4, "Joi");
        days.put(5, "Vineri");
        days.put(6, "Sambata");
        days.put(7, "Duminica");

        return days;
    }

    public static List<Assignment> setDateTime(int hours, List<Assignment> assignmentList) {
        HashMap<Integer,String> days;
        days = addDays();
        for(Assignment a : assignmentList){
            a.setDay(days.get(1 + a.getPosition() / hours));
            a.setStartHour(a.getStartHour().plusHours((a.getPosition() % hours)));
            a.setEndHour(a.getStartHour().plusHours(a.getEventDuration()));
        }
        return assignmentList;

    }

    public static Timetable generateTimetable(int semesterId){

        HashMap<Integer, Professor> professorHashMap = new HashMap<>();
        HashMap<Integer, Student> studentHashMap = new HashMap<>();

        try {

            TimetableDAO timetableDAO = new TimetableDAO();
            Timetable primaryTimetable = timetableDAO.getTimetable(semesterId);

            AssignmentDAO assignmentDAO = new AssignmentDAO();
            ActorDAO actorDAO = new ActorDAO();
            List<Assignment> assignmentList = assignmentDAO.getAssignments(semesterId);
            assignmentList = DBService.setDateTime(primaryTimetable.getHours(), assignmentList);

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
                return null;
            }
            else {
                primaryTimetable.setProfessorList(new LinkedList<>(professorHashMap.values()));
                primaryTimetable.setStudentList(new LinkedList<>(studentHashMap.values()));
                return primaryTimetable;
            }

        } catch (SQLException err) {
            System.out.println(err.getSQLState());
        }
        return null;
    }

    public static List<Assignment> getScheduleEventsSorted(List<Assignment> assignmentList) {
        List<Assignment> assignments = new LinkedList<>();
        HashMap<Integer,String> days = addDays();

        for (String day : days.values())
            assignments.addAll(getAssignmentsByDayOrdered(assignmentList, day));
        return assignments;
    }

    private static List<Assignment> getAssignmentsByDayOrdered(List<Assignment> assignmentList, String day) {
        List<Assignment> assignments = new LinkedList<>();

        for (Assignment assignment : assignmentList) {
            if(assignment.getDay() == day)
                assignments.add(assignment);
        }

        assignments.sort(Comparator.comparing(Assignment::getStartHour));
        return assignments;
    }
}
