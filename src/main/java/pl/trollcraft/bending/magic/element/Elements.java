package pl.trollcraft.bending.magic.element;

import org.bukkit.Bukkit;
import pl.trollcraft.bending.help.database.DatabaseManager;
import pl.trollcraft.bending.help.locale.Localized;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Elements {

    public static class LocalizedElement {

        public LocalizedElement(Element element, String locale){
            this.element = element;
            this.locale = locale;
        }

        public Element element;
        public String locale;

    }

    private static final ArrayList<Element> elements = new ArrayList<>();

    // -------- -------- --------

    public static void register(Element element) { elements.add(element); }

    public static Element create(String id, Localized name, Localized desc,
                                 String lightColor, String darkColor) {

        Element element = new Element(id, name, desc, lightColor, darkColor);
        register(element);
        return element;
    }

    // -------- -------- --------

    public static Element find(String id) {
        for (Element e : elements)
            if (e.getId().equals(id))
                return e;
        return null;
    }

    public static LocalizedElement findByName(String name) {
        String locale;
        for (Element e : elements) {
            locale = e.getName().determineLocale(name);
            if (locale != null) {
                LocalizedElement le = new LocalizedElement(e, locale);
                return le;
            }
        }
        return null;
    }

    public static void save(Element element) {
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO elements VALUES (");
        sql.append("'" + element.getId() + "',");
        sql.append("'" + element.getName().toString() + "',");
        sql.append("'" + element.getDesc().toString() + "',");
        sql.append("'" + element.getLightColor() + "',");
        sql.append("'" + element.getDarkColor() + "') ");
        sql.append("ON DUPLICATE KEY UPDATE ");
        sql.append("name='" + element.getName().toString() + "', ");
        sql.append("descr='" + element.getDesc().toString() + "', ");
        sql.append("lightColor='" + element.getLightColor()+ "', ");
        sql.append("darkColor='" + element.getDarkColor()+ "'");

        DatabaseManager.exec(sql.toString());
    }

    public static void load(){

        DatabaseManager.query("SELECT * FROM elements", res -> {

            String id;
            Localized name, desc;
            String lightColor, darkColor;

            try {

                while (res.next()) {
                    id = res.getString("id");
                    name = new Localized();
                    desc = new Localized();

                    name.fromText(res.getString("name"));
                    desc.fromText(res.getString("descr"));

                    lightColor = res.getString("lightColor");
                    darkColor = res.getString("darkColor");

                    create(id, name, desc, lightColor, darkColor);
                }

            }
            catch (SQLException e) {
                e.printStackTrace();
            }

        });

    }

}
