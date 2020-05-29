package pl.trollcraft.bending.help.menu;

import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Menus {

    public static ArrayList<Menu> menus = new ArrayList<>();

    public static Menu create(Player player, String locale) {
        Menu menu = new Menu(player, locale);
        menus.add(menu);
        return menu;
    }

    public static Menu prepare(Player player, String locale) {
        Menu menu = find(player);

        if (menu == null) return create(player, locale);
        else menu.setLocale(locale);

        menu.clear();
        return menu;
    }

    public static Menu find(Player player) {
        int id = player.getEntityId();
        for (Menu m : menus)
            if (m.getPlayer().getEntityId() == id)
                return m;
        return null;
    }
}
