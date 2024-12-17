package me.loving11ish.redlightgreenlight.managers.filemanagers;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class MessagesManager {

    private final FileConfiguration messages;

    // Strings
    private String prefix;
    private String joinedGame;
    private String commandSendError;
    private String failedJoinArena;
    private String successfulLeaveGame;
    private String playerNotInGame;
    private String waitingForPlayers;
    private String gameRunning;
    private String noGameRunning;
    private String spectatingGame;
    private String roundEndTitle;
    private String roundEndSubtitle;
    private String greenLight;
    private String redLight;
    private String gameStartTitle;
    private String gameStartSubtitle;
    private String gameWinTitle;
    private String gameWinSubtitle;
    private String gameLoseTitle;
    private String gameLoseSubtitle;
    private String gameLeaveTitle;
    private String gameLeaveSubtitle;
    private String pluginReloadStart;
    private String pluginReloadComplete;
    private String pluginReloadBroadcast;
    private String disabledWorld;
    private String noPermission;
    private String unknownCommand;

    private String updateAvailable1;
    private String updateAvailable2;
    private String updateAvailable3;
    private String noUpdateAvailable1;
    private String noUpdateAvailable2;
    private String noUpdateAvailable3;
    private String updateError;

    // Booleans
    private boolean sendRedLightTitle;
    private boolean sendLeaveTitle;

    // Lists
    private List<String> consoleSyntaxError;
    private List<String> helpCommand;

    public MessagesManager(FileConfiguration messages) {
        this.messages = messages;
    }

    public void loadMessagesValues() {
        // Strings
        prefix = messages.getString("prefix", "&f[&4&lRL&r&c-&a&lGL&f]");
        joinedGame = messages.getString("joined-game", "&aYou have joined the game.\n&aNow prepare to move only when told!");
        commandSendError = messages.getString("command-send-error", "&cSorry, that command cannot be run during gameplay!");
        failedJoinArena = messages.getString("failed-join-arena", "&cYou are already part of a game.\n&cUse &e/redlight leave &cto return to main lobby.");
        successfulLeaveGame = messages.getString("successful-leave-game", "&aYou left the game and were teleported back to the lobby!");
        playerNotInGame = messages.getString("player-not-in-game", "&cYou are not currently in a game!");
        waitingForPlayers = messages.getString("waiting-for-enough-players", "&eYou are now in the queue to play.&r\n&eNow waiting for enough players to begin the game.&r\n&eUse &b/redlight leave &eto leave the game.");
        gameRunning = messages.getString("game-already-running", "&cSorry there is already a game running!");
        noGameRunning = messages.getString("no-game-running", "&cSorry there is not currently a game running!");
        spectatingGame = messages.getString("spectating-message", "&aYou are now spectating the arena! \n&aUse &b/redlight leave &a to return to spawn.");
        roundEndTitle = messages.getString("round-end-title", "&eNo One Won The Game!");
        roundEndSubtitle = messages.getString("round-end-subtitle", "&a&lGG's All Round");
        greenLight = messages.getString("greenLight-message", "&aGreen Light");
        redLight = messages.getString("redLight-message", "&cRed Light");
        gameStartTitle = messages.getString("game-start-title", "&a&lLet The Race Begin!");
        gameStartSubtitle = messages.getString("game-start-subtitle", "&bBeat everyone to the finish line!");
        gameWinTitle = messages.getString("game-win-title", "&6&lCongratulations!  You Made it!");
        gameWinSubtitle = messages.getString("game-win-subtitle", "&bLets wait and see who will join you");
        gameLoseTitle = messages.getString("game-loose-title", "&c&lOOF You didn't stand still!");
        gameLoseSubtitle = messages.getString("game-loose-subtitle", "&4Better luck next time!");
        gameLeaveTitle = messages.getString("game-leave-title", "&a&lYou Left The Game!");
        gameLeaveSubtitle = messages.getString("game-leave-subtitle", "&eThank you for playing!");
        pluginReloadStart = messages.getString("plugin-reload-beginning", "&aBeginning plugin reload...");
        pluginReloadComplete = messages.getString("plugin-reload-successful", "&aPlugin reload complete!");
        pluginReloadBroadcast = messages.getString("plugin-reload-broadcast", "&aThe plugin is being reloaded!\\n&aPlease wait until the plugin is reloaded before starting a new game.");
        disabledWorld = messages.getString("disabled-world-message", "&4&lRedLight&r&c-&a&lGreenLight has been disabled in this world.");
        noPermission = messages.getString("no-permission", "&cYou do not have permission to do that!");
        unknownCommand = messages.getString("unknown-command", "&cCommand not found! Use &e/redlight help.");

        updateAvailable1 = messages.getString("update-available.1", "&4*-------------------------------------------*");
        updateAvailable2 = messages.getString("update-available.2", "&cA new version is available!");
        updateAvailable3 = messages.getString("update-available.3", "&4*-------------------------------------------*");
        noUpdateAvailable1 = messages.getString("no-update-available.1", "&a*-------------------------------------------*");
        noUpdateAvailable2 = messages.getString("no-update-available.2", "&aPlugin is up to date!");
        noUpdateAvailable3 = messages.getString("no-update-available.3", "&a*-------------------------------------------*");
        updateError = messages.getString("update-check-failure", "&4Unable to check for updates! - &c");

        // Booleans
        sendRedLightTitle = messages.getBoolean("redLight-title-enable", true);
        sendLeaveTitle = messages.getBoolean("show-leave-game-title", false);

        // Lists
        consoleSyntaxError = messages.getStringList("console-syntax-error");
        helpCommand = messages.getStringList("help-command");
    }

    // String getters
    public String getPrefix() {
        return prefix;
    }

    public String getJoinedGame() {
        return joinedGame;
    }

    public String getCommandSendError() {
        return commandSendError;
    }

    public String getFailedJoinArena() {
        return failedJoinArena;
    }

    public String getSuccessfulLeaveGame() {
        return successfulLeaveGame;
    }

    public String getPlayerNotInGame() {
        return playerNotInGame;
    }

    public String getWaitingForPlayers() {
        return waitingForPlayers;
    }

    public String getGameRunning() {
        return gameRunning;
    }

    public String getNoGameRunning() {
        return noGameRunning;
    }

    public String getSpectatingGame() {
        return spectatingGame;
    }

    public String getRoundEndTitle() {
        return roundEndTitle;
    }

    public String getRoundEndSubtitle() {
        return roundEndSubtitle;
    }

    public String getGreenLight() {
        return greenLight;
    }

    public String getRedLight() {
        return redLight;
    }

    public String getGameStartTitle() {
        return gameStartTitle;
    }

    public String getGameStartSubtitle() {
        return gameStartSubtitle;
    }

    public String getGameWinTitle() {
        return gameWinTitle;
    }

    public String getGameWinSubtitle() {
        return gameWinSubtitle;
    }

    public String getGameLoseTitle() {
        return gameLoseTitle;
    }

    public String getGameLoseSubtitle() {
        return gameLoseSubtitle;
    }

    public String getGameLeaveTitle() {
        return gameLeaveTitle;
    }

    public String getGameLeaveSubtitle() {
        return gameLeaveSubtitle;
    }

    public String getPluginReloadStart() {
        return pluginReloadStart;
    }

    public String getPluginReloadComplete() {
        return pluginReloadComplete;
    }

    public String getPluginReloadBroadcast() {
        return pluginReloadBroadcast;
    }

    public String getDisabledWorld() {
        return disabledWorld;
    }

    public String getNoPermission() {
        return noPermission;
    }

    public String getUnknownCommand() {
        return unknownCommand;
    }

    public String getUpdateAvailable1() {
        return updateAvailable1;
    }

    public String getUpdateAvailable2() {
        return updateAvailable2;
    }

    public String getUpdateAvailable3() {
        return updateAvailable3;
    }

    public String getNoUpdateAvailable1() {
        return noUpdateAvailable1;
    }

    public String getNoUpdateAvailable2() {
        return noUpdateAvailable2;
    }

    public String getNoUpdateAvailable3() {
        return noUpdateAvailable3;
    }

    public String getUpdateError() {
        return updateError;
    }


    // Boolean getters
    public boolean isSendRedLightTitle() {
        return sendRedLightTitle;
    }

    public boolean isSendLeaveTitle() {
        return sendLeaveTitle;
    }


    // List getters
    public List<String> getConsoleSyntaxError() {
        return consoleSyntaxError;
    }

    public List<String> getHelpCommand() {
        return helpCommand;
    }
}
