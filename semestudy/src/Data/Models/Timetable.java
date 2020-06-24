package Data.Models;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class Timetable {

    int id, hours, hourLength;
    String name, title, domain,publish;
    LocalDate beginDate, endDate;
    List<Professor> professorList;
    List<Student> studentList;
    List<Announcement> announcementList;


    public Timetable(){
    }

    public void setTimetable(int id, int hours, int hourLength, String name, String title, String publish, LocalDate beginDate, LocalDate endDate, String domain){
        setId(id);
        setHours(hours);
        setHourLength(hourLength);
        setName(name);
        setTitle(title);
        setPublish(publish);
        setBeginDate(beginDate);
        setEndDate(endDate);
        setDomain(domain);

    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public void setHourLength(int hourLength) {
        this.hourLength = hourLength;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPublish(String publish) {
        this.publish = publish;
    }

    public void setBeginDate(LocalDate beginDate) {
        this.beginDate = beginDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public int getHours() { return hours; }

    public String getTitle() { return title; }

    public int getId() { return id; }

    public void setProfessorList(List<Professor> professorList) {
        this.professorList = professorList;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }

    public void setAnnouncementList(List<Announcement> announcementList) {
        this.announcementList = announcementList;
    }

    public List<Professor> getProfessorList() {
        return professorList;
    }

    public List<Announcement> getAnnouncementList() {
        return announcementList;
    }

    public int getHourLength() {
        return hourLength;
    }

    public String getName() {
        return name;
    }

    public String getDomain() {
        return domain;
    }

    public String getPublish() {
        return publish;
    }

    public LocalDate getBeginDate() {
        return beginDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public List<Student> getStudentList() {
        return studentList;
    }

    @Override
    public String toString() {
        return "Timetable{" +
                "id=" + id +
                ", hours=" + hours +
                ", hourLength=" + hourLength +
                ", name='" + name + '\'' +
                ", title='" + title + '\'' +
                ", domain='" + domain + '\'' +
                ", publish='" + publish + '\'' +
                ", beginDate=" + beginDate +
                ", endDate=" + endDate +
                ", professorList=" + professorList +
                ", studentList=" + studentList +
                '}';
    }
}
