package me.pincer.namelessmcstoregui.utils;

import me.pincer.namelessmcstoregui.objects.Category;
import me.pincer.namelessmcstoregui.objects.Product;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ChatUtils {

    // Translate color codes
    public static String c(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    // Encode color codes in ItemStack display name and lore
    public static ItemStack encode(ItemStack item) {
        if (item == null) return null;
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return item;

        if (meta.hasDisplayName()) {
            meta.setDisplayName(c(meta.getDisplayName()));
        }
        if (meta.hasLore()) {
            List<String> lore = meta.getLore();
            if (lore != null) {
                lore = lore.stream().map(ChatUtils::c).collect(Collectors.toList());
                meta.setLore(lore);
            }
        }
        item.setItemMeta(meta);
        return item;
    }

    // Encode placeholders specific to a Category into ItemStack
    public static ItemStack encode(ItemStack item, Category category) {
        if (category == null) return item;

        Map<String, String> placeholders = new HashMap<>();
        placeholders.put("{category_name}", category.getName());
        placeholders.put("{category_id}", String.valueOf(category.getId()));
        placeholders.put("{category_description}", ""); // Replace with actual description if available
        placeholders.put("{number_of_products}", String.valueOf(category.getProducts().size()));

        return encode(item, placeholders);
    }

    // Encode placeholders specific to a Product into ItemStack
    public static ItemStack encode(ItemStack item, Product product) {
        if (product == null) return item;

        Map<String, String> placeholders = new HashMap<>();
        placeholders.put("{product_name}", product.getName());
        placeholders.put("{product_price}", String.valueOf(product.getPrice()));
        placeholders.put("{product_id}", String.valueOf(product.getId()));
        placeholders.put("{product_url}", ""); // Replace with actual URL if available

        return encode(item, placeholders);
    }

    // Replace placeholders in ItemStack
    private static ItemStack encode(ItemStack item, Map<String, String> placeholders) {
        if (item == null || placeholders == null) return item;

        // Clone the item to ensure we are not modifying the original
        ItemStack clonedItem = item.clone();
        ItemMeta meta = clonedItem.getItemMeta();
        if (meta == null) return item;

        // Replace placeholders in the display name
        if (meta.hasDisplayName()) {
            meta.setDisplayName(replacePlaceholders(meta.getDisplayName(), placeholders));
        }

        // Replace placeholders in the lore
        if (meta.hasLore()) {
            List<String> lore = meta.getLore();
            if (lore != null) {
                lore = lore.stream().map(line -> replacePlaceholders(line, placeholders)).collect(Collectors.toList());
                meta.setLore(lore);
            }
        }

        // Set the modified meta back to the cloned item
        clonedItem.setItemMeta(meta);
        return clonedItem;
    }

    // Encode placeholders specific to a Product into a String message
    public static String encode(String message, Product product) {
        if (product == null) return message;

        return message.replace("{product_name}", product.getName())
                .replace("{product_price}", String.valueOf(product.getPrice()))
                .replace("{product_description}", "") // Replace with actual description if available
                .replace("{product_id}", String.valueOf(product.getId()))
                .replace("{product_url}", ""); // Replace with actual URL if available
    }

    // Replace placeholders in a String message
    private static String replacePlaceholders(String message, Map<String, String> placeholders) {
        for (Map.Entry<String, String> entry : placeholders.entrySet()) {
            message = message.replace(entry.getKey(), entry.getValue());
        }
        return c(message);
    }
}
