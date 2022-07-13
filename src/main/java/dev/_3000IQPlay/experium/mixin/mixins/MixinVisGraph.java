// 
// Decompiled by Procyon v0.5.36
// 

package dev._3000IQPlay.experium.mixin.mixins;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import dev._3000IQPlay.experium.features.modules.render.XRay;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.util.math.BlockPos;
import net.minecraft.client.renderer.chunk.VisGraph;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ VisGraph.class })
public class MixinVisGraph
{
    @Inject(method = { "setOpaqueCube" }, at = { @At("HEAD") }, cancellable = true)
    public void setOpaqueCubeHook(final BlockPos pos, final CallbackInfo info) {
        try {
            if (XRay.getInstance().isOn()) {
                info.cancel();
            }
        }
        catch (Exception ex) {}
    }
}
