package me.loving11ish.redlightgreenlight.events;

import me.loving11ish.redlightgreenlight.utils.GameManager;
import me.loving11ish.redlightgreenlight.utils.PlayerInventoryHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;

import java.util.UUID;

public class PlayerKick implements Listener {

    @EventHandler
    public void onPlayerKick(PlayerKickEvent event){
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        if (GameManager.getGame1().contains(uuid)){
            GameManager.leaveGame1(player);
            if (PlayerInventoryHandler.getItems().contains(uuid) && PlayerInventoryHandler.getArmor().contains(uuid)){
                PlayerInventoryHandler.clearInventory(player);
                PlayerInventoryHandler.restoreInventory(player);
            }
        }
        if (GameManager.getPlayersInRound().contains(uuid)){
            GameManager.leaveRound(player);
        }
        if (GameManager.getSpectatingPlayers().contains(uuid)){
            GameManager.leaveSpectating(player);
        }
    }
}