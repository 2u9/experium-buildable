//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Luni\Documents\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev._3000IQPlay.experium.features.modules.client;

import java.util.Iterator;
import dev._3000IQPlay.experium.mixin.mixins.accessors.IC00Handshake;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.network.play.server.SPacketKeepAlive;
import dev._3000IQPlay.experium.util.TextUtil;
import net.minecraft.network.play.client.CPacketKeepAlive;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketChatMessage;
import dev._3000IQPlay.experium.util.Util;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.network.play.server.SPacketChat;
import dev._3000IQPlay.experium.event.events.PacketEvent;
import java.util.ArrayList;
import dev._3000IQPlay.experium.features.setting.Setting;
import java.util.List;
import dev._3000IQPlay.experium.util.Timer;
import java.util.concurrent.atomic.AtomicBoolean;
import dev._3000IQPlay.experium.features.modules.Module;

public class PingBypass extends Module
{
    private static PingBypass instance;
    private final AtomicBoolean connected;
    private final Timer pingTimer;
    private final List<Long> pingList;
    public Setting<String> ip;
    public Setting<String> port;
    public Setting<String> serverIP;
    public Setting<Boolean> noFML;
    public Setting<Boolean> getName;
    public Setting<Boolean> average;
    public Setting<Boolean> clear;
    public Setting<Boolean> oneWay;
    public Setting<Integer> delay;
    private long currentPing;
    private long serverPing;
    private StringBuffer name;
    private long averagePing;
    private String serverPrefix;
    
    public PingBypass() {
        super("PingBypass", "Manages Experium`s internal Server", Category.CLIENT, false, false, true);
        this.connected = new AtomicBoolean(false);
        this.pingTimer = new Timer();
        this.pingList = new ArrayList<Long>();
        this.ip = (Setting<String>)this.register(new Setting("ExperiumIP", (T)"0.0.0.0.0"));
        this.port = (Setting<String>)this.register(new Setting<String>("Port", "0").setRenderName(true));
        this.serverIP = (Setting<String>)this.register(new Setting("ServerIP", (T)"crystalpvp.cc"));
        this.noFML = (Setting<Boolean>)this.register(new Setting("RemoveFML", (T)false));
        this.getName = (Setting<Boolean>)this.register(new Setting("GetName", (T)false));
        this.average = (Setting<Boolean>)this.register(new Setting("Average", (T)false));
        this.clear = (Setting<Boolean>)this.register(new Setting("ClearPings", (T)false));
        this.oneWay = (Setting<Boolean>)this.register(new Setting("OneWay", (T)false));
        this.delay = (Setting<Integer>)this.register(new Setting("KeepAlives", (T)10, (T)1, (T)50));
        this.currentPing = 0L;
        this.serverPing = 0L;
        this.name = null;
        this.averagePing = 0L;
        this.serverPrefix = "idk";
        PingBypass.instance = this;
    }
    
    public static PingBypass getInstance() {
        if (PingBypass.instance == null) {
            PingBypass.instance = new PingBypass();
        }
        return PingBypass.instance;
    }
    
    public String getPlayerName() {
        if (this.name == null) {
            return null;
        }
        return this.name.toString();
    }
    
    public String getServerPrefix() {
        return this.serverPrefix;
    }
    
    @Override
    public void onLogout() {
        this.averagePing = 0L;
        this.currentPing = 0L;
        this.serverPing = 0L;
        this.pingList.clear();
        this.connected.set(false);
        this.name = null;
    }
    
    @SubscribeEvent
    public void onReceivePacket(final PacketEvent.Receive event) {
        if (event.getPacket() instanceof SPacketChat) {
            final SPacketChat packet = event.getPacket();
            if (packet.chatComponent.getUnformattedText().startsWith("@Clientprefix")) {
                final String prefix = this.serverPrefix = packet.chatComponent.getFormattedText().replace("@Clientprefix", "");
            }
        }
    }
    
    @Override
    public void onTick() {
        if (Util.mc.getConnection() != null && this.isConnected()) {
            if (this.getName.getValue()) {
                Util.mc.getConnection().sendPacket((Packet)new CPacketChatMessage("@Servername"));
                this.getName.setValue(false);
            }
            if (this.serverPrefix.equalsIgnoreCase("idk") && PingBypass.mc.world != null) {
                Util.mc.getConnection().sendPacket((Packet)new CPacketChatMessage("@Servergetprefix"));
            }
            if (this.pingTimer.passedMs(this.delay.getValue() * 1000)) {
                Util.mc.getConnection().sendPacket((Packet)new CPacketKeepAlive(100L));
                this.pingTimer.reset();
            }
            if (this.clear.getValue()) {
                this.pingList.clear();
            }
        }
    }
    
    @SubscribeEvent
    public void onPacketReceive(final PacketEvent.Receive event) {
        if (event.getPacket() instanceof SPacketChat) {
            final SPacketChat packetChat = event.getPacket();
            if (packetChat.getChatComponent().getFormattedText().startsWith("@Client")) {
                this.name = new StringBuffer(TextUtil.stripColor(packetChat.getChatComponent().getFormattedText().replace("@Client", "")));
                event.setCanceled(true);
            }
        }
        else {
            final SPacketKeepAlive alive;
            if (event.getPacket() instanceof SPacketKeepAlive && (alive = event.getPacket()).getId() > 0L && alive.getId() < 1000L) {
                this.serverPing = alive.getId();
                this.currentPing = (this.oneWay.getValue() ? (this.pingTimer.getPassedTimeMs() / 2L) : this.pingTimer.getPassedTimeMs());
                this.pingList.add(this.currentPing);
                this.averagePing = this.getAveragePing();
            }
        }
    }
    
    @SubscribeEvent
    public void onPacketSend(final PacketEvent.Send event) {
        final IC00Handshake packet;
        final String ip;
        if (event.getPacket() instanceof C00Handshake && (ip = (packet = event.getPacket()).getIp()).equals(this.ip.getValue())) {
            packet.setIp(this.serverIP.getValue());
            System.out.println(packet.getIp());
            this.connected.set(true);
        }
    }
    
    @Override
    public String getDisplayInfo() {
        return this.averagePing + "ms";
    }
    
    private long getAveragePing() {
        if (!this.average.getValue() || this.pingList.isEmpty()) {
            return this.currentPing;
        }
        int full = 0;
        for (final long i : this.pingList) {
            full += (int)i;
        }
        return full / this.pingList.size();
    }
    
    public boolean isConnected() {
        return this.connected.get();
    }
    
    public int getPort() {
        int result;
        try {
            result = Integer.parseInt(this.port.getValue());
        }
        catch (NumberFormatException e) {
            return -1;
        }
        return result;
    }
    
    public long getServerPing() {
        return this.serverPing;
    }
}
