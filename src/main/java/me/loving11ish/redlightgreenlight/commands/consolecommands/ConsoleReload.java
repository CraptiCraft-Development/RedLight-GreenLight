package me.loving11ish.redlightgreenlight.commands.consolecommands;

import com.tcoded.folialib.FoliaLib;
import me.loving11ish.redlightgreenlight.RedLightGreenLight;
import me.loving11ish.redlightgreenlight.commands.ConsoleCommand;
import me.loving11ish.redlightgreenlight.utils.MessageUtils;

import java.util.concurrent.TimeUnit;

public class ConsoleReload extends ConsoleCommand {

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
        return "redlight reload";
    }

    @Override
    public void perform(String[] args) {
        MessageUtils.sendConsole(RedLightGreenLight.getPlugin().getMessagesManager().getPluginReloadStart());
        MessageUtils.broadcastMessage(RedLightGreenLight.getPlugin().getMessagesManager().getPluginReloadBroadcast());

        RedLightGreenLight.getPlugin().getCommandManager().reloadPlugin();

        FoliaLib foliaLib = RedLightGreenLight.getPlugin().getFoliaLib();
        foliaLib.getScheduler().runLater(() -> {
            MessageUtils.sendConsole(RedLightGreenLight.getPlugin().getMessagesManager().getPluginReloadComplete());
            MessageUtils.broadcastMessage(RedLightGreenLight.getPlugin().getMessagesManager().getPluginReloadComplete());
        }, 8L, TimeUnit.SECONDS);
    }
}
