package pl.trollcraft.bending.magic.creator;

import org.bukkit.entity.Player;
import pl.trollcraft.bending.magic.ability.Abilities;
import pl.trollcraft.bending.magic.ability.Ability;
import pl.trollcraft.bending.magic.element.Element;
import pl.trollcraft.bending.magic.element.Elements;
import pl.trollcraft.bending.magic.titles.rarity.Rarities;
import pl.trollcraft.bending.magic.titles.rarity.Rarity;
import pl.trollcraft.bending.magic.titles.title.Title;
import pl.trollcraft.bending.magic.titles.title.Titles;

import java.util.ArrayList;

public class Creator {

    private static final ArrayList<Creator> creators = new ArrayList<>();

    private Player player;
    private CreatorType type;

    private Operation operation;

    private Element element;
    private Ability ability;
    private Rarity rarity;
    private Title title;

    // -------- -------- -------- --------

    public Creator(String id, Player player, CreatorType type) {
        this.player = player;
        this.type = type;

        if (type == CreatorType.ABILITY) ability = new Ability(id);
        else if (type == CreatorType.ELEMENT) element = new Element(id);
        else if (type == CreatorType.RARITY) rarity = new Rarity(id);
        else if (type == CreatorType.TITLE) title = new Title(id);

        creators.add(this);
    }

    public void dispose() {

        if (type == CreatorType.ABILITY) {
            Abilities.register(ability);
            Abilities.save(ability);
        }
        else if (type == CreatorType.ELEMENT) {
            Elements.register(element);
            Elements.save(element);
        }
        else if (type == CreatorType.RARITY) {
            Rarities.register(rarity);
            Rarities.save(rarity);
        }
        else if (type == CreatorType.TITLE) {
            Titles.register(title);
            Titles.save(title);
        }

        creators.remove(this);
    }

    // -------- -------- -------- --------

    public CreatorType getType() { return type; }

    public void setOperation(Operation operation) { this.operation = operation; }
    public Operation getOperation() { return operation; }

    public Ability getAbility() { return ability; }
    public Element getElement() { return element; }
    public Rarity getRarity() { return rarity; }
    public Title getTitle() { return title; }

    // -------- -------- -------- --------

    public static Creator find(Player player) {
        int id = player.getEntityId();
        for (Creator c : creators)
            if (c.player.getEntityId() == id)
                return c;
        return null;
    }

}
