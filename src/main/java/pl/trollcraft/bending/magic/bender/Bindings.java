package pl.trollcraft.bending.magic.bender;

import java.util.Map;
import java.util.stream.Collectors;

public class Bindings {

    private Map<Integer, String> slots;

    public Bindings(Map<Integer, String> slots) {
        this.slots = slots;
    }

    // -------- ------- -------

    public Map<Integer, String> getSlots() { return slots; }

    // -------- ------- -------

    public void bind(int slot, String ability) {
        if (slots.containsKey(slot))
            slots.replace(slot, ability);
        else
            slots.put(slot, ability);
    }

    public void clear() {
        slots.clear();
    }

    public String get(int slot) {
        if (slots.containsKey(slot))
            return slots.get(slot);
        return null;
    }

    public int find(String abilityId) {
        if (slots.containsValue(abilityId)){
            return slots.entrySet()
                    .stream()
                    .filter( e -> e.getValue().equals(abilityId) )
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList())
                    .get(0);
        }
        return -1;
    }

    // -------- -------- --------

    public void setSlots(Map<Integer, String> slots) {
        this.slots = slots;
    }

}
