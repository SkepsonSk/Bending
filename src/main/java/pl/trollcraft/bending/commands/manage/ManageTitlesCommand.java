package pl.trollcraft.bending.commands.manage;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.trollcraft.bending.help.Help;
import pl.trollcraft.bending.magic.creator.Creator;
import pl.trollcraft.bending.magic.creator.CreatorType;
import pl.trollcraft.bending.magic.creator.Operation;
import pl.trollcraft.bending.magic.titles.rarity.Rarities;
import pl.trollcraft.bending.magic.titles.rarity.Rarity;
import pl.trollcraft.bending.magic.titles.title.Title;
import pl.trollcraft.bending.magic.titles.title.Titles;
import pl.trollcraft.bending.magic.titles.titleuser.TitleUser;
import pl.trollcraft.bending.magic.titles.titleuser.TitleUsers;

public class ManageTitlesCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (!(sender instanceof Player)){
            sender.sendMessage("Komenda jedynie dla graczy online.");
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage(Help.color("&7Zarzadzanie tytulami:"));
            sender.sendMessage("");
            sender.sendMessage(Help.color("&e/title new <id> <rzadkosc>&7 - nowa tytul,"));
            sender.sendMessage(Help.color("&e/title del <id>&7 - usuwa tytul,"));
            sender.sendMessage(Help.color("&e/title add <id> <gracz>&7 - dodaje graczowi tytul,"));
            sender.sendMessage(Help.color("&e/title rem <id> <gracz>&7 - usuwa graczowi tytul."));
        }
        else {

            if (args[0].equalsIgnoreCase("new")) {

                if (args.length != 3) {
                    sender.sendMessage(Help.color("&cUzycie: &e/title new <id> <rzadkosc>"));
                    return true;
                }

                Player player = (Player) sender;
                Creator creator = Creator.find(player);

                if (creator != null){
                    player.sendMessage(Help.color("&cEdytor jest aktywny."));
                    return true;
                }

                Rarity rarity = Rarities.find(args[2]);
                if (rarity == null) {
                    player.sendMessage(Help.color("&cNieznana rzadkosc."));
                    return true;
                }

                String id = args[1];

                creator = new Creator(id, player, CreatorType.TITLE);
                creator.setOperation(Operation.NAME);
                creator.getTitle().setRarity(rarity);

                player.sendMessage(Help.color("&a" + creator.getOperation().message()));

                return true;
            }

            else if (args[0].equalsIgnoreCase("add")) {

                if (args.length != 3) {
                    sender.sendMessage(Help.color("&cUzycie: &e/title add <id> <gracz>"));
                    return true;
                }

                Title title = Titles.find(args[1]);

                if (title == null){
                    sender.sendMessage(Help.color("&cNieznany tytul."));
                    return true;
                }

                TitleUser titleUser = TitleUsers.find(args[2]);

                if (titleUser == null){
                    sender.sendMessage(Help.color("&cNieznany gracz (mozliwe, ze gracz jest offline)."));
                    return true;
                }

                if (titleUser.getTitles().contains(title.getId())){
                    sender.sendMessage(Help.color("&cGracz posiada juz ten tytul."));
                    return true;
                }

                titleUser.getTitles().add(title.getId());
                sender.sendMessage(Help.color("&aDodano tytul!"));

                return true;
            }

            else if (args[0].equalsIgnoreCase("rem")) {

                if (args.length != 3) {
                    sender.sendMessage(Help.color("&cUzycie: &e/title rem <id> <gracz>"));
                    return true;
                }

                Title title = Titles.find(args[1]);

                if (title == null){
                    sender.sendMessage(Help.color("&cNieznany tytul."));
                    return true;
                }

                TitleUser titleUser = TitleUsers.find(args[2]);

                if (titleUser == null){
                    sender.sendMessage(Help.color("&cNieznany gracz (mozliwe, ze gracz jest offline)."));
                    return true;
                }

                if (!titleUser.getTitles().contains(title.getId())){
                    sender.sendMessage(Help.color("&cGracz nie posiada tego tytulu."));
                    return true;
                }

                titleUser.getTitles().remove(title.getId());
                sender.sendMessage(Help.color("&aUsunieto tytul!"));

                return true;
            }

        }

        return true;
    }
}
