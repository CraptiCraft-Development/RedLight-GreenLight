package me.loving11ish.redlightgreenlight.commands.subcommands;

import me.loving11ish.redlightgreenlight.RedLightGreenLight;
import me.loving11ish.redlightgreenlight.commands.SubCommand;
import me.loving11ish.redlightgreenlight.utils.*;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class JoinGame extends SubCommand {

    @Override
    public String getName() {
        return "join";
    }

    @Override
    public String getDescription() {
        return "Join the game";
    }

    @Override
    public String getSyntax() {
        return "/redlight join";
    }

    @Override
    public void perform(Player player, String[] args) {
        UUID uuid = player.getUniqueId();
        if (!(RedLightGreenLight.getPlugin().getConfigManager().getDisabledWorldsList().contains(player.getWorld().getName()))) {
            if (!(GameManager.getGameRunning() == 0)) {
                MessageUtils.sendPlayer(player, RedLightGreenLight.getPlugin().getMessagesManager().getGameRunning());
                return;
            }
            if (!(GameManager.getGame1().contains(uuid))) {
                GameManager.addToGame1(player);
                PlayerInventoryHandler.storeAndClearInventory(player);
                GameManager.startGameArena1(player);
            } else {
                MessageUtils.sendPlayer(player, RedLightGreenLight.getPlugin().getMessagesManager().getFailedJoinArena());
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
