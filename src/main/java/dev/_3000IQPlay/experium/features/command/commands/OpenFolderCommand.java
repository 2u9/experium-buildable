// 
// Decompiled by Procyon v0.5.36
// 

package dev._3000IQPlay.experium.features.command.commands;

import java.io.IOException;
import java.io.File;
import java.awt.Desktop;
import dev._3000IQPlay.experium.features.command.Command;

public class OpenFolderCommand extends Command
{
    public OpenFolderCommand() {
        super("openfolder", new String[0]);
    }
    
    @Override
    public void execute(final String[] commands) {
        try {
            Desktop.getDesktop().open(new File("experium/"));
            Command.sendMessage("Opened config folder!", false);
        }
        catch (IOException e) {
            Command.sendMessage("Could not open config folder!", false);
            e.printStackTrace();
        }
    }
}
