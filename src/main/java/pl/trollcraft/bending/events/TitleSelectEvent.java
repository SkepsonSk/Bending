package pl.trollcraft.bending.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import pl.trollcraft.bending.magic.titles.title.Title;

public class TitleSelectEvent extends Event {

    private Player player;
    private Title title;

    public TitleSelectEvent (Player player, Title title) {
        this.player = player;
        this.title = title;
    }

    public Player getPlayer() { return player; }
    public Title getTitle() { return title; }

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
