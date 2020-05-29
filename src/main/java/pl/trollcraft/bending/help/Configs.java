package pl.trollcraft.bending.help;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import pl.trollcraft.bending.Bending;

import java.io.File;
import java.io.IOException;

public class Configs {

    private static final Bending bending = Bending.getPlugin(Bending.class);

    public static YamlConfiguration load(String configName){
        YamlConfiguration config;
        File file = new File(bending.getDataFolder() + File.separator + configName);
        if (!file.exists())
            bending.saveResource(configName, false);
        config = new YamlConfiguration();
        try {
            config.load(file);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
            return null;
        }
        return config;
    }

    public static void save(YamlConfiguration c, String file) {
        try {
            c.save(new File(bending.getDataFolder(), file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
