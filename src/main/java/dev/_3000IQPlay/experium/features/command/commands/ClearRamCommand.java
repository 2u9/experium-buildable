// 
// Decompiled by Procyon v0.5.36
// 

package dev._3000IQPlay.experium.features.command.commands;

import dev._3000IQPlay.experium.features.command.Command;

public class ClearRamCommand extends Command
{
    public ClearRamCommand() {
        super("clearram");
    }
    
    @Override
    public void execute(final String[] commands) {
        System.gc();
        Command.sendMessage("Finished clearing the ram.", false);
    }
}
