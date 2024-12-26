package me.loving11ish.redlightgreenlight.utils;

import io.papermc.lib.PaperLib;
import me.loving11ish.redlightgreenlight.RedLightGreenLight;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.concurrent.CompletableFuture;

public class GameManager {

    private static final Map<UUID, Player> game1 = new HashMap<>();
    private static final Map<UUID, Player> round = new HashMap<>();
    private static final Map<UUID, Player> spectating = new HashMap<>();
    public static Integer lightGreen = 0;
    public static Integer gameRunning = 0;
    public static Integer countDown = 0;

    public static void addToGame1(Player player) {
        UUID uuid = player.getUniqueId();
        game1.put(uuid, player);
    }

    public static void leaveGame1(Player player) {
        UUID uuid = player.getUniqueId();
        game1.remove(uuid);
    }

    public static Set<UUID> getGame1() {
        return game1.keySet();
    }

    public static void addToRound(Player player) {
        UUID uuid = player.getUniqueId();
        round.put(uuid, player);
    }

    public static void leaveRound(Player player) {
        UUID uuid = player.getUniqueId();
        round.remove(uuid);
    }

    public static Set<UUID> getPlayersInRound() {
        return round.keySet();
    }

    public static void addToSpectating(Player player) {
        UUID uuid = player.getUniqueId();
        spectating.put(uuid, player);
    }

    public static void leaveSpectating(Player player) {
        UUID uuid = player.getUniqueId();
        spectating.remove(uuid);
    }

    public static Set<UUID> getSpectatingPlayers() {
        return spectating.keySet();
    }

    public static Integer getLightGreen() {
        return lightGreen;
    }

    public static void setLightGreen(Integer lightGreen) {
        GameManager.lightGreen = lightGreen;
    }

    public static Integer getGameRunning() {
        return gameRunning;
    }

    public static Integer getCountDown() {
        return countDown;
    }

    public static void setCountDown(Integer countdown) {
        GameManager.countDown = countdown;
    }

    public static void setGameRunning(Integer gamerunning) {
        GameManager.gameRunning = gamerunning;
    }

    public static void teleportToLobby(Player player) {
        if (player == null) {
            // Handle null player
            MessageUtils.sendConsole("error", "Method: teleportToLobby(Player player)");
            MessageUtils.sendConsole("error", "Player is null!");
            return;
        }

        Location location = RedLightGreenLight.getPlugin().getConfigManager().getLobbyLocation();
        if (location == null) {
            // Handle null location
            MessageUtils.sendConsole("error", "Method: teleportToLobby(Player player)");
            MessageUtils.sendConsole("error", "Lobby location is null!");
            return;
        }

        CompletableFuture<Boolean> future = PaperLib.teleportAsync(player, location);
        future.thenAccept(result -> {
            if (result) {
                player.setInvulnerable(RedLightGreenLight.getPlugin().getConfigManager().isLeavePlayerInvulnerable());
                if (!(player.hasPermission("redlight.bypass.gamemode") || player.hasPermission("redlight.*") || player.isOp())) {
                    player.setGameMode(GameMode.SURVIVAL);
                    MessageUtils.sendDebugConsole("info", "Player " + player.getName() + " did not have bypass permission.");
                }
                MessageUtils.sendDebugConsole("info", "Player " + player.getName() + " teleported to lobby location.");
            } else {
                // Handle teleportation failure
                MessageUtils.sendConsole("error", "Method: teleportToLobby(Player player)");
                MessageUtils.sendConsole("error", "Failed to teleport player to lobby location!");
                MessageUtils.sendConsole("error", "Using PaperLib teleportAsync method.");
                MessageUtils.sendConsole("info", "Trying alternate FoliaLib AsyncTeleport method.");
                CompletableFuture<Boolean> foliaLibFuture = RedLightGreenLight.getPlugin().getFoliaLib().getScheduler().teleportAsync(player, location);
                foliaLibFuture.thenAccept(foliaLibResult -> {
                    if (foliaLibResult) {
                        player.setInvulnerable(RedLightGreenLight.getPlugin().getConfigManager().isLeavePlayerInvulnerable());
                        if (!(player.hasPermission("redlight.bypass.gamemode") || player.hasPermission("redlight.*") || player.isOp())) {
                            player.setGameMode(GameMode.SURVIVAL);
                        }
                        MessageUtils.sendDebugConsole("info", "Player " + player.getName() + " teleported to lobby location.");
                    } else {
                        // Handle fallback teleportation failure
                        MessageUtils.sendConsole("error", "Method: teleportToLobby(Player player)");
                        MessageUtils.sendConsole("error", "Failed to teleport player to lobby location!");
                        MessageUtils.sendConsole("error", "Using FoliaLib AsyncTeleport method.");
                        MessageUtils.sendConsole("severe", "<=============================================>");
                        MessageUtils.sendConsole("severe", "Failed to teleport player to lobby location!");
                        MessageUtils.sendConsole("severe", "Player: " + player.getName());
                        MessageUtils.sendConsole("severe", "Location: " + location);
                        MessageUtils.sendConsole("severe", "Attempted using PaperLib teleportAsync method.");
                        MessageUtils.sendConsole("severe", "Attempted using backup FoliaLib AsyncTeleport method.");
                        MessageUtils.sendConsole("severe", "Please check your lobby location in the config.");
                        MessageUtils.sendConsole("severe", "Please check your server console for more information.");
                        MessageUtils.sendConsole("severe", "Please send your server console log to the plugin developer.");
                        MessageUtils.sendConsole("severe", "Discord: https://discord.gg/crapticraft");
                        MessageUtils.sendConsole("severe", "<=============================================>");
                    }
                });
            }
        });
    }

