package pl.trollcraft.bending.help;

import com.projectkorra.projectkorra.BendingPlayer;
import pl.trollcraft.bending.magic.bender.Bindings;

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

    public void bind(Bindings bindings, String playerName) {
        BendingPlayer bPlayer = BendingPlayer.getBendingPlayer(playerName);
        bPlayer.setAbilities(bindings.getSlots());
    }

    public Bindings getBindings(String playerName) {
        BendingPlayer bPlayer = BendingPlayer.getBendingPlayer(playerName);
        Bindings bindings = new Bindings(bPlayer.getAbilities());
        return bindings;
    }

}
