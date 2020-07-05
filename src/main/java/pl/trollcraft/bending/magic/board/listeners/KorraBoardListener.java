package pl.trollcraft.bending.magic.board.listeners;

import com.projectkorra.projectkorra.event.PlayerCooldownChangeEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import pl.trollcraft.bending.magic.board.Board;
import pl.trollcraft.bending.magic.board.Boards;

public class KorraBoardListener implements Listener {

    @EventHandler
    public void onCooldownChange (PlayerCooldownChangeEvent event) {
        Player player = event.getPlayer();
        String playerName = player.getName();
        PlayerCooldownChangeEvent.Result result = event.getResult();
        String abilityId = event.getAbility();

        Board board = Boards.find(playerName);

        if (result == PlayerCooldownChangeEvent.Result.ADDED)
            board.cooldownOn(abilityId);

        else
            board.cooldownOff(abilityId);

        board.render();
    }

}
