//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Luni\Documents\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev._3000IQPlay.experium.features.modules.player;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.play.client.CPacketPlayer;
import dev._3000IQPlay.experium.util.MathUtil;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketEntityAction;
import dev._3000IQPlay.experium.util.InventoryUtil;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockAir;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.init.MobEffects;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.EnumHand;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.util.EnumFacing;
import dev._3000IQPlay.experium.util.BlockUtil;
import dev._3000IQPlay.experium.util.EntityUtil;
import dev._3000IQPlay.experium.features.Feature;
import dev._3000IQPlay.experium.event.events.UpdateWalkingPlayerEvent;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.inventory.Container;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import java.util.Arrays;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import dev._3000IQPlay.experium.util.Timer;
import net.minecraft.block.Block;
import java.util.List;
import dev._3000IQPlay.experium.features.setting.Setting;
import dev._3000IQPlay.experium.features.modules.Module;

public class Scaffold extends Module
{
    private final Setting<Mode> mode;
    private final Setting<Boolean> swing;
    private final Setting<Boolean> bSwitch;
    private final Setting<Boolean> center;
    private final Setting<Boolean> tower;
    private final Setting<Boolean> keepY;
    private final Setting<Boolean> sprint;
    private final Setting<Boolean> replenishBlocks;
    private final Setting<Boolean> down;
    private final Setting<Float> expand;
    private final List<Block> invalid;
    private final Timer timerMotion;
    private final Timer itemTimer;
    private final Timer timer;
    public Setting<Boolean> rotation;
    private int lastY;
    private BlockPos pos;
    private boolean teleported;
    
    public Scaffold() {
        super("Scaffold", "Places Blocks underneath you.", Category.PLAYER, true, false, false);
        this.mode = (Setting<Mode>)this.register(new Setting("Mode", (T)Mode.New));
        this.swing = (Setting<Boolean>)this.register(new Setting("SwingArm", (T)true, bl -> this.mode.getValue() == Mode.New));
        this.bSwitch = (Setting<Boolean>)this.register(new Setting("Switch", (T)true, bl -> this.mode.getValue() == Mode.New));
        this.center = (Setting<Boolean>)this.register(new Setting("Center", (T)false, bl -> this.mode.getValue() == Mode.New));
        this.tower = (Setting<Boolean>)this.register(new Setting("Tower", (T)true, bl -> this.mode.getValue() == Mode.New));
        this.keepY = (Setting<Boolean>)this.register(new Setting("KeepYLevel", (T)false, bl -> this.mode.getValue() == Mode.New));
        this.sprint = (Setting<Boolean>)this.register(new Setting("UseSprint", (T)true, bl -> this.mode.getValue() == Mode.New));
        this.replenishBlocks = (Setting<Boolean>)this.register(new Setting("Replenish", (T)true, bl -> this.mode.getValue() == Mode.New));
        this.down = (Setting<Boolean>)this.register(new Setting("Down", (T)true, bl -> this.mode.getValue() == Mode.New));
        this.expand = (Setting<Float>)this.register(new Setting("Expand", (T)0.0f, (T)0.0f, (T)6.0f, f -> this.mode.getValue() == Mode.New));
        this.invalid = Arrays.asList(Blocks.ENCHANTING_TABLE, Blocks.FURNACE, Blocks.CARPET, Blocks.CRAFTING_TABLE, Blocks.TRAPPED_CHEST, (Block)Blocks.CHEST, Blocks.DISPENSER, Blocks.AIR, (Block)Blocks.WATER, (Block)Blocks.LAVA, (Block)Blocks.FLOWING_WATER, (Block)Blocks.FLOWING_LAVA, Blocks.SNOW_LAYER, Blocks.TORCH, Blocks.ANVIL, Blocks.JUKEBOX, Blocks.STONE_BUTTON, Blocks.WOODEN_BUTTON, Blocks.LEVER, Blocks.NOTEBLOCK, Blocks.STONE_PRESSURE_PLATE, Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE, Blocks.WOODEN_PRESSURE_PLATE, Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE, (Block)Blocks.RED_MUSHROOM, (Block)Blocks.BROWN_MUSHROOM, (Block)Blocks.YELLOW_FLOWER, (Block)Blocks.RED_FLOWER, Blocks.ANVIL, (Block)Blocks.CACTUS, Blocks.LADDER, Blocks.ENDER_CHEST);
        this.timerMotion = new Timer();
        this.itemTimer = new Timer();
        this.timer = new Timer();
        this.rotation = (Setting<Boolean>)this.register(new Setting("Rotate", (T)false, bl -> this.mode.getValue() == Mode.Old));
    }
    
    public static void swap(final int n, final int n2) {
        Scaffold.mc.playerController.windowClick(Scaffold.mc.player.inventoryContainer.windowId, n, 0, ClickType.PICKUP, (EntityPlayer)Scaffold.mc.player);
        Scaffold.mc.playerController.windowClick(Scaffold.mc.player.inventoryContainer.windowId, n2, 0, ClickType.PICKUP, (EntityPlayer)Scaffold.mc.player);
        Scaffold.mc.playerController.windowClick(Scaffold.mc.player.inventoryContainer.windowId, n, 0, ClickType.PICKUP, (EntityPlayer)Scaffold.mc.player);
        Scaffold.mc.playerController.updateController();
    }
    
