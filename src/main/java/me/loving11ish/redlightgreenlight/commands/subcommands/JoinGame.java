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
        if (!(RedLightGreenLight.getPlugin().getConfig().getList("Disabled-worlds").contains(player.getWorld().getName()))){
            if (!(GameManager.getGame1().contains(uuid))){
                GameManager.addToGame1(player);
                PlayerInventoryHandler.storeAndClearInventory(player);
                GameManager.startGameArena1(player);
            }else {
                player.sendMessage(ColorUtils.translateColorCodes(RedLightGreenLight.getPlugin().getConfig().getString("Failed-join-arena")));
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
