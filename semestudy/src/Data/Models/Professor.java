package Data.Models;

import java.util.LinkedList;
import java.util.List;

public class Professor {
    int id;
    String name;
    String abbr;
    String prefix;
    List<Assignment> assignmentList;

    public Professor() {
    }

    public void setProfessor(int id, String name, String abbr, String prefix) {
        this.id = id;
        this.name = name;
        this.abbr = abbr;
        this.prefix = prefix;
        this.assignmentList = new LinkedList<>();
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public void setAbbr(String abbr) {
        this.abbr = abbr;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public void setAssignment(Assignment assignment){
        assignmentList.add(assignment);
    }

    public Professor(int id, String domain, String name, String abbr, String prefix, List<Assignment> assignmentList) {
        this.id = id;
        this.name = name;
        this.abbr = abbr;
        this.prefix = prefix;
        this.assignmentList = assignmentList;
    }

    public int getId() {
        return id;
    }

    public String getAbbr() {
        return abbr;
    }

    public String getPrefix() {
        return prefix;
    }

    public List<Assignment> getAssignmentList() {
        return assignmentList;
    }

    @Override
    public String toString() {
        String assignmentString = "";
        if(assignmentList.isEmpty())
            assignmentString = "No assignments";
        else {
            for (Assignment a : assignmentList) {
                assignmentString = assignmentString + " " + a.toString();
            }
        }


        return "Professor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", abbr='" + abbr + '\'' +
                ", prefix='" + prefix + '\'' +
                ", assignmentList=" + assignmentString +
                '}';
    }
}
