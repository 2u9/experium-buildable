//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Luni\Documents\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev._3000IQPlay.experium.features.modules.client;

import net.minecraft.util.ResourceLocation;
import net.minecraft.client.gui.GuiScreen;
import dev._3000IQPlay.experium.features.gui.ExperiumGui;
import dev._3000IQPlay.experium.util.Util;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import dev._3000IQPlay.experium.features.command.Command;
import dev._3000IQPlay.experium.Experium;
import dev._3000IQPlay.experium.event.events.ClientEvent;
import net.minecraft.client.settings.GameSettings;
import dev._3000IQPlay.experium.features.setting.Setting;
import dev._3000IQPlay.experium.features.modules.Module;

public class ClickGui extends Module
{
    private static ClickGui INSTANCE;
    private final Setting<Settings> setting;
    public Setting<String> prefix;
    public Setting<Boolean> colorSync;
    public Setting<Boolean> rainbowRolling;
    public Setting<Integer> guiWidth;
    public Setting<Integer> moduleButtonHeight;
    public Setting<Boolean> outline;
    public Setting<Boolean> outlineNew;
    public Setting<Float> outlineThickness;
    public Setting<Integer> o_red;
    public Setting<Integer> o_green;
    public Setting<Integer> o_blue;
    public Setting<Integer> o_alpha;
    public Setting<Boolean> snowing;
    public Setting<Boolean> enableSwitch;
    public Setting<Integer> sbRed;
    public Setting<Integer> sbGreen;
    public Setting<Integer> sbBlue;
    public Setting<Integer> sbAlpha;
    public Setting<Integer> seRed;
    public Setting<Integer> seGreen;
    public Setting<Integer> seBlue;
    public Setting<Integer> seAlpha;
    public Setting<Integer> sdRed;
    public Setting<Integer> sdGreen;
    public Setting<Integer> sdBlue;
    public Setting<Integer> sdAlpha;
    public Setting<Boolean> categoryDots;
    public Setting<Boolean> moduleDescription;
    public Setting<Boolean> blurEffect;
    public Setting<Boolean> guiBackground;
    public Setting<Integer> gbRed;
    public Setting<Integer> gbGreen;
    public Setting<Integer> gbBlue;
    public Setting<Integer> gbAlpha;
    public Setting<Boolean> moduleSeperate;
    public Setting<Integer> mosRed;
    public Setting<Integer> mosGreen;
    public Setting<Integer> mosBlue;
    public Setting<Integer> mosAlpha;
    public Setting<Boolean> moduleOutline;
    public Setting<Integer> moRed;
    public Setting<Integer> moGreen;
    public Setting<Integer> moBlue;
    public Setting<Integer> moAlpha;
    public Setting<Boolean> scroll;
    public Setting<Integer> scrollval;
    public Setting<Integer> red;
    public Setting<Integer> green;
    public Setting<Integer> blue;
    public Setting<Integer> hoverAlpha;
    public Setting<Integer> alpha;
    public Setting<Integer> b_red;
    public Setting<Integer> b_green;
    public Setting<Integer> b_blue;
    public Setting<Integer> b_alpha;
    public Setting<Integer> textRed;
    public Setting<Integer> textGreen;
    public Setting<Integer> textBlue;
    public Setting<Integer> textAlpha;
    public Setting<Integer> textRed2;
    public Setting<Integer> textGreen2;
    public Setting<Integer> textBlue2;
    public Setting<Integer> textAlpha2;
    public Setting<Boolean> customFov;
    public Setting<Float> fov;
    public Setting<Boolean> gear;
    public Setting<Boolean> openCloseChange;
    public Setting<String> open;
    public Setting<String> close;
    public Setting<String> moduleButton;
    public Setting<Boolean> devSettings;
    public Setting<Integer> topRed;
    public Setting<Integer> topGreen;
    public Setting<Integer> topBlue;
    public Setting<Integer> topAlpha;
    public Setting<Boolean> frameSettings;
    public Setting<Integer> frameRed;
    public Setting<Integer> frameGreen;
    public Setting<Integer> frameBlue;
    public Setting<Integer> frameAlpha;
    public Setting<Boolean> gradiant;
    public Setting<Integer> gradiantRed;
    public Setting<Integer> gradiantGreen;
    public Setting<Integer> gradiantBlue;
    public Setting<Integer> gradiantAlpha;
    public Setting<Boolean> particles;
    public Setting<Integer> particleLength;
    public Setting<Integer> particlered;
    public Setting<Integer> particlegreen;
    public Setting<Integer> particleblue;
    public float hue;
    
