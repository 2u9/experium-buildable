/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraftforge.client.event.EntityViewRenderEvent$FogColors
 *  net.minecraftforge.client.event.EntityViewRenderEvent$FogDensity
 *  net.minecraftforge.common.MinecraftForge
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package dev._3000IQPlay.experium.features.modules.render;

import dev._3000IQPlay.experium.features.modules.Module;
import dev._3000IQPlay.experium.features.setting.Setting;
import java.awt.Color;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class SkyColor
extends Module {
    private static SkyColor INSTANCE = new SkyColor();
    private Setting<Integer> red = this.register(new Setting<Integer>("Red", 135, 0, 255));
    private Setting<Integer> green = this.register(new Setting<Integer>("Green", 0, 0, 255));
    private Setting<Integer> blue = this.register(new Setting<Integer>("Blue", 255, 0, 255));
    private Setting<Boolean> rainbow = this.register(new Setting<Boolean>("Rainbow", false));
    private Setting<Boolean> fog = this.register(new Setting<Boolean>("Fog", true));

    public SkyColor() {
        super("SkyColor", "Changes the color of the sky", Module.Category.RENDER, false, false, false);
    }

    public static SkyColor getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SkyColor();
        }
        return INSTANCE;
    }

    private void setInstance() {
        INSTANCE = this;
    }

    @SubscribeEvent
    public void fogColors(EntityViewRenderEvent.FogColors event) {
        event.setRed((float)this.red.getValue().intValue() / 255.0f);
        event.setGreen((float)this.green.getValue().intValue() / 255.0f);
        event.setBlue((float)this.blue.getValue().intValue() / 255.0f);
    }

    @SubscribeEvent
    public void fog_density(EntityViewRenderEvent.FogDensity event) {
        if (this.fog.getValue().booleanValue()) {
            event.setDensity(0.0f);
            event.setCanceled(true);
        }
    }

    @Override
    public void onEnable() {
        MinecraftForge.EVENT_BUS.register((Object)this);
    }

    @Override
    public void onDisable() {
        MinecraftForge.EVENT_BUS.unregister((Object)this);
    }

    @Override
    public void onUpdate() {
        if (this.rainbow.getValue().booleanValue()) {
            this.doRainbow();
        }
    }

    public void doRainbow() {
        float[] tick_color = new float[]{(float)(System.currentTimeMillis() % 11520L) / 11520.0f};
        int color_rgb_o = Color.HSBtoRGB(tick_color[0], 0.8f, 0.8f);
        this.red.setValue(color_rgb_o >> 16 & 0xFF);
        this.green.setValue(color_rgb_o >> 8 & 0xFF);
        this.blue.setValue(color_rgb_o & 0xFF);
    }
}

