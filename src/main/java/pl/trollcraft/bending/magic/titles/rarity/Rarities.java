package pl.trollcraft.bending.magic.titles.rarity;

import pl.trollcraft.bending.help.database.DatabaseManager;
import pl.trollcraft.bending.help.locale.Localized;
import pl.trollcraft.bending.magic.preferences.Preference;

import java.sql.SQLException;
import java.util.ArrayList;

public class Rarities {

    private static ArrayList<Rarity> rarities = new ArrayList<>();

    public static void register(Rarity rarity) { rarities.add(rarity); }

    public static Rarity create(String id, Localized name, Localized desc, String lightColor, String darkColor) {
        Rarity rarity = new Rarity(id, name, desc, lightColor, darkColor);
        register(rarity);
        return rarity;
    }

    public static void save(Rarity rarity) {

        String sql = "INSERT INTO bending_rarities VALUES (?,?,?,?,?) ON DUPLICATE KEY UPDATE name=?,descr=?,lightColor=?,darkColor=?";
        DatabaseManager.exec(sql, statement -> {

            String id = rarity.getId();
            String name = rarity.getName().toString();
            String desc = rarity.getDesc().toString();
            String lightColor = rarity.getLightColor();
            String darkColor = rarity.getDarkColor();

            try {

                statement.setString(1, id);
                statement.setString(2, name);
                statement.setString(3, desc);
                statement.setString(4, lightColor);
                statement.setString(5, darkColor);
                statement.setString(6, name);
                statement.setString(7, desc);
                statement.setString(8, lightColor);
                statement.setString(9, darkColor);

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        });

    }

    public static void load() {

        String sql = "SELECT * FROM bending_rarities";
        DatabaseManager.query(sql, res -> {

            String id;
            Localized name;
            Localized desc;
            String lightColor, darkColor;

            try {

                while (res.next()) {
                    id = res.getString("id");
                    name = new Localized();
                    name.fromText(res.getString("name"));
                    desc = new Localized();
                    desc.fromText(res.getString("descr"));
                    lightColor = res.getString("lightColor");
                    darkColor = res.getString("darkColor");

                    register(new Rarity(id, name, desc, lightColor, darkColor));
                }

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        });

    }

    public static Rarity find(String id) {
        for (Rarity r : rarities)
            if (r.getId().equals(id))
                return r;
        return null;
    }

}
