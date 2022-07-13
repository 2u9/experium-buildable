//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Luni\Documents\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.entity.Entity
 *  net.minecraft.entity.EntityLivingBase
 *  net.minecraft.entity.player.EntityPlayer
 *  net.minecraft.init.Items
 *  net.minecraft.inventory.ClickType
 *  net.minecraft.item.ItemSword
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketEntityAction
 *  net.minecraft.network.play.client.CPacketEntityAction$Action
 *  net.minecraft.util.EnumHand
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package dev._3000IQPlay.experium.features.modules.combat;

import dev._3000IQPlay.experium.Experium;
import dev._3000IQPlay.experium.event.events.UpdateWalkingPlayerEvent;
import dev._3000IQPlay.experium.features.modules.Module;
import dev._3000IQPlay.experium.features.setting.Setting;
import dev._3000IQPlay.experium.util.DamageUtil;
import dev._3000IQPlay.experium.util.EntityUtil;
import dev._3000IQPlay.experium.util.InventoryUtil;
import dev._3000IQPlay.experium.util.MathUtil;
import dev._3000IQPlay.experium.util.Timer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemSword;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Killaura
extends Module {
    public static Entity target;
    private final Timer timer = new Timer();
    private final Setting<TargetMode> targetMode = this.register(new Setting<TargetMode>("Target", TargetMode.CLOSEST));
    public Setting<Float> range = this.register(new Setting<Float>("Range", Float.valueOf(6.0f), Float.valueOf(0.1f), Float.valueOf(7.0f)));
    public Setting<Boolean> autoSwitch = this.register(new Setting<Boolean>("AutoSwitch", false));
    public Setting<Boolean> oneDotEight = this.register(new Setting<Boolean>("1.8", false));
    public Setting<Boolean> rotate = this.register(new Setting<Boolean>("Rotate", false));
    public Setting<Boolean> stay = this.register(new Setting<Object>("Stay", Boolean.valueOf(true), v -> this.rotate.getValue()));
    public Setting<Boolean> armorBreak = this.register(new Setting<Boolean>("ArmorBreak", false));
    public Setting<Boolean> eating = this.register(new Setting<Boolean>("Eating", true));
    public Setting<Boolean> onlySharp = this.register(new Setting<Boolean>("Axe/Sword", true));
    public Setting<Boolean> teleport = this.register(new Setting<Boolean>("Teleport", false));
    public Setting<Float> raytrace = this.register(new Setting<Object>("Raytrace", Float.valueOf(6.0f), Float.valueOf(0.1f), Float.valueOf(7.0f), v -> this.teleport.getValue() == false, "Wall Range."));
    public Setting<Float> teleportRange = this.register(new Setting<Object>("TpRange", Float.valueOf(15.0f), Float.valueOf(0.1f), Float.valueOf(50.0f), v -> this.teleport.getValue(), "Teleport Range."));
    public Setting<Boolean> lagBack = this.register(new Setting<Object>("LagBack", Boolean.valueOf(true), v -> this.teleport.getValue()));
    public Setting<Boolean> players = this.register(new Setting<Boolean>("Players", true));
    public Setting<Boolean> mobs = this.register(new Setting<Boolean>("Mobs", false));
    public Setting<Boolean> animals = this.register(new Setting<Boolean>("Animals", false));
    public Setting<Boolean> vehicles = this.register(new Setting<Boolean>("Entities", false));
    public Setting<Boolean> projectiles = this.register(new Setting<Boolean>("Projectiles", false));
    public Setting<Boolean> tps = this.register(new Setting<Boolean>("TpsSync", true));
    public Setting<Boolean> packet = this.register(new Setting<Boolean>("Packet", false));
    public Setting<Boolean> swing = this.register(new Setting<Boolean>("Swing", true));
    public Setting<Boolean> sneak = this.register(new Setting<Boolean>("State", false));
    public Setting<Float> health = this.register(new Setting<Object>("Health", Float.valueOf(6.0f), Float.valueOf(0.1f), Float.valueOf(36.0f), v -> this.targetMode.getValue() == TargetMode.SMART));

    public Killaura() {
        super("Killaura", "Kills aura.", Module.Category.COMBAT, true, false, false);
    }

    @Override
    public void onTick() {
        if (!this.rotate.getValue().booleanValue()) {
            this.doKillaura();
        }
    }

    @SubscribeEvent
    public void onUpdateWalkingPlayerEvent(UpdateWalkingPlayerEvent event) {
        if (event.getStage() == 0 && this.rotate.getValue().booleanValue()) {
            if (this.stay.getValue().booleanValue() && target != null) {
                Experium.rotationManager.lookAtEntity(target);
            }
            this.doKillaura();
        }
    }

    private void doKillaura() {
        int sword;
        int wait;
        if (this.onlySharp.getValue().booleanValue() && !EntityUtil.holdingWeapon((EntityPlayer)Killaura.mc.player)) {
            target = null;
            return;
        }

        int n = this.oneDotEight.getValue() != false ? 0 : (wait = (wait = (int)((float)DamageUtil.getCooldownByWeapon((EntityPlayer)Killaura.mc.player) * (this.tps.getValue() != false ? Experium.serverManager.getTpsFactor() : 1.0f))));
        if (!this.timer.passedMs(n) || !this.eating.getValue().booleanValue() && Killaura.mc.player.isHandActive() && (!Killaura.mc.player.getHeldItemOffhand().getItem().equals((Object)Items.SHIELD) || Killaura.mc.player.getActiveHand() != EnumHand.OFF_HAND)) {
            return;
        }
        if (!(this.targetMode.getValue() == TargetMode.FOCUS && target != null && (Killaura.mc.player.getDistanceSq(target) < MathUtil.square(this.range.getValue().floatValue()) || this.teleport.getValue().booleanValue() && Killaura.mc.player.getDistanceSq(target) < MathUtil.square(this.teleportRange.getValue().floatValue())) && (Killaura.mc.player.canEntityBeSeen(target) || EntityUtil.canEntityFeetBeSeen(target) || Killaura.mc.player.getDistanceSq(target) < MathUtil.square(this.raytrace.getValue().floatValue()) || this.teleport.getValue().booleanValue()))) {
            target = this.getTarget();
        }
        if (target == null) {
            return;
        }
        if (this.autoSwitch.getValue().booleanValue() && (sword = InventoryUtil.findHotbarBlock(ItemSword.class)) != -1) {
            InventoryUtil.switchToHotbarSlot(sword, false);
        }
        if (this.rotate.getValue().booleanValue()) {
            Experium.rotationManager.lookAtEntity(target);
        }
        if (this.teleport.getValue().booleanValue()) {
            Experium.positionManager.setPositionPacket(Killaura.target.posX, EntityUtil.canEntityFeetBeSeen(target) ? Killaura.target.posY : Killaura.target.posY + (double)target.getEyeHeight(), Killaura.target.posZ, true, true, this.lagBack.getValue() == false);
        }
        if (this.armorBreak.getValue().booleanValue()) {
            Killaura.mc.playerController.windowClick(Killaura.mc.player.inventoryContainer.windowId, 9, Killaura.mc.player.inventory.currentItem, ClickType.SWAP, (EntityPlayer)Killaura.mc.player);
            EntityUtil.attackEntity(target, this.packet.getValue(), this.swing.getValue());
            Killaura.mc.playerController.windowClick(Killaura.mc.player.inventoryContainer.windowId, 9, Killaura.mc.player.inventory.currentItem, ClickType.SWAP, (EntityPlayer)Killaura.mc.player);
            EntityUtil.attackEntity(target, this.packet.getValue(), this.swing.getValue());
        } else {
            boolean sneaking = Killaura.mc.player.isSneaking();
            boolean sprint = Killaura.mc.player.isSprinting();
            if (this.sneak.getValue().booleanValue()) {
                if (sneaking) {
                    Killaura.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Killaura.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
                }
                if (sprint) {
                    Killaura.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Killaura.mc.player, CPacketEntityAction.Action.STOP_SPRINTING));
                }
            }
            EntityUtil.attackEntity(target, this.packet.getValue(), this.swing.getValue());
            if (this.sneak.getValue().booleanValue()) {
                if (sprint) {
                    Killaura.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Killaura.mc.player, CPacketEntityAction.Action.START_SPRINTING));
                }
                if (sneaking) {
                    Killaura.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity)Killaura.mc.player, CPacketEntityAction.Action.START_SNEAKING));
                }
            }
        }
        this.timer.reset();
    }

    private void startEntityAttackThread(Entity entity, int time) {
        new Thread(() -> {
            Timer timer = new Timer();
            timer.reset();
            try {
                Thread.sleep(time);
            }
            catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            EntityUtil.attackEntity(entity, true, this.swing.getValue());
        }).start();
    }

    private Entity getTarget() {
        Entity target = null;
        double distance = this.teleport.getValue() != false ? (double)this.teleportRange.getValue().floatValue() : (double)this.range.getValue().floatValue();
        double maxHealth = 36.0;
        for (Entity entity : Killaura.mc.world.loadedEntityList) {
            if (!(this.players.getValue() != false && entity instanceof EntityPlayer || this.animals.getValue() != false && EntityUtil.isPassive(entity) || this.mobs.getValue() != false && EntityUtil.isMobAggressive(entity) || this.vehicles.getValue() != false && EntityUtil.isVehicle(entity)) && (!this.projectiles.getValue().booleanValue() || !EntityUtil.isProjectile(entity)) || entity instanceof EntityLivingBase && EntityUtil.isntValid(entity, distance) || !this.teleport.getValue().booleanValue() && !Killaura.mc.player.canEntityBeSeen(entity) && !EntityUtil.canEntityFeetBeSeen(entity) && Killaura.mc.player.getDistanceSq(entity) > MathUtil.square(this.raytrace.getValue().floatValue())) continue;
            if (target == null) {
                target = entity;
                distance = Killaura.mc.player.getDistanceSq(entity);
                maxHealth = EntityUtil.getHealth(entity);
                continue;
            }
            if (entity instanceof EntityPlayer && DamageUtil.isArmorLow((EntityPlayer)entity, 18)) {
                target = entity;
                break;
            }
            if (this.targetMode.getValue() == TargetMode.SMART && EntityUtil.getHealth(entity) < this.health.getValue().floatValue()) {
                target = entity;
                break;
            }
            if (this.targetMode.getValue() != TargetMode.HEALTH && Killaura.mc.player.getDistanceSq(entity) < distance) {
                target = entity;
                distance = Killaura.mc.player.getDistanceSq(entity);
                maxHealth = EntityUtil.getHealth(entity);
            }
            if (this.targetMode.getValue() != TargetMode.HEALTH || !((double)EntityUtil.getHealth(entity) < maxHealth)) continue;
            target = entity;
            distance = Killaura.mc.player.getDistanceSq(entity);
            maxHealth = EntityUtil.getHealth(entity);
        }
        return target;
    }

    public static enum TargetMode {
        FOCUS,
        CLOSEST,
        HEALTH,
        SMART;

    }
}

