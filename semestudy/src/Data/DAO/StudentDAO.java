package Data.DAO;

import Data.DAO.Interfaces.IStudent;
import Data.Database;
import Data.Models.Professor;
import Data.Models.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO implements IStudent {

    static Connection conn = Database.getConnection();

    @Override
    public Student getStudent(int id) throws SQLException {
        String query = "select id, name, abbr from actors where leaf like 'Y' and stud like 'Y' and prof like 'N' and id = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        boolean found = false;
        Student student = new Student();

        while (resultSet.next()) {
            found = true;
            student.setStudent(resultSet.getInt("ID"), resultSet.getString("name"), resultSet.getString("abbr"));
        }
        preparedStatement.close();
        if (found) {
            return student;
        } else
            return null;
    }

    @Override
    public List<Student> getStudents() throws SQLException {
        String query = "select id, name, abbr from actors where leaf like 'Y' and prof like 'N' and stud like 'Y'";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Student> studentList = new ArrayList<Student>();

        while (resultSet.next()) {
            Student student = new Student();
            student.setStudent(resultSet.getInt("ID"), resultSet.getString("name"), resultSet.getString("abbr"));
            studentList.add(student);
        }

        return studentList;
    }
}
