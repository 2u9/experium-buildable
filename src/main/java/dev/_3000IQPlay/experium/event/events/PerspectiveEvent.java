// 
// Decompiled by Procyon v0.5.36
// 

package dev._3000IQPlay.experium.event.events;

import dev._3000IQPlay.experium.event.EventStage;

public class PerspectiveEvent extends EventStage
{
    private float aspect;
    
    public PerspectiveEvent(final float f) {
        this.aspect = f;
    }
    
    public float getAspect() {
        return this.aspect;
    }
    
    public void setAspect(final float f) {
        this.aspect = f;
    }
}
