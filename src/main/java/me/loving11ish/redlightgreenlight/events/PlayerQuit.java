package me.loving11ish.redlightgreenlight.events;

import me.loving11ish.redlightgreenlight.RedLightGreenLight;
import me.loving11ish.redlightgreenlight.utils.GameManager;
import me.loving11ish.redlightgreenlight.utils.PlayerInventoryHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

import static org.bukkit.Bukkit.getServer;

public class PlayerQuit implements Listener {

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
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
            if (RedLightGreenLight.getPlugin().getConfig().getBoolean("Smite-losing-players")){
                getServer().dispatchCommand(Bukkit.getConsoleSender(), "execute at " + player.getName() + " run summon minecraft:lightning_bolt ~ ~ ~");
            }
            GameManager.leaveRound(player);
        }
        if (GameManager.getSpectatingPlayers().contains(uuid)){
            if (RedLightGreenLight.getPlugin().getConfig().getBoolean("Smite-losing-players")){
                getServer().dispatchCommand(Bukkit.getConsoleSender(), "execute at " + player.getName() + " run summon minecraft:lightning_bolt ~ ~ ~");
            }
            GameManager.leaveSpectating(player);
        }
    }
}
