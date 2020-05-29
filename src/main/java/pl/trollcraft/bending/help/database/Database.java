package pl.trollcraft.bending.help.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private Connection connection;
    private String host, database, username, password;
    private int port;

    public Database (String host, int port, String database, String username, String password) {
        this.host = host;
        this.port = port;
        this.database = database;
        this.username = username;
        this.password = password;
    }

    // -------- -------- -------- --------

    public void open() throws SQLException {
        if (connection != null && !connection.isClosed()) return;

        synchronized (this) {
            if (connection != null && !connection.isClosed()) {
                return;
            }

            connection = DriverManager
                    .getConnection("jdbc:mysql://" + this.host+ ":" +
                            this.port + "/" + this.database, this.username, this.password);
        }
    }

    public void close() throws SQLException {

        if (connection == null || connection.isClosed()) return;

        synchronized (this) {
            try {

                if (connection!=null && !connection.isClosed())
                    connection.close();

            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    // -------- -------- --------- --------


    public Connection connection() { return connection; }
}
