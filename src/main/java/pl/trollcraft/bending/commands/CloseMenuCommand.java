package pl.trollcraft.bending.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.trollcraft.bending.help.menu.Menu;
import pl.trollcraft.bending.help.menu.Menus;

public class CloseMenuCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("Komenda jedynie dla graczy.");
            return true;
        }

        Player player = (Player) sender;
        Menu menu = Menus.find(player);

        if (menu == null) return true;

        menu.close();

        return true;
    }
}
