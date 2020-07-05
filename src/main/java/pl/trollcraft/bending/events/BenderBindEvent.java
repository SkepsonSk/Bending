package pl.trollcraft.bending.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import pl.trollcraft.bending.magic.ability.Ability;
import pl.trollcraft.bending.magic.bender.Bender;

public class BenderBindEvent extends Event {

    private Bender bender;
    private Ability ability;
    private int boundTo;

    public BenderBindEvent (Bender bender, Ability ability, int boundTo) {
        this.bender = bender;
        this.ability = ability;
        this.boundTo = boundTo;
    }

    // -------- -------- -------- --------

    public Bender getBender() { return bender; }
    public Ability getAbility() { return ability; }
    public int getBoundTo() { return boundTo; }

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
