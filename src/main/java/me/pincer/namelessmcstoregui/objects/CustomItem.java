package me.pincer.namelessmcstoregui.objects;

import com.cryptomorin.xseries.XMaterial;
import lombok.Getter;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomItem implements ConfigurationSerializable {
    private final String displayName;
    private final String[] lore;
    private final ItemStack material;
    @Getter
    private final ItemStack item;
    public CustomItem(String name, String[] lore, String material, int quantity) {
        this.displayName = name;
        this.lore = lore;
        this.material = new ItemStack(XMaterial.valueOf(material).parseItem().getType(), quantity);
        ItemStack tempItem = new ItemStack(XMaterial.valueOf(material).parseItem().getType(), quantity);
        ItemMeta meta = tempItem.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(lore));
        tempItem.setItemMeta(meta);
        this.item = tempItem;
    }


    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("display-name", this.displayName);
        map.put("lore", this.lore);
        map.put("material", this.material.getType().name());
        map.put("quantity", this.material.getAmount());
        return map;
    }

    public static CustomItem deserialize(Map<String, Object> map) {
        String name = (String) map.get("display-name");
        List<String> loreList = (List<String>) map.get("lore");
        String[] lore = loreList.toArray(new String[0]);
        String material = (String) map.get("material");
        int quantity = (int) map.get("quantity");
        return new CustomItem(name, lore, material, quantity);
    }
}
