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

public class BrowseCommand extends Command
{
    Desktop desktop;
    
    public BrowseCommand() {
        super("browse", new String[] { "text" });
        this.desktop = Desktop.getDesktop();
    }
    
    @Override
    public void execute(final String[] commands) {
        try {
            this.desktop.browse(new URI("https://www.google.com/search?q=" + URLEncoder.encode(commands[0]) + "&sxsrf=APq-WBs6H06yXya-qlggIMTKxjRwW7jvOA%3A1650656158846&ei=ngNjYo2nM4b_rgStiaO4BA&oq=sxsrf&gs_lcp=Cgdnd3Mtd2l6EAMYATIFCAAQgAQyBQgAEIAEMgUIABCABDIFCAAQgAQyBggAEAoQHjIGCAAQBRAeSgQIQRgASgQIRhgAUABYAGCmDWgAcAF4AIABVogBVpIBATGYAQCgAQKgAQHAAQE&sclient=gws-wiz"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (URISyntaxException e2) {
            e2.printStackTrace();
        }
    }
}
