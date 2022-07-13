//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Luni\Documents\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev._3000IQPlay.experium.features.modules.combat;

import java.util.Iterator;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.util.EnumHand;
import dev._3000IQPlay.experium.features.command.Command;
import net.minecraft.block.BlockObsidian;
import net.minecraft.block.BlockEnderChest;
import dev._3000IQPlay.experium.util.BurrowUtil;
import net.minecraft.block.BlockAnvil;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import dev._3000IQPlay.experium.features.setting.Setting;
import dev._3000IQPlay.experium.features.modules.Module;

public class Burrow extends Module
{
    private final Setting<Integer> offset;
    private final Setting<Boolean> ground;
    private final Setting<Boolean> rotate;
    private final Setting<Boolean> center;
    private final Setting<Boolean> echest;
    private final Setting<Boolean> anvil;
    private BlockPos originalPos;
    private int oldSlot;
    
    public Burrow() {
        super("Burrow", "TPs you into a block", Category.COMBAT, true, false, false);
        this.offset = (Setting<Integer>)this.register(new Setting("Offset", (T)3, (T)(-5), (T)5));
        this.ground = (Setting<Boolean>)this.register(new Setting("GroundCheck", (T)true));
        this.rotate = (Setting<Boolean>)this.register(new Setting("Rotate", (T)false));
        this.center = (Setting<Boolean>)this.register(new Setting("Center", (T)true));
        this.echest = (Setting<Boolean>)this.register(new Setting("UseEchest", (T)false));
        this.anvil = (Setting<Boolean>)this.register(new Setting("UseAnvil", (T)false));
        this.oldSlot = -1;
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
        this.originalPos = new BlockPos(Burrow.mc.player.posX, Burrow.mc.player.posY, Burrow.mc.player.posZ);
        if (Burrow.mc.world.getBlockState(new BlockPos(Burrow.mc.player.posX, Burrow.mc.player.posY, Burrow.mc.player.posZ)).getBlock().equals(Blocks.OBSIDIAN) || this.intersectsWithEntity(this.originalPos)) {
            this.toggle();
            return;
        }
        if (this.center.getValue()) {
            double x = Burrow.mc.player.posX - Math.floor(Burrow.mc.player.posX);
            double z = Burrow.mc.player.posZ - Math.floor(Burrow.mc.player.posZ);
            if (x <= 0.3 || x >= 0.7) {
                double n = 0.0;
                if (x <= 0.5) {
                    n = 0.31;
                }
                x = n;
            }
            if (z < 0.3 || z > 0.7) {
                z = ((z > 0.5) ? 0.69 : 0.31);
            }
            Burrow.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Math.floor(Burrow.mc.player.posX) + x, Burrow.mc.player.posY, Math.floor(Burrow.mc.player.posZ) + z, Burrow.mc.player.onGround));
            Burrow.mc.player.setPosition(Math.floor(Burrow.mc.player.posX) + x, Burrow.mc.player.posY, Math.floor(Burrow.mc.player.posZ) + z);
        }
        this.oldSlot = Burrow.mc.player.inventory.currentItem;
    }
    
    @Override
    public void onUpdate() {
        if (this.ground.getValue() && !Burrow.mc.player.onGround) {
            this.toggle();
            return;
        }
        Label_0151: {
            if (!this.anvil.getValue() || BurrowUtil.findHotbarBlock(BlockAnvil.class) == -1) {
                Label_0141: {
                    if (this.echest.getValue()) {
                        if (BurrowUtil.findHotbarBlock(BlockEnderChest.class) == -1) {
                            break Label_0141;
                        }
                    }
                    else if (BurrowUtil.findHotbarBlock(BlockObsidian.class) == -1) {
                        break Label_0141;
                    }
                    BurrowUtil.switchToSlot(((boolean)this.echest.getValue()) ? BurrowUtil.findHotbarBlock(BlockEnderChest.class) : BurrowUtil.findHotbarBlock(BlockObsidian.class));
                    break Label_0151;
                }
                Command.sendMessage("Unable to place burrow block (anvil, ec or oby)");
                this.toggle();
                return;
            }
            BurrowUtil.switchToSlot(BurrowUtil.findHotbarBlock(BlockAnvil.class));
        }
        Burrow.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Burrow.mc.player.posX, Burrow.mc.player.posY + 0.41999998688698, Burrow.mc.player.posZ, true));
        Burrow.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Burrow.mc.player.posX, Burrow.mc.player.posY + 0.7531999805211997, Burrow.mc.player.posZ, true));
        Burrow.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Burrow.mc.player.posX, Burrow.mc.player.posY + 1.00133597911214, Burrow.mc.player.posZ, true));
        Burrow.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Burrow.mc.player.posX, Burrow.mc.player.posY + 1.16610926093821, Burrow.mc.player.posZ, true));
        BurrowUtil.placeBlock(this.originalPos, EnumHand.MAIN_HAND, this.rotate.getValue(), true, false);
        Burrow.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(Burrow.mc.player.posX, Burrow.mc.player.posY + this.offset.getValue(), Burrow.mc.player.posZ, false));
        Burrow.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Burrow.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
        Burrow.mc.player.setSneaking(false);
        BurrowUtil.switchToSlot(this.oldSlot);
        this.toggle();
    }
    
    private boolean intersectsWithEntity(final BlockPos pos) {
        for (final Entity entity : Burrow.mc.world.loadedEntityList) {
            if (!entity.equals((Object)Burrow.mc.player) && !(entity instanceof EntityItem)) {
                if (!new AxisAlignedBB(pos).intersects(entity.getEntityBoundingBox())) {
                    continue;
                }
                return true;
            }
        }
        return false;
    }
}
