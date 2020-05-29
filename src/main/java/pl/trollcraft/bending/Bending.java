package pl.trollcraft.bending;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import pl.trollcraft.bending.commands.BindCommand;
import pl.trollcraft.bending.commands.ManageElementsCommand;
import pl.trollcraft.bending.commands.element.CommandListener;
import pl.trollcraft.bending.help.Configs;
import pl.trollcraft.bending.help.KorraLinker;
import pl.trollcraft.bending.help.database.DatabaseManager;
import pl.trollcraft.bending.magic.ability.Abilities;
import pl.trollcraft.bending.magic.bender.BenderListener;
import pl.trollcraft.bending.magic.creator.CreatorListener;
import pl.trollcraft.bending.commands.ManageAbilitiesCommand;
import pl.trollcraft.bending.help.database.Database;
import pl.trollcraft.bending.magic.element.Elements;

import java.util.logging.Level;

public final class Bending extends JavaPlugin {

    private Database database;
    private KorraLinker korraLinker;

    // -------- -------- --------

    private void loadDatabase() {
        YamlConfiguration conf = Configs.load("database.yml");
        String host = conf.getString("database.host");
        int port = conf.getInt("database.port");
        String username = conf.getString("database.username");
        String password = conf.getString("database.password");
        String database = conf.getString("database.database");
        this.database = new Database(host, port, database, username, password);
    }

    private void loadDependencies() {
        String tag = "[Dependencies] ";

        Plugin korra = getServer().getPluginManager()
                .getPlugin("ProjectKorra");

        if (korra != null && korra.isEnabled()) {
            korraLinker = KorraLinker.getInstance();
            getLogger().log(Level.INFO, tag +
                    "ProjectKorra found.");
        }
        else
            getLogger().log(Level.WARNING, tag +
                    "ProjectKorra not found.");

    }

    private void loadData() {
        Elements.load();
        Abilities.load();
    }

    // -------- -------- --------

    @Override
    public void onEnable() {

        loadDatabase();
        if (!DatabaseManager.init(database)){
            getLogger().log(Level.SEVERE, "An error occurred while connecting to the database.");
            setEnabled(false);
            return;
        }

        loadDependencies();
        loadData();

        getCommand("em").setExecutor(new ManageElementsCommand());
        getCommand("am").setExecutor(new ManageAbilitiesCommand());
        getCommand("bind").setExecutor(new BindCommand());

        getServer().getPluginManager().registerEvents(new BenderListener(), this);
        getServer().getPluginManager().registerEvents(new CreatorListener(), this);
        getServer().getPluginManager().registerEvents(new CommandListener(), this);
    }

    // -------- -------- --------

}
