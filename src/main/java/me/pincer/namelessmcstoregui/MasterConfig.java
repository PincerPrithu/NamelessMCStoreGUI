package me.pincer.namelessmcstoregui;

import lombok.Getter;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

import static me.pincer.namelessmcstoregui.NamelessMCStoreGUI.log;
import static org.bukkit.configuration.file.YamlConfiguration.loadConfiguration;

public class MasterConfig {
    @Getter
    public final String configName;
    public final File file;
    public YamlConfiguration config;

    // Define config using this
    public MasterConfig(String configName, String subdirectory) {
        if (subdirectory.toUpperCase().equals("NONE")) subdirectory = "";
        this.configName = configName;
        this.file = new File(NamelessMCStoreGUI.getInstance().getDataFolder() + "/" + subdirectory, configName +".yml");
        this.config = loadConfiguration(file);
    }

    // Returns the config
    public YamlConfiguration getConfig() {
        return this.config;
    }

    // Saves the config
    public void saveConfig () {
        try {
            this.config.save(this.file);
        } catch (Exception e) {
            log("Failed to save YAML file: " + file.getName());
            log(e.getMessage());
        }
    }

    // Reloads the config
    public void reloadConfig() {
        try {
            this.config.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            log(e.getMessage());
        }
    }

}
