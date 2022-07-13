//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Luni\Documents\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev._3000IQPlay.experium.features.modules.combat;

import java.util.Iterator;
import dev._3000IQPlay.experium.Experium;
import dev._3000IQPlay.experium.util.oyveyutils.OyVeyentityUtil;
import net.minecraft.entity.Entity;
import dev._3000IQPlay.experium.util.EntityUtil;
import net.minecraft.entity.player.EntityPlayer;
import dev._3000IQPlay.experium.util.BlockUtil;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import dev._3000IQPlay.experium.features.command.Command;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.block.Block;
import dev._3000IQPlay.experium.util.InventoryUtil;
import net.minecraft.init.Blocks;
import dev._3000IQPlay.experium.features.setting.Setting;
import dev._3000IQPlay.experium.features.modules.Module;

public class PistonPush extends Module
{
    private final Setting<Boolean> rotate;
    private final Setting<Boolean> disable;
    private final Setting<Boolean> noGhost;
    private final Setting<Integer> deay;
    private final Setting<Double> range;
    private int tick;
    private boolean gs;
    
    public PistonPush() {
        super("PistonPush", "Makes 8yo kids cry", Category.COMBAT, true, false, false);
        this.rotate = (Setting<Boolean>)this.register(new Setting("Rotate", (T)true));
        this.disable = (Setting<Boolean>)this.register(new Setting("Disable", (T)true));
        this.noGhost = (Setting<Boolean>)this.register(new Setting("Packet", (T)false));
        this.deay = (Setting<Integer>)this.register(new Setting("Deay", (T)30, (T)0, (T)100));
        this.range = (Setting<Double>)this.register(new Setting("Range", (T)4.0, (T)0.0, (T)10.0));
        this.gs = true;
    }
    
