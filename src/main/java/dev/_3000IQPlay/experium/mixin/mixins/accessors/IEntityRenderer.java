// 
// Decompiled by Procyon v0.5.36
// 

package dev._3000IQPlay.experium.mixin.mixins.accessors;

import org.spongepowered.asm.mixin.gen.Invoker;
import net.minecraft.client.renderer.EntityRenderer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ EntityRenderer.class })
public interface IEntityRenderer
{
    @Invoker("setupCameraTransform")
    void setupCameraTransformInvoker(final float p0, final int p1);
}
