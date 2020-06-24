package Data.DAO.Interfaces;

import Data.Models.Announcement;
import Data.Models.Assignment;

import java.sql.SQLException;
import java.util.List;

public interface IAnnouncement {
    public List<Announcement> getAnnouncements(int timetableId)
            throws SQLException;
}
