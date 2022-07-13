//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Luni\Documents\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev._3000IQPlay.experium.features.modules.render;

import net.minecraft.entity.Entity;
import dev._3000IQPlay.experium.util.ChinaHatUtil;
import java.awt.Color;
import dev._3000IQPlay.experium.event.events.Render3DEvent;
import dev._3000IQPlay.experium.features.setting.Setting;
import dev._3000IQPlay.experium.features.modules.Module;

public class ChinaHat extends Module
{
    public Setting<Integer> red;
    public Setting<Integer> green;
    public Setting<Integer> blue;
    public Setting<Integer> red2;
    public Setting<Integer> green2;
    public Setting<Integer> blue2;
    public Setting<Integer> points;
    public Setting<Boolean> firstP;
    
    public ChinaHat() {
        super("ChinaHat", "Cool china hat from (GuguHack)", Category.RENDER, true, false, false);
        this.red = (Setting<Integer>)this.register(new Setting("Red", (T)0, (T)0, (T)255));
        this.green = (Setting<Integer>)this.register(new Setting("Green", (T)255, (T)0, (T)255));
        this.blue = (Setting<Integer>)this.register(new Setting("Blue", (T)255, (T)0, (T)255));
        this.red2 = (Setting<Integer>)this.register(new Setting("Red2", (T)135, (T)0, (T)255));
        this.green2 = (Setting<Integer>)this.register(new Setting("Green2", (T)135, (T)0, (T)255));
        this.blue2 = (Setting<Integer>)this.register(new Setting("Blue2", (T)255, (T)0, (T)255));
        this.points = (Setting<Integer>)this.register(new Setting("Points", (T)64, (T)4, (T)64));
        this.firstP = (Setting<Boolean>)this.register(new Setting("FirstPerson", (T)false));
    }
    
    @Override
    public void onRender3D(final Render3DEvent render3DEvent) {
        float f = 0.0f;
        if (ChinaHat.mc.gameSettings.thirdPersonView != 0 || this.firstP.getValue()) {
            for (int i = 0; i < 400; ++i) {
                f = (float)ChinaHatUtil.getGradientOffset(new Color(this.red2.getValue(), this.green2.getValue(), this.blue2.getValue(), 255), new Color(this.red.getValue(), this.green.getValue(), this.blue.getValue(), 255), Math.abs(System.currentTimeMillis() / 7L - i / 2) / 120.0).getRGB();
                if (ChinaHat.mc.player.isElytraFlying()) {
                    ChinaHatUtil.drawHat((Entity)ChinaHat.mc.player, 0.009 + i * 0.0014, render3DEvent.getPartialTicks(), this.points.getValue(), 2.0f, 1.1f - i * 7.85E-4f - (ChinaHat.mc.player.isSneaking() ? 0.03f : 0.03f), (int)f);
                }
                else {
                    ChinaHatUtil.drawHat((Entity)ChinaHat.mc.player, 0.009 + i * 0.0014, render3DEvent.getPartialTicks(), this.points.getValue(), 2.0f, 2.2f - i * 7.85E-4f - (ChinaHat.mc.player.isSneaking() ? 0.03f : 0.03f), (int)f);
                }
            }
        }
    }
}
