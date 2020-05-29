package pl.trollcraft.bending.help.locale;

import org.bukkit.Bukkit;
import pl.trollcraft.bending.help.Help;

import java.util.HashMap;
import java.util.Map;

public class Localized {

    private HashMap<String, String> locales;

    public Localized() {
        locales = new HashMap<>();
    }

    public Localized locale(String locale, String text){
        locales.put(locale, text);
        return this;
    }

    public Localized before(String text) {
        locales.keySet().forEach( key -> {
            String org = locales.get(key);
            locales.replace(key, text + org);
        } );
        return this;
    }

    public String determineLocale(String text) {
        for (Map.Entry<String, String> e : locales.entrySet() ) {
            Bukkit.getConsoleSender().sendMessage(text + " ?= " + e.getValue());
            if (e.getValue().equalsIgnoreCase(text)) return e.getKey();
        }
        return null;
    }

    public String text(String locale) { return Help.color(locales.get(locale)); }

    public void fromText(String localized) {

        this.locales.clear();
        String[] locales = localized.split("#");

        for (String s : locales) {
            String[] loc = s.split("@");
            this.locales.put(loc[0], loc[1]);
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        locales.forEach( (k, v) -> {
            builder.append(k + "@" + v);
            builder.append('#');
        } );

        String string = builder.toString();
        return string.substring(0, string.length() - 1);
    }

}
