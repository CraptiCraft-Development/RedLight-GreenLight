package me.loving11ish.redlightgreenlight.events;

import me.loving11ish.redlightgreenlight.utils.GameManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

import java.util.UUID;

public class PlayerInventoryAction implements Listener {

    @EventHandler
    public void onMenuClickEvent(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();
        UUID uuid = player.getUniqueId();
        if (GameManager.getGame1().contains(uuid)){
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event){
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        if (GameManager.getGame1().contains(uuid)){
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void onItemPlace(BlockPlaceEvent event){
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        if (GameManager.getGame1().contains(uuid)){
            event.setCancelled(true);
        }
    }
}
