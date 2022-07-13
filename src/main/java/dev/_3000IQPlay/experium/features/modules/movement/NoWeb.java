//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Luni\Documents\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.block.Block
 *  net.minecraft.block.BlockWeb
 *  net.minecraftforge.fml.common.eventhandler.SubscribeEvent
 *  org.lwjgl.input.Keyboard
 */
package dev._3000IQPlay.experium.features.modules.movement;

import dev._3000IQPlay.experium.Experium;
import dev._3000IQPlay.experium.event.events.BlockCollisionBoundingBoxEvent;
import dev._3000IQPlay.experium.features.modules.Module;
import dev._3000IQPlay.experium.features.setting.Setting;
import net.minecraft.block.Block;
import net.minecraft.block.BlockWeb;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

public class NoWeb
extends Module {
    public Setting<Boolean> disableBB = this.register(new Setting<Boolean>("Add BB", true));
    public Setting<Boolean> onGround = this.register(new Setting<Boolean>("On Ground", true));
    public Setting<Double> bbOffset = this.register(new Setting<Double>("BB Offset", 0.0, -2.0, 2.0));
    public Setting<Double> motionY = this.register(new Setting<Double>("MotionY", 1.0, 0.0, 20.0));
    public Setting<Double> motionX = this.register(new Setting<Double>("MotionX", 0.84, -1.0, 5.0));

    public NoWeb() {
        super("NoWeb", "Prevents you from getting slowed by web", Module.Category.MOVEMENT, true, false, false);
    }

    @SubscribeEvent
    public void bbEvent(BlockCollisionBoundingBoxEvent event) {
        if (NoWeb.nullCheck()) {
            return;
        }
        if (NoWeb.mc.world.getBlockState(event.getPos()).getBlock() instanceof BlockWeb && this.disableBB.getValue().booleanValue()) {
            event.setCanceled(true);
            event.setBoundingBox(Block.FULL_BLOCK_AABB.contract(0.0, this.bbOffset.getValue().doubleValue(), 0.0));
        }
    }

    @Override
    public void onUpdate() {
        if (NoWeb.mc.player.isInWeb && !Experium.moduleManager.isModuleEnabled("Step")) {
            if (Keyboard.isKeyDown((int)NoWeb.mc.gameSettings.keyBindSneak.getKeyCode())) {
                NoWeb.mc.player.isInWeb = true;
                NoWeb.mc.player.motionY *= this.motionY.getValue().doubleValue();
            } else if (this.onGround.getValue().booleanValue()) {
                NoWeb.mc.player.onGround = false;
            }
            if (Keyboard.isKeyDown((int)NoWeb.mc.gameSettings.keyBindForward.keyCode) || Keyboard.isKeyDown((int)NoWeb.mc.gameSettings.keyBindBack.keyCode) || Keyboard.isKeyDown((int)NoWeb.mc.gameSettings.keyBindLeft.keyCode) || Keyboard.isKeyDown((int)NoWeb.mc.gameSettings.keyBindRight.keyCode)) {
                NoWeb.mc.player.isInWeb = false;
                NoWeb.mc.player.motionX *= this.motionX.getValue().doubleValue();
                NoWeb.mc.player.motionZ *= this.motionX.getValue().doubleValue();
            }
        }
    }
}

