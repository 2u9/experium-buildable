//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Luni\Documents\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 */
package dev._3000IQPlay.experium.features.command.commands;

import dev._3000IQPlay.experium.features.command.Command;

public class ChatClearCommand
extends Command {
    public ChatClearCommand() {
        super("chatclear", new String[0]);
    }

    @Override
    public void execute(String[] commands) {
        ChatClearCommand.mc.ingameGUI.getChatGUI().clearChatMessages(true);
    }
}

