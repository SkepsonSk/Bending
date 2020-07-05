package pl.trollcraft.bending.magic.bender;

import com.projectkorra.projectkorra.BendingPlayer;
import com.projectkorra.projectkorra.event.BendingPlayerCreationEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import pl.trollcraft.bending.Bending;
import pl.trollcraft.bending.help.KorraLinker;
import pl.trollcraft.bending.help.database.Importer;

public class KorraBenderListener implements Listener {

    @EventHandler
    public void onJoin (BendingPlayerCreationEvent event) {
        BendingPlayer player = event.getBendingPlayer();
        String playerName = player.getName();

        Importer importer = Bending.getPlugin().getImporter();
        Bender bender;
        if (Bending.getPlugin().isImportEnabled() && !importer.wasImported(playerName)) {
            bender = importer.importBender(playerName);

            if (bender != null) {
                KorraLinker instance = KorraLinker.getInstance();
                instance.load(playerName);
                instance.clear(playerName);
                bender.getBindings().getSlots().forEach( (s, a) -> {
                    Bukkit.getConsoleSender().sendMessage(s + " " + a);
                    instance.bind(s, a, playerName);
                } );
                Benders.register(bender);
                return;
            }

        }

        bender = Benders.load(playerName);

        KorraLinker instance = KorraLinker.getInstance();
        instance.load(playerName);
        instance.clear(playerName);
        bender.getBindings().getSlots().forEach( (s, a) -> {
            Bukkit.getConsoleSender().sendMessage(s + " " + a);
            instance.bind(s, a, playerName);
        } );

        Benders.register(bender);
    }

    @EventHandler
    public void onQuit (PlayerQuitEvent event) {
        Player player = event.getPlayer();
        Bender bender = Benders.find(player);
        Benders.save(bender);
        Benders.dispose(bender);
    }

    private void bind() {

    }

}
