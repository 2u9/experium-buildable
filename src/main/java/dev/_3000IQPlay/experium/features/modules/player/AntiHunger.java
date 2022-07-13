//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Luni\Documents\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketEntityAction
 *  net.minecraft.network.play.client.CPacketEntityAction$Action
 *  net.minecraft.network.play.client.CPacketPlayer
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package dev._3000IQPlay.experium.features.modules.player;

import dev._3000IQPlay.experium.event.events.PacketEvent;
import dev._3000IQPlay.experium.features.modules.Module;
import dev._3000IQPlay.experium.features.setting.Setting;
import dev._3000IQPlay.experium.mixin.mixins.accessors.ICPacketPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AntiHunger
extends Module {
    public Setting<Boolean> sprint = this.register(new Setting<Boolean>("Sprint", true));
    public Setting<Boolean> noGround = this.register(new Setting<Boolean>("Ground", true));
    private boolean isOnGround = false;

    public AntiHunger() {
        super("AntiHunger", "Prevents hunger loss", Module.Category.PLAYER, true, false, false);
    }

    @Override
    public void onEnable() {
        if (this.sprint.getValue().booleanValue() && AntiHunger.mc.player != null) {
            AntiHunger.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)AntiHunger.mc.player, CPacketEntityAction.Action.STOP_SPRINTING));
        }
    }

    @Override
    public void onDisable() {
        if (this.sprint.getValue().booleanValue() && AntiHunger.mc.player != null && AntiHunger.mc.player.isSprinting()) {
            AntiHunger.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)AntiHunger.mc.player, CPacketEntityAction.Action.START_SPRINTING));
        }
    }

    @SubscribeEvent
    public void onPacketSend(PacketEvent.Send event) {
        if (event.getPacket() instanceof CPacketEntityAction) {
            CPacketEntityAction action = (CPacketEntityAction)event.getPacket();
            if (this.sprint.getValue().booleanValue() && (action.getAction() == CPacketEntityAction.Action.START_SPRINTING || action.getAction() == CPacketEntityAction.Action.STOP_SPRINTING)) {
                event.setCanceled(true);
            }
        }
        if (event.getPacket() instanceof CPacketPlayer) {
            CPacketPlayer player = (CPacketPlayer)event.getPacket();
            boolean ground = AntiHunger.mc.player.onGround;
            if (this.noGround.getValue().booleanValue() && this.isOnGround && ground && player.getY(0.0) == (!((ICPacketPlayer)player).isMoving() ? 0.0 : AntiHunger.mc.player.posY)) {
                ((ICPacketPlayer)player).setOnGround(false);
            }
            this.isOnGround = ground;
        }
    }
}

