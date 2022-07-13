// 
// Decompiled by Procyon v0.5.36
// 

package dev._3000IQPlay.experium.mixin.mixins.accessors;

import org.spongepowered.asm.mixin.gen.Accessor;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.CPacketCustomPayload;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ CPacketCustomPayload.class })
public interface ICPacketCustomPayload
{
    @Accessor("data")
    void setData(final PacketBuffer p0);
}
