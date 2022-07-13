//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Luni\Documents\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev._3000IQPlay.experium.features.modules.movement;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import dev._3000IQPlay.experium.Experium;
import dev._3000IQPlay.experium.features.setting.Setting;
import dev._3000IQPlay.experium.features.modules.Module;

public class AntiVoid extends Module
{
    Setting<Mode> mode;
    Setting<Integer> distance;
    Setting<Integer> height;
    Setting<Float> speed;
    Setting<Float> timer;
    
    public AntiVoid() {
        super("AntiVoid", "Prevents u from falling in the void", Category.MOVEMENT, true, false, false);
        this.mode = (Setting<Mode>)this.register(new Setting("Mode", (T)Mode.MotionStop));
        this.distance = (Setting<Integer>)this.register(new Setting("Distance", (T)10, (T)1, (T)256));
        this.height = (Setting<Integer>)this.register(new Setting("Height", (T)4, (T)0, (T)10, v -> this.mode.getValue() == Mode.Packet));
        this.speed = (Setting<Float>)this.register(new Setting("Speed", (T)5.0f, (T)0.1f, (T)10.0f, v -> this.mode.getValue() == Mode.Motion || this.mode.getValue() == Mode.Glide));
        this.timer = (Setting<Float>)this.register(new Setting("Timer", (T)8.0f, (T)0.1f, (T)10.0f, v -> this.mode.getValue() == Mode.Timer));
    }
    
    @Override
    public void onToggle() {
        if (fullNullCheck()) {
            return;
        }
        if (this.mode.getValue() == Mode.Timer) {
            Experium.timerManager.reset();
        }
    }
    
    @Override
    public void onUpdate() {
        if (fullNullCheck()) {
            return;
        }
        if (AntiVoid.mc.player.noClip || AntiVoid.mc.player.posY > this.distance.getValue() || AntiVoid.mc.player.isRiding()) {
            return;
        }
        final RayTraceResult trace = AntiVoid.mc.world.rayTraceBlocks(AntiVoid.mc.player.getPositionVector(), new Vec3d(AntiVoid.mc.player.posX, 0.0, AntiVoid.mc.player.posZ), false, false, false);
        if (trace == null || trace.typeOfHit != RayTraceResult.Type.BLOCK) {
            switch (this.mode.getValue()) {
                case MotionStop: {
                    AntiVoid.mc.player.setVelocity(0.0, 0.0, 0.0);
                    AntiVoid.mc.player.motionY = 0.0;
                    break;
                }
                case Motion: {
                    AntiVoid.mc.player.motionY = this.speed.getValue();
                    break;
                }
                case Timer: {
                    Experium.timerManager.setTimer(this.timer.getValue());
                    break;
                }
                case Glide: {
                    final EntityPlayerSP player = AntiVoid.mc.player;
                    player.motionY *= this.speed.getValue();
                    break;
                }
                case Packet: {
                    AntiVoid.mc.getConnection().sendPacket((Packet)new CPacketPlayer.Position(AntiVoid.mc.player.posX, (double)this.height.getValue(), AntiVoid.mc.player.posZ, AntiVoid.mc.player.onGround));
                    break;
                }
            }
        }
    }
    
    enum Mode
    {
        MotionStop, 
        Motion, 
        Timer, 
        Glide, 
        Packet;
    }
}
