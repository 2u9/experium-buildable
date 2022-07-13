//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Luni\Documents\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev._3000IQPlay.experium.features.modules.render;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import dev._3000IQPlay.experium.event.events.WorldEvent;
import org.lwjgl.opengl.GL11;
import dev._3000IQPlay.experium.event.events.Render3DEvent;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import dev._3000IQPlay.experium.features.setting.Setting;
import dev._3000IQPlay.experium.features.modules.Module;

public class BreadCrumbs extends Module
{
    private final Setting<Integer> colorRed;
    private final Setting<Integer> colorGreen;
    private final Setting<Integer> colorBlue;
    private final Setting<Float> width;
    private final Setting<Boolean> fade;
    private final Setting<Integer> fadeTime;
    private final List<BreadCrumbPoint> points;
    private final long startTime;
    
    public BreadCrumbs() {
        super("BreadCrumbs", "Makes a line", Category.RENDER, true, false, false);
        this.colorRed = (Setting<Integer>)this.register(new Setting("Red", (T)135, (T)0, (T)255));
        this.colorGreen = (Setting<Integer>)this.register(new Setting("Green", (T)0, (T)0, (T)255));
        this.colorBlue = (Setting<Integer>)this.register(new Setting("Blue", (T)255, (T)0, (T)255));
        this.width = (Setting<Float>)this.register(new Setting("Width", (T)Float.intBitsToFloat(Float.floatToIntBits(15.599429f) ^ 0x7EB99743), (T)Float.intBitsToFloat(Float.floatToIntBits(2.076195f) ^ 0x7F04E061), (T)Float.intBitsToFloat(Float.floatToIntBits(1.3190416f) ^ 0x7F08D65B)));
        this.fade = (Setting<Boolean>)this.register(new Setting("Fade", (T)false));
        this.fadeTime = (Setting<Integer>)this.register(new Setting("FadeTime", (T)5, (T)1, (T)100, _hidden -> this.fade.getValue()));
        this.points = new ArrayList<BreadCrumbPoint>();
        this.startTime = System.currentTimeMillis();
    }
    
    private Color getColor() {
        return new Color(this.colorRed.getValue(), this.colorGreen.getValue(), this.colorBlue.getValue());
    }
    
    @Override
    public void onRender3D(final Render3DEvent event) {
        final int fTime = this.fadeTime.getValue() * 100;
        final long fadeSec = System.currentTimeMillis() - fTime;
        final List<BreadCrumbPoint> list = this.points;
        synchronized (list) {
            GL11.glPushMatrix();
            GL11.glDisable(3553);
            GL11.glBlendFunc(770, 771);
            GL11.glEnable(2848);
            GL11.glEnable(3042);
            GL11.glDisable(2929);
            BreadCrumbs.mc.entityRenderer.disableLightmap();
            GL11.glLineWidth((float)this.width.getValue());
            GL11.glBegin(3);
            final double renderPosX = BreadCrumbs.mc.renderManager.viewerPosX;
            final double renderPosY = BreadCrumbs.mc.renderManager.viewerPosY;
            final double renderPosZ = BreadCrumbs.mc.renderManager.viewerPosZ;
            if (this.fade.getValue()) {
                final float pct;
                this.points.removeIf(BreadCrumbPoint -> {
                    pct = (BreadCrumbPoint.time - fadeSec) / (float)fTime;
                    return pct < 0.0f || pct > 1.0f;
                });
            }
            final double n;
            final double n2;
            final double n3;
            this.points.forEach(BreadCrumbPoint -> {
                glColor(BreadCrumbPoint.color, 1.0f);
                GL11.glVertex3d(BreadCrumbPoint.x - n, BreadCrumbPoint.y - n2, BreadCrumbPoint.z - n3);
                return;
            });
            GL11.glColor4d(1.0, 1.0, 1.0, 1.0);
            GL11.glEnd();
            GL11.glEnable(2929);
            GL11.glDisable(2848);
            GL11.glDisable(3042);
            GL11.glEnable(3553);
            GL11.glPopMatrix();
        }
    }
    
    @Override
    public void onUpdate() {
        final List<BreadCrumbPoint> list = this.points;
        synchronized (list) {
            this.points.add(new BreadCrumbPoint(BreadCrumbs.mc.player.posX, BreadCrumbs.mc.player.getEntityBoundingBox().minY, BreadCrumbs.mc.player.posZ, System.currentTimeMillis(), this.getColor().getRGB()));
        }
    }
    
    @SubscribeEvent
    public void onWorldLoad(final WorldEvent event) {
        final List<BreadCrumbPoint> list = this.points;
        synchronized (list) {
            this.points.clear();
        }
    }
    
    @Override
    public void onDisable() {
        final List<BreadCrumbPoint> list = this.points;
        synchronized (list) {
            this.points.clear();
        }
    }
    
    private static void glColor(final int hex, final float alpha) {
        final float red = (hex >> 16 & 0xFF) / 255.0f;
        final float green = (hex >> 8 & 0xFF) / 255.0f;
        final float blue = (hex & 0xFF) / 255.0f;
        GlStateManager.color(red, green, blue, alpha);
    }
    
    public static class BreadCrumbPoint
    {
        private double x;
        private double y;
        private double z;
        private long time;
        private int color;
        
        public double getX() {
            return this.x;
        }
        
        public void setX(final double x) {
            this.x = x;
        }
        
        public double getY() {
            return this.y;
        }
        
        public void setY(final double y) {
            this.y = y;
        }
        
        public double getZ() {
            return this.z;
        }
        
        public void setZ(final double z) {
            this.z = z;
        }
        
        public long getTime() {
            return this.time;
        }
        
        public void setTime(final long time) {
            this.time = time;
        }
        
        public int getColor() {
            return this.color;
        }
        
        public void setColor(final int color) {
            this.color = color;
        }
        
        public BreadCrumbPoint(final double x, final double y, final double z, final long time, final int color) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.time = time;
            this.color = color;
        }
    }
}
