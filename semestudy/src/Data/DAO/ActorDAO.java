package Data.DAO;

import Data.DAO.Interfaces.IActor;
import Data.Database;
import Data.Models.Actor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ActorDAO implements IActor {

    static Connection conn = Database.getConnection();

    @Override
    public Actor getActor(int id) throws SQLException {
        String query = "select a.id \"ID\" , a.name \"NAME\" , a.abbr \"ABBR\", p.name \"PREFIX\", a.leaf \"LEAF\", a.prof \"PROF\", a.stud \"STUD\" from actors a full outer join actorprefixes p on a.prefixid = p.id where a.id = ? and LEAF like 'Y' ";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        boolean found = false;
        Actor actor = new Actor();

        while (resultSet.next()) {
            found = true;
            actor.setActor(resultSet.getInt("ID"), resultSet.getString("NAME") ,resultSet.getString("ABBR"), resultSet.getString("PREFIX"), resultSet.getString("PROF"), resultSet.getString("STUD"));
        }
        preparedStatement.close();
        if (found) {
            return actor;
        } else
            return null;


    }
}
