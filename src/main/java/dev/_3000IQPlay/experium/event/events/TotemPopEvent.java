// 
// Decompiled by Procyon v0.5.36
// 

package dev._3000IQPlay.experium.event.events;

import net.minecraft.entity.player.EntityPlayer;
import dev._3000IQPlay.experium.event.EventStage;

public class TotemPopEvent extends EventStage
{
    private final EntityPlayer entity;
    
    public TotemPopEvent(final EntityPlayer entity) {
        this.entity = entity;
    }
    
    public EntityPlayer getEntity() {
        return this.entity;
    }
}
