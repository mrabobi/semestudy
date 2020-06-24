package Data.DAO;

import Data.DAO.Interfaces.IAnnouncement;
import Data.Database;
import Data.Models.Announcement;
import Data.Models.Professor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AnnouncementDAO implements IAnnouncement {

    static Connection conn = Database.getConnection();

    @Override
    public List<Announcement> getAnnouncements(int timetableId) throws SQLException {
        String query = "select header, message, expiredate from announcements where timetableid = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setInt(1, timetableId);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Announcement> announcements = new ArrayList<Announcement>();

        while (resultSet.next()) {
            Announcement announcement = new Announcement();
            announcement.setAnnouncement(resultSet.getString("header"), resultSet.getString("message"), resultSet.getString("expiredate"));
            announcements.add(announcement);
        }
        preparedStatement.close();
        return announcements;
    }
}
