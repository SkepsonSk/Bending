package pl.trollcraft.bending.magic.bender;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import pl.trollcraft.bending.Bending;
import pl.trollcraft.bending.help.database.Importer;

public class BenderListener implements Listener {

    @EventHandler
    public void onJoin (PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String playerName = player.getName();

        Importer importer = Bending.getPlugin().getImporter();
        Bender bender;
        if (Bending.getPlugin().isImportEnabled() && !importer.wasImported(playerName)) {
            bender = importer.importBender(playerName);

            if (bender != null) {
                Benders.register(bender);
                return;
            }

        }

        bender = Benders.load(playerName);
        Benders.register(bender);
    }

    @EventHandler
    public void onQuit (PlayerQuitEvent event) {
        Player player = event.getPlayer();
        Bender bender = Benders.find(player);
        Benders.save(bender);
        Benders.dispose(bender);
    }

}
