package pl.trollcraft.bending.magic.titles.title;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class HoloTitles {

    private static HashMap<Integer, Hologram> holograms = new HashMap<>();

    // -------- -------- ------- ---------

    public static void register(Player player, Hologram hologram) { holograms.put(player.getEntityId(), hologram); }

    public static void unregister(Player player) {
        int id = player.getEntityId();
        if (holograms.containsKey(id)) {
            holograms.get(id).delete();
            holograms.remove(id);
        }
    }

    // -------- -------- ------- ---------

    public static Hologram get(Player player) {
        int id = player.getEntityId();
        if (holograms.containsKey(id))
            return holograms.get(id);
        return null;
    }

}
