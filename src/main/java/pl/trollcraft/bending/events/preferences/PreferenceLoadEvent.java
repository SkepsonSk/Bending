package pl.trollcraft.bending.events.preferences;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import pl.trollcraft.bending.magic.preferences.Preference;

public class PreferenceLoadEvent extends Event {

    private Player player;
    private Preference preference;

    // -------- -------- -------- --------

    public PreferenceLoadEvent (Player player, Preference preference) {
        this.player = player;
        this.preference = preference;
    }

    public Player getPlayer() { return player; }
    public Preference getPreference() { return preference; }

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