    @Override
    public void onUpdate() {
        if (this.tick != 90 && this.tick++ >= this.deay.getValue()) {
            this.tick = 0;
            this.gs = true;
        }
        if (PistonPush.mc.player == null || PistonPush.mc.player.isDead) {
            return;
        }
        if (nullCheck()) {
            return;
        }
        if (this.disable.getValue()) {
            this.disable();
        }
        if (InventoryUtil.findHotbarBlock((Block)Blocks.PISTON) == -1) {
            if (this.disable.getValue()) {
                Command.sendMessage("<" + this.getDisplayName() + "> " + ChatFormatting.RED + "No PISTON...");
            }
            return;
        }
        if (InventoryUtil.findHotbarBlock(Blocks.REDSTONE_BLOCK) == -1) {
            if (this.disable.getValue()) {
                Command.sendMessage("<" + this.getDisplayName() + "> " + ChatFormatting.RED + "No REDSTONE BLOCK...");
            }
            return;
        }
        if (InventoryUtil.findHotbarBlock(Blocks.OBSIDIAN) == -1) {
            if (this.disable.getValue()) {
                Command.sendMessage("<" + this.getDisplayName() + "> " + ChatFormatting.RED + "No PISTON...");
            }
            return;
        }
        final EntityPlayer target = this.getTarget(this.range.getValue(), true);
        if (this.gs) {
            this.gs = false;
            if (target != null && PistonPush.mc.world.getBlockState(new BlockPos(target.posX, target.posY, target.posZ)).getBlock() != Blocks.AIR) {
                if (PistonPush.mc.world.getBlockState(new BlockPos(target.posX, target.posY + 2.0, target.posZ)).getBlock() == Blocks.AIR && PistonPush.mc.world.getBlockState(new BlockPos(target.posX + 1.0, target.posY + 1.0, target.posZ)).getBlock() == Blocks.AIR && PistonPush.mc.world.getBlockState(new BlockPos(target.posX - 1.0, target.posY + 1.0, target.posZ)).getBlock() == Blocks.AIR && PistonPush.mc.world.getBlockState(new BlockPos(target.posX + 2.0, target.posY + 1.0, target.posZ)).getBlock() == Blocks.AIR) {
                    if (PistonPush.mc.world.getBlockState(new BlockPos(target.posX + 2.0, target.posY, target.posZ)).getBlock() == Blocks.AIR) {
                        final int imp = PistonPush.mc.player.inventory.currentItem;
                        PistonPush.mc.player.inventory.currentItem = InventoryUtil.findHotbarBlock(Blocks.OBSIDIAN);
                        BlockUtil.placeBlock(new BlockPos(target.posX + 2.0, target.posY, target.posZ), EnumHand.MAIN_HAND, this.rotate.getValue(), this.noGhost.getValue(), true);
                        PistonPush.mc.player.inventory.currentItem = imp;
                    }
                    final int imp = PistonPush.mc.player.inventory.currentItem;
                    PistonPush.mc.player.inventory.currentItem = InventoryUtil.findHotbarBlock(Blocks.REDSTONE_BLOCK);
                    BlockUtil.placeBlock(new BlockPos(target.posX + 2.0, target.posY + 1.0, target.posZ), EnumHand.MAIN_HAND, this.rotate.getValue(), this.noGhost.getValue(), true);
                    PistonPush.mc.player.inventory.currentItem = InventoryUtil.findHotbarBlock((Block)Blocks.PISTON);
                    BlockUtil.placeBlock(new BlockPos(target.posX + 1.0, target.posY + 1.0, target.posZ), EnumHand.MAIN_HAND, this.rotate.getValue(), this.noGhost.getValue(), true);
                    PistonPush.mc.player.inventory.currentItem = imp;
                    return;
                }
                if (PistonPush.mc.world.getBlockState(new BlockPos(target.posX, target.posY + 2.0, target.posZ)).getBlock() == Blocks.AIR && PistonPush.mc.world.getBlockState(new BlockPos(target.posX, target.posY + 1.0, target.posZ + 1.0)).getBlock() == Blocks.AIR && PistonPush.mc.world.getBlockState(new BlockPos(target.posX, target.posY + 1.0, target.posZ - 1.0)).getBlock() == Blocks.AIR && PistonPush.mc.world.getBlockState(new BlockPos(target.posX, target.posY + 1.0, target.posZ + 2.0)).getBlock() == Blocks.AIR) {
                    if (PistonPush.mc.world.getBlockState(new BlockPos(target.posX, target.posY, target.posZ + 2.0)).getBlock() == Blocks.AIR) {
                        final int imp = PistonPush.mc.player.inventory.currentItem;
                        PistonPush.mc.player.inventory.currentItem = InventoryUtil.findHotbarBlock(Blocks.OBSIDIAN);
                        BlockUtil.placeBlock(new BlockPos(target.posX, target.posY, target.posZ + 2.0), EnumHand.MAIN_HAND, this.rotate.getValue(), this.noGhost.getValue(), true);
                        PistonPush.mc.player.inventory.currentItem = imp;
                    }
                    final int imp = PistonPush.mc.player.inventory.currentItem;
                    PistonPush.mc.player.inventory.currentItem = InventoryUtil.findHotbarBlock(Blocks.REDSTONE_BLOCK);
                    BlockUtil.placeBlock(new BlockPos(target.posX, target.posY + 1.0, target.posZ + 2.0), EnumHand.MAIN_HAND, this.rotate.getValue(), this.noGhost.getValue(), true);
                    PistonPush.mc.player.inventory.currentItem = InventoryUtil.findHotbarBlock((Block)Blocks.PISTON);
                    BlockUtil.placeBlock(new BlockPos(target.posX, target.posY + 1.0, target.posZ + 1.0), EnumHand.MAIN_HAND, this.rotate.getValue(), this.noGhost.getValue(), true);
                    PistonPush.mc.player.inventory.currentItem = imp;
                    return;
                }
                if (PistonPush.mc.world.getBlockState(new BlockPos(target.posX, target.posY + 2.0, target.posZ)).getBlock() == Blocks.AIR && PistonPush.mc.world.getBlockState(new BlockPos(target.posX, target.posY + 1.0, target.posZ - 1.0)).getBlock() == Blocks.AIR && PistonPush.mc.world.getBlockState(new BlockPos(target.posX, target.posY + 1.0, target.posZ + 1.0)).getBlock() == Blocks.AIR && PistonPush.mc.world.getBlockState(new BlockPos(target.posX, target.posY + 1.0, target.posZ - 2.0)).getBlock() == Blocks.AIR) {
                    if (PistonPush.mc.world.getBlockState(new BlockPos(target.posX, target.posY, target.posZ - 2.0)).getBlock() == Blocks.AIR) {
                        final int imp = PistonPush.mc.player.inventory.currentItem;
                        PistonPush.mc.player.inventory.currentItem = InventoryUtil.findHotbarBlock(Blocks.OBSIDIAN);
                        BlockUtil.placeBlock(new BlockPos(target.posX, target.posY, target.posZ - 2.0), EnumHand.MAIN_HAND, this.rotate.getValue(), this.noGhost.getValue(), true);
                        PistonPush.mc.player.inventory.currentItem = imp;
                    }
                    final int imp = PistonPush.mc.player.inventory.currentItem;
                    PistonPush.mc.player.inventory.currentItem = InventoryUtil.findHotbarBlock(Blocks.REDSTONE_BLOCK);
                    BlockUtil.placeBlock(new BlockPos(target.posX, target.posY + 1.0, target.posZ - 2.0), EnumHand.MAIN_HAND, this.rotate.getValue(), this.noGhost.getValue(), true);
                    PistonPush.mc.player.inventory.currentItem = InventoryUtil.findHotbarBlock((Block)Blocks.PISTON);
                    BlockUtil.placeBlock(new BlockPos(target.posX, target.posY + 1.0, target.posZ - 1.0), EnumHand.MAIN_HAND, this.rotate.getValue(), this.noGhost.getValue(), true);
                    PistonPush.mc.player.inventory.currentItem = imp;
                    return;
                }
                if (PistonPush.mc.world.getBlockState(new BlockPos(target.posX, target.posY + 2.0, target.posZ)).getBlock() == Blocks.AIR && PistonPush.mc.world.getBlockState(new BlockPos(target.posX - 1.0, target.posY + 1.0, target.posZ)).getBlock() == Blocks.AIR && PistonPush.mc.world.getBlockState(new BlockPos(target.posX + 1.0, target.posY + 1.0, target.posZ)).getBlock() == Blocks.AIR && PistonPush.mc.world.getBlockState(new BlockPos(target.posX - 2.0, target.posY + 1.0, target.posZ)).getBlock() == Blocks.AIR) {
                    if (PistonPush.mc.world.getBlockState(new BlockPos(target.posX - 2.0, target.posY, target.posZ)).getBlock() == Blocks.AIR) {
                        final int imp = PistonPush.mc.player.inventory.currentItem;
                        PistonPush.mc.player.inventory.currentItem = InventoryUtil.findHotbarBlock(Blocks.OBSIDIAN);
                        BlockUtil.placeBlock(new BlockPos(target.posX - 2.0, target.posY, target.posZ), EnumHand.MAIN_HAND, this.rotate.getValue(), this.noGhost.getValue(), true);
                        PistonPush.mc.player.inventory.currentItem = imp;
                    }
                    final int imp = PistonPush.mc.player.inventory.currentItem;
                    PistonPush.mc.player.inventory.currentItem = InventoryUtil.findHotbarBlock(Blocks.REDSTONE_BLOCK);
                    BlockUtil.placeBlock(new BlockPos(target.posX - 2.0, target.posY + 1.0, target.posZ), EnumHand.MAIN_HAND, this.rotate.getValue(), this.noGhost.getValue(), true);
                    PistonPush.mc.player.inventory.currentItem = InventoryUtil.findHotbarBlock((Block)Blocks.PISTON);
                    BlockUtil.placeBlock(new BlockPos(target.posX - 1.0, target.posY + 1.0, target.posZ), EnumHand.MAIN_HAND, this.rotate.getValue(), this.noGhost.getValue(), true);
                    PistonPush.mc.player.inventory.currentItem = imp;
                }
            }
        }
    }
    
    private EntityPlayer getTarget(final double range, final boolean trapped) {
        EntityPlayer target = null;
        double distance = Math.pow(range, 2.0) + 1.0;
        for (final EntityPlayer player : AutoTrap.mc.world.playerEntities) {
            if (!EntityUtil.isntValid((Entity)player, range) && (!trapped || !OyVeyentityUtil.isTrapped(player, false, false, false, false, false))) {
                if (Experium.speedManager.getPlayerSpeed(player) > 10.0) {
                    continue;
                }
                if (target == null) {
                    target = player;
                    distance = AutoTrap.mc.player.getDistanceSq((Entity)player);
                }
                else {
                    if (AutoTrap.mc.player.getDistanceSq((Entity)player) >= distance) {
                        continue;
                    }
                    target = player;
                    distance = AutoTrap.mc.player.getDistanceSq((Entity)player);
                }
            }
        }
        return target;
    }
}
