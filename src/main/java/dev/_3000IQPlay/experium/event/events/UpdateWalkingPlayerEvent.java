// 
// Decompiled by Procyon v0.5.36
// 

package dev._3000IQPlay.experium.event.events;

import net.minecraftforge.fml.common.eventhandler.Cancelable;
import dev._3000IQPlay.experium.event.EventStage;

@Cancelable
public class UpdateWalkingPlayerEvent extends EventStage
{
    public UpdateWalkingPlayerEvent(final int stage) {
        super(stage);
    }
}
