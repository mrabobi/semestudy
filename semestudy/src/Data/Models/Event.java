package Data.Models;

public class Event {
    int id, duration;
    String abbr, name, relatedActorsId, eventType;


    public void setEvent(int id, int duration, String abbr, String name, String relatedActorsId, String eventType) {
        this.id = id;
        this.duration = duration;
        this.abbr = abbr;
        this.name = name;
        this.relatedActorsId = relatedActorsId;
        this.eventType = eventType;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public int getDuration() {
        return duration;
    }

    public String getAbbr() {
        return abbr;
    }

    public String getRelatedActorsId() {
        return relatedActorsId;
    }

    public String getEventType() {
        return eventType;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", duration=" + duration +
                ", abbr='" + abbr + '\'' +
                ", name='" + name + '\'' +
                ", eventType='" + eventType + '\'' +
                '}';
    }
}
