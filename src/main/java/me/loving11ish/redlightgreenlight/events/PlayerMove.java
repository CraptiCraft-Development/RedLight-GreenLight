package me.loving11ish.redlightgreenlight.events;

import com.tcoded.folialib.FoliaLib;
import me.loving11ish.redlightgreenlight.RedLightGreenLight;
import me.loving11ish.redlightgreenlight.utils.*;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.UUID;

import static org.bukkit.Bukkit.getServer;

public class PlayerMove implements Listener {

    private final FoliaLib foliaLib = RedLightGreenLight.getPlugin().getFoliaLib();

    @EventHandler
    public void countDownPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        if (GameManager.getGame1().contains(uuid)) {
            if (GameManager.getCountDown().equals(1)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        String target = player.getName();
        if (GameManager.getGame1().contains(uuid)) {
            if (GameManager.getPlayersInRound().contains(uuid)) {
                if (!(GameManager.getLightGreen().equals(0))) {
                    if (!(GameManager.getCountDown().equals(0))) {
                        return;
                    }
                    if (!CountDownTasksUtils.wrappedTask4.isCancelled()) {
                        return;
                    }
                    player.setGameMode(GameMode.SURVIVAL);
                    player.sendTitle(ColorUtils.translateColorCodes(RedLightGreenLight.getPlugin().getMessagesManager().getGameLoseTitle()),
                            ColorUtils.translateColorCodes(RedLightGreenLight.getPlugin().getMessagesManager().getGameLoseSubtitle()),
                            10, 30, 10);
                    if (RedLightGreenLight.getPlugin().getConfigManager().isSmiteLosingPlayers()) {
                        foliaLib.getScheduler().runNextTick((task) ->
                                getServer().dispatchCommand(Bukkit.getConsoleSender(), "execute at " + player.getName() + " run summon minecraft:lightning_bolt ~ ~ ~"));
                    }
                    if (RedLightGreenLight.getPlugin().getConfigManager().isRunLoseCommands()) {
                        for (String string : RedLightGreenLight.getPlugin().getConfigManager().getLoseCommandsList()) {
                            foliaLib.getScheduler().runNextTick((task) ->
                                    getServer().dispatchCommand(Bukkit.getConsoleSender(), string.replace("%player%", target)));
                        }
                    }
                    if (RedLightGreenLight.getPlugin().getConfigManager().isLosersSpectateGame()) {
                        GameManager.spectatorTeleportToArena(player);
                        MessageUtils.sendPlayer(player, RedLightGreenLight.getPlugin().getMessagesManager().getSpectatingGame());
                    } else {
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
    public void playerBlockMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        String target = player.getName();
        Location blockUnder = player.getLocation();
        Location bottomBlock = player.getLocation();
        blockUnder.setY(blockUnder.getY() - 1);
        bottomBlock.setY(bottomBlock.getY() - 2);
        if (blockUnder.getBlock().getType().equals(RedLightGreenLight.getPlugin().getConfigManager().getTopTriggerBlock()) &&
                bottomBlock.getBlock().getType().equals(RedLightGreenLight.getPlugin().getConfigManager().getBottomTriggerBlock())) {
            if (GameManager.getGame1().contains(uuid)) {
                if (GameManager.getPlayersInRound().contains(uuid)) {
                    player.sendTitle(ColorUtils.translateColorCodes(RedLightGreenLight.getPlugin().getMessagesManager().getGameWinTitle()),
                            ColorUtils.translateColorCodes(RedLightGreenLight.getPlugin().getMessagesManager().getGameWinSubtitle()),
                            10, 30, 10);
                    foliaLib.getScheduler().runNextTick((task) ->
                            getServer().dispatchCommand(Bukkit.getConsoleSender(), "execute at " + player.getName() + " run summon minecraft:firework_rocket ~ ~ ~"));
                    if (RedLightGreenLight.getPlugin().getConfigManager().isRunWinCommands()) {
                        for (String string : RedLightGreenLight.getPlugin().getConfigManager().getWinCommandsList()) {
                            foliaLib.getScheduler().runNextTick((task) ->
                                    getServer().dispatchCommand(Bukkit.getConsoleSender(), string.replace("%player%", target)));
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
