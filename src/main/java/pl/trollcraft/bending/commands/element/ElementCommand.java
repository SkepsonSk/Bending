package pl.trollcraft.bending.commands.element;

import org.bukkit.entity.Player;
import pl.trollcraft.bending.help.locale.Localized;
import pl.trollcraft.bending.help.menu.Menu;
import pl.trollcraft.bending.help.menu.Menus;
import pl.trollcraft.bending.help.menu.components.Closeable;
import pl.trollcraft.bending.help.menu.components.Empty;
import pl.trollcraft.bending.help.menu.components.Interactable;
import pl.trollcraft.bending.help.menu.components.Text;
import pl.trollcraft.bending.magic.ability.Ability;
import pl.trollcraft.bending.magic.bender.Bender;
import pl.trollcraft.bending.magic.bender.Benders;
import pl.trollcraft.bending.magic.element.Element;

import java.util.List;

public class ElementCommand {

    public static void menu(Element element,
                            Player player,
                            String locale) {

        String l = element.getLightColor();
        String d = element.getDarkColor();

        Bender bender = Benders.find(player);

        Menu menu = Menus.prepare(player, locale);

        menu.add(new Closeable(new Localized()
                .locale("pl", l + "Ruchy zywiolu: " + d + element.getName(locale))
                .locale("en", l + "Abilities for: " + d + element.getName(locale)),
                "closeMenu"));

        menu.add(new Empty());

        List<Ability> abilities = bender.getAbilities();
        if (abilities.isEmpty())
            menu.add(new Text(new Localized()
                    .locale("pl", l + "  Brak ruchow ;(")
                    .locale("en", l + "  No abilities unlocked ;(")));

        else
            abilities.forEach( ability ->
                    menu.add(new Interactable("  " + l, ability.getName(),
                            ability.getDesc(), "/bind " + ability.getId()))
            );

        menu.add(new Empty());
        menu.add(new Text(new Localized()
                .locale("pl", l + "Wiecej opcji...")
                .locale("en", l + "More options...")));
        menu.add(new Empty());

        menu.print();

    }

}
