package me.loving11ish.redlightgreenlight.events;

import me.loving11ish.redlightgreenlight.utils.GameManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

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
    @EventHandler
    public void onOffHand(PlayerSwapHandItemsEvent event){
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        if (GameManager.getGame1().contains(uuid)){
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void onItemRightClick(PlayerInteractEvent event){
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        if (event.getAction().equals(Action.RIGHT_CLICK_AIR)){
            if (GameManager.getGame1().contains(uuid)){
                event.setCancelled(true);
            }
        }
        if (event.getAction().equals(Action.LEFT_CLICK_AIR)){
            if (GameManager.getGame1().contains(uuid)){
                event.setCancelled(true);
            }
        }
        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
            if (GameManager.getGame1().contains(uuid)){
                event.setCancelled(true);
            }
        }
        if (event.getAction().equals(Action.LEFT_CLICK_BLOCK)){
            if (GameManager.getGame1().contains(uuid)){
                event.setCancelled(true);
            }
        }
    }
}
