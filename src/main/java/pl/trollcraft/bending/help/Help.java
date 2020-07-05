package pl.trollcraft.bending.help;

import org.bukkit.entity.Player;

import java.util.List;

public class Help {

    public static String color(String message) { return message.replaceAll("&", "§"); }

    public static void clearChat(Player player) {
        for (int i = 0 ; i < 100 ; i++)
            player.sendMessage("");
    }

    public static void changeList (List<String> changed, List<String> target /* titles */) {
        target.removeIf(tit -> !changed.contains(tit));
        for (String s : changed)
            if (!target.contains(s))
                target.add(s);
    }

}
