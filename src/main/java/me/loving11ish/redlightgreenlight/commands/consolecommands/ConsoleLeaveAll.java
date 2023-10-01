package me.loving11ish.redlightgreenlight.commands.consolecommands;

import me.loving11ish.redlightgreenlight.RedLightGreenLight;
import me.loving11ish.redlightgreenlight.commands.ConsoleCommand;
import me.loving11ish.redlightgreenlight.utils.ColorUtils;
import me.loving11ish.redlightgreenlight.utils.GameManager;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;

public class ConsoleLeaveAll extends ConsoleCommand {

    ConsoleCommandSender console = Bukkit.getConsoleSender();

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
        return "redlight leavall";
    }

    @Override
    public void perform(String[] args) {
        if (GameManager.getGameRunning() == 0) {
            console.sendMessage(ColorUtils.translateColorCodes(RedLightGreenLight.getPlugin().getConfig().getString("No-game-running")));
            return;
        }
        GameManager.endSpectatingGame();
        GameManager.endGameArena1();
    }
}
