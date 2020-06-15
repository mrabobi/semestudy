package Data.Models;

import java.util.LinkedList;
import java.util.List;

public class Student {
    int id;
    String name, abbr;
    List<Assignment> assignmentList;

    public void setStudent(int id, String name, String abbr){
        this.id = id;
        this.name = name;
        this.abbr = abbr;
        this.assignmentList = new LinkedList<>();
    }

    public int getId() {
        return id;
    }

    public Student() {
    }

    public void addAssignment(Assignment assignment){
        assignmentList.add(assignment);
    }

    public Student(int id, String name, String abbr, List<Assignment> assignmentList) {
        this.id = id;
        this.name = name;
        this.abbr = abbr;
        this.assignmentList = assignmentList;
    }

    public String getName() {
        return name;
    }

    public String getAbbr() {
        return abbr;
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



        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", abbr='" + abbr + '\'' +
                ", assignmentList=" + assignmentString +
                '}';
    }
}
