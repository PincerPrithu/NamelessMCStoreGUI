package me.pincer.namelessmcstoregui.menu;

import com.samjakob.spigui.buttons.SGButton;
import com.samjakob.spigui.item.ItemBuilder;
import com.samjakob.spigui.menu.SGMenu;
import me.pincer.namelessmcstoregui.NamelessMCStoreGUI;
import me.pincer.namelessmcstoregui.objects.Category;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import static me.pincer.namelessmcstoregui.NamelessMCStoreGUI.storeURL;
import static me.pincer.namelessmcstoregui.objects.Product.productMap;

public class ProductMenu {
    public static void openCategoryList(Player player, Category category) {

        // Create a GUI with 3 rows (27 slots)
        SGMenu menu = NamelessMCStoreGUI.spiGUI.create("Shop: " + category.getName(), 6);
        menu.setAutomaticPaginationEnabled(false);

        // Create a button
        SGButton glass = new SGButton(new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).build());

        int i = 0;
        int x = 0;
        while (i < 54) {
            if (i < 9 || i > 44) {
                menu.setButton(i, glass);
            } else if (i % 9 == 0 || i == 17  || i  == 26 || i == 35 || i == 44 || i == 53) {
                menu.setButton(i, glass);
            } else if ((i > 19 && i < 30 && i != 25 && i != 28) && productMap.get(category).size() != x) {
                int finalX = x;
                SGButton product = new SGButton(
                        new ItemBuilder(Material.CHEST_MINECART)
                                .name(ChatColor.GREEN + "" + ChatColor.BOLD + productMap.get(category).get(x).getName() + " " +
                                        ChatColor.YELLOW + "($" + productMap.get(category).get(x).getPrice() + ")")
                                .lore(ChatColor.GRAY + "Click to view this item on our web store").build()
                ).withListener((InventoryClickEvent event) -> {
                    player.closeInventory();
                    event.getWhoClicked().sendMessage(ChatColor.GREEN + "View " + productMap.get(category).get(finalX).getName()
                    + " on our webstore: " + storeURL);
                });

                menu.setButton(i, product);
                x++;
            }
            i++;
        }
        SGButton back = new SGButton(new ItemBuilder(Material.RED_STAINED_GLASS_PANE).name(ChatColor.RED + "Back to Categories")
                .build()).withListener((InventoryClickEvent event) -> {
                player.closeInventory();
                Main.openStore(player);
        });
        menu.setButton(49, back);

        // Show the GUI
        player.openInventory(menu.getInventory());

    }
}
