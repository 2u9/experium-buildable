// 
// Decompiled by Procyon v0.5.36
// 

package dev._3000IQPlay.experium.features.command.commands;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import dev._3000IQPlay.experium.util.PlayerUtil;
import dev._3000IQPlay.experium.features.command.Command;

public class NameMCCommand extends Command
{
    public NameMCCommand() {
        super("namemc", new String[] { "<player>" });
    }
    
    @Override
    public void execute(final String[] commands) {
        if (commands.length == 1 || commands.length == 0) {
            Command.sendMessage("Please specify a player.");
        }
        UUID uuid;
        try {
            uuid = PlayerUtil.getUUIDFromName(commands[0]);
        }
        catch (Exception e) {
            Command.sendMessage("An error occured.");
            return;
        }
        List<String> names;
        try {
            names = PlayerUtil.getHistoryOfNames(uuid);
        }
        catch (Exception e) {
            Command.sendMessage("An error occured.");
            return;
        }
        if (names != null) {
            Command.sendMessage(commands[0] + "s name history:");
            for (final String name : names) {
                Command.sendMessage(name);
            }
        }
        else {
            Command.sendMessage("No names found.");
        }
    }
}
