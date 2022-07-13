//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Luni\Documents\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev._3000IQPlay.experium.features.modules.player;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import dev._3000IQPlay.experium.mixin.mixins.accessors.ICPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayer;
import dev._3000IQPlay.experium.event.events.PacketEvent;
import net.minecraft.network.Packet;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.CPacketEntityAction;
import dev._3000IQPlay.experium.features.setting.Setting;
import dev._3000IQPlay.experium.features.modules.Module;

public class AntiHunger extends Module
{
    public Setting<Boolean> sprint;
    public Setting<Boolean> noGround;
    private boolean isOnGround;
    
    public AntiHunger() {
        super("AntiHunger", "Prevents hunger loss", Category.PLAYER, true, false, false);
        this.sprint = (Setting<Boolean>)this.register(new Setting("Sprint", (T)true));
        this.noGround = (Setting<Boolean>)this.register(new Setting("Ground", (T)true));
        this.isOnGround = false;
    }
    
    @Override
    public void onEnable() {
        if (this.sprint.getValue() && AntiHunger.mc.player != null) {
            AntiHunger.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)AntiHunger.mc.player, CPacketEntityAction.Action.STOP_SPRINTING));
        }
    }
    
    @Override
    public void onDisable() {
        if (this.sprint.getValue() && AntiHunger.mc.player != null && AntiHunger.mc.player.isSprinting()) {
            AntiHunger.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)AntiHunger.mc.player, CPacketEntityAction.Action.START_SPRINTING));
        }
    }
    
    @SubscribeEvent
    public void onPacketSend(final PacketEvent.Send event) {
        if (event.getPacket() instanceof CPacketEntityAction) {
            final CPacketEntityAction action = event.getPacket();
            if (this.sprint.getValue() && (action.getAction() == CPacketEntityAction.Action.START_SPRINTING || action.getAction() == CPacketEntityAction.Action.STOP_SPRINTING)) {
                event.setCanceled(true);
            }
        }
        if (event.getPacket() instanceof CPacketPlayer) {
            final CPacketPlayer player = event.getPacket();
            final boolean ground = AntiHunger.mc.player.onGround;
            if (this.noGround.getValue() && this.isOnGround && ground && player.getY(0.0) == (((ICPacketPlayer)player).isMoving() ? AntiHunger.mc.player.posY : 0.0)) {
                ((ICPacketPlayer)player).setOnGround(false);
            }
            this.isOnGround = ground;
        }
    }
}