    public static int getItemSlot(final Container container, final Item item) {
        int n = 0;
        for (int i = 9; i < 45; ++i) {
            if (container.getSlot(i).getHasStack()) {
                if (container.getSlot(i).getStack().getItem() == item) {
                    n = i;
                }
            }
        }
        return n;
    }
    
    public static boolean isMoving(final EntityLivingBase entityLivingBase) {
        return entityLivingBase.moveForward == 0.0f && entityLivingBase.moveStrafing == 0.0f;
    }
    
    @Override
    public void onEnable() {
        this.timer.reset();
    }
    
    @SubscribeEvent
    public void onUpdateWalkingPlayerPost(final UpdateWalkingPlayerEvent updateWalkingPlayerEvent) {
        if (this.mode.getValue() == Mode.Old) {
            if (this.isOff() || Feature.fullNullCheck() || updateWalkingPlayerEvent.getStage() == 0) {
                return;
            }
            if (!Scaffold.mc.gameSettings.keyBindJump.isKeyDown()) {
                this.timer.reset();
            }
            final BlockPos blockPos;
            if (BlockUtil.isScaffoldPos((blockPos = EntityUtil.getPlayerPosWithEntity()).add(0, -1, 0))) {
                if (BlockUtil.isValidBlock(blockPos.add(0, -2, 0))) {
                    this.place(blockPos.add(0, -1, 0), EnumFacing.UP);
                }
                else if (BlockUtil.isValidBlock(blockPos.add(-1, -1, 0))) {
                    this.place(blockPos.add(0, -1, 0), EnumFacing.EAST);
                }
                else if (BlockUtil.isValidBlock(blockPos.add(1, -1, 0))) {
                    this.place(blockPos.add(0, -1, 0), EnumFacing.WEST);
                }
                else if (BlockUtil.isValidBlock(blockPos.add(0, -1, -1))) {
                    this.place(blockPos.add(0, -1, 0), EnumFacing.SOUTH);
                }
                else if (BlockUtil.isValidBlock(blockPos.add(0, -1, 1))) {
                    this.place(blockPos.add(0, -1, 0), EnumFacing.NORTH);
                }
                else if (BlockUtil.isValidBlock(blockPos.add(1, -1, 1))) {
                    if (BlockUtil.isValidBlock(blockPos.add(0, -1, 1))) {
                        this.place(blockPos.add(0, -1, 1), EnumFacing.NORTH);
                    }
                    this.place(blockPos.add(1, -1, 1), EnumFacing.EAST);
                }
                else if (BlockUtil.isValidBlock(blockPos.add(-1, -1, 1))) {
                    if (BlockUtil.isValidBlock(blockPos.add(-1, -1, 0))) {
                        this.place(blockPos.add(0, -1, 1), EnumFacing.WEST);
                    }
                    this.place(blockPos.add(-1, -1, 1), EnumFacing.SOUTH);
                }
                else if (BlockUtil.isValidBlock(blockPos.add(1, -1, 1))) {
                    if (BlockUtil.isValidBlock(blockPos.add(0, -1, 1))) {
                        this.place(blockPos.add(0, -1, 1), EnumFacing.SOUTH);
                    }
                    this.place(blockPos.add(1, -1, 1), EnumFacing.WEST);
                }
                else if (BlockUtil.isValidBlock(blockPos.add(1, -1, 1))) {
                    if (BlockUtil.isValidBlock(blockPos.add(0, -1, 1))) {
                        this.place(blockPos.add(0, -1, 1), EnumFacing.EAST);
                    }
                    this.place(blockPos.add(1, -1, 1), EnumFacing.NORTH);
                }
            }
        }
    }
    
