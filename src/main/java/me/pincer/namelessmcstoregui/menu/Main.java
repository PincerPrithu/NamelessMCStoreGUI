package me.pincer.namelessmcstoregui.menu;

import com.samjakob.spigui.buttons.SGButton;
import com.samjakob.spigui.item.ItemBuilder;
import com.samjakob.spigui.menu.SGMenu;
import me.pincer.namelessmcstoregui.NamelessMCStoreGUI;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import static me.pincer.namelessmcstoregui.objects.Category.categories;
import static me.pincer.namelessmcstoregui.objects.Category.getCatByName;
import static me.pincer.namelessmcstoregui.objects.Product.productMap;

public class Main {
    public static void openStore(Player player) {

        // Create a GUI with 3 rows (27 slots)
        SGMenu menu = NamelessMCStoreGUI.spiGUI.create("Server Store", 6);
        menu.setAutomaticPaginationEnabled(false);

        // Create a button
        SGButton glass = new SGButton(
                // Includes an ItemBuilder class with chainable methods to easily
                // create menu items.
                new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).build()
        ).withListener((InventoryClickEvent event) -> {
            event.getWhoClicked().sendMessage("Hello, world!");
        });

        // Add the button to your
        int i = 0;
        int x = 0;
        while (i < 54) {
            if (i < 9 || i > 44) {
                menu.setButton(i, glass);
            } else if (i % 9 == 0 || i == 17  || i  == 26 || i == 35 || i == 44 || i == 53) {
                menu.setButton(i, glass);
            } else if ((i > 19 && i < 30 && i != 25 && i != 28) && categories.size() != x) {
                SGButton category = new SGButton(
                        new ItemBuilder(Material.CHEST)
                                .name(ChatColor.GREEN + "" + ChatColor.BOLD + categories.get(x).getName())
                                .lore(ChatColor.GRAY + "" + productMap.get(getCatByName(categories.get(x).getName())).size() + " Products").build()
                ).withListener((InventoryClickEvent event) -> {
                    Player player1 = (Player) event.getWhoClicked();
                    ProductMenu.openCategoryList(player, getCatByName(ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName())));
                });

                menu.setButton(i, category);
                x++;

            }
            i++;
        }

        SGButton close = new SGButton(new ItemBuilder(Material.RED_STAINED_GLASS_PANE).name(ChatColor.RED + "Close Menu")
                .build()).withListener((InventoryClickEvent event) -> {
            player.closeInventory();
        });
        menu.setButton(49, close);

        // Show the GUI
        player.openInventory(menu.getInventory());
    }
}
