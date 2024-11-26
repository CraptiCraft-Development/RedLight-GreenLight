package me.loving11ish.redlightgreenlight.events;

import me.loving11ish.redlightgreenlight.RedLightGreenLight;
import me.loving11ish.redlightgreenlight.utils.GameManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

import java.util.UUID;

public class PlayerHungerChange implements Listener {

    @EventHandler
    public void onPlayerHungerChange(FoodLevelChangeEvent event){
        Player player = (Player) event.getEntity();
        UUID uuid = player.getUniqueId();
        if (RedLightGreenLight.getPlugin().getConfigManager().isDisableGlobalHunger()){
            event.setCancelled(true);
            return;
        }
        if (RedLightGreenLight.getPlugin().getConfigManager().isDisableInGameHunger()){
            if (GameManager.getGame1().contains(uuid)){
                event.setCancelled(true);
            }
        }
    }
}
