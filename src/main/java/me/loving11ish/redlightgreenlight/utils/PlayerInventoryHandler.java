package me.loving11ish.redlightgreenlight.utils;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class PlayerInventoryHandler {

    private static final Map<UUID, ItemStack[]> items = new HashMap<>();
    private static final Map<UUID, ItemStack[]> armor = new HashMap<>();

    public static void storeAndClearInventory(Player player){
        UUID uuid = player.getUniqueId();
        ItemStack[] contents = player.getInventory().getContents();
        ItemStack[] armorContents = player.getInventory().getArmorContents();
        items.put(uuid, contents);
        armor.put(uuid, armorContents);
        clearInventory(player);
    }
    public static void restoreInventory(Player player) {
        UUID uuid = player.getUniqueId();
        ItemStack[] contents = items.get(uuid);
        ItemStack[] armorContents = armor.get(uuid);
        if (!(contents == null)){
            player.getInventory().setContents(contents);
        } else {
            player.getInventory().clear();
        }
        if (!(armorContents == null)) {
            player.getInventory().setArmorContents(armorContents);
        } else {
            player.getInventory().setHelmet(null);
            player.getInventory().setChestplate(null);
            player.getInventory().setLeggings(null);
            player.getInventory().setBoots(null);
        }
    }
    public static void clearInventory(Player player) {
        player.getInventory().clear();
        player.getInventory().setHelmet(null);
        player.getInventory().setChestplate(null);
        player.getInventory().setLeggings(null);
        player.getInventory().setBoots(null);
    }
    public static Set<UUID> getItems(){
        return items.keySet();
    }
    public static Set<UUID> getArmor(){
        return armor.keySet();
    }
}
