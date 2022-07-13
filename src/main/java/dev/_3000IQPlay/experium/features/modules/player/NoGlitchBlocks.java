//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Luni\Documents\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev._3000IQPlay.experium.features.modules.player;

import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.EnumFacing;
import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.world.BlockEvent;
import dev._3000IQPlay.experium.features.modules.Module;

public class NoGlitchBlocks extends Module
{
    private static NoGlitchBlocks INSTANCE;
    
    public NoGlitchBlocks() {
        super("NoGlitchBlocks", "deletes blocks", Category.PLAYER, true, false, false);
        this.setInstance();
    }
    
    public static NoGlitchBlocks getINSTANCE() {
        if (NoGlitchBlocks.INSTANCE == null) {
            NoGlitchBlocks.INSTANCE = new NoGlitchBlocks();
        }
        return NoGlitchBlocks.INSTANCE;
    }
    
    private void setInstance() {
        NoGlitchBlocks.INSTANCE = this;
    }
    
    @SubscribeEvent
    public void onBreak(final BlockEvent.BreakEvent event) {
        if (fullNullCheck()) {
            return;
        }
        if (!(NoGlitchBlocks.mc.player.getHeldItemMainhand().getItem() instanceof ItemBlock)) {
            final BlockPos pos = NoGlitchBlocks.mc.player.getPosition();
            this.removeGlitchBlocks(pos);
        }
    }
    
    private void removeGlitchBlocks(final BlockPos pos) {
        for (int dx = -4; dx <= 4; ++dx) {
            for (int dy = -4; dy <= 4; ++dy) {
                for (int dz = -4; dz <= 4; ++dz) {
                    final BlockPos blockPos = new BlockPos(pos.getX() + dx, pos.getY() + dy, pos.getZ() + dz);
                    if (NoGlitchBlocks.mc.world.getBlockState(blockPos).getBlock().equals(Blocks.AIR)) {
                        NoGlitchBlocks.mc.playerController.processRightClickBlock(NoGlitchBlocks.mc.player, NoGlitchBlocks.mc.world, blockPos, EnumFacing.DOWN, new Vec3d(0.5, 0.5, 0.5), EnumHand.MAIN_HAND);
                    }
                }
            }
        }
    }
    
    static {
        NoGlitchBlocks.INSTANCE = new NoGlitchBlocks();
    }
}
