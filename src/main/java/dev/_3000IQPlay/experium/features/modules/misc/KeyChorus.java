//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Luni\Documents\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev._3000IQPlay.experium.features.modules.misc;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraft.world.World;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.EnumHand;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.init.Items;
import dev._3000IQPlay.experium.util.InventoryUtil;
import net.minecraft.item.ItemChorusFruit;
import org.lwjgl.input.Keyboard;
import dev._3000IQPlay.experium.features.setting.Bind;
import dev._3000IQPlay.experium.features.setting.Setting;
import dev._3000IQPlay.experium.features.modules.Module;

public class KeyChorus extends Module
{
    private boolean clicked;
    private Setting<Bind> keyBind;
    
    public KeyChorus() {
        super("KeyChorus", "When the key gets pressed you will eat chorus", Category.MISC, false, false, false);
        this.clicked = false;
        this.keyBind = (Setting<Bind>)this.register(new Setting("Key", (T)new Bind(-1)));
    }
    
    @Override
    public void onEnable() {
        if (fullNullCheck()) {
            this.disable();
        }
    }
    
    @Override
    public void onTick() {
        if (Keyboard.getEventKey() == this.keyBind.getValue().getKey()) {
            if (!this.clicked) {
                this.eatChorus();
            }
        }
        else {
            this.clicked = false;
        }
    }
    
    private void eatChorus() {
        final int chorusSlot = InventoryUtil.findHotbarBlock(ItemChorusFruit.class);
        final boolean bl;
        final boolean offhand = bl = (KeyChorus.mc.player.getHeldItemOffhand().getItem() == Items.CHORUS_FRUIT);
        if (chorusSlot != -1 || offhand) {
            final int oldslot = KeyChorus.mc.player.inventory.currentItem;
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
    public void onPlayerRightClick(final PlayerInteractEvent.RightClickItem event) {
        if (event.getItemStack().getItem().equals(Items.CHORUS_FRUIT)) {
            event.getItemStack().getItem().onItemUseFinish(event.getItemStack(), event.getWorld(), (EntityLivingBase)event.getEntityPlayer());
        }
    }
}
