/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParser
 */
package dev._3000IQPlay.experium.features.command.commands;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev._3000IQPlay.experium.features.command.Command;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;

public class QueueCommand
extends Command {
    public QueueCommand() {
        super("queue", new String[]{"priority, regular"});
    }

    @Override
    public void execute(String[] commands) {
        if (commands.length == 1 || commands.length == 0) {
            QueueCommand.sendMessage("ayo, specify the type! (priority/regular)");
            return;
        }
        String sURL = "https://2bqueue.info/*";
        String adjsaofj = commands[0];
        if (adjsaofj.equalsIgnoreCase("regular")) {
            try {
                URL url = new URL(sURL);
                URLConnection request = url.openConnection();
                request.connect();
                JsonParser jp = new JsonParser();
                JsonElement root = jp.parse((Reader)new InputStreamReader((InputStream)request.getContent()));
                JsonObject rootobj = root.getAsJsonObject();
                String aaaaaa = rootobj.get("regular").getAsString();
                QueueCommand.sendMessage("Regular queue currently have: " + aaaaaa);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        } else if (adjsaofj.equalsIgnoreCase("priority")) {
            try {
                URL url = new URL(sURL);
                URLConnection request = url.openConnection();
                request.connect();
                JsonParser jp = new JsonParser();
                JsonElement root = jp.parse((Reader)new InputStreamReader((InputStream)request.getContent()));
                JsonObject rootobj = root.getAsJsonObject();
                String aaaaaa = rootobj.get("prio").getAsString();
                QueueCommand.sendMessage("Priority queue currently have: " + aaaaaa);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

