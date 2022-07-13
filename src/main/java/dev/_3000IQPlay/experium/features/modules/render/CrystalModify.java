// 
// Decompiled by Procyon v0.5.36
// 

package dev._3000IQPlay.experium.features.modules.render;

import dev._3000IQPlay.experium.features.setting.Setting;
import dev._3000IQPlay.experium.features.modules.Module;

public class CrystalModify extends Module
{
    public static CrystalModify INSTANCE;
    public Setting<modes> mode;
    public Setting<outlineModes> outlineMode;
    public Setting<Float> size;
    public Setting<Float> crystalSpeed;
    public Setting<Float> crystalBounce;
    public Setting<BlendModes> blendModes;
    public Setting<Boolean> enchanted;
    public Setting<Integer> enchantRed;
    public Setting<Integer> enchantGreen;
    public Setting<Integer> enchantBlue;
    public Setting<Integer> enchantAlpha;
    public Setting<Boolean> texture;
    public Setting<Boolean> colorSync;
    public Setting<Integer> red;
    public Setting<Integer> green;
    public Setting<Integer> blue;
    public Setting<Integer> alpha;
    public Setting<Boolean> outline;
    public Setting<Float> lineWidth;
    public Setting<Integer> outlineRed;
    public Setting<Integer> outlineGreen;
    public Setting<Integer> outlineBlue;
    public Setting<Integer> outlineAlpha;
    public Setting<Boolean> hiddenSync;
    public Setting<Integer> hiddenRed;
    public Setting<Integer> hiddenGreen;
    public Setting<Integer> hiddenBlue;
    public Setting<Integer> hiddenAlpha;
    
    public CrystalModify() {
        super("CrystalModify", "Modifies crystal rendering in different ways", Category.RENDER, true, false, false);
        this.mode = (Setting<modes>)this.register(new Setting("Mode", (T)modes.FILL));
        this.outlineMode = (Setting<outlineModes>)this.register(new Setting("Outline Mode", (T)outlineModes.WIRE));
        this.size = (Setting<Float>)this.register(new Setting("Size", (T)1.0f, (T)0.1f, (T)2.0f));
        this.crystalSpeed = (Setting<Float>)this.register(new Setting("Speed", (T)1.0f, (T)0.1f, (T)20.0f));
        this.crystalBounce = (Setting<Float>)this.register(new Setting("Bounce", (T)0.2f, (T)0.1f, (T)1.0f));
        this.blendModes = (Setting<BlendModes>)this.register(new Setting("Blend", (T)BlendModes.Default));
        this.enchanted = (Setting<Boolean>)this.register(new Setting("Glint", (T)false));
        this.enchantRed = (Setting<Integer>)this.register(new Setting("Glint Red", (T)135, (T)0, (T)255, v -> this.enchanted.getValue()));
        this.enchantGreen = (Setting<Integer>)this.register(new Setting("Glint Green", (T)0, (T)0, (T)255, v -> this.enchanted.getValue()));
        this.enchantBlue = (Setting<Integer>)this.register(new Setting("Glint Blue", (T)255, (T)0, (T)255, v -> this.enchanted.getValue()));
        this.enchantAlpha = (Setting<Integer>)this.register(new Setting("Glint Alpha", (T)255, (T)0, (T)255, v -> this.enchanted.getValue()));
        this.texture = (Setting<Boolean>)this.register(new Setting("Texture", (T)false));
        this.colorSync = (Setting<Boolean>)this.register(new Setting("Sync", (T)false));
        this.red = (Setting<Integer>)this.register(new Setting("Red", (T)135, (T)0, (T)255));
        this.green = (Setting<Integer>)this.register(new Setting("Green", (T)0, (T)0, (T)255));
        this.blue = (Setting<Integer>)this.register(new Setting("Blue", (T)255, (T)0, (T)255));
        this.alpha = (Setting<Integer>)this.register(new Setting("Alpha", (T)255, (T)0, (T)255));
        this.outline = (Setting<Boolean>)this.register(new Setting("Outline", (T)false));
        this.lineWidth = (Setting<Float>)this.register(new Setting("LineWidth", (T)1.0f, (T)0.1f, (T)5.0f, v -> this.outline.getValue()));
        this.outlineRed = (Setting<Integer>)this.register(new Setting("Outline Red", (T)135, (T)0, (T)255, v -> this.outline.getValue()));
        this.outlineGreen = (Setting<Integer>)this.register(new Setting("Outline Green", (T)0, (T)0, (T)255, v -> this.outline.getValue()));
        this.outlineBlue = (Setting<Integer>)this.register(new Setting("Outline Blue", (T)255, (T)0, (T)255, v -> this.outline.getValue()));
        this.outlineAlpha = (Setting<Integer>)this.register(new Setting("Outline Alpha", (T)90, (T)0, (T)255, v -> this.outline.getValue()));
        this.hiddenSync = (Setting<Boolean>)this.register(new Setting("Hidden Sync", (T)false));
        this.hiddenRed = (Setting<Integer>)this.register(new Setting("Hidden Red", (T)135, (T)0, (T)255, v -> !this.hiddenSync.getValue()));
        this.hiddenGreen = (Setting<Integer>)this.register(new Setting("Hidden Green", (T)0, (T)0, (T)255, v -> !this.hiddenSync.getValue()));
        this.hiddenBlue = (Setting<Integer>)this.register(new Setting("Hidden Blue", (T)255, (T)0, (T)255, v -> !this.hiddenSync.getValue()));
        this.hiddenAlpha = (Setting<Integer>)this.register(new Setting("Hidden Alpha", (T)90, (T)0, (T)255, v -> !this.hiddenSync.getValue()));
        CrystalModify.INSTANCE = this;
    }
    
    public enum modes
    {
        FILL, 
        WIREFRAME;
    }
    
    public enum outlineModes
    {
        WIRE, 
        FLAT;
    }
    
    public enum BlendModes
    {
        Default, 
        Brighter;
    }
}
