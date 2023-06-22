package me.loving11ish.redlightgreenlight;

import com.rylinaux.plugman.api.PlugManAPI;
import com.tcoded.folialib.FoliaLib;
import io.papermc.lib.PaperLib;
import me.loving11ish.redlightgreenlight.commands.CommandManager;
import me.loving11ish.redlightgreenlight.events.*;
import me.loving11ish.redlightgreenlight.updatesystem.JoinEvent;
import me.loving11ish.redlightgreenlight.updatesystem.UpdateChecker;
import me.loving11ish.redlightgreenlight.utils.ColorUtils;
import me.loving11ish.redlightgreenlight.utils.CountDownTasksUtils;
import me.loving11ish.redlightgreenlight.utils.GameManager;
import me.loving11ish.redlightgreenlight.utils.PlayerInventoryHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
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
    private static FoliaLib foliaLib;
    Logger logger = this.getLogger();

    public List<Player> onlinePlayers = new ArrayList<>(Bukkit.getServer().getOnlinePlayers());

    @Override
    public void onEnable() {
        //Plugin startup logic
        plugin = this;
        foliaLib = new FoliaLib(plugin);

        //Server version compatibility check
        if (!(Bukkit.getServer().getVersion().contains("1.13")||Bukkit.getServer().getVersion().contains("1.14")||
                Bukkit.getServer().getVersion().contains("1.15")||Bukkit.getServer().getVersion().contains("1.16")||
                Bukkit.getServer().getVersion().contains("1.17")||Bukkit.getServer().getVersion().contains("1.18")||
                Bukkit.getServer().getVersion().contains("1.19")||Bukkit.getServer().getVersion().contains("1.20"))){
            logger.warning(ColorUtils.translateColorCodes("&4-------------------------------------------"));
            logger.warning(ColorUtils.translateColorCodes("&6RedLightGreenLight: &4Your server version is: &d" + Bukkit.getServer().getVersion()));
            logger.warning(ColorUtils.translateColorCodes("&6RedLightGreenLight: &4This plugin is only supported on the Minecraft versions listed below:"));
            logger.warning(ColorUtils.translateColorCodes("&6RedLightGreenLight: &41.13.x"));
            logger.warning(ColorUtils.translateColorCodes("&6RedLightGreenLight: &41.14.x"));
            logger.warning(ColorUtils.translateColorCodes("&6RedLightGreenLight: &41.15.x"));
            logger.warning(ColorUtils.translateColorCodes("&6RedLightGreenLight: &41.16.x"));
            logger.warning(ColorUtils.translateColorCodes("&6RedLightGreenLight: &41.17.x"));
            logger.warning(ColorUtils.translateColorCodes("&6RedLightGreenLight: &41.18.x"));
            logger.warning(ColorUtils.translateColorCodes("&6RedLightGreenLight: &41.19.x"));
            logger.warning(ColorUtils.translateColorCodes("&6RedLightGreenLight: &41.20.x"));
            logger.warning(ColorUtils.translateColorCodes("&6RedLightGreenLight: &4Is now disabling!"));
            logger.warning(ColorUtils.translateColorCodes("&4-------------------------------------------"));
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }else {
            logger.info(ColorUtils.translateColorCodes("&a-------------------------------------------"));
            logger.info(ColorUtils.translateColorCodes("&6RedLightGreenLight: &aA supported Minecraft version has been detected"));
            logger.info(ColorUtils.translateColorCodes("&6RedLightGreenLight: &aYour server version is: &d" + Bukkit.getServer().getVersion()));
            logger.info(ColorUtils.translateColorCodes("&6RedLightGreenLight: &6Continuing plugin startup"));
            logger.info(ColorUtils.translateColorCodes("&a-------------------------------------------"));
        }

        //Suggest PaperMC if not using
        if (foliaLib.isUnsupported()||foliaLib.isSpigot()){
            PaperLib.suggestPaper(this);
        }

        //Check if PlugManX is enabled
        if (isPlugManXEnabled()){
            if (!PlugManAPI.iDoNotWantToBeUnOrReloaded("RedLightGreenLight")){
                logger.severe(ColorUtils.translateColorCodes("&c-------------------------------------------"));
                logger.severe(ColorUtils.translateColorCodes("&c-------------------------------------------"));
                logger.severe(ColorUtils.translateColorCodes("&4WARNING WARNING WARNING WARNING!"));
                logger.severe(ColorUtils.translateColorCodes("&c-------------------------------------------"));
                logger.severe(ColorUtils.translateColorCodes("&6RedLightGreenLight: &4You appear to be using an unsupported version of &d&lPlugManX"));
                logger.severe(ColorUtils.translateColorCodes("&6RedLightGreenLight: &4Please &4&lDO NOT USE PLUGMANX TO LOAD/UNLOAD/RELOAD THIS PLUGIN!"));
                logger.severe(ColorUtils.translateColorCodes("&6RedLightGreenLight: &4Please &4&lFULLY RESTART YOUR SERVER!"));
                logger.severe(ColorUtils.translateColorCodes("&c-------------------------------------------"));
                logger.severe(ColorUtils.translateColorCodes("&6RedLightGreenLight: &4This plugin &4&lHAS NOT &4been validated to use this version of PlugManX!"));
                logger.severe(ColorUtils.translateColorCodes("&6RedLightGreenLight: &4&lNo official support will be given to you if you use this!"));
                logger.severe(ColorUtils.translateColorCodes("&6RedLightGreenLight: &4&lUnless Loving11ish has explicitly agreed to help!"));
                logger.severe(ColorUtils.translateColorCodes("&6RedLightGreenLight: &4Please add RedLightGreenLight to the ignored-plugins list in PlugManX's config.yml"));
                logger.severe(ColorUtils.translateColorCodes("&c-------------------------------------------"));
                logger.severe(ColorUtils.translateColorCodes("&6RedLightGreenLight: &6Continuing plugin startup"));
                logger.severe(ColorUtils.translateColorCodes("&c-------------------------------------------"));
                logger.severe(ColorUtils.translateColorCodes("&c-------------------------------------------"));
            }else {
                logger.info(ColorUtils.translateColorCodes("&a-------------------------------------------"));
                logger.info(ColorUtils.translateColorCodes("&6RedLightGreenLight: &aSuccessfully hooked into PlugManX"));
                logger.info(ColorUtils.translateColorCodes("&6RedLightGreenLight: &aSuccessfully added RedLightGreenLight to ignoredPlugins list."));
                logger.info(ColorUtils.translateColorCodes("&6RedLightGreenLight: &6Continuing plugin startup"));
                logger.info(ColorUtils.translateColorCodes("&a-------------------------------------------"));
            }
        }else {
            logger.info(ColorUtils.translateColorCodes("-------------------------------------------"));
            logger.info(ColorUtils.translateColorCodes("&6RedLightGreenLight: &cPlugManX not found!"));
            logger.info(ColorUtils.translateColorCodes("&6RedLightGreenLight: &cDisabling PlugManX hook loader"));
            logger.info(ColorUtils.translateColorCodes("&6RedLightGreenLight: &6Continuing plugin startup"));
            logger.info(ColorUtils.translateColorCodes("-------------------------------------------"));
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
        logger.info(ColorUtils.translateColorCodes("-------------------------------------------"));
        logger.info(ColorUtils.translateColorCodes("&6RedLightGreenLight: &3Plugin by: &b&lLoving11ish"));
        logger.info(ColorUtils.translateColorCodes("&6RedLightGreenLight: &3has been loaded successfully"));
        logger.info(ColorUtils.translateColorCodes("&6RedLightGreenLight: &3Plugin Version: &d&l" + pluginVersion));
        logger.info(ColorUtils.translateColorCodes("-------------------------------------------"));

        //Check for available updates
        new UpdateChecker(96866).getVersion(version -> {
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
        //Plugin shutdown logic

        //Unregister plugin listeners
        HandlerList.unregisterAll(this);

        //Cancel background tasks
        logger.info(ColorUtils.translateColorCodes("-------------------------------------------"));
        logger.info(ColorUtils.translateColorCodes("&6RedLightGreenLight: &3Plugin by: &b&lLoving11ish"));
        try {
            if (!CountDownTasksUtils.wrappedTask1.isCancelled()){
                CountDownTasksUtils.wrappedTask1.cancel();
            }
            if (!CountDownTasksUtils.wrappedTask2.isCancelled()){
                CountDownTasksUtils.wrappedTask2.cancel();
            }
            if (!CountDownTasksUtils.wrappedTask3.isCancelled()){
                CountDownTasksUtils.wrappedTask3.cancel();
            }
            if (!CountDownTasksUtils.wrappedTask4.isCancelled()){
                CountDownTasksUtils.wrappedTask4.cancel();
            }
            if (foliaLib.isUnsupported()){
                Bukkit.getScheduler().cancelTasks(this);
            }
            logger.info(ColorUtils.translateColorCodes("&6RedLightGreenLight: &3Background tasks have disabled successfully!"));
        }catch (Exception e){
            logger.info(ColorUtils.translateColorCodes("&6RedLightGreenLight: &3Background tasks have disabled successfully!"));
        }

        //Restore players invs & leave active game
        for (Player onlinePlayer : onlinePlayers) {
            String onPlayerName = onlinePlayer.getName();
            Player onlinePlayerName = Bukkit.getServer().getPlayer(onPlayerName);
            if (onlinePlayerName != null){
                UUID onlineUUID = onlinePlayerName.getUniqueId();
                if (GameManager.getGame1().contains(onlineUUID)) {
                    onlinePlayerName.setInvulnerable(false);
                    if (PlayerInventoryHandler.getItems().contains(onlineUUID) && PlayerInventoryHandler.getArmor().contains(onlineUUID)) {
                        PlayerInventoryHandler.clearInventory(onlinePlayerName);
                        PlayerInventoryHandler.restoreInventory(onlinePlayerName);
                    }
                    if (GameManager.getPlayersInRound().contains(onlineUUID)) {
                        GameManager.leaveRound(onlinePlayerName);
                    }
                    GameManager.leaveGame1(onlinePlayerName);
                    if (GameManager.getSpectatingPlayers().contains(onlineUUID)) {
                        GameManager.leaveSpectating(onlinePlayerName);
                    }
                }
            }
        }

        //Final plugin shutdown message
        logger.info(ColorUtils.translateColorCodes("&6RedLightGreenLight: &3Plugin Version: &d&l" + pluginVersion));
        logger.info(ColorUtils.translateColorCodes("&6RedLightGreenLight: &3Has been shutdown successfully"));
        logger.info(ColorUtils.translateColorCodes("&6RedLightGreenLight: &3Goodbye!"));
        logger.info(ColorUtils.translateColorCodes("-------------------------------------------"));

        //Cleanup any plugin remains
        foliaLib = null;
        plugin = null;
    }

    public boolean isPlugManXEnabled() {
        try {
            Class.forName("com.rylinaux.plugman.PlugMan");
            logger.info(ColorUtils.translateColorCodes("&6RedLightGreenLight: &aFound PlugManX main class at:"));
            logger.info(ColorUtils.translateColorCodes("&6RedLightGreenLight: &dcom.rylinaux.plugman.PlugMan"));
            return true;
        }catch (ClassNotFoundException e){
            logger.info(ColorUtils.translateColorCodes("&6RedLightGreenLight: &aCould not find PlugManX main class at:"));
            logger.info(ColorUtils.translateColorCodes("&6RedLightGreenLight: &dcom.rylinaux.plugman.PlugMan"));
            return false;
        }
    }

    public static RedLightGreenLight getPlugin(){
        return plugin;
    }

    public static FoliaLib getFoliaLib() {
        return foliaLib;
    }
}
