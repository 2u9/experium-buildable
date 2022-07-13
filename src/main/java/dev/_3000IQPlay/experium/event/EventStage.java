// 
// Decompiled by Procyon v0.5.36
// 

package dev._3000IQPlay.experium.event;

import net.minecraftforge.fml.common.eventhandler.Event;

public class EventStage extends Event
{
    private int stage;
    
    public EventStage() {
    }
    
    public EventStage(final int stage) {
        this.stage = stage;
    }
    
    public int getStage() {
        return this.stage;
    }
    
    public void setStage(final int stage) {
        this.stage = stage;
    }
}
