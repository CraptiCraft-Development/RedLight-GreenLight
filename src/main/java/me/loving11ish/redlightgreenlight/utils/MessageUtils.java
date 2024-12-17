package me.loving11ish.redlightgreenlight.utils;

import me.loving11ish.redlightgreenlight.RedLightGreenLight;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class MessageUtils {

    private static final ConsoleCommandSender console = Bukkit.getConsoleSender();

    public static String prefix = RedLightGreenLight.getPlugin().getMessagesManager().getPrefix();

    private static boolean debug;
    private static String levelColor;

    /**
     * @param message The message to be sent to the console with prefix applied. Supports color codes.
     */
    public static void sendConsole(String message) {
        console.sendMessage(ColorUtils.translateColorCodes(prefix + " &r" + message));
    }

    /**
     * @param player  The player to send the message too.
     * @param message The message to be sent with prefix applied. Supports color codes.
     */
    public static void sendPlayer(Player player, String message) {
        player.sendMessage(ColorUtils.translateColorCodes(prefix + " &r" + message));
    }

    /**
     * @param offlinePlayer  The offlinePlayer to send the message too.
     * @param message The message to be sent with prefix applied. Supports color codes.
     */
    public static void sendOfflinePlayer(OfflinePlayer offlinePlayer, String message) {
        Player player = offlinePlayer.getPlayer();
        if (player != null) {
            player.sendMessage(ColorUtils.translateColorCodes(prefix + " &r" + message));
        } else {
            sendConsole("error", "Player " + offlinePlayer.getName() + " is not online!");
        }
    }

    /**
     * @param message The message to be sent with prefix applied. Supports color codes.
     * @param level   The log level of the message. Supports info, warning, error, and severe.
     */
    public static void sendConsole(String level, String message) {
        level = setDebugLevel(level);
        console.sendMessage(ColorUtils.translateColorCodes(prefix + " &r[" + level + "&r] - " + levelColor + message));
    }

    /**
     * @param message The message to be broadcast with prefix applied. Supports color codes.
     */
    public static void broadcastMessage(String message) {
        Bukkit.broadcastMessage(ColorUtils.translateColorCodes(prefix + " &l" + message));
    }

    /**
     * @param sender  The sender to send the message too.
     * @param message The message to be sent with prefix applied. Supports color codes.
     */
    public static void sendSender(CommandSender sender, String message) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            sendPlayer(player, message);
        } else {
            sendConsole(message);
        }
    }

    /**
     * @param sender  The sender to send the message too.
     * @param level   The log level of the message. Supports: info, warning, error, and severe.
     * @param message The message to be sent with prefix applied. Supports color codes.
     */
    public static void sendSender(CommandSender sender, String level, String message) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            sendPlayer(player, message);
        } else {
            sendConsole(level, message);
        }
    }

    /**
     * @param message The debug message to be sent with prefix applied. Supports color codes.
     */
    public static void sendDebugConsole(String message) {
        if (debug) {
            console.sendMessage(ColorUtils.translateColorCodes("&6RL-GL-Debug&7: " + message));
        }
    }

    /**
     * @param message The debug message to be sent with prefix applied. Supports color codes.
     * @param level   The log level of the message. Supports info, warning, error, and severe.
     */
    public static void sendDebugConsole(String level, String message) {
        if (debug) {
            level = setDebugLevel(level);
            console.sendMessage(ColorUtils.translateColorCodes("&6RL-GL-Debug&7: [" + level + "&7] - " + levelColor + message));
        }
    }

    private static String setDebugLevel(String level) {
        if (level.equalsIgnoreCase("info")) {
            levelColor = "&a";
            level = levelColor + level;
        } else if (level.equalsIgnoreCase("warning")) {
            levelColor = "&e";
            level = levelColor + level;
        } else if (level.equalsIgnoreCase("error")) {
            levelColor = "&c";
            level = levelColor + level;
        } else if (level.equalsIgnoreCase("severe")) {
            levelColor = "&4";
            level = levelColor + level;
        } else {
            levelColor = "&7";
            level = levelColor + level;
        }
        return level;
    }

    public static void setDebug(boolean debug) {
        MessageUtils.debug = debug;
    }
}
