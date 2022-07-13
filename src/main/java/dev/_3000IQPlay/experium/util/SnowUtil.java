//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Luni\Documents\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.ScaledResolution
 *  net.minecraft.client.renderer.BufferBuilder
 *  net.minecraft.client.renderer.GLAllocation
 *  net.minecraft.client.renderer.GlStateManager
 *  net.minecraft.client.renderer.GlStateManager$DestFactor
 *  net.minecraft.client.renderer.GlStateManager$SourceFactor
 *  net.minecraft.client.renderer.OpenGlHelper
 *  net.minecraft.client.renderer.Tessellator
 *  net.minecraft.client.renderer.vertex.DefaultVertexFormats
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.util.math.AxisAlignedBB
 *  org.lwjgl.BufferUtils
 *  org.lwjgl.opengl.GL11
 */
package dev._3000IQPlay.experium.util;

import dev._3000IQPlay.experium.util.GLUProjection;
import java.awt.Color;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

public final class SnowUtil {
    private static final IntBuffer VIEWPORT = GLAllocation.createDirectIntBuffer((int)16);
    private static final FloatBuffer MODELVIEW = GLAllocation.createDirectFloatBuffer((int)16);
    private static final FloatBuffer PROJECTION = GLAllocation.createDirectFloatBuffer((int)16);

