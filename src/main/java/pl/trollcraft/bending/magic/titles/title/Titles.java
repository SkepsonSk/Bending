package pl.trollcraft.bending.magic.titles.title;

import pl.trollcraft.bending.help.database.DatabaseManager;
import pl.trollcraft.bending.help.locale.Localized;
import pl.trollcraft.bending.magic.titles.rarity.Rarities;
import pl.trollcraft.bending.magic.titles.rarity.Rarity;

import java.sql.SQLException;
import java.util.ArrayList;

public class Titles {

    private static ArrayList<Title> titles = new ArrayList<>();

    public static void register(Title title) { titles.add(title); }

    public static Title create(String id, Localized name, Localized desc, Rarity rarity) {
        Title title = new Title(id, name, desc, rarity);
        register(title);
        return title;
    }

    // -------- -------- -------- --------

    public static void save(Title title) {

        String sql = "INSERT INTO bending_titles VALUES (?,?,?,?) ON DUPLICATE KEY UPDATE name=?,descr=?,rarity=?";
        DatabaseManager.exec(sql, statement -> {

            String id = title.getId();
            String name = title.getName().toString();
            String desc = title.getDesc().toString();
            String rarity = title.getRarity().getId();

            try {

                statement.setString(1, id);
                statement.setString(2, name);
                statement.setString(3, desc);
                statement.setString(4, rarity);
                statement.setString(5, name);
                statement.setString(6, desc);
                statement.setString(7, rarity);

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        });

    }

    public static void load() {

        String sql = "SELECT * FROM bending_titles";
        DatabaseManager.query(sql, res -> {

            String id;
            Localized name;
            Localized desc;
            Rarity rarity;

            try {

                while (res.next()) {

                    id = res.getString("id");
                    name = new Localized();
                    name.fromText(res.getString("name"));
                    desc = new Localized();
                    desc.fromText(res.getString("descr"));
                    rarity = Rarities.find(res.getString("rarity"));

                    register(new Title(id, name, desc, rarity));

                }

            } catch (SQLException exception) {
                exception.printStackTrace();
            }

        });

    }

    // -------- -------- -------- --------

    public static Title find(String id) {
        for (Title title : titles)
            if (title.getId().equals(id))
                return title;
        return null;
    }

}
