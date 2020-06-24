package Data.Models;

public class Announcement {
    String header, message, end;

    public void setAnnouncement(String header, String message, String expiredate) {
        this.header = header;
        this.message = message;
        this.end = expiredate;
    }

    public String getHeader() {
        return header;
    }

    public String getMessage() {
        return message;
    }

    public String getEnd() {
        return end;
    }
}
