//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Luni\Documents\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev._3000IQPlay.experium.features.modules.misc;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemWrittenBook;
import net.minecraft.network.play.client.CPacketClickWindow;
import dev._3000IQPlay.experium.event.events.PacketEvent;
import dev._3000IQPlay.experium.features.modules.Module;

public class AntiBookBan extends Module
{
    public AntiBookBan() {
        super("AntiBookBan", "Prevents you from getting book banned (can desync you)", Category.MISC, true, false, false);
    }
    
    @SubscribeEvent
    public void onLeavingDeathEvent(final PacketEvent.Receive event) {
        if (!(event.getPacket() instanceof CPacketClickWindow)) {
            return;
        }
        final CPacketClickWindow packet = event.getPacket();
        if (!(packet.getClickedItem().getItem() instanceof ItemWrittenBook)) {
            return;
        }
        event.isCanceled();
        AntiBookBan.mc.player.openContainer.slotClick(packet.getSlotId(), packet.getUsedButton(), packet.getClickType(), (EntityPlayer)AntiBookBan.mc.player);
    }
}
