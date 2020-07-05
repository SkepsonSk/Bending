package pl.trollcraft.bending.magic.titles.title;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import pl.trollcraft.bending.Bending;
import pl.trollcraft.bending.events.TitleSelectEvent;
import pl.trollcraft.bending.events.preferences.PreferenceChangeEvent;
import pl.trollcraft.bending.magic.preferences.Preference;
import pl.trollcraft.bending.magic.preferences.Preferences;
import pl.trollcraft.bending.magic.titles.titleuser.TitleUser;
import pl.trollcraft.bending.magic.titles.titleuser.TitleUsers;

public class TitleListener implements Listener {

    @EventHandler
    public void onJoin (PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Location loc = player.getLocation();
        String playerName = player.getName();
        TitleUser titleUser = TitleUsers.find(playerName);
        Plugin plugin = Bending.getPlugin();

        String selectedTitle = titleUser.getSelectedTitle();
        if (selectedTitle == null || selectedTitle.isEmpty()) return;

        Bukkit.getConsoleSender().sendMessage(selectedTitle);

        Preference preference = Preferences.find(playerName);
        Title title = Titles.find(selectedTitle);
        Hologram titleHolo = HologramsAPI.createHologram(plugin, loc.add(0, 3, 0));
        titleHolo.getVisibilityManager().hideTo(player);
        titleHolo.appendTextLine(title.getRarity().getLightColor() + title.getName(preference.getLocale()));
        HoloTitles.register(player, titleHolo);
    }

    @EventHandler
    public void onQuit (PlayerQuitEvent event) {
        Player player = event.getPlayer();
        HoloTitles.unregister(player);
    }

    @EventHandler
    public void onMove (PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Hologram holo = HoloTitles.get(player);
        if (holo != null) {
            Location loc = player.getLocation();
            holo.teleport(loc.add(0, 2.5, 0));
        }
    }

    @EventHandler
    public void onSelect (TitleSelectEvent event) {
        Player player = event.getPlayer();
        Title title = event.getTitle();

        Hologram holo = HoloTitles.get(player);
        String playerName = player.getName();
        Preference preference = Preferences.find(playerName);
        if (holo == null) {
            Plugin plugin = Bending.getPlugin();

            Location loc = player.getLocation();

            holo = HologramsAPI.createHologram(plugin, loc.add(0, 2.5, 0));
            holo.getVisibilityManager().hideTo(player);
            holo.appendTextLine(title.getRarity().getLightColor() + title.getName(preference.getLocale()));
            HoloTitles.register(player, holo);
        }
        else {
            holo.clearLines();
            holo.appendTextLine(title.getRarity().getLightColor() + title.getName(preference.getLocale()));
        }

    }

    @EventHandler
    public void onPreferenceChange (PreferenceChangeEvent event) {
        Player player = event.getPlayer();
        PreferenceChangeEvent.Field field = event.getField();

        if (field == PreferenceChangeEvent.Field.LOCALE) {

            String playerName = player.getName();
            Hologram holo = HoloTitles.get(player);
            Preference preference = event.getPreference();
            TitleUser titleUser = TitleUsers.find(playerName);

            if (titleUser.getSelectedTitle() == null) return;;

            Title title = Titles.find(titleUser.getSelectedTitle());

            if (holo == null) {
                Plugin plugin = Bending.getPlugin();

                Location loc = player.getLocation();

                holo = HologramsAPI.createHologram(plugin, loc.add(0, 2.5, 0));
                holo.getVisibilityManager().hideTo(player);
                holo.appendTextLine(title.getRarity().getLightColor() + title.getName(preference.getLocale()));
                HoloTitles.register(player, holo);
            }
            else {
                holo.clearLines();
                holo.appendTextLine(title.getRarity().getLightColor() + title.getName(preference.getLocale()));
            }

        }

    }

}