    public static void spectatorTeleportToArena(Player player) {
        if (player == null) {
            // Handle null player
            MessageUtils.sendConsole("error", "Method: spectatorTeleportToArena(Player player)");
            MessageUtils.sendConsole("error", "Player is null!");
            return;
        }

        Location location = RedLightGreenLight.getPlugin().getConfigManager().getSpectateLocation();
        if (location == null) {
            // Handle null location
            MessageUtils.sendConsole("error", "Method: spectatorTeleportToArena(Player player)");
            MessageUtils.sendConsole("error", "Spectate location is null!");
            return;
        }

        CompletableFuture<Boolean> future = PaperLib.teleportAsync(player, location);
        future.thenAccept(result -> {
            if (result) {
                player.setGameMode(GameMode.SPECTATOR);
                MessageUtils.sendDebugConsole("info", "Player " + player.getName() + " teleported to spectate location.");
            } else {
                // Handle teleportation failure
                MessageUtils.sendConsole("error", "Method: spectatorTeleportToArena(Player player)");
                MessageUtils.sendConsole("error", "Failed to teleport player to spectate location!");
                MessageUtils.sendConsole("error", "Using PaperLib teleportAsync method.");
                MessageUtils.sendConsole("info", "Trying alternate FoliaLib AsyncTeleport method.");
                CompletableFuture<Boolean> foliaLibFuture = RedLightGreenLight.getPlugin().getFoliaLib().getScheduler().teleportAsync(player, location);
                foliaLibFuture.thenAccept(foliaLibResult -> {
                    if (foliaLibResult) {
                        player.setGameMode(GameMode.SPECTATOR);
                        MessageUtils.sendDebugConsole("info", "Player " + player.getName() + " teleported to spectate location.");
                    } else {
                        // Handle fallback teleportation failure
                        MessageUtils.sendConsole("error", "Method: spectatorTeleportToArena(Player player)");
                        MessageUtils.sendConsole("error", "Failed to teleport player to spectate location!");
                        MessageUtils.sendConsole("error", "Using FoliaLib AsyncTeleport method.");
                        MessageUtils.sendConsole("severe", "<=============================================>");
                        MessageUtils.sendConsole("severe", "Failed to teleport player to spectate location!");
                        MessageUtils.sendConsole("severe", "Player: " + player.getName());
                        MessageUtils.sendConsole("severe", "Location: " + location);
                        MessageUtils.sendConsole("severe", "Attempted using PaperLib teleportAsync method.");
                        MessageUtils.sendConsole("severe", "Attempted using backup FoliaLib AsyncTeleport method.");
                        MessageUtils.sendConsole("severe", "Please check your spectate location in the config.");
                        MessageUtils.sendConsole("severe", "Please check your server console for more information.");
                        MessageUtils.sendConsole("severe", "Please send your server console log to the plugin developer.");
                        MessageUtils.sendConsole("severe", "Discord: https://discord.gg/crapticraft");
                        MessageUtils.sendConsole("severe", "<=============================================>");
                    }
                });
            }
        });
    }

