package pl.trollcraft.bending.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class AbilitiesClearEvent extends Event {

    private Player player;

    public AbilitiesClearEvent(Player player) {
        this.player = player;
    }

    public Player getPlayer() { return player; }

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
