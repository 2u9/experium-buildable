//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Luni\Documents\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev._3000IQPlay.experium.features.modules.movement;

import net.minecraft.client.entity.EntityPlayerSP;
import dev._3000IQPlay.experium.util.EntityUtil;
import net.minecraft.entity.EntityLivingBase;
import dev._3000IQPlay.experium.util.MovementUtil;
import dev._3000IQPlay.experium.features.Feature;
import dev._3000IQPlay.experium.event.events.UpdateWalkingPlayerEvent;
import java.util.Objects;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.Entity;
import net.minecraft.init.MobEffects;
import dev._3000IQPlay.experium.event.events.MoveEvent;
import dev._3000IQPlay.experium.features.setting.Setting;
import dev._3000IQPlay.experium.features.modules.Module;

public class SpeedNew extends Module
{
    private final Setting<SpeedNewModes> mode;
    private final Setting<Float> customSpeedNew;
    private final Setting<Float> customY;
    private final Setting<Boolean> customStrafe;
    private final Setting<Boolean> resetXZ;
    private final Setting<Boolean> resetY;
    private final Setting<StrafeMode> strafeMode;
    private double lastDist;
    private double moveSpeedNew;
    int stage;
    
    public SpeedNew() {
        super("SpeedNew", "placeholder", Category.MOVEMENT, false, false, false);
        this.mode = (Setting<SpeedNewModes>)this.register(new Setting("Mode", (T)SpeedNewModes.HYPIXELHOP));
        this.customSpeedNew = (Setting<Float>)this.register(new Setting("CustomSpeedNew", (T)0.35f, (T)0.2f, (T)2.0f, t -> this.mode.getValue().equals(SpeedNewModes.CUSTOM)));
        this.customY = (Setting<Float>)this.register(new Setting("CustomY", (T)0.44f, (T)0.0f, (T)4.0f, t -> this.mode.getValue().equals(SpeedNewModes.CUSTOM)));
        this.customStrafe = (Setting<Boolean>)this.register(new Setting("CustomStrafe", (T)false, t -> this.mode.getValue().equals(SpeedNewModes.CUSTOM)));
        this.resetXZ = (Setting<Boolean>)this.register(new Setting("CustomResetXZ", (T)false, t -> this.mode.getValue().equals(SpeedNewModes.CUSTOM)));
        this.resetY = (Setting<Boolean>)this.register(new Setting("CustomResetY", (T)false, t -> this.mode.getValue().equals(SpeedNewModes.CUSTOM)));
        this.strafeMode = (Setting<StrafeMode>)this.register(new Setting("StrafeMode", (T)StrafeMode.NORMAL, t -> this.mode.getValue().equals(SpeedNewModes.STRAFE)));
    }
    
