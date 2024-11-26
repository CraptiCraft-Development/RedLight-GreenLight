package me.loving11ish.redlightgreenlight.events;

import me.loving11ish.redlightgreenlight.RedLightGreenLight;
import me.loving11ish.redlightgreenlight.utils.GameManager;
import me.loving11ish.redlightgreenlight.utils.MessageUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.List;
import java.util.UUID;

public class PlayerCommand implements Listener {

    private final List<String> bannedCommands = RedLightGreenLight.getPlugin().getConfigManager().getBlockedInGameCommandsList();

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerCommandSend(PlayerCommandPreprocessEvent event){
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        if (GameManager.getGame1().contains(uuid)){
            if (!(player.hasPermission("redlight.bypass.commands") || player.hasPermission("redlight.*") || player.isOp())){
                String[] message = event.getMessage().split(" ");
                String command = message[0];

                for (String string : bannedCommands) {
                    if (command.equalsIgnoreCase(string)){
                        event.setCancelled(true);
                        MessageUtils.sendPlayer(player, RedLightGreenLight.getPlugin().getMessagesManager().getCommandSendError());
                    }
                }
            }
        }
    }
}