    @Override
    public void onUpdate() {
        if (this.mode.getValue() == Mode.New) {
            if (this.down.getValue() && Scaffold.mc.gameSettings.keyBindSneak.isKeyDown() && !this.sprint.getValue()) {
                Scaffold.mc.player.setSprinting(false);
            }
            if (this.replenishBlocks.getValue() && !(Scaffold.mc.player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemBlock) && this.getBlockCountHotbar() <= 0 && this.itemTimer.passedMs(100L)) {
                for (int i = 9; i < 45; ++i) {
                    final ItemStack itemStack;
                    if (Scaffold.mc.player.inventoryContainer.getSlot(i).getHasStack() && (itemStack = Scaffold.mc.player.inventoryContainer.getSlot(i).getStack()).getItem() instanceof ItemBlock && !this.invalid.contains(Block.getBlockFromItem(itemStack.getItem()))) {
                        if (i < 36) {
                            swap(getItemSlot(Scaffold.mc.player.inventoryContainer, itemStack.getItem()), 44);
                        }
                    }
                }
            }
            if (this.keepY.getValue()) {
                if ((isMoving((EntityLivingBase)Scaffold.mc.player) && Scaffold.mc.gameSettings.keyBindJump.isKeyDown()) || Scaffold.mc.player.collidedVertically || Scaffold.mc.player.onGround) {
                    this.lastY = MathHelper.floor(Scaffold.mc.player.posY);
                }
            }
            else {
                this.lastY = MathHelper.floor(Scaffold.mc.player.posY);
            }
            BlockData blockData = null;
            double d = Scaffold.mc.player.posX;
            double d2 = Scaffold.mc.player.posZ;
            final double d3 = this.keepY.getValue() ? this.lastY : Scaffold.mc.player.posY;
            final double d4 = Scaffold.mc.player.movementInput.moveForward;
            final double d5 = Scaffold.mc.player.movementInput.moveStrafe;
            final float f = Scaffold.mc.player.rotationYaw;
            if (!Scaffold.mc.player.collidedHorizontally) {
                final double[] object2 = this.getExpandCoords(d, d2, d4, d5, f);
                d = object2[0];
                d2 = object2[1];
            }
            if (this.canPlace(Scaffold.mc.world.getBlockState(new BlockPos(Scaffold.mc.player.posX, Scaffold.mc.player.posY - ((Scaffold.mc.gameSettings.keyBindSneak.isKeyDown() && this.down.getValue()) ? 2 : 1), Scaffold.mc.player.posZ)).getBlock())) {
                d = Scaffold.mc.player.posX;
                d2 = Scaffold.mc.player.posZ;
            }
            BlockPos object3 = new BlockPos(d, d3 - 1.0, d2);
            if (Scaffold.mc.gameSettings.keyBindSneak.isKeyDown() && this.down.getValue()) {
                object3 = new BlockPos(d, d3 - 2.0, d2);
            }
            this.pos = object3;
            if (Scaffold.mc.world.getBlockState(object3).getBlock() == Blocks.AIR) {
                blockData = this.getBlockData2(object3);
            }
            if (blockData != null) {
                if (this.getBlockCountHotbar() <= 0 || (!this.bSwitch.getValue() && !(Scaffold.mc.player.getHeldItemMainhand().getItem() instanceof ItemBlock))) {
                    return;
                }
                final int n = Scaffold.mc.player.inventory.currentItem;
                if (this.bSwitch.getValue()) {
                    for (int j = 0; j < 9; ++j) {
                        Scaffold.mc.player.inventory.getStackInSlot(j);
                        if (Scaffold.mc.player.inventory.getStackInSlot(j).getCount() != 0 && Scaffold.mc.player.inventory.getStackInSlot(j).getItem() instanceof ItemBlock && !this.invalid.contains(((ItemBlock)Scaffold.mc.player.inventory.getStackInSlot(j).getItem()).getBlock())) {
                            Scaffold.mc.player.inventory.currentItem = j;
                            break;
                        }
                    }
                }
                if (this.mode.getValue() == Mode.New) {
                    if (Scaffold.mc.gameSettings.keyBindJump.isKeyDown() && Scaffold.mc.player.moveForward == 0.0f && Scaffold.mc.player.moveStrafing == 0.0f && !Scaffold.mc.player.isPotionActive(MobEffects.JUMP_BOOST)) {
                        if (!this.teleported && this.center.getValue()) {
                            this.teleported = true;
                            final BlockPos blockPos = new BlockPos(Scaffold.mc.player.posX, Scaffold.mc.player.posY, Scaffold.mc.player.posZ);
                            Scaffold.mc.player.setPosition(blockPos.getX() + 0.5, (double)blockPos.getY(), blockPos.getZ() + 0.5);
                        }
                        if (this.center.getValue() && !this.teleported) {
                            return;
                        }
                        Scaffold.mc.player.motionY = 0.41999998688697815;
                        Scaffold.mc.player.motionZ = 0.0;
                        Scaffold.mc.player.motionX = 0.0;
                        if (!this.tower.getValue()) {
                            Scaffold.mc.player.motionY = -0.28;
                        }
                    }
                    else {
                        this.timerMotion.reset();
                        if (this.teleported && this.center.getValue()) {
                            this.teleported = false;
                        }
                    }
                }
                if (Scaffold.mc.playerController.processRightClickBlock(Scaffold.mc.player, Scaffold.mc.world, blockData.position, blockData.face, new Vec3d(blockData.position.getX() + Math.random(), blockData.position.getY() + Math.random(), blockData.position.getZ() + Math.random()), EnumHand.MAIN_HAND) != EnumActionResult.FAIL) {
                    if (this.swing.getValue()) {
                        Scaffold.mc.player.swingArm(EnumHand.MAIN_HAND);
                    }
                    else {
                        Scaffold.mc.player.connection.sendPacket((Packet)new CPacketAnimation(EnumHand.MAIN_HAND));
                    }
                }
                Scaffold.mc.player.inventory.currentItem = n;
            }
        }
    }
    
