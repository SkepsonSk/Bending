package pl.trollcraft.bending.magic.preferences;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import pl.trollcraft.bending.events.preferences.PreferenceLoadEvent;

public class PreferencesListener implements Listener {

    @EventHandler
    public void onJoin (PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String playerName = player.getName();
        Preference p = Preferences.load(playerName);
        Preferences.register(p);

        Bukkit.getPluginManager().callEvent(new PreferenceLoadEvent(player , p));
    }

    @EventHandler
    public void onQuit (PlayerQuitEvent event) {
        Player player = event.getPlayer();
        String playerName = player.getName();
        Preference p = Preferences.find(playerName);
        Preferences.save(p);
        Preferences.dispose(p);
    }

}
