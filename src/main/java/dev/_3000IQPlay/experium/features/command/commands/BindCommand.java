// 
// Decompiled by Procyon v0.5.36
// 

package dev._3000IQPlay.experium.features.command.commands;

import dev._3000IQPlay.experium.features.modules.Module;
import dev._3000IQPlay.experium.features.setting.Bind;
import org.lwjgl.input.Keyboard;
import dev._3000IQPlay.experium.Experium;
import dev._3000IQPlay.experium.features.command.Command;

public class BindCommand extends Command
{
    public BindCommand() {
        super("bind", new String[] { "<module>", "<bind>" });
    }
    
    @Override
    public void execute(final String[] commands) {
        if (commands.length == 1) {
            Command.sendMessage("Please specify a module.");
            return;
        }
        final String rkey = commands[1];
        final String moduleName = commands[0];
        final Module module = Experium.moduleManager.getModuleByName(moduleName);
        if (module == null) {
            Command.sendMessage("Unknown module '" + module + "'!");
            return;
        }
        if (rkey == null) {
            Command.sendMessage(module.getName() + " is bound to &b" + module.getBind().toString());
            return;
        }
        int key = Keyboard.getKeyIndex(rkey.toUpperCase());
        if (rkey.equalsIgnoreCase("none")) {
            key = -1;
        }
        if (key == 0) {
            Command.sendMessage("Unknown key '" + rkey + "'!");
            return;
        }
        module.bind.setValue(new Bind(key));
        Command.sendMessage("Bind for &b" + module.getName() + "&r set to &b" + rkey.toUpperCase());
    }
}
