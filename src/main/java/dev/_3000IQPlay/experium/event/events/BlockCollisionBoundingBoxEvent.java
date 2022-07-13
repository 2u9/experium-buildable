// 
// Decompiled by Procyon v0.5.36
// 

package dev._3000IQPlay.experium.event.events;

import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import dev._3000IQPlay.experium.event.EventStage;

public class BlockCollisionBoundingBoxEvent extends EventStage
{
    private final BlockPos pos;
    private AxisAlignedBB _boundingBox;
    
    public BlockCollisionBoundingBoxEvent(final BlockPos pos) {
        this.pos = pos;
    }
    
    public BlockPos getPos() {
        return this.pos;
    }
    
    public AxisAlignedBB getBoundingBox() {
        return this._boundingBox;
    }
    
    public void setBoundingBox(final AxisAlignedBB boundingBox) {
        this._boundingBox = boundingBox;
    }
}
