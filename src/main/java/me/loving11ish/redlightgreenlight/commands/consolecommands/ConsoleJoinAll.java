package me.loving11ish.redlightgreenlight.commands.consolecommands;

import me.loving11ish.redlightgreenlight.RedLightGreenLight;
import me.loving11ish.redlightgreenlight.commands.ConsoleCommand;
import me.loving11ish.redlightgreenlight.utils.GameManager;
import me.loving11ish.redlightgreenlight.utils.MessageUtils;
import me.loving11ish.redlightgreenlight.utils.PlayerInventoryHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class ConsoleJoinAll extends ConsoleCommand {

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
        List<Player> onlinePlayers = RedLightGreenLight.getPlugin().onlinePlayers;
        if (!(GameManager.getGameRunning() == 0)) {
            MessageUtils.sendConsole(RedLightGreenLight.getPlugin().getMessagesManager().getGameRunning());
            return;
        }

        RedLightGreenLight.getPlugin().getFoliaLib().getScheduler().runAsync((task) -> {
            for (Player onlinePlayer : onlinePlayers) {
                String onPlayerName = onlinePlayer.getName();
                Player onlinePlayerName = Bukkit.getServer().getPlayer(onPlayerName);
                RedLightGreenLight.getPlugin().getFoliaLib().getScheduler().runAtEntity(onlinePlayerName, (t) -> {
                    if (onlinePlayerName != null){
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

    }
}


