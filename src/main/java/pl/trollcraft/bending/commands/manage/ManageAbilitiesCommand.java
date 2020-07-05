package pl.trollcraft.bending.commands.manage;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.trollcraft.bending.magic.ability.Abilities;
import pl.trollcraft.bending.magic.ability.Ability;
import pl.trollcraft.bending.magic.bender.Bender;
import pl.trollcraft.bending.magic.bender.Benders;
import pl.trollcraft.bending.magic.creator.Creator;
import pl.trollcraft.bending.magic.creator.CreatorType;
import pl.trollcraft.bending.help.Help;
import pl.trollcraft.bending.magic.creator.Operation;
import pl.trollcraft.bending.magic.element.Element;
import pl.trollcraft.bending.magic.element.Elements;

public class ManageAbilitiesCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!sender.hasPermission("bending.abilities.admin")){
            sender.sendMessage(Help.color("&cBrak uprawnien."));
            return true;
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage(Help.color("&cKomendy jedynie dla graczy."));
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage(Help.color("&7Zarzadzanie ruchami:"));
            sender.sendMessage("");
            sender.sendMessage(Help.color("&e/am new <id> <zywiol> - &7kreator nowego ruchu/zywiolu;"));
            sender.sendMessage(Help.color("&e/am rm <id> - &7usuwanie ruchu/zywiolu;"));
            sender.sendMessage("");
            sender.sendMessage(Help.color("&e/am add <gracz> <id> - &7dodaj ruch;"));
            sender.sendMessage(Help.color("&e/am remove <gracz> <id> - &7usun ruch;"));
            //sender.sendMessage(Help.color("&e/am addall <gracz> <zywiol> <id> - &7dodaj wszystkie ruchy;"));
            //sender.sendMessage(Help.color("&e/am removeall <gracz> <zywiol> <id> - &7usun wszystkie ruchy;"));
        }
        else {

            Player player = (Player) sender;

            if (args[0].equalsIgnoreCase("new")) {

                if (args.length != 3){
                    sender.sendMessage(Help.color("&cUzycie: &e/am new <id> <zywiol>"));
                    return true;
                }

                Creator creator = Creator.find(player);

                if (creator != null){
                    sender.sendMessage(Help.color("&cEdytor jest aktywny."));
                    return true;
                }

                String id = args[1];
                Element element = Elements.find(args[2]);

                creator = new Creator(id, player, CreatorType.ABILITY);
                creator.getAbility().setElement(element);
                creator.setOperation(Operation.NAME);
                player.sendMessage(Help.color("&a" + creator.getOperation().message()));

                return true;
            }

            else if (args[0].equalsIgnoreCase("add")) {

                if (args.length != 3){
                    player.sendMessage(Help.color("&cUzycie: &e/am add <gracz> <id ruchu>"));
                    return true;
                }

                Bender bender = Benders.find(args[1]);
                Ability ability = Abilities.find(args[2]);

                bender.getAbilities().add(ability);

                player.sendMessage(Help.color("&aDodano ruch."));

                return true;

            }

        }

        return true;
    }
}
