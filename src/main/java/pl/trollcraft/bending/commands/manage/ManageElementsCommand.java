package pl.trollcraft.bending.commands.manage;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.trollcraft.bending.help.Help;
import pl.trollcraft.bending.magic.creator.Creator;
import pl.trollcraft.bending.magic.creator.CreatorType;
import pl.trollcraft.bending.magic.creator.Operation;

public class ManageElementsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!sender.hasPermission("bending.elements.admin")){
            sender.sendMessage(Help.color("&cBrak uprawnien."));
            return true;
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage(Help.color("&cKomendy jedynie dla graczy."));
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage(Help.color("&7Zarzadzanie zywiolami:"));
            sender.sendMessage("");
            sender.sendMessage(Help.color("&e/em new <id> - &7kreator nowego zywiolu;"));
            sender.sendMessage(Help.color("&e/em rm <id> - &7usuwanie zywiolu;"));
            sender.sendMessage("");
        }
        else {

            Player player = (Player) sender;

            if (args[0].equalsIgnoreCase("new")) {

                if (args.length != 2){
                    sender.sendMessage(Help.color("&cUzycie: &e/em new <id>"));
                    return true;
                }

                Creator creator = Creator.find(player);

                if (creator != null){
                    sender.sendMessage(Help.color("&cEdytor jest aktywny."));
                    return true;
                }

                String id = args[1];
                creator = new Creator(id, player, CreatorType.ELEMENT);
                creator.setOperation(Operation.NAME);
                player.sendMessage(Help.color("&a" + creator.getOperation().message()));

                return true;
            }

        }

        return true;
    }

}
