package pl.knowak91;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static Connection connection = null;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection("jdbc:sqlite:db/tasks.db");
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
        return connection;
    }
}
