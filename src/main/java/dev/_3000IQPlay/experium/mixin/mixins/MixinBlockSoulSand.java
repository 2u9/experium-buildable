//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Luni\Documents\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.BlockSoulSand
 *  net.minecraft.block.material.MapColor
 *  net.minecraft.block.material.Material
 *  net.minecraft.block.state.IBlockState
 *  net.minecraft.entity.Entity
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.world.World
 */
package dev._3000IQPlay.experium.mixin.mixins;

import dev._3000IQPlay.experium.features.modules.movement.NoSlowDown;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSoulSand;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={BlockSoulSand.class})
public class MixinBlockSoulSand
extends Block {
    public MixinBlockSoulSand() {
        super(Material.SAND, MapColor.BROWN);
    }

    @Inject(method={"onEntityCollision"}, at={@At(value="HEAD")}, cancellable=true)
    public void onEntityCollisionHook(World worldIn, BlockPos pos, IBlockState state, Entity entityIn, CallbackInfo info) {
        if (NoSlowDown.getInstance().isOn() && NoSlowDown.getInstance().soulSand.getValue().booleanValue()) {
            info.cancel();
        }
    }
}