    public ClickGui() {
        super("ClickGui", "Opens the ClickGui", Category.CLIENT, true, false, false);
        this.setting = (Setting<Settings>)this.register(new Setting("Page", (T)Settings.Main));
        this.prefix = (Setting<String>)this.register(new Setting<String>("Prefix", ".").setRenderName(true));
        this.colorSync = (Setting<Boolean>)this.register(new Setting("Sync", (T)false, v -> this.setting.getValue() == Settings.Misc));
        this.rainbowRolling = (Setting<Boolean>)this.register(new Setting("RollingRainbow", (T)false, v -> this.setting.getValue() == Settings.Misc && this.colorSync.getValue() && Colors.INSTANCE.rainbow.getValue()));
        this.guiWidth = (Setting<Integer>)this.register(new Setting("GuiWidth", (T)113, (T)90, (T)115, v -> this.setting.getValue() == Settings.Scaling));
        this.moduleButtonHeight = (Setting<Integer>)this.register(new Setting("ModuleButtonHeight", (T)14, (T)12, (T)18, v -> this.setting.getValue() == Settings.Scaling));
        this.outline = (Setting<Boolean>)this.register(new Setting("Outline", (T)false, v -> this.setting.getValue() == Settings.Main));
        this.outlineNew = (Setting<Boolean>)this.register(new Setting("OutlineNew", (T)false, v -> this.setting.getValue() == Settings.Main));
        this.outlineThickness = (Setting<Float>)this.register(new Setting("LineThickness", (T)2.5f, (T)0.5f, (T)5.0f, v -> this.setting.getValue() == Settings.Main && this.outlineNew.getValue()));
        this.o_red = (Setting<Integer>)this.register(new Setting("OutlineRed", (T)135, (T)0, (T)255, v -> this.setting.getValue() == Settings.Main && this.outlineNew.getValue()));
        this.o_green = (Setting<Integer>)this.register(new Setting("OutlineGreen", (T)135, (T)0, (T)255, v -> this.setting.getValue() == Settings.Main && this.outlineNew.getValue()));
        this.o_blue = (Setting<Integer>)this.register(new Setting("OutlineBlue", (T)255, (T)0, (T)255, v -> this.setting.getValue() == Settings.Main && this.outlineNew.getValue()));
        this.o_alpha = (Setting<Integer>)this.register(new Setting("OutlineAlpha", (T)255, (T)0, (T)255, v -> this.setting.getValue() == Settings.Main && this.outlineNew.getValue()));
        this.snowing = (Setting<Boolean>)this.register(new Setting("Snowing", (T)false, v -> this.setting.getValue() == Settings.Background));
        this.enableSwitch = (Setting<Boolean>)this.register(new Setting("Switch", (T)true, v -> this.setting.getValue() == Settings.Misc));
        this.sbRed = (Setting<Integer>)this.register(new Setting("SwitchBackgroundRed", (T)21, (T)0, (T)255, v -> this.setting.getValue() == Settings.Misc && this.enableSwitch.getValue()));
        this.sbGreen = (Setting<Integer>)this.register(new Setting("SwitchBackgroundGreen", (T)21, (T)0, (T)255, v -> this.setting.getValue() == Settings.Misc && this.enableSwitch.getValue()));
        this.sbBlue = (Setting<Integer>)this.register(new Setting("SwitchBackgroundBlue", (T)21, (T)0, (T)255, v -> this.setting.getValue() == Settings.Misc && this.enableSwitch.getValue()));
        this.sbAlpha = (Setting<Integer>)this.register(new Setting("SwitchBackgroundAlpha", (T)255, (T)0, (T)255, v -> this.setting.getValue() == Settings.Misc && this.enableSwitch.getValue()));
        this.seRed = (Setting<Integer>)this.register(new Setting("SwitchEnableRed", (T)102, (T)0, (T)255, v -> this.setting.getValue() == Settings.Misc && this.enableSwitch.getValue()));
        this.seGreen = (Setting<Integer>)this.register(new Setting("SwitchEnableGreen", (T)255, (T)0, (T)255, v -> this.setting.getValue() == Settings.Misc && this.enableSwitch.getValue()));
        this.seBlue = (Setting<Integer>)this.register(new Setting("SwitchEnabledBlue", (T)51, (T)0, (T)255, v -> this.setting.getValue() == Settings.Misc && this.enableSwitch.getValue()));
        this.seAlpha = (Setting<Integer>)this.register(new Setting("SwitchEnableAlpha", (T)200, (T)0, (T)255, v -> this.setting.getValue() == Settings.Misc && this.enableSwitch.getValue()));
        this.sdRed = (Setting<Integer>)this.register(new Setting("SwitchDisableRed", (T)102, (T)0, (T)255, v -> this.setting.getValue() == Settings.Misc && this.enableSwitch.getValue()));
        this.sdGreen = (Setting<Integer>)this.register(new Setting("SwitchDisableGreen", (T)102, (T)0, (T)255, v -> this.setting.getValue() == Settings.Misc && this.enableSwitch.getValue()));
        this.sdBlue = (Setting<Integer>)this.register(new Setting("SwitchDisableBlue", (T)102, (T)0, (T)255, v -> this.setting.getValue() == Settings.Misc && this.enableSwitch.getValue()));
        this.sdAlpha = (Setting<Integer>)this.register(new Setting("SwitchDisableAlpha", (T)200, (T)0, (T)255, v -> this.setting.getValue() == Settings.Misc && this.enableSwitch.getValue()));
        this.categoryDots = (Setting<Boolean>)this.register(new Setting("CategoryDots", (T)true, v -> this.setting.getValue() == Settings.Misc));
        this.moduleDescription = (Setting<Boolean>)this.register(new Setting("Description", (T)true, v -> this.setting.getValue() == Settings.Misc));
        this.blurEffect = (Setting<Boolean>)this.register(new Setting("Blur", (T)false, v -> this.setting.getValue() == Settings.Background));
        this.guiBackground = (Setting<Boolean>)this.register(new Setting("GuiBackground", (T)true, v -> this.setting.getValue() == Settings.Background));
        this.gbRed = (Setting<Integer>)this.register(new Setting("GuiBackgroundRed", (T)0, (T)0, (T)255, v -> this.setting.getValue() == Settings.Background && this.guiBackground.getValue()));
        this.gbGreen = (Setting<Integer>)this.register(new Setting("GuiBackgroundGreen", (T)0, (T)0, (T)255, v -> this.setting.getValue() == Settings.Background && this.guiBackground.getValue()));
        this.gbBlue = (Setting<Integer>)this.register(new Setting("GuiBackgroundBlue", (T)0, (T)0, (T)255, v -> this.setting.getValue() == Settings.Background && this.guiBackground.getValue()));
        this.gbAlpha = (Setting<Integer>)this.register(new Setting("GuiBackgroundAlpha", (T)150, (T)0, (T)255, v -> this.setting.getValue() == Settings.Background && this.guiBackground.getValue()));
        this.moduleSeperate = (Setting<Boolean>)this.register(new Setting("ModuleSeperateLine", (T)true, v -> this.setting.getValue() == Settings.Main));
        this.mosRed = (Setting<Integer>)this.register(new Setting("ModuleSeperateRed", (T)135, (T)0, (T)255, v -> this.setting.getValue() == Settings.Main && this.moduleSeperate.getValue()));
        this.mosGreen = (Setting<Integer>)this.register(new Setting("ModuleSeperateGreen", (T)135, (T)0, (T)255, v -> this.setting.getValue() == Settings.Main && this.moduleSeperate.getValue()));
        this.mosBlue = (Setting<Integer>)this.register(new Setting("ModuleSeperateBlue", (T)255, (T)0, (T)255, v -> this.setting.getValue() == Settings.Main && this.moduleSeperate.getValue()));
        this.mosAlpha = (Setting<Integer>)this.register(new Setting("ModuleSeperateAlpha", (T)255, (T)0, (T)255, v -> this.setting.getValue() == Settings.Main && this.moduleSeperate.getValue()));
        this.moduleOutline = (Setting<Boolean>)this.register(new Setting("ModuleOutline", (T)false, v -> this.setting.getValue() == Settings.Main));
        this.moRed = (Setting<Integer>)this.register(new Setting("ModuleOutlineRed", (T)135, (T)0, (T)255, v -> this.setting.getValue() == Settings.Main && this.moduleOutline.getValue()));
        this.moGreen = (Setting<Integer>)this.register(new Setting("ModuleOutlineGreen", (T)135, (T)0, (T)255, v -> this.setting.getValue() == Settings.Main && this.moduleOutline.getValue()));
        this.moBlue = (Setting<Integer>)this.register(new Setting("ModuleOutlineBlue", (T)255, (T)0, (T)255, v -> this.setting.getValue() == Settings.Main && this.moduleOutline.getValue()));
        this.moAlpha = (Setting<Integer>)this.register(new Setting("ModuleOutlineAlpha", (T)255, (T)0, (T)255, v -> this.setting.getValue() == Settings.Main && this.moduleOutline.getValue()));
        this.scroll = (Setting<Boolean>)this.register(new Setting("Scroll", (T)true, v -> this.setting.getValue() == Settings.Misc));
        this.scrollval = (Setting<Integer>)this.register(new Setting("Scroll Speed", (T)10, (T)1, (T)30, v -> this.setting.getValue() == Settings.Misc && this.scroll.getValue()));
        this.red = (Setting<Integer>)this.register(new Setting("Red", (T)75, (T)0, (T)255, v -> this.setting.getValue() == Settings.Main));
        this.green = (Setting<Integer>)this.register(new Setting("Green", (T)75, (T)0, (T)255, v -> this.setting.getValue() == Settings.Main));
        this.blue = (Setting<Integer>)this.register(new Setting("Blue", (T)75, (T)0, (T)255, v -> this.setting.getValue() == Settings.Main));
        this.hoverAlpha = (Setting<Integer>)this.register(new Setting("Alpha", (T)0, (T)0, (T)255, v -> this.setting.getValue() == Settings.Main));
        this.alpha = (Setting<Integer>)this.register(new Setting("HoverAlpha", (T)170, (T)0, (T)255, v -> this.setting.getValue() == Settings.Main));
        this.b_red = (Setting<Integer>)this.register(new Setting("ButtonRed", (T)40, (T)0, (T)255, v -> this.setting.getValue() == Settings.Main));
        this.b_green = (Setting<Integer>)this.register(new Setting("ButtonGreen", (T)40, (T)0, (T)255, v -> this.setting.getValue() == Settings.Main));
        this.b_blue = (Setting<Integer>)this.register(new Setting("ButtonBlue", (T)40, (T)0, (T)255, v -> this.setting.getValue() == Settings.Main));
        this.b_alpha = (Setting<Integer>)this.register(new Setting("ButtonAlpha", (T)255, (T)0, (T)255, v -> this.setting.getValue() == Settings.Main));
        this.textRed = (Setting<Integer>)this.register(new Setting("EnabledTextRed", (T)135, (T)0, (T)255, v -> this.setting.getValue() == Settings.FontC));
        this.textGreen = (Setting<Integer>)this.register(new Setting("EnabledTextGreen", (T)135, (T)0, (T)255, v -> this.setting.getValue() == Settings.FontC));
        this.textBlue = (Setting<Integer>)this.register(new Setting("EnabledTextBlue", (T)255, (T)0, (T)255, v -> this.setting.getValue() == Settings.FontC));
        this.textAlpha = (Setting<Integer>)this.register(new Setting("EnabledTextAlpha", (T)255, (T)0, (T)255, v -> this.setting.getValue() == Settings.FontC));
        this.textRed2 = (Setting<Integer>)this.register(new Setting("DisabledTextRed", (T)255, (T)0, (T)255, v -> this.setting.getValue() == Settings.FontC));
        this.textGreen2 = (Setting<Integer>)this.register(new Setting("DisabledTextGreen", (T)255, (T)0, (T)255, v -> this.setting.getValue() == Settings.FontC));
        this.textBlue2 = (Setting<Integer>)this.register(new Setting("DisabledTextBlue", (T)255, (T)0, (T)255, v -> this.setting.getValue() == Settings.FontC));
        this.textAlpha2 = (Setting<Integer>)this.register(new Setting("DisabledTextAlpha", (T)255, (T)0, (T)255, v -> this.setting.getValue() == Settings.FontC));
        this.customFov = (Setting<Boolean>)this.register(new Setting("CustomFov", (T)false, v -> this.setting.getValue() == Settings.Misc));
        this.fov = (Setting<Float>)this.register(new Setting("Fov", (T)135.0f, (T)(-180.0f), (T)180.0f, v -> this.setting.getValue() == Settings.Misc && this.customFov.getValue()));
        this.gear = (Setting<Boolean>)this.register(new Setting("Gears", (T)false, v -> this.setting.getValue() == Settings.Misc));
        this.openCloseChange = (Setting<Boolean>)this.register(new Setting("Open/Close", (T)true, v -> this.setting.getValue() == Settings.Misc));
        this.open = (Setting<String>)this.register(new Setting<Object>("Open:", "+", v -> this.setting.getValue() == Settings.Misc && this.openCloseChange.getValue()).setRenderName(true));
        this.close = (Setting<String>)this.register(new Setting<Object>("Close:", "-", v -> this.setting.getValue() == Settings.Misc && this.openCloseChange.getValue()).setRenderName(true));
        this.moduleButton = (Setting<String>)this.register(new Setting<Object>("Buttons:", "", v -> this.setting.getValue() == Settings.Misc && !this.openCloseChange.getValue()).setRenderName(true));
        this.devSettings = (Setting<Boolean>)this.register(new Setting("DevSettings", (T)true, v -> this.setting.getValue() == Settings.Main));
        this.topRed = (Setting<Integer>)this.register(new Setting("TopRed", (T)30, (T)0, (T)255, v -> this.setting.getValue() == Settings.Main && this.devSettings.getValue()));
        this.topGreen = (Setting<Integer>)this.register(new Setting("TopGreen", (T)30, (T)0, (T)255, v -> this.setting.getValue() == Settings.Main && this.devSettings.getValue()));
        this.topBlue = (Setting<Integer>)this.register(new Setting("TopBlue", (T)30, (T)0, (T)255, v -> this.setting.getValue() == Settings.Main && this.devSettings.getValue()));
        this.topAlpha = (Setting<Integer>)this.register(new Setting("TopAlpha", (T)255, (T)0, (T)255, v -> this.setting.getValue() == Settings.Main && this.devSettings.getValue()));
        this.frameSettings = (Setting<Boolean>)this.register(new Setting("FrameSetting", (T)true, v -> this.setting.getValue() == Settings.Main));
        this.frameRed = (Setting<Integer>)this.register(new Setting("FrameRed", (T)135, (T)0, (T)255, v -> this.setting.getValue() == Settings.Main && this.frameSettings.getValue()));
        this.frameGreen = (Setting<Integer>)this.register(new Setting("FrameGreen", (T)135, (T)0, (T)255, v -> this.setting.getValue() == Settings.Main && this.frameSettings.getValue()));
        this.frameBlue = (Setting<Integer>)this.register(new Setting("FrameBlue", (T)255, (T)0, (T)255, v -> this.setting.getValue() == Settings.Main && this.frameSettings.getValue()));
        this.frameAlpha = (Setting<Integer>)this.register(new Setting("FrameAlpha", (T)255, (T)0, (T)255, v -> this.setting.getValue() == Settings.Main && this.frameSettings.getValue()));
        this.gradiant = (Setting<Boolean>)this.register(new Setting("Gradiant", (T)false, v -> this.setting.getValue() == Settings.Background));
        this.gradiantRed = (Setting<Integer>)this.register(new Setting("GradiantRed", (T)135, (T)0, (T)255, v -> this.setting.getValue() == Settings.Background && this.gradiant.getValue()));
        this.gradiantGreen = (Setting<Integer>)this.register(new Setting("GradiantGreen", (T)135, (T)0, (T)255, v -> this.setting.getValue() == Settings.Background && this.gradiant.getValue()));
        this.gradiantBlue = (Setting<Integer>)this.register(new Setting("GradiantBlue", (T)255, (T)0, (T)255, v -> this.setting.getValue() == Settings.Background && this.gradiant.getValue()));
        this.gradiantAlpha = (Setting<Integer>)this.register(new Setting("GradiantAlpha", (T)200, (T)0, (T)255, v -> this.setting.getValue() == Settings.Background && this.gradiant.getValue()));
        this.particles = (Setting<Boolean>)this.register(new Setting("Particles", (T)false, v -> this.setting.getValue() == Settings.Background));
        this.particleLength = (Setting<Integer>)this.register(new Setting("ParticleLength", (T)80, (T)0, (T)300, v -> this.setting.getValue() == Settings.Background && this.particles.getValue()));
        this.particlered = (Setting<Integer>)this.register(new Setting("ParticleRed", (T)135, (T)0, (T)255, v -> this.setting.getValue() == Settings.Background && this.particles.getValue()));
        this.particlegreen = (Setting<Integer>)this.register(new Setting("ParticleGreen", (T)135, (T)0, (T)255, v -> this.setting.getValue() == Settings.Background && this.particles.getValue()));
        this.particleblue = (Setting<Integer>)this.register(new Setting("ParticleBlue", (T)255, (T)0, (T)255, v -> this.setting.getValue() == Settings.Background && this.particles.getValue()));
        this.setInstance();
        this.setBind(54);
    }
    
