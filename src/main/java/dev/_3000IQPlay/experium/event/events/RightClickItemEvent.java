// 
// Decompiled by Procyon v0.5.36
// 

package dev._3000IQPlay.experium.event.events;

import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraft.entity.player.EntityPlayer;
import dev._3000IQPlay.experium.event.EventStage;

public class RightClickItemEvent extends EventStage
{
    private final EntityPlayer player;
    private final World worldIn;
    private final EnumHand hand;
    
    public RightClickItemEvent(final EntityPlayer player, final World worldIn, final EnumHand hand) {
        this.player = player;
        this.worldIn = worldIn;
        this.hand = hand;
    }
    
    public EntityPlayer getPlayer() {
        return this.player;
    }
    
    public World getWorldIn() {
        return this.worldIn;
    }
    
    public EnumHand getHand() {
        return this.hand;
    }
}
