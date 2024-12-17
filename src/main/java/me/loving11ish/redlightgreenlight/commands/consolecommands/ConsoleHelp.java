package me.loving11ish.redlightgreenlight.commands.consolecommands;

import me.loving11ish.redlightgreenlight.RedLightGreenLight;
import me.loving11ish.redlightgreenlight.commands.ConsoleCommand;
import me.loving11ish.redlightgreenlight.utils.MessageUtils;
import org.bukkit.plugin.PluginDescriptionFile;

import java.util.List;

public class ConsoleHelp extends ConsoleCommand {

    private final PluginDescriptionFile pluginInfo = RedLightGreenLight.getPlugin().getDescription();
    private final String pluginVersion = pluginInfo.getVersion();

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getDescription() {
        return "The help menu for the plugin.";
    }

    @Override
    public String getSyntax() {
        return "redlight help";
    }

    @Override
    public void perform(String[] args) {
        MessageUtils.sendConsole(this.getHelpMessage());
    }

    private String getHelpMessage() {
        List<String> helpMessage = RedLightGreenLight.getPlugin().getMessagesManager().getHelpCommand();
        StringBuilder stringBuilder = new StringBuilder();
        for (String message : helpMessage) {
            stringBuilder.append(message);
        }
        return stringBuilder.toString().replace("%version%", pluginVersion);
    }
}
