package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {

    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String HOST = "jdbc:mysql://localhost:3306/myschemakata";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public static Connection getNewConnection() {
        Connection connection;

        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(HOST, USER, PASSWORD);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return connection;
    }
}
