//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Luni\Documents\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.util.EnumFacing
 *  net.minecraft.util.math.BlockPos
 *  net.minecraft.util.math.RayTraceResult
 *  net.minecraft.util.math.RayTraceResult$Type
 */
package dev._3000IQPlay.experium.features.modules.player;

import dev._3000IQPlay.experium.features.modules.Module;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;

public class NoEntityTrace
extends Module {
    private boolean focus = false;

    public NoEntityTrace() {
        super("NoEntityTrace", "Mines trought entities", Module.Category.PLAYER, false, false, false);
    }

    @Override
    public void onUpdate() {
        if (NoEntityTrace.nullCheck()) {
            return;
        }
        NoEntityTrace.mc.world.loadedEntityList.stream().filter(entity -> entity instanceof EntityLivingBase).filter(entity -> NoEntityTrace.mc.player == entity).map(entity -> (EntityLivingBase)entity).filter(entity -> !entity.isDead).forEach(this::process);
        RayTraceResult normalResult = NoEntityTrace.mc.objectMouseOver;
        if (normalResult != null) {
            this.focus = normalResult.typeOfHit == RayTraceResult.Type.ENTITY;
        }
    }

    private void process(EntityLivingBase event) {
        RayTraceResult bypassEntityResult = event.rayTrace(6.0, mc.getRenderPartialTicks());
        if (bypassEntityResult != null && this.focus && bypassEntityResult.typeOfHit == RayTraceResult.Type.BLOCK) {
            BlockPos pos = bypassEntityResult.getBlockPos();
            if (NoEntityTrace.mc.gameSettings.keyBindAttack.isKeyDown()) {
                NoEntityTrace.mc.playerController.onPlayerDamageBlock(pos, EnumFacing.UP);
            }
        }
    }
}

