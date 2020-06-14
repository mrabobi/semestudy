package Data.DAO.Interfaces;

import Data.Models.Actor;

import java.sql.SQLException;

public interface IActor {
    public Actor getActor(int id)
            throws SQLException;

}
