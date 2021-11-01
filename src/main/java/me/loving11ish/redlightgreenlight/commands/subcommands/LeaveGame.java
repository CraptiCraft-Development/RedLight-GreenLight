package me.loving11ish.redlightgreenlight.commands.subcommands;

import me.loving11ish.redlightgreenlight.RedLightGreenLight;
import me.loving11ish.redlightgreenlight.commands.SubCommand;
import me.loving11ish.redlightgreenlight.utils.ColorUtils;
import me.loving11ish.redlightgreenlight.utils.GameManager;
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
        if (!(RedLightGreenLight.getPlugin().getConfig().getList("Disabled-worlds").contains(player.getWorld().getName()))){
            if (GameManager.getGame1().contains(uuid)||GameManager.getSpectatingPlayers().contains(uuid)){
                if (GameManager.getPlayersInRound().contains(uuid)){
                    GameManager.leaveRound(player);
                }
                if (GameManager.getSpectatingPlayers().contains(uuid)){
                    GameManager.leaveSpectating(player);
                }
                GameManager.teleportToLobby(player);
                PlayerInventoryHandler.clearInventory(player);
                PlayerInventoryHandler.restoreInventory(player);
                GameManager.leaveGame1(player);
                player.sendMessage(ColorUtils.translateColorCodes(RedLightGreenLight.getPlugin().getConfig().getString("Successful-leave-game")));
            }else {
                player.sendMessage(ColorUtils.translateColorCodes(RedLightGreenLight.getPlugin().getConfig().getString("Player-not-in-game")));
            }
        }else {
            player.sendMessage(ColorUtils.translateColorCodes(RedLightGreenLight.getPlugin().getConfig().getString("Disabled-world-message")));
        }
    }

    @Override
    public List<String> getSubcommandArguments(Player player, String[] args) {
        return null;
    }
}
