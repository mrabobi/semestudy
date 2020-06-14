package Data.DAO.Interfaces;

import Data.Models.Assignment;

import java.sql.SQLException;
import java.util.List;

public interface IAssignment {
    public Assignment getAssignment(int id)
            throws SQLException;

    public List<Assignment> getAssignments(int timetableId)
            throws SQLException;
}
