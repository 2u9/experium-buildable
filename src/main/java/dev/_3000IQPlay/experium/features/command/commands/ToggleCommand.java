// 
// Decompiled by Procyon v0.5.36
// 

package dev._3000IQPlay.experium.features.command.commands;

import dev._3000IQPlay.experium.features.modules.Module;
import dev._3000IQPlay.experium.Experium;
import dev._3000IQPlay.experium.features.command.Command;

public class ToggleCommand extends Command
{
    public ToggleCommand() {
        super("toggle", new String[] { "<toggle>", "<module>" });
    }
    
    @Override
    public void execute(final String[] commands) {
        if (commands.length == 2) {
            final String name = commands[0].replaceAll("_", " ");
            final Module module = Experium.moduleManager.getModuleByName(name);
            if (module != null) {
                module.toggle();
            }
            else {
                Command.sendMessage("Unable to find a module with that name!");
            }
        }
        else {
            Command.sendMessage("Please provide a valid module name!");
        }
    }
}
