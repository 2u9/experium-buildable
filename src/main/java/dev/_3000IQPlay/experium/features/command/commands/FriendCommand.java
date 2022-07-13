//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Luni\Documents\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.mojang.realmsclient.gui.ChatFormatting
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketChatMessage
 */
package dev._3000IQPlay.experium.features.command.commands;

import com.mojang.realmsclient.gui.ChatFormatting;
import dev._3000IQPlay.experium.Experium;
import dev._3000IQPlay.experium.features.command.Command;
import dev._3000IQPlay.experium.features.modules.client.FriendSettings;
import dev._3000IQPlay.experium.util.Util;
import java.util.Map;
import java.util.UUID;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketChatMessage;

public class FriendCommand
extends Command {
    public FriendCommand() {
        super("friend", new String[]{"<add/del/name/clear>", "<name>"});
    }

    @Override
    public void execute(String[] commands) {
        if (commands.length == 1) {
            if (Experium.friendManager.getFriends().isEmpty()) {
                FriendCommand.sendMessage("You currently dont have any friends added.");
            } else {
                FriendCommand.sendMessage("Friends: ");
                for (Map.Entry<String, UUID> entry : Experium.friendManager.getFriends().entrySet()) {
                    FriendCommand.sendMessage(entry.getKey());
                }
            }
            return;
        }
        if (commands.length == 2) {
            switch (commands[0]) {
                case "reset": {
                    Experium.friendManager.onLoad();
                    FriendCommand.sendMessage("Friends got reset.");
                    return;
                }
            }
            FriendCommand.sendMessage(commands[0] + (Experium.friendManager.isFriend(commands[0]) ? " is friended." : " isn't friended."));
            return;
        }
        if (commands.length >= 2) {
            switch (commands[0]) {
                case "add": {
                    Experium.friendManager.addFriend(commands[1]);
                    FriendCommand.sendMessage((Object)ChatFormatting.GREEN + commands[1] + " has been friended");
                    if (FriendSettings.getInstance().notify.getValue().booleanValue()) {
                        Util.mc.player.connection.sendPacket((Packet)new CPacketChatMessage("/w " + commands[1] + " I just added you to my friends list on Charlie dana hack!"));
                    }
                    return;
                }
                case "del": {
                    Experium.friendManager.removeFriend(commands[1]);
                    if (FriendSettings.getInstance().notify.getValue().booleanValue()) {
                        Util.mc.player.connection.sendPacket((Packet)new CPacketChatMessage("/w " + commands[1] + " I just removed you from my friends list on Charlie dana hack!"));
                    }
                    FriendCommand.sendMessage((Object)ChatFormatting.RED + commands[1] + " has been unfriended");
                    return;
                }
            }
            FriendCommand.sendMessage("Unknown Command, try friend add/del (name)");
        }
    }
}

