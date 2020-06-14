package Data.DAO;

import Data.Database;
import Data.Models.Professor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProfessorDAO implements Data.DAO.Interfaces.IProfessorDAO {

    static Connection conn = Database.getConnection();

    @Override
    public Professor getProfessor(int id) throws SQLException {
        String query = "select a.id, a.name, a.abbr, d.name \"domain\"," +
                " p.name \"prefix\" from actors a JOIN domains d on  a.domainid = d.id" +
                " join actorprefixes p on a.prefixid = p.id where a.id = ? and a.leaf like 'Y' and a.stud like 'N' and a.prof like 'Y'";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        boolean found = false;
        Professor professor = new Professor();

        while (resultSet.next()) {
            found = true;
            professor.setProfessor(resultSet.getInt("ID"), resultSet.getString("Name"), resultSet.getString("ABBR"), resultSet.getString("prefix"));
        }
        if (found) {
            return professor;
        } else
            return null;
    }

    @Override
    public List<Professor> getProfessors() throws SQLException {
            String query = "select a.id, a.name, a.abbr, d.name \"domain\"," +
                    " p.name \"prefix\" from actors a JOIN domains d on  a.domainid = d.id" +
                    " join actorprefixes p on a.prefixid = p.id";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Professor> professorList = new ArrayList<Professor>();

            while (resultSet.next()) {
                Professor professor = new Professor();
                professor.setProfessor(resultSet.getInt("ID"), resultSet.getString("Name"), resultSet.getString("ABBR"), resultSet.getString("prefix"));
                professorList.add(professor);
            }
            preparedStatement.close();
            return professorList;
    }
}
