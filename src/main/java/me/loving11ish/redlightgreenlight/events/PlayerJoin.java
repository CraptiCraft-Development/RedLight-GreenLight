package me.loving11ish.redlightgreenlight.events;

import io.papermc.lib.PaperLib;
import me.loving11ish.redlightgreenlight.RedLightGreenLight;
import me.loving11ish.redlightgreenlight.utils.ColorUtils;
import me.loving11ish.redlightgreenlight.utils.GameManager;
import me.loving11ish.redlightgreenlight.utils.PlayerInventoryHandler;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public class PlayerJoin implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        RedLightGreenLight.getPlugin().getFoliaLib().getScheduler().runAtEntity(player, (task) -> {
            player.setInvulnerable(RedLightGreenLight.getPlugin().getConfigManager().isJoinPlayerInvulnerable());
            player.setFoodLevel(20);
            if (RedLightGreenLight.getPlugin().getConfigManager().isWipeInventoryOnJoin()){
                if (!(player.hasPermission("redlight.bypass.joinwipe") || player.hasPermission("redlight.*") || player.isOp())){
                    PlayerInventoryHandler.clearInventory(player);
                }
            }
            if (GameManager.getGame1().contains(uuid)){
                GameManager.leaveGame1(player);
            }
            if (GameManager.getPlayersInRound().contains(uuid)){
                GameManager.leaveRound(player);
            }
            if (GameManager.getSpectatingPlayers().contains(uuid)){
                GameManager.leaveSpectating(player);
            }
            if (RedLightGreenLight.getPlugin().getConfigManager().isSendWelcomeTitle()){
                player.sendTitle(ColorUtils.translateColorCodes(RedLightGreenLight.getPlugin().getConfigManager().getWelcomeTitle()),
                        ColorUtils.translateColorCodes(RedLightGreenLight.getPlugin().getConfigManager().getWelcomeSubTitle()), 20, 100, 20);
            }
            if (RedLightGreenLight.getPlugin().getConfigManager().isHandleJoinSpawnEvent()){
                Location location = RedLightGreenLight.getPlugin().getConfigManager().getLobbyLocation();
                PaperLib.teleportAsync(player, location);
            }
        });
    }
}
