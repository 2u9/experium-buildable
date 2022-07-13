// 
// Decompiled by Procyon v0.5.36
// 

package dev._3000IQPlay.experium.features.modules.client;

import dev._3000IQPlay.experium.features.setting.Setting;
import dev._3000IQPlay.experium.features.modules.Module;

public class ModuleTools extends Module
{
    private static ModuleTools INSTANCE;
    public Setting<Notifier> notifier;
    public Setting<PopNotifier> popNotifier;
    
    public ModuleTools() {
        super("ModuleTools", "Change settings", Category.CLIENT, true, false, false);
        this.notifier = (Setting<Notifier>)this.register(new Setting("ModuleNotifier", (T)Notifier.EXPERIUM));
        this.popNotifier = (Setting<PopNotifier>)this.register(new Setting("PopNotifier", (T)PopNotifier.NONE));
        ModuleTools.INSTANCE = this;
    }
    
    public static ModuleTools getInstance() {
        if (ModuleTools.INSTANCE == null) {
            ModuleTools.INSTANCE = new ModuleTools();
        }
        return ModuleTools.INSTANCE;
    }
    
    public enum Notifier
    {
        EXPERIUM, 
        FUTURE, 
        DOTGOD, 
        MUFFIN, 
        WEATHER, 
        SNOW, 
        PYRO, 
        CATALYST, 
        KONAS, 
        RUSHERHACK, 
        LEGACY, 
        EUROPA;
    }
    
    public enum PopNotifier
    {
        PHOBOS, 
        FUTURE, 
        DOTGOD, 
        NONE;
    }
}
