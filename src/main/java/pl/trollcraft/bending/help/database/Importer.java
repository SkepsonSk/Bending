package pl.trollcraft.bending.help.database;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import pl.trollcraft.bending.help.Configs;
import pl.trollcraft.bending.help.disable.DisableTask;
import pl.trollcraft.bending.magic.ability.Abilities;
import pl.trollcraft.bending.magic.ability.Ability;
import pl.trollcraft.bending.magic.bender.Bender;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Importer implements DisableTask {

    private ArrayList<String> importedPlayers;
    private Database database;

    // -------- -------- -------- --------

    public Importer(Database database) {
        importedPlayers = new ArrayList<>();
        this.database = database;

        try {

            database.open();
            database.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // -------- -------- -------- --------

    public Bender importBender(String playerName) {

        Connection conn = database.connection();

        String sql = "SELECT abilities FROM benders WHERE name=?";

        try {

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, playerName);

            ResultSet res = statement.executeQuery();

            if (res.next()) {

                String abilitiesString = res.getString("abilities");
                abilitiesString = abilitiesString.substring(0, abilitiesString.length() - 2);
                String[] abilities = abilitiesString.split(",");

                Bender bender = new Bender(playerName);
                Ability ability;
                register(playerName);

                for (String abilityName : abilities) {

                    ability = Abilities.find(abilityName);
                    if (ability == null) {
                        Bukkit.getConsoleSender().sendMessage("Warning, null ability - " + abilityName + ". Import failed for " + playerName);
                    }
                    else
                        bender.getAbilities().add(ability);

                }

                return bender;

            }
            else return null;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;

    }

    // -------- -------- -------- --------

    private void register(String playerName) {
        if (!importedPlayers.contains(playerName))
            importedPlayers.add(playerName);
    }

    // -------- -------- -------- --------

    @Override
    public void execute() {
        YamlConfiguration conf = Configs.load("imported.yml");
        conf.set("imported", importedPlayers);
        Configs.save(conf, "imported.yml");
    }


    // -------- -------- -------- --------

    public boolean wasImported(String playerName) { return importedPlayers.contains(playerName); }

}
