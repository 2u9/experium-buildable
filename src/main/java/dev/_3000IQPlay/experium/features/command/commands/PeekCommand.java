//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Luni\Documents\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemShulkerBox
 *  net.minecraft.item.ItemStack
 */
package dev._3000IQPlay.experium.features.command.commands;

import dev._3000IQPlay.experium.features.command.Command;
import dev._3000IQPlay.experium.features.modules.misc.ToolTips;
import java.util.Map;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemShulkerBox;
import net.minecraft.item.ItemStack;

public class PeekCommand
extends Command {
    public PeekCommand() {
        super("peek", new String[]{"<player>"});
    }

    @Override
    public void execute(String[] commands) {
        if (commands.length == 1) {
            ItemStack stack = PeekCommand.mc.player.getHeldItemMainhand();
            if (stack != null && stack.getItem() instanceof ItemShulkerBox) {
                ToolTips.displayInv(stack, null);
            } else {
                Command.sendMessage("\u00a7cYou need to hold a Shulker in your mainhand.");
                return;
            }
        }
        if (commands.length > 1) {
            if (ToolTips.getInstance().isOn() && ToolTips.getInstance().shulkerSpy.getValue().booleanValue()) {
                for (Map.Entry<EntityPlayer, ItemStack> entry : ToolTips.getInstance().spiedPlayers.entrySet()) {
                    if (!entry.getKey().getName().equalsIgnoreCase(commands[0])) continue;
                    ItemStack stack = entry.getValue();
                    ToolTips.displayInv(stack, entry.getKey().getName());
                    break;
                }
            } else {
                Command.sendMessage("\u00a7cYou need to turn on Tooltips - ShulkerSpy");
            }
        }
    }
}

