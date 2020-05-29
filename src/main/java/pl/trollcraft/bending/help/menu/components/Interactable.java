package pl.trollcraft.bending.help.menu.components;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import pl.trollcraft.bending.help.locale.Localized;

public class Interactable extends Text {

    private String prefix;
    private Localized hover;
    private String command;

    public Interactable(String prefix, Localized message, Localized hover, String command) {
        super(message);
        this.prefix = prefix;
        this.hover = hover;
        this.command = command;
    }

    @Override
    public void print(Player player, String locale) {
        TextComponent base = new TextComponent(prefix + message.text(locale));
        TextComponent[] hover = { new TextComponent(this.hover.text(locale)) };
        base.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, hover));
        base.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, command));
        player.spigot().sendMessage(base);
    }
}
