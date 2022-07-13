//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Luni\Documents\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Items
 *  net.minecraft.item.ItemChorusFruit
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketPlayer
 *  net.minecraft.network.play.client.CPacketPlayerTryUseItem
 *  net.minecraft.util.EnumHand
 *  net.minecraft.world.World
 *  net.minecraftforge.event.entity.player.PlayerInteractEvent$RightClickItem
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  org.lwjgl.input.Keyboard
 */
package dev._3000IQPlay.experium.features.modules.misc;

import dev._3000IQPlay.experium.features.modules.Module;
import dev._3000IQPlay.experium.features.setting.Bind;
import dev._3000IQPlay.experium.features.setting.Setting;
import dev._3000IQPlay.experium.util.InventoryUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemChorusFruit;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

public class KeyChorus
extends Module {
    private boolean clicked = false;
    private Setting<Bind> keyBind = this.register(new Setting<Bind>("Key", new Bind(-1)));

    public KeyChorus() {
        super("KeyChorus", "When the key gets pressed you will eat chorus", Module.Category.MISC, false, false, false);
    }

    @Override
    public void onEnable() {
        if (KeyChorus.fullNullCheck()) {
            this.disable();
        }
    }

    @Override
    public void onTick() {
        if (Keyboard.getEventKey() == this.keyBind.getValue().getKey()) {
            if (!this.clicked) {
                this.eatChorus();
            }
        } else {
            this.clicked = false;
        }
    }

    private void eatChorus() {
        int chorusSlot = InventoryUtil.findHotbarBlock(ItemChorusFruit.class);
        boolean offhand = KeyChorus.mc.player.getHeldItemOffhand().getItem() == Items.CHORUS_FRUIT;
        boolean bl = offhand;
        if (chorusSlot != -1 || offhand) {
            int oldslot = KeyChorus.mc.player.inventory.currentItem;
            if (!offhand) {
                InventoryUtil.switchToHotbarSlot(chorusSlot, true);
            }
            KeyChorus.mc.player.connection.sendPacket((Packet)new CPacketPlayer());
            KeyChorus.mc.player.connection.sendPacket((Packet)new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
            KeyChorus.mc.playerController.processRightClick((EntityPlayer)KeyChorus.mc.player, (World)KeyChorus.mc.world, offhand ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND);
            if (!offhand) {
                InventoryUtil.switchToHotbarSlot(oldslot, false);
            }
            this.clicked = true;
        }
    }

    @SubscribeEvent
    public void onPlayerRightClick(PlayerInteractEvent.RightClickItem event) {
        if (event.getItemStack().getItem().equals((Object)Items.CHORUS_FRUIT)) {
            event.getItemStack().getItem().onItemUseFinish(event.getItemStack(), event.getWorld(), (EntityLivingBase)event.getEntityPlayer());
        }
    }
}

