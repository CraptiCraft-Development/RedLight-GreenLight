package me.loving11ish.redlightgreenlight.commands.consolecommands;

import me.loving11ish.redlightgreenlight.RedLightGreenLight;
import me.loving11ish.redlightgreenlight.commands.ConsoleCommand;
import me.loving11ish.redlightgreenlight.utils.GameManager;
import me.loving11ish.redlightgreenlight.utils.MessageUtils;

public class ConsoleLeaveAll extends ConsoleCommand {

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
            MessageUtils.sendConsole(RedLightGreenLight.getPlugin().getMessagesManager().getNoGameRunning());
            return;
        }
        GameManager.endSpectatingGame();
        GameManager.endGameArena1();
    }
}
