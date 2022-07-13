// 
// Decompiled by Procyon v0.5.36
// 

package dev._3000IQPlay.experium.features.modules.client;

import dev._3000IQPlay.experium.features.setting.Setting;
import dev._3000IQPlay.experium.features.modules.Module;

public class FriendSettings extends Module
{
    private static FriendSettings INSTANCE;
    public Setting<Boolean> notify;
    
    public FriendSettings() {
        super("FriendSettings", "Change aspects of friends", Category.CLIENT, true, false, false);
        this.notify = (Setting<Boolean>)this.register(new Setting("Notify", (T)false));
        FriendSettings.INSTANCE = this;
    }
    
    public static FriendSettings getInstance() {
        if (FriendSettings.INSTANCE == null) {
            FriendSettings.INSTANCE = new FriendSettings();
        }
        return FriendSettings.INSTANCE;
    }
}
