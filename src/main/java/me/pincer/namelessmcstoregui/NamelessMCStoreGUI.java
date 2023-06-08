package me.pincer.namelessmcstoregui;

import com.samjakob.spigui.SpiGUI;
import me.pincer.namelessmcstoregui.command.WebStoreCommand;
import me.pincer.namelessmcstoregui.objects.Category;
import me.pincer.namelessmcstoregui.objects.Product;
import org.bukkit.Bukkit;
import org.bukkit.configuration.Configuration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.logging.Level;

public final class NamelessMCStoreGUI extends JavaPlugin {
    private static NamelessMCStoreGUI instance;
    public static SpiGUI spiGUI;
    public static String storeURL;
    public static HashMap<String, String> configValues;
    @Override
    public void onEnable() {
        instance = this;
        spiGUI = new SpiGUI(this);
        configValues = startAPI();
        getCommand("webstore").setExecutor(new WebStoreCommand());
    }
    @Override
    public void onDisable() {
        
    }

    public static NamelessMCStoreGUI getInstance() {
        return instance;
    }
    private static HashMap<String, String> startAPI() {
        final HashMap<String, String> values = new HashMap<>();
        MasterConfig cFile = new MasterConfig("config", "none");
        Configuration config = cFile.getConfig();
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!config.isSet("api-credentials")) {
                    config.set("api-credentials.api-url", "https://mywebsite.com/api/v2");
                    config.set("api-credentials.api-key", "supersecretkey");
                    config.set("api-credentials.timeout-seconds", 15);
                    cFile.saveConfig();
                }
                values.put("api-url", config.getString("api-credentials.api-url"));
                values.put("api-key", config.getString("api-credentials.api-key"));
                values.put("timeout-seconds", config.getString("api-credentials.timeout-seconds"));

                if (!config.isSet("settings")) {
                    config.set("settings.primary-color", "NOT-IMPLEMENTED-YET");
                    config.set("settings.store-url", "https://pizzacraft.com/store");
                    cFile.saveConfig();
                }
                values.put("color", config.getString("settings.primary-color"));
                storeURL = config.getString("settings.store-url");
                Category.fetchCategories();
                Product.fetchProducts();

            }
        }.runTaskAsynchronously(instance);

        return values;
    }
    public static void log(String msg){
        Bukkit.getLogger().log(Level.WARNING, msg);
    }
}
