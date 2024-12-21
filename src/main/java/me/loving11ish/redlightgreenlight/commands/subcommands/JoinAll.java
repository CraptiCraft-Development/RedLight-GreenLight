package me.loving11ish.redlightgreenlight.commands.subcommands;

import me.loving11ish.redlightgreenlight.RedLightGreenLight;
import me.loving11ish.redlightgreenlight.commands.SubCommand;
import me.loving11ish.redlightgreenlight.utils.GameManager;
import me.loving11ish.redlightgreenlight.utils.MessageUtils;
import me.loving11ish.redlightgreenlight.utils.PlayerInventoryHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class JoinAll extends SubCommand {

    @Override
    public String getName() {
        return "joinall";
    }

    @Override
    public String getDescription() {
        return "Joins all online players into a round.";
    }

    @Override
    public String getSyntax() {
        return "/redlight joinall";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (player.hasPermission("redlight.command.joinall") || player.hasPermission("redlight.command.*") ||
                player.hasPermission("redlight.*") || player.isOp()) {
            List<Player> onlinePlayers = RedLightGreenLight.getPlugin().onlinePlayers;
            if (!(RedLightGreenLight.getPlugin().getConfigManager().getDisabledWorldsList().contains(player.getWorld().getName()))) {
                if (!(GameManager.getGameRunning() == 0)) {
                    MessageUtils.sendPlayer(player, RedLightGreenLight.getPlugin().getMessagesManager().getGameRunning());
                    return;
                }
                RedLightGreenLight.getPlugin().getFoliaLib().getScheduler().runAsync((task) -> {
                    for (Player onlinePlayer : onlinePlayers) {
                        String onPlayerName = onlinePlayer.getName();
                        Player onlinePlayerName = Bukkit.getServer().getPlayer(onPlayerName);
                        RedLightGreenLight.getPlugin().getFoliaLib().getScheduler().runAtEntity(onlinePlayerName, (t) -> {
                            if (onlinePlayerName != null) {
                                UUID onlineUUID = onlinePlayerName.getUniqueId();
                                if (!(GameManager.getGame1().contains(onlineUUID))) {
                                    GameManager.addToGame1(onlinePlayerName);
                                    PlayerInventoryHandler.storeAndClearInventory(onlinePlayerName);
                                    GameManager.startGameArena1(onlinePlayerName);
                                } else {
                                    MessageUtils.sendPlayer(onlinePlayerName, RedLightGreenLight.getPlugin().getMessagesManager().getFailedJoinArena());
                                }
                            }
                        });
                    }
                });
            } else {
                MessageUtils.sendPlayer(player, RedLightGreenLight.getPlugin().getMessagesManager().getDisabledWorld());
            }
        } else {
            MessageUtils.sendPlayer(player, RedLightGreenLight.getPlugin().getMessagesManager().getNoPermission());
        }
    }

    @Override
    public List<String> getSubcommandArguments(Player player, String[] args) {
        return null;
    }
}
