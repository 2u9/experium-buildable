//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Luni\Documents\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.material.Material
 *  net.minecraft.network.Packet
 *  net.minecraft.network.play.client.CPacketPlayer$Position
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 */
package dev._3000IQPlay.experium.features.modules.movement;

import dev._3000IQPlay.experium.event.events.StepEvent;
import dev._3000IQPlay.experium.features.modules.Module;
import dev._3000IQPlay.experium.features.setting.Setting;
import dev._3000IQPlay.experium.util.EntityUtil;
import net.minecraft.block.material.Material;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Step
extends Module {
    private static Step instance;
    public Setting<Boolean> vanilla = this.register(new Setting<Boolean>("Vanilla", true));
    public Setting<Integer> stepHeight = this.register(new Setting<Integer>("Height", 2, 1, 10));
    public Setting<Boolean> noLiquid = this.register(new Setting<Boolean>("NoLiquid", true));
    public Setting<Boolean> turnOff = this.register(new Setting<Boolean>("Disable", false));

    public Step() {
        super("Step", "Allows you to step up blocks", Module.Category.MOVEMENT, true, false, false);
        instance = this;
    }

    public static Step getInstance() {
        if (instance == null) {
            instance = new Step();
        }
        return instance;
    }

    @Override
    public void onUpdate() {
        if (Step.fullNullCheck()) {
            return;
        }
        if (this.noLiquid.getValue().booleanValue() && EntityUtil.isInLiquid()) {
            return;
        }
    }

    @SubscribeEvent
    public void onStep(StepEvent event) {
        if (Step.mc.player.onGround && !Step.mc.player.isInsideOfMaterial(Material.WATER) && !Step.mc.player.isInsideOfMaterial(Material.LAVA) && Step.mc.player.collidedVertically && Step.mc.player.fallDistance == 0.0f && !Step.mc.gameSettings.keyBindJump.pressed && !Step.mc.player.isOnLadder()) {
            event.setHeight(this.stepHeight.getValue().intValue());
            double rheight = Step.mc.player.getEntityBoundingBox().minY - Step.mc.player.posY;
            if (rheight >= 0.625) {
                if (!this.vanilla.getValue().booleanValue()) {
                    this.ncpStep(rheight);
                }
                if (this.turnOff.getValue().booleanValue()) {
                    this.disable();
                }
            }
        } else {
            event.setHeight(0.6f);
        }
    }

    private void ncpStep(double height) {
        block12: {
            double y;
            double posZ;
            double posX;
            block11: {
                posX = Step.mc.player.posX;
                posZ = Step.mc.player.posZ;
                y = Step.mc.player.posY;
                if (!(height < 1.1)) break block11;
                double first = 0.42;
                double second = 0.75;
                if (height != 1.0) {
                    first *= height;
                    second *= height;
                    if (first > 0.425) {
                        first = 0.425;
                    }
                    if (second > 0.78) {
                        second = 0.78;
                    }
                    if (second < 0.49) {
                        second = 0.49;
                    }
                }
                Step.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(posX, y + first, posZ, false));
                if (!(y + second < y + height)) break block12;
                Step.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(posX, y + second, posZ, false));
                break block12;
            }
            if (height < 1.6) {
                double[] offset = new double[]{0.42, 0.33, 0.24, 0.083, -0.078};
                for (double off : offset) {
                    Step.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(posX, y += off, posZ, false));
                }
            } else if (height < 2.1) {
                double[] heights = new double[]{0.425, 0.821, 0.699, 0.599, 1.022, 1.372, 1.652, 1.869};
                for (double off : heights) {
                    Step.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(posX, y + off, posZ, false));
                }
            } else {
                double[] heights = new double[]{0.425, 0.821, 0.699, 0.599, 1.022, 1.372, 1.652, 1.869, 2.019, 1.907};
                for (double off : heights) {
                    Step.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Position(posX, y + off, posZ, false));
                }
            }
        }
    }
}

