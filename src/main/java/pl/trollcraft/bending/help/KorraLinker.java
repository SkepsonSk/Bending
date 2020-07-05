package pl.trollcraft.bending.help;

import com.projectkorra.projectkorra.BendingPlayer;
import com.projectkorra.projectkorra.GeneralMethods;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.trollcraft.bending.magic.bender.Bender;
import pl.trollcraft.bending.magic.bender.Bindings;

import java.util.HashMap;

/**
 * A link class providing clean connection
 * with ProjectKorra plugin.
 *
 * @author Jakub Zelmanowicz
 */
public class KorraLinker {

    private static KorraLinker instance;

    // -------- -------- --------

    public static KorraLinker getInstance() {
        if (instance == null)
            instance = new KorraLinker();
        return instance;
    }

    public static boolean isOn() {
        return instance != null;
    }

    // -------- -------- --------

    public void load(String playerName) {
        Player player = Bukkit.getPlayer(playerName);
        BendingPlayer bPlayer = BendingPlayer.getBendingPlayer(playerName);
        if (bPlayer == null) GeneralMethods.createBendingPlayer(player.getUniqueId(), playerName);
    }

    public void bind(int slot, String abilityName, String playerName) {
        Player player = Bukkit.getPlayer(playerName);
        GeneralMethods.bindAbility(player, abilityName, slot);
    }

    public void clear(String playerName) {
        BendingPlayer bPlayer = BendingPlayer.getBendingPlayer(playerName);
        bPlayer.getAbilities().clear();
        for (int slot = 1 ; slot <= 9 ; slot++)
            GeneralMethods.saveAbility(bPlayer, slot, null);
    }

    public Bindings getBindings(String playerName) {
        BendingPlayer bPlayer = BendingPlayer.getBendingPlayer(playerName);
        Bindings bindings = new Bindings(bPlayer.getAbilities());
        return bindings;
    }



}
