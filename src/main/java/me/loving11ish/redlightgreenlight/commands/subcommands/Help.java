package me.loving11ish.redlightgreenlight.commands.subcommands;

import me.loving11ish.redlightgreenlight.RedLightGreenLight;
import me.loving11ish.redlightgreenlight.commands.SubCommand;
import me.loving11ish.redlightgreenlight.utils.ColorUtils;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;

import java.util.List;

public class Help extends SubCommand {

    private PluginDescriptionFile pluginInfo = RedLightGreenLight.getPlugin().getDescription();
    private String pluginVersion = pluginInfo.getVersion();

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
        if (player.hasPermission("redlight.help")||player.hasPermission("redlight.*")||player.isOp()){
            player.sendMessage(ColorUtils.translateColorCodes(RedLightGreenLight.getPlugin().getConfig().getString("Help-1").replace("%version%", pluginVersion)));
            player.sendMessage(ColorUtils.translateColorCodes(RedLightGreenLight.getPlugin().getConfig().getString("Help-2").replace("%version%", pluginVersion)));
            player.sendMessage(ColorUtils.translateColorCodes(RedLightGreenLight.getPlugin().getConfig().getString("Help-3").replace("%version%", pluginVersion)));
            player.sendMessage(ColorUtils.translateColorCodes(RedLightGreenLight.getPlugin().getConfig().getString("Help-4").replace("%version%", pluginVersion)));
            player.sendMessage(ColorUtils.translateColorCodes(RedLightGreenLight.getPlugin().getConfig().getString("Help-5").replace("%version%", pluginVersion)));
            player.sendMessage(ColorUtils.translateColorCodes(RedLightGreenLight.getPlugin().getConfig().getString("Help-6").replace("%version%", pluginVersion)));
            player.sendMessage(ColorUtils.translateColorCodes(RedLightGreenLight.getPlugin().getConfig().getString("Help-7").replace("%version%", pluginVersion)));
            player.sendMessage(ColorUtils.translateColorCodes(RedLightGreenLight.getPlugin().getConfig().getString("Help-8").replace("%version%", pluginVersion)));
            player.sendMessage(ColorUtils.translateColorCodes(RedLightGreenLight.getPlugin().getConfig().getString("Help-9").replace("%version%", pluginVersion)));
            player.sendMessage(ColorUtils.translateColorCodes(RedLightGreenLight.getPlugin().getConfig().getString("Help-10").replace("%version%", pluginVersion)));
            player.sendMessage(ColorUtils.translateColorCodes(RedLightGreenLight.getPlugin().getConfig().getString("Help-11").replace("%version%", pluginVersion)));
            player.sendMessage(ColorUtils.translateColorCodes(RedLightGreenLight.getPlugin().getConfig().getString("Help-12").replace("%version%", pluginVersion)));
            player.sendMessage(ColorUtils.translateColorCodes(RedLightGreenLight.getPlugin().getConfig().getString("Help-13").replace("%version%", pluginVersion)));
            player.sendMessage(ColorUtils.translateColorCodes(RedLightGreenLight.getPlugin().getConfig().getString("Help-14").replace("%version%", pluginVersion)));
            player.sendMessage(ColorUtils.translateColorCodes(RedLightGreenLight.getPlugin().getConfig().getString("Help-15").replace("%version%", pluginVersion)));
            player.sendMessage(ColorUtils.translateColorCodes(RedLightGreenLight.getPlugin().getConfig().getString("Help-16").replace("%version%", pluginVersion)));
            player.sendMessage(ColorUtils.translateColorCodes(RedLightGreenLight.getPlugin().getConfig().getString("Help-17").replace("%version%", pluginVersion)));

        }else {
            player.sendMessage(ColorUtils.translateColorCodes(RedLightGreenLight.getPlugin().getConfig().getString("Help-command-no-permission")));
        }
    }

    @Override
    public List<String> getSubcommandArguments(Player player, String[] args) {
        return null;
    }
}