    @SubscribeEvent
    public void onStrafe(final MoveEvent event) {
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
                if (!Strafe.mc.player.onGround) {
                    break;
                }
                if (!Strafe.mc.gameSettings.keyBindJump.isKeyDown()) {
                    break;
                }
                if (Strafe.mc.player.isPotionActive(MobEffects.JUMP_BOOST)) {
                    motionY += (Strafe.mc.player.getActivePotionEffect(MobEffects.JUMP_BOOST).getAmplifier() + 1) * 0.1f;
                }
                event.setY(Strafe.mc.player.motionY = motionY);
                this.moveSpeedNew *= ((this.strafeMode.getValue() == StrafeMode.NORMAL) ? 1.67 : 2.149);
                break;
            }
            case 3: {
                this.moveSpeedNew = this.lastDist - ((this.strafeMode.getValue() == StrafeMode.NORMAL) ? 0.6896 : 0.795) * (this.lastDist - this.getBaseMoveSpeedNew());
                break;
            }
            default: {
                if ((Strafe.mc.world.getCollisionBoxes((Entity)Strafe.mc.player, Strafe.mc.player.getEntityBoundingBox().offset(0.0, Strafe.mc.player.motionY, 0.0)).size() > 0 || Strafe.mc.player.collidedVertically) && this.stage > 0) {
                    this.stage = ((Strafe.mc.player.moveForward != 0.0f || Strafe.mc.player.moveStrafing != 0.0f) ? 1 : 0);
                }
                this.moveSpeedNew = this.lastDist - this.lastDist / ((this.strafeMode.getValue() == StrafeMode.NORMAL) ? 730.0 : 159.0);
                break;
            }
        }
        this.moveSpeedNew = ((!Strafe.mc.gameSettings.keyBindJump.isKeyDown() && Strafe.mc.player.onGround) ? this.getBaseMoveSpeedNew() : Math.max(this.moveSpeedNew, this.getBaseMoveSpeedNew()));
        double n = Strafe.mc.player.movementInput.moveForward;
        double n2 = Strafe.mc.player.movementInput.moveStrafe;
        final double n3 = Strafe.mc.player.rotationYaw;
        if (n == 0.0 && n2 == 0.0) {
            event.setX(0.0);
            event.setZ(0.0);
        }
        else if (n != 0.0 && n2 != 0.0) {
            n *= Math.sin(0.7853981633974483);
            n2 *= Math.cos(0.7853981633974483);
        }
        final double n4 = (this.strafeMode.getValue() == StrafeMode.NORMAL) ? 0.993 : 0.99;
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
        return n *= 1.0 + 0.2 * (Objects.requireNonNull(Strafe.mc.player.getActivePotionEffect(MobEffects.SPEED)).getAmplifier() + 1);
    }
    
    @SubscribeEvent
    public void onMotion(final UpdateWalkingPlayerEvent event) {
        if (event.getStage() == 1 || Feature.fullNullCheck()) {
            return;
        }
        switch (this.mode.getValue()) {
            case CUSTOM: {
                if (!MovementUtil.isMoving((EntityLivingBase)SpeedNew.mc.player)) {
                    final EntityPlayerSP player = SpeedNew.mc.player;
                    final EntityPlayerSP player2 = SpeedNew.mc.player;
                    final double n = 0.0;
                    player2.motionZ = n;
                    player.motionX = n;
                    break;
                }
                if (SpeedNew.mc.player.onGround) {
                    EntityUtil.moveEntityStrafe(this.customSpeedNew.getValue(), (Entity)SpeedNew.mc.player);
                    SpeedNew.mc.player.motionY = this.customY.getValue();
                    break;
                }
                if (this.customStrafe.getValue()) {
                    EntityUtil.moveEntityStrafe(this.customSpeedNew.getValue(), (Entity)SpeedNew.mc.player);
                    break;
                }
                EntityUtil.moveEntityStrafe(Math.sqrt(SpeedNew.mc.player.motionX * SpeedNew.mc.player.motionX + SpeedNew.mc.player.motionY * SpeedNew.mc.player.motionY + SpeedNew.mc.player.motionZ * SpeedNew.mc.player.motionZ), (Entity)SpeedNew.mc.player);
                break;
            }
            case STRAFE: {
                this.lastDist = Math.sqrt((Strafe.mc.player.posX - Strafe.mc.player.prevPosX) * (Strafe.mc.player.posX - Strafe.mc.player.prevPosX) + (Strafe.mc.player.posZ - Strafe.mc.player.prevPosZ) * (Strafe.mc.player.posZ - Strafe.mc.player.prevPosZ));
                break;
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
                if (!MovementUtil.isMoving((EntityLivingBase)SpeedNew.mc.player)) {
                    final EntityPlayerSP player = SpeedNew.mc.player;
                    final EntityPlayerSP player2 = SpeedNew.mc.player;
                    final double n = 0.0;
                    player2.motionZ = n;
                    player.motionX = n;
                    break;
                }
                if (SpeedNew.mc.player.onGround) {
                    EntityUtil.moveEntityStrafe(this.customSpeedNew.getValue(), (Entity)SpeedNew.mc.player);
                    SpeedNew.mc.player.motionY = this.customY.getValue();
                    break;
                }
                if (this.customStrafe.getValue()) {
                    EntityUtil.moveEntityStrafe(this.customSpeedNew.getValue(), (Entity)SpeedNew.mc.player);
                    break;
                }
                EntityUtil.moveEntityStrafe(Math.sqrt(SpeedNew.mc.player.motionX * SpeedNew.mc.player.motionX + SpeedNew.mc.player.motionY * SpeedNew.mc.player.motionY + SpeedNew.mc.player.motionZ * SpeedNew.mc.player.motionZ), (Entity)SpeedNew.mc.player);
                break;
            }
            case HYPIXELHOP: {
                if (MovementUtil.isMoving((EntityLivingBase)SpeedNew.mc.player)) {
                    if (SpeedNew.mc.player.onGround) {
                        SpeedNew.mc.player.jump();
                        float speed = (MovementUtil.getBaseMoveSpeed() < 0.5600000023841858) ? ((float)(MovementUtil.getBaseMoveSpeed() * 1.0449999570846558)) : 0.56f;
                        if (SpeedNew.mc.player.onGround) {
                            speed *= 1.13f;
                        }
                        EntityUtil.moveEntityStrafe(speed, (Entity)SpeedNew.mc.player);
                    }
                    else if (SpeedNew.mc.player.motionY < 0.2) {
                        final EntityPlayerSP player3 = SpeedNew.mc.player;
                        player3.motionY -= 0.02;
                    }
                    EntityUtil.moveEntityStrafe(MovementUtil.getBaseMoveSpeed() * 1.0188900232315063, (Entity)SpeedNew.mc.player);
                    break;
                }
                final EntityPlayerSP player4 = SpeedNew.mc.player;
                final EntityPlayerSP player5 = SpeedNew.mc.player;
                final double n2 = 0.0;
                player5.motionZ = n2;
                player4.motionX = n2;
                break;
            }
        }
    }
    
    @Override
    public void onEnable() {
        if (this.resetXZ.getValue()) {
            final EntityPlayerSP player = SpeedNew.mc.player;
            final EntityPlayerSP player2 = SpeedNew.mc.player;
            final double n = 0.0;
            player2.motionZ = n;
            player.motionX = n;
        }
        if (this.resetY.getValue()) {
            SpeedNew.mc.player.motionX = 0.0;
        }
        super.onEnable();
    }
    
    @Override
    public String getDisplayInfo() {
        if (!this.mode.getValue().equals(SpeedNewModes.STRAFE)) {
            return this.mode.currentEnumName();
        }
        if (this.strafeMode.getValue().equals(StrafeMode.NORMAL)) {
            return "Strafe Normal";
        }
        return "Strafe Strict";
    }
    
    public enum SpeedNewModes
    {
        HYPIXELHOP, 
        CUSTOM, 
        STRAFE;
    }
    
    public enum StrafeMode
    {
        NORMAL, 
        STRICT;
    }
}
