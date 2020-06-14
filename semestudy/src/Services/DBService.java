package Services;

import Data.DAO.TimetableDAO;
import Data.Models.Actor;
import Data.Models.Assignment;
import Data.Models.Timetable;

import java.sql.SQLException;
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

    private static HashMap<Integer, String> addDays() {
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



}
