package pl.trollcraft.bending.magic.creator;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import pl.trollcraft.bending.help.Help;

public class CreatorListener implements Listener {

    @EventHandler
    public void onChat (AsyncPlayerChatEvent event) {

        Player player = event.getPlayer();
        Creator creator = Creator.find(player);

        if (creator == null) return;

        event.setCancelled(true);
        String message = event.getMessage();

        if (creator.getOperation() == Operation.NAME) {

            if (message.equalsIgnoreCase(".")) {
                creator.setOperation(Operation.DESC);
                player.sendMessage(Operation.DESC.message());
                return;
            }

            String[] msgSplit = message.split("@");

            String locale = msgSplit[0];
            String text = msgSplit[1];

            if (creator.getType() == CreatorType.ABILITY)
                creator.getAbility().getName().locale(locale, text);
            else
                creator.getElement().getName().locale(locale, text);

        }
        else if (creator.getOperation() == Operation.DESC) {

            if (creator.getType() == CreatorType.ABILITY) {

                if (message.equalsIgnoreCase(".")) {
                    creator.dispose();
                    player.sendMessage(Help.color("&aGotowe."));
                    return;
                }

                String[] msgSplit = message.split("@");

                String locale = msgSplit[0];
                String text = msgSplit[1];

                creator.getAbility().getDesc().locale(locale, text);
            }
            else {

                if (message.equalsIgnoreCase(".")) {
                    creator.setOperation(Operation.COLORS);
                    player.sendMessage(Operation.COLORS.message());
                    return;
                }

                String[] msgSplit = message.split("@");

                String locale = msgSplit[0];
                String text = msgSplit[1];

                creator.getElement().getDesc().locale(locale, text);
            }

        }
        else if (creator.getOperation() == Operation.COLORS) {

            if (message.length() != 3 || !message.contains(":")){
                player.sendMessage(Help.color("&cFormat: kolor jasny:kolor ciemny &7(np. c:4)"));
            }

            String[] split = message.split(":");
            creator.getElement().setLightColor(Help.color("&") + split[0]);
            creator.getElement().setDarkColor(Help.color("&") + split[1]);

            creator.dispose();
            player.sendMessage(Help.color("&aGotowe."));
        }

    }

}
