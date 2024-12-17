package me.loving11ish.redlightgreenlight.commands;

public abstract class ConsoleCommand {

    public abstract String getName();

    public abstract String getDescription();

    public abstract String getSyntax();

    public abstract void perform(String[] args);

}
