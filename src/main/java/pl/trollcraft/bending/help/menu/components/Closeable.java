package pl.trollcraft.bending.help.menu.components;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import pl.trollcraft.bending.help.Help;
import pl.trollcraft.bending.help.locale.Localized;

public class Closeable extends Text {

    private String command;

    public Closeable(Localized message, String command) {
        super(message);
        this.command = command;
    }

    @Override
    public void print(Player player, String locale) {
        TextComponent base = new TextComponent(message.text(locale));
        TextComponent close = new TextComponent(Help.color(" &c(✖)"));
        close.setClickEvent( new ClickEvent(ClickEvent.Action.RUN_COMMAND, command));

        base.addExtra(close);

        player.spigot().sendMessage(base);
    }
}
