//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Luni\Documents\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev._3000IQPlay.experium.features.modules.client;

import dev._3000IQPlay.experium.util.Util;
import dev._3000IQPlay.experium.features.Feature;
import dev._3000IQPlay.experium.features.setting.Setting;
import dev._3000IQPlay.experium.features.modules.Module;

public class NickHider extends Module
{
    private static NickHider instance;
    public final Setting<Boolean> changeOwn;
    public final Setting<String> ownName;
    
    public NickHider() {
        super("NickHider", "Helps with creating media", Category.CLIENT, false, false, false);
        this.changeOwn = (Setting<Boolean>)this.register(new Setting("MyName", (T)true));
        this.ownName = (Setting<String>)this.register(new Setting("Name", (T)"Name here...", v -> this.changeOwn.getValue()));
        NickHider.instance = this;
    }
    
    public static NickHider getInstance() {
        if (NickHider.instance == null) {
            NickHider.instance = new NickHider();
        }
        return NickHider.instance;
    }
    
    public static String getPlayerName() {
        if (Feature.fullNullCheck() || !PingBypass.getInstance().isConnected()) {
            return Util.mc.getSession().getUsername();
        }
        final String name = PingBypass.getInstance().getPlayerName();
        if (name == null || name.isEmpty()) {
            return Util.mc.getSession().getUsername();
        }
        return name;
    }
}
