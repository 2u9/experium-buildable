//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Luni\Documents\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev._3000IQPlay.experium.features.modules.client;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.network.PacketBuffer;
import io.netty.buffer.Unpooled;
import dev._3000IQPlay.experium.mixin.mixins.accessors.ICPacketCustomPayload;
import net.minecraft.network.play.client.CPacketCustomPayload;
import dev._3000IQPlay.experium.event.events.PacketEvent;
import dev._3000IQPlay.experium.features.modules.Module;

public class FakeVanilla extends Module
{
    public FakeVanilla() {
        super("FakeVanilla", "Prevents client from sending forge signature", Category.CLIENT, true, false, false);
    }
    
    @SubscribeEvent
    public void onPacketSend(final PacketEvent.Send event) {
        if (!FakeVanilla.mc.isIntegratedServerRunning()) {
            if (event.getPacket().getClass().getName().equals("net.minecraftforge.fml.common.network.internal.FMLProxyPacket")) {
                event.setCanceled(true);
            }
            else if (event.getPacket() instanceof CPacketCustomPayload && event.getPacket().getChannelName().equalsIgnoreCase("MC|Brand")) {
                event.getPacket().setData(new PacketBuffer(Unpooled.buffer()).writeString("vanilla"));
            }
        }
    }
}
