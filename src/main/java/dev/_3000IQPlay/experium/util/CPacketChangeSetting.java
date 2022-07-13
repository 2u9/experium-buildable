//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Luni\Documents\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev._3000IQPlay.experium.util;

import net.minecraft.network.INetHandler;
import net.minecraftforge.fml.common.eventhandler.Event;
import dev._3000IQPlay.experium.event.events.ValueChangeEvent;
import net.minecraftforge.common.MinecraftForge;
import dev._3000IQPlay.experium.Experium;
import java.io.IOException;
import net.minecraft.network.PacketBuffer;
import dev._3000IQPlay.experium.features.setting.Setting;
import dev._3000IQPlay.experium.features.modules.Module;
import net.minecraft.network.play.INetHandlerPlayServer;
import net.minecraft.network.Packet;

public class CPacketChangeSetting implements Packet<INetHandlerPlayServer>
{
    public String setting;
    
    public CPacketChangeSetting(final String module, final String setting, final String value) {
        this.setting = setting + "-" + module + "-" + value;
    }
    
    public CPacketChangeSetting(final Module module, final Setting setting, final String value) {
        this.setting = setting.getName() + "-" + module.getName() + "-" + value;
    }
    
    public void readPacketData(final PacketBuffer buf) throws IOException {
        this.setting = buf.readString(256);
    }
    
    public void writePacketData(final PacketBuffer buf) throws IOException {
        buf.writeString(this.setting);
    }
    
    public void processPacket(final INetHandlerPlayServer handler) {
        final Module module = Experium.moduleManager.getModuleByName(this.setting.split("-")[1]);
        final Setting setting1 = module.getSettingByName(this.setting.split("-")[0]);
        MinecraftForge.EVENT_BUS.post((Event)new ValueChangeEvent(setting1, this.setting.split("-")[2]));
    }
}
