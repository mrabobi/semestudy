package Data.DAO;

import Data.DAO.Interfaces.IAssignment;
import Data.Models.Assignment;
import Data.Database;
import Data.Models.Event;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class AssignmentDAO implements IAssignment {

    static Connection conn = Database.getConnection();

    @Override
    public Assignment getAssignment(int id) throws SQLException {
        return null;
    }

    @Override
    public List<Assignment> getAssignments(int timetableId) throws SQLException {
        String query = "select eventid \"ID\", position, resources from assignments where timetableid = ? order by position";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setInt(1, timetableId);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Assignment> assignments = new ArrayList<Assignment>();
        EventDAO eventDAO = new EventDAO();

        while (resultSet.next()) {
            Assignment assignment = new Assignment();
            Event event = eventDAO.getEvent(resultSet.getInt("ID"));
            assignment.setAssignment(event, resultSet.getInt("position"), resultSet.getString("resources"), "Luni", LocalTime.of(8,0), LocalTime.of(8,0));
            assignments.add(assignment);
        }
        preparedStatement.close();
        return assignments;
    }
}
