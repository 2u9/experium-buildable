//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Luni\Documents\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev._3000IQPlay.experium.features.modules.misc;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import dev._3000IQPlay.experium.util.MathUtil;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.server.SPacketBlockChange;
import dev._3000IQPlay.experium.event.events.PacketEvent;
import net.minecraft.network.Packet;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.network.play.server.SPacketDisconnect;
import net.minecraft.util.text.TextComponentString;
import dev._3000IQPlay.experium.Experium;
import dev._3000IQPlay.experium.features.Feature;
import dev._3000IQPlay.experium.features.setting.Setting;
import dev._3000IQPlay.experium.features.modules.Module;

public class AutoLog extends Module
{
    private static AutoLog INSTANCE;
    private final Setting<Float> health;
    private final Setting<Boolean> bed;
    private final Setting<Float> range;
    private final Setting<Boolean> logout;
    
    public AutoLog() {
        super("AutoLog", "Logs when in danger.", Category.MISC, false, false, false);
        this.health = (Setting<Float>)this.register(new Setting("Health", (T)16.0f, (T)0.1f, (T)36.0f));
        this.bed = (Setting<Boolean>)this.register(new Setting("Beds", (T)true));
        this.range = (Setting<Float>)this.register(new Setting("BedRange", (T)6.0f, (T)0.1f, (T)36.0f, v -> this.bed.getValue()));
        this.logout = (Setting<Boolean>)this.register(new Setting("LogoutOff", (T)true));
        this.setInstance();
    }
    
    public static AutoLog getInstance() {
        if (AutoLog.INSTANCE == null) {
            AutoLog.INSTANCE = new AutoLog();
        }
        return AutoLog.INSTANCE;
    }
    
    private void setInstance() {
        AutoLog.INSTANCE = this;
    }
    
    @Override
    public void onTick() {
        if (!Feature.nullCheck() && AutoLog.mc.player.getHealth() <= this.health.getValue()) {
            Experium.moduleManager.disableModule("AutoReconnect");
            AutoLog.mc.player.connection.sendPacket((Packet)new SPacketDisconnect((ITextComponent)new TextComponentString("AutoLogged")));
            if (this.logout.getValue()) {
                this.disable();
            }
        }
    }
    
    @SubscribeEvent
    public void onReceivePacket(final PacketEvent.Receive event) {
        final SPacketBlockChange packet;
        if (event.getPacket() instanceof SPacketBlockChange && this.bed.getValue() && (packet = event.getPacket()).getBlockState().getBlock() == Blocks.BED && AutoLog.mc.player.getDistanceSqToCenter(packet.getBlockPosition()) <= MathUtil.square(this.range.getValue())) {
            Experium.moduleManager.disableModule("AutoReconnect");
            AutoLog.mc.player.connection.sendPacket((Packet)new SPacketDisconnect((ITextComponent)new TextComponentString("AutoLogged")));
            if (this.logout.getValue()) {
                this.disable();
            }
        }
    }
    
    static {
        AutoLog.INSTANCE = new AutoLog();
    }
}
