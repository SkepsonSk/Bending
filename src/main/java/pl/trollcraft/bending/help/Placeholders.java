package pl.trollcraft.bending.help;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;

public class Placeholders extends PlaceholderExpansion {

    @Override
    public boolean canRegister() { return true; }

    @Override
    public String getAuthor() { return "Jakub Zelmanowicz"; }

    @Override
    public String getIdentifier() { return "bending"; }

    @Override
    public String getVersion(){ return "1.0.0"; }

    // -------- -------- -------- --------

    @Override
    public String onPlaceholderRequest(Player player, String identifier) {
        if (player == null) return "";

        //TODO add placeholder support.

        return null;
    }

}
