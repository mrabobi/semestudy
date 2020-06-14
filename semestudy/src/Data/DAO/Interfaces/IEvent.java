package Data.DAO.Interfaces;

import Data.Models.Event;

import java.sql.SQLException;
import java.util.List;

public interface IEvent {
    public Event getEvent(int id)
            throws SQLException;

    public List<Event> getEvents()
            throws SQLException;
}
