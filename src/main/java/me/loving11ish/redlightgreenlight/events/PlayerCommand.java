package me.loving11ish.redlightgreenlight.events;

import me.loving11ish.redlightgreenlight.RedLightGreenLight;
import me.loving11ish.redlightgreenlight.utils.ColorUtils;
import me.loving11ish.redlightgreenlight.utils.GameManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.List;
import java.util.UUID;

public class PlayerCommand implements Listener {

    List<String> bannedcommands = RedLightGreenLight.getPlugin().getConfig().getStringList("Blocked-commands-in-game");

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerCommandSend(PlayerCommandPreprocessEvent event){
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        if (GameManager.getGame1().contains(uuid)){
            if (!(player.hasPermission("redlight.bypass.commands") || player.hasPermission("redlight.*") || player.isOp())){
                String[] message = event.getMessage().split(" ");
                String command = message[0];

                for (String string : bannedcommands) {
                    if (command.equalsIgnoreCase(string)){
                        event.setCancelled(true);
                        player.sendMessage(ColorUtils.translateColorCodes(RedLightGreenLight.getPlugin().getConfig().getString("Command-send-error")));
                    }
                }
            }
        }
    }
}
