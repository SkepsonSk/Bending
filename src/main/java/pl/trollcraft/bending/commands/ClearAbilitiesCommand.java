package pl.trollcraft.bending.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.trollcraft.bending.events.AbilitiesClearEvent;
import pl.trollcraft.bending.help.KorraLinker;
import pl.trollcraft.bending.magic.bender.Bender;
import pl.trollcraft.bending.magic.bender.Benders;

public class ClearAbilitiesCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("Komenda jedynie dla graczy online.");
            return true;
        }

        Player player = (Player) sender;
        String playerName = player.getName();
        Bender bender = Benders.find(playerName);
        bender.getBindings().clear();

        if (KorraLinker.isOn())
            KorraLinker.getInstance().clear(playerName);

        Bukkit.getPluginManager().callEvent(new AbilitiesClearEvent(player));

        return true;
    }
}
