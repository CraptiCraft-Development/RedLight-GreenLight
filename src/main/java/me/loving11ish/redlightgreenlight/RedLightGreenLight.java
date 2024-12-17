package me.loving11ish.redlightgreenlight;

import com.tcoded.folialib.FoliaLib;
import com.tcoded.folialib.wrapper.task.WrappedTask;
import io.papermc.lib.PaperLib;
import me.loving11ish.redlightgreenlight.commands.CommandManager;
import me.loving11ish.redlightgreenlight.events.*;
import me.loving11ish.redlightgreenlight.files.MessagesFileManager;
import me.loving11ish.redlightgreenlight.managers.filemanagers.ConfigManager;
import me.loving11ish.redlightgreenlight.managers.filemanagers.MessagesManager;
import me.loving11ish.redlightgreenlight.tasks.OnlinePlayerTasks;
import me.loving11ish.redlightgreenlight.updatesystem.JoinEvent;
import me.loving11ish.redlightgreenlight.updatesystem.UpdateChecker;
import me.loving11ish.redlightgreenlight.utils.*;
import me.loving11ish.redlightgreenlight.versionsystems.ServerVersion;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("unused")
public final class RedLightGreenLight extends JavaPlugin {

    private final PluginDescriptionFile pluginInfo = getDescription();
    private final String pluginVersion = pluginInfo.getVersion();

    private static RedLightGreenLight plugin;
    private FoliaLib foliaLib;
    private VersionCheckerUtils versionCheckerUtils;
    private BukkitAudiences bukkitAudiences;
    private ServerVersion serverVersion;

    // File Managers
    private MessagesFileManager messagesFileManager;

    // Config Managers
    private ConfigManager configManager;
    private MessagesManager messagesManager;

    // Booleans
    private boolean isPluginEnabled = true;
    private boolean isUpdateAvailable = false;
    private boolean isPluginReloading = false;

    // Commands
    private CommandManager commandManager;

    // Tasks
    private WrappedTask onlinePlayerUpdateTasks;

    // Lists
    public final List<Player> onlinePlayers = new ArrayList<>(Bukkit.getServer().getOnlinePlayers());

