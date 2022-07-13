//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Luni\Documents\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev._3000IQPlay.experium.features.gui.components.items.buttons;

import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.init.SoundEvents;
import dev._3000IQPlay.experium.util.Util;
import dev._3000IQPlay.experium.features.gui.ExperiumGui;
import dev._3000IQPlay.experium.util.RenderUtil;
import dev._3000IQPlay.experium.util.ColorUtil;
import dev._3000IQPlay.experium.Experium;
import dev._3000IQPlay.experium.util.MathUtil;
import dev._3000IQPlay.experium.features.modules.client.HUD;
import dev._3000IQPlay.experium.features.modules.client.ClickGui;
import dev._3000IQPlay.experium.features.setting.Setting;

public class BooleanButton extends Button
{
    private final Setting setting;
    
    public BooleanButton(final Setting setting) {
        super(setting.getName());
        this.setting = setting;
        this.width = 40;
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        if (ClickGui.getInstance().rainbowRolling.getValue()) {
            final int color = ColorUtil.changeAlpha(HUD.getInstance().colorMap.get(MathUtil.clamp((int)this.y, 0, this.renderer.scaledHeight)), Experium.moduleManager.getModuleByClass(ClickGui.class).hoverAlpha.getValue());
            final int color2 = ColorUtil.changeAlpha(HUD.getInstance().colorMap.get(MathUtil.clamp((int)this.y + this.height, 0, this.renderer.scaledHeight)), Experium.moduleManager.getModuleByClass(ClickGui.class).hoverAlpha.getValue());
            RenderUtil.drawGradientRect(this.x, this.y, this.width + 7.4f, this.height - 0.5f, this.getState() ? (this.isHovering(mouseX, mouseY) ? color : HUD.getInstance().colorMap.get(MathUtil.clamp((int)this.y, 0, this.renderer.scaledHeight))) : (this.isHovering(mouseX, mouseY) ? -2007673515 : 290805077), this.getState() ? (this.isHovering(mouseX, mouseY) ? color2 : HUD.getInstance().colorMap.get(MathUtil.clamp((int)this.y + this.height, 0, this.renderer.scaledHeight))) : (this.isHovering(mouseX, mouseY) ? -2007673515 : 290805077));
        }
        else {
            RenderUtil.drawRect(this.x, this.y, this.x + this.width + 7.4f, this.y + this.height - 0.5f, this.getState() ? (this.isHovering(mouseX, mouseY) ? Experium.colorManager.getColorWithAlpha(Experium.moduleManager.getModuleByClass(ClickGui.class).alpha.getValue()) : Experium.colorManager.getColorWithAlpha(Experium.moduleManager.getModuleByClass(ClickGui.class).hoverAlpha.getValue())) : (this.isHovering(mouseX, mouseY) ? -2007673515 : 290805077));
        }
        Experium.textManager.drawStringWithShadow(this.getName(), this.x + 2.3f, this.y - 1.7f - ExperiumGui.getClickGui().getTextOffset(), this.getState() ? -1 : -5592406);
        if (ClickGui.getInstance().enableSwitch.getValue()) {
            Experium.textManager.drawStringWithShadow(this.getName(), this.x + 2.3f, this.y - 1.7f - ExperiumGui.getClickGui().getTextOffset(), this.getState() ? -1 : -5592406);
            RenderUtil.drawRect(this.x + 85.0f, this.y + 5.0f, this.x + this.width + 3.5f, this.y + this.height - 2.0f, this.getState() ? ColorUtil.toRGBA(ClickGui.getInstance().sbRed.getValue(), ClickGui.getInstance().sbGreen.getValue(), ClickGui.getInstance().sbBlue.getValue(), ClickGui.getInstance().sbAlpha.getValue()) : ColorUtil.toRGBA(ClickGui.getInstance().sbRed.getValue(), ClickGui.getInstance().sbGreen.getValue(), ClickGui.getInstance().sbBlue.getValue(), ClickGui.getInstance().sbAlpha.getValue()));
            if (this.getState()) {
                RenderUtil.drawRect(this.x + 93.0f, this.y + 6.0f, this.x + this.width + 2.5f, this.y + this.height - 3.0f, this.getState() ? ColorUtil.toRGBA(ClickGui.getInstance().seRed.getValue(), ClickGui.getInstance().seGreen.getValue(), ClickGui.getInstance().seBlue.getValue(), ClickGui.getInstance().seAlpha.getValue()) : ColorUtil.toRGBA(ClickGui.getInstance().seRed.getValue(), ClickGui.getInstance().seGreen.getValue(), ClickGui.getInstance().seBlue.getValue(), ClickGui.getInstance().seAlpha.getValue()));
            }
            else {
                RenderUtil.drawRect(this.x + 86.0f, this.y + 6.0f, this.x + this.width - 4.7f, this.y + this.height - 3.0f, this.getState() ? ColorUtil.toRGBA(ClickGui.getInstance().sdRed.getValue(), ClickGui.getInstance().sdGreen.getValue(), ClickGui.getInstance().sdBlue.getValue(), ClickGui.getInstance().sdAlpha.getValue()) : ColorUtil.toRGBA(ClickGui.getInstance().sdRed.getValue(), ClickGui.getInstance().sdGreen.getValue(), ClickGui.getInstance().sdBlue.getValue(), ClickGui.getInstance().sdAlpha.getValue()));
            }
        }
    }
    
    @Override
    public void update() {
        this.setHidden(!this.setting.isVisible());
    }
    
    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (this.isHovering(mouseX, mouseY)) {
            Util.mc.getSoundHandler().playSound((ISound)PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0f));
        }
    }
    
    @Override
    public int getHeight() {
        return 14;
    }
    
    @Override
    public void toggle() {
        this.setting.setValue(!this.setting.getValue());
    }
    
    @Override
    public boolean getState() {
        return this.setting.getValue();
    }
}
