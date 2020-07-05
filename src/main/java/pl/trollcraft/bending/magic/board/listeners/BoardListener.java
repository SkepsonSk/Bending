package pl.trollcraft.bending.magic.board.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import pl.trollcraft.bending.events.AbilitiesClearEvent;
import pl.trollcraft.bending.events.BenderBindEvent;
import pl.trollcraft.bending.events.preferences.PreferenceChangeEvent;
import pl.trollcraft.bending.events.preferences.PreferenceLoadEvent;
import pl.trollcraft.bending.magic.board.Board;
import pl.trollcraft.bending.magic.board.Boards;
import pl.trollcraft.bending.magic.ability.Ability;
import pl.trollcraft.bending.magic.preferences.Preference;

public class BoardListener implements Listener {

    @EventHandler
    public void onJoin (PreferenceLoadEvent event) {
        Player player = event.getPlayer();
        String playerName = player.getName();
        Preference p = event.getPreference();

        Board board = Boards.find(playerName);

        if (board == null)
            board = Boards.create(playerName, p.hasBoardHidden(), p.getLocale());
        else
            board.setHidden(p.hasBoardHidden());

        board.init(player.getInventory().getHeldItemSlot());
        board.render();
    }

    @EventHandler
    public void onQuit (PlayerQuitEvent event) {
        Player player = event.getPlayer();
        String playerName = player.getName();

        Board board = Boards.find(playerName);
        Boards.dispose(board);
    }

    // -------- --------- ------- --------

    @EventHandler
    public void onPreferenceChange (PreferenceChangeEvent event) {

        PreferenceChangeEvent.Field field = event.getField();
        Player player = event.getPlayer();
        String playerName = player.getName();
        Preference p = event.getPreference();
        Board board = Boards.find(playerName);

        if (field == PreferenceChangeEvent.Field.BOARD) {
            board.setHidden(p.hasBoardHidden());
            board.render();
        }

        else if (field == PreferenceChangeEvent.Field.LOCALE) {
            board.setLocale(p.getLocale());
            board.render();
        }

    }

    // -------- --------- ------- --------

    @EventHandler
    public void onItemSwitch (PlayerItemHeldEvent event) {
        String playerName = event.getPlayer().getName();
        int from = event.getPreviousSlot();
        int to = event.getNewSlot();

        Board board = Boards.find(playerName);

        board.changeSlots(from, to);
        board.render();
    }

    @EventHandler
    public void onBind (BenderBindEvent event) {
        Player player = Bukkit.getPlayer(event.getBender().getName());
        String playerName = player.getName();
        Ability ability = event.getAbility();
        int to = event.getBoundTo();

        Board board = Boards.find(playerName);

        board.bind(ability, to);
        board.render();
    }

    @EventHandler
    public void onClear (AbilitiesClearEvent event) {
        Player player = event.getPlayer();
        String playerName = player.getName();
        Board board = Boards.find(playerName);

        board.clear();
        board.render();
    }

}
