package me.loving11ish.redlightgreenlight.utils;

import com.tcoded.folialib.FoliaLib;
import com.tcoded.folialib.wrapper.task.WrappedTask;
import io.papermc.lib.PaperLib;
import me.loving11ish.redlightgreenlight.RedLightGreenLight;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.bukkit.Bukkit.getServer;

public class CountDownTasksUtils {

    private static FoliaLib foliaLib = RedLightGreenLight.getFoliaLib();

    public static WrappedTask wrappedTask1;
    public static WrappedTask wrappedTask2;
    public static WrappedTask wrappedTask3;
    public static WrappedTask wrappedTask4;

    private static List<String> losecommands = RedLightGreenLight.getPlugin().getConfig().getStringList("Lose-commands-list");

    final static double x = RedLightGreenLight.getPlugin().getConfig().getDouble("Arena-Start-x");
    final static double y = RedLightGreenLight.getPlugin().getConfig().getDouble("Arena-Start-y");
    final static double z = RedLightGreenLight.getPlugin().getConfig().getDouble("Arena-Start-z");
    final static float yaw = (float) RedLightGreenLight.getPlugin().getConfig().getDouble("Arena-Start-yaw");
    final static float pitch = (float) RedLightGreenLight.getPlugin().getConfig().getDouble("Arena-Start-pitch");

