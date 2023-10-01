package me.loving11ish.redlightgreenlight.externalhooks;

import me.loving11ish.redlightgreenlight.utils.ColorUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;

public class PlugManXAPI {

    static ConsoleCommandSender console = Bukkit.getConsoleSender();

    public static boolean isPlugManXEnabled() {
        try {
            Class.forName("com.rylinaux.plugman.PlugMan");
            console.sendMessage(ColorUtils.translateColorCodes("&6RedLightGreenLight: &aFound PlugManX main class at:"));
            console.sendMessage(ColorUtils.translateColorCodes("&6RedLightGreenLight: &dcom.rylinaux.plugman.PlugMan"));
            return true;
        }catch (ClassNotFoundException e){
            console.sendMessage(ColorUtils.translateColorCodes("&6RedLightGreenLight: &aCould not find PlugManX main class at:"));
            console.sendMessage(ColorUtils.translateColorCodes("&6RedLightGreenLight: &dcom.rylinaux.plugman.PlugMan"));
            return false;
        }
    }
}
