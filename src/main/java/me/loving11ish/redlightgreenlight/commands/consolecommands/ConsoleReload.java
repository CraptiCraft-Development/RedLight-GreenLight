package me.loving11ish.redlightgreenlight.commands.consolecommands;

import me.loving11ish.redlightgreenlight.RedLightGreenLight;
import me.loving11ish.redlightgreenlight.commands.ConsoleCommand;
import me.loving11ish.redlightgreenlight.utils.ColorUtils;
import org.bukkit.ChatColor;

import java.util.logging.Logger;

public class ConsoleReload extends ConsoleCommand {

    Logger logger = RedLightGreenLight.getPlugin().getLogger();

    @Override
    public String getName() {
        return "reload";
    }

    @Override
    public String getDescription() {
        return ChatColor.AQUA + "This reloads the plugin config file.";
    }

    @Override
    public String getSyntax() {
        return ChatColor.AQUA + "redlight reload";
    }

    @Override
    public void perform(String[] args) {
        RedLightGreenLight.getPlugin().reloadConfig();
        logger.info(ColorUtils.translateColorCodes(RedLightGreenLight.getPlugin().getConfig().getString("Plugin-reload-successful")));
    }
}