    public static void updateModelViewProjectionMatrix() {
        GL11.glGetFloat((int)2982, (FloatBuffer)MODELVIEW);
        GL11.glGetFloat((int)2983, (FloatBuffer)PROJECTION);
        GL11.glGetInteger((int)2978, (IntBuffer)VIEWPORT);
        ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());
        GLUProjection.getInstance().updateMatrices(VIEWPORT, MODELVIEW, PROJECTION, (float)res.getScaledWidth() / (float)Minecraft.getMinecraft().displayWidth, (float)res.getScaledHeight() / (float)Minecraft.getMinecraft().displayHeight);
    }

    public static void DrawPolygon(double x, double y, int radius, int sides, int color) {
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glBlendFunc((int)770, (int)771);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(6, DefaultVertexFormats.POSITION);
        float alpha = (float)(color >> 24 & 0xFF) / 255.0f;
        float red = (float)(color >> 16 & 0xFF) / 255.0f;
        float green = (float)(color >> 8 & 0xFF) / 255.0f;
        float blue = (float)(color & 0xFF) / 255.0f;
        bufferbuilder.pos(x, y, 0.0).endVertex();
        double TWICE_PI = Math.PI * 2;
        for (int i = 0; i <= sides; ++i) {
            double angle = Math.PI * 2 * (double)i / (double)sides + Math.toRadians(180.0);
            bufferbuilder.pos(x + Math.sin(angle) * (double)radius, y + Math.cos(angle) * (double)radius, 0.0).endVertex();
        }
        tessellator.draw();
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
    }

    public static void drawRect(float x, float y, float w, float h, int color) {
        float alpha = (float)(color >> 24 & 0xFF) / 255.0f;
        float red = (float)(color >> 16 & 0xFF) / 255.0f;
        float green = (float)(color >> 8 & 0xFF) / 255.0f;
        float blue = (float)(color & 0xFF) / 255.0f;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate((int)770, (int)771, (int)1, (int)0);
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos((double)x, (double)h, 0.0).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos((double)w, (double)h, 0.0).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos((double)w, (double)y, 0.0).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos((double)x, (double)y, 0.0).color(red, green, blue, alpha).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawRect(float x, float y, float w, float h, int color, float alpha) {
        float red = (float)(color >> 16 & 0xFF) / 255.0f;
        float green = (float)(color >> 8 & 0xFF) / 255.0f;
        float blue = (float)(color & 0xFF) / 255.0f;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate((int)770, (int)771, (int)1, (int)0);
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos((double)x, (double)h, 0.0).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos((double)w, (double)h, 0.0).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos((double)w, (double)y, 0.0).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos((double)x, (double)y, 0.0).color(red, green, blue, alpha).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawGradientRect(float left, float top, float right, float bottom, int startColor, int endColor) {
        float f = (float)(startColor >> 24 & 0xFF) / 255.0f;
        float f1 = (float)(startColor >> 16 & 0xFF) / 255.0f;
        float f2 = (float)(startColor >> 8 & 0xFF) / 255.0f;
        float f3 = (float)(startColor & 0xFF) / 255.0f;
        float f4 = (float)(endColor >> 24 & 0xFF) / 255.0f;
        float f5 = (float)(endColor >> 16 & 0xFF) / 255.0f;
        float f6 = (float)(endColor >> 8 & 0xFF) / 255.0f;
        float f7 = (float)(endColor & 0xFF) / 255.0f;
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate((GlStateManager.SourceFactor)GlStateManager.SourceFactor.SRC_ALPHA, (GlStateManager.DestFactor)GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, (GlStateManager.SourceFactor)GlStateManager.SourceFactor.ONE, (GlStateManager.DestFactor)GlStateManager.DestFactor.ZERO);
        GlStateManager.shadeModel((int)7425);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos((double)right, (double)top, 0.0).color(f1, f2, f3, f).endVertex();
        bufferbuilder.pos((double)left, (double)top, 0.0).color(f1, f2, f3, f).endVertex();
        bufferbuilder.pos((double)left, (double)bottom, 0.0).color(f5, f6, f7, f4).endVertex();
        bufferbuilder.pos((double)right, (double)bottom, 0.0).color(f5, f6, f7, f4).endVertex();
        tessellator.draw();
        GlStateManager.shadeModel((int)7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }

    public static void drawTriangle(float x, float y, float size, float theta, int color) {
        GL11.glTranslated((double)x, (double)y, (double)0.0);
        GL11.glRotatef((float)(180.0f + theta), (float)0.0f, (float)0.0f, (float)1.0f);
        float alpha = (float)(color >> 24 & 0xFF) / 255.0f;
        float red = (float)(color >> 16 & 0xFF) / 255.0f;
        float green = (float)(color >> 8 & 0xFF) / 255.0f;
        float blue = (float)(color & 0xFF) / 255.0f;
        GL11.glColor4f((float)red, (float)green, (float)blue, (float)alpha);
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glEnable((int)2848);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glLineWidth((float)1.0f);
        GL11.glBegin((int)6);
        GL11.glVertex2d((double)0.0, (double)(1.0f * size));
        GL11.glVertex2d((double)(1.0f * size), (double)(-(1.0f * size)));
        GL11.glVertex2d((double)(-(1.0f * size)), (double)(-(1.0f * size)));
        GL11.glEnd();
        GL11.glDisable((int)2848);
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
        GL11.glRotatef((float)(-180.0f - theta), (float)0.0f, (float)0.0f, (float)1.0f);
        GL11.glTranslated((double)(-x), (double)(-y), (double)0.0);
    }

    public static void drawOutlineRect(float x, float y, float w, float h, float thickness, int c) {
        SnowUtil.drawRect(x, y, x - thickness, h, c);
        SnowUtil.drawRect(w + thickness, y, w, h, c);
        SnowUtil.drawRect(x, y, w, y - thickness, c);
        SnowUtil.drawRect(x, h + thickness, w, h, c);
    }

    public static void drawLine(float x, float y, float x1, float y1, float thickness, int hex) {
        float red = (float)(hex >> 16 & 0xFF) / 255.0f;
        float green = (float)(hex >> 8 & 0xFF) / 255.0f;
        float blue = (float)(hex & 0xFF) / 255.0f;
        float alpha = (float)(hex >> 24 & 0xFF) / 255.0f;
        GlStateManager.pushMatrix();
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate((int)770, (int)771, (int)1, (int)0);
        GlStateManager.shadeModel((int)7425);
        GL11.glLineWidth((float)thickness);
        GL11.glEnable((int)2848);
        GL11.glHint((int)3154, (int)4354);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos((double)x, (double)y, 0.0).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos((double)x1, (double)y1, 0.0).color(red, green, blue, alpha).endVertex();
        tessellator.draw();
        GlStateManager.shadeModel((int)7424);
        GL11.glDisable((int)2848);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
        GlStateManager.popMatrix();
    }

    public static void drawLine3D(float x, float y, float z, float x1, float y1, float z1, float thickness, int hex) {
        float red = (float)(hex >> 16 & 0xFF) / 255.0f;
        float green = (float)(hex >> 8 & 0xFF) / 255.0f;
        float blue = (float)(hex & 0xFF) / 255.0f;
        float alpha = (float)(hex >> 24 & 0xFF) / 255.0f;
        GlStateManager.pushMatrix();
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate((int)770, (int)771, (int)1, (int)0);
        GlStateManager.shadeModel((int)7425);
        GL11.glLineWidth((float)thickness);
        GL11.glEnable((int)2848);
        GL11.glHint((int)3154, (int)4354);
        GlStateManager.disableDepth();
        GL11.glEnable((int)34383);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(1, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos((double)x, (double)y, (double)z).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos((double)x1, (double)y1, (double)z1).color(red, green, blue, alpha).endVertex();
        tessellator.draw();
        GlStateManager.shadeModel((int)7424);
        GL11.glDisable((int)2848);
        GlStateManager.enableDepth();
        GL11.glDisable((int)34383);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
        GlStateManager.popMatrix();
    }

    public static void drawBoundingBox(AxisAlignedBB bb, float width, float red, float green, float blue, float alpha) {
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableDepth();
        GlStateManager.tryBlendFuncSeparate((int)770, (int)771, (int)0, (int)1);
        GlStateManager.disableTexture2D();
        GlStateManager.depthMask((boolean)false);
        GL11.glEnable((int)2848);
        GL11.glHint((int)3154, (int)4354);
        GL11.glLineWidth((float)width);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos(bb.minX, bb.minY, bb.minZ).color(red, green, blue, 0.0f).endVertex();
        bufferbuilder.pos(bb.minX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY, bb.maxZ).color(red, green, blue, 0.0f).endVertex();
        bufferbuilder.pos(bb.minX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY, bb.maxZ).color(red, green, blue, 0.0f).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY, bb.minZ).color(red, green, blue, 0.0f).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.minZ).color(red, green, blue, 0.0f).endVertex();
        tessellator.draw();
        GL11.glDisable((int)2848);
        GlStateManager.depthMask((boolean)true);
        GlStateManager.enableDepth();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

    public static void drawBoundingBox(AxisAlignedBB bb, float width, int color) {
        float alpha = (float)(color >> 24 & 0xFF) / 255.0f;
        float red = (float)(color >> 16 & 0xFF) / 255.0f;
        float green = (float)(color >> 8 & 0xFF) / 255.0f;
        float blue = (float)(color & 0xFF) / 255.0f;
        SnowUtil.drawBoundingBox(bb, width, red, green, blue, alpha);
    }

    public static void drawPlane(double x, double y, double z, AxisAlignedBB bb, float width, int color) {
        GL11.glPushMatrix();
        GL11.glTranslated((double)x, (double)y, (double)z);
        SnowUtil.drawPlane(bb, width, color);
        GL11.glPopMatrix();
    }

    public static void drawPlane(AxisAlignedBB axisalignedbb, float width, int color) {
        GlStateManager.pushMatrix();
        GlStateManager.glLineWidth((float)width);
        GlStateManager.enableBlend();
        GlStateManager.disableDepth();
        GlStateManager.tryBlendFuncSeparate((GlStateManager.SourceFactor)GlStateManager.SourceFactor.SRC_ALPHA, (GlStateManager.DestFactor)GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, (GlStateManager.SourceFactor)GlStateManager.SourceFactor.ONE, (GlStateManager.DestFactor)GlStateManager.DestFactor.ONE);
        GlStateManager.disableTexture2D();
        GlStateManager.depthMask((boolean)false);
        GL11.glEnable((int)2848);
        GL11.glHint((int)3154, (int)4354);
        SnowUtil.drawPlane(axisalignedbb, color);
        GL11.glDisable((int)2848);
        GlStateManager.depthMask((boolean)true);
        GlStateManager.enableDepth();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

    public static void drawPlane(AxisAlignedBB boundingBox, int color) {
        float alpha = (float)(color >> 24 & 0xFF) / 255.0f;
        float red = (float)(color >> 16 & 0xFF) / 255.0f;
        float green = (float)(color >> 8 & 0xFF) / 255.0f;
        float blue = (float)(color & 0xFF) / 255.0f;
        double minX = boundingBox.minX;
        double minY = boundingBox.minY;
        double minZ = boundingBox.minZ;
        double maxX = boundingBox.maxX;
        double maxY = boundingBox.maxY;
        double maxZ = boundingBox.maxZ;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos(minX, minY, minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(maxX, minY, maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(minX, minY, maxZ).color(red, green, blue, 0.0f).endVertex();
        bufferbuilder.pos(maxZ, minY, minZ).color(red, green, blue, alpha).endVertex();
        tessellator.draw();
    }

    public static void drawFilledBox(AxisAlignedBB bb, int color) {
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.disableDepth();
        GlStateManager.tryBlendFuncSeparate((int)770, (int)771, (int)0, (int)1);
        GlStateManager.disableTexture2D();
        GlStateManager.depthMask((boolean)false);
        float alpha = (float)(color >> 24 & 0xFF) / 255.0f;
        float red = (float)(color >> 16 & 0xFF) / 255.0f;
        float green = (float)(color >> 8 & 0xFF) / 255.0f;
        float blue = (float)(color & 0xFF) / 255.0f;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos(bb.minX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.maxX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.minY, bb.minZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.minY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY, bb.maxZ).color(red, green, blue, alpha).endVertex();
        bufferbuilder.pos(bb.minX, bb.maxY, bb.minZ).color(red, green, blue, alpha).endVertex();
        tessellator.draw();
        GlStateManager.depthMask((boolean)true);
        GlStateManager.enableDepth();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

    public static void glScissor(float x, float y, float x1, float y1, ScaledResolution sr) {
        GL11.glScissor((int)((int)(x * (float)sr.getScaleFactor())), (int)((int)((float)Minecraft.getMinecraft().displayHeight - y1 * (float)sr.getScaleFactor())), (int)((int)((x1 - x) * (float)sr.getScaleFactor())), (int)((int)((y1 - y) * (float)sr.getScaleFactor())));
    }

    public static void glBillboard(float x, float y, float z) {
        float scale = 0.02666667f;
        GlStateManager.translate((double)((double)x - Minecraft.getMinecraft().getRenderManager().renderPosX), (double)((double)y - Minecraft.getMinecraft().getRenderManager().renderPosY), (double)((double)z - Minecraft.getMinecraft().getRenderManager().renderPosZ));
        GlStateManager.glNormal3f((float)0.0f, (float)1.0f, (float)0.0f);
        GlStateManager.rotate((float)(-Minecraft.getMinecraft().player.rotationYaw), (float)0.0f, (float)1.0f, (float)0.0f);
        GlStateManager.rotate((float)Minecraft.getMinecraft().player.rotationPitch, (float)(Minecraft.getMinecraft().gameSettings.thirdPersonView == 2 ? -1.0f : 1.0f), (float)0.0f, (float)0.0f);
        GlStateManager.scale((float)(-scale), (float)(-scale), (float)scale);
    }

    public static void glBillboardDistanceScaled(float x, float y, float z, EntityPlayer player, float scale) {
        SnowUtil.glBillboard(x, y, z);
        int distance = (int)player.getDistance((double)x, (double)y, (double)z);
        float scaleDistance = (float)distance / 2.0f / (2.0f + (2.0f - scale));
        if (scaleDistance < 1.0f) {
            scaleDistance = 1.0f;
        }
        GlStateManager.scale((float)scaleDistance, (float)scaleDistance, (float)scaleDistance);
    }

    public static void drawTexture(float x, float y, float textureX, float textureY, float width, float height) {
        float f = 0.00390625f;
        float f1 = 0.00390625f;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos((double)x, (double)(y + height), 0.0).tex((double)(textureX * f), (double)((textureY + height) * f1)).endVertex();
        bufferbuilder.pos((double)(x + width), (double)(y + height), 0.0).tex((double)((textureX + width) * f), (double)((textureY + height) * f1)).endVertex();
        bufferbuilder.pos((double)(x + width), (double)y, 0.0).tex((double)((textureX + width) * f), (double)(textureY * f1)).endVertex();
        bufferbuilder.pos((double)x, (double)y, 0.0).tex((double)(textureX * f), (double)(textureY * f1)).endVertex();
        tessellator.draw();
    }

    public static void drawTexture(float x, float y, float width, float height, float u, float v, float t, float s) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(4, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos((double)(x + width), (double)y, 0.0).tex((double)t, (double)v).endVertex();
        bufferbuilder.pos((double)x, (double)y, 0.0).tex((double)u, (double)v).endVertex();
        bufferbuilder.pos((double)x, (double)(y + height), 0.0).tex((double)u, (double)s).endVertex();
        bufferbuilder.pos((double)x, (double)(y + height), 0.0).tex((double)u, (double)s).endVertex();
        bufferbuilder.pos((double)(x + width), (double)(y + height), 0.0).tex((double)t, (double)s).endVertex();
        bufferbuilder.pos((double)(x + width), (double)y, 0.0).tex((double)t, (double)v).endVertex();
        tessellator.draw();
    }

    public static final void DrawNodusBetterRect(double x, double y, double x1, double y1, int color2, int color) {
        GL11.glEnable((int)3042);
        GL11.glEnable((int)2848);
        SnowUtil.drawRect((int)x, (int)y, (int)x1, (int)y1, color);
        GL11.glScalef((float)0.5f, (float)0.5f, (float)0.5f);
        SnowUtil.drawRect((int)x * 2 - 1, (int)y * 2, (int)x * 2, (int)y1 * 2 - 1, color2);
        SnowUtil.drawRect((int)x * 2, (int)y * 2 - 1, (int)x1 * 2, (int)y * 2, color2);
        SnowUtil.drawRect((int)x1 * 2, (int)y * 2, (int)x1 * 2 + 1, (int)y1 * 2 - 1, color2);
        SnowUtil.drawRect((int)x * 2, (int)y1 * 2 - 1, (int)x1 * 2, (int)y1 * 2, color2);
        GL11.glDisable((int)3042);
        GL11.glScalef((float)2.0f, (float)2.0f, (float)2.0f);
    }

    public static final void DrawNodusRect(float par0, float par1, float par2, float par3, int par4) {
        float var5;
        if (par0 < par2) {
            var5 = par0;
            par0 = par2;
            par2 = var5;
        }
        if (par1 < par3) {
            var5 = par1;
            par1 = par3;
            par3 = var5;
        }
        float var10 = (float)(par4 >> 24 & 0xFF) / 255.0f;
        float var6 = (float)(par4 >> 16 & 0xFF) / 255.0f;
        float var7 = (float)(par4 >> 8 & 0xFF) / 255.0f;
        float var8 = (float)(par4 & 0xFF) / 255.0f;
        Tessellator tessellator = Tessellator.getInstance();
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glDisable((int)2896);
        OpenGlHelper.glBlendFunc((int)770, (int)771, (int)1, (int)0);
        GL11.glColor4f((float)var6, (float)var7, (float)var8, (float)var10);
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos((double)par0, (double)par3, 0.0).color(var6, var7, var8, var10).endVertex();
        bufferbuilder.pos((double)par2, (double)par3, 0.0).color(var6, var7, var8, var10).endVertex();
        bufferbuilder.pos((double)par2, (double)par1, 0.0).color(var6, var7, var8, var10).endVertex();
        bufferbuilder.pos((double)par0, (double)par1, 0.0).color(var6, var7, var8, var10).endVertex();
        tessellator.draw();
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
    }

    public static void drawSplitString(String p_Name, int p_X, int p_Y, int p_K, int p_Color) {
        Minecraft.getMinecraft().fontRenderer.drawSplitString(p_Name, p_X, p_Y, p_K, p_Color);
    }

    public static void drawBorderedRect(int x, int y, int x1, int y1, int color, float lineWidth, int color1) {
        SnowUtil.drawRect(x, y, x1, y1, color);
        SnowUtil.setupOverlayRendering();
        SnowUtil.disableDefaults();
        GL11.glColor4d((double)SnowUtil.getRedFromHex(color1), (double)SnowUtil.getGreenFromHex(color1), (double)SnowUtil.getBlueFromHex(color1), (double)SnowUtil.getAlphaFromHex(color1));
        GL11.glLineWidth((float)lineWidth);
        GL11.glBegin((int)1);
        GL11.glVertex2d((double)x, (double)y);
        GL11.glVertex2d((double)x, (double)y1);
        GL11.glVertex2d((double)x1, (double)y1);
        GL11.glVertex2d((double)x1, (double)y);
        GL11.glVertex2d((double)x, (double)y);
        GL11.glVertex2d((double)x1, (double)y);
        GL11.glVertex2d((double)x, (double)y1);
        GL11.glVertex2d((double)x1, (double)y1);
        GL11.glEnd();
        SnowUtil.enableDefaults();
    }

    public static void drawRect(int x, int y, int x1, int y1, int color, int p_CustomAlpha) {
        SnowUtil.setupOverlayRendering();
        SnowUtil.disableDefaults();
        GL11.glColor4d((double)SnowUtil.getRedFromHex(color), (double)SnowUtil.getGreenFromHex(color), (double)SnowUtil.getBlueFromHex(color), (double)(p_CustomAlpha > 0 ? (double)p_CustomAlpha : SnowUtil.getAlphaFromHex(color)));
        GL11.glBegin((int)7);
        GL11.glVertex2i((int)x1, (int)y);
        GL11.glVertex2i((int)x, (int)y);
        GL11.glVertex2i((int)x, (int)y1);
        GL11.glVertex2i((int)x1, (int)y1);
        GL11.glEnd();
        SnowUtil.enableDefaults();
    }

    public static void drawRoundedRect(int x, int y, int x1, int y1, int radius, int color, int p_CustomAlpha) {
        SnowUtil.disableDefaults();
        float newX = Math.abs(x + radius);
        float newY = Math.abs(y + radius);
        float newX1 = Math.abs(x1 - radius);
        float newY1 = Math.abs(y1 - radius);
        SnowUtil.drawRect(newX, newY, newX1, newY1, color);
        SnowUtil.drawRect(x, newY, newX, newY1, color);
        SnowUtil.drawRect(newX1, newY, x1, newY1, color);
        SnowUtil.drawRect(newX, y, newX1, newY, color);
        SnowUtil.drawRect(newX, newY1, newX1, y1, color);
        SnowUtil.drawQuarterCircle((int)newX, (int)newY, radius, 0, color, p_CustomAlpha);
        SnowUtil.drawQuarterCircle((int)newX1, (int)newY, radius, 1, color, p_CustomAlpha);
        SnowUtil.drawQuarterCircle((int)newX, (int)newY1, radius, 2, color, p_CustomAlpha);
        SnowUtil.drawQuarterCircle((int)newX1, (int)newY1, radius, 3, color, p_CustomAlpha);
        SnowUtil.enableDefaults();
    }

    public static void drawLine2D(int x, int y, int x1, int y1, int color, float lineWidth) {
        SnowUtil.setupOverlayRendering();
        SnowUtil.disableDefaults();
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)2848);
        GL11.glColor4d((double)SnowUtil.getRedFromHex(color), (double)SnowUtil.getGreenFromHex(color), (double)SnowUtil.getBlueFromHex(color), (double)SnowUtil.getAlphaFromHex(color));
        GL11.glBegin((int)1);
        GL11.glVertex2i((int)x, (int)y);
        GL11.glVertex2i((int)x1, (int)y1);
        GL11.glEnd();
        GL11.glDisable((int)2848);
        SnowUtil.enableDefaults();
    }

    public static void drawBorderedCircle(int x, int y, int radius, int color, float lineWidth, int color1) {
        SnowUtil.drawCircle(x, y, radius, color);
        SnowUtil.drawUnfilledCircle(x, y, radius, lineWidth, color1);
    }

    public static void drawUnfilledCircle(int x, int y, int radius, float lineWidth, int color) {
        SnowUtil.setupOverlayRendering();
        SnowUtil.disableDefaults();
        GL11.glColor4d((double)SnowUtil.getRedFromHex(color), (double)SnowUtil.getGreenFromHex(color), (double)SnowUtil.getBlueFromHex(color), (double)SnowUtil.getAlphaFromHex(color));
        GL11.glLineWidth((float)lineWidth);
        GL11.glEnable((int)2848);
        GL11.glBegin((int)2);
        for (int i = 0; i <= 360; ++i) {
            GL11.glVertex2d((double)((double)x + Math.sin((double)i * 3.141526 / 180.0) * (double)radius), (double)((double)y + Math.cos((double)i * 3.141526 / 180.0) * (double)radius));
        }
        GL11.glEnd();
        GL11.glDisable((int)2848);
        SnowUtil.enableDefaults();
    }

    public static void drawCircle(int x, int y, int radius, int color) {
        SnowUtil.setupOverlayRendering();
        SnowUtil.disableDefaults();
        GL11.glColor4d((double)SnowUtil.getRedFromHex(color), (double)SnowUtil.getGreenFromHex(color), (double)SnowUtil.getBlueFromHex(color), (double)SnowUtil.getAlphaFromHex(color));
        GL11.glBegin((int)9);
        for (int i = 0; i <= 360; ++i) {
            GL11.glVertex2d((double)((double)x + Math.sin((double)i * 3.141526 / 180.0) * (double)radius), (double)((double)y + Math.cos((double)i * 3.141526 / 180.0) * (double)radius));
        }
        GL11.glEnd();
        SnowUtil.enableDefaults();
    }

    public static void drawQuarterCircle(int x, int y, int radius, int mode2, int color, int p_CustomAlpha) {
        SnowUtil.disableDefaults();
        GL11.glColor4d((double)SnowUtil.getRedFromHex(color), (double)SnowUtil.getGreenFromHex(color), (double)SnowUtil.getBlueFromHex(color), (double)(p_CustomAlpha > 0 ? (double)p_CustomAlpha : SnowUtil.getAlphaFromHex(color)));
        GL11.glBegin((int)9);
        GL11.glVertex2d((double)x, (double)y);
        if (mode2 == 0) {
            for (int i = 0; i <= 90; ++i) {
                GL11.glVertex2d((double)((double)x + Math.sin((double)i * 3.141526 / 180.0) * (double)(radius * -1)), (double)((double)y + Math.cos((double)i * 3.141526 / 180.0) * (double)(radius * -1)));
            }
        } else if (mode2 == 1) {
            for (int i = 90; i <= 180; ++i) {
                GL11.glVertex2d((double)((double)x + Math.sin((double)i * 3.141526 / 180.0) * (double)radius), (double)((double)y + Math.cos((double)i * 3.141526 / 180.0) * (double)radius));
            }
        } else if (mode2 == 2) {
            for (int i = 90; i <= 180; ++i) {
                GL11.glVertex2d((double)((double)x + Math.sin((double)i * 3.141526 / 180.0) * (double)(radius * -1)), (double)((double)y + Math.cos((double)i * 3.141526 / 180.0) * (double)(radius * -1)));
            }
        } else if (mode2 == 3) {
            for (int i = 0; i <= 90; ++i) {
                GL11.glVertex2d((double)((double)x + Math.sin((double)i * 3.141526 / 180.0) * (double)radius), (double)((double)y + Math.cos((double)i * 3.141526 / 180.0) * (double)radius));
            }
        }
        GL11.glEnd();
        SnowUtil.enableDefaults();
    }

    public static double getAlphaFromHex(int color) {
        return (float)(color >> 24 & 0xFF) / 255.0f;
    }

    public static double getRedFromHex(int color) {
        return (float)(color >> 16 & 0xFF) / 255.0f;
    }

    public static double getGreenFromHex(int color) {
        return (float)(color >> 8 & 0xFF) / 255.0f;
    }

    public static double getBlueFromHex(int color) {
        return (float)(color & 0xFF) / 255.0f;
    }

    public static int getScreenWidth() {
        IntBuffer viewport = BufferUtils.createIntBuffer((int)16);
        GL11.glGetInteger((int)2978, (IntBuffer)viewport);
        return Math.round(viewport.get(2));
    }

    public static int getScreenHeight() {
        IntBuffer viewport = BufferUtils.createIntBuffer((int)16);
        GL11.glGetInteger((int)2978, (IntBuffer)viewport);
        return Math.round(viewport.get(3));
    }

    public static void setupGradient() {
        GL11.glDisable((int)3553);
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3008);
        GL11.glShadeModel((int)7425);
    }

    public static void unsetupGradient() {
        GL11.glShadeModel((int)7424);
        GL11.glDisable((int)3042);
        GL11.glEnable((int)3008);
        GL11.glEnable((int)3553);
    }

    public static void setupOverlayRendering() {
        GL11.glClear((int)256);
        GL11.glMatrixMode((int)5889);
        GL11.glLoadIdentity();
        GL11.glOrtho((double)0.0, (double)SnowUtil.getScreenWidth(), (double)SnowUtil.getScreenHeight(), (double)0.0, (double)1000.0, (double)3000.0);
        GL11.glMatrixMode((int)5888);
        GL11.glLoadIdentity();
        GL11.glTranslatef((float)0.0f, (float)0.0f, (float)-2000.0f);
    }

    public static void disableDefaults() {
        GL11.glEnable((int)3042);
        GL11.glDisable((int)2896);
        GL11.glDisable((int)3553);
    }

    public static void enableDefaults() {
        GL11.glDisable((int)3042);
        GL11.glEnable((int)3553);
        GL11.glEnable((int)2896);
    }

    public static void disableLighting() {
        GL11.glDisable((int)2896);
    }

    public static String trimStringToWidth(String substring, int width) {
        return Minecraft.getMinecraft().fontRenderer.trimStringToWidth(substring, width);
    }

    public static String trimStringToWidth(String text, int j, boolean b) {
        return Minecraft.getMinecraft().fontRenderer.trimStringToWidth(text, j, b);
    }

    public static void drawColorBox(AxisAlignedBB axisalignedbb, float red, float green, float blue, float alpha) {
        Tessellator ts = Tessellator.getInstance();
        BufferBuilder vb = ts.getBuffer();
        vb.begin(7, DefaultVertexFormats.POSITION_TEX);
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        ts.draw();
        vb.begin(7, DefaultVertexFormats.POSITION_TEX);
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        ts.draw();
        vb.begin(7, DefaultVertexFormats.POSITION_TEX);
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        ts.draw();
        vb.begin(7, DefaultVertexFormats.POSITION_TEX);
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        ts.draw();
        vb.begin(7, DefaultVertexFormats.POSITION_TEX);
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        ts.draw();
        vb.begin(7, DefaultVertexFormats.POSITION_TEX);
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(red, green, blue, alpha).endVertex();
        ts.draw();
    }

    public static void drawColorBox(AxisAlignedBB axisalignedbb, Color c) {
        Tessellator ts = Tessellator.getInstance();
        BufferBuilder vb = ts.getBuffer();
        vb.begin(7, DefaultVertexFormats.POSITION_TEX);
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
        ts.draw();
        vb.begin(7, DefaultVertexFormats.POSITION_TEX);
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
        ts.draw();
        vb.begin(7, DefaultVertexFormats.POSITION_TEX);
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
        ts.draw();
        vb.begin(7, DefaultVertexFormats.POSITION_TEX);
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
        ts.draw();
        vb.begin(7, DefaultVertexFormats.POSITION_TEX);
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
        ts.draw();
        vb.begin(7, DefaultVertexFormats.POSITION_TEX);
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
        vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
        vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ).color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha()).endVertex();
        ts.draw();
    }

    public static void drawBox(AxisAlignedBB boundingBox) {
        assert (boundingBox != null);
        GlStateManager.glBegin((int)7);
        GlStateManager.glVertex3f((float)((float)boundingBox.minX), (float)((float)boundingBox.minY), (float)((float)boundingBox.maxZ));
        GlStateManager.glVertex3f((float)((float)boundingBox.maxX), (float)((float)boundingBox.minY), (float)((float)boundingBox.maxZ));
        GlStateManager.glVertex3f((float)((float)boundingBox.maxX), (float)((float)boundingBox.maxY), (float)((float)boundingBox.maxZ));
        GlStateManager.glVertex3f((float)((float)boundingBox.minX), (float)((float)boundingBox.maxY), (float)((float)boundingBox.maxZ));
        GlStateManager.glEnd();
        GlStateManager.glBegin((int)7);
        GlStateManager.glVertex3f((float)((float)boundingBox.maxX), (float)((float)boundingBox.minY), (float)((float)boundingBox.maxZ));
        GlStateManager.glVertex3f((float)((float)boundingBox.minX), (float)((float)boundingBox.minY), (float)((float)boundingBox.maxZ));
        GlStateManager.glVertex3f((float)((float)boundingBox.minX), (float)((float)boundingBox.maxY), (float)((float)boundingBox.maxZ));
        GlStateManager.glVertex3f((float)((float)boundingBox.maxX), (float)((float)boundingBox.maxY), (float)((float)boundingBox.maxZ));
        GlStateManager.glEnd();
        GlStateManager.glBegin((int)7);
        GlStateManager.glVertex3f((float)((float)boundingBox.minX), (float)((float)boundingBox.minY), (float)((float)boundingBox.minZ));
        GlStateManager.glVertex3f((float)((float)boundingBox.minX), (float)((float)boundingBox.minY), (float)((float)boundingBox.maxZ));
        GlStateManager.glVertex3f((float)((float)boundingBox.minX), (float)((float)boundingBox.maxY), (float)((float)boundingBox.maxZ));
        GlStateManager.glVertex3f((float)((float)boundingBox.minX), (float)((float)boundingBox.maxY), (float)((float)boundingBox.minZ));
        GlStateManager.glEnd();
        GlStateManager.glBegin((int)7);
        GlStateManager.glVertex3f((float)((float)boundingBox.minX), (float)((float)boundingBox.minY), (float)((float)boundingBox.maxZ));
        GlStateManager.glVertex3f((float)((float)boundingBox.minX), (float)((float)boundingBox.minY), (float)((float)boundingBox.minZ));
        GlStateManager.glVertex3f((float)((float)boundingBox.minX), (float)((float)boundingBox.maxY), (float)((float)boundingBox.minZ));
        GlStateManager.glVertex3f((float)((float)boundingBox.minX), (float)((float)boundingBox.maxY), (float)((float)boundingBox.maxZ));
        GlStateManager.glEnd();
        GlStateManager.glBegin((int)7);
        GlStateManager.glVertex3f((float)((float)boundingBox.maxX), (float)((float)boundingBox.minY), (float)((float)boundingBox.maxZ));
        GlStateManager.glVertex3f((float)((float)boundingBox.maxX), (float)((float)boundingBox.minY), (float)((float)boundingBox.minZ));
        GlStateManager.glVertex3f((float)((float)boundingBox.maxX), (float)((float)boundingBox.maxY), (float)((float)boundingBox.minZ));
        GlStateManager.glVertex3f((float)((float)boundingBox.maxX), (float)((float)boundingBox.maxY), (float)((float)boundingBox.maxZ));
        GlStateManager.glEnd();
        GlStateManager.glBegin((int)7);
        GlStateManager.glVertex3f((float)((float)boundingBox.maxX), (float)((float)boundingBox.minY), (float)((float)boundingBox.minZ));
        GlStateManager.glVertex3f((float)((float)boundingBox.maxX), (float)((float)boundingBox.minY), (float)((float)boundingBox.maxZ));
        GlStateManager.glVertex3f((float)((float)boundingBox.maxX), (float)((float)boundingBox.maxY), (float)((float)boundingBox.maxZ));
        GlStateManager.glVertex3f((float)((float)boundingBox.maxX), (float)((float)boundingBox.maxY), (float)((float)boundingBox.minZ));
        GlStateManager.glEnd();
        GlStateManager.glBegin((int)7);
        GlStateManager.glVertex3f((float)((float)boundingBox.minX), (float)((float)boundingBox.minY), (float)((float)boundingBox.minZ));
        GlStateManager.glVertex3f((float)((float)boundingBox.maxX), (float)((float)boundingBox.minY), (float)((float)boundingBox.minZ));
        GlStateManager.glVertex3f((float)((float)boundingBox.maxX), (float)((float)boundingBox.maxY), (float)((float)boundingBox.minZ));
        GlStateManager.glVertex3f((float)((float)boundingBox.minX), (float)((float)boundingBox.maxY), (float)((float)boundingBox.minZ));
        GlStateManager.glEnd();
        GlStateManager.glBegin((int)7);
        GlStateManager.glVertex3f((float)((float)boundingBox.maxX), (float)((float)boundingBox.minY), (float)((float)boundingBox.minZ));
        GlStateManager.glVertex3f((float)((float)boundingBox.minX), (float)((float)boundingBox.minY), (float)((float)boundingBox.minZ));
        GlStateManager.glVertex3f((float)((float)boundingBox.minX), (float)((float)boundingBox.maxY), (float)((float)boundingBox.minZ));
        GlStateManager.glVertex3f((float)((float)boundingBox.maxX), (float)((float)boundingBox.maxY), (float)((float)boundingBox.minZ));
        GlStateManager.glEnd();
        GlStateManager.glBegin((int)7);
        GlStateManager.glVertex3f((float)((float)boundingBox.minX), (float)((float)boundingBox.maxY), (float)((float)boundingBox.minZ));
        GlStateManager.glVertex3f((float)((float)boundingBox.maxX), (float)((float)boundingBox.maxY), (float)((float)boundingBox.minZ));
        GlStateManager.glVertex3f((float)((float)boundingBox.maxX), (float)((float)boundingBox.maxY), (float)((float)boundingBox.maxZ));
        GlStateManager.glVertex3f((float)((float)boundingBox.minX), (float)((float)boundingBox.maxY), (float)((float)boundingBox.maxZ));
        GlStateManager.glEnd();
        GlStateManager.glBegin((int)7);
        GlStateManager.glVertex3f((float)((float)boundingBox.maxX), (float)((float)boundingBox.maxY), (float)((float)boundingBox.minZ));
        GlStateManager.glVertex3f((float)((float)boundingBox.minX), (float)((float)boundingBox.maxY), (float)((float)boundingBox.minZ));
        GlStateManager.glVertex3f((float)((float)boundingBox.minX), (float)((float)boundingBox.maxY), (float)((float)boundingBox.maxZ));
        GlStateManager.glVertex3f((float)((float)boundingBox.maxX), (float)((float)boundingBox.maxY), (float)((float)boundingBox.maxZ));
        GlStateManager.glEnd();
        GlStateManager.glBegin((int)7);
        GlStateManager.glVertex3f((float)((float)boundingBox.minX), (float)((float)boundingBox.minY), (float)((float)boundingBox.minZ));
        GlStateManager.glVertex3f((float)((float)boundingBox.maxX), (float)((float)boundingBox.minY), (float)((float)boundingBox.minZ));
        GlStateManager.glVertex3f((float)((float)boundingBox.maxX), (float)((float)boundingBox.minY), (float)((float)boundingBox.maxZ));
        GlStateManager.glVertex3f((float)((float)boundingBox.minX), (float)((float)boundingBox.minY), (float)((float)boundingBox.maxZ));
        GlStateManager.glEnd();
        GlStateManager.glBegin((int)7);
        GlStateManager.glVertex3f((float)((float)boundingBox.maxX), (float)((float)boundingBox.minY), (float)((float)boundingBox.minZ));
        GlStateManager.glVertex3f((float)((float)boundingBox.minX), (float)((float)boundingBox.minY), (float)((float)boundingBox.minZ));
        GlStateManager.glVertex3f((float)((float)boundingBox.minX), (float)((float)boundingBox.minY), (float)((float)boundingBox.maxZ));
        GlStateManager.glVertex3f((float)((float)boundingBox.maxX), (float)((float)boundingBox.minY), (float)((float)boundingBox.maxZ));
        GlStateManager.glEnd();
    }

    public static void drawOutlinedBox(AxisAlignedBB bb) {
        GL11.glBegin((int)1);
        GL11.glVertex3d((double)bb.minX, (double)bb.minY, (double)bb.minZ);
        GL11.glVertex3d((double)bb.maxX, (double)bb.minY, (double)bb.minZ);
        GL11.glVertex3d((double)bb.maxX, (double)bb.minY, (double)bb.minZ);
        GL11.glVertex3d((double)bb.maxX, (double)bb.minY, (double)bb.maxZ);
        GL11.glVertex3d((double)bb.maxX, (double)bb.minY, (double)bb.maxZ);
        GL11.glVertex3d((double)bb.minX, (double)bb.minY, (double)bb.maxZ);
        GL11.glVertex3d((double)bb.minX, (double)bb.minY, (double)bb.maxZ);
        GL11.glVertex3d((double)bb.minX, (double)bb.minY, (double)bb.minZ);
        GL11.glVertex3d((double)bb.minX, (double)bb.minY, (double)bb.minZ);
        GL11.glVertex3d((double)bb.minX, (double)bb.maxY, (double)bb.minZ);
        GL11.glVertex3d((double)bb.maxX, (double)bb.minY, (double)bb.minZ);
        GL11.glVertex3d((double)bb.maxX, (double)bb.maxY, (double)bb.minZ);
        GL11.glVertex3d((double)bb.maxX, (double)bb.minY, (double)bb.maxZ);
        GL11.glVertex3d((double)bb.maxX, (double)bb.maxY, (double)bb.maxZ);
        GL11.glVertex3d((double)bb.minX, (double)bb.minY, (double)bb.maxZ);
        GL11.glVertex3d((double)bb.minX, (double)bb.maxY, (double)bb.maxZ);
        GL11.glVertex3d((double)bb.minX, (double)bb.maxY, (double)bb.minZ);
        GL11.glVertex3d((double)bb.maxX, (double)bb.maxY, (double)bb.minZ);
        GL11.glVertex3d((double)bb.maxX, (double)bb.maxY, (double)bb.minZ);
        GL11.glVertex3d((double)bb.maxX, (double)bb.maxY, (double)bb.maxZ);
        GL11.glVertex3d((double)bb.maxX, (double)bb.maxY, (double)bb.maxZ);
        GL11.glVertex3d((double)bb.minX, (double)bb.maxY, (double)bb.maxZ);
        GL11.glVertex3d((double)bb.minX, (double)bb.maxY, (double)bb.maxZ);
        GL11.glVertex3d((double)bb.minX, (double)bb.maxY, (double)bb.minZ);
        GL11.glEnd();
    }

    public static void drawESPOutline(AxisAlignedBB bb, float red, float green, float blue, float alpha, float width) {
        GL11.glPushMatrix();
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glDisable((int)3553);
        GL11.glEnable((int)2848);
        GL11.glDisable((int)2929);
        GL11.glDepthMask((boolean)false);
        GL11.glLineWidth((float)width);
        GL11.glColor4f((float)(red / 255.0f), (float)(green / 255.0f), (float)(blue / 255.0f), (float)(alpha / 255.0f));
        SnowUtil.drawOutlinedBox(bb);
        GL11.glDisable((int)2848);
        GL11.glEnable((int)3553);
        GL11.glEnable((int)2929);
        GL11.glDepthMask((boolean)true);
        GL11.glDisable((int)3042);
        GL11.glPopMatrix();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
    }

    public static void drawESP(AxisAlignedBB bb, float red, float green, float blue, float alpha) {
        GL11.glPushMatrix();
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glDisable((int)3553);
        GL11.glEnable((int)2848);
        GL11.glDisable((int)2929);
        GL11.glDepthMask((boolean)false);
        GL11.glColor4f((float)(red / 255.0f), (float)(green / 255.0f), (float)(blue / 255.0f), (float)(alpha / 255.0f));
        SnowUtil.drawBox(bb);
        GL11.glDisable((int)2848);
        GL11.glEnable((int)3553);
        GL11.glEnable((int)2929);
        GL11.glDepthMask((boolean)true);
        GL11.glDisable((int)3042);
        GL11.glPopMatrix();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
    }
}

