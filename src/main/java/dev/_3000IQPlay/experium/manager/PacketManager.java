//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Luni\Documents\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev._3000IQPlay.experium.manager;

import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import dev._3000IQPlay.experium.event.events.PacketEvent;
import java.util.ArrayList;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import dev._3000IQPlay.experium.util.Timer;
import net.minecraft.network.play.server.SPacketExplosion;
import net.minecraft.network.Packet;
import java.util.List;
import dev._3000IQPlay.experium.features.Feature;

public class PacketManager extends Feature
{
    private final List<Packet<?>> noEventPackets;
    SPacketExplosion pExplosion;
    Timer timerExplosion;
    boolean caughtPExplosion;
    SPacketPlayerPosLook pPlayerPosLook;
    Timer timerPlayerPosLook;
    boolean caughtPlayerPosLook;
    
    public PacketManager() {
        this.noEventPackets = new ArrayList<Packet<?>>();
        this.pExplosion = null;
        this.timerExplosion = new Timer();
        this.caughtPExplosion = false;
        this.pPlayerPosLook = null;
        this.timerPlayerPosLook = new Timer();
        this.caughtPlayerPosLook = false;
    }
    
    public void sendPacketNoEvent(final Packet<?> packet) {
        if (packet != null && !Feature.nullCheck()) {
            this.noEventPackets.add(packet);
            PacketManager.mc.player.connection.sendPacket((Packet)packet);
        }
    }
    
    public boolean shouldSendPacket(final Packet<?> packet) {
        if (this.noEventPackets.contains(packet)) {
            this.noEventPackets.remove(packet);
            return false;
        }
        return true;
    }
    
    @SubscribeEvent
    public void onPacketReceive(final PacketEvent.Receive e) {
        if (fullNullCheck()) {
            return;
        }
        if (e.getPacket() instanceof SPacketPlayerPosLook) {
            this.pPlayerPosLook = e.getPacket();
            this.timerPlayerPosLook.reset();
            this.caughtPlayerPosLook = true;
        }
        if (e.getPacket() instanceof SPacketExplosion) {
            this.pExplosion = e.getPacket();
            this.timerExplosion.reset();
            this.caughtPExplosion = true;
        }
    }
    
    @SubscribeEvent
    public void onUpdate(final TickEvent.ClientTickEvent e) {
        if (fullNullCheck()) {
            return;
        }
        if (this.timerExplosion.passedMs(250L)) {
            this.pExplosion = null;
            this.caughtPExplosion = false;
        }
        if (this.timerPlayerPosLook.passedMs(250L)) {
            this.pPlayerPosLook = null;
            this.caughtPlayerPosLook = false;
        }
    }
    
    public boolean caughtPlayerPosLook() {
        return this.caughtPlayerPosLook;
    }
    
    public boolean caughtPExplosion() {
        return this.caughtPExplosion;
    }
    
    public SPacketPlayerPosLook pPlayerPosLook() {
        return this.pPlayerPosLook;
    }
    
    public SPacketExplosion pExplosion() {
        return this.pExplosion;
    }
    
    public Timer timerExplosion() {
        return this.timerExplosion;
    }
    
    public Timer timerPlayerPosLook() {
        return this.timerPlayerPosLook;
    }
}
