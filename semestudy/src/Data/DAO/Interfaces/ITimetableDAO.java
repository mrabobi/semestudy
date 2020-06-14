package Data.DAO.Interfaces;

import Data.Models.Timetable;

import java.sql.SQLException;
import java.util.List;

public interface ITimetableDAO {
    public Timetable getTimetable(int id)
        throws SQLException;
    public List<Timetable> getTimetables()
        throws SQLException;

}
