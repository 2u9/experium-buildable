// 
// Decompiled by Procyon v0.5.36
// 

package dev._3000IQPlay.experium.mixin.mixins.accessors;

import org.spongepowered.asm.mixin.gen.Accessor;
import net.minecraft.network.play.client.CPacketPlayer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ CPacketPlayer.class })
public interface ICPacketPlayer
{
    @Accessor("yaw")
    float getYaw();
    
    @Accessor("yaw")
    void setYaw(final float p0);
    
    @Accessor("pitch")
    float getPitch();
    
    @Accessor("pitch")
    void setPitch(final float p0);
    
    @Accessor("y")
    void setY(final double p0);
    
    @Accessor("y")
    double getY();
    
    @Accessor("onGround")
    void setOnGround(final boolean p0);
    
    @Accessor("rotating")
    boolean isRotating();
    
    @Accessor("moving")
    boolean isMoving();
}
