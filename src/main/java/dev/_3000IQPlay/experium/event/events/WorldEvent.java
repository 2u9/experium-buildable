// 
// Decompiled by Procyon v0.5.36
// 

package dev._3000IQPlay.experium.event.events;

import net.minecraft.client.multiplayer.WorldClient;
import dev._3000IQPlay.experium.event.EventStage;

public class WorldEvent extends EventStage
{
    private final WorldClient world;
    
    public WorldEvent(final WorldClient worldIn) {
        this.world = worldIn;
    }
    
    public WorldClient getWorld() {
        return this.world;
    }
}
