//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Luni\Documents\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev._3000IQPlay.experium.features.modules.player;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.RayTraceResult;
import java.util.function.Consumer;
import net.minecraft.entity.EntityLivingBase;
import dev._3000IQPlay.experium.features.modules.Module;

public class NoEntityTrace extends Module
{
    private boolean focus;
    
    public NoEntityTrace() {
        super("NoEntityTrace", "Mines trought entities", Category.PLAYER, false, false, false);
        this.focus = false;
    }
    
    @Override
    public void onUpdate() {
        if (nullCheck()) {
            return;
        }
        NoEntityTrace.mc.world.loadedEntityList.stream().filter(entity -> entity instanceof EntityLivingBase).filter(entity -> NoEntityTrace.mc.player == entity).map(entity -> entity).filter(entity -> !entity.isDead).forEach(this::process);
        final RayTraceResult normalResult = NoEntityTrace.mc.objectMouseOver;
        if (normalResult != null) {
            this.focus = (normalResult.typeOfHit == RayTraceResult.Type.ENTITY);
        }
    }
    
    private void process(final EntityLivingBase event) {
        final RayTraceResult bypassEntityResult = event.rayTrace(6.0, NoEntityTrace.mc.getRenderPartialTicks());
        if (bypassEntityResult != null && this.focus && bypassEntityResult.typeOfHit == RayTraceResult.Type.BLOCK) {
            final BlockPos pos = bypassEntityResult.getBlockPos();
            if (NoEntityTrace.mc.gameSettings.keyBindAttack.isKeyDown()) {
                NoEntityTrace.mc.playerController.onPlayerDamageBlock(pos, EnumFacing.UP);
            }
        }
    }
}
