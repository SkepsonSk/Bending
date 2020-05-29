package pl.trollcraft.bending.magic.ability;

import pl.trollcraft.bending.help.database.DatabaseManager;
import pl.trollcraft.bending.help.locale.Localized;
import pl.trollcraft.bending.magic.element.Element;
import pl.trollcraft.bending.magic.element.Elements;

import java.sql.SQLException;
import java.util.ArrayList;

public class Abilities {

    private static final ArrayList<Ability> abilities = new ArrayList<>();

    public static void register(Ability ability) { abilities.add(ability); }

    public static Ability create(String id, Element element,
                                 Localized name, Localized desc) {

        Ability ability = new Ability(id, element, name, desc);
        register(ability);
        return ability;
    }

    public static Ability find(String id) {
        for (Ability a : abilities)
            if (a.getId().equals(id))
                return a;
        return null;
    }

    public static void save(Ability ability) {
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO abilities VALUES (");
        sql.append("'" + ability.getId() + "',");
        sql.append("'" + ability.getElement().getId() + "',");
        sql.append("'" + ability.getName().toString() + "',");
        sql.append("'" + ability.getDesc().toString() + "')");
        sql.append("ON DUPLICATE KEY UPDATE ");
        sql.append("element='" + ability.getElement().getId() + "',");
        sql.append("name='" + ability.getName().toString() + "',");
        sql.append("descr='" + ability.getDesc().toString() + "'");

        DatabaseManager.exec(sql.toString());
    }

    public static void load(){

        DatabaseManager.query("SELECT * FROM abilities", res -> {

            String id;
            Element element;
            Localized name, desc;

            try {

                while (res.next()) {
                    id = res.getString("id");
                    element = Elements.find(res.getString("element"));
                    name = new Localized();
                    desc = new Localized();

                    name.fromText(res.getString("name"));
                    desc.fromText(res.getString("descr"));

                    create(id, element, name, desc);
                }

            }
            catch (SQLException e) {
                e.printStackTrace();
            }

        });

    }

}
