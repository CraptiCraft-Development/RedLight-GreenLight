package me.loving11ish.redlightgreenlight.commands.subcommands;

import com.tcoded.folialib.FoliaLib;
import me.loving11ish.redlightgreenlight.RedLightGreenLight;
import me.loving11ish.redlightgreenlight.commands.SubCommand;
import me.loving11ish.redlightgreenlight.files.MessagesFileManager;
import me.loving11ish.redlightgreenlight.managers.filemanagers.ConfigManager;
import me.loving11ish.redlightgreenlight.managers.filemanagers.MessagesManager;
import me.loving11ish.redlightgreenlight.updatesystem.UpdateChecker;
import me.loving11ish.redlightgreenlight.utils.CountDownTasksUtils;
import me.loving11ish.redlightgreenlight.utils.GameManager;
import me.loving11ish.redlightgreenlight.utils.MessageUtils;
import me.loving11ish.redlightgreenlight.utils.PlayerInventoryHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class Reload extends SubCommand {

    @Override
    public String getName() {
        return "reload";
    }

    @Override
    public String getDescription() {
        return "This reloads the plugin config file.";
    }

    @Override
    public String getSyntax() {
        return "/redlight reload";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (player.hasPermission("redlight.reload") || player.hasPermission("redlight.*") || player.isOp()) {

            MessageUtils.sendPlayer(player, RedLightGreenLight.getPlugin().getMessagesManager().getPluginReloadStart());
            MessageUtils.sendConsole(RedLightGreenLight.getPlugin().getMessagesManager().getPluginReloadStart());
            MessageUtils.broadcastMessage(RedLightGreenLight.getPlugin().getMessagesManager().getPluginReloadBroadcast());

            RedLightGreenLight.getPlugin().getCommandManager().reloadPlugin();

            FoliaLib foliaLib = RedLightGreenLight.getPlugin().getFoliaLib();
            foliaLib.getScheduler().runLater(() -> {
                MessageUtils.sendPlayer(player, RedLightGreenLight.getPlugin().getMessagesManager().getPluginReloadComplete());
                MessageUtils.sendConsole(RedLightGreenLight.getPlugin().getMessagesManager().getPluginReloadComplete());
                MessageUtils.broadcastMessage(RedLightGreenLight.getPlugin().getMessagesManager().getPluginReloadComplete());
            }, 8L, TimeUnit.SECONDS);

        } else {
            MessageUtils.sendPlayer(player, RedLightGreenLight.getPlugin().getMessagesManager().getNoPermission());
        }
    }

    @Override
    public List<String> getSubcommandArguments(Player player, String[] args) {
        return null;
    }

}
