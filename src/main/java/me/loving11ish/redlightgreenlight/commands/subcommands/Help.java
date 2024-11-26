package me.loving11ish.redlightgreenlight.commands.subcommands;

import me.loving11ish.redlightgreenlight.RedLightGreenLight;
import me.loving11ish.redlightgreenlight.commands.SubCommand;
import me.loving11ish.redlightgreenlight.utils.MessageUtils;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;

import java.util.List;

public class Help extends SubCommand {

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
        return "/redlight help";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (player.hasPermission("redlight.help") || player.hasPermission("redlight.*") || player.isOp()) {
            MessageUtils.sendPlayer(player, this.getHelpMessage());
        } else {
            MessageUtils.sendPlayer(player, RedLightGreenLight.getPlugin().getMessagesManager().getNoPermission());
        }
    }

    @Override
    public List<String> getSubcommandArguments(Player player, String[] args) {
        return null;
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
