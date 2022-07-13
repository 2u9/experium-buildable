//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Luni\Documents\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev._3000IQPlay.experium.features.modules.player;

import java.util.Iterator;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import com.mojang.authlib.GameProfile;
import java.util.UUID;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.enchantment.EnchantmentHelper;
import java.util.Random;
import java.util.ArrayList;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import dev._3000IQPlay.experium.features.setting.Setting;
import java.util.List;
import dev._3000IQPlay.experium.features.modules.Module;

public class FakePlayer extends Module
{
    private static FakePlayer INSTANCE;
    public List<Integer> fakePlayerIdList;
    public Setting<Boolean> moving;
    private EntityOtherPlayerMP otherPlayer;
    
    public FakePlayer() {
        super("FakePlayer", "Spawns fake player", Category.PLAYER, false, false, false);
        this.fakePlayerIdList = new ArrayList<Integer>();
        this.moving = (Setting<Boolean>)this.register(new Setting("Moving", (T)false));
        this.setInstance();
    }
    
    public static FakePlayer getInstance() {
        if (FakePlayer.INSTANCE == null) {
            FakePlayer.INSTANCE = new FakePlayer();
        }
        return FakePlayer.INSTANCE;
    }
    
    private void setInstance() {
        FakePlayer.INSTANCE = this;
    }
    
    @Override
    public void onTick() {
        if (this.otherPlayer != null) {
            final Random random = new Random();
            this.otherPlayer.moveForward = FakePlayer.mc.player.moveForward + random.nextInt(5) / 10.0f;
            this.otherPlayer.moveStrafing = FakePlayer.mc.player.moveStrafing + random.nextInt(5) / 10.0f;
            if (this.moving.getValue()) {
                this.travel(this.otherPlayer.moveStrafing, this.otherPlayer.moveVertical, this.otherPlayer.moveForward);
            }
        }
    }
    
    public void travel(final float strafe, final float vertical, final float forward) {
        final double d0 = this.otherPlayer.posY;
        float f1 = 0.8f;
        float f2 = 0.02f;
        float f3 = (float)EnchantmentHelper.getDepthStriderModifier((EntityLivingBase)this.otherPlayer);
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
        final EntityOtherPlayerMP otherPlayer = this.otherPlayer;
        otherPlayer.motionX *= f1;
        final EntityOtherPlayerMP otherPlayer2 = this.otherPlayer;
        otherPlayer2.motionY *= 0.800000011920929;
        final EntityOtherPlayerMP otherPlayer3 = this.otherPlayer;
        otherPlayer3.motionZ *= f1;
        if (!this.otherPlayer.hasNoGravity()) {
            final EntityOtherPlayerMP otherPlayer4 = this.otherPlayer;
            otherPlayer4.motionY -= 0.02;
        }
        if (this.otherPlayer.collidedHorizontally && this.otherPlayer.isOffsetPositionInLiquid(this.otherPlayer.motionX, this.otherPlayer.motionY + 0.6000000238418579 - this.otherPlayer.posY + d0, this.otherPlayer.motionZ)) {
            this.otherPlayer.motionY = 0.30000001192092896;
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
    
    public void addFakePlayer(final int entityId) {
        if (this.otherPlayer == null) {
            (this.otherPlayer = new EntityOtherPlayerMP((World)FakePlayer.mc.world, new GameProfile(UUID.randomUUID(), "|Experium|"))).copyLocationAndAnglesFrom((Entity)FakePlayer.mc.player);
            this.otherPlayer.inventory.copyInventory(FakePlayer.mc.player.inventory);
        }
        FakePlayer.mc.world.addEntityToWorld(entityId, (Entity)this.otherPlayer);
        this.fakePlayerIdList.add(entityId);
    }
    
    @Override
    public void onDisable() {
        for (final int id : this.fakePlayerIdList) {
            FakePlayer.mc.world.removeEntityFromWorld(id);
        }
        if (this.otherPlayer != null) {
            FakePlayer.mc.world.removeEntity((Entity)this.otherPlayer);
            this.otherPlayer = null;
        }
    }
    
    static {
        FakePlayer.INSTANCE = new FakePlayer();
    }
}
