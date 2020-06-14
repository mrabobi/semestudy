package Data.DAO;

import Data.DAO.Interfaces.IEvent;
import Data.Models.Event;
import Data.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class EventDAO implements IEvent {

    static Connection conn = Database.getConnection();

    @Override
    public Event getEvent(int id) throws SQLException {
        String query = "SELECT e.id, t.name \"TYPE\", e.abbr, e.name \"NAME\", e.duration, e.relatedactorids FROM events e JOIN eventtypes t ON e.eventtypeid = t.id where e.id = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        boolean found = false;
        Event event = new Event();

        while (resultSet.next()) {
            found = true;
            event.setEvent(resultSet.getInt("ID"),resultSet.getInt("DURATION"), resultSet.getString("ABBR"), resultSet.getString("NAME"),  resultSet.getString("relatedactorids"), resultSet.getString("TYPE"));
        }
        preparedStatement.close();
        if (found) {
            return event;
        } else
            return null;
    }

    @Override
    public List<Event> getEvents() throws SQLException {
        return null;
    }
}
