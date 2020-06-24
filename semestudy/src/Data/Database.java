package Data;
import java.sql.*;

public class Database {

    private final String url = "jdbc:oracle:thin:@localhost:1521:xe";

    private static Database instance;
    private static Connection connection;


    private Database(String username, String password) throws SQLException {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException ex) {
            System.out.println("Database Connection Creation Failed : " + ex.getMessage());
        }
    }

    public static Connection getConnection() {
        return connection;
    }

    public static Database getInstance(String username, String password) throws SQLException {
        if (instance == null) {
            instance = new Database(username, password);
        } else if (getConnection().isClosed()) {
            instance = new Database(username, password);
        }
        return instance;
    }


}
