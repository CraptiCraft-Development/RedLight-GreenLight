package me.loving11ish.redlightgreenlight.commands.subcommands;

import me.loving11ish.redlightgreenlight.RedLightGreenLight;
import me.loving11ish.redlightgreenlight.commands.SubCommand;
import me.loving11ish.redlightgreenlight.utils.GameManager;
import me.loving11ish.redlightgreenlight.utils.MessageUtils;
import org.bukkit.entity.Player;

import java.util.List;

public class LeaveAll extends SubCommand {

    @Override
    public String getName() {
        return "leaveall";
    }

    @Override
    public String getDescription() {
        return "Forces all online players to leave a round.";
    }

    @Override
    public String getSyntax() {
        return "/redlight leaveall";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (player.hasPermission("redlight.command.leaveall") || player.hasPermission("redlight.command.*") ||
                player.hasPermission("redlight.*") || player.isOp()) {
            if (!(RedLightGreenLight.getPlugin().getConfigManager().getDisabledWorldsList().contains(player.getWorld().getName()))) {
                if (GameManager.getGameRunning() == 0) {
                    MessageUtils.sendPlayer(player, RedLightGreenLight.getPlugin().getMessagesManager().getNoGameRunning());
                    return;
                }
                GameManager.endSpectatingGame();
                GameManager.endGameArena1();
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
