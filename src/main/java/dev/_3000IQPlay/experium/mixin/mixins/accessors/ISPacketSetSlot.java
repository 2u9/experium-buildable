// 
// Decompiled by Procyon v0.5.36
// 

package dev._3000IQPlay.experium.mixin.mixins.accessors;

import org.spongepowered.asm.mixin.gen.Accessor;
import net.minecraft.network.play.server.SPacketSetSlot;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ SPacketSetSlot.class })
public interface ISPacketSetSlot
{
    @Accessor("windowId")
    int getId();
    
    @Accessor("windowId")
    void setWindowId(final int p0);
}
