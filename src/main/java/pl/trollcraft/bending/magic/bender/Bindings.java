package pl.trollcraft.bending.magic.bender;

import java.util.HashMap;

public class Bindings {

    private HashMap<Integer, String> slots;

    public Bindings(HashMap<Integer, String> slots) {
        this.slots = slots;
    }

    // -------- ------- -------

    public HashMap<Integer, String> getSlots() { return slots; }

    // -------- ------- -------

    public void bind(int slot, String ability) {
        if (slots.containsKey(slot))
            slots.replace(slot, ability);
        else
            slots.put(slot, ability);
    }

    public boolean unbind(int slot) {
        if (slots.containsKey(slot)){
            slots.remove(slot);
            return true;
        }
        return false;
    }

    public String get(int slot) {
        if (slots.containsKey(slot))
            return slots.get(slot);
        return null;
    }

}
