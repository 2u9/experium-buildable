//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Luni\Documents\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev._3000IQPlay.experium.features.modules.movement;

import net.minecraft.client.entity.EntityPlayerSP;
import org.lwjgl.input.Keyboard;
import dev._3000IQPlay.experium.Experium;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.block.BlockWeb;
import dev._3000IQPlay.experium.event.events.BlockCollisionBoundingBoxEvent;
import dev._3000IQPlay.experium.features.setting.Setting;
import dev._3000IQPlay.experium.features.modules.Module;

public class NoWeb extends Module
{
    public Setting<Boolean> disableBB;
    public Setting<Boolean> onGround;
    public Setting<Double> bbOffset;
    public Setting<Double> motionY;
    public Setting<Double> motionX;
    
    public NoWeb() {
        super("NoWeb", "Prevents you from getting slowed by web", Category.MOVEMENT, true, false, false);
        this.disableBB = (Setting<Boolean>)this.register(new Setting("Add BB", (T)true));
        this.onGround = (Setting<Boolean>)this.register(new Setting("On Ground", (T)true));
        this.bbOffset = (Setting<Double>)this.register(new Setting("BB Offset", (T)0.0, (T)(-2.0), (T)2.0));
        this.motionY = (Setting<Double>)this.register(new Setting("MotionY", (T)1.0, (T)0.0, (T)20.0));
        this.motionX = (Setting<Double>)this.register(new Setting("MotionX", (T)0.84, (T)(-1.0), (T)5.0));
    }
    
    @SubscribeEvent
    public void bbEvent(final BlockCollisionBoundingBoxEvent event) {
        if (nullCheck()) {
            return;
        }
        if (NoWeb.mc.world.getBlockState(event.getPos()).getBlock() instanceof BlockWeb && this.disableBB.getValue()) {
            event.setCanceled(true);
            event.setBoundingBox(Block.FULL_BLOCK_AABB.contract(0.0, (double)this.bbOffset.getValue(), 0.0));
        }
    }
    
    @Override
    public void onUpdate() {
        if (NoWeb.mc.player.isInWeb && !Experium.moduleManager.isModuleEnabled("Step")) {
            if (Keyboard.isKeyDown(NoWeb.mc.gameSettings.keyBindSneak.getKeyCode())) {
                NoWeb.mc.player.isInWeb = true;
                final EntityPlayerSP player = NoWeb.mc.player;
                player.motionY *= this.motionY.getValue();
            }
            else if (this.onGround.getValue()) {
                NoWeb.mc.player.onGround = false;
            }
            if (Keyboard.isKeyDown(NoWeb.mc.gameSettings.keyBindForward.keyCode) || Keyboard.isKeyDown(NoWeb.mc.gameSettings.keyBindBack.keyCode) || Keyboard.isKeyDown(NoWeb.mc.gameSettings.keyBindLeft.keyCode) || Keyboard.isKeyDown(NoWeb.mc.gameSettings.keyBindRight.keyCode)) {
                NoWeb.mc.player.isInWeb = false;
                final EntityPlayerSP player2 = NoWeb.mc.player;
                player2.motionX *= this.motionX.getValue();
                final EntityPlayerSP player3 = NoWeb.mc.player;
                player3.motionZ *= this.motionX.getValue();
            }
        }
    }
}
