//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Luni\Documents\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.Unpooled
 *  net.minecraft.network.PacketBuffer
 *  net.minecraft.network.play.client.CPacketCustomPayload
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package dev._3000IQPlay.experium.features.modules.client;

import dev._3000IQPlay.experium.event.events.PacketEvent;
import dev._3000IQPlay.experium.features.modules.Module;
import dev._3000IQPlay.experium.mixin.mixins.accessors.ICPacketCustomPayload;
import io.netty.buffer.Unpooled;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.CPacketCustomPayload;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class FakeVanilla
extends Module {
    public FakeVanilla() {
        super("FakeVanilla", "Prevents client from sending forge signature", Module.Category.CLIENT, true, false, false);
    }

    @SubscribeEvent
    public void onPacketSend(PacketEvent.Send event) {
        if (!mc.isIntegratedServerRunning()) {
            if (event.getPacket().getClass().getName().equals("net.minecraftforge.fml.common.network.internal.FMLProxyPacket")) {
                event.setCanceled(true);
            } else if (event.getPacket() instanceof CPacketCustomPayload && ((CPacketCustomPayload)event.getPacket()).getChannelName().equalsIgnoreCase("MC|Brand")) {
                ((ICPacketCustomPayload)event.getPacket()).setData(new PacketBuffer(Unpooled.buffer()).writeString("vanilla"));
            }
        }
    }
}

