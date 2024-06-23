package me.pincer.namelessmcstoregui.menu;

import com.samjakob.spigui.buttons.SGButton;
import com.samjakob.spigui.menu.SGMenu;
import me.pincer.namelessmcstoregui.NamelessMCStoreGUI;
import me.pincer.namelessmcstoregui.objects.Category;
import me.pincer.namelessmcstoregui.objects.Product;
import me.pincer.namelessmcstoregui.utils.ChatUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

import static me.pincer.namelessmcstoregui.NamelessMCStoreGUI.icons;
import static me.pincer.namelessmcstoregui.NamelessMCStoreGUI.storeURL;
import static me.pincer.namelessmcstoregui.utils.ChatUtils.encode;

public class ProductMenu {

    private static final int MENU_ROWS = 6;
    private static final int MENU_SIZE = MENU_ROWS * 9;
    private static final int BORDER_START = 0;
    private static final int BORDER_END = 8;
    private static final int BACK_BUTTON_SLOT = 49;
    private static final int[] BORDER_SLOTS = {
            0, 1, 2, 3, 4, 5, 6, 7, 8,   // Top row
            45, 46, 47, 48, 49, 50, 51, 52, 53, // Bottom row
            9, 18, 27, 36 // Left column
    };
    private static final int[] PRODUCT_SLOTS = {
            10, 11, 12, 13, 14, 15, 16,  // 2nd row
            19, 20, 21, 22, 23, 24, 25,  // 3rd row
            28, 29, 30, 31, 32, 33, 34   // 4th row
    };

    public static void openCategoryList(Player player, Category category) {
        SGMenu menu = NamelessMCStoreGUI.spiGUI.create("Shop: " + category.getName(), MENU_ROWS);
        menu.setAutomaticPaginationEnabled(false);

        addBorderItems(menu);
        addProductItems(menu, player, category);
        addBackButton(menu, player);

        player.openInventory(menu.getInventory());
    }

    private static void addBorderItems(SGMenu menu) {
        SGButton glass = new SGButton(encode(icons.get("border-item")));
        for (int slot : BORDER_SLOTS) {
            menu.setButton(slot, glass);
        }
    }

    private static void addProductItems(SGMenu menu, Player player, Category category) {
        int index = 0;
        for (Product product : category.getProducts()) {
            if (index >= PRODUCT_SLOTS.length) {
                break;
            }
            int slot = PRODUCT_SLOTS[index];
            // Encode the product item with the product details
            ItemStack encodedItem = encode(icons.get("product-item"), product);

            SGButton productButton = new SGButton(encodedItem)
                    .withListener((InventoryClickEvent event) -> {
                        player.closeInventory();
                        event.getWhoClicked().sendMessage(ChatColor.GREEN + "View " + product.getName()
                                + " on our webstore: " + storeURL);
                    });
            menu.setButton(slot, productButton);
            index++;
        }
    }

    private static void addBackButton(SGMenu menu, Player player) {
        SGButton backButton = new SGButton(encode(icons.get("back-item"))).withListener((InventoryClickEvent event) -> {
            player.closeInventory();
            MainMenu.openStore(player);
        });
        menu.setButton(BACK_BUTTON_SLOT, backButton);
    }
}
