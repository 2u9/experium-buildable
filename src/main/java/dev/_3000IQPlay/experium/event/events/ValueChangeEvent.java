// 
// Decompiled by Procyon v0.5.36
// 

package dev._3000IQPlay.experium.event.events;

import dev._3000IQPlay.experium.features.setting.Setting;
import dev._3000IQPlay.experium.event.EventStage;

public class ValueChangeEvent extends EventStage
{
    public Setting setting;
    public Object value;
    
    public ValueChangeEvent(final Setting setting, final Object value) {
        this.setting = setting;
        this.value = value;
    }
}
