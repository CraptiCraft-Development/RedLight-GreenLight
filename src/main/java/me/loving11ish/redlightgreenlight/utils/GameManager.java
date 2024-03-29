package me.loving11ish.redlightgreenlight.utils;

import io.papermc.lib.PaperLib;
import me.loving11ish.redlightgreenlight.RedLightGreenLight;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.*;

public class GameManager {

    private static Map<UUID, Player> game1 = new HashMap<>();
    private static Map<UUID, Player> round = new HashMap<>();
    private static Map<UUID, Player> spectating = new HashMap<>();
    public static Integer lightgreen = 0;
    public static Integer gamerunning = 0;
    public static Integer countdown = 0;

    public static void addToGame1(Player player){
        UUID uuid = player.getUniqueId();
        game1.put(uuid, player);
    }

    public static void leaveGame1(Player player){
        UUID uuid = player.getUniqueId();
        game1.remove(uuid);
    }

    public static Set<UUID> getGame1(){
        return game1.keySet();
    }

    public static void addToRound(Player player){
        UUID uuid = player.getUniqueId();
        round.put(uuid, player);
    }

    public static void leaveRound(Player player){
        UUID uuid = player.getUniqueId();
        round.remove(uuid);
    }

    public static Set<UUID> getPlayersInRound(){
        return round.keySet();
    }

    public static void addToSpectating(Player player){
        UUID uuid = player.getUniqueId();
        spectating.put(uuid, player);
    }

    public static void leaveSpectating(Player player){
        UUID uuid = player.getUniqueId();
        spectating.remove(uuid);
    }

    public static Set<UUID> getSpectatingPlayers(){
        return spectating.keySet();
    }

    public static Integer getLightgreen() {
        return lightgreen;
    }

    public static void setLightgreen(Integer lightgreen) {
        GameManager.lightgreen = lightgreen;
    }

    public static Integer getGameRunning() {
        return gamerunning;
    }

    public static Integer getCountDown() {
        return countdown;
    }

    public static void setCountDown(Integer countdown) {
        GameManager.countdown = countdown;
    }

    public static void setGameRunning(Integer gamerunning) {
        GameManager.gamerunning = gamerunning;
    }

    public static void teleportToLobby(Player player){
        double x = RedLightGreenLight.getPlugin().getConfig().getDouble("lobby-x");
        double y = RedLightGreenLight.getPlugin().getConfig().getDouble("lobby-y");
        double z = RedLightGreenLight.getPlugin().getConfig().getDouble("lobby-z");
        float yaw = (float) RedLightGreenLight.getPlugin().getConfig().getDouble("lobby-yaw");
        float pitch = (float) RedLightGreenLight.getPlugin().getConfig().getDouble("lobby-pitch");
        Location location = new Location(player.getWorld(), x, y, z, yaw, pitch);
        PaperLib.teleportAsync(player, location);
        player.setInvulnerable(RedLightGreenLight.getPlugin().getConfig().getBoolean("Leave-player-invulnerable"));
        if (!(player.hasPermission("redlight.bypass.gamemode")||player.hasPermission("redlight.*")||player.isOp())){
            player.setGameMode(GameMode.SURVIVAL);
        }
    }

    public static void spectatorTeleportToArena(Player player){
        double x = RedLightGreenLight.getPlugin().getConfig().getDouble("Arena-spectate-x");
        double y = RedLightGreenLight.getPlugin().getConfig().getDouble("Arena-spectate-y");
        double z = RedLightGreenLight.getPlugin().getConfig().getDouble("Arena-spectate-z");
        float yaw = (float) RedLightGreenLight.getPlugin().getConfig().getDouble("Arena-spectate-yaw");
        float pitch = (float) RedLightGreenLight.getPlugin().getConfig().getDouble("Arena-spectate-pitch");
        Location location = new Location(player.getWorld(), x, y, z, yaw, pitch);
        PaperLib.teleportAsync(player, location);
        player.setGameMode(GameMode.SPECTATOR);
        GameManager.addToSpectating(player);
    }

