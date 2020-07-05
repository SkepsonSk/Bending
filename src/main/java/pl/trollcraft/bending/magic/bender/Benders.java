package pl.trollcraft.bending.magic.bender;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.redisson.api.RList;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import pl.trollcraft.bending.help.database.DatabaseManager;
import java.util.ArrayList;

public class Benders {

    private static final ArrayList<Bender> benders = new ArrayList<>();

    public static void register(Bender bender) { benders.add(bender); }
    public static void dispose(Bender bender) { benders.remove(bender); }

    public static Bender find(String name) {
        for (Bender b : benders)
            if (b.getName().equals(name))
                return b;
        return null;
    }

    /**
     * @deprecated Please use Benders#find(String) instead.
     */
    @Deprecated
    public static Bender find(Player player) {
        String name = player.getName();
        return find(name);
    }

    public static void save(Bender bender) {
        RedissonClient client = DatabaseManager.getRedisson();
        String playerName = bender.getName();

        RList<String> abilities = client.getList("benders/" + playerName + "/abilities");
        abilities.clear();
        abilities.addAll(bender.exportAbilities());

        RMap<Integer, String> bindings = client.getMap("benders/" + playerName + "/bindings");
        bindings.putAll(bender.getBindings().getSlots());
    }

    public static Bender load(String playerName) {
        Bender bender = new Bender(playerName);

        RedissonClient client = DatabaseManager.getRedisson();

        RList<String> abilities = client.getList("benders/" + playerName + "/abilities");
        bender.importAbilities(abilities);

        RMap<Integer, String> bindings = client.getMap("benders/" + playerName + "/bindings");

        bindings.forEach( (i, s) -> Bukkit.getConsoleSender().sendMessage(i + " " + s) );

        bender.getBindings().setSlots(bindings);

        return bender;
    }

}
