// 
// Decompiled by Procyon v0.5.36
// 

package dev._3000IQPlay.experium.event.events;

import dev._3000IQPlay.experium.features.setting.Setting;
import dev._3000IQPlay.experium.features.Feature;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import dev._3000IQPlay.experium.event.EventStage;

@Cancelable
public class ClientEvent extends EventStage
{
    private Feature feature;
    private Setting setting;
    
    public ClientEvent(final int stage, final Feature feature) {
        super(stage);
        this.feature = feature;
    }
    
    public ClientEvent(final Setting setting) {
        super(2);
        this.setting = setting;
    }
    
    public Feature getFeature() {
        return this.feature;
    }
    
    public Setting getSetting() {
        return this.setting;
    }
}
