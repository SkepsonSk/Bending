package pl.trollcraft.bending.help;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import pl.trollcraft.bending.events.PlayerMoveEvent;

import java.util.HashMap;
import java.util.function.Predicate;

public class MoveDetector {

    /**
     * Sums of x, y and z of player's location.
     */
    private HashMap<Player, Integer> movements;

    /**
     * Last locations of player before detection.
     */
    private HashMap<Player, Location> lastLocations;

    private Plugin plugin;
    private BukkitTask task;

    /**
     * A condition that must be met for
     * detector to observe player.
     */
    private Predicate<Player> condition;

    // -------- -------- -------- --------

    public MoveDetector(Plugin plugin, Predicate<Player> condition) {
        movements = new HashMap<>();
        lastLocations = new HashMap<>();
        this.plugin = plugin;
        this.condition = condition;
        startDetecting();
    }

    private void startDetecting() {

        task = new BukkitRunnable() {

            @Override
            public void run() {

                Bukkit.getOnlinePlayers().stream()
                        .filter(condition.negate())
                        .forEach( p -> {

                            if (movements.containsKey(p)) {
                                movements.remove(p);
                                lastLocations.remove(p);
                            }

                        });

                Bukkit.getOnlinePlayers().stream()
                        .filter(condition)
                        .forEach( p -> {

                            Location loc = p.getLocation(), lastLoc;
                            int x = loc.getBlockX(),
                                   y = loc.getBlockY(),
                                   z = loc.getBlockZ(),
                                   move = x + y + z,
                                   lastMove;

                            if (!movements.containsKey(p)) {
                                movements.put(p, move);
                                lastLocations.put(p, loc);
                            }
                            else {

                                lastMove = movements.get(p);
                                if (lastMove != move){
                                    lastLoc = lastLocations.get(p);
                                    Bukkit.getPluginManager().callEvent(new PlayerMoveEvent(p, lastLoc, loc));
                                    movements.replace(p, move);
                                    lastLocations.replace(p, loc);
                                }

                            }




                        });

            }

        }.runTaskTimerAsynchronously(plugin, 20, 10);

    }

}
