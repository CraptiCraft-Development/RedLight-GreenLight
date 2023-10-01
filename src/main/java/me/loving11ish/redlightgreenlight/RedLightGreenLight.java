package me.loving11ish.redlightgreenlight;

import com.rylinaux.plugman.api.PlugManAPI;
import com.tcoded.folialib.FoliaLib;
import io.papermc.lib.PaperLib;
import me.loving11ish.redlightgreenlight.commands.CommandManager;
import me.loving11ish.redlightgreenlight.events.*;
import me.loving11ish.redlightgreenlight.externalhooks.PlugManXAPI;
import me.loving11ish.redlightgreenlight.updatesystem.JoinEvent;
import me.loving11ish.redlightgreenlight.updatesystem.UpdateChecker;
import me.loving11ish.redlightgreenlight.utils.*;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class RedLightGreenLight extends JavaPlugin {

    ConsoleCommandSender console = Bukkit.getConsoleSender();

    private PluginDescriptionFile pluginInfo = getDescription();
    private String pluginVersion = pluginInfo.getVersion();
    
    private static RedLightGreenLight plugin;
    private static FoliaLib foliaLib;
    private static VersionCheckerUtils versionCheckerUtils;

    public List<Player> onlinePlayers = new ArrayList<>(Bukkit.getServer().getOnlinePlayers());

    @Override
    public void onEnable() {
        //Plugin startup logic
        plugin = this;
        foliaLib = new FoliaLib(plugin);
        versionCheckerUtils = new VersionCheckerUtils();
        versionCheckerUtils.setVersion();

        //Server version compatibility check
        if (getVersionCheckerUtils().getVersion() < 13||getVersionCheckerUtils().getVersion() > 20){
            console.sendMessage(ColorUtils.translateColorCodes("&4-------------------------------------------"));
            console.sendMessage(ColorUtils.translateColorCodes("&6RedLightGreenLight: &4Your server version is: &d" + Bukkit.getServer().getVersion()));
            console.sendMessage(ColorUtils.translateColorCodes("&6RedLightGreenLight: &4This plugin is only supported on the Minecraft versions listed below:"));
            console.sendMessage(ColorUtils.translateColorCodes("&6RedLightGreenLight: &41.13.x"));
            console.sendMessage(ColorUtils.translateColorCodes("&6RedLightGreenLight: &41.14.x"));
            console.sendMessage(ColorUtils.translateColorCodes("&6RedLightGreenLight: &41.15.x"));
            console.sendMessage(ColorUtils.translateColorCodes("&6RedLightGreenLight: &41.16.x"));
            console.sendMessage(ColorUtils.translateColorCodes("&6RedLightGreenLight: &41.17.x"));
            console.sendMessage(ColorUtils.translateColorCodes("&6RedLightGreenLight: &41.18.x"));
            console.sendMessage(ColorUtils.translateColorCodes("&6RedLightGreenLight: &41.19.x"));
            console.sendMessage(ColorUtils.translateColorCodes("&6RedLightGreenLight: &41.20.x"));
            console.sendMessage(ColorUtils.translateColorCodes("&6RedLightGreenLight: &4Is now disabling!"));
            console.sendMessage(ColorUtils.translateColorCodes("&4-------------------------------------------"));
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }else {
            console.sendMessage(ColorUtils.translateColorCodes("&a-------------------------------------------"));
            console.sendMessage(ColorUtils.translateColorCodes("&6RedLightGreenLight: &aA supported Minecraft version has been detected"));
            console.sendMessage(ColorUtils.translateColorCodes("&6RedLightGreenLight: &aYour server version is: &d" + Bukkit.getServer().getVersion()));
            console.sendMessage(ColorUtils.translateColorCodes("&6RedLightGreenLight: &6Continuing plugin startup"));
            console.sendMessage(ColorUtils.translateColorCodes("&a-------------------------------------------"));
        }

        //Suggest PaperMC if not using
        if (foliaLib.isUnsupported()||foliaLib.isSpigot()){
            PaperLib.suggestPaper(this);
        }

        //Check if PlugManX is enabled
        if (Bukkit.getServer().getPluginManager().isPluginEnabled("PlugManX")||PlugManXAPI.isPlugManXEnabled()){
            if (!PlugManAPI.iDoNotWantToBeUnOrReloaded("RedLightGreenLight")){
                console.sendMessage(ColorUtils.translateColorCodes("&c-------------------------------------------"));
                console.sendMessage(ColorUtils.translateColorCodes("&c-------------------------------------------"));
                console.sendMessage(ColorUtils.translateColorCodes("&4WARNING WARNING WARNING WARNING!"));
                console.sendMessage(ColorUtils.translateColorCodes("&c-------------------------------------------"));
                console.sendMessage(ColorUtils.translateColorCodes("&6RedLightGreenLight: &4You appear to be using an unsupported version of &d&lPlugManX"));
                console.sendMessage(ColorUtils.translateColorCodes("&6RedLightGreenLight: &4Please &4&lDO NOT USE PLUGMANX TO LOAD/UNLOAD/RELOAD THIS PLUGIN!"));
                console.sendMessage(ColorUtils.translateColorCodes("&6RedLightGreenLight: &4Please &4&lFULLY RESTART YOUR SERVER!"));
                console.sendMessage(ColorUtils.translateColorCodes("&c-------------------------------------------"));
                console.sendMessage(ColorUtils.translateColorCodes("&6RedLightGreenLight: &4This plugin &4&lHAS NOT &4been validated to use this version of PlugManX!"));
                console.sendMessage(ColorUtils.translateColorCodes("&6RedLightGreenLight: &4&lNo official support will be given to you if you use this!"));
                console.sendMessage(ColorUtils.translateColorCodes("&6RedLightGreenLight: &4&lUnless Loving11ish has explicitly agreed to help!"));
                console.sendMessage(ColorUtils.translateColorCodes("&6RedLightGreenLight: &4Please add RedLightGreenLight to the ignored-plugins list in PlugManX's config.yml"));
                console.sendMessage(ColorUtils.translateColorCodes("&c-------------------------------------------"));
                console.sendMessage(ColorUtils.translateColorCodes("&6RedLightGreenLight: &6Continuing plugin startup"));
                console.sendMessage(ColorUtils.translateColorCodes("&c-------------------------------------------"));
                console.sendMessage(ColorUtils.translateColorCodes("&c-------------------------------------------"));
            }else {
                console.sendMessage(ColorUtils.translateColorCodes("&a-------------------------------------------"));
                console.sendMessage(ColorUtils.translateColorCodes("&6RedLightGreenLight: &aSuccessfully hooked into PlugManX"));
                console.sendMessage(ColorUtils.translateColorCodes("&6RedLightGreenLight: &aSuccessfully added RedLightGreenLight to ignoredPlugins list."));
                console.sendMessage(ColorUtils.translateColorCodes("&6RedLightGreenLight: &6Continuing plugin startup"));
                console.sendMessage(ColorUtils.translateColorCodes("&a-------------------------------------------"));
            }
        }else {
            console.sendMessage(ColorUtils.translateColorCodes("-------------------------------------------"));
            console.sendMessage(ColorUtils.translateColorCodes("&6RedLightGreenLight: &cPlugManX not found!"));
            console.sendMessage(ColorUtils.translateColorCodes("&6RedLightGreenLight: &cDisabling PlugManX hook loader"));
            console.sendMessage(ColorUtils.translateColorCodes("&6RedLightGreenLight: &6Continuing plugin startup"));
            console.sendMessage(ColorUtils.translateColorCodes("-------------------------------------------"));
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
        console.sendMessage(ColorUtils.translateColorCodes("-------------------------------------------"));
        console.sendMessage(ColorUtils.translateColorCodes("&6RedLightGreenLight: &3Plugin by: &b&lLoving11ish"));
        console.sendMessage(ColorUtils.translateColorCodes("&6RedLightGreenLight: &3has been loaded successfully"));
        console.sendMessage(ColorUtils.translateColorCodes("&6RedLightGreenLight: &3Plugin Version: &d&l" + pluginVersion));
        console.sendMessage(ColorUtils.translateColorCodes("-------------------------------------------"));

        //Check for available updates
        new UpdateChecker(96866).getVersion(version -> {
            if (this.getDescription().getVersion().equalsIgnoreCase(version)) {
                console.sendMessage(ColorUtils.translateColorCodes(getConfig().getString("No-update-1")));
                console.sendMessage(ColorUtils.translateColorCodes(getConfig().getString("No-update-2")));
                console.sendMessage(ColorUtils.translateColorCodes(getConfig().getString("No-update-3")));
            }else {
                console.sendMessage(ColorUtils.translateColorCodes(getConfig().getString("Update-1")));
                console.sendMessage(ColorUtils.translateColorCodes(getConfig().getString("Update-2")));
                console.sendMessage(ColorUtils.translateColorCodes(getConfig().getString("Update-3")));
            }
        });
    }

    @Override
    public void onDisable() {
        //Plugin shutdown logic

        //Unregister plugin listeners
        HandlerList.unregisterAll(this);

        //Cancel background tasks
        console.sendMessage(ColorUtils.translateColorCodes("-------------------------------------------"));
        console.sendMessage(ColorUtils.translateColorCodes("&6RedLightGreenLight: &3Plugin by: &b&lLoving11ish"));
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
            console.sendMessage(ColorUtils.translateColorCodes("&6RedLightGreenLight: &3Background tasks have disabled successfully!"));
        }catch (Exception e){
            console.sendMessage(ColorUtils.translateColorCodes("&6RedLightGreenLight: &3Background tasks have disabled successfully!"));
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
        console.sendMessage(ColorUtils.translateColorCodes("&6RedLightGreenLight: &3Plugin Version: &d&l" + pluginVersion));
        console.sendMessage(ColorUtils.translateColorCodes("&6RedLightGreenLight: &3Has been shutdown successfully"));
        console.sendMessage(ColorUtils.translateColorCodes("&6RedLightGreenLight: &3Goodbye!"));
        console.sendMessage(ColorUtils.translateColorCodes("-------------------------------------------"));

        //Cleanup any plugin remains
        versionCheckerUtils = null;
        foliaLib = null;
        plugin = null;
    }

    public static RedLightGreenLight getPlugin(){
        return plugin;
    }

    public static FoliaLib getFoliaLib() {
        return foliaLib;
    }

    public static VersionCheckerUtils getVersionCheckerUtils() {
        return versionCheckerUtils;
    }
}
