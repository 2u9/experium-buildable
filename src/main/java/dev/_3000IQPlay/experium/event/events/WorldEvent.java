/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.multiplayer.WorldClient
 */
package dev._3000IQPlay.experium.event.events;

import dev._3000IQPlay.experium.event.EventStage;
import net.minecraft.client.multiplayer.WorldClient;

public class WorldEvent
extends EventStage {
    private final WorldClient world;

    public WorldEvent(WorldClient worldIn) {
        this.world = worldIn;
    }

    public WorldClient getWorld() {
        return this.world;
    }
}

