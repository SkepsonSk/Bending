package pl.trollcraft.bending.events.preferences;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import pl.trollcraft.bending.magic.preferences.Preference;

public class PreferenceChangeEvent extends Event {

    public static enum Field {BOARD, LOCALE}

    private Player player;
    private Preference preference;
    private Field field;

    // -------- -------- -------- --------

    public PreferenceChangeEvent (Player player, Preference preference, Field field) {
        this.player = player;
        this.preference = preference;
        this.field = field;
    }

    public Player getPlayer() { return player; }
    public Preference getPreference() { return preference; }
    public Field getField() { return field; }

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