    public static ClickGui getInstance() {
        if (ClickGui.INSTANCE == null) {
            ClickGui.INSTANCE = new ClickGui();
        }
        return ClickGui.INSTANCE;
    }
    
    private void setInstance() {
        ClickGui.INSTANCE = this;
    }
    
    @Override
    public void onUpdate() {
        if (this.customFov.getValue()) {
            ClickGui.mc.gameSettings.setOptionFloatValue(GameSettings.Options.FOV, (float)this.fov.getValue());
        }
    }
    
    @SubscribeEvent
    public void onSettingChange(final ClientEvent event) {
        if (event.getStage() == 2 && event.getSetting().getFeature().equals(this)) {
            if (event.getSetting().equals(this.prefix)) {
                Experium.commandManager.setPrefix(this.prefix.getPlannedValue());
                Command.sendMessage("Prefix set to §a" + Experium.commandManager.getPrefix());
            }
            Experium.colorManager.setColor(this.red.getPlannedValue(), this.green.getPlannedValue(), this.blue.getPlannedValue(), this.hoverAlpha.getPlannedValue());
        }
    }
    
    @Override
    public void onEnable() {
        Util.mc.displayGuiScreen((GuiScreen)new ExperiumGui());
        if (this.blurEffect.getValue()) {
            ClickGui.mc.entityRenderer.loadShader(new ResourceLocation("shaders/post/blur.json"));
        }
    }
    
