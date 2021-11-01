package me.loving11ish.redlightgreenlight.commands;

import me.loving11ish.redlightgreenlight.RedLightGreenLight;
import me.loving11ish.redlightgreenlight.commands.consolecommands.ConsoleHelp;
import me.loving11ish.redlightgreenlight.commands.consolecommands.ConsoleJoinAll;
import me.loving11ish.redlightgreenlight.commands.consolecommands.ConsoleLeaveAll;
import me.loving11ish.redlightgreenlight.commands.consolecommands.ConsoleReload;
import me.loving11ish.redlightgreenlight.commands.subcommands.*;
import me.loving11ish.redlightgreenlight.utils.ColorUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class CommandManager implements TabExecutor {

    private ArrayList<SubCommand> subcommands = new ArrayList<>();
    private ArrayList<ConsoleCommand> consolecommands = new ArrayList<>();
    Logger logger = RedLightGreenLight.getPlugin().getLogger();

    public CommandManager() {
        //Get the subcommands so we can access them in the command manager class(here)
        consolecommands.add(new ConsoleReload());
        consolecommands.add(new ConsoleJoinAll());
        consolecommands.add(new ConsoleLeaveAll());
        consolecommands.add(new ConsoleHelp());
        subcommands.add(new Reload());
        subcommands.add(new JoinGame());
        subcommands.add(new LeaveGame());
        subcommands.add(new LeaveAll());
        subcommands.add(new JoinAll());
        subcommands.add(new Help());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length > 0) {
                for (int i = 0; i < getSubCommands().size(); i++) {
                    if (args[0].equalsIgnoreCase(getSubCommands().get(i).getName())) {
                        getSubCommands().get(i).perform(player, args);
                    }
                }
            }
        } else if (!(sender instanceof Player)) {
            if (args.length > 0) {
                for (int i = 0; i < getConsoleCommands().size(); i++) {
                    if (args[0].equalsIgnoreCase(getConsoleCommands().get(i).getName())) {
                        getConsoleCommands().get(i).perform(args);
                    }
                }
            } else {
                logger.info(ColorUtils.translateColorCodes(RedLightGreenLight.getPlugin().getConfig().getString("Syntax-error-1")));
                logger.info(ColorUtils.translateColorCodes(RedLightGreenLight.getPlugin().getConfig().getString("Syntax-error-2")));
                logger.info(ColorUtils.translateColorCodes(RedLightGreenLight.getPlugin().getConfig().getString("Syntax-error-3")));
            }
        }
        return true;
    }

    public ArrayList<SubCommand> getSubCommands() {
        return subcommands;
    }

    public ArrayList<ConsoleCommand> getConsoleCommands() {
        return consolecommands;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) { //redlight <subcommand> <args>
            ArrayList<String> subcommandsArguments = new ArrayList<>();
            for (int i = 0; i < getSubCommands().size(); i++) {
                subcommandsArguments.add(getSubCommands().get(i).getName());
            }
            return subcommandsArguments;
        } else if (args.length >= 2) {
            for (int i = 0; i < getSubCommands().size(); i++) {
                if (args[0].equalsIgnoreCase(getSubCommands().get(i).getName())) {
                    return getSubCommands().get(i).getSubcommandArguments((Player) sender, args);
                }
            }
        }
        return null;
    }
}
