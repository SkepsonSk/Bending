package pl.trollcraft.bending.help.menu;

import org.bukkit.entity.Player;
import pl.trollcraft.bending.help.Help;
import pl.trollcraft.bending.help.locale.Localized;
import pl.trollcraft.bending.help.menu.components.Component;
import pl.trollcraft.bending.magic.element.Element;

import java.util.ArrayList;
import java.util.Locale;

public class Menu {

    private Player player;
    private ArrayList<Component> components;
    private String locale;

    public Menu (Player player, String locale) {
        this.player = player;
        this.locale = locale;
        this.components = new ArrayList<>();
    }

    public void setLocale(String locale) { this.locale = locale; }

    public void add(Component component) { components.add(component); }
    public void addIf(Component component, boolean condition) { if (condition) components.add(component); }

    public void clear() { components.clear(); }

    public Player getPlayer() { return player; }
    public String getLocale() { return locale; }

    public void print() {
        Help.clearChat(player);
        components.forEach(c -> c.print(player, locale) );
    }

    public void close() {
        Menus.unregister(this);
        Help.clearChat(player);
        player.sendMessage(new Localized()
            .locale("pl", "&cZamknieto &c&lMENU.")
            .locale("en", "&cThe menu &c&lhas been closed.")
            .text(locale)
        );
    }

}
