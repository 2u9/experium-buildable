//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Luni\Documents\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev._3000IQPlay.experium.features.modules.combat;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.item.ItemStack;
import net.minecraft.init.Items;
import dev._3000IQPlay.experium.event.events.UpdateWalkingPlayerEvent;
import dev._3000IQPlay.experium.features.command.Command;
import java.util.Iterator;
import dev._3000IQPlay.experium.util.BlockUtil;
import net.minecraft.init.Blocks;
import net.minecraft.entity.Entity;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import dev._3000IQPlay.experium.Experium;
import java.util.Objects;
import dev._3000IQPlay.experium.util.EntityUtil;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import dev._3000IQPlay.experium.util.PairUtil;
import java.util.ArrayList;
import dev._3000IQPlay.experium.features.setting.Setting;
import net.minecraft.util.math.BlockPos;
import dev._3000IQPlay.experium.features.modules.Module;

public class AutoCity extends Module
{
    private static final BlockPos[] surroundOffset;
    public Setting<Boolean> raytrace;
    public Setting<Integer> range;
    public Setting<Boolean> rotate;
    public Setting<Boolean> autodisable;
    public Setting<Integer> rotations;
    
    public AutoCity() {
        super("AutoCity", "For lazy apes.", Category.COMBAT, true, false, false);
        this.raytrace = (Setting<Boolean>)this.register(new Setting("Raytrace", (T)false));
        this.range = (Setting<Integer>)this.register(new Setting("Range", (T)5, (T)1, (T)6));
        this.rotate = (Setting<Boolean>)this.register(new Setting("Rotate", (T)false));
        this.autodisable = (Setting<Boolean>)this.register(new Setting("AutoDisable", (T)true));
        this.rotations = (Setting<Integer>)this.register(new Setting("Spoofs", (T)1, (T)1, (T)20));
    }
    
    public static ArrayList<PairUtil<EntityPlayer, ArrayList<BlockPos>>> GetPlayersReadyToBeCitied() {
        final ArrayList<PairUtil<EntityPlayer, ArrayList<BlockPos>>> arrayList = new ArrayList<PairUtil<EntityPlayer, ArrayList<BlockPos>>>();
        for (final EntityPlayer entity : Objects.requireNonNull(EntityUtil.getNearbyPlayers(6.0)).stream().filter(entityPlayer -> !Experium.friendManager.isFriend(entityPlayer)).collect((Collector<? super Object, ?, List<? super Object>>)Collectors.toList())) {
            final ArrayList<BlockPos> arrayList2 = new ArrayList<BlockPos>();
            for (int i = 0; i < 4; ++i) {
                final BlockPos blockPos = EntityUtil.GetPositionVectorBlockPos((Entity)entity, AutoCity.surroundOffset[i]);
                if (AutoCity.mc.world.getBlockState(blockPos).getBlock() == Blocks.OBSIDIAN) {
                    boolean bl = false;
                    switch (i) {
                        case 0: {
                            bl = BlockUtil.canPlaceCrystal(blockPos.north(2), true, false);
                            break;
                        }
                        case 1: {
                            bl = BlockUtil.canPlaceCrystal(blockPos.east(2), true, false);
                            break;
                        }
                        case 2: {
                            bl = BlockUtil.canPlaceCrystal(blockPos.south(2), true, false);
                            break;
                        }
                        case 3: {
                            bl = BlockUtil.canPlaceCrystal(blockPos.west(2), true, false);
                            break;
                        }
                    }
                    if (bl) {
                        arrayList2.add(blockPos);
                    }
                }
            }
            if (arrayList2.isEmpty()) {
                continue;
            }
            arrayList.add(new PairUtil<EntityPlayer, ArrayList<BlockPos>>(entity, arrayList2));
        }
        return arrayList;
    }
    
    @Override
    public void onEnable() {
        final ArrayList<PairUtil<EntityPlayer, ArrayList<BlockPos>>> arrayList = GetPlayersReadyToBeCitied();
        if (arrayList.isEmpty()) {
            Command.sendMessage("There is no one to city!");
            this.toggle();
            return;
        }
        EntityPlayer entityPlayer = null;
        BlockPos blockPos = null;
        double d = 50.0;
        for (final PairUtil<EntityPlayer, ArrayList<BlockPos>> pairUtil : arrayList) {
            for (final BlockPos blockPos2 : pairUtil.getSecond()) {
                if (blockPos == null) {
                    entityPlayer = pairUtil.getFirst();
                    blockPos = blockPos2;
                }
                else {
                    final double d2 = blockPos2.getDistance(blockPos.getX(), blockPos.getY(), blockPos.getZ());
                    if (d2 >= d) {
                        continue;
                    }
                    d = d2;
                    blockPos = blockPos2;
                    entityPlayer = pairUtil.getFirst();
                }
            }
        }
        if (blockPos == null || entityPlayer == null) {
            Command.sendMessage("Couldn't find any blocks to mine!");
            this.toggle();
            return;
        }
        BlockUtil.SetCurrentBlock(blockPos);
        Command.sendMessage("Attempting to mine a block by your target: " + entityPlayer.getName());
    }
    
    @SubscribeEvent
    public void onUpdateWalkingPlayer(final UpdateWalkingPlayerEvent updateWalkingPlayerEvent) {
        final boolean bl2;
        boolean bl = bl2 = (AutoCity.mc.player.getHeldItemMainhand().getItem() == Items.DIAMOND_PICKAXE);
        if (!bl) {
            for (int i = 0; i < 9; ++i) {
                final ItemStack itemStack = AutoCity.mc.player.inventory.getStackInSlot(i);
                if (!itemStack.isEmpty() && itemStack.getItem() == Items.DIAMOND_PICKAXE) {
                    bl = true;
                    AutoCity.mc.player.inventory.currentItem = i;
                    AutoCity.mc.playerController.updateController();
                    break;
                }
            }
        }
        if (!bl) {
            Command.sendMessage("No pickaxe!");
            this.toggle();
            return;
        }
        final BlockPos blockPos = BlockUtil.GetCurrBlock();
        if (blockPos == null) {
            if (this.autodisable.getValue()) {
                Command.sendMessage("Done!");
                this.toggle();
            }
            return;
        }
        if (this.rotate.getValue()) {
            Experium.rotationManager.updateRotations();
            Experium.rotationManager.lookAtPos(blockPos);
            updateWalkingPlayerEvent.setCanceled(true);
        }
        BlockUtil.Update(this.range.getValue(), this.raytrace.getValue());
    }
    
    static {
        surroundOffset = new BlockPos[] { new BlockPos(0, 0, -1), new BlockPos(1, 0, 0), new BlockPos(0, 0, 1), new BlockPos(-1, 0, 0) };
    }
}
