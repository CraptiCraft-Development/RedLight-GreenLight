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
        player.setInvulnerable(RedLightGreenLight.getPlugin().getConfig().getBoolean("Join-player-invulnerable"));
        player.setFoodLevel(20);
        if (RedLightGreenLight.getPlugin().getConfig().getBoolean("Wipe-inventory-on-join")){
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
        if (RedLightGreenLight.getPlugin().getConfig().getBoolean("Send-welcome-title")){
            player.sendTitle(ColorUtils.translateColorCodes(RedLightGreenLight.getPlugin().getConfig().getString("Welcome-player-title")),
                    ColorUtils.translateColorCodes(RedLightGreenLight.getPlugin().getConfig().getString("Welcome-player-subtitle")), 20, 100, 20);
        }
        if (RedLightGreenLight.getPlugin().getConfig().getBoolean("Handle-player-join-spawn-event")){
            double x = RedLightGreenLight.getPlugin().getConfig().getDouble("lobby-x");
            double y = RedLightGreenLight.getPlugin().getConfig().getDouble("lobby-y");
            double z = RedLightGreenLight.getPlugin().getConfig().getDouble("lobby-z");
            float yaw = (float) RedLightGreenLight.getPlugin().getConfig().getDouble("lobby-yaw");
            float pitch = (float) RedLightGreenLight.getPlugin().getConfig().getDouble("lobby-pitch");
            Location location = new Location(player.getWorld(), x, y, z, yaw, pitch);
            PaperLib.teleportAsync(player, location);
        }
    }
}
