//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Luni\Documents\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev._3000IQPlay.experium.features.modules.player;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumFacing;
import net.minecraft.init.Items;
import net.minecraft.item.ItemEndCrystal;
import net.minecraft.item.ItemMinecart;
import net.minecraft.block.BlockObsidian;
import dev._3000IQPlay.experium.util.InventoryUtil;
import net.minecraft.item.ItemExpBottle;
import net.minecraft.util.math.BlockPos;
import dev._3000IQPlay.experium.features.setting.Setting;
import dev._3000IQPlay.experium.features.modules.Module;

public class FastUse extends Module
{
    private Setting<Boolean> all;
    private Setting<Boolean> obby;
    private Setting<Boolean> crystals;
    private Setting<Boolean> exp;
    private Setting<Boolean> minecart;
    private Setting<Boolean> packetCrystal;
    private Setting<Boolean> strict;
    private BlockPos mousePos;
    
    public FastUse() {
        super("FastUse", "Allows you to use items faster", Category.PLAYER, true, false, false);
        this.all = (Setting<Boolean>)this.register(new Setting("All", (T)false));
        this.obby = (Setting<Boolean>)this.register(new Setting("Obsidian", (T)false, v -> !this.all.getValue()));
        this.crystals = (Setting<Boolean>)this.register(new Setting("Crystals", (T)false, v -> !this.all.getValue()));
        this.exp = (Setting<Boolean>)this.register(new Setting("Experience", (T)false, v -> !this.all.getValue()));
        this.minecart = (Setting<Boolean>)this.register(new Setting("Minecarts", (T)false, v -> !this.all.getValue()));
        this.packetCrystal = (Setting<Boolean>)this.register(new Setting("PacketCrystal", (T)false));
        this.strict = (Setting<Boolean>)this.register(new Setting("Strict", (T)false));
        this.mousePos = null;
    }
    
    @Override
    public void onUpdate() {
        if (this.strict.getValue() && FastUse.mc.player.ticksExisted % 2 == 0) {
            return;
        }
        if (fullNullCheck()) {
            return;
        }
        if (InventoryUtil.holdingItem(ItemExpBottle.class) && this.exp.getValue()) {
            FastUse.mc.rightClickDelayTimer = 0;
        }
        if (InventoryUtil.holdingItem(BlockObsidian.class) && this.obby.getValue()) {
            FastUse.mc.rightClickDelayTimer = 0;
        }
        if (InventoryUtil.holdingItem(ItemMinecart.class) && this.minecart.getValue()) {
            FastUse.mc.rightClickDelayTimer = 0;
        }
        if (this.all.getValue()) {
            FastUse.mc.rightClickDelayTimer = 0;
        }
        if (InventoryUtil.holdingItem(ItemEndCrystal.class) && (this.crystals.getValue() || this.all.getValue())) {
            FastUse.mc.rightClickDelayTimer = 0;
        }
        if (this.packetCrystal.getValue() && FastUse.mc.gameSettings.keyBindUseItem.isKeyDown()) {
            final boolean bl;
            final boolean offhand = bl = (FastUse.mc.player.getHeldItemOffhand().getItem() == Items.END_CRYSTAL);
            if (offhand || FastUse.mc.player.getHeldItemMainhand().getItem() == Items.END_CRYSTAL) {
                final RayTraceResult result = FastUse.mc.objectMouseOver;
                if (result == null) {
                    return;
                }
                switch (result.typeOfHit) {
                    case MISS: {
                        this.mousePos = null;
                        break;
                    }
                    case BLOCK: {
                        this.mousePos = FastUse.mc.objectMouseOver.getBlockPos();
                        break;
                    }
                    case ENTITY: {
                        final Entity entity;
                        if (this.mousePos == null || (entity = result.entityHit) == null) {
                            break;
                        }
                        if (!this.mousePos.equals((Object)new BlockPos(entity.posX, entity.posY - 1.0, entity.posZ))) {
                            break;
                        }
                        FastUse.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(this.mousePos, EnumFacing.DOWN, offhand ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND, 0.0f, 0.0f, 0.0f));
                        break;
                    }
                }
            }
        }
    }
}
