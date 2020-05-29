package pl.trollcraft.bending.help.menu.components;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import pl.trollcraft.bending.help.Help;
import pl.trollcraft.bending.magic.ability.Ability;
import pl.trollcraft.bending.magic.bender.Bender;

import java.util.ArrayList;

public class Slots implements Component {

    private Ability ability;
    private Bender bender;

    public Slots (Ability ability, Bender bender) {
        this.ability = ability;
        this.bender = bender;
    }

    @Override
    public void print(Player player, String locale) {
        String color = ability.getElement().getLightColor();

        TextComponent base = new TextComponent("  ");
        TextComponent slot;

        for (int i = 1 ; i < 10 ; i++) {

            slot = new TextComponent(Help.color(color + "(" + i + ") "));

            slot.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,
                    "/bind " + i + " " + ability.getId()));

            base.addExtra(slot);
        }

        player.spigot().sendMessage(base);
    }

}
