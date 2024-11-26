package me.loving11ish.redlightgreenlight.utils;

import me.loving11ish.redlightgreenlight.RedLightGreenLight;
import org.bukkit.Bukkit;

import java.util.regex.PatternSyntaxException;

public class VersionCheckerUtils {

    private String serverPackage;
    private int version;
    private boolean versionCheckedSuccessfully = false;

    public VersionCheckerUtils() {
        try {
            serverPackage = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        } catch (ArrayIndexOutOfBoundsException e) {
            serverPackage = null;
        }
    }

    public void setVersion() {
        try {
            version = RedLightGreenLight.getPlugin().getServerVersion().getServerMajorVersionNumber();
            versionCheckedSuccessfully = true;
        } catch (NumberFormatException | PatternSyntaxException e) {
            versionCheckedSuccessfully = false;
            MessageUtils.sendConsole("&c-------------------------------------------");
            MessageUtils.sendConsole("&4Unable to process server version!");
            MessageUtils.sendConsole("&4Some features may break unexpectedly!");
            MessageUtils.sendConsole("&4Report any issues to the developer!");
            MessageUtils.sendConsole("&c-------------------------------------------");
        }
    }

    public String getServerPackage() {
        return serverPackage != null ? serverPackage : "Unknown";
    }

    public int getVersion() {
        return version;
    }

    public boolean isVersionCheckedSuccessfully() {
        return versionCheckedSuccessfully;
    }
}
