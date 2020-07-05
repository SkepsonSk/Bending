package pl.trollcraft.bending.magic.titles.titleuser;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class TitleUserListener implements Listener {

    @EventHandler
    public void onJoin (PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String playerName = player.getName();
        TitleUsers.load(playerName);
    }

    @EventHandler
    public void onQuit (PlayerQuitEvent event) {
        Player player = event.getPlayer();
        String playerName = player.getName();
        TitleUser titleUser = TitleUsers.find(playerName);
        if (titleUser != null) {
            TitleUsers.save(titleUser);
            TitleUsers.dispose(titleUser);
        }
    }

}
