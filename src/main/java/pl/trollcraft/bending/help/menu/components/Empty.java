package pl.trollcraft.bending.help.menu.components;

import org.bukkit.entity.Player;

public class Empty implements Component {

    public Empty(){}

    @Override
    public void print(Player player, String locale) {
        player.sendMessage("");
    }
}
