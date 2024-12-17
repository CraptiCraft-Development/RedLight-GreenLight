package me.loving11ish.redlightgreenlight.updatesystem;

import me.loving11ish.redlightgreenlight.RedLightGreenLight;
import me.loving11ish.redlightgreenlight.utils.MessageUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinEvent implements Listener {

    private final RedLightGreenLight plugin;

    public JoinEvent(RedLightGreenLight plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("playergui.update")) {
            new UpdateChecker(96866).getVersion(version -> {
                try {
                    if (!(plugin.getDescription().getVersion().equalsIgnoreCase(version))) {
                        MessageUtils.sendPlayer(player, this.plugin.getMessagesManager().getUpdateAvailable1());
                        MessageUtils.sendPlayer(player, this.plugin.getMessagesManager().getUpdateAvailable2());
                        MessageUtils.sendPlayer(player, this.plugin.getMessagesManager().getUpdateAvailable3());
                    }
                } catch (NullPointerException e) {
                    MessageUtils.sendPlayer(player, this.plugin.getMessagesManager().getUpdateError());
                }
            });
        }
    }
}
