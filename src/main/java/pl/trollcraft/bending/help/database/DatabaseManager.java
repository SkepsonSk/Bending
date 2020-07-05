package pl.trollcraft.bending.help.database;

import org.redisson.api.RedissonClient;

import java.sql.*;
import java.util.function.Consumer;

public class DatabaseManager {

    private static Database database;
    private static RedissonClient redisson;

    public static boolean init(Database database, RedissonClient redisson) {

        DatabaseManager.database = database;
        DatabaseManager.redisson = redisson;

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

            StringBuilder raritiesTable = new StringBuilder();
            raritiesTable.append("CREATE TABLE IF NOT EXISTS bending_rarities (");
            raritiesTable.append("id varchar(32) PRIMARY KEY,");
            raritiesTable.append("name text,");
            raritiesTable.append("descr text,");
            raritiesTable.append("lightColor varchar(4),");
            raritiesTable.append("darkColor varchar(4)");
            raritiesTable.append(")");

            StringBuilder titlesTable = new StringBuilder();
            titlesTable.append("CREATE TABLE IF NOT EXISTS bending_titles (");
            titlesTable.append("id varchar(32) PRIMARY KEY,");
            titlesTable.append("name text,");
            titlesTable.append("descr text,");
            titlesTable.append("rarity varchar(32)");
            titlesTable.append(")");

            Statement statement = conn.createStatement();

            statement.addBatch(elementsTable.toString());
            statement.addBatch(abilitiesTable.toString());
            statement.addBatch(raritiesTable.toString());
            statement.addBatch(titlesTable.toString());
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

    public static boolean exec(String sql, Consumer<PreparedStatement> call) {

        try {

            database.open();
            Connection conn = database.connection();
            conn.setAutoCommit(false);

            PreparedStatement st = conn.prepareStatement(sql);
            call.accept(st);
            st.executeUpdate();
            conn.commit();
            database.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
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

    // -------- -------- --------

    public static RedissonClient getRedisson() { return redisson; }

}
