package me.loving11ish.redlightgreenlight.updatesystem;

import com.tcoded.folialib.FoliaLib;
import me.loving11ish.redlightgreenlight.RedLightGreenLight;
import me.loving11ish.redlightgreenlight.utils.ColorUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.util.Consumer;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

public class UpdateChecker {

    ConsoleCommandSender console = Bukkit.getConsoleSender();

    private int resourceId;

    public UpdateChecker(int resourceId) {
        this.resourceId = resourceId;
    }

    public void getVersion(final Consumer<String> consumer) {
        FoliaLib foliaLib = RedLightGreenLight.getFoliaLib();
        foliaLib.getImpl().runAsync((task) -> {
            try (InputStream inputStream = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + this.resourceId).openStream(); Scanner scanner = new Scanner(inputStream)) {
                if (scanner.hasNext()) {
                    consumer.accept(scanner.next());
                }
            } catch (IOException exception) {
                console.sendMessage(ColorUtils.translateColorCodes(RedLightGreenLight.getPlugin().getConfig().getString("Update-check-failure") + exception.getMessage()));
            }
        });
    }
}
