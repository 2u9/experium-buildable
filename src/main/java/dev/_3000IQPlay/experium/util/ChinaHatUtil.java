//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Luni\Documents\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev._3000IQPlay.experium.util;

import java.awt.Color;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;
import net.minecraft.entity.Entity;

public class ChinaHatUtil
{
    public static double interpolate(final double d, final double d2, final double d3) {
        return d + (d2 - d) * d3;
    }
    
    public static void drawHat(final Entity entity, final double d, final float f, final int n, final float f2, final float f3, final int n2) {
        GL11.glPushMatrix();
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        GL11.glDepthMask(false);
        GL11.glLineWidth(f2);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2929);
        GL11.glBegin(3);
        final float f4 = entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * Minecraft.getMinecraft().timer.renderPartialTicks;
        final float f5 = entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * Minecraft.getMinecraft().timer.renderPartialTicks;
        final double d2 = interpolate(entity.prevPosX, entity.posX, f) - Minecraft.getMinecraft().getRenderManager().viewerPosX;
        final double d3 = interpolate(entity.prevPosY + f3, entity.posY + f3, f) - Minecraft.getMinecraft().getRenderManager().viewerPosY;
        final double d4 = interpolate(entity.prevPosZ, entity.posZ, f) - Minecraft.getMinecraft().getRenderManager().viewerPosZ;
        GL11.glColor4f(new Color(n2).getRed() / 255.0f, new Color(n2).getGreen() / 255.0f, new Color(n2).getBlue() / 255.0f, 0.15f);
        for (int i = 0; i <= n; ++i) {
            GL11.glVertex3d(d2 + d * Math.cos(i * 3.141592653589793 * 2.0 / n), d3, d4 + d * Math.sin(i * 3.141592653589793 * 2.0 / n));
        }
        GL11.glEnd();
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glEnable(2929);
        GL11.glDisable(2848);
        GL11.glEnable(2929);
        GL11.glEnable(3553);
        GL11.glPopMatrix();
    }
    
    public static Color getGradientOffset(final Color color, final Color color2, double d) {
        if (d > 1.0) {
            final double d2 = d % 1.0;
            final int n = (int)d;
            d = ((n % 2 == 0) ? d2 : (1.0 - d2));
        }
        final double d2 = 1.0 - d;
        final int n = (int)(color.getRed() * d2 + color2.getRed() * d);
        final int n2 = (int)(color.getGreen() * d2 + color2.getGreen() * d);
        final int n3 = (int)(color.getBlue() * d2 + color2.getBlue() * d);
        return new Color(n, n2, n3);
    }
}
