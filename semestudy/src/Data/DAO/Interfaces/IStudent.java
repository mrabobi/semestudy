package Data.DAO.Interfaces;

import Data.Models.Student;

import java.sql.SQLException;
import java.util.List;

public interface IStudent {
    public Student getStudent(int id)
        throws SQLException;

    public List<Student> getStudents()
        throws SQLException;
}
