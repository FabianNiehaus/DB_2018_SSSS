package persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

    private Connection connection;
    private String url = "jdbc:mysql://localhost:3306/buzzwordbingodb";
    private String user = "buzz";
    private String password = "bingo";


    public ConnectionManager() throws Exception {
        try {
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connection successful: " + connection);

        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex);
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
