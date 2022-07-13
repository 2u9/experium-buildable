//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Luni\Documents\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev._3000IQPlay.experium.features.modules.movement;

import dev._3000IQPlay.experium.features.modules.player.Freecam;
import dev._3000IQPlay.experium.util.EntityUtil;
import dev._3000IQPlay.experium.util.PlayerUtil;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import dev._3000IQPlay.experium.Experium;
import dev._3000IQPlay.experium.features.Feature;
import java.util.Arrays;
import net.minecraft.init.Blocks;
import net.minecraft.block.Block;
import java.util.List;
import dev._3000IQPlay.experium.features.setting.Setting;
import dev._3000IQPlay.experium.features.modules.Module;

public class FastFall extends Module
{
    public Setting<Double> speed;
    public Setting<Double> height;
    public Setting<Boolean> noLag;
    List<Block> incelBlocks;
    
    public FastFall() {
        super("FastFall", "Fast fall", Category.MOVEMENT, true, false, false);
        this.speed = (Setting<Double>)this.register(new Setting("Speed", (T)3.0, (T)0.1, (T)10.0));
        this.height = (Setting<Double>)this.register(new Setting("Height", (T)10.0, (T)0.1, (T)90.0));
        this.noLag = (Setting<Boolean>)this.register(new Setting("NoLag", (T)true));
        this.incelBlocks = Arrays.asList(Blocks.BED, Blocks.SLIME_BLOCK);
    }
    
    @Override
    public void onUpdate() {
        if (Feature.fullNullCheck() || this.shouldReturn()) {
            return;
        }
        if (this.noLag.getValue() && Experium.packetManager.caughtPlayerPosLook()) {
            return;
        }
        final RayTraceResult trace = FastFall.mc.world.rayTraceBlocks(FastFall.mc.player.getPositionVector(), new Vec3d(FastFall.mc.player.posX, FastFall.mc.player.posY - this.height.getValue(), FastFall.mc.player.posZ), false, false, false);
        if (trace != null && trace.typeOfHit == RayTraceResult.Type.BLOCK && FastFall.mc.world.getBlockState(new BlockPos(FastFall.mc.player.posX, FastFall.mc.player.posY - 0.1, FastFall.mc.player.posZ)).getBlock() != this.incelBlocks) {
            FastFall.mc.player.motionY = -this.speed.getValue();
        }
    }
    
    boolean shouldReturn() {
        return FastFall.mc.player.isElytraFlying() || PlayerUtil.isClipping() || EntityUtil.isInLiquid() || FastFall.mc.player.isOnLadder() || FastFall.mc.player.capabilities.isFlying || FastFall.mc.player.motionY > 0.0 || FastFall.mc.gameSettings.keyBindJump.isKeyDown() || FastFall.mc.player.isEntityInsideOpaqueBlock() || FastFall.mc.player.noClip || !FastFall.mc.player.onGround || Experium.moduleManager.isModuleEnabled("PacketFly") || Freecam.getInstance().isEnabled() || Experium.moduleManager.isModuleEnabled("PhaseWalk") || Experium.moduleManager.isModuleEnabled("LongJump") || Experium.moduleManager.isModuleEnabled("Strafe") || Experium.moduleManager.isModuleEnabled("SpeedNew");
    }
}