    public double[] getExpandCoords(final double x, final double z, final double forward, final double strafe, final float YAW) {
        BlockPos underPos = new BlockPos(x, Scaffold.mc.player.posY - ((Scaffold.mc.gameSettings.keyBindSneak.isKeyDown() && this.down.getValue()) ? 2 : 1), z);
        Block underBlock = Scaffold.mc.world.getBlockState(underPos).getBlock();
        double xCalc = -999.0;
        double zCalc = -999.0;
        double dist = 0.0;
        final double expandDist = this.expand.getValue() * 2.0f;
        while (!this.canPlace(underBlock)) {
            ++dist;
            if (dist > expandDist) {
                dist = expandDist;
            }
            xCalc = x + (forward * 0.45 * Math.cos(Math.toRadians(YAW + 90.0f)) + strafe * 0.45 * Math.sin(Math.toRadians(YAW + 90.0f))) * dist;
            zCalc = z + (forward * 0.45 * Math.sin(Math.toRadians(YAW + 90.0f)) - strafe * 0.45 * Math.cos(Math.toRadians(YAW + 90.0f))) * dist;
            if (dist == expandDist) {
                break;
            }
            underPos = new BlockPos(xCalc, Scaffold.mc.player.posY - ((Scaffold.mc.gameSettings.keyBindSneak.isKeyDown() && this.down.getValue()) ? 2 : 1), zCalc);
            underBlock = Scaffold.mc.world.getBlockState(underPos).getBlock();
        }
        return new double[] { xCalc, zCalc };
    }
    
    public boolean canPlace(final Block block) {
        return (block instanceof BlockAir || block instanceof BlockLiquid) && Scaffold.mc.world != null && Scaffold.mc.player != null && this.pos != null && Scaffold.mc.world.getEntitiesWithinAABBExcludingEntity((Entity)null, new AxisAlignedBB(this.pos)).isEmpty();
    }
    
    private int getBlockCountHotbar() {
        int n = 0;
        for (int i = 36; i < 45; ++i) {
            if (Scaffold.mc.player.inventoryContainer.getSlot(i).getHasStack()) {
                final ItemStack itemStack = Scaffold.mc.player.inventoryContainer.getSlot(i).getStack();
                final Item item = itemStack.getItem();
                if (itemStack.getItem() instanceof ItemBlock) {
                    if (!this.invalid.contains(((ItemBlock)item).getBlock())) {
                        n += itemStack.getCount();
                    }
                }
            }
        }
        return n;
    }
    
