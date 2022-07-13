//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Luni\Documents\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.GameProfile
 *  net.minecraft.client.entity.EntityOtherPlayerMP
 *  net.minecraft.enchantment.EnchantmentHelper
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.MoverType
 *  net.minecraft.world.World
 */
package dev._3000IQPlay.experium.features.modules.player;

import com.mojang.authlib.GameProfile;
import dev._3000IQPlay.experium.features.modules.Module;
import dev._3000IQPlay.experium.features.setting.Setting;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.world.World;

public class FakePlayer
extends Module {
    private static FakePlayer INSTANCE = new FakePlayer();
    public List<Integer> fakePlayerIdList = new ArrayList<Integer>();
    public Setting<Boolean> moving = this.register(new Setting<Boolean>("Moving", false));
    private EntityOtherPlayerMP otherPlayer;

    public FakePlayer() {
        super("FakePlayer", "Spawns fake player", Module.Category.PLAYER, false, false, false);
        this.setInstance();
    }

    public static FakePlayer getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new FakePlayer();
        }
        return INSTANCE;
    }

    private void setInstance() {
        INSTANCE = this;
    }

    @Override
    public void onTick() {
        if (this.otherPlayer != null) {
            Random random = new Random();
            this.otherPlayer.moveForward = FakePlayer.mc.player.moveForward + (float)random.nextInt(5) / 10.0f;
            this.otherPlayer.moveStrafing = FakePlayer.mc.player.moveStrafing + (float)random.nextInt(5) / 10.0f;
            if (this.moving.getValue().booleanValue()) {
                this.travel(this.otherPlayer.moveStrafing, this.otherPlayer.moveVertical, this.otherPlayer.moveForward);
            }
        }
    }

    public void travel(float strafe, float vertical, float forward) {
        double d0 = this.otherPlayer.posY;
        float f1 = 0.8f;
        float f2 = 0.02f;
        float f3 = EnchantmentHelper.getDepthStriderModifier((EntityLivingBase)this.otherPlayer);
        if (f3 > 3.0f) {
            f3 = 3.0f;
        }
        if (!this.otherPlayer.onGround) {
            f3 *= 0.5f;
        }
        if (f3 > 0.0f) {
            f1 += (0.54600006f - f1) * f3 / 3.0f;
            f2 += (this.otherPlayer.getAIMoveSpeed() - f2) * f3 / 4.0f;
        }
        this.otherPlayer.moveRelative(strafe, vertical, forward, f2);
        this.otherPlayer.move(MoverType.SELF, this.otherPlayer.motionX, this.otherPlayer.motionY, this.otherPlayer.motionZ);
        this.otherPlayer.motionX *= (double)f1;
        this.otherPlayer.motionY *= (double)0.8f;
        this.otherPlayer.motionZ *= (double)f1;
        if (!this.otherPlayer.hasNoGravity()) {
            this.otherPlayer.motionY -= 0.02;
        }
        if (this.otherPlayer.collidedHorizontally && this.otherPlayer.isOffsetPositionInLiquid(this.otherPlayer.motionX, this.otherPlayer.motionY + (double)0.6f - this.otherPlayer.posY + d0, this.otherPlayer.motionZ)) {
            this.otherPlayer.motionY = 0.3f;
        }
    }

    @Override
    public void onEnable() {
        if (FakePlayer.mc.world == null || FakePlayer.mc.player == null) {
            this.toggle();
            return;
        }
        this.fakePlayerIdList = new ArrayList<Integer>();
        this.addFakePlayer(-100);
    }

    public void addFakePlayer(int entityId) {
        if (this.otherPlayer == null) {
            this.otherPlayer = new EntityOtherPlayerMP((World)FakePlayer.mc.world, new GameProfile(UUID.randomUUID(), "|Experium|"));
            this.otherPlayer.copyLocationAndAnglesFrom((Entity)FakePlayer.mc.player);
            this.otherPlayer.inventory.copyInventory(FakePlayer.mc.player.inventory);
        }
        FakePlayer.mc.world.addEntityToWorld(entityId, (Entity)this.otherPlayer);
        this.fakePlayerIdList.add(entityId);
    }

    @Override
    public void onDisable() {
        for (int id : this.fakePlayerIdList) {
            FakePlayer.mc.world.removeEntityFromWorld(id);
        }
        if (this.otherPlayer != null) {
            FakePlayer.mc.world.removeEntity((Entity)this.otherPlayer);
            this.otherPlayer = null;
        }
    }
}