    @Override
    public void onLoad() {
        // Plugin load logic
        plugin = this;
        foliaLib = new FoliaLib(this);

        // Load the plugin configs
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        // Load config manager
        setConfigManager(new ConfigManager(getConfig()));
        getConfigManager().loadConfigVales();

        // Load messages.yml
        setMessagesFileManager(new MessagesFileManager(this));

        // Load messages manager
        setMessagesManager(new MessagesManager(getMessagesFileManager().getMessagesConfig()));
        getMessagesManager().loadMessagesValues();
        MessageUtils.setDebug(getConfigManager().isDebugMode());

        // Check server version and set it
        setVersion();
        versionCheckerUtils = new VersionCheckerUtils();
        versionCheckerUtils.setVersion();

        // Server version compatibility check
        if (versionCheckerUtils.getVersion() < 16 || versionCheckerUtils.getVersion() > 21
                || !versionCheckerUtils.isVersionCheckedSuccessfully()
                && !serverVersion.serverVersionEqual(ServerVersion.Other)) {
            MessageUtils.sendConsole("&4-------------------------------------------");
            MessageUtils.sendConsole("&4Your server version is: &d" + Bukkit.getVersion());
            MessageUtils.sendConsole("&4This plugin is only supported on the Minecraft versions listed below:");
            MessageUtils.sendConsole("&41.16.x");
            MessageUtils.sendConsole("&41.17.x");
            MessageUtils.sendConsole("&41.18.x");
            MessageUtils.sendConsole("&41.19.x");
            MessageUtils.sendConsole("&41.20.x");
            MessageUtils.sendConsole("&41.21.x");
            MessageUtils.sendConsole("&4Is now disabling!");
            MessageUtils.sendConsole("&4-------------------------------------------");
            setPluginEnabled(false);
            return;
        } else {
            MessageUtils.sendConsole("&a-------------------------------------------");
            MessageUtils.sendConsole("&aA supported Minecraft version has been detected");
            MessageUtils.sendConsole("&aYour server version is: &d" + Bukkit.getVersion());
            MessageUtils.sendConsole("&6Continuing plugin startup");
            MessageUtils.sendConsole("&a-------------------------------------------");
        }

        if (foliaLib.isUnsupported()) {
            MessageUtils.sendConsole("&4-------------------------------------------");
            MessageUtils.sendConsole("&4Your server appears to running a version other than Spigot based!");
            MessageUtils.sendConsole("&4This plugin uses features that your server most likely doesn't have!");
            MessageUtils.sendConsole("&4Is now disabling!");
            MessageUtils.sendConsole("&4-------------------------------------------");
            setPluginEnabled(false);
            return;
        }

        if (foliaLib.isSpigot()) {
            PaperLib.suggestPaper(this);
        }

        // Signal end of onLoad method
        MessageUtils.sendDebugConsole("End of onLoad method");
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        MessageUtils.sendDebugConsole("Start of onEnable method");

        // Check plugin was not disabled during onLoad
        if (!isPluginEnabled()) {
            MessageUtils.sendConsole("&4Plugin has been disabled during onLoad!");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        // Initial startup message
        MessageUtils.sendConsole("&a-------------------------------------------");
        MessageUtils.sendConsole("&aDetected Version &d" + Bukkit.getVersion());
        MessageUtils.sendConsole("&aLoading settings for Version &d" + Bukkit.getVersion());

        // Load BukkitAudiences
        try {
            setBukkitAudiences(BukkitAudiences.create(this));
            MessageUtils.sendConsole("&aBukkitAudiences has been created successfully!");
        } catch (Exception e) {
            MessageUtils.sendConsole("severe", "&4-------------------------------------------");
            MessageUtils.sendConsole("severe", "&4BukkitAudiences failed to be created!");
            MessageUtils.sendConsole("severe", "&4This plugin uses Adventure Library to function properly!");
            MessageUtils.sendConsole("severe", "&4Please send this error to the developer of RL-GL using below link!");
            MessageUtils.sendConsole("severe", "&4https://discord.gg/crapticraft");
            MessageUtils.sendConsole("severe", "&4Is now disabling!");
            MessageUtils.sendConsole("severe", "&4-------------------------------------------");
            Bukkit.getPluginManager().disablePlugin(this);
            setPluginEnabled(false);
            return;
        }

        //Register commands here
        setCommandManager(new CommandManager());
        getCommand("redlight").setExecutor(getCommandManager());

        //Register events here
        getServer().getPluginManager().registerEvents(new PlayerCommand(), this);
        getServer().getPluginManager().registerEvents(new PlayerHungerChange(), this);
        getServer().getPluginManager().registerEvents(new PlayerInventoryAction(), this);
        getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
        getServer().getPluginManager().registerEvents(new PlayerMove(), this);
        getServer().getPluginManager().registerEvents(new PlayerQuit(), this);
        getServer().getPluginManager().registerEvents(new PlayerKick(), this);
        getServer().getPluginManager().registerEvents(new JoinEvent(this), this);

        // Final startup message
        MessageUtils.sendConsole("&aPlugin by: &b&lLoving11ish");
        MessageUtils.sendConsole("&ahas been loaded successfully");
        MessageUtils.sendConsole("&aPlugin Version: &d&l" + pluginVersion);
        MessageUtils.sendDebugConsole("&e&lDeveloper debug mode enabled!");
        MessageUtils.sendDebugConsole("&e&lThis WILL fill the console");
        MessageUtils.sendDebugConsole("&e&lwith additional UStats information!");
        MessageUtils.sendDebugConsole("&e&lThis setting is not intended for ");
        MessageUtils.sendDebugConsole("&e&lcontinuous use!");
        MessageUtils.sendConsole("-------------------------------------------");

        // Start online player update task
        this.onlinePlayerUpdateTasks = foliaLib.getScheduler().runTimerAsync(new OnlinePlayerTasks(), 5L, 300L, TimeUnit.SECONDS);

        // Check for available updates
        new UpdateChecker(96866).getVersion(version -> {
            if (this.getDescription().getVersion().equalsIgnoreCase(version)) {
                MessageUtils.sendConsole(getMessagesManager().getNoUpdateAvailable1());
                MessageUtils.sendConsole(getMessagesManager().getNoUpdateAvailable2());
                MessageUtils.sendConsole(getMessagesManager().getNoUpdateAvailable3());
                setUpdateAvailable(false);
            } else {
                MessageUtils.sendConsole(getMessagesManager().getUpdateAvailable1());
                MessageUtils.sendConsole(getMessagesManager().getUpdateAvailable2());
                MessageUtils.sendConsole(getMessagesManager().getUpdateAvailable3());
                setUpdateAvailable(true);
            }
        });

        // Signal end of onEnable method
        MessageUtils.sendDebugConsole("End of onEnable method");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        // Check if the server is reloading and warn users
        if (ReloadingUtils.isCurrentlyReloading()) {
            MessageUtils.sendConsole("error", "&4╔══════════════════════════════════════════════════════════════════╗");
            MessageUtils.sendConsole("error", "&4║                             WARNING                              ║");
            MessageUtils.sendConsole("error", "&4║         RELOADING THE SERVER WHILE RL-GL IS ENABLED MIGHT        ║");
            MessageUtils.sendConsole("error", "&4║                    LEAD TO UNEXPECTED ERRORS!                    ║");
            MessageUtils.sendConsole("error", "&4║                                                                  ║");
            MessageUtils.sendConsole("error", "&4║   Please to fully restart your server if you encounter issues!   ║");
            MessageUtils.sendConsole("error", "&4╚══════════════════════════════════════════════════════════════════╝");
        }

        // Shutdown message start
        MessageUtils.sendConsole("-----------------------------------------");

        // Set plugin to disabled
        setPluginEnabled(false);
        MessageUtils.sendConsole("&aPlugin has been disabled!");

        // Unregister plugin listeners
        HandlerList.unregisterAll(this);
        MessageUtils.sendConsole("&aListeners have unregistered successfully!");

        // End all games
        GameManager.endGameArena1();

        // Cancel background tasks
        try {
            if (!CountDownTasksUtils.wrappedTask1.isCancelled()) {
                CountDownTasksUtils.wrappedTask1.cancel();
            }
            if (!CountDownTasksUtils.wrappedTask2.isCancelled()) {
                CountDownTasksUtils.wrappedTask2.cancel();
            }
            if (!CountDownTasksUtils.wrappedTask3.isCancelled()) {
                CountDownTasksUtils.wrappedTask3.cancel();
            }
            if (!CountDownTasksUtils.wrappedTask4.isCancelled()) {
                CountDownTasksUtils.wrappedTask4.cancel();
            }
            if (!this.onlinePlayerUpdateTasks.isCancelled()) {
                this.onlinePlayerUpdateTasks.cancel();
            }
            if (foliaLib.isUnsupported()) {
                Bukkit.getScheduler().cancelTasks(this);
            }
            MessageUtils.sendConsole("&aBackground tasks have disabled successfully!");
        } catch (Exception e) {
            MessageUtils.sendConsole("&aBackground tasks have disabled successfully!");
        }

        // Restore players inv's & leave active game
        for (Player onlinePlayer : onlinePlayers) {
            String onPlayerName = onlinePlayer.getName();
            Player onlinePlayerName = Bukkit.getServer().getPlayer(onPlayerName);
            if (onlinePlayerName != null) {
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

        // Final plugin shutdown message
        MessageUtils.sendConsole("&aPlugin Version: &d&l" + pluginVersion);
        MessageUtils.sendConsole("&aHas been shutdown successfully");
        MessageUtils.sendConsole("&aGoodbye!");
        MessageUtils.sendConsole("-----------------------------------------");

        // Clear lists
        onlinePlayers.clear();
        bukkitAudiences.close();

        // Cleanup any plugin remains
        onlinePlayerUpdateTasks = null;
        commandManager = null;
        messagesManager = null;
        configManager = null;
        messagesFileManager = null;
        serverVersion = null;
        bukkitAudiences = null;
        versionCheckerUtils = null;
        foliaLib = null;
        plugin = null;
    }

    private void setVersion() {
        try {
            String packageName = Bukkit.getServer().getClass().getPackage().getName();
            String bukkitVersion = Bukkit.getServer().getBukkitVersion();
            if (bukkitVersion.contains("1.20.5")) {
                serverVersion = ServerVersion.v1_20_R5;
            } else if (bukkitVersion.contains("1.20.6")) {
                serverVersion = ServerVersion.v1_20_R5;
            } else if (bukkitVersion.contains("1.21")) {
                serverVersion = ServerVersion.v1_21_R1;
            } else if (bukkitVersion.contains("1.21.1")) {
                serverVersion = ServerVersion.v1_21_R2;
            } else if (bukkitVersion.contains("1.21.2")) {
                serverVersion = ServerVersion.v1_21_R3;
            } else if (bukkitVersion.contains("1.21.3")) {
                serverVersion = ServerVersion.v1_21_R4;
            } else if (bukkitVersion.contains("1.21.4")) {
                serverVersion = ServerVersion.v1_21_R5;
            } else {
                serverVersion = ServerVersion.valueOf(packageName.replace("org.bukkit.craftbukkit.", ""));
            }
        } catch (Exception e) {
            serverVersion = ServerVersion.Other;
            MessageUtils.sendDebugConsole("Failed to detect server version, defaulting to: " + serverVersion);
        }
        MessageUtils.sendDebugConsole("Set server version: " + serverVersion);
    }

    public static RedLightGreenLight getPlugin() {
        return plugin;
    }

    public FoliaLib getFoliaLib() {
        return foliaLib;
    }

    public VersionCheckerUtils getVersionCheckerUtils() {
        return versionCheckerUtils;
    }

    public BukkitAudiences getBukkitAudiences() {
        return bukkitAudiences;
    }

    public void setBukkitAudiences(BukkitAudiences bukkitAudiences) {
        this.bukkitAudiences = bukkitAudiences;
    }

    public ServerVersion getServerVersion() {
        return serverVersion;
    }

    public MessagesFileManager getMessagesFileManager() {
        return messagesFileManager;
    }

    public void setMessagesFileManager(MessagesFileManager messagesFileManager) {
        this.messagesFileManager = messagesFileManager;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public void setConfigManager(ConfigManager configManager) {
        this.configManager = configManager;
    }

    public MessagesManager getMessagesManager() {
        return messagesManager;
    }

    public void setMessagesManager(MessagesManager messagesManager) {
        this.messagesManager = messagesManager;
    }

    public boolean isPluginEnabled() {
        return isPluginEnabled;
    }

    public void setPluginEnabled(boolean pluginEnabled) {
        isPluginEnabled = pluginEnabled;
    }

    public boolean isUpdateAvailable() {
        return isUpdateAvailable;
    }

    public void setUpdateAvailable(boolean updateAvailable) {
        isUpdateAvailable = updateAvailable;
    }

    public boolean isPluginReloading() {
        return isPluginReloading;
    }

    public void setPluginReloading(boolean pluginReloading) {
        isPluginReloading = pluginReloading;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

    public void setCommandManager(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    public WrappedTask getOnlinePlayerUpdateTasks() {
        return onlinePlayerUpdateTasks;
    }

    public void setOnlinePlayerUpdateTasks(WrappedTask onlinePlayerUpdateTasks) {
        this.onlinePlayerUpdateTasks = onlinePlayerUpdateTasks;
    }
}
