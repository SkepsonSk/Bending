package pl.trollcraft.bending.magic.creator;

import org.bukkit.entity.Player;
import pl.trollcraft.bending.magic.ability.Abilities;
import pl.trollcraft.bending.magic.ability.Ability;
import pl.trollcraft.bending.magic.element.Element;
import pl.trollcraft.bending.magic.element.Elements;

import java.util.ArrayList;

public class Creator {

    private static final ArrayList<Creator> creators = new ArrayList<>();

    private Player player;
    private CreatorType type;

    private Operation operation;

    private Element element;
    private Ability ability;

    // -------- -------- -------- --------

    public Creator(String id, Player player, CreatorType type) {
        this.player = player;
        this.type = type;

        if (type == CreatorType.ABILITY) ability = new Ability(id);
        else element = new Element(id);

        creators.add(this);
    }

    public void dispose() {

        if (type == CreatorType.ABILITY) {
            Abilities.register(ability);
            Abilities.save(ability);
        }
        else {
            Elements.register(element);
            Elements.save(element);
        }

        creators.remove(this);
    }

    // -------- -------- -------- --------

    public CreatorType getType() { return type; }

    public void setOperation(Operation operation) { this.operation = operation; }
    public Operation getOperation() { return operation; }

    public Ability getAbility() { return ability; }
    public Element getElement() { return element; }

    // -------- -------- -------- --------

    public static Creator find(Player player) {
        int id = player.getEntityId();
        for (Creator c : creators)
            if (c.player.getEntityId() == id)
                return c;
        return null;
    }

}
