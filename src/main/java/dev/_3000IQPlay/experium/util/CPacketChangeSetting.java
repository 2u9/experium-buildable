//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Luni\Documents\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.Packet
 *  net.minecraft.network.PacketBuffer
 *  net.minecraft.network.play.INetHandlerPlayServer
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.fml.common.eventhandler.Event
 */
package dev._3000IQPlay.experium.util;

import dev._3000IQPlay.experium.Experium;
import dev._3000IQPlay.experium.event.events.ValueChangeEvent;
import dev._3000IQPlay.experium.features.modules.Module;
import dev._3000IQPlay.experium.features.setting.Setting;
import java.io.IOException;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.INetHandlerPlayServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Event;

public class CPacketChangeSetting
implements Packet<INetHandlerPlayServer> {
    public String setting;

    public CPacketChangeSetting(String module, String setting, String value) {
        this.setting = setting + "-" + module + "-" + value;
    }

    public CPacketChangeSetting(Module module, Setting setting, String value) {
        this.setting = setting.getName() + "-" + module.getName() + "-" + value;
    }

    public void readPacketData(PacketBuffer buf) throws IOException {
        this.setting = buf.readString(256);
    }

    public void writePacketData(PacketBuffer buf) throws IOException {
        buf.writeString(this.setting);
    }

    public void processPacket(INetHandlerPlayServer handler) {
        Module module = Experium.moduleManager.getModuleByName(this.setting.split("-")[1]);
        Setting setting1 = module.getSettingByName(this.setting.split("-")[0]);
        MinecraftForge.EVENT_BUS.post((Event)new ValueChangeEvent(setting1, this.setting.split("-")[2]));
    }
}

