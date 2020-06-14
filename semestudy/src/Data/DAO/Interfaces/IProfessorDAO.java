package Data.DAO.Interfaces;

import Data.Models.Professor;

import java.sql.SQLException;
import java.util.List;

public interface IProfessorDAO {
    public Professor getProfessor(int id)
            throws SQLException;

    public List<Professor> getProfessors()
            throws SQLException;
}