    public static void startGameArena1(Player player) {
        if (getGame1().size() == RedLightGreenLight.getPlugin().getConfigManager().getArenaStartPlayerAmount()) {
            CountDownTasksUtils.runTaskStartArena1();
            MessageUtils.sendPlayer(player, RedLightGreenLight.getPlugin().getMessagesManager().getJoinedGame());
        } else {
            MessageUtils.sendPlayer(player, RedLightGreenLight.getPlugin().getMessagesManager().getWaitingForPlayers());
        }
    }

    public static void endSpectatingGame() {
        ArrayList<UUID> spectators = new ArrayList<>(GameManager.getSpectatingPlayers());
        for (UUID uuid : spectators) {
            Player player = (Player) Bukkit.getServer().getOfflinePlayer(uuid);
            RedLightGreenLight.getPlugin().getFoliaLib().getScheduler().runAtEntity(player, (task) -> {
                Location location = RedLightGreenLight.getPlugin().getConfigManager().getLobbyLocation();
                PaperLib.teleportAsync(player, location);
                if (RedLightGreenLight.getPlugin().getMessagesManager().isSendLeaveTitle()) {
                    player.sendTitle(ColorUtils.translateColorCodes(RedLightGreenLight.getPlugin().getMessagesManager().getGameLeaveTitle()),
                            ColorUtils.translateColorCodes(RedLightGreenLight.getPlugin().getMessagesManager().getGameLeaveSubtitle()), 20, 80, 20);
                }
                PlayerInventoryHandler.clearInventory(player);
                PlayerInventoryHandler.restoreInventory(player);
                if (!(player.hasPermission("redlight.bypass.gamemode") || player.hasPermission("redlight.*") || player.isOp())) {
                    player.setGameMode(GameMode.SURVIVAL);
                }
                player.setInvulnerable(RedLightGreenLight.getPlugin().getConfigManager().isLeavePlayerInvulnerable());
                player.setFoodLevel(20);
                GameManager.leaveSpectating(player);
            });
        }
    }

    public static void endGameArena1() {
        ArrayList<UUID> game = new ArrayList<>(GameManager.getGame1());
        for (UUID uuid : game) {
            Player player = (Player) Bukkit.getServer().getOfflinePlayer(uuid);
            RedLightGreenLight.getPlugin().getFoliaLib().getScheduler().runAtEntity(player, (task) -> {
                Location location = RedLightGreenLight.getPlugin().getConfigManager().getLobbyLocation();
                PaperLib.teleportAsync(player, location);
                if (RedLightGreenLight.getPlugin().getMessagesManager().isSendLeaveTitle()) {
                    player.sendTitle(ColorUtils.translateColorCodes(RedLightGreenLight.getPlugin().getMessagesManager().getGameLeaveTitle()),
                            ColorUtils.translateColorCodes(RedLightGreenLight.getPlugin().getMessagesManager().getGameLeaveSubtitle()), 20, 80, 20);
                }
                PlayerInventoryHandler.clearInventory(player);
                PlayerInventoryHandler.restoreInventory(player);
                if (!(player.hasPermission("redlight.bypass.gamemode") || player.hasPermission("redlight.*") || player.isOp())) {
                    player.setGameMode(GameMode.SURVIVAL);
                }
                player.setInvulnerable(RedLightGreenLight.getPlugin().getConfigManager().isLeavePlayerInvulnerable());
                player.setFoodLevel(20);
                GameManager.leaveGame1(player);
                GameManager.leaveRound(player);
            });
        }
        GameManager.setGameRunning(0);
    }
}
