//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Luni\Documents\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev._3000IQPlay.experium.features.modules.misc;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.network.play.server.SPacketBlockAction;
import net.minecraft.network.play.server.SPacketChunkData;
import net.minecraft.network.play.server.SPacketMultiBlockChange;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.network.play.server.SPacketBlockChange;
import dev._3000IQPlay.experium.event.events.PacketEvent;
import dev._3000IQPlay.experium.features.modules.Module;

public class AntiChunkBan extends Module
{
    public AntiChunkBan() {
        super("AntiChunkBan", "Prevents you from getting chunk banned (can desync you)", Category.MISC, true, false, false);
    }
    
    @SubscribeEvent
    public void onPacketReceive(final PacketEvent.Receive event) {
        final Object p = event.getPacket();
        if (p instanceof SPacketBlockChange) {
            event.setCanceled(true);
        }
        else if (p instanceof SPacketUpdateTileEntity) {
            event.setCanceled(true);
        }
        else if (p instanceof SPacketMultiBlockChange) {
            event.setCanceled(true);
        }
        else if (p instanceof SPacketChunkData) {
            final SPacketChunkData data = (SPacketChunkData)p;
            final Chunk chunk = AntiChunkBan.mc.world.getChunk(data.getChunkX(), data.getChunkZ());
            if (chunk.isLoaded()) {
                event.setCanceled(true);
            }
        }
        else if (p instanceof SPacketBlockAction) {
            event.setCanceled(true);
        }
    }
}
