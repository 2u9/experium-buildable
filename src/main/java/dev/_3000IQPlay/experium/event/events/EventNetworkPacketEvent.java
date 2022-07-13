/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.network.Packet
 */
package dev._3000IQPlay.experium.event.events;

import dev._3000IQPlay.experium.event.EventStage;
import net.minecraft.network.Packet;

public class EventNetworkPacketEvent
extends EventStage {
    public Packet m_Packet;

    public EventNetworkPacketEvent(Packet p_Packet) {
        this.m_Packet = p_Packet;
    }

    public Packet GetPacket() {
        return this.m_Packet;
    }

    public Packet getPacket() {
        return this.m_Packet;
    }
}

