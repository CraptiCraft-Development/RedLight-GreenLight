package me.loving11ish.redlightgreenlight.commands.consolecommands;

import com.tcoded.folialib.FoliaLib;
import me.loving11ish.redlightgreenlight.RedLightGreenLight;
import me.loving11ish.redlightgreenlight.commands.ConsoleCommand;
import me.loving11ish.redlightgreenlight.utils.ColorUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;

import java.util.concurrent.TimeUnit;

public class ConsoleReload extends ConsoleCommand {

    ConsoleCommandSender console = Bukkit.getConsoleSender();

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
        console.sendMessage(ColorUtils.translateColorCodes(RedLightGreenLight.getPlugin().getConfig().getString("Plugin-reload-beginning")));
        FoliaLib foliaLib = RedLightGreenLight.getFoliaLib();
        RedLightGreenLight.getPlugin().onDisable();
        foliaLib.getImpl().runLater(() -> {
            Bukkit.getPluginManager().getPlugin("RedLightGreenLight").onEnable();
        }, 5L, TimeUnit.SECONDS);
        foliaLib.getImpl().runLater(() -> {
            RedLightGreenLight.getPlugin().reloadConfig();
            console.sendMessage(ColorUtils.translateColorCodes(RedLightGreenLight.getPlugin().getConfig().getString("Plugin-reload-successful")));
        }, 5L, TimeUnit.SECONDS);
    }
}
