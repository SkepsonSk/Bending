package pl.trollcraft.bending.magic.bender;

import org.bukkit.entity.Player;

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

    public static Bender find(Player player) {
        String name = player.getName();
        return find(name);
    }

}
