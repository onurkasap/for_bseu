import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestConnection {
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/lastikcim", "root", "1603");
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}