package pl.trollcraft.bending;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import pl.trollcraft.bending.commands.*;
import pl.trollcraft.bending.commands.element.CommandListener;
import pl.trollcraft.bending.commands.manage.ManageAbilitiesCommand;
import pl.trollcraft.bending.commands.manage.ManageElementsCommand;
import pl.trollcraft.bending.commands.manage.ManageRaritiesCommand;
import pl.trollcraft.bending.commands.manage.ManageTitlesCommand;
import pl.trollcraft.bending.help.Configs;
import pl.trollcraft.bending.help.KorraLinker;
import pl.trollcraft.bending.help.MoveDetector;
import pl.trollcraft.bending.help.Placeholders;
import pl.trollcraft.bending.help.database.Importer;
import pl.trollcraft.bending.help.disable.DisableManager;
import pl.trollcraft.bending.help.menu.Menus;
import pl.trollcraft.bending.magic.bender.KorraBenderListener;
import pl.trollcraft.bending.magic.board.listeners.BoardListener;
import pl.trollcraft.bending.magic.board.listeners.KorraBoardListener;
import pl.trollcraft.bending.help.database.DatabaseManager;
import pl.trollcraft.bending.magic.ability.Abilities;
import pl.trollcraft.bending.magic.bender.BenderListener;
import pl.trollcraft.bending.magic.creator.CreatorListener;
import pl.trollcraft.bending.help.database.Database;
import pl.trollcraft.bending.magic.element.Elements;
import pl.trollcraft.bending.magic.preferences.PreferencesListener;
import pl.trollcraft.bending.magic.titles.rarity.Rarities;
import pl.trollcraft.bending.magic.titles.title.TitleListener;
import pl.trollcraft.bending.magic.titles.title.Titles;
import pl.trollcraft.bending.magic.titles.titleuser.TitleUserListener;

import java.util.logging.Level;

public final class Bending extends JavaPlugin {

    private static Bending plugin;

    private Database database;
    private Database importDatabase;
    private RedissonClient redisson;

    private boolean importEnabled;
    private boolean hologramsEnabled;

    private MoveDetector moveDetector;

    private KorraLinker korraLinker;

    private Importer importer;

    // -------- -------- --------

    private void loadStorage() {
        YamlConfiguration conf = Configs.load("storage.yml");
        String host = conf.getString("database.host");
        int port = conf.getInt("database.port");
        String username = conf.getString("database.username");
        String password = conf.getString("database.password");
        String database = conf.getString("database.database");
        this.database = new Database(host, port, database, username, password);

        password = conf.getString("redis.password");
        Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://127.0.0.1:6379")
                .setPassword(password);

        redisson = Redisson.create(config);

        importEnabled = conf.getBoolean("import.enabled");
        if (importEnabled) {
            host = conf.getString("import.host");
            port = conf.getInt("import.port");
            username = conf.getString("import.username");
            password = conf.getString("import.password");
            database = conf.getString("import.database");
            this.importDatabase = new Database(host, port, database, username, password);
        }

    }

    private void loadConfig() {
        YamlConfiguration conf = Configs.load("config.yml");
        hologramsEnabled = conf.getBoolean("nametags.enabled");
    }

    private void loadDependencies() {
        String tag = "[Dependencies] ";

        Plugin korra = getServer().getPluginManager()
                .getPlugin("ProjectKorra");

        Plugin placeholders = getServer().getPluginManager()
                .getPlugin("PlaceholderAPI");

        if (korra != null && korra.isEnabled()) {
            korraLinker = KorraLinker.getInstance();
            getLogger().log(Level.INFO, tag +
                    "ProjectKorra found.");
        }
        else
            getLogger().log(Level.WARNING, tag +
                    "ProjectKorra not found.");

        if (placeholders != null && placeholders.isEnabled()) {
            new Placeholders().register();
            getLogger().log(Level.INFO, tag +
                    "PlaceholdersAPI found.");
        }
        else
            getLogger().log(Level.WARNING, tag +
                    "PlaceholdersAPI not found.");

        if (hologramsEnabled) {

            Plugin holograms = getServer().getPluginManager()
                    .getPlugin("HolographicDisplays");

            if (holograms != null && holograms.isEnabled()) {
                getLogger().log(Level.INFO, tag +
                        "HolographicDisplays found.");
            }
            else {
                hologramsEnabled = false;
                getLogger().log(Level.WARNING, tag +
                        "HolographicDisplays not found.");
            }

        }

    }

    private void loadData() {
        Elements.load();
        Abilities.load();
        Rarities.load();
        Titles.load();
    }

    private void loadMisc() {
        moveDetector = new MoveDetector(this, p -> Menus.find(p) != null);
    }

    private void registerDisableTasks() {
        if (importer != null) DisableManager.registerTask(importer);
    }

    // -------- -------- --------

    @Override
    public void onEnable() {
        plugin = this;

        loadStorage();
        if (!DatabaseManager.init(database, redisson)){
            getLogger().log(Level.SEVERE, "An error occurred while connecting to the database.");
            setEnabled(false);
            return;
        }

        if (importEnabled) {
            getLogger().log(Level.INFO, "Import mode is enabled.");
            importer = new Importer(importDatabase);
        }

        loadConfig();
        loadDependencies();
        loadData();
        loadMisc();

        getCommand("em").setExecutor(new ManageElementsCommand());
        getCommand("am").setExecutor(new ManageAbilitiesCommand());
        getCommand("rarity").setExecutor(new ManageRaritiesCommand());
        getCommand("title").setExecutor(new ManageTitlesCommand());
        getCommand("bind").setExecutor(new BindCommand());
        getCommand("clearAbilities").setExecutor(new ClearAbilitiesCommand());
        getCommand("toggleBoard").setExecutor(new ToggleBoardCommand());
        getCommand("myTitles").setExecutor(new MyTitlesCommand());
        getCommand("closeMenu").setExecutor(new CloseMenuCommand());

        if (KorraLinker.isOn())
            getServer().getPluginManager().registerEvents(new BenderListener(), this);
        else
            getServer().getPluginManager().registerEvents(new KorraBenderListener(), this);

        getServer().getPluginManager().registerEvents(new CreatorListener(), this);
        getServer().getPluginManager().registerEvents(new CommandListener(), this);
        getServer().getPluginManager().registerEvents(new PreferencesListener(), this);
        getServer().getPluginManager().registerEvents(new BoardListener(), this);
        getServer().getPluginManager().registerEvents(new TitleUserListener(), this);

        if (KorraLinker.isOn())
            getServer().getPluginManager().registerEvents(new KorraBoardListener(), this);

        if (hologramsEnabled)
            getServer().getPluginManager().registerEvents(new TitleListener(), this);

        registerDisableTasks();

    }

    @Override
    public void onDisable() {
        DisableManager.executeTasks();
    }

    // -------- -------- --------

    public static Bending getPlugin() { return plugin; }

    public boolean isImportEnabled() { return importEnabled; }
    public Importer getImporter() { return importer; }

}
