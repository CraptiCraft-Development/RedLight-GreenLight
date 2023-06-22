package me.loving11ish.redlightgreenlight.commands.subcommands;

import com.tcoded.folialib.FoliaLib;
import me.loving11ish.redlightgreenlight.RedLightGreenLight;
import me.loving11ish.redlightgreenlight.commands.SubCommand;
import me.loving11ish.redlightgreenlight.utils.ColorUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class Reload extends SubCommand {


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
        return ChatColor.AQUA + "/redlight reload";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (player.hasPermission("redlight.reload")||player.hasPermission("redlight.*")||player.isOp()){
            player.sendMessage(ColorUtils.translateColorCodes(RedLightGreenLight.getPlugin().getConfig().getString("Plugin-reload-beginning")));
            FoliaLib foliaLib = RedLightGreenLight.getFoliaLib();
            RedLightGreenLight.getPlugin().onDisable();
            foliaLib.getImpl().runLater(() -> {
                Bukkit.getPluginManager().getPlugin("RedLightGreenLight").onEnable();
            }, 5L, TimeUnit.SECONDS);
            foliaLib.getImpl().runLater(() -> {
                RedLightGreenLight.getPlugin().reloadConfig();
                player.sendMessage(ColorUtils.translateColorCodes(RedLightGreenLight.getPlugin().getConfig().getString("Plugin-reload-successful")));
            }, 5L, TimeUnit.SECONDS);
        }else {
            player.sendMessage(ColorUtils.translateColorCodes(RedLightGreenLight.getPlugin().getConfig().getString("Reload-command-no-permission")));
        }
    }

    @Override
    public List<String> getSubcommandArguments(Player player, String[] args) {
        return null;
    }
}
