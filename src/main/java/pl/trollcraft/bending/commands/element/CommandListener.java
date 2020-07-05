package pl.trollcraft.bending.commands.element;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import pl.trollcraft.bending.events.PlayerMoveEvent;
import pl.trollcraft.bending.events.preferences.PreferenceChangeEvent;
import pl.trollcraft.bending.help.menu.Menu;
import pl.trollcraft.bending.help.menu.Menus;
import pl.trollcraft.bending.magic.element.Element;
import pl.trollcraft.bending.magic.element.Elements;
import pl.trollcraft.bending.magic.preferences.Preference;
import pl.trollcraft.bending.magic.preferences.Preferences;

public class CommandListener implements Listener {

    @EventHandler
    public void onCommand (PlayerCommandPreprocessEvent event) {
        String element = event.getMessage();
        Elements.LocalizedElement le = Elements.findByName(element.substring(1));

        if (le == null) return;

        Player player = event.getPlayer();
        String playerName = player.getName();
        Element e = le.element;
        String locale = le.locale;

        ElementCommand.menu(e, player, locale);

        Preference p = Preferences.find(playerName);

        if (!p.getLocale().equals(locale)) {
            p.setLocale(locale);
            Bukkit.getPluginManager().callEvent(new PreferenceChangeEvent(player, p, PreferenceChangeEvent.Field.LOCALE));
        }

        event.setCancelled(true);
    }

    @EventHandler
    public void onMove (PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Menu menu = Menus.find(player);
        if (menu != null)
            menu.close();
    }

    @EventHandler
    public void onChat (AsyncPlayerChatEvent event) {

        Player player = event.getPlayer();
        if (Menus.find(player) != null) event.setCancelled(true);

        Bukkit.getOnlinePlayers().stream()
                .filter(p -> Menus.find(p) != null)
                .forEach(p -> event.getRecipients().remove(p));
    }

}
