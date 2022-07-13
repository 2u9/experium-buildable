// 
// Decompiled by Procyon v0.5.36
// 

package dev._3000IQPlay.experium.features.modules.render;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import dev._3000IQPlay.experium.event.events.PerspectiveEvent;
import dev._3000IQPlay.experium.features.setting.Setting;
import dev._3000IQPlay.experium.features.modules.Module;

public class Aspect extends Module
{
    public Setting<Float> aspect;
    
    public Aspect() {
        super("Aspect", "Change ur screen aspect like fortnite.", Category.RENDER, true, false, false);
        this.aspect = (Setting<Float>)this.register(new Setting("Alpha", (T)1.0f, (T)0.1f, (T)5.0f));
    }
    
    @SubscribeEvent
    public void onPerspectiveEvent(final PerspectiveEvent perspectiveEvent) {
        perspectiveEvent.setAspect(this.aspect.getValue());
    }
}
