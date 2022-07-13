//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Luni\Documents\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev._3000IQPlay.experium.features.command.commands;

import java.util.Iterator;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketChatMessage;
import dev._3000IQPlay.experium.util.Util;
import dev._3000IQPlay.experium.features.modules.client.FriendSettings;
import com.mojang.realmsclient.gui.ChatFormatting;
import java.util.UUID;
import java.util.Map;
import dev._3000IQPlay.experium.Experium;
import dev._3000IQPlay.experium.features.command.Command;

public class FriendCommand extends Command
{
    public FriendCommand() {
        super("friend", new String[] { "<add/del/name/clear>", "<name>" });
    }
    
    @Override
    public void execute(final String[] commands) {
        if (commands.length == 1) {
            if (Experium.friendManager.getFriends().isEmpty()) {
                Command.sendMessage("You currently dont have any friends added.");
            }
            else {
                Command.sendMessage("Friends: ");
                for (final Map.Entry<String, UUID> entry : Experium.friendManager.getFriends().entrySet()) {
                    Command.sendMessage(entry.getKey());
                }
            }
            return;
        }
        if (commands.length != 2) {
            if (commands.length >= 2) {
                final String s = commands[0];
                switch (s) {
                    case "add": {
                        Experium.friendManager.addFriend(commands[1]);
                        Command.sendMessage(ChatFormatting.GREEN + commands[1] + " has been friended");
                        if (FriendSettings.getInstance().notify.getValue()) {
                            Util.mc.player.connection.sendPacket((Packet)new CPacketChatMessage("/w " + commands[1] + " I just added you to my friends list on Charlie dana hack!"));
                        }
                    }
                    case "del": {
                        Experium.friendManager.removeFriend(commands[1]);
                        if (FriendSettings.getInstance().notify.getValue()) {
                            Util.mc.player.connection.sendPacket((Packet)new CPacketChatMessage("/w " + commands[1] + " I just removed you from my friends list on Charlie dana hack!"));
                        }
                        Command.sendMessage(ChatFormatting.RED + commands[1] + " has been unfriended");
                    }
                    default: {
                        Command.sendMessage("Unknown Command, try friend add/del (name)");
                        break;
                    }
                }
            }
            return;
        }
        final String s2 = commands[0];
        switch (s2) {
            case "reset": {
                Experium.friendManager.onLoad();
                Command.sendMessage("Friends got reset.");
            }
            default: {
                Command.sendMessage(commands[0] + (Experium.friendManager.isFriend(commands[0]) ? " is friended." : " isn't friended."));
            }
        }
    }
}
