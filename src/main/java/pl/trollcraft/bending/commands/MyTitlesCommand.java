package pl.trollcraft.bending.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.trollcraft.bending.events.TitleSelectEvent;
import pl.trollcraft.bending.events.preferences.PreferenceChangeEvent;
import pl.trollcraft.bending.help.Help;
import pl.trollcraft.bending.help.locale.Localized;
import pl.trollcraft.bending.help.menu.Menu;
import pl.trollcraft.bending.help.menu.Menus;
import pl.trollcraft.bending.help.menu.components.Closeable;
import pl.trollcraft.bending.help.menu.components.Empty;
import pl.trollcraft.bending.help.menu.components.Interactable;
import pl.trollcraft.bending.help.menu.components.Text;
import pl.trollcraft.bending.magic.preferences.Preference;
import pl.trollcraft.bending.magic.preferences.Preferences;
import pl.trollcraft.bending.magic.titles.title.Title;
import pl.trollcraft.bending.magic.titles.title.Titles;
import pl.trollcraft.bending.magic.titles.titleuser.TitleUser;
import pl.trollcraft.bending.magic.titles.titleuser.TitleUsers;

public class MyTitlesCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String name, String[] args) {

        if (!(sender instanceof Player)){
            sender.sendMessage("Komenda jedynie dla graczy online.");
            return true;
        }

        Player player = (Player) sender;
        String playerName = player.getName();
        Preference preference = Preferences.find(playerName);
        Menu menu = Menus.prepare(player, preference.getLocale());
        TitleUser titleUser = TitleUsers.find(playerName);

        if (name.equalsIgnoreCase("me") || name.equalsIgnoreCase("myTitles") ||
                name.equalsIgnoreCase("titles"))
            preference.setLocale("en");
        else
            preference.setLocale("pl");

        Bukkit.getPluginManager().callEvent(new PreferenceChangeEvent(player, preference, PreferenceChangeEvent.Field.LOCALE));

        String locale = preference.getLocale();

        if (args.length == 0) {

            menu.clear();
            menu.setLocale(locale);

            menu.add(new Closeable(new Localized()
                    .locale("pl", "&7Moje &etytuly")
                    .locale("en", "&7My &etitles"),
                    "/closeMenu" ));

            menu.add(new Empty());

            menu.add(new Text(new Localized()
                    .locale("pl", "  &7Tytuly sluza do ozdabiania chatu.")
                    .locale("en", "  &7Titles are meant to decorate your chat.")));
            menu.add(new Text(new Localized()
                    .locale("pl", "  &7Klikajac na tytul przypiszesz go sobie.")
                    .locale("en", "  &7When clicking on a title it will be bound to you.")));
            menu.add(new Text(new Localized()
                    .locale("pl", "  &7Bedzie wyswietlany przed Twoim nickiem.")
                    .locale("en", "  &7It will be displayed before your nickname.")));

            menu.add(new Empty());

            if (titleUser.getTitles().isEmpty())
                menu.add(new Text(new Localized()
                        .locale("pl", "  &7Brak odblokowanych tytulow.")
                        .locale("en", "  &7No unlocked titles.")));

            else
                titleUser.getTitles().forEach( titleName -> {
                    Title title = Titles.find(titleName);
                    menu.add(new Interactable("  " + title.getRarity().getLightColor(),
                            title.getName(), title.getDesc(),
                            "/mytitles select " + titleName));
                } );

            menu.add(new Empty());

            menu.print();

            return true;

        }
        else if (args[0].equalsIgnoreCase("select")) {

            if (args.length != 2) {
                player.sendMessage(Help.color("&cUzycie: &e/mytitles select <id>"));
                return true;
            }

            Title title = Titles.find(args[1]);
            if (title == null) {
                player.sendMessage(Help.color("&cNieznany tytul."));
                return true;
            }

            titleUser.setSelectedTitle(args[1]);

            Bukkit.getPluginManager().callEvent(new TitleSelectEvent(player, title));

            player.sendTitle(title.getRarity().getDarkColor() + title.getName().text(locale),
                            new Localized()
                            .locale("pl", title.getRarity().getLightColor() + "Wybrano!")
                            .locale("en", title.getRarity().getLightColor() + "Chosen!")
                            .text(locale),
                            10, 20, 10);

            return true;
        }

        return true;
    }
}
