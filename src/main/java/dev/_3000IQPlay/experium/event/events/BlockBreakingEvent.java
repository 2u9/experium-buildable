// 
// Decompiled by Procyon v0.5.36
// 

package dev._3000IQPlay.experium.event.events;

import net.minecraft.util.math.BlockPos;
import dev._3000IQPlay.experium.event.EventStage;

public class BlockBreakingEvent extends EventStage
{
    public BlockPos pos;
    public int breakingID;
    public int breakStage;
    
    public BlockBreakingEvent(final BlockPos pos, final int breakingID, final int breakStage) {
        this.pos = pos;
        this.breakingID = breakingID;
        this.breakStage = breakStage;
    }
}
