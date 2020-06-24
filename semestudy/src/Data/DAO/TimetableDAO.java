package Data.DAO;

import Data.Database;
import Data.Models.Timetable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TimetableDAO implements Data.DAO.Interfaces.ITimetableDAO{

    static Connection conn = Database.getConnection();

    @Override
    public Timetable getTimetable(int id) throws SQLException {
        String query = "select t.id, t.name, t.publishdate, t.begindate, t.enddate, t.hours, " +
                "t.hourlength, t.title, d.name \"domain\" from timetables t join domains d " +
                "on t.domainid = d.id where t.id = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        boolean found = false;
        Timetable timetable = new Timetable();

        while (resultSet.next()) {
            found = true;
            timetable.setTimetable(resultSet.getInt("ID"),resultSet.getInt("hours"),resultSet.getInt("hourlength"), resultSet.getString("Name"), resultSet.getString("title"), resultSet.getString("publishdate"), resultSet.getDate("begindate").toLocalDate(), resultSet.getDate("enddate").toLocalDate(), resultSet.getString("domain"));
        }
        if (found) {
            return timetable;
        } else
            return null;
    }

    @Override
    public List<Timetable> getTimetables() throws SQLException {
        String query = "select t.id, t.name, t.publishdate, t.begindate, t.enddate, " +
                "t.hours, t.hourlength, t.title, d.name \"domain\" from timetables t " +
                "join domains d on t.domainid = d.id where t.title is not NULL order by t.id desc";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Timetable> timetableList = new ArrayList<Timetable>();

        while (resultSet.next()) {
            Timetable timetable = new Timetable();
            timetable.setTimetable(resultSet.getInt("ID"),resultSet.getInt("hours"),resultSet.getInt("hourlength"), resultSet.getString("Name"), resultSet.getString("title"), resultSet.getString("publishdate"), resultSet.getDate("begindate").toLocalDate(), resultSet.getDate("enddate").toLocalDate(), resultSet.getString("domain"));
            timetableList.add(timetable);
        }
        preparedStatement.close();
        return timetableList;
    }
}