    public static void runTaskStartArena1(){
        wrappedTask1 = foliaLib.getImpl().runTimerAsync(new Runnable() {
            Integer time = RedLightGreenLight.getPlugin().getConfig().getInt("Game-starting-countdown-length");
            @Override
            public void run() {
                if (time == 1){
                    ArrayList<UUID> playersInGame = new ArrayList<>(GameManager.getGame1());
                    for (UUID uuid : playersInGame) {
                        Player player = (Player) Bukkit.getServer().getOfflinePlayer(uuid);
                        Location location = new Location(player.getWorld(), x, y, z, yaw, pitch);
                        PaperLib.teleportAsync(player, location);
                        player.setWalkSpeed(0.0f);
                        foliaLib.getImpl().runAtEntityLater(player, () ->
                                player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 20 * 10, 100000, false, false, false)), 50L, TimeUnit.MILLISECONDS);
                    }
                    CountDownTasksUtils.runTaskGoGame1();
                    GameManager.setGameRunning(1);
                    GameManager.setCountDown(1);
                    wrappedTask1.cancel();
                    return;
                }
                else {
                    time --;
                    ArrayList<UUID> playersInGame = new ArrayList<>(GameManager.getGame1());
                    for (UUID uuid : playersInGame) {
                        Player player = (Player) Bukkit.getServer().getOfflinePlayer(uuid);
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ColorUtils.translateColorCodes("&c&lGame Starting In: ") + ColorUtils.translateColorCodes("&c") + time));
                        player.playSound(player.getLocation(), Sound.BLOCK_END_PORTAL_FRAME_FILL, 2, 2);
                    }
                }
            }
        }, 1L, 1L, TimeUnit.SECONDS);
    }

    public static void runTaskGoGame1(){
        wrappedTask2 = foliaLib.getImpl().runTimerAsync(new Runnable() {
            Integer time = 10;
            @Override
            public void run() {
                if(time == 1){
                    ArrayList<UUID> playersInGame = new ArrayList<>(GameManager.getGame1());
                    for (UUID uuid : playersInGame) {
                        Player player = (Player) Bukkit.getServer().getOfflinePlayer(uuid);
                        player.setInvulnerable(RedLightGreenLight.getPlugin().getConfig().getBoolean("Join-player-invulnerable"));
                        player.setHealth(20.0);
                        player.setFoodLevel(20);
                        if (!(player.hasPermission("redlight.bypass.gamemode") || player.hasPermission("redlight.*") || player.isOp())) {
                            player.setGameMode(GameMode.ADVENTURE);
                        }
                        player.sendTitle(ColorUtils.translateColorCodes(RedLightGreenLight.getPlugin().getConfig().getString("Game-start-title")),
                                ColorUtils.translateColorCodes(RedLightGreenLight.getPlugin().getConfig().getString("Game-start-subtitle")), 20, 80, 20);
                        player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_PLACE, 2, 2);
                        player.setWalkSpeed(0.2f);
                        foliaLib.getImpl().runAtEntityLater(player, () ->
                                player.removePotionEffect(PotionEffectType.JUMP), 50L, TimeUnit.MILLISECONDS);
                        GameManager.setCountDown(0);
                        GameManager.addToRound(player);
                    }
                    game1Timer();
                    wrappedTask2.cancel();
                    return;
                }
                else {
                    time --;
                    ArrayList<UUID> playersInGame = new ArrayList<>(GameManager.getGame1());
                    for (UUID uuid : playersInGame) {
                        Player player = (Player) Bukkit.getServer().getOfflinePlayer(uuid);
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ColorUtils.translateColorCodes("&c&lRound Commencing in: ") + ColorUtils.translateColorCodes("&c") + time));
                        player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 2, 2);
                    }
                }
            }
        }, 0L, 1L, TimeUnit.SECONDS);
    }

    public static void game1Timer() {
        wrappedTask3 = foliaLib.getImpl().runTimerAsync(new Runnable() {
            Integer time = RedLightGreenLight.getPlugin().getConfig().getInt("Total-game-length");
            @Override
            public void run() {
                if (time == 30) {
                    ArrayList<UUID> playersInGame = new ArrayList<>(GameManager.getGame1());
                    for (UUID uuid : playersInGame) {
                        Player player = (Player) Bukkit.getServer().getOfflinePlayer(uuid);
                        player.sendTitle(ColorUtils.translateColorCodes("&c&l30 Seconds Remaining!"), ColorUtils.translateColorCodes(" "), 10, 30, 10);
                    }
                }
                if (time == 10) {
                    ArrayList<UUID> playersInGame = new ArrayList<>(GameManager.getGame1());
                    for (UUID uuid : playersInGame) {
                        Player player = (Player) Bukkit.getServer().getOfflinePlayer(uuid);
                        player.sendTitle(ColorUtils.translateColorCodes("&c&l10 Seconds Remaining!"), ColorUtils.translateColorCodes(" "), 10, 30, 10);
                    }
                }
                if (time == 5) {
                    ArrayList<UUID> playersInGame = new ArrayList<>(GameManager.getGame1());
                    for (UUID uuid : playersInGame) {
                        Player player = (Player) Bukkit.getServer().getOfflinePlayer(uuid);
                        player.sendTitle(ColorUtils.translateColorCodes("&c&l5 Seconds Remaining!"), ColorUtils.translateColorCodes(" "), 10, 30, 10);
                    }
                }
                if (time == 4) {
                    ArrayList<UUID> playersInGame = new ArrayList<>(GameManager.getGame1());
                    for (UUID uuid : playersInGame) {
                        Player player = (Player) Bukkit.getServer().getOfflinePlayer(uuid);
                        player.sendTitle(ColorUtils.translateColorCodes("&c&l4 Seconds Remaining!"), ColorUtils.translateColorCodes(" "), 10, 30, 10);
                    }
                }
                if (time == 3) {
                    ArrayList<UUID> playersInGame = new ArrayList<>(GameManager.getGame1());
                    for (UUID uuid : playersInGame) {
                        Player player = (Player) Bukkit.getServer().getOfflinePlayer(uuid);
                        player.sendTitle(ColorUtils.translateColorCodes("&c&l3 Seconds Remaining!"), ColorUtils.translateColorCodes(" "), 10, 30, 10);
                    }
                }
                if (time == 2) {
                    ArrayList<UUID> playersInGame = new ArrayList<>(GameManager.getGame1());
                    for (UUID uuid : playersInGame) {
                        Player player = (Player) Bukkit.getServer().getOfflinePlayer(uuid);
                        player.sendTitle(ColorUtils.translateColorCodes("&c&l2 Seconds Remaining!"), ColorUtils.translateColorCodes(" "), 10, 30, 10);
                    }
                }
                if (time == 1) {
                    ArrayList<UUID> playersInGame = new ArrayList<>(GameManager.getGame1());
                    for (UUID uuid : playersInGame) {
                        Player player = (Player) Bukkit.getServer().getOfflinePlayer(uuid);
                        String target = player.getName();
                        foliaLib.getImpl().runNextTick((task) ->
                                getServer().dispatchCommand(Bukkit.getConsoleSender(), "execute at " + player.getName() + " run summon minecraft:lightning_bolt ~ ~ ~"));
                        player.sendTitle(ColorUtils.translateColorCodes(RedLightGreenLight.getPlugin().getConfig().getString("Round-end-title")),
                                ColorUtils.translateColorCodes(RedLightGreenLight.getPlugin().getConfig().getString("Round-end-subtitle")), 10, 30, 10);
                        if (RedLightGreenLight.getPlugin().getConfig().getBoolean("Run-lose-commands")) {
                            for (String string : losecommands) {
                                foliaLib.getImpl().runNextTick((task) ->
                                        getServer().dispatchCommand(Bukkit.getConsoleSender(), string.replace("%player%", target)));
                            }
                        }
                    }
                    GameManager.endGameArena1();
                    wrappedTask3.cancel();
                    return;
                }
                else {
                    time --;
                    Random random = new Random();
                    int r = random.nextInt(10);
                    if (r == 5 || r == 8 || r == 2) {
                        if (time < RedLightGreenLight.getPlugin().getConfig().getInt("Total-game-length") -5){
                            coolDownTimer();
                            GameManager.setLightgreen(1);
                            ArrayList<UUID> playersInGame = new ArrayList<>(GameManager.getGame1());
                            for (UUID uuid : playersInGame) {
                                Player player = (Player) Bukkit.getServer().getOfflinePlayer(uuid);
                                if (RedLightGreenLight.getPlugin().getConfig().getBoolean("RedLight-title-enable")) {
                                    player.sendTitle(ColorUtils.translateColorCodes(RedLightGreenLight.getPlugin().getConfig().getString("RedLight-message")),
                                            ColorUtils.translateColorCodes(" "), 10, 30, 10);
                                }
                                player.sendMessage(ColorUtils.translateColorCodes(RedLightGreenLight.getPlugin().getConfig().getString("RedLight-message")));
                            }
                        }
                    }else {
                        GameManager.setLightgreen(0);
                        ArrayList<UUID> playersInGame = new ArrayList<>(GameManager.getGame1());
                        for (UUID uuid : playersInGame) {
                            Player player = (Player) Bukkit.getServer().getOfflinePlayer(uuid);
                            player.sendMessage(ColorUtils.translateColorCodes(RedLightGreenLight.getPlugin().getConfig().getString("GreenLight-message")));
                        }
                    }
                    if (GameManager.getGame1().size() == 0 || GameManager.getPlayersInRound().size() == 0){
                        if (!(GameManager.getSpectatingPlayers().size() == 0)){
                            GameManager.endSpectatingGame();
                        }
                        GameManager.getPlayersInRound().clear();
                        GameManager.getGame1().clear();
                        GameManager.setLightgreen(0);
                        GameManager.setGameRunning(0);

                        wrappedTask3.cancel();
                        return;
                    }
                    return;
                }
            }
        }, 1L, 1L, TimeUnit.SECONDS);
    }

    private static long repeatTime = RedLightGreenLight.getPlugin().getConfig().getInt("RedLight-delay-checking-time");

    public static void coolDownTimer(){
        wrappedTask4 = foliaLib.getImpl().runTimerAsync(new Runnable() {
            Integer time = 2;
            @Override
            public void run() {
                if (time == 1){
                    wrappedTask4.cancel();
                    return;
                }
                else {
                    time --;
                }
            }
        }, 1L, repeatTime, TimeUnit.MILLISECONDS);
    }
}
