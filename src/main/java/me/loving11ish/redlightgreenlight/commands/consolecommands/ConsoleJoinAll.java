package me.loving11ish.redlightgreenlight.commands.consolecommands;

import me.loving11ish.redlightgreenlight.RedLightGreenLight;
import me.loving11ish.redlightgreenlight.commands.ConsoleCommand;
import me.loving11ish.redlightgreenlight.utils.ColorUtils;
import me.loving11ish.redlightgreenlight.utils.GameManager;
import me.loving11ish.redlightgreenlight.utils.PlayerInventoryHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

public class ConsoleJoinAll extends ConsoleCommand {

    Logger logger = RedLightGreenLight.getPlugin().getLogger();

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
        return "red joinall";
    }

    @Override
    public void perform(String[] args) {
        List<Player> onlinePlayers = new ArrayList<>(Bukkit.getServer().getOnlinePlayers());
        if (!(GameManager.getGameRunning() == 0)) {
            logger.warning(ColorUtils.translateColorCodes(RedLightGreenLight.getPlugin().getConfig().getString("Game-already-running")));
            return;
        }
        for (int i = 0; i < onlinePlayers.size(); i++) {
            String onPlayerName = onlinePlayers.get(i).getName();
            Player onlinePlayerName = Bukkit.getServer().getPlayer(onPlayerName);
            UUID onlineUUID = onlinePlayerName.getUniqueId();
            if (!(GameManager.getGame1().contains(onlineUUID))) {
                GameManager.addToGame1(onlinePlayerName);
                PlayerInventoryHandler.storeAndClearInventory(onlinePlayerName);
                GameManager.startGameArena1(onlinePlayerName);
            } else {
                onlinePlayerName.sendMessage(ColorUtils.translateColorCodes(RedLightGreenLight.getPlugin().getConfig().getString("Failed-join-arena")));
            }
        }
    }
}


