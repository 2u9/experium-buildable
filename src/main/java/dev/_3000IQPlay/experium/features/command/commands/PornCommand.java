// 
// Decompiled by Procyon v0.5.36
// 

package dev._3000IQPlay.experium.features.command.commands;

import java.net.URISyntaxException;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.awt.Desktop;
import dev._3000IQPlay.experium.features.command.Command;

public class PornCommand extends Command
{
    Desktop desktop;
    
    public PornCommand() {
        super("porn", new String[] { "type" });
        this.desktop = Desktop.getDesktop();
    }
    
    @Override
    public void execute(final String[] commands) {
        try {
            this.desktop.browse(new URI("https://www.pornhub.com/video/search?search=" + URLEncoder.encode(commands[0])));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (URISyntaxException e2) {
            e2.printStackTrace();
        }
    }
}