    private BlockData getBlockData2(final BlockPos blockPos) {
        if (!this.invalid.contains(Scaffold.mc.world.getBlockState(blockPos.add(0, -1, 0)).getBlock())) {
            return new BlockData(blockPos.add(0, -1, 0), EnumFacing.UP);
        }
        if (!this.invalid.contains(Scaffold.mc.world.getBlockState(blockPos.add(-1, 0, 0)).getBlock())) {
            return new BlockData(blockPos.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (!this.invalid.contains(Scaffold.mc.world.getBlockState(blockPos.add(1, 0, 0)).getBlock())) {
            return new BlockData(blockPos.add(1, 0, 0), EnumFacing.WEST);
        }
        if (!this.invalid.contains(Scaffold.mc.world.getBlockState(blockPos.add(0, 0, 1)).getBlock())) {
            return new BlockData(blockPos.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (!this.invalid.contains(Scaffold.mc.world.getBlockState(blockPos.add(0, 0, -1)).getBlock())) {
            return new BlockData(blockPos.add(0, 0, -1), EnumFacing.SOUTH);
        }
        if (!this.invalid.contains(Scaffold.mc.world.getBlockState(blockPos.add(0, 1, 0)).getBlock())) {
            return new BlockData(blockPos.add(0, 1, 0), EnumFacing.DOWN);
        }
        final BlockPos blockPos2 = blockPos.add(-1, 0, 0);
        if (!this.invalid.contains(Scaffold.mc.world.getBlockState(blockPos2.add(0, -1, 0)).getBlock())) {
            return new BlockData(blockPos2.add(0, -1, 0), EnumFacing.UP);
        }
        if (!this.invalid.contains(Scaffold.mc.world.getBlockState(blockPos2.add(0, 1, 0)).getBlock())) {
            return new BlockData(blockPos2.add(0, 1, 0), EnumFacing.DOWN);
        }
        if (!this.invalid.contains(Scaffold.mc.world.getBlockState(blockPos2.add(-1, 0, 0)).getBlock())) {
            return new BlockData(blockPos2.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (!this.invalid.contains(Scaffold.mc.world.getBlockState(blockPos2.add(1, 0, 0)).getBlock())) {
            return new BlockData(blockPos2.add(1, 0, 0), EnumFacing.WEST);
        }
        if (!this.invalid.contains(Scaffold.mc.world.getBlockState(blockPos2.add(0, 0, 1)).getBlock())) {
            return new BlockData(blockPos2.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (!this.invalid.contains(Scaffold.mc.world.getBlockState(blockPos2.add(0, 0, -1)).getBlock())) {
            return new BlockData(blockPos2.add(0, 0, -1), EnumFacing.SOUTH);
        }
        final BlockPos blockPos3 = blockPos.add(1, 0, 0);
        if (!this.invalid.contains(Scaffold.mc.world.getBlockState(blockPos3.add(0, -1, 0)).getBlock())) {
            return new BlockData(blockPos3.add(0, -1, 0), EnumFacing.UP);
        }
        if (!this.invalid.contains(Scaffold.mc.world.getBlockState(blockPos3.add(0, 1, 0)).getBlock())) {
            return new BlockData(blockPos3.add(0, 1, 0), EnumFacing.DOWN);
        }
        if (!this.invalid.contains(Scaffold.mc.world.getBlockState(blockPos3.add(-1, 0, 0)).getBlock())) {
            return new BlockData(blockPos3.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (!this.invalid.contains(Scaffold.mc.world.getBlockState(blockPos3.add(1, 0, 0)).getBlock())) {
            return new BlockData(blockPos3.add(1, 0, 0), EnumFacing.WEST);
        }
        if (!this.invalid.contains(Scaffold.mc.world.getBlockState(blockPos3.add(0, 0, 1)).getBlock())) {
            return new BlockData(blockPos3.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (!this.invalid.contains(Scaffold.mc.world.getBlockState(blockPos3.add(0, 0, -1)).getBlock())) {
            return new BlockData(blockPos3.add(0, 0, -1), EnumFacing.SOUTH);
        }
        final BlockPos blockPos4 = blockPos.add(0, 0, 1);
        if (!this.invalid.contains(Scaffold.mc.world.getBlockState(blockPos4.add(0, -1, 0)).getBlock())) {
            return new BlockData(blockPos4.add(0, -1, 0), EnumFacing.UP);
        }
        if (!this.invalid.contains(Scaffold.mc.world.getBlockState(blockPos4.add(0, 1, 0)).getBlock())) {
            return new BlockData(blockPos4.add(0, 1, 0), EnumFacing.DOWN);
        }
        if (!this.invalid.contains(Scaffold.mc.world.getBlockState(blockPos4.add(-1, 0, 0)).getBlock())) {
            return new BlockData(blockPos4.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (!this.invalid.contains(Scaffold.mc.world.getBlockState(blockPos4.add(1, 0, 0)).getBlock())) {
            return new BlockData(blockPos4.add(1, 0, 0), EnumFacing.WEST);
        }
        if (!this.invalid.contains(Scaffold.mc.world.getBlockState(blockPos4.add(0, 0, 1)).getBlock())) {
            return new BlockData(blockPos4.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (!this.invalid.contains(Scaffold.mc.world.getBlockState(blockPos4.add(0, 0, -1)).getBlock())) {
            return new BlockData(blockPos4.add(0, 0, -1), EnumFacing.SOUTH);
        }
        final BlockPos blockPos5 = blockPos.add(0, 0, -1);
        if (!this.invalid.contains(Scaffold.mc.world.getBlockState(blockPos5.add(0, -1, 0)).getBlock())) {
            return new BlockData(blockPos5.add(0, -1, 0), EnumFacing.UP);
        }
        if (!this.invalid.contains(Scaffold.mc.world.getBlockState(blockPos5.add(0, 1, 0)).getBlock())) {
            return new BlockData(blockPos5.add(0, 1, 0), EnumFacing.DOWN);
        }
        if (!this.invalid.contains(Scaffold.mc.world.getBlockState(blockPos5.add(-1, 0, 0)).getBlock())) {
            return new BlockData(blockPos5.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (!this.invalid.contains(Scaffold.mc.world.getBlockState(blockPos5.add(1, 0, 0)).getBlock())) {
            return new BlockData(blockPos5.add(1, 0, 0), EnumFacing.WEST);
        }
        if (!this.invalid.contains(Scaffold.mc.world.getBlockState(blockPos5.add(0, 0, 1)).getBlock())) {
            return new BlockData(blockPos5.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (!this.invalid.contains(Scaffold.mc.world.getBlockState(blockPos5.add(0, 0, -1)).getBlock())) {
            return new BlockData(blockPos5.add(0, 0, -1), EnumFacing.SOUTH);
        }
        if (!this.invalid.contains(Scaffold.mc.world.getBlockState(blockPos2.add(0, -1, 0)).getBlock())) {
            return new BlockData(blockPos2.add(0, -1, 0), EnumFacing.UP);
        }
        if (!this.invalid.contains(Scaffold.mc.world.getBlockState(blockPos2.add(0, 1, 0)).getBlock())) {
            return new BlockData(blockPos2.add(0, 1, 0), EnumFacing.DOWN);
        }
        if (!this.invalid.contains(Scaffold.mc.world.getBlockState(blockPos2.add(-1, 0, 0)).getBlock())) {
            return new BlockData(blockPos2.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (!this.invalid.contains(Scaffold.mc.world.getBlockState(blockPos2.add(1, 0, 0)).getBlock())) {
            return new BlockData(blockPos2.add(1, 0, 0), EnumFacing.WEST);
        }
        if (!this.invalid.contains(Scaffold.mc.world.getBlockState(blockPos2.add(0, 0, 1)).getBlock())) {
            return new BlockData(blockPos2.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (!this.invalid.contains(Scaffold.mc.world.getBlockState(blockPos2.add(0, 0, -1)).getBlock())) {
            return new BlockData(blockPos2.add(0, 0, -1), EnumFacing.SOUTH);
        }
        if (!this.invalid.contains(Scaffold.mc.world.getBlockState(blockPos3.add(0, -1, 0)).getBlock())) {
            return new BlockData(blockPos3.add(0, -1, 0), EnumFacing.UP);
        }
        if (!this.invalid.contains(Scaffold.mc.world.getBlockState(blockPos3.add(0, 1, 0)).getBlock())) {
            return new BlockData(blockPos3.add(0, 1, 0), EnumFacing.DOWN);
        }
        if (!this.invalid.contains(Scaffold.mc.world.getBlockState(blockPos3.add(-1, 0, 0)).getBlock())) {
            return new BlockData(blockPos3.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (!this.invalid.contains(Scaffold.mc.world.getBlockState(blockPos3.add(1, 0, 0)).getBlock())) {
            return new BlockData(blockPos3.add(1, 0, 0), EnumFacing.WEST);
        }
        if (!this.invalid.contains(Scaffold.mc.world.getBlockState(blockPos3.add(0, 0, 1)).getBlock())) {
            return new BlockData(blockPos3.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (!this.invalid.contains(Scaffold.mc.world.getBlockState(blockPos3.add(0, 0, -1)).getBlock())) {
            return new BlockData(blockPos3.add(0, 0, -1), EnumFacing.SOUTH);
        }
        if (!this.invalid.contains(Scaffold.mc.world.getBlockState(blockPos4.add(0, -1, 0)).getBlock())) {
            return new BlockData(blockPos4.add(0, -1, 0), EnumFacing.UP);
        }
        if (!this.invalid.contains(Scaffold.mc.world.getBlockState(blockPos4.add(0, 1, 0)).getBlock())) {
            return new BlockData(blockPos4.add(0, 1, 0), EnumFacing.DOWN);
        }
        if (!this.invalid.contains(Scaffold.mc.world.getBlockState(blockPos4.add(-1, 0, 0)).getBlock())) {
            return new BlockData(blockPos4.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (!this.invalid.contains(Scaffold.mc.world.getBlockState(blockPos4.add(1, 0, 0)).getBlock())) {
            return new BlockData(blockPos4.add(1, 0, 0), EnumFacing.WEST);
        }
        if (!this.invalid.contains(Scaffold.mc.world.getBlockState(blockPos4.add(0, 0, 1)).getBlock())) {
            return new BlockData(blockPos4.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (!this.invalid.contains(Scaffold.mc.world.getBlockState(blockPos4.add(0, 0, -1)).getBlock())) {
            return new BlockData(blockPos4.add(0, 0, -1), EnumFacing.SOUTH);
        }
        if (!this.invalid.contains(Scaffold.mc.world.getBlockState(blockPos5.add(0, -1, 0)).getBlock())) {
            return new BlockData(blockPos5.add(0, -1, 0), EnumFacing.UP);
        }
        if (!this.invalid.contains(Scaffold.mc.world.getBlockState(blockPos5.add(0, 1, 0)).getBlock())) {
            return new BlockData(blockPos5.add(0, 1, 0), EnumFacing.DOWN);
        }
        if (!this.invalid.contains(Scaffold.mc.world.getBlockState(blockPos5.add(-1, 0, 0)).getBlock())) {
            return new BlockData(blockPos5.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (!this.invalid.contains(Scaffold.mc.world.getBlockState(blockPos5.add(1, 0, 0)).getBlock())) {
            return new BlockData(blockPos5.add(1, 0, 0), EnumFacing.WEST);
        }
        if (!this.invalid.contains(Scaffold.mc.world.getBlockState(blockPos5.add(0, 0, 1)).getBlock())) {
            return new BlockData(blockPos5.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (!this.invalid.contains(Scaffold.mc.world.getBlockState(blockPos5.add(0, 0, -1)).getBlock())) {
            return new BlockData(blockPos5.add(0, 0, -1), EnumFacing.SOUTH);
        }
        final BlockPos blockPos6 = blockPos.add(0, -1, 0);
        if (!this.invalid.contains(Scaffold.mc.world.getBlockState(blockPos6.add(0, -1, 0)).getBlock())) {
            return new BlockData(blockPos6.add(0, -1, 0), EnumFacing.UP);
        }
        if (!this.invalid.contains(Scaffold.mc.world.getBlockState(blockPos6.add(0, 1, 0)).getBlock())) {
            return new BlockData(blockPos6.add(0, 1, 0), EnumFacing.DOWN);
        }
        if (!this.invalid.contains(Scaffold.mc.world.getBlockState(blockPos6.add(-1, 0, 0)).getBlock())) {
            return new BlockData(blockPos6.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (!this.invalid.contains(Scaffold.mc.world.getBlockState(blockPos6.add(1, 0, 0)).getBlock())) {
            return new BlockData(blockPos6.add(1, 0, 0), EnumFacing.WEST);
        }
        if (!this.invalid.contains(Scaffold.mc.world.getBlockState(blockPos6.add(0, 0, 1)).getBlock())) {
            return new BlockData(blockPos6.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (!this.invalid.contains(Scaffold.mc.world.getBlockState(blockPos6.add(0, 0, -1)).getBlock())) {
            return new BlockData(blockPos6.add(0, 0, -1), EnumFacing.SOUTH);
        }
        final BlockPos blockPos7 = blockPos6.add(1, 0, 0);
        if (!this.invalid.contains(Scaffold.mc.world.getBlockState(blockPos7.add(0, -1, 0)).getBlock())) {
            return new BlockData(blockPos7.add(0, -1, 0), EnumFacing.UP);
        }
        if (!this.invalid.contains(Scaffold.mc.world.getBlockState(blockPos7.add(0, 1, 0)).getBlock())) {
            return new BlockData(blockPos7.add(0, 1, 0), EnumFacing.DOWN);
        }
        if (!this.invalid.contains(Scaffold.mc.world.getBlockState(blockPos7.add(-1, 0, 0)).getBlock())) {
            return new BlockData(blockPos7.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (!this.invalid.contains(Scaffold.mc.world.getBlockState(blockPos7.add(1, 0, 0)).getBlock())) {
            return new BlockData(blockPos7.add(1, 0, 0), EnumFacing.WEST);
        }
        if (!this.invalid.contains(Scaffold.mc.world.getBlockState(blockPos7.add(0, 0, 1)).getBlock())) {
            return new BlockData(blockPos7.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (!this.invalid.contains(Scaffold.mc.world.getBlockState(blockPos7.add(0, 0, -1)).getBlock())) {
            return new BlockData(blockPos7.add(0, 0, -1), EnumFacing.SOUTH);
        }
        final BlockPos blockPos8 = blockPos6.add(-1, 0, 0);
        if (!this.invalid.contains(Scaffold.mc.world.getBlockState(blockPos8.add(0, -1, 0)).getBlock())) {
            return new BlockData(blockPos8.add(0, -1, 0), EnumFacing.UP);
        }
        if (!this.invalid.contains(Scaffold.mc.world.getBlockState(blockPos8.add(0, 1, 0)).getBlock())) {
            return new BlockData(blockPos8.add(0, 1, 0), EnumFacing.DOWN);
        }
        if (!this.invalid.contains(Scaffold.mc.world.getBlockState(blockPos8.add(-1, 0, 0)).getBlock())) {
            return new BlockData(blockPos8.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (!this.invalid.contains(Scaffold.mc.world.getBlockState(blockPos8.add(1, 0, 0)).getBlock())) {
            return new BlockData(blockPos8.add(1, 0, 0), EnumFacing.WEST);
        }
        if (!this.invalid.contains(Scaffold.mc.world.getBlockState(blockPos8.add(0, 0, 1)).getBlock())) {
            return new BlockData(blockPos8.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (!this.invalid.contains(Scaffold.mc.world.getBlockState(blockPos8.add(0, 0, -1)).getBlock())) {
            return new BlockData(blockPos8.add(0, 0, -1), EnumFacing.SOUTH);
        }
        final BlockPos blockPos9 = blockPos6.add(0, 0, 1);
        if (!this.invalid.contains(Scaffold.mc.world.getBlockState(blockPos9.add(0, -1, 0)).getBlock())) {
            return new BlockData(blockPos9.add(0, -1, 0), EnumFacing.UP);
        }
        if (!this.invalid.contains(Scaffold.mc.world.getBlockState(blockPos9.add(-1, 0, 0)).getBlock())) {
            return new BlockData(blockPos9.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (!this.invalid.contains(Scaffold.mc.world.getBlockState(blockPos9.add(0, 1, 0)).getBlock())) {
            return new BlockData(blockPos9.add(0, 1, 0), EnumFacing.DOWN);
        }
        if (!this.invalid.contains(Scaffold.mc.world.getBlockState(blockPos9.add(1, 0, 0)).getBlock())) {
            return new BlockData(blockPos9.add(1, 0, 0), EnumFacing.WEST);
        }
        if (!this.invalid.contains(Scaffold.mc.world.getBlockState(blockPos9.add(0, 0, 1)).getBlock())) {
            return new BlockData(blockPos9.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (!this.invalid.contains(Scaffold.mc.world.getBlockState(blockPos9.add(0, 0, -1)).getBlock())) {
            return new BlockData(blockPos9.add(0, 0, -1), EnumFacing.SOUTH);
        }
        final BlockPos blockPos10 = blockPos6.add(0, 0, -1);
        if (!this.invalid.contains(Scaffold.mc.world.getBlockState(blockPos10.add(0, -1, 0)).getBlock())) {
            return new BlockData(blockPos10.add(0, -1, 0), EnumFacing.UP);
        }
        if (!this.invalid.contains(Scaffold.mc.world.getBlockState(blockPos10.add(0, 1, 0)).getBlock())) {
            return new BlockData(blockPos10.add(0, 1, 0), EnumFacing.DOWN);
        }
        if (!this.invalid.contains(Scaffold.mc.world.getBlockState(blockPos10.add(-1, 0, 0)).getBlock())) {
            return new BlockData(blockPos10.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (!this.invalid.contains(Scaffold.mc.world.getBlockState(blockPos10.add(1, 0, 0)).getBlock())) {
            return new BlockData(blockPos10.add(1, 0, 0), EnumFacing.WEST);
        }
        if (!this.invalid.contains(Scaffold.mc.world.getBlockState(blockPos10.add(0, 0, 1)).getBlock())) {
            return new BlockData(blockPos10.add(0, 0, 1), EnumFacing.NORTH);
        }
        if (!this.invalid.contains(Scaffold.mc.world.getBlockState(blockPos10.add(0, 0, -1)).getBlock())) {
            return new BlockData(blockPos10.add(0, 0, -1), EnumFacing.SOUTH);
        }
        return null;
    }
    
    public void place(final BlockPos blockPos, final EnumFacing enumFacing) {
        BlockPos blockPos2 = blockPos;
        if (enumFacing == EnumFacing.UP) {
            blockPos2 = blockPos2.add(0, -1, 0);
        }
        else if (enumFacing == EnumFacing.NORTH) {
            blockPos2 = blockPos2.add(0, 0, 1);
        }
        else if (enumFacing == EnumFacing.SOUTH) {
            blockPos2 = blockPos2.add(0, 0, -1);
        }
        else if (enumFacing == EnumFacing.EAST) {
            blockPos2 = blockPos2.add(-1, 0, 0);
        }
        else if (enumFacing == EnumFacing.WEST) {
            blockPos2 = blockPos2.add(1, 0, 0);
        }
        final int n2 = Scaffold.mc.player.inventory.currentItem;
        int n3 = -1;
        for (int n4 = 0; n4 < 9; ++n4) {
            final ItemStack object = Scaffold.mc.player.inventory.getStackInSlot(n4);
            if (!InventoryUtil.isNull(object) && object.getItem() instanceof ItemBlock && Block.getBlockFromItem(object.getItem()).getDefaultState().isFullBlock()) {
                n3 = n4;
                break;
            }
        }
        if (n3 == -1) {
            return;
        }
        int n4 = 0;
        if (!Scaffold.mc.player.isSneaking() && BlockUtil.blackList.contains(Scaffold.mc.world.getBlockState(blockPos2).getBlock())) {
            Scaffold.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Scaffold.mc.player, CPacketEntityAction.Action.START_SNEAKING));
            n4 = 1;
        }
        if (!(Scaffold.mc.player.getHeldItemMainhand().getItem() instanceof ItemBlock)) {
            Scaffold.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(n3));
            Scaffold.mc.player.inventory.currentItem = n3;
            Scaffold.mc.playerController.updateController();
        }
        if (Scaffold.mc.gameSettings.keyBindJump.isKeyDown()) {
            final EntityPlayerSP player = Scaffold.mc.player;
            player.motionX *= 0.3;
            final EntityPlayerSP player2 = Scaffold.mc.player;
            player2.motionZ *= 0.3;
            Scaffold.mc.player.jump();
            if (this.timer.passedMs(1500L)) {
                Scaffold.mc.player.motionY = -0.28;
                this.timer.reset();
            }
        }
        if (this.rotation.getValue()) {
            final float[] angle = MathUtil.calcAngle(Scaffold.mc.player.getPositionEyes(Scaffold.mc.getRenderPartialTicks()), new Vec3d((double)(this.pos.getX() + 0.5f), (double)(this.pos.getY() - 0.5f), (double)(this.pos.getZ() + 0.5f)));
            Scaffold.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(angle[0], (float)MathHelper.normalizeAngle((int)angle[1], 360), Scaffold.mc.player.onGround));
        }
        Scaffold.mc.playerController.processRightClickBlock(Scaffold.mc.player, Scaffold.mc.world, blockPos2, enumFacing, new Vec3d(0.5, 0.5, 0.5), EnumHand.MAIN_HAND);
        Scaffold.mc.player.swingArm(EnumHand.MAIN_HAND);
        Scaffold.mc.player.connection.sendPacket((Packet)new CPacketHeldItemChange(n2));
        Scaffold.mc.player.inventory.currentItem = n2;
        Scaffold.mc.playerController.updateController();
        if (n4 != 0) {
            Scaffold.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Scaffold.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
        }
    }
    
    private static class BlockData
    {
        public BlockPos position;
        public EnumFacing face;
        
        public BlockData(final BlockPos blockPos, final EnumFacing enumFacing) {
            this.position = blockPos;
            this.face = enumFacing;
        }
    }
    
    public enum Mode
    {
        New, 
        Old;
    }
}
