//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Luni\Documents\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.entity.Entity
 *  org.lwjgl.opengl.GL11
 */
package dev._3000IQPlay.experium.util;

import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class ChinaHatUtil {
    public static double interpolate(double d, double d2, double d3) {
        return d + (d2 - d) * d3;
    }

    public static void drawHat(Entity entity, double d, float f, int n, float f2, float f3, int n2) {
        GL11.glPushMatrix();
        GL11.glDisable((int)3553);
        GL11.glEnable((int)2848);
        GL11.glHint((int)3154, (int)4354);
        GL11.glDepthMask((boolean)false);
        GL11.glLineWidth((float)f2);
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)2929);
        GL11.glBegin((int)3);
        float f4 = entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * Minecraft.getMinecraft().timer.renderPartialTicks;
        float f5 = entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * Minecraft.getMinecraft().timer.renderPartialTicks;
        double d2 = ChinaHatUtil.interpolate(entity.prevPosX, entity.posX, f) - Minecraft.getMinecraft().getRenderManager().viewerPosX;
        double d3 = ChinaHatUtil.interpolate(entity.prevPosY + (double)f3, entity.posY + (double)f3, f) - Minecraft.getMinecraft().getRenderManager().viewerPosY;
        double d4 = ChinaHatUtil.interpolate(entity.prevPosZ, entity.posZ, f) - Minecraft.getMinecraft().getRenderManager().viewerPosZ;
        GL11.glColor4f((float)((float)new Color(n2).getRed() / 255.0f), (float)((float)new Color(n2).getGreen() / 255.0f), (float)((float)new Color(n2).getBlue() / 255.0f), (float)0.15f);
        for (int i = 0; i <= n; ++i) {
            GL11.glVertex3d((double)(d2 + d * Math.cos((double)i * Math.PI * 2.0 / (double)n)), (double)d3, (double)(d4 + d * Math.sin((double)i * Math.PI * 2.0 / (double)n)));
        }
        GL11.glEnd();
        GL11.glDepthMask((boolean)true);
        GL11.glDisable((int)3042);
        GL11.glEnable((int)2929);
        GL11.glDisable((int)2848);
        GL11.glEnable((int)2929);
        GL11.glEnable((int)3553);
        GL11.glPopMatrix();
    }

    public static Color getGradientOffset(Color color, Color color2, double d) {
        int n;
        double d2;
        if (d > 1.0) {
            d2 = d % 1.0;
            n = (int)d;
            d = n % 2 == 0 ? d2 : 1.0 - d2;
        }
        d2 = 1.0 - d;
        n = (int)((double)color.getRed() * d2 + (double)color2.getRed() * d);
        int n2 = (int)((double)color.getGreen() * d2 + (double)color2.getGreen() * d);
        int n3 = (int)((double)color.getBlue() * d2 + (double)color2.getBlue() * d);
        return new Color(n, n2, n3);
    }
}

