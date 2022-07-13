// 
// Decompiled by Procyon v0.5.36
// 

package dev._3000IQPlay.experium.features.command.commands;

import dev._3000IQPlay.experium.features.modules.player.FakePlayer;
import dev._3000IQPlay.experium.features.command.Command;

public class FakePlayerCommand extends Command
{
    public FakePlayerCommand() {
        super("fakeplayer");
    }
    
    @Override
    public void execute(final String[] commands) {
        FakePlayer.getInstance().enable();
    }
}
