//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Luni\Documents\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.init.MobEffects
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package dev._3000IQPlay.experium.features.modules.movement;

import dev._3000IQPlay.experium.event.events.MoveEvent;
import dev._3000IQPlay.experium.event.events.UpdateWalkingPlayerEvent;
import dev._3000IQPlay.experium.features.modules.Module;
import dev._3000IQPlay.experium.features.modules.movement.Strafe;
import dev._3000IQPlay.experium.features.setting.Setting;
import dev._3000IQPlay.experium.util.EntityUtil;
import dev._3000IQPlay.experium.util.MovementUtil;
import java.util.Objects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class SpeedNew
extends Module {
    private final Setting<SpeedNewModes> mode = this.register(new Setting<SpeedNewModes>("Mode", SpeedNewModes.HYPIXELHOP));
    private final Setting<Float> customSpeedNew = this.register(new Setting<Float>("CustomSpeedNew", Float.valueOf(0.35f), Float.valueOf(0.2f), Float.valueOf(2.0f), t -> this.mode.getValue().equals((Object)SpeedNewModes.CUSTOM)));
    private final Setting<Float> customY = this.register(new Setting<Float>("CustomY", Float.valueOf(0.44f), Float.valueOf(0.0f), Float.valueOf(4.0f), t -> this.mode.getValue().equals((Object)SpeedNewModes.CUSTOM)));
    private final Setting<Boolean> customStrafe = this.register(new Setting<Boolean>("CustomStrafe", Boolean.valueOf(false), t -> this.mode.getValue().equals((Object)SpeedNewModes.CUSTOM)));
    private final Setting<Boolean> resetXZ = this.register(new Setting<Boolean>("CustomResetXZ", Boolean.valueOf(false), t -> this.mode.getValue().equals((Object)SpeedNewModes.CUSTOM)));
    private final Setting<Boolean> resetY = this.register(new Setting<Boolean>("CustomResetY", Boolean.valueOf(false), t -> this.mode.getValue().equals((Object)SpeedNewModes.CUSTOM)));
    private final Setting<StrafeMode> strafeMode = this.register(new Setting<Object>("StrafeMode", (Object)StrafeMode.NORMAL, t -> this.mode.getValue().equals((Object)SpeedNewModes.STRAFE)));
    private double lastDist;
    private double moveSpeedNew;
    int stage;

    public SpeedNew() {
        super("SpeedNew", "placeholder", Module.Category.MOVEMENT, false, false, false);
    }

    @SubscribeEvent
    public void onStrafe(MoveEvent event) {
        if (Strafe.fullNullCheck()) {
            return;
        }
        if (Strafe.mc.player.isInWater()) {
            return;
        }
        if (Strafe.mc.player.isInLava()) {
            return;
        }
        if (Strafe.mc.player.onGround) {
            this.stage = 2;
        }
        switch (this.stage) {
            case 0: {
                ++this.stage;
                this.lastDist = 0.0;
                break;
            }
            case 2: {
                double motionY = 0.40123128;
                if (!Strafe.mc.player.onGround || !Strafe.mc.gameSettings.keyBindJump.isKeyDown()) break;
                if (Strafe.mc.player.isPotionActive(MobEffects.JUMP_BOOST)) {
                    motionY += (double)((float)(Strafe.mc.player.getActivePotionEffect(MobEffects.JUMP_BOOST).getAmplifier() + 1) * 0.1f);
                }
                Strafe.mc.player.motionY = motionY;
                event.setY(Strafe.mc.player.motionY);
                this.moveSpeedNew *= this.strafeMode.getValue() == StrafeMode.NORMAL ? 1.67 : 2.149;
                break;
            }
            case 3: {
                this.moveSpeedNew = this.lastDist - (this.strafeMode.getValue() == StrafeMode.NORMAL ? 0.6896 : 0.795) * (this.lastDist - this.getBaseMoveSpeedNew());
                break;
            }
            default: {
                if ((Strafe.mc.world.getCollisionBoxes((Entity)Strafe.mc.player, Strafe.mc.player.getEntityBoundingBox().offset(0.0, Strafe.mc.player.motionY, 0.0)).size() > 0 || Strafe.mc.player.collidedVertically) && this.stage > 0) {
                    this.stage = Strafe.mc.player.moveForward != 0.0f || Strafe.mc.player.moveStrafing != 0.0f ? 1 : 0;
                }
                this.moveSpeedNew = this.lastDist - this.lastDist / (this.strafeMode.getValue() == StrafeMode.NORMAL ? 730.0 : 159.0);
            }
        }
        this.moveSpeedNew = !Strafe.mc.gameSettings.keyBindJump.isKeyDown() && Strafe.mc.player.onGround ? this.getBaseMoveSpeedNew() : Math.max(this.moveSpeedNew, this.getBaseMoveSpeedNew());
        double n = Strafe.mc.player.movementInput.moveForward;
        double n2 = Strafe.mc.player.movementInput.moveStrafe;
        double n3 = Strafe.mc.player.rotationYaw;
        if (n == 0.0 && n2 == 0.0) {
            event.setX(0.0);
            event.setZ(0.0);
        } else if (n != 0.0 && n2 != 0.0) {
            n *= Math.sin(0.7853981633974483);
            n2 *= Math.cos(0.7853981633974483);
        }
        double n4 = this.strafeMode.getValue() == StrafeMode.NORMAL ? 0.993 : 0.99;
        event.setX((n * this.moveSpeedNew * -Math.sin(Math.toRadians(n3)) + n2 * this.moveSpeedNew * Math.cos(Math.toRadians(n3))) * n4);
        event.setZ((n * this.moveSpeedNew * Math.cos(Math.toRadians(n3)) - n2 * this.moveSpeedNew * -Math.sin(Math.toRadians(n3))) * n4);
        ++this.stage;
        event.setCanceled(true);
    }

    public double getBaseMoveSpeedNew() {
        double n = 0.2873;
        if (!Strafe.mc.player.isPotionActive(MobEffects.SPEED)) {
            return n;
        }
        return n *= 1.0 + 0.2 * (double)(Objects.requireNonNull(Strafe.mc.player.getActivePotionEffect(MobEffects.SPEED)).getAmplifier() + 1);
    }

    @SubscribeEvent
    public void onMotion(UpdateWalkingPlayerEvent event) {
        if (event.getStage() == 1 || Strafe.fullNullCheck()) {
            return;
        }
        switch (this.mode.getValue()) {
            case CUSTOM: {
                if (MovementUtil.isMoving((EntityLivingBase)SpeedNew.mc.player)) {
                    if (SpeedNew.mc.player.onGround) {
                        EntityUtil.moveEntityStrafe(this.customSpeedNew.getValue().floatValue(), (Entity)SpeedNew.mc.player);
                        SpeedNew.mc.player.motionY = this.customY.getValue().floatValue();
                        break;
                    }
                    if (this.customStrafe.getValue().booleanValue()) {
                        EntityUtil.moveEntityStrafe(this.customSpeedNew.getValue().floatValue(), (Entity)SpeedNew.mc.player);
                        break;
                    }
                    EntityUtil.moveEntityStrafe(Math.sqrt(SpeedNew.mc.player.motionX * SpeedNew.mc.player.motionX + SpeedNew.mc.player.motionY * SpeedNew.mc.player.motionY + SpeedNew.mc.player.motionZ * SpeedNew.mc.player.motionZ), (Entity)SpeedNew.mc.player);
                    break;
                }
                SpeedNew.mc.player.motionZ = 0.0;
                SpeedNew.mc.player.motionX = 0.0;
                break;
            }
            case STRAFE: {
                this.lastDist = Math.sqrt((Strafe.mc.player.posX - Strafe.mc.player.prevPosX) * (Strafe.mc.player.posX - Strafe.mc.player.prevPosX) + (Strafe.mc.player.posZ - Strafe.mc.player.prevPosZ) * (Strafe.mc.player.posZ - Strafe.mc.player.prevPosZ));
            }
        }
    }

    @Override
    public void onTick() {
        switch (this.mode.getValue()) {
            case STRAFE: {
                if (MovementUtil.isMoving((EntityLivingBase)SpeedNew.mc.player) && SpeedNew.mc.player.onGround) {
                    SpeedNew.mc.player.jump();
                }
            }
            case CUSTOM: {
                if (MovementUtil.isMoving((EntityLivingBase)SpeedNew.mc.player)) {
                    if (SpeedNew.mc.player.onGround) {
                        EntityUtil.moveEntityStrafe(this.customSpeedNew.getValue().floatValue(), (Entity)SpeedNew.mc.player);
                        SpeedNew.mc.player.motionY = this.customY.getValue().floatValue();
                        break;
                    }
                    if (this.customStrafe.getValue().booleanValue()) {
                        EntityUtil.moveEntityStrafe(this.customSpeedNew.getValue().floatValue(), (Entity)SpeedNew.mc.player);
                        break;
                    }
                    EntityUtil.moveEntityStrafe(Math.sqrt(SpeedNew.mc.player.motionX * SpeedNew.mc.player.motionX + SpeedNew.mc.player.motionY * SpeedNew.mc.player.motionY + SpeedNew.mc.player.motionZ * SpeedNew.mc.player.motionZ), (Entity)SpeedNew.mc.player);
                    break;
                }
                SpeedNew.mc.player.motionZ = 0.0;
                SpeedNew.mc.player.motionX = 0.0;
                break;
            }
            case HYPIXELHOP: {
                if (MovementUtil.isMoving((EntityLivingBase)SpeedNew.mc.player)) {
                    if (SpeedNew.mc.player.onGround) {
                        float speed;
                        SpeedNew.mc.player.jump();
                        float f = speed = MovementUtil.getBaseMoveSpeed() < (double)0.56f ? (float)(MovementUtil.getBaseMoveSpeed() * (double)1.045f) : 0.56f;
                        if (SpeedNew.mc.player.onGround) {
                            speed *= 1.13f;
                        }
                        EntityUtil.moveEntityStrafe(speed, (Entity)SpeedNew.mc.player);
                    } else if (SpeedNew.mc.player.motionY < 0.2) {
                        SpeedNew.mc.player.motionY -= 0.02;
                    }
                    EntityUtil.moveEntityStrafe(MovementUtil.getBaseMoveSpeed() * (double)1.01889f, (Entity)SpeedNew.mc.player);
                    break;
                }
                SpeedNew.mc.player.motionZ = 0.0;
                SpeedNew.mc.player.motionX = 0.0;
            }
        }
    }

    @Override
    public void onEnable() {
        if (this.resetXZ.getValue().booleanValue()) {
            SpeedNew.mc.player.motionZ = 0.0;
            SpeedNew.mc.player.motionX = 0.0;
        }
        if (this.resetY.getValue().booleanValue()) {
            SpeedNew.mc.player.motionX = 0.0;
        }
        super.onEnable();
    }

    @Override
    public String getDisplayInfo() {
        if (!this.mode.getValue().equals((Object)SpeedNewModes.STRAFE)) {
            return this.mode.currentEnumName();
        }
        if (this.strafeMode.getValue().equals((Object)StrafeMode.NORMAL)) {
            return "Strafe Normal";
        }
        return "Strafe Strict";
    }

    public static enum StrafeMode {
        NORMAL,
        STRICT;

    }

    public static enum SpeedNewModes {
        HYPIXELHOP,
        CUSTOM,
        STRAFE;

    }
}

