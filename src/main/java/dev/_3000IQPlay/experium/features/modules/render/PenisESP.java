//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Luni\Documents\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.renderer.RenderHelper
 *  net.minecraft.entity.player.EntityPlayer
 *  org.lwjgl.opengl.GL11
 *  org.lwjgl.util.glu.Cylinder
 *  org.lwjgl.util.glu.Sphere
 */
package dev._3000IQPlay.experium.features.modules.render;

import dev._3000IQPlay.experium.Experium;
import dev._3000IQPlay.experium.event.events.Render3DEvent;
import dev._3000IQPlay.experium.features.modules.Module;
import dev._3000IQPlay.experium.features.setting.Setting;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Cylinder;
import org.lwjgl.util.glu.Sphere;

public class PenisESP
extends Module {
    private Setting<Boolean> self = this.register(new Setting<Boolean>("Self", true));
    private Setting<Float> selfLength = this.register(new Setting<Float>("SelfLength", Float.valueOf(1.0f), Float.valueOf(0.1f), Float.valueOf(5.0f), v -> this.self.getValue()));
    private Setting<Boolean> friends = this.register(new Setting<Boolean>("Friends", true));
    private Setting<Float> friendLength = this.register(new Setting<Float>("FriendsLength", Float.valueOf(0.8f), Float.valueOf(0.1f), Float.valueOf(5.0f), v -> this.friends.getValue()));
    private Setting<Boolean> others = this.register(new Setting<Boolean>("Others", true));
    private Setting<Float> othersLength = this.register(new Setting<Float>("OthersLength", Float.valueOf(0.4f), Float.valueOf(0.1f), Float.valueOf(5.0f), v -> this.others.getValue()));
    private Setting<Float> penisSize = this.register(new Setting<Float>("Scale", Float.valueOf(1.5f), Float.valueOf(0.1f), Float.valueOf(5.0f)));

    public PenisESP() {
        super("PenisESP", "Renders a penis on ur screen", Module.Category.RENDER, true, false, false);
    }

    @Override
    public void onRender3D(Render3DEvent event) {
        for (Object o : PenisESP.mc.world.loadedEntityList) {
            if (!(o instanceof EntityPlayer)) continue;
            EntityPlayer player = (EntityPlayer)o;
            double n = player.lastTickPosX + (player.posX - player.lastTickPosX) * (double)PenisESP.mc.timer.renderPartialTicks;
            mc.getRenderManager();
            double x = n - PenisESP.mc.getRenderManager().renderPosX;
            double n2 = player.lastTickPosY + (player.posY - player.lastTickPosY) * (double)PenisESP.mc.timer.renderPartialTicks;
            mc.getRenderManager();
            double y = n2 - PenisESP.mc.getRenderManager().renderPosY;
            double n3 = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * (double)PenisESP.mc.timer.renderPartialTicks;
            mc.getRenderManager();
            double z = n3 - PenisESP.mc.getRenderManager().renderPosZ;
            GL11.glPushMatrix();
            RenderHelper.disableStandardItemLighting();
            this.esp(player, x, y, z);
            RenderHelper.enableStandardItemLighting();
            GL11.glPopMatrix();
        }
    }

    private boolean shouldRenderPenis(EntityPlayer player) {
        if (player.entityUniqueID == PenisESP.mc.player.entityUniqueID) {
            return this.self.getValue();
        }
        if (Experium.friendManager.isFriend(player)) {
            return this.friends.getValue();
        }
        return this.others.getValue();
    }

    private float getPenisLength(EntityPlayer player) {
        if (player.entityUniqueID == PenisESP.mc.player.entityUniqueID) {
            return this.selfLength.getValue().floatValue();
        }
        if (Experium.friendManager.isFriend(player)) {
            return this.friendLength.getValue().floatValue();
        }
        return this.othersLength.getValue().floatValue();
    }

    public void esp(EntityPlayer player, double x, double y, double z) {
        if (!this.shouldRenderPenis(player)) {
            return;
        }
        GL11.glDisable((int)2896);
        GL11.glDisable((int)3553);
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glDisable((int)2929);
        GL11.glEnable((int)2848);
        GL11.glDepthMask((boolean)true);
        GL11.glLineWidth((float)1.0f);
        GL11.glTranslated((double)x, (double)y, (double)z);
        GL11.glRotatef((float)(-player.rotationYaw), (float)0.0f, (float)player.height, (float)0.0f);
        GL11.glTranslated((double)(-x), (double)(-y), (double)(-z));
        GL11.glTranslated((double)x, (double)(y + (double)(player.height / 2.0f) - (double)0.225f), (double)z);
        GL11.glColor4f((float)1.38f, (float)0.55f, (float)2.38f, (float)1.0f);
        GL11.glRotated((double)(player.isSneaking() ? 35 : 0), (double)1.0, (double)0.0, (double)0.0);
        GL11.glTranslated((double)0.0, (double)0.0, (double)0.075f);
        Cylinder shaft = new Cylinder();
        shaft.setDrawStyle(100013);
        shaft.draw(0.1f * this.penisSize.getValue().floatValue(), 0.11f, this.getPenisLength(player), 25, 20);
        GL11.glTranslated((double)0.0, (double)0.0, (double)-0.12500000298023223);
        GL11.glTranslated((double)-0.09000000074505805, (double)0.0, (double)0.0);
        Sphere right = new Sphere();
        right.setDrawStyle(100013);
        right.draw(0.14f * this.penisSize.getValue().floatValue(), 10, 20);
        GL11.glTranslated((double)0.16000000149011612, (double)0.0, (double)0.0);
        Sphere left = new Sphere();
        left.setDrawStyle(100013);
        left.draw(0.14f * this.penisSize.getValue().floatValue(), 10, 20);
        GL11.glColor4f((float)1.35f, (float)0.0f, (float)0.0f, (float)1.0f);
        GL11.glTranslated((double)-0.07000000074505806, (double)0.0, (double)((double)this.getPenisLength(player) + 0.189999952316284));
        Sphere tip = new Sphere();
        tip.setDrawStyle(100013);
        tip.draw(0.13f * this.penisSize.getValue().floatValue(), 15, 20);
        GL11.glDepthMask((boolean)true);
        GL11.glDisable((int)2848);
        GL11.glEnable((int)2929);
        GL11.glDisable((int)3042);
        GL11.glEnable((int)2896);
        GL11.glEnable((int)3553);
    }
}

