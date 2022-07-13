//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Luni\Documents\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev._3000IQPlay.experium.mixin.mixins;

import org.spongepowered.asm.mixin.injection.Inject;
import dev._3000IQPlay.experium.util.RenderUtil;
import java.awt.Color;
import dev._3000IQPlay.experium.features.modules.client.Colors;
import net.minecraft.util.math.MathHelper;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import dev._3000IQPlay.experium.features.modules.render.CrystalModify;
import dev._3000IQPlay.experium.Experium;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.client.model.ModelBase;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Final;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.renderer.entity.RenderEnderCrystal;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ RenderEnderCrystal.class })
public abstract class MixinRenderEnderCrystal
{
    private static final ResourceLocation RES_ITEM_GLINT;
    @Final
    @Shadow
    private static ResourceLocation ENDER_CRYSTAL_TEXTURES;
    @Shadow
    public ModelBase modelEnderCrystal;
    @Shadow
    public ModelBase modelEnderCrystalNoBase;
    
    @Shadow
    public abstract void doRender(final EntityEnderCrystal p0, final double p1, final double p2, final double p3, final float p4, final float p5);
    
    @Redirect(method = { "doRender(Lnet/minecraft/entity/item/EntityEnderCrystal;DDDFF)V" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/ModelBase;render(Lnet/minecraft/entity/Entity;FFFFFF)V"))
    private void render1(final ModelBase var1, final Entity var2, final float var3, final float var4, final float var5, final float var6, final float var7, final float var8) {
        if (!Experium.moduleManager.isModuleEnabled(CrystalModify.class)) {
            var1.render(var2, var3, var4, var5, var6, var7, var8);
        }
    }
    
    @Redirect(method = { "doRender(Lnet/minecraft/entity/item/EntityEnderCrystal;DDDFF)V" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/ModelBase;render(Lnet/minecraft/entity/Entity;FFFFFF)V", ordinal = 1))
    private void render2(final ModelBase var1, final Entity var2, final float var3, final float var4, final float var5, final float var6, final float var7, final float var8) {
        if (!Experium.moduleManager.isModuleEnabled(CrystalModify.class)) {
            var1.render(var2, var3, var4, var5, var6, var7, var8);
        }
    }
    
    @Inject(method = { "doRender(Lnet/minecraft/entity/item/EntityEnderCrystal;DDDFF)V" }, at = { @At("RETURN") }, cancellable = true)
    public void IdoRender(final EntityEnderCrystal var1, final double var2, final double var4, final double var6, final float var8, final float var9, final CallbackInfo var10) {
        final Minecraft mc = Minecraft.getMinecraft();
        mc.gameSettings.fancyGraphics = false;
        if (Experium.moduleManager.isModuleEnabled(CrystalModify.class)) {
            GL11.glPushMatrix();
            final float var11 = var1.innerRotation + var9;
            GlStateManager.translate(var2, var4, var6);
            GlStateManager.scale((float)CrystalModify.INSTANCE.size.getValue(), (float)CrystalModify.INSTANCE.size.getValue(), (float)CrystalModify.INSTANCE.size.getValue());
            Minecraft.getMinecraft().getRenderManager().renderEngine.bindTexture(MixinRenderEnderCrystal.ENDER_CRYSTAL_TEXTURES);
            float var12 = MathHelper.sin(var11 * 0.2f) / 2.0f + 0.5f;
            var12 += var12 * var12;
            final float spinSpeed = CrystalModify.INSTANCE.crystalSpeed.getValue();
            final float bounceSpeed = CrystalModify.INSTANCE.crystalBounce.getValue();
            if (CrystalModify.INSTANCE.texture.getValue()) {
                if (var1.shouldShowBottom()) {
                    this.modelEnderCrystal.render((Entity)var1, 0.0f, var11 * spinSpeed, var12 * bounceSpeed, 0.0f, 0.0f, 0.0625f);
                }
                else {
                    this.modelEnderCrystalNoBase.render((Entity)var1, 0.0f, var11 * spinSpeed, var12 * bounceSpeed, 0.0f, 0.0f, 0.0625f);
                }
            }
            GL11.glPushAttrib(1048575);
            if (CrystalModify.INSTANCE.mode.getValue().equals(CrystalModify.modes.WIREFRAME)) {
                GL11.glPolygonMode(1032, 6913);
            }
            if (CrystalModify.INSTANCE.blendModes.getValue().equals(CrystalModify.BlendModes.Default)) {
                GL11.glBlendFunc(770, 771);
            }
            if (CrystalModify.INSTANCE.blendModes.getValue().equals(CrystalModify.BlendModes.Brighter)) {
                GL11.glBlendFunc(770, 32772);
            }
            GL11.glDisable(3008);
            GL11.glDisable(3553);
            GL11.glDisable(2896);
            GL11.glEnable(3042);
            GL11.glLineWidth(1.5f);
            GL11.glEnable(2960);
            GL11.glDisable(2929);
            GL11.glDepthMask(false);
            GL11.glEnable(10754);
            final Color visibleColor = CrystalModify.INSTANCE.colorSync.getValue() ? Colors.INSTANCE.getCurrentColor() : new Color(CrystalModify.INSTANCE.red.getValue(), CrystalModify.INSTANCE.green.getValue(), CrystalModify.INSTANCE.blue.getValue());
            final Color hiddenColor = CrystalModify.INSTANCE.colorSync.getValue() ? Colors.INSTANCE.getCurrentColor() : new Color(CrystalModify.INSTANCE.hiddenRed.getValue(), CrystalModify.INSTANCE.hiddenGreen.getValue(), CrystalModify.INSTANCE.hiddenBlue.getValue());
            final Color color;
            final Color outlineColor = color = (CrystalModify.INSTANCE.colorSync.getValue() ? Colors.INSTANCE.getCurrentColor() : new Color(CrystalModify.INSTANCE.outlineRed.getValue(), CrystalModify.INSTANCE.outlineGreen.getValue(), CrystalModify.INSTANCE.outlineBlue.getValue()));
            if (CrystalModify.INSTANCE.hiddenSync.getValue()) {
                GL11.glColor4f(visibleColor.getRed() / 255.0f, visibleColor.getGreen() / 255.0f, visibleColor.getBlue() / 255.0f, CrystalModify.INSTANCE.alpha.getValue() / 255.0f);
            }
            else {
                GL11.glColor4f(hiddenColor.getRed() / 255.0f, hiddenColor.getGreen() / 255.0f, hiddenColor.getBlue() / 255.0f, CrystalModify.INSTANCE.hiddenAlpha.getValue() / 255.0f);
            }
            if (var1.shouldShowBottom()) {
                this.modelEnderCrystal.render((Entity)var1, 0.0f, var11 * spinSpeed, var12 * bounceSpeed, 0.0f, 0.0f, 0.0625f);
            }
            else {
                this.modelEnderCrystalNoBase.render((Entity)var1, 0.0f, var11 * spinSpeed, var12 * bounceSpeed, 0.0f, 0.0f, 0.0625f);
            }
            GL11.glEnable(2929);
            GL11.glDepthMask(true);
            GL11.glColor4f(visibleColor.getRed() / 255.0f, visibleColor.getGreen() / 255.0f, visibleColor.getBlue() / 255.0f, CrystalModify.INSTANCE.alpha.getValue() / 255.0f);
            if (var1.shouldShowBottom()) {
                this.modelEnderCrystal.render((Entity)var1, 0.0f, var11 * spinSpeed, var12 * bounceSpeed, 0.0f, 0.0f, 0.0625f);
            }
            else {
                this.modelEnderCrystalNoBase.render((Entity)var1, 0.0f, var11 * spinSpeed, var12 * bounceSpeed, 0.0f, 0.0f, 0.0625f);
            }
            if (CrystalModify.INSTANCE.enchanted.getValue()) {
                mc.getTextureManager().bindTexture(MixinRenderEnderCrystal.RES_ITEM_GLINT);
                GL11.glTexCoord3d(1.0, 1.0, 1.0);
                GL11.glEnable(3553);
                GL11.glBlendFunc(768, 771);
                GL11.glColor4f(CrystalModify.INSTANCE.enchantRed.getValue() / 255.0f, CrystalModify.INSTANCE.enchantGreen.getValue() / 255.0f, CrystalModify.INSTANCE.enchantBlue.getValue() / 255.0f, CrystalModify.INSTANCE.enchantAlpha.getValue() / 255.0f);
                if (var1.shouldShowBottom()) {
                    this.modelEnderCrystal.render((Entity)var1, 0.0f, var11 * spinSpeed, var12 * bounceSpeed, 0.0f, 0.0f, 0.0625f);
                }
                else {
                    this.modelEnderCrystalNoBase.render((Entity)var1, 0.0f, var11 * spinSpeed, var12 * bounceSpeed, 0.0f, 0.0f, 0.0625f);
                }
                if (CrystalModify.INSTANCE.blendModes.getValue().equals(CrystalModify.BlendModes.Default)) {
                    GL11.glBlendFunc(768, 771);
                }
                if (CrystalModify.INSTANCE.blendModes.getValue().equals(CrystalModify.BlendModes.Brighter)) {
                    GL11.glBlendFunc(770, 32772);
                }
                else {
                    GL11.glBlendFunc(770, 771);
                }
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            }
            GL11.glEnable(3042);
            GL11.glEnable(2896);
            GL11.glEnable(3553);
            GL11.glEnable(3008);
            GL11.glPopAttrib();
            if (CrystalModify.INSTANCE.outline.getValue()) {
                if (CrystalModify.INSTANCE.outlineMode.getValue().equals(CrystalModify.outlineModes.WIRE)) {
                    GL11.glPushAttrib(1048575);
                    GL11.glPolygonMode(1032, 6913);
                    GL11.glDisable(3008);
                    GL11.glDisable(3553);
                    GL11.glDisable(2896);
                    GL11.glEnable(3042);
                    GL11.glBlendFunc(770, 771);
                    GL11.glLineWidth((float)CrystalModify.INSTANCE.lineWidth.getValue());
                    GL11.glEnable(2960);
                    GL11.glDisable(2929);
                    GL11.glDepthMask(false);
                    GL11.glEnable(10754);
                    GL11.glColor4f(outlineColor.getRed() / 255.0f, outlineColor.getGreen() / 255.0f, outlineColor.getBlue() / 255.0f, CrystalModify.INSTANCE.outlineAlpha.getValue() / 255.0f);
                    if (var1.shouldShowBottom()) {
                        this.modelEnderCrystal.render((Entity)var1, 0.0f, var11 * spinSpeed, var12 * bounceSpeed, 0.0f, 0.0f, 0.0625f);
                    }
                    else {
                        this.modelEnderCrystalNoBase.render((Entity)var1, 0.0f, var11 * spinSpeed, var12 * bounceSpeed, 0.0f, 0.0f, 0.0625f);
                    }
                    GL11.glEnable(2929);
                    GL11.glDepthMask(true);
                    if (var1.shouldShowBottom()) {
                        this.modelEnderCrystal.render((Entity)var1, 0.0f, var11 * spinSpeed, var12 * bounceSpeed, 0.0f, 0.0f, 0.0625f);
                    }
                    else {
                        this.modelEnderCrystalNoBase.render((Entity)var1, 0.0f, var11 * spinSpeed, var12 * bounceSpeed, 0.0f, 0.0f, 0.0625f);
                    }
                    GL11.glEnable(3042);
                    GL11.glEnable(2896);
                    GL11.glEnable(3553);
                    GL11.glEnable(3008);
                    GL11.glPopAttrib();
                }
                else {
                    RenderUtil.setColor(new Color(outlineColor.getRed(), outlineColor.getGreen(), outlineColor.getBlue()));
                    RenderUtil.renderOne(CrystalModify.INSTANCE.lineWidth.getValue());
                    if (var1.shouldShowBottom()) {
                        this.modelEnderCrystal.render((Entity)var1, 0.0f, var11 * spinSpeed, var12 * bounceSpeed, 0.0f, 0.0f, 0.0625f);
                    }
                    else {
                        this.modelEnderCrystalNoBase.render((Entity)var1, 0.0f, var11 * spinSpeed, var12 * bounceSpeed, 0.0f, 0.0f, 0.0625f);
                    }
                    RenderUtil.renderTwo();
                    if (var1.shouldShowBottom()) {
                        this.modelEnderCrystal.render((Entity)var1, 0.0f, var11 * spinSpeed, var12 * bounceSpeed, 0.0f, 0.0f, 0.0625f);
                    }
                    else {
                        this.modelEnderCrystalNoBase.render((Entity)var1, 0.0f, var11 * spinSpeed, var12 * bounceSpeed, 0.0f, 0.0f, 0.0625f);
                    }
                    RenderUtil.renderThree();
                    RenderUtil.renderFour(outlineColor);
                    RenderUtil.setColor(new Color(outlineColor.getRed(), outlineColor.getGreen(), outlineColor.getBlue()));
                    if (var1.shouldShowBottom()) {
                        this.modelEnderCrystal.render((Entity)var1, 0.0f, var11 * spinSpeed, var12 * bounceSpeed, 0.0f, 0.0f, 0.0625f);
                    }
                    else {
                        this.modelEnderCrystalNoBase.render((Entity)var1, 0.0f, var11 * spinSpeed, var12 * bounceSpeed, 0.0f, 0.0f, 0.0625f);
                    }
                    RenderUtil.renderFive();
                    RenderUtil.setColor(Color.WHITE);
                }
            }
            GL11.glPopMatrix();
        }
    }
    
    static {
        RES_ITEM_GLINT = new ResourceLocation("textures/misc/enchanted_item_glint.png");
    }
}
