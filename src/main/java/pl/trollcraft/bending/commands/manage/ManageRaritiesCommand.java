package pl.trollcraft.bending.commands.manage;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.trollcraft.bending.help.Help;
import pl.trollcraft.bending.magic.creator.Creator;
import pl.trollcraft.bending.magic.creator.CreatorType;
import pl.trollcraft.bending.magic.creator.Operation;

public class ManageRaritiesCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("Komenda jedynie dla graczy online.");
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage(Help.color("&7Zarzadzanie rzadkosciami:"));
            sender.sendMessage("");
            sender.sendMessage(Help.color("&e/rarity new <id>&7 - nowa rzadkosc,"));
            sender.sendMessage(Help.color("&e/rarity rm <id>&7 - usuwa rzadkosc."));
        }
        else {

            if (args[0].equalsIgnoreCase("new")) {

                if (args.length != 2){
                    sender.sendMessage(Help.color("&cUzycie: &e/rarity new <id>"));
                    return true;
                }

                Player player = (Player) sender;
                Creator creator = Creator.find(player);

                if (creator != null){
                    sender.sendMessage(Help.color("&cEdytor jest aktywny."));
                    return true;
                }

                String id = args[1];

                creator = new Creator(id, player, CreatorType.RARITY);
                creator.setOperation(Operation.NAME);
                player.sendMessage(Help.color("&a" + creator.getOperation().message()));

                return true;
            }

        }

        return true;
    }
}
