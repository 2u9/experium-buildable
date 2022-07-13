//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Luni\Documents\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev._3000IQPlay.experium.mixin.mixins;

import net.minecraft.client.multiplayer.PlayerControllerMP;
import dev._3000IQPlay.experium.features.modules.player.MultiTask;
import net.minecraft.client.entity.EntityPlayerSP;
import dev._3000IQPlay.experium.features.modules.render.NoRender;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.crash.CrashReport;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.lwjgl.opengl.Display;
import dev._3000IQPlay.experium.features.modules.client.Managers;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import dev._3000IQPlay.experium.Experium;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.Shadow;
import javax.annotation.Nullable;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ Minecraft.class })
public abstract class MixinMinecraft
{
    @Shadow
    public abstract void displayGuiScreen(@Nullable final GuiScreen p0);
    
    @Inject(method = { "runTickKeyboard" }, at = { @At(value = "FIELD", target = "Lnet/minecraft/client/Minecraft;currentScreen:Lnet/minecraft/client/gui/GuiScreen;", ordinal = 0) }, locals = LocalCapture.CAPTURE_FAILSOFT)
    private void onRunTickKeyboard(final CallbackInfo ci, final int i) {
        if (Keyboard.getEventKeyState() && Experium.moduleManager != null) {
            Experium.moduleManager.onKeyPressed(i);
        }
    }
    
    @Inject(method = { "getLimitFramerate" }, at = { @At("HEAD") }, cancellable = true)
    public void getLimitFramerateHook(final CallbackInfoReturnable<Integer> callbackInfoReturnable) {
        try {
            if (Managers.getInstance().unfocusedCpu.getValue() && !Display.isActive()) {
                callbackInfoReturnable.setReturnValue(Managers.getInstance().cpuFPS.getValue());
            }
        }
        catch (NullPointerException ex) {}
    }
    
    @Redirect(method = { "runGameLoop" }, at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/Display;sync(I)V", remap = false))
    public void syncHook(final int maxFps) {
        if (Managers.getInstance().betterFrames.getValue()) {
            Display.sync((int)Managers.getInstance().betterFPS.getValue());
        }
        else {
            Display.sync(maxFps);
        }
    }
    
    @Redirect(method = { "run" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;displayCrashReport(Lnet/minecraft/crash/CrashReport;)V"))
    public void displayCrashReportHook(final Minecraft minecraft, final CrashReport crashReport) {
        this.unload();
    }
    
    @Redirect(method = { "runTick" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/WorldClient;doVoidFogParticles(III)V"))
    public void doVoidFogParticlesHook(final WorldClient world, final int x, final int y, final int z) {
        NoRender.getInstance().doVoidFogParticles(x, y, z);
    }
    
    @Inject(method = { "shutdown" }, at = { @At("HEAD") })
    public void shutdownHook(final CallbackInfo info) {
        this.unload();
    }
    
    private void unload() {
        System.out.println("Shutting down: saving configuration");
        Experium.onUnload();
        System.out.println("Configuration saved.");
    }
    
    @Redirect(method = { "sendClickBlockToController" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/EntityPlayerSP;isHandActive()Z"))
    private boolean isHandActiveWrapper(final EntityPlayerSP playerSP) {
        return !MultiTask.getInstance().isOn() && playerSP.isHandActive();
    }
    
    @Redirect(method = { "rightClickMouse" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/PlayerControllerMP;getIsHittingBlock()Z", ordinal = 0))
    private boolean isHittingBlockHook(final PlayerControllerMP playerControllerMP) {
        return !MultiTask.getInstance().isOn() && playerControllerMP.getIsHittingBlock();
    }
}
