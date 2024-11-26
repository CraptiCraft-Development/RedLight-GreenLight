package me.loving11ish.redlightgreenlight.commands;

import com.tcoded.folialib.FoliaLib;
import me.loving11ish.redlightgreenlight.RedLightGreenLight;
import me.loving11ish.redlightgreenlight.commands.consolecommands.ConsoleHelp;
import me.loving11ish.redlightgreenlight.commands.consolecommands.ConsoleJoinAll;
import me.loving11ish.redlightgreenlight.commands.consolecommands.ConsoleLeaveAll;
import me.loving11ish.redlightgreenlight.commands.consolecommands.ConsoleReload;
import me.loving11ish.redlightgreenlight.commands.subcommands.*;
import me.loving11ish.redlightgreenlight.files.MessagesFileManager;
import me.loving11ish.redlightgreenlight.managers.filemanagers.ConfigManager;
import me.loving11ish.redlightgreenlight.managers.filemanagers.MessagesManager;
import me.loving11ish.redlightgreenlight.updatesystem.UpdateChecker;
import me.loving11ish.redlightgreenlight.utils.CountDownTasksUtils;
import me.loving11ish.redlightgreenlight.utils.GameManager;
import me.loving11ish.redlightgreenlight.utils.MessageUtils;
import me.loving11ish.redlightgreenlight.utils.PlayerInventoryHandler;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class CommandManager implements TabExecutor {

    private final ArrayList<SubCommand> subCommands = new ArrayList<>();
    private final ArrayList<ConsoleCommand> consoleCommands = new ArrayList<>();

    public CommandManager() {
        //Get the subcommands so we can access them in the command manager class(here)
        consoleCommands.add(new ConsoleReload());
        consoleCommands.add(new ConsoleJoinAll());
        consoleCommands.add(new ConsoleLeaveAll());
        consoleCommands.add(new ConsoleHelp());
        subCommands.add(new Reload());
        subCommands.add(new JoinGame());
        subCommands.add(new LeaveGame());
        subCommands.add(new LeaveAll());
        subCommands.add(new JoinAll());
        subCommands.add(new Help());
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length > 0) {
                for (int i = 0; i < getSubCommands().size(); i++) {
                    if (args[0].equalsIgnoreCase(getSubCommands().get(i).getName())) {
                        getSubCommands().get(i).perform(player, args);
                    }
                }
            }
        } else if (sender instanceof ConsoleCommandSender) {
            if (args.length > 0) {
                for (int i = 0; i < getConsoleCommands().size(); i++) {
                    if (args[0].equalsIgnoreCase(getConsoleCommands().get(i).getName())) {
                        getConsoleCommands().get(i).perform(args);
                    }
                }
            } else {
                MessageUtils.sendConsole(consoleSyntaxMessage());
            }
        }
        return true;
    }

    public ArrayList<SubCommand> getSubCommands() {
        return subCommands;
    }

    public ArrayList<ConsoleCommand> getConsoleCommands() {
        return consoleCommands;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args) {
        if (args.length == 1) { //redlight <subcommand> <args>
            ArrayList<String> subcommandsArguments = new ArrayList<>();
            for (int i = 0; i < getSubCommands().size(); i++) {
                subcommandsArguments.add(getSubCommands().get(i).getName());
            }
            return subcommandsArguments;
        } else if (args.length >= 2) {
            for (int i = 0; i < getSubCommands().size(); i++) {
                if (args[0].equalsIgnoreCase(getSubCommands().get(i).getName())) {
                    return getSubCommands().get(i).getSubcommandArguments((Player) sender, args);
                }
            }
        }
        return null;
    }

    private String consoleSyntaxMessage() {
        List<String> messages = RedLightGreenLight.getPlugin().getMessagesManager().getConsoleSyntaxError();
        StringBuilder message = new StringBuilder();
        for (String msg : messages) {
            message.append(msg);
        }
        return message.toString();
    }

    public void reloadPlugin() {
        FoliaLib foliaLib = RedLightGreenLight.getPlugin().getFoliaLib();
        RedLightGreenLight.getPlugin().setPluginReloading(true);

        MessageUtils.sendConsole("-----------------------------------------");

        // End all games
        GameManager.endGameArena1();

        // Cancel all tasks
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
            if (!RedLightGreenLight.getPlugin().getOnlinePlayerUpdateTasks().isCancelled()) {
                RedLightGreenLight.getPlugin().getOnlinePlayerUpdateTasks().cancel();
            }
            if (foliaLib.isUnsupported()) {
                Bukkit.getScheduler().cancelTasks(RedLightGreenLight.getPlugin());
            }
            MessageUtils.sendConsole("&aBackground tasks have disabled successfully!");
        } catch (Exception e) {
            MessageUtils.sendConsole("&aBackground tasks have disabled successfully!");
        }

        // Restore players inv's & leave active game
        for (Player onlinePlayer : RedLightGreenLight.getPlugin().onlinePlayers) {
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

        foliaLib.getScheduler().runLaterAsync(() -> {
            // Reload main config.yml
            RedLightGreenLight.getPlugin().reloadConfig();

            // Load data from config.yml
            ConfigManager configManager = new ConfigManager(RedLightGreenLight.getPlugin().getConfig());
            configManager.loadConfigVales();
            RedLightGreenLight.getPlugin().setConfigManager(configManager);
            MessageUtils.sendConsole("&aReloading config.yml...");

            // Reload messages.yml
            MessagesFileManager messagesFileManager = new MessagesFileManager(RedLightGreenLight.getPlugin());
            RedLightGreenLight.getPlugin().setMessagesFileManager(messagesFileManager);

            // Load data from messages.yml
            MessagesManager messagesManager = new MessagesManager(RedLightGreenLight.getPlugin().getMessagesFileManager().getMessagesConfig());
            messagesManager.loadMessagesValues();
            RedLightGreenLight.getPlugin().setMessagesManager(messagesManager);
            MessageUtils.prefix = RedLightGreenLight.getPlugin().getMessagesManager().getPrefix();
            MessageUtils.setDebug(RedLightGreenLight.getPlugin().getConfigManager().isDebugMode());
            MessageUtils.sendConsole("&aReloading messages.yml...");

            foliaLib.getScheduler().runLaterAsync(() -> {
                // Signal that the plugin has been reloaded
                MessageUtils.sendConsole("&aPlugin has been reloaded successfully!");
                MessageUtils.sendConsole("-----------------------------------------");

                RedLightGreenLight.getPlugin().setPluginReloading(false);
            }, 6L, TimeUnit.SECONDS);
        }, 2L, TimeUnit.SECONDS);

        // Check for available updates
        new UpdateChecker(96866).getVersion(version -> {
            if (RedLightGreenLight.getPlugin().getDescription().getVersion().equalsIgnoreCase(version)) {
                MessageUtils.sendConsole(RedLightGreenLight.getPlugin().getMessagesManager().getNoUpdateAvailable1());
                MessageUtils.sendConsole(RedLightGreenLight.getPlugin().getMessagesManager().getNoUpdateAvailable2());
                MessageUtils.sendConsole(RedLightGreenLight.getPlugin().getMessagesManager().getNoUpdateAvailable3());
                RedLightGreenLight.getPlugin().setUpdateAvailable(false);
            } else {
                MessageUtils.sendConsole(RedLightGreenLight.getPlugin().getMessagesManager().getUpdateAvailable1());
                MessageUtils.sendConsole(RedLightGreenLight.getPlugin().getMessagesManager().getUpdateAvailable2());
                MessageUtils.sendConsole(RedLightGreenLight.getPlugin().getMessagesManager().getUpdateAvailable3());
                RedLightGreenLight.getPlugin().setUpdateAvailable(true);
            }
        });
    }
}
