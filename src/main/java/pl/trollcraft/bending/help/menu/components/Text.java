package pl.trollcraft.bending.help.menu.components;

import org.bukkit.entity.Player;
import pl.trollcraft.bending.help.locale.Localized;

public class Text implements Component {

    protected Localized message;

    public Text(Localized message) {
        this.message = message;
    }

    @Override
    public void print(Player player, String locale) {
        player.sendMessage(message.text(locale));
    }

}
