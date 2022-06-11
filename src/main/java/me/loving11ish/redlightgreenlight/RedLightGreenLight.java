package me.loving11ish.redlightgreenlight;

import me.loving11ish.redlightgreenlight.commands.CommandManager;
import me.loving11ish.redlightgreenlight.events.*;
import me.loving11ish.redlightgreenlight.updatesystem.JoinEvent;
import me.loving11ish.redlightgreenlight.updatesystem.UpdateChecker;
import me.loving11ish.redlightgreenlight.utils.ColorUtils;
import me.loving11ish.redlightgreenlight.utils.CountDownTasksUtils;
import me.loving11ish.redlightgreenlight.utils.GameManager;
import me.loving11ish.redlightgreenlight.utils.PlayerInventoryHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

public final class RedLightGreenLight extends JavaPlugin {

    private PluginDescriptionFile pluginInfo = getDescription();
    private String pluginVersion = pluginInfo.getVersion();
    private static RedLightGreenLight plugin;
    Logger logger = this.getLogger();

    public List<Player> onlinePlayers = new ArrayList<>(Bukkit.getServer().getOnlinePlayers());

    @Override
    public void onEnable() {
        //Plugin startup logic
        plugin = this;

        //Server version compatibility check
        if (!(Bukkit.getServer().getVersion().contains("1.13")||Bukkit.getServer().getVersion().contains("1.14")||
                Bukkit.getServer().getVersion().contains("1.15")||Bukkit.getServer().getVersion().contains("1.16")||
                Bukkit.getServer().getVersion().contains("1.17")||Bukkit.getServer().getVersion().contains("1.18")||
                Bukkit.getServer().getVersion().contains("1.19"))){
            logger.warning(ChatColor.RED + "-------------------------------------------");
            logger.warning(ChatColor.RED + "RedLightGreenLight - This plugin is only supported on the Minecraft versions listed below:");
            logger.warning(ChatColor.RED + "RedLightGreenLight - 1.13.x");
            logger.warning(ChatColor.RED + "RedLightGreenLight - 1.14.x");
            logger.warning(ChatColor.RED + "RedLightGreenLight - 1.15.x");
            logger.warning(ChatColor.RED + "RedLightGreenLight - 1.16.x");
            logger.warning(ChatColor.RED + "RedLightGreenLight - 1.17.x");
            logger.warning(ChatColor.RED + "RedLightGreenLight - 1.18.x");
            logger.warning(ChatColor.RED + "RedLightGreenLight - 1.19.x");
            logger.warning(ChatColor.RED + "RedLightGreenLight - Is now disabling!");
            logger.warning(ChatColor.RED + "-------------------------------------------");
            Bukkit.getPluginManager().disablePlugin(this);
        }else {
            logger.info(ChatColor.GREEN + "-------------------------------------------");
            logger.info(ChatColor.GREEN + "RedLightGreenLight - A supported Minecraft version has been detected");
            logger.info(ChatColor.GREEN + "RedLightGreenLight - Continuing plugin startup");
            logger.info(ChatColor.GREEN + "-------------------------------------------");
        }

        //Load the main config file
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        //Register commands here
        getCommand("redlight").setExecutor(new CommandManager());

        //Register events here
        getServer().getPluginManager().registerEvents(new PlayerCommand(),this);
        getServer().getPluginManager().registerEvents(new PlayerHungerChange(), this);
        getServer().getPluginManager().registerEvents(new PlayerInventoryAction(), this);
        getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
        getServer().getPluginManager().registerEvents(new PlayerMove(), this);
        getServer().getPluginManager().registerEvents(new PlayerQuit(),this);
        getServer().getPluginManager().registerEvents(new PlayerKick(), this);
        getServer().getPluginManager().registerEvents(new JoinEvent(this), this);

        //Plugin startup message
        logger.info("-------------------------------------------");
        logger.info(ChatColor.AQUA + "RedLightGreenLight - Plugin By Loving11ish");
        logger.info(ChatColor.AQUA + "RedLightGreenLight - has been loaded successfully");
        logger.info(ChatColor.AQUA + "RedLightGreenLight - Plugin Version: " + ChatColor.GREEN + pluginVersion);
        logger.info("-------------------------------------------");

        //Check for available updates
        new UpdateChecker(this, 96866).getVersion(version -> {
            if (this.getDescription().getVersion().equalsIgnoreCase(version)) {
                logger.info(ColorUtils.translateColorCodes(getConfig().getString("No-update-1")));
                logger.info(ColorUtils.translateColorCodes(getConfig().getString("No-update-2")));
                logger.info(ColorUtils.translateColorCodes(getConfig().getString("No-update-3")));
            }else {
                logger.warning(ColorUtils.translateColorCodes(getConfig().getString("Update-1")));
                logger.warning(ColorUtils.translateColorCodes(getConfig().getString("Update-2")));
                logger.warning(ColorUtils.translateColorCodes(getConfig().getString("Update-3")));
            }
        });
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        try {
            if (Bukkit.getScheduler().isCurrentlyRunning(CountDownTasksUtils.taskID1) || Bukkit.getScheduler().isQueued(CountDownTasksUtils.taskID1)){
                Bukkit.getScheduler().cancelTask(CountDownTasksUtils.taskID1);
            }
            if (Bukkit.getScheduler().isCurrentlyRunning(CountDownTasksUtils.taskID2) || Bukkit.getScheduler().isQueued(CountDownTasksUtils.taskID2)){
                Bukkit.getScheduler().cancelTask(CountDownTasksUtils.taskID2);
            }
            if (Bukkit.getScheduler().isCurrentlyRunning(CountDownTasksUtils.taskID3) || Bukkit.getScheduler().isQueued(CountDownTasksUtils.taskID3)){
                Bukkit.getScheduler().cancelTask(CountDownTasksUtils.taskID3);
            }
            if (Bukkit.getScheduler().isCurrentlyRunning(CountDownTasksUtils.taskID4) || Bukkit.getScheduler().isQueued(CountDownTasksUtils.taskID4)){
                Bukkit.getScheduler().cancelTask(CountDownTasksUtils.taskID4);
            }
        }catch (Exception e){
            logger.info("-------------------------------------------");
            logger.info(ChatColor.AQUA + "RedLightGreenLight - Plugin By Loving11ish");
            logger.info(ChatColor.AQUA + "RedLightGreenLight - background tasks have disabled successfully");
        }

        for (int i = 0; i < onlinePlayers.size(); i++){
            String onPlayerName = onlinePlayers.get(i).getName();
            Player onlinePlayerName = Bukkit.getServer().getPlayer(onPlayerName);
            UUID onlineUUID = onlinePlayerName.getUniqueId();
            if (GameManager.getGame1().contains(onlineUUID)){
                if (PlayerInventoryHandler.getItems().contains(onlineUUID) && PlayerInventoryHandler.getArmor().contains(onlineUUID)){
                    PlayerInventoryHandler.clearInventory(onlinePlayerName);
                    PlayerInventoryHandler.restoreInventory(onlinePlayerName);
                }
                if (GameManager.getPlayersInRound().contains(onlineUUID)){
                    GameManager.leaveRound(onlinePlayerName);
                }
                GameManager.leaveGame1(onlinePlayerName);
                if (GameManager.getSpectatingPlayers().contains(onlineUUID)){
                    GameManager.leaveSpectating(onlinePlayerName);
                }
            }
        }
        logger.info(ChatColor.AQUA + "RedLightGreenLight - Shutdown complete!");
        logger.info(ChatColor.AQUA + "RedLightGreenLight - Goodbye!");
        logger.info("-------------------------------------------");
    }

    public static RedLightGreenLight getPlugin(){
        return plugin;
    }
}
