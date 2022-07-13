// 
// Decompiled by Procyon v0.5.36
// 

package dev._3000IQPlay.experium.manager;

import java.util.Iterator;
import java.util.LinkedList;
import dev._3000IQPlay.experium.features.command.commands.PornCommand;
import dev._3000IQPlay.experium.features.command.commands.HistoryCommand;
import dev._3000IQPlay.experium.features.command.commands.CrashCommand;
import dev._3000IQPlay.experium.features.command.commands.BookCommand;
import dev._3000IQPlay.experium.features.command.commands.ToggleCommand;
import dev._3000IQPlay.experium.features.command.commands.XrayCommand;
import dev._3000IQPlay.experium.features.command.commands.PeekCommand;
import dev._3000IQPlay.experium.features.command.commands.ReloadSoundCommand;
import dev._3000IQPlay.experium.features.command.commands.UnloadCommand;
import dev._3000IQPlay.experium.features.command.commands.ReloadCommand;
import dev._3000IQPlay.experium.features.command.commands.HelpCommand;
import dev._3000IQPlay.experium.features.command.commands.FriendCommand;
import dev._3000IQPlay.experium.features.command.commands.CoordsCommand;
import dev._3000IQPlay.experium.features.command.commands.ClearRamCommand;
import dev._3000IQPlay.experium.features.command.commands.ConfigCommand;
import dev._3000IQPlay.experium.features.command.commands.QueueCommand;
import dev._3000IQPlay.experium.features.command.commands.ChatClearCommand;
import dev._3000IQPlay.experium.features.command.commands.OpenFolderCommand;
import dev._3000IQPlay.experium.features.command.commands.BrowseCommand;
import dev._3000IQPlay.experium.features.command.commands.FakePlayerCommand;
import dev._3000IQPlay.experium.features.command.commands.PrefixCommand;
import dev._3000IQPlay.experium.features.command.commands.NameMCCommand;
import dev._3000IQPlay.experium.features.command.commands.ModuleCommand;
import dev._3000IQPlay.experium.features.command.commands.BindCommand;
import dev._3000IQPlay.experium.features.command.Command;
import java.util.ArrayList;
import dev._3000IQPlay.experium.features.Feature;

public class CommandManager extends Feature
{
    private final ArrayList<Command> commands;
    private String clientMessage;
    private String prefix;
    
    public CommandManager() {
        super("Command");
        this.commands = new ArrayList<Command>();
        this.clientMessage = "[Experium]";
        this.prefix = ".";
        this.commands.add(new BindCommand());
        this.commands.add(new ModuleCommand());
        this.commands.add(new NameMCCommand());
        this.commands.add(new PrefixCommand());
        this.commands.add(new FakePlayerCommand());
        this.commands.add(new BrowseCommand());
        this.commands.add(new OpenFolderCommand());
        this.commands.add(new ChatClearCommand());
        this.commands.add(new QueueCommand());
        this.commands.add(new ConfigCommand());
        this.commands.add(new ClearRamCommand());
        this.commands.add(new CoordsCommand());
        this.commands.add(new FriendCommand());
        this.commands.add(new HelpCommand());
        this.commands.add(new ReloadCommand());
        this.commands.add(new UnloadCommand());
        this.commands.add(new ReloadSoundCommand());
        this.commands.add(new PeekCommand());
        this.commands.add(new XrayCommand());
        this.commands.add(new ToggleCommand());
        this.commands.add(new BookCommand());
        this.commands.add(new CrashCommand());
        this.commands.add(new HistoryCommand());
        this.commands.add(new PornCommand());
    }
    
    public static String[] removeElement(final String[] input, final int indexToDelete) {
        final LinkedList<String> result = new LinkedList<String>();
        for (int i = 0; i < input.length; ++i) {
            if (i != indexToDelete) {
                result.add(input[i]);
            }
        }
        return result.toArray(input);
    }
    
    private static String strip(final String str, final String key) {
        if (str.startsWith(key) && str.endsWith(key)) {
            return str.substring(key.length(), str.length() - key.length());
        }
        return str;
    }
    
    public void executeCommand(final String command) {
        final String[] parts = command.split(" (?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
        final String name = parts[0].substring(1);
        final String[] args = removeElement(parts, 0);
        for (int i = 0; i < args.length; ++i) {
            if (args[i] != null) {
                args[i] = strip(args[i], "\"");
            }
        }
        for (final Command c : this.commands) {
            if (!c.getName().equalsIgnoreCase(name)) {
                continue;
            }
            c.execute(parts);
            return;
        }
        Command.sendMessage("Unknown command. try 'commands' for a list of commands.");
    }
    
    public Command getCommandByName(final String name) {
        for (final Command command : this.commands) {
            if (!command.getName().equals(name)) {
                continue;
            }
            return command;
        }
        return null;
    }
    
    public ArrayList<Command> getCommands() {
        return this.commands;
    }
    
    public String getClientMessage() {
        return this.clientMessage;
    }
    
    public void setClientMessage(final String clientMessage) {
        this.clientMessage = clientMessage;
    }
    
    public String getPrefix() {
        return this.prefix;
    }
    
    public void setPrefix(final String prefix) {
        this.prefix = prefix;
    }
}
