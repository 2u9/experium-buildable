// 
// Decompiled by Procyon v0.5.36
// 

package dev._3000IQPlay.experium.mixin;

import java.util.Map;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Mixins;
import org.spongepowered.asm.launch.MixinBootstrap;
import dev._3000IQPlay.experium.Experium;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

public class ExperiumMixinLoader implements IFMLLoadingPlugin
{
    private static boolean isObfuscatedEnvironment;
    
    public ExperiumMixinLoader() {
        Experium.LOGGER.info("Experium mixins initialized");
        MixinBootstrap.init();
        Mixins.addConfiguration("mixins.experium.json");
        MixinEnvironment.getDefaultEnvironment().setObfuscationContext("searge");
        Experium.LOGGER.info(MixinEnvironment.getDefaultEnvironment().getObfuscationContext());
    }
    
    public String[] getASMTransformerClass() {
        return new String[0];
    }
    
    public String getModContainerClass() {
        return null;
    }
    
    public String getSetupClass() {
        return null;
    }
    
    public void injectData(final Map<String, Object> data) {
        ExperiumMixinLoader.isObfuscatedEnvironment = data.get("runtimeDeobfuscationEnabled");
    }
    
    public String getAccessTransformerClass() {
        return null;
    }
    
    static {
        ExperiumMixinLoader.isObfuscatedEnvironment = false;
    }
}
