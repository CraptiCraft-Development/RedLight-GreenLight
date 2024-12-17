package me.loving11ish.redlightgreenlight.commands.subcommands;

import me.loving11ish.redlightgreenlight.RedLightGreenLight;
import me.loving11ish.redlightgreenlight.commands.SubCommand;
import me.loving11ish.redlightgreenlight.utils.GameManager;
import me.loving11ish.redlightgreenlight.utils.MessageUtils;
import me.loving11ish.redlightgreenlight.utils.PlayerInventoryHandler;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class LeaveGame extends SubCommand {

    @Override
    public String getName() {
        return "leave";
    }

    @Override
    public String getDescription() {
        return "Leave the game & return to the lobby.";
    }

    @Override
    public String getSyntax() {
        return "/redlight leave";
    }

    @Override
    public void perform(Player player, String[] args) {
        UUID uuid = player.getUniqueId();
        if (!(RedLightGreenLight.getPlugin().getConfigManager().getDisabledWorldsList().contains(player.getWorld().getName()))) {
            if (GameManager.getGame1().contains(uuid) || GameManager.getSpectatingPlayers().contains(uuid)) {
                if (GameManager.getPlayersInRound().contains(uuid)) {
                    GameManager.leaveRound(player);
                }
                if (GameManager.getSpectatingPlayers().contains(uuid)) {
                    GameManager.leaveSpectating(player);
                }
                GameManager.teleportToLobby(player);
                PlayerInventoryHandler.clearInventory(player);
                PlayerInventoryHandler.restoreInventory(player);
                GameManager.leaveGame1(player);
                MessageUtils.sendPlayer(player, RedLightGreenLight.getPlugin().getMessagesManager().getSuccessfulLeaveGame());

            } else {
                MessageUtils.sendPlayer(player, RedLightGreenLight.getPlugin().getMessagesManager().getPlayerNotInGame());
            }
        } else {
            MessageUtils.sendPlayer(player, RedLightGreenLight.getPlugin().getMessagesManager().getDisabledWorld());
        }
    }

    @Override
    public List<String> getSubcommandArguments(Player player, String[] args) {
        return null;
    }
}
