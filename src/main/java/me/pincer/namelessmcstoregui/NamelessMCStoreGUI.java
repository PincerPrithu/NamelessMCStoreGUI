package me.pincer.namelessmcstoregui;

import com.samjakob.spigui.SpiGUI;
import lombok.Getter;
import me.pincer.namelessmcstoregui.command.WebStoreCommand;
import me.pincer.namelessmcstoregui.objects.Category;
import me.pincer.namelessmcstoregui.objects.CustomItem;
import me.pincer.namelessmcstoregui.objects.Product;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public final class NamelessMCStoreGUI extends JavaPlugin {
    @Getter
    private static NamelessMCStoreGUI instance;
    public static SpiGUI spiGUI;
    public static String storeURL;
    public static HashMap<String, String> configValues;
    public static ChatColor color;
    public static HashMap<String, ItemStack> icons = new HashMap<>();
    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        spiGUI = new SpiGUI(this);
        ConfigurationSerialization.registerClass(CustomItem.class);
        getCommand("webstore").setExecutor(new WebStoreCommand());
        setIcons();
        configValues = startAPI();

    }

    public void reload() {
        reloadConfig();
        setIcons();
        configValues = startAPI();
    }

    private HashMap<String, String> startAPI() {
        final HashMap<String, String> values = new HashMap<>();
        Configuration config = getConfig();
        new BukkitRunnable() {
            @Override
            public void run() {
                values.put("api-url", config.getString("api-credentials.api-url"));
                values.put("api-key", config.getString("api-credentials.api-key"));
                values.put("timeout-seconds", config.getString("api-credentials.timeout-seconds"));
                color = ChatColor.valueOf(config.getString("settings.primary-color").toUpperCase());
                storeURL = config.getString("settings.store-url");
                Category.fetchCategories();
            }
        }.runTaskAsynchronously(instance);

        return values;
    }
    public void setIcons() {
        HashMap<String, ItemStack> tIcons = new HashMap<>();
        Configuration config = getConfig();
        for (String key : config.getConfigurationSection("gui-icons").getKeys(false)) {
            Map<String, Object> itemData = config.getConfigurationSection("gui-icons." + key).getValues(false);
            CustomItem customItem = CustomItem.deserialize(itemData);
            tIcons.put(key, customItem.getItem());
        }
        icons = tIcons;
    }

    public static void log(String msg){
        Bukkit.getLogger().log(Level.WARNING, msg);
    }
}
