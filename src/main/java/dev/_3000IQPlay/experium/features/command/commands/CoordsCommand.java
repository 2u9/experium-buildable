//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Luni\Documents\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev._3000IQPlay.experium.features.command.commands;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.Transferable;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.text.DecimalFormat;
import dev._3000IQPlay.experium.features.command.Command;

public class CoordsCommand extends Command
{
    public CoordsCommand() {
        super("coords", new String[0]);
    }
    
    @Override
    public void execute(final String[] commands) {
        final DecimalFormat format = new DecimalFormat("#");
        final StringSelection contents = new StringSelection(format.format(CoordsCommand.mc.player.posX) + ", " + format.format(CoordsCommand.mc.player.posY) + ", " + format.format(CoordsCommand.mc.player.posZ));
        final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(contents, null);
        Command.sendMessage("Saved Coordinates To Clipboard.", false);
    }
}
