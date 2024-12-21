package me.loving11ish.redlightgreenlight.managers.filemanagers;

import me.loving11ish.redlightgreenlight.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class ConfigManager {

    private final FileConfiguration config;

    // Strings
    private String welcomeTitle;
    private String welcomeSubTitle;

    // Integers
    private int arenaStartPlayerAmount;
    private int gameStartCountdown;
    private int totalGameLength;
    private int redLightDelayCheckTime;

    // Booleans
    private boolean debugMode;
    private boolean wipeInventoryOnJoin;
    private boolean handleJoinSpawnEvent;
    private boolean sendWelcomeTitle;
    private boolean joinPlayerInvulnerable;
    private boolean leavePlayerInvulnerable;
    private boolean disableGlobalHunger;
    private boolean disableInGameHunger;
    private boolean losersSpectateGame;
    private boolean runWinCommands;
    private boolean runLoseCommands;
    private boolean smiteLosingPlayers;

    // Lists
    private List<String> disabledWorldsList;
    private List<String> blockedInGameCommandsList;
    private List<String> winCommandsList;
    private List<String> loseCommandsList;

    // Materials
    private Material topTriggerBlock;
    private Material bottomTriggerBlock;

    // Locations
    private Location lobbyLocation;
    private Location arenaStartLocation;
    private Location spectateLocation;


    public ConfigManager(FileConfiguration config){
        this.config = config;
    }

    public void loadConfigVales() {
        // Load strings
        welcomeTitle = config.getString("welcome-player-title", "&a&lWelcome To RedLight-GreenLight!");
        welcomeSubTitle = config.getString("welcome-player-subtitle", "&ePlease use /redlight join");

        // Load integers
        arenaStartPlayerAmount = config.getInt("arena-start-size", 4);
        gameStartCountdown = config.getInt("game-starting-countdown-length", 10);
        totalGameLength = config.getInt("total-game-length", 300);
        redLightDelayCheckTime = config.getInt("redLight-delay-checking-time", 600);

        // Load booleans
        debugMode = config.getBoolean("developer-debug-mode.enabled", false);
        wipeInventoryOnJoin = config.getBoolean("wipe-inventory-on-join", false);
        handleJoinSpawnEvent = config.getBoolean("handle-player-join-spawn-event", true);
        sendWelcomeTitle = config.getBoolean("send-welcome-title", true);
        joinPlayerInvulnerable = config.getBoolean("join-player-invulnerable", true);
        leavePlayerInvulnerable = config.getBoolean("leave-player-invulnerable", true);
        disableGlobalHunger = config.getBoolean("disable-global-hunger-drain", false);
        disableInGameHunger = config.getBoolean("disable-in-game-hunger-drain", true);
        losersSpectateGame = config.getBoolean("losers-spectate-game", true);
        runWinCommands = config.getBoolean("run-win-commands", true);
        runLoseCommands = config.getBoolean("run-lose-commands", true);
        smiteLosingPlayers = config.getBoolean("smite-losing-players", true);

        // Load lists
        disabledWorldsList = config.getStringList("disabled-worlds");
        blockedInGameCommandsList = config.getStringList("blocked-commands-in-game");
        winCommandsList = config.getStringList("win-commands-list");
        loseCommandsList = config.getStringList("lose-commands-list");

        // Load materials
        topTriggerBlock = Material.getMaterial(config.getString("top-trigger-block", "COAL_BLOCK"));
        bottomTriggerBlock = Material.getMaterial(config.getString("bottom-trigger-block", "GOLD_BLOCK"));
    }

    public void loadLocations() {
        // Load locations
        String lobbyWorld = config.getString("lobby-position.world-name", "world");
        double lobbyX = config.getDouble("lobby-position.x", -233.0);
        double lobbyY = config.getDouble("lobby-position.y", 52.2);
        double lobbyZ = config.getDouble("lobby-position.z", 24.0);
        float lobbyYaw = (float) config.getDouble("lobby-position.yaw", 0.0);
        float lobbyPitch = (float) config.getDouble("lobby-position.pitch", -0.0);

        World lobbyBukkitWorld;
        try {
            lobbyBukkitWorld = Bukkit.getWorld(lobbyWorld);
            MessageUtils.sendDebugConsole("info", "Lobby world &e" + lobbyBukkitWorld.getName() + " &aloaded successfully!");
        } catch (NullPointerException e) {
            lobbyBukkitWorld = Bukkit.getServer().getWorlds().get(0);
            MessageUtils.sendConsole("warning", "Lobby world &e" + lobbyWorld + " &cnot found! Using default world instead.");
            MessageUtils.sendConsole("info", "Lobby world &e" + lobbyBukkitWorld.getName() + " &aloaded successfully!");
        }
        lobbyLocation = new Location(lobbyBukkitWorld, lobbyX, lobbyY, lobbyZ, lobbyYaw, lobbyPitch);

        String arenaWorld = config.getString("arena-start.world-name", "world");
        double arenaX = config.getDouble("arena-start.x", -220.5);
        double arenaY = config.getDouble("arena-start.y", 50.0);
        double arenaZ = config.getDouble("arena-start.z", 161.5);
        float arenaYaw = (float) config.getDouble("arena-start.yaw", 0.0);
        float arenaPitch = (float) config.getDouble("arena-start.pitch", -6.0);

        World arenaBukkitWorld;
        try {
            arenaBukkitWorld = Bukkit.getWorld(arenaWorld);
            MessageUtils.sendDebugConsole("info", "Arena world &e" + arenaBukkitWorld.getName() + " &aloaded successfully!");
        } catch (NullPointerException e) {
            arenaBukkitWorld = Bukkit.getServer().getWorlds().get(0);
            MessageUtils.sendConsole("warning", "Arena world &e" + arenaWorld + " &cnot found! Using default world instead.");
            MessageUtils.sendConsole("info", "Arena world &e" + arenaBukkitWorld.getName() + " &aloaded successfully!");
        }
        arenaStartLocation = new Location(arenaBukkitWorld, arenaX, arenaY, arenaZ, arenaYaw, arenaPitch);

        String spectateWorld = config.getString("arena-spectate.world-name", "world");
        double spectateX = config.getDouble("arena-spectate.x", -220.5);
        double spectateY = config.getDouble("arena-spectate.y", 50.0);
        double spectateZ = config.getDouble("arena-spectate.z", 161.5);
        float spectateYaw = (float) config.getDouble("arena-spectate.yaw", 0.0);
        float spectatePitch = (float) config.getDouble("arena-spectate.pitch", -6.0);

        World spectateBukkitWorld;
        try {
            spectateBukkitWorld = Bukkit.getWorld(spectateWorld);
            MessageUtils.sendDebugConsole("info", "Spectate world &e" + spectateBukkitWorld.getName() + " &aloaded successfully!");
        } catch (NullPointerException e) {
            spectateBukkitWorld = Bukkit.getServer().getWorlds().get(0);
            MessageUtils.sendConsole("warning", "Spectate world &e" + spectateWorld + " &cnot found! Using default world instead.");
            MessageUtils.sendConsole("info", "Spectate world &e" + spectateBukkitWorld.getName() + " &aloaded successfully!");
        }
        spectateLocation = new Location(spectateBukkitWorld, spectateX, spectateY, spectateZ, spectateYaw, spectatePitch);
    }

    // String getters
    public String getWelcomeTitle() {
        return welcomeTitle;
    }

    public String getWelcomeSubTitle() {
        return welcomeSubTitle;
    }


    // Integer getters
    public int getArenaStartPlayerAmount() {
        return arenaStartPlayerAmount;
    }

    public int getGameStartCountdown() {
        return gameStartCountdown;
    }

    public int getTotalGameLength() {
        return totalGameLength;
    }

    public int getRedLightDelayCheckTime() {
        return redLightDelayCheckTime;
    }


    // Boolean getters
    public boolean isDebugMode() {
        return debugMode;
    }

    public boolean isWipeInventoryOnJoin() {
        return wipeInventoryOnJoin;
    }

    public boolean isHandleJoinSpawnEvent() {
        return handleJoinSpawnEvent;
    }

    public boolean isSendWelcomeTitle() {
        return sendWelcomeTitle;
    }

    public boolean isJoinPlayerInvulnerable() {
        return joinPlayerInvulnerable;
    }

    public boolean isLeavePlayerInvulnerable() {
        return leavePlayerInvulnerable;
    }

    public boolean isDisableGlobalHunger() {
        return disableGlobalHunger;
    }

    public boolean isDisableInGameHunger() {
        return disableInGameHunger;
    }

    public boolean isLosersSpectateGame() {
        return losersSpectateGame;
    }

    public boolean isRunWinCommands() {
        return runWinCommands;
    }

    public boolean isRunLoseCommands() {
        return runLoseCommands;
    }

    public boolean isSmiteLosingPlayers() {
        return smiteLosingPlayers;
    }


    // List getters
    public List<String> getDisabledWorldsList() {
        return disabledWorldsList;
    }

    public List<String> getBlockedInGameCommandsList() {
        return blockedInGameCommandsList;
    }

    public List<String> getWinCommandsList() {
        return winCommandsList;
    }

    public List<String> getLoseCommandsList() {
        return loseCommandsList;
    }


    // Material getters
    public Material getTopTriggerBlock() {
        return topTriggerBlock;
    }

    public Material getBottomTriggerBlock() {
        return bottomTriggerBlock;
    }


    // Location getters
    public Location getLobbyLocation() {
        return lobbyLocation;
    }

    public Location getArenaStartLocation() {
        return arenaStartLocation;
    }

    public Location getSpectateLocation() {
        return spectateLocation;
    }
}
