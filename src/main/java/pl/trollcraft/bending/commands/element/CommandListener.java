package pl.trollcraft.bending.commands.element;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import pl.trollcraft.bending.magic.element.Element;
import pl.trollcraft.bending.magic.element.Elements;

public class CommandListener implements Listener {

    @EventHandler
    public void onCommand (PlayerCommandPreprocessEvent event) {
        String element = event.getMessage();
        Elements.LocalizedElement le = Elements.findByName(element.substring(1));

        if (le == null) return;

        Player player = event.getPlayer();
        Element e = le.element;
        String locale = le.locale;

        ElementCommand.menu(e, player, locale);
        event.setCancelled(true);
    }

}
