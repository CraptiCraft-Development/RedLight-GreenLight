package me.loving11ish.redlightgreenlight.events;

import me.loving11ish.redlightgreenlight.RedLightGreenLight;
import me.loving11ish.redlightgreenlight.utils.ColorUtils;
import me.loving11ish.redlightgreenlight.utils.CountDownTasksUtils;
import me.loving11ish.redlightgreenlight.utils.GameManager;
import me.loving11ish.redlightgreenlight.utils.PlayerInventoryHandler;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.List;
import java.util.UUID;

import static org.bukkit.Bukkit.getServer;

public class PlayerMove implements Listener {

    List<String> wincommands = RedLightGreenLight.getPlugin().getConfig().getStringList("Win-commands-list");
    List<String> losecommands = RedLightGreenLight.getPlugin().getConfig().getStringList("Lose-commands-list");


    @EventHandler
    public void countDownPlayerMove(PlayerMoveEvent event){
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        if (GameManager.getGame1().contains(uuid)) {
            if (GameManager.getCountDown().equals(1)){
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event){
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        String target = player.getName();
        if (GameManager.getGame1().contains(uuid)){
            if (GameManager.getPlayersInRound().contains(uuid)){
                if (!(GameManager.getLightgreen().equals(0))){
                    if (!(GameManager.getCountDown().equals(0))){
                        return;
                    }
                    if (Bukkit.getScheduler().isCurrentlyRunning(CountDownTasksUtils.taskID4) || Bukkit.getScheduler().isQueued(CountDownTasksUtils.taskID4)){
                        return;
                    }
                    player.setGameMode(GameMode.SURVIVAL);
                    player.sendTitle(ColorUtils.translateColorCodes(RedLightGreenLight.getPlugin().getConfig().getString("Game-loose-title")),
                            ColorUtils.translateColorCodes(RedLightGreenLight.getPlugin().getConfig().getString("Game-loose-subtitle")),
                            10, 30, 10);
                    if (RedLightGreenLight.getPlugin().getConfig().getBoolean("Smite-losing-players")){
                        getServer().dispatchCommand(Bukkit.getConsoleSender(), "execute at " + player.getName() + " run summon minecraft:lightning_bolt ~ ~ ~");
                    }
                    if (RedLightGreenLight.getPlugin().getConfig().getBoolean("Run-lose-commands")){
                        for (String string : losecommands) {
                            getServer().dispatchCommand(Bukkit.getConsoleSender(), string.replace("%player%", target));
                        }
                    }
                    if (RedLightGreenLight.getPlugin().getConfig().getBoolean("Losers-spectate-game")){
                        GameManager.spectatorTeleportToArena(player);
                        player.sendMessage(ColorUtils.translateColorCodes(RedLightGreenLight.getPlugin().getConfig().getString("Spectating-message")));
                    }else {
                        GameManager.teleportToLobby(player);
                        PlayerInventoryHandler.clearInventory(player);
                        PlayerInventoryHandler.restoreInventory(player);
                    }
                    GameManager.leaveRound(player);
                    GameManager.leaveGame1(player);
                }
            }
        }
    }

    @EventHandler
    public void playerBlockMove(PlayerMoveEvent event){
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        String target = player.getName();
        Location blockunder = player.getLocation();
        Location bottomblock = player.getLocation();
        blockunder.setY(blockunder.getY() - 1);
        bottomblock.setY(bottomblock.getY() - 2);
        if (blockunder.getBlock().getType().equals(Material.getMaterial(RedLightGreenLight.getPlugin().getConfig().getString("Top-trigger-block"))) &&
                bottomblock.getBlock().getType().equals(Material.getMaterial(RedLightGreenLight.getPlugin().getConfig().getString("Bottom-trigger-block")))){
            if (GameManager.getGame1().contains(uuid)){
                if (GameManager.getPlayersInRound().contains(uuid)){
                    player.sendTitle(ColorUtils.translateColorCodes(RedLightGreenLight.getPlugin().getConfig().getString("Game-win-title")),
                            ColorUtils.translateColorCodes(RedLightGreenLight.getPlugin().getConfig().getString("Game-win-subtitle")),
                            10, 30, 10);
                    getServer().dispatchCommand(Bukkit.getConsoleSender(), "execute at " + player.getName() + " run summon minecraft:firework_rocket ~ ~ ~");
                    if (RedLightGreenLight.getPlugin().getConfig().getBoolean("Run-win-commands")){
                        for (String string : wincommands) {
                            getServer().dispatchCommand(Bukkit.getConsoleSender(), string.replace("%player%", target));
                        }
                    }
                    GameManager.teleportToLobby(player);
                    PlayerInventoryHandler.clearInventory(player);
                    PlayerInventoryHandler.restoreInventory(player);
                    GameManager.leaveRound(player);
                    GameManager.leaveGame1(player);

                }
            }
        }
    }
}
