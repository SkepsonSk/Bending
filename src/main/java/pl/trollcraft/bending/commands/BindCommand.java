package pl.trollcraft.bending.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.trollcraft.bending.commands.element.ElementCommand;
import pl.trollcraft.bending.help.Help;
import pl.trollcraft.bending.help.KorraLinker;
import pl.trollcraft.bending.help.locale.Localized;
import pl.trollcraft.bending.help.menu.Menu;
import pl.trollcraft.bending.help.menu.Menus;
import pl.trollcraft.bending.help.menu.components.Closeable;
import pl.trollcraft.bending.help.menu.components.Empty;
import pl.trollcraft.bending.help.menu.components.Slots;
import pl.trollcraft.bending.help.menu.components.Text;
import pl.trollcraft.bending.magic.ability.Abilities;
import pl.trollcraft.bending.magic.ability.Ability;
import pl.trollcraft.bending.magic.bender.Bender;
import pl.trollcraft.bending.magic.bender.Benders;
import pl.trollcraft.bending.magic.element.Element;

public class BindCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)){
            sender.sendMessage("Komenda jedynie dla graczy online.");
            return true;
        }

        Player player = (Player) sender;
        Bender bender = Benders.find(player);

        if (args.length == 1){

            Menu menu = Menus.find(player);

            if (menu == null) {
                player.sendMessage(Help.color("&cBlad."));
                return true;
            }

            Ability ability = Abilities.find(args[0]);

            menu.clear();

            menu.add(new Closeable(new Localized()
                    .locale("pl", "Przypisywanie: " + ability.getName("pl"))
                    .locale("en", "Binding: " + ability.getName("en")),
                    "/close"));

            menu.add(new Empty());

            menu.add(new Text(new Localized()
                    .locale("pl", "Wybierz slot, do ktorego przypisany zostanie ruch.")
                    .locale("en", "Choose a slot where the ability will be bound.")));

            menu.add(new Empty());
            menu.add(new Slots(ability, bender));
            menu.add(new Empty());

            menu.print();

            return true;

        }
        else if (args.length == 2) {
            int slot = Integer.parseInt(args[0]);
            String ability = args[1];

            bender.getBindings().bind(slot, ability);
            if (KorraLinker.isOn())
                KorraLinker.getInstance().bind(bender.getBindings(), bender.getName());

            Ability a = Abilities.find(ability);
            Element e = a.getElement();
            String locale = Menus.find(player).getLocale();

            player.sendTitle(e.getDarkColor() + a.getName(locale),
                             new Localized()
                            .locale("pl", e.getLightColor() + "Przypieto do slotu: &e" + slot)
                            .locale("en", e.getLightColor() + "Bound to slot: &e" + slot)
                            .text(locale),
                            5, 30, 5);

            ElementCommand.menu(a.getElement(), player, locale);
        }

        return true;

    }
}