    @Override
    public void onLoad() {
        if (this.colorSync.getValue()) {
            Experium.colorManager.setColor(Colors.INSTANCE.getCurrentColor().getRed(), Colors.INSTANCE.getCurrentColor().getGreen(), Colors.INSTANCE.getCurrentColor().getBlue(), this.hoverAlpha.getValue());
        }
        else {
            Experium.colorManager.setColor(this.red.getValue(), this.green.getValue(), this.blue.getValue(), this.hoverAlpha.getValue());
        }
        Experium.commandManager.setPrefix(this.prefix.getValue());
    }
    
    @Override
    public void onTick() {
        if (!(ClickGui.mc.currentScreen instanceof ExperiumGui)) {
            this.disable();
            if (ClickGui.mc.entityRenderer.getShaderGroup() != null) {
                ClickGui.mc.entityRenderer.getShaderGroup().deleteShaderGroup();
            }
        }
    }
    
    @Override
    public void onDisable() {
        if (ClickGui.mc.currentScreen instanceof ExperiumGui) {
            Util.mc.displayGuiScreen((GuiScreen)null);
        }
    }
    
    static {
        ClickGui.INSTANCE = new ClickGui();
    }
    
    public enum Settings
    {
        Main, 
        Background, 
        FontC, 
        Scaling, 
        Misc;
    }
}
