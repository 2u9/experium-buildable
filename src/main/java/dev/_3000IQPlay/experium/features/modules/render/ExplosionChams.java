//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Luni\Documents\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev._3000IQPlay.experium.features.modules.render;

import dev._3000IQPlay.experium.util.RenderUtil;
import java.awt.Color;
import dev._3000IQPlay.experium.event.events.Render3DEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import java.util.Iterator;
import net.minecraft.network.play.server.SPacketExplosion;
import net.minecraft.entity.Entity;
import dev._3000IQPlay.experium.event.events.PacketEvent;
import java.util.HashMap;
import dev._3000IQPlay.experium.util.Timer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.entity.item.EntityEnderCrystal;
import java.util.Map;
import dev._3000IQPlay.experium.features.setting.Setting;
import dev._3000IQPlay.experium.features.modules.Module;

public class ExplosionChams extends Module
{
    public final Setting<Integer> red;
    public final Setting<Integer> green;
    public final Setting<Integer> blue;
    public final Setting<Integer> alpha;
    public final Setting<Float> increase;
    public final Setting<Integer> riseSpeed;
    public final Setting<Boolean> rainbow;
    public Map<EntityEnderCrystal, BlockPos> explodedCrystals;
    public BlockPos crystalPos;
    public int aliveTicks;
    public double speed;
    public Timer timer;
    
    public ExplosionChams() {
        super("ExplosionChams", "Draws a cham when a crystal explodes", Category.RENDER, true, false, false);
        this.red = (Setting<Integer>)this.register(new Setting("Red", (T)135, (T)0, (T)255));
        this.green = (Setting<Integer>)this.register(new Setting("Green", (T)0, (T)0, (T)255));
        this.blue = (Setting<Integer>)this.register(new Setting("Blue", (T)255, (T)0, (T)255));
        this.alpha = (Setting<Integer>)this.register(new Setting("Alpha", (T)255, (T)0, (T)255));
        this.increase = (Setting<Float>)this.register(new Setting("Increase Size", (T)0.0f, (T)0.0f, (T)5.0f));
        this.riseSpeed = (Setting<Integer>)this.register(new Setting("Rise Time", (T)5, (T)1, (T)50));
        this.rainbow = (Setting<Boolean>)this.register(new Setting("Sync", (T)false));
        this.explodedCrystals = new HashMap<EntityEnderCrystal, BlockPos>();
        this.crystalPos = null;
        this.aliveTicks = 0;
        this.speed = 0.0;
        this.timer = new Timer();
    }
    
    @Override
    public void onEnable() {
        this.explodedCrystals.clear();
    }
    
    @Override
    public void onUpdate() {
        if (fullNullCheck()) {
            return;
        }
        ++this.aliveTicks;
        if (this.timer.passedMs(5L)) {
            this.speed += 0.5;
            this.timer.reset();
        }
        if (this.speed > this.riseSpeed.getValue()) {
            this.speed = 0.0;
            this.explodedCrystals.clear();
        }
    }
    
    @SubscribeEvent
    public void onPacketReceive(final PacketEvent.Receive event) {
        try {
            for (final Entity crystal : ExplosionChams.mc.world.loadedEntityList) {
                if (crystal instanceof EntityEnderCrystal) {
                    if (!(event.getPacket() instanceof SPacketExplosion)) {
                        continue;
                    }
                    this.crystalPos = new BlockPos(crystal.posX, crystal.posY, crystal.posZ);
                    this.explodedCrystals.put((EntityEnderCrystal)crystal, this.crystalPos);
                }
            }
        }
        catch (Exception ex) {}
    }
    
    @SubscribeEvent
    @Override
    public void onRender3D(final Render3DEvent event) {
        if (!this.explodedCrystals.isEmpty()) {
            RenderUtil.drawCircle((float)this.crystalPos.getX(), this.crystalPos.getY() + (float)this.speed / 3.0f + 0.7f, (float)this.crystalPos.getZ(), 0.6f + this.increase.getValue(), new Color(this.red.getValue(), this.green.getValue(), this.blue.getValue(), this.alpha.getValue() - 60));
            RenderUtil.drawCircle((float)this.crystalPos.getX(), this.crystalPos.getY() + (float)this.speed / 3.0f + 0.6f, (float)this.crystalPos.getZ(), 0.5f + this.increase.getValue(), new Color(this.red.getValue(), this.green.getValue(), this.blue.getValue(), this.alpha.getValue() - 50));
            RenderUtil.drawCircle((float)this.crystalPos.getX(), this.crystalPos.getY() + (float)this.speed / 3.0f + 0.5f, (float)this.crystalPos.getZ(), 0.4f + this.increase.getValue(), new Color(this.red.getValue(), this.green.getValue(), this.blue.getValue(), this.alpha.getValue() - 40));
            RenderUtil.drawCircle((float)this.crystalPos.getX(), this.crystalPos.getY() + (float)this.speed / 3.0f + 0.4f, (float)this.crystalPos.getZ(), 0.3f + this.increase.getValue(), new Color(this.red.getValue(), this.green.getValue(), this.blue.getValue(), this.alpha.getValue() - 30));
            RenderUtil.drawCircle((float)this.crystalPos.getX(), this.crystalPos.getY() + (float)this.speed / 3.0f + 0.3f, (float)this.crystalPos.getZ(), 0.2f + this.increase.getValue(), new Color(this.red.getValue(), this.green.getValue(), this.blue.getValue(), this.alpha.getValue() - 20));
            RenderUtil.drawCircle((float)this.crystalPos.getX(), this.crystalPos.getY() + (float)this.speed / 3.0f + 0.2f, (float)this.crystalPos.getZ(), 0.1f + this.increase.getValue(), new Color(this.red.getValue(), this.green.getValue(), this.blue.getValue(), this.alpha.getValue() - 10));
            RenderUtil.drawCircle((float)this.crystalPos.getX(), this.crystalPos.getY() + (float)this.speed / 3.0f + 0.1f, (float)this.crystalPos.getZ(), 0.0f + this.increase.getValue(), new Color(this.red.getValue(), this.green.getValue(), this.blue.getValue(), this.alpha.getValue()));
        }
    }
}
