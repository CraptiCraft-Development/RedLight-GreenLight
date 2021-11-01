package me.loving11ish.redlightgreenlight.commands.consolecommands;

import me.loving11ish.redlightgreenlight.RedLightGreenLight;
import me.loving11ish.redlightgreenlight.commands.ConsoleCommand;
import me.loving11ish.redlightgreenlight.utils.ColorUtils;
import org.bukkit.plugin.PluginDescriptionFile;

import java.util.logging.Logger;

public class ConsoleHelp extends ConsoleCommand {

    private PluginDescriptionFile pluginInfo = RedLightGreenLight.getPlugin().getDescription();
    private String pluginVersion = pluginInfo.getVersion();

    Logger logger = RedLightGreenLight.getPlugin().getLogger();

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
        logger.info(ColorUtils.translateColorCodes(RedLightGreenLight.getPlugin().getConfig().getString("Help-1").replace("%version%", pluginVersion)));
        logger.info(ColorUtils.translateColorCodes(RedLightGreenLight.getPlugin().getConfig().getString("Help-2").replace("%version%", pluginVersion)));
        logger.info(ColorUtils.translateColorCodes(RedLightGreenLight.getPlugin().getConfig().getString("Help-3").replace("%version%", pluginVersion)));
        logger.info(ColorUtils.translateColorCodes(RedLightGreenLight.getPlugin().getConfig().getString("Help-4").replace("%version%", pluginVersion)));
        logger.info(ColorUtils.translateColorCodes(RedLightGreenLight.getPlugin().getConfig().getString("Help-5").replace("%version%", pluginVersion)));
        logger.info(ColorUtils.translateColorCodes(RedLightGreenLight.getPlugin().getConfig().getString("Help-6").replace("%version%", pluginVersion)));
        logger.info(ColorUtils.translateColorCodes(RedLightGreenLight.getPlugin().getConfig().getString("Help-7").replace("%version%", pluginVersion)));
        logger.info(ColorUtils.translateColorCodes(RedLightGreenLight.getPlugin().getConfig().getString("Help-8").replace("%version%", pluginVersion)));
        logger.info(ColorUtils.translateColorCodes(RedLightGreenLight.getPlugin().getConfig().getString("Help-9").replace("%version%", pluginVersion)));
        logger.info(ColorUtils.translateColorCodes(RedLightGreenLight.getPlugin().getConfig().getString("Help-10").replace("%version%", pluginVersion)));
        logger.info(ColorUtils.translateColorCodes(RedLightGreenLight.getPlugin().getConfig().getString("Help-11").replace("%version%", pluginVersion)));
        logger.info(ColorUtils.translateColorCodes(RedLightGreenLight.getPlugin().getConfig().getString("Help-12").replace("%version%", pluginVersion)));
        logger.info(ColorUtils.translateColorCodes(RedLightGreenLight.getPlugin().getConfig().getString("Help-13").replace("%version%", pluginVersion)));
        logger.info(ColorUtils.translateColorCodes(RedLightGreenLight.getPlugin().getConfig().getString("Help-14").replace("%version%", pluginVersion)));
        logger.info(ColorUtils.translateColorCodes(RedLightGreenLight.getPlugin().getConfig().getString("Help-15").replace("%version%", pluginVersion)));
        logger.info(ColorUtils.translateColorCodes(RedLightGreenLight.getPlugin().getConfig().getString("Help-16").replace("%version%", pluginVersion)));
        logger.info(ColorUtils.translateColorCodes(RedLightGreenLight.getPlugin().getConfig().getString("Help-17").replace("%version%", pluginVersion)));
        logger.info(ColorUtils.translateColorCodes(RedLightGreenLight.getPlugin().getConfig().getString("Help-18").replace("%version%", pluginVersion)));
        logger.info(ColorUtils.translateColorCodes(RedLightGreenLight.getPlugin().getConfig().getString("Help-19").replace("%version%", pluginVersion)));

    }
}
