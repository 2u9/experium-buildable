//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Luni\Documents\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev._3000IQPlay.experium.mixin.mixins;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import dev._3000IQPlay.experium.features.modules.movement.NoSlowDown;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.block.state.IBlockState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.block.BlockEndPortalFrame;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ BlockEndPortalFrame.class })
public class MixinBlockEndPortalFrame
{
    @Shadow
    @Final
    protected static AxisAlignedBB AABB_BLOCK;
    
    @Inject(method = { "getBoundingBox" }, at = { @At("HEAD") }, cancellable = true)
    public void getBoundingBox(final IBlockState state, final IBlockAccess source, final BlockPos pos, final CallbackInfoReturnable<AxisAlignedBB> info) {
        if (NoSlowDown.getInstance().isOn() && NoSlowDown.getInstance().endPortal.getValue()) {
            info.setReturnValue(new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 1.0, 1.0));
        }
    }
}