    public static void startGameArena1(Player player){
        if (getGame1().size() == RedLightGreenLight.getPlugin().getConfig().getInt("Arena-start-size")){
            CountDownTasksUtils.runTaskStartArena1();
            player.sendMessage(ColorUtils.translateColorCodes(RedLightGreenLight.getPlugin().getConfig().getString("Joined-game")));
        }else {
            player.sendMessage(ColorUtils.translateColorCodes(RedLightGreenLight.getPlugin().getConfig().getString("Waiting-for-enough-players")));
        }
    }

    public static void endSpectatingGame(){
        ArrayList<UUID> spectators = new ArrayList<>(GameManager.getSpectatingPlayers());
        for (int i = 0; i < spectators.size(); i++){
            UUID uuid = spectators.get(i);
            Player player = (Player) Bukkit.getServer().getOfflinePlayer(uuid);
            double x = RedLightGreenLight.getPlugin().getConfig().getDouble("lobby-x");
            double y = RedLightGreenLight.getPlugin().getConfig().getDouble("lobby-y");
            double z = RedLightGreenLight.getPlugin().getConfig().getDouble("lobby-z");
            float yaw = (float) RedLightGreenLight.getPlugin().getConfig().getDouble("lobby-yaw");
            float pitch = (float) RedLightGreenLight.getPlugin().getConfig().getDouble("lobby-pitch");
            Location location = new Location(player.getWorld(), x, y, z, yaw, pitch);
            PaperLib.teleportAsync(player, location);
            if (RedLightGreenLight.getPlugin().getConfig().getBoolean("Show-leave-game-title")){
                player.sendTitle(ColorUtils.translateColorCodes(RedLightGreenLight.getPlugin().getConfig().getString("Game-leave-title")),
                        ColorUtils.translateColorCodes(RedLightGreenLight.getPlugin().getConfig().getString("Game-leave-subtitle")), 20, 80, 20);
            }
            PlayerInventoryHandler.clearInventory(player);
            PlayerInventoryHandler.restoreInventory(player);
            if (!(player.hasPermission("redlight.bypass.gamemode")||player.hasPermission("redlight.*")||player.isOp())){
                player.setGameMode(GameMode.SURVIVAL);
            }
            player.setInvulnerable(RedLightGreenLight.getPlugin().getConfig().getBoolean("Leave-player-invulnerable"));
            player.setFoodLevel(20);
            GameManager.leaveSpectating(player);
        }
    }

    public static void endGameArena1(){
        ArrayList<UUID> game = new ArrayList<>(GameManager.getGame1());
        for (int i = 0; i < game.size(); i++){
            UUID uuid = game.get(i);
            Player player = (Player) Bukkit.getServer().getOfflinePlayer(uuid);
            double x = RedLightGreenLight.getPlugin().getConfig().getDouble("lobby-x");
            double y = RedLightGreenLight.getPlugin().getConfig().getDouble("lobby-y");
            double z = RedLightGreenLight.getPlugin().getConfig().getDouble("lobby-z");
            float yaw = (float) RedLightGreenLight.getPlugin().getConfig().getDouble("lobby-yaw");
            float pitch = (float) RedLightGreenLight.getPlugin().getConfig().getDouble("lobby-pitch");
            Location location = new Location(player.getWorld(), x, y, z, yaw, pitch);
            PaperLib.teleportAsync(player, location);
            if (RedLightGreenLight.getPlugin().getConfig().getBoolean("Show-leave-game-title")){
                player.sendTitle(ColorUtils.translateColorCodes(RedLightGreenLight.getPlugin().getConfig().getString("Game-leave-title")),
                        ColorUtils.translateColorCodes(RedLightGreenLight.getPlugin().getConfig().getString("Game-leave-subtitle")), 20, 80, 20);
            }
            PlayerInventoryHandler.clearInventory(player);
            PlayerInventoryHandler.restoreInventory(player);
            if (!(player.hasPermission("redlight.bypass.gamemode")||player.hasPermission("redlight.*")||player.isOp())){
                player.setGameMode(GameMode.SURVIVAL);
            }
            player.setInvulnerable(RedLightGreenLight.getPlugin().getConfig().getBoolean("Leave-player-invulnerable"));
            player.setFoodLevel(20);
            GameManager.leaveGame1(player);
            GameManager.leaveRound(player);
            GameManager.setGameRunning(0);
        }
    }
}
