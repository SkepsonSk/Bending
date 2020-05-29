package pl.trollcraft.bending.help.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.function.Consumer;

public class DatabaseManager {

    private static Database database;

    public static boolean init(Database database) {

        DatabaseManager.database = database;

        try {

            database.open();
            Connection conn = database.connection();

            StringBuilder elementsTable = new StringBuilder();
            elementsTable.append("CREATE TABLE IF NOT EXISTS elements (");
            elementsTable.append("id varchar(32) PRIMARY KEY,");
            elementsTable.append("name text,");
            elementsTable.append("descr text,");
            elementsTable.append("lightColor varchar(4),");
            elementsTable.append("darkColor varchar(4)");
            elementsTable.append(")");

            StringBuilder abilitiesTable = new StringBuilder();
            abilitiesTable.append("CREATE TABLE IF NOT EXISTS abilities (");
            abilitiesTable.append("id varchar(32) PRIMARY KEY,");
            abilitiesTable.append("element varchar(32),");
            abilitiesTable.append("name text,");
            abilitiesTable.append("descr text");
            abilitiesTable.append(")");

            Statement statement = conn.createStatement();

            statement.addBatch(elementsTable.toString());
            statement.addBatch(abilitiesTable.toString());
            statement.executeBatch();

            statement.close();
            database.close();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    // -------- -------- --------

    public static boolean exec(String sql) {

        try {

            database.open();

            Connection conn = database.connection();

            Statement statement = conn.createStatement();
            statement.executeUpdate(sql);

            statement.close();
            database.close();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public static boolean query(String sql, Consumer<ResultSet> call) {

        ResultSet res;

        try {

            database.open();

            Connection conn = database.connection();

            Statement statement = conn.createStatement();
            res = statement.executeQuery(sql);

            call.accept(res);

            statement.close();
            database.close();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

}
