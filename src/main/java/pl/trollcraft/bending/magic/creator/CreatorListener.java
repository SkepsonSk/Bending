package pl.trollcraft.bending.magic.creator;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import pl.trollcraft.bending.help.Help;
import pl.trollcraft.bending.magic.titles.rarity.Rarities;
import pl.trollcraft.bending.magic.titles.rarity.Rarity;

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
            else if (creator.getType() == CreatorType.ELEMENT)
                creator.getElement().getName().locale(locale, text);
            else if (creator.getType() == CreatorType.RARITY)
                creator.getRarity().getName().locale(locale, text);
            else if (creator.getType() == CreatorType.TITLE)
                creator.getTitle().getName().locale(locale, text);

            player.sendMessage(Help.color("&6" + locale + " >> " + text + " - &aok"));

        }
        else if (creator.getOperation() == Operation.DESC) {

            if (creator.getType() == CreatorType.ABILITY || creator.getType() == CreatorType.TITLE) {

                if (message.equalsIgnoreCase(".")) {
                    creator.dispose();
                    player.sendMessage(Help.color("&aGotowe."));
                    return;
                }

                String[] msgSplit = message.split("@");

                String locale = msgSplit[0];
                String text = msgSplit[1];

                if (creator.getType() == CreatorType.ABILITY)
                    creator.getAbility().getDesc().locale(locale, text);
                else
                    creator.getTitle().getDesc().locale(locale, text);

                player.sendMessage(Help.color("&6" + locale + " >> " + text + " - &aok"));

            }
            else if (creator.getType() == CreatorType.ELEMENT || creator.getType() == CreatorType.RARITY) {

                if (message.equalsIgnoreCase(".")) {
                    creator.setOperation(Operation.COLORS);
                    player.sendMessage(Operation.COLORS.message());
                    return;
                }

                String[] msgSplit = message.split("@");

                String locale = msgSplit[0];
                String text = msgSplit[1];

                if (creator.getType() == CreatorType.ELEMENT)
                    creator.getElement().getDesc().locale(locale, text);
                else
                    creator.getRarity().getDesc().locale(locale, text);

                player.sendMessage(Help.color("&6" + locale + " >> " + text + " - &aok"));
            }

        }
        else if (creator.getOperation() == Operation.COLORS) {

            if (message.length() != 3 || !message.contains(":")){
                player.sendMessage(Help.color("&cFormat: &7kolor jasny:kolor ciemny &e(np. c:4)"));
            }

            String[] split = message.split(":");

            if (creator.getType() == CreatorType.ELEMENT){
                creator.getElement().setLightColor(Help.color("&") + split[0]);
                creator.getElement().setDarkColor(Help.color("&") + split[1]);
            }
            else {
                creator.getRarity().setLightColor(Help.color("&") + split[0]);
                creator.getRarity().setDarkColor(Help.color("&") + split[1]);
            }

            creator.dispose();
            player.sendMessage(Help.color("&aGotowe."));
        }

    }

}
