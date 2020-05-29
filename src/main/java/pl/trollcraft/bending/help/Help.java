package pl.trollcraft.bending.help;

import org.bukkit.entity.Player;

public class Help {

    public static String color(String message) { return message.replaceAll("&", "§"); }

    public static void clearChat(Player player) {
        for (int i = 0 ; i < 100 ; i++)
            player.sendMessage("");
    }

}
