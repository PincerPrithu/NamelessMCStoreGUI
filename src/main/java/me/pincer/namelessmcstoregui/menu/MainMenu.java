package me.pincer.namelessmcstoregui.menu;

import com.samjakob.spigui.buttons.SGButton;
import com.samjakob.spigui.menu.SGMenu;
import me.pincer.namelessmcstoregui.NamelessMCStoreGUI;
import me.pincer.namelessmcstoregui.objects.Category;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.HashMap;

import static me.pincer.namelessmcstoregui.NamelessMCStoreGUI.icons;
import static me.pincer.namelessmcstoregui.utils.ChatUtils.encode;

public class MainMenu {

    public static HashMap<Integer, Category> catMap = new HashMap<>();

    public static void openStore(Player player) {
        // Create a GUI with 6 rows (54 slots)
        SGMenu menu = NamelessMCStoreGUI.spiGUI.create("Server Store", 6);
        menu.setAutomaticPaginationEnabled(false);

        // Create the border button
        SGButton glass = new SGButton(encode(icons.get("border-item")));

        // Add the border buttons to the menu
        for (int i = 0; i < 54; i++) {
            if (i < 9 || i > 44 || i % 9 == 0 || i == 17 || i == 26 || i == 35 || i == 44) {
                menu.setButton(i, glass);
            }
        }

        // Define the slots where category buttons will be placed
        int[] categorySlots = {20, 21, 22, 23, 24, 29, 30, 31, 32, 33};
        int slotIndex = 0;

        // Add the category buttons to the menu
        for (Category category : Category.getCategories()) {
            if (slotIndex >= categorySlots.length) break;
            catMap.put(categorySlots[slotIndex], category);
            SGButton categoryButton = new SGButton(encode(icons.get("category-item"), category))
                    .withListener((InventoryClickEvent event) -> {
                        ProductMenu.openCategoryList(player, catMap.get(event.getSlot()));
                    });

            menu.setButton(categorySlots[slotIndex], categoryButton);
            slotIndex++;
        }

        // Create and add the close button
        SGButton close = new SGButton(encode(icons.get("close-item"))).withListener((InventoryClickEvent event) -> {
            player.closeInventory();
        });

        menu.setButton(49, close);

        // Show the GUI
        player.openInventory(menu.getInventory());
    }
}
