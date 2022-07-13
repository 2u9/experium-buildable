//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Luni\Documents\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev._3000IQPlay.experium.features.modules.combat;

import net.minecraft.util.math.Vec3d;
import java.util.Iterator;
import dev._3000IQPlay.experium.util.MathUtil;
import dev._3000IQPlay.experium.util.EntityUtil;
import net.minecraft.entity.Entity;
import dev._3000IQPlay.experium.Experium;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBow;
import dev._3000IQPlay.experium.features.modules.Module;

public class BowAim extends Module
{
    public BowAim() {
        super("BowAim", "BowAim", Category.COMBAT, true, false, false);
    }
    
    @Override
    public void onUpdate() {
        if (BowAim.mc.player.getHeldItemMainhand().getItem() instanceof ItemBow && BowAim.mc.player.isHandActive() && BowAim.mc.player.getItemInUseMaxCount() >= 3) {
            EntityPlayer player = null;
            float tickDis = 100.0f;
            for (final EntityPlayer p : BowAim.mc.world.playerEntities) {
                if (!(p instanceof EntityPlayerSP) && !Experium.friendManager.isFriend(p.getName())) {
                    final float dis;
                    if ((dis = p.getDistance((Entity)BowAim.mc.player)) >= tickDis) {
                        continue;
                    }
                    tickDis = dis;
                    player = p;
                }
            }
            if (player != null) {
                final Vec3d pos = EntityUtil.getInterpolatedPos((Entity)player, BowAim.mc.getRenderPartialTicks());
                final float[] angels = MathUtil.calcAngle(EntityUtil.getInterpolatedPos((Entity)BowAim.mc.player, BowAim.mc.getRenderPartialTicks()), pos);
                BowAim.mc.player.rotationYaw = angels[0];
                BowAim.mc.player.rotationPitch = angels[1];
            }
        }
    }
}
