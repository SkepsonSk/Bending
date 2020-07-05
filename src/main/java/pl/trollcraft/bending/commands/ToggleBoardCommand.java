package pl.trollcraft.bending.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.trollcraft.bending.commands.element.ElementCommand;
import pl.trollcraft.bending.events.preferences.PreferenceChangeEvent;
import pl.trollcraft.bending.magic.board.Board;
import pl.trollcraft.bending.magic.board.Boards;
import pl.trollcraft.bending.magic.preferences.Preference;
import pl.trollcraft.bending.magic.preferences.Preferences;

public class ToggleBoardCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("Komenda jedynie dla graczy online.");
            return true;
        }

        Player player = (Player) sender;
        String playerName = player.getName();
        Board board = Boards.find(playerName);
        Preference preference = Preferences.find(playerName);

        board.toggleHidden();
        preference.setBoardHidden(!preference.hasBoardHidden());

        Bukkit.getPluginManager().callEvent(new PreferenceChangeEvent(player, preference, PreferenceChangeEvent.Field.BOARD));

        ElementCommand.menu(preference.getElement(), player, preference.getLocale());

        return true;
    }
}
