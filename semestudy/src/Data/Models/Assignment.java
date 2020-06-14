package Data.Models;

import java.time.LocalTime;

public class Assignment {

    Event event;
    int position;
    String resources, day;
    LocalTime startHour, endHour;


    public void setAssignment(Event event, int position, String resources, String day, LocalTime startHour, LocalTime endHour) {
        this.event = event;
        this.position = position;
        this.resources = resources;
        this.day = day;
        this.startHour = startHour;
        this.endHour = endHour;
    }

    public String getResources() {
        return resources;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setStartHour(LocalTime startHour) {
        this.startHour = startHour;
    }

    public void setEndHour(LocalTime endHour) {
        this.endHour = endHour;
    }

    public int getEventId(){
        return event.id;
    }

    public int getPosition() {
        return position;
    }

    public LocalTime getStartHour() {
        return startHour;
    }

    public LocalTime getEndHour() {
        return endHour;
    }

    public int getEventDuration(){
        return event.duration;
    }

    public String getEventRelatedActorsId(){
        return event.relatedActorsId;
    }

    @Override
    public String toString() {
        return "Assignment{" +
                "event=" + event.toString() +
                ", position=" + position +
                ", resources='" + resources + '\'' +
                ", day='" + day + '\'' +
                ", startHour='" + startHour.toString() + '\'' +
                ", endHour='" + endHour.toString() + '\'' +
                '}';
    }
}
