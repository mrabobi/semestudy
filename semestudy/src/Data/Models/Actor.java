package Data.Models;

public class Actor {
    int id;
    String name, abbr, prefix, prof, stud;

    public void setActor(int id, String name, String abbr, String prefix, String prof, String stud) {
        this.id = id;
        this.name = name;
        this.abbr = abbr;
        this.prefix = prefix;
        this.prof = prof;
        this.stud = stud;
    }

    public String getProf() {
        return prof;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAbbr() {
        return abbr;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getStud() {
        return stud;
    }

    @Override
    public String toString() {
        return "Actor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", abbr='" + abbr + '\'' +
                ", prefix='" + prefix + '\'' +
                ", prof='" + prof + '\'' +
                ", stud='" + stud + '\'' +
                '}';
    }
}
