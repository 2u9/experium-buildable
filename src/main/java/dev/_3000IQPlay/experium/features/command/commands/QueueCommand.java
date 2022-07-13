// 
// Decompiled by Procyon v0.5.36
// 

package dev._3000IQPlay.experium.features.command.commands;

import com.google.gson.JsonObject;
import com.google.gson.JsonElement;
import java.net.URLConnection;
import java.io.IOException;
import java.io.Reader;
import java.io.InputStreamReader;
import java.io.InputStream;
import com.google.gson.JsonParser;
import java.net.URL;
import dev._3000IQPlay.experium.features.command.Command;

public class QueueCommand extends Command
{
    public QueueCommand() {
        super("queue", new String[] { "priority, regular" });
    }
    
    @Override
    public void execute(final String[] commands) {
        if (commands.length == 1 || commands.length == 0) {
            Command.sendMessage("ayo, specify the type! (priority/regular)");
            return;
        }
        final String sURL = "https://2bqueue.info/*";
        final String adjsaofj = commands[0];
        if (adjsaofj.equalsIgnoreCase("regular")) {
            try {
                final URL url = new URL(sURL);
                final URLConnection request = url.openConnection();
                request.connect();
                final JsonParser jp = new JsonParser();
                final JsonElement root = jp.parse((Reader)new InputStreamReader((InputStream)request.getContent()));
                final JsonObject rootobj = root.getAsJsonObject();
                final String aaaaaa = rootobj.get("regular").getAsString();
                Command.sendMessage("Regular queue currently have: " + aaaaaa);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (adjsaofj.equalsIgnoreCase("priority")) {
            try {
                final URL url = new URL(sURL);
                final URLConnection request = url.openConnection();
                request.connect();
                final JsonParser jp = new JsonParser();
                final JsonElement root = jp.parse((Reader)new InputStreamReader((InputStream)request.getContent()));
                final JsonObject rootobj = root.getAsJsonObject();
                final String aaaaaa = rootobj.get("prio").getAsString();
                Command.sendMessage("Priority queue currently have: " + aaaaaa);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
