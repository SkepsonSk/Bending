package pl.trollcraft.bending.events;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerMoveEvent extends Event {

    private Player player;
    private Location from;
    private Location to;

    public PlayerMoveEvent (Player player, Location from, Location to) {
        this.player = player;
        this.from = from;
        this.to = to;
    }

    public Player getPlayer() { return player; }
    public Location getFrom() { return from; }
    public Location getTo() { return to; }

    // -------- -------- -------- --------

    private static final HandlerList HANDLERS_LIST = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }

}
