//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Luni\Documents\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.item.ItemWrittenBook
 *  net.minecraft.network.play.client.CPacketClickWindow
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package dev._3000IQPlay.experium.features.modules.misc;

import dev._3000IQPlay.experium.event.events.PacketEvent;
import dev._3000IQPlay.experium.features.modules.Module;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemWrittenBook;
import net.minecraft.network.play.client.CPacketClickWindow;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AntiBookBan
extends Module {
    public AntiBookBan() {
        super("AntiBookBan", "Prevents you from getting book banned (can desync you)", Module.Category.MISC, true, false, false);
    }

    @SubscribeEvent
    public void onLeavingDeathEvent(PacketEvent.Receive event) {
        if (!(event.getPacket() instanceof CPacketClickWindow)) {
            return;
        }
        CPacketClickWindow packet = (CPacketClickWindow)event.getPacket();
        if (!(packet.getClickedItem().getItem() instanceof ItemWrittenBook)) {
            return;
        }
        event.isCanceled();
        AntiBookBan.mc.player.openContainer.slotClick(packet.getSlotId(), packet.getUsedButton(), packet.getClickType(), (EntityPlayer)AntiBookBan.mc.player);
    }
}

