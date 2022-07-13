//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Luni\Documents\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev._3000IQPlay.experium.features.gui.components.items.buttons;

import java.util.Iterator;
import dev._3000IQPlay.experium.features.gui.components.Component;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.init.SoundEvents;
import dev._3000IQPlay.experium.features.gui.ExperiumGui;
import dev._3000IQPlay.experium.Experium;
import dev._3000IQPlay.experium.util.MathUtil;
import dev._3000IQPlay.experium.features.modules.client.HUD;
import dev._3000IQPlay.experium.util.RenderUtil;
import dev._3000IQPlay.experium.util.ColorUtil;
import dev._3000IQPlay.experium.features.modules.client.ClickGui;
import dev._3000IQPlay.experium.features.gui.components.items.Item;

public class Button extends Item
{
    private boolean state;
    
    public Button(final String name) {
        super(name);
        this.height = ClickGui.getInstance().moduleButtonHeight.getValue() + 1;
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        if (ClickGui.getInstance().moduleOutline.getValue()) {
            RenderUtil.drawOutlineRect(this.x, this.y, this.x + this.width, this.y + this.height - 0.9f, this.getState() ? ColorUtil.toRGBA(ClickGui.getInstance().moRed.getValue(), ClickGui.getInstance().moGreen.getValue(), ClickGui.getInstance().moBlue.getValue(), ClickGui.getInstance().moAlpha.getValue()) : ColorUtil.toRGBA(ClickGui.getInstance().moRed.getValue(), ClickGui.getInstance().moGreen.getValue(), ClickGui.getInstance().moBlue.getValue(), ClickGui.getInstance().moAlpha.getValue()));
        }
        if (ClickGui.getInstance().moduleSeperate.getValue()) {
            RenderUtil.drawRect(this.x + 2.0f, this.y - 0.5f, this.x + this.width - 2.0f, this.y - 0.2f, this.getState() ? ColorUtil.toRGBA(ClickGui.getInstance().mosRed.getValue(), ClickGui.getInstance().mosGreen.getValue(), ClickGui.getInstance().mosBlue.getValue(), ClickGui.getInstance().mosAlpha.getValue()) : ColorUtil.toRGBA(ClickGui.getInstance().mosRed.getValue(), ClickGui.getInstance().mosGreen.getValue(), ClickGui.getInstance().mosBlue.getValue(), ClickGui.getInstance().mosAlpha.getValue()));
        }
        if (ClickGui.getInstance().rainbowRolling.getValue()) {
            final int color = ColorUtil.changeAlpha(HUD.getInstance().colorMap.get(MathUtil.clamp((int)this.y, 0, this.renderer.scaledHeight)), Experium.moduleManager.getModuleByClass(ClickGui.class).hoverAlpha.getValue());
            final int color2 = ColorUtil.changeAlpha(HUD.getInstance().colorMap.get(MathUtil.clamp((int)this.y + this.height, 0, this.renderer.scaledHeight)), Experium.moduleManager.getModuleByClass(ClickGui.class).hoverAlpha.getValue());
            RenderUtil.drawGradientRect(this.x, this.y, (float)this.width, this.height - 0.5f, this.getState() ? (this.isHovering(mouseX, mouseY) ? color : HUD.getInstance().colorMap.get(MathUtil.clamp((int)this.y, 0, this.renderer.scaledHeight))) : (this.isHovering(mouseX, mouseY) ? -2007673515 : 290805077), this.getState() ? (this.isHovering(mouseX, mouseY) ? color2 : HUD.getInstance().colorMap.get(MathUtil.clamp((int)this.y + this.height, 0, this.renderer.scaledHeight))) : (this.isHovering(mouseX, mouseY) ? -2007673515 : 290805077));
        }
        else {
            RenderUtil.drawRect(this.x, this.y, this.x + this.width, this.y + this.height - 0.5f, this.getState() ? (this.isHovering(mouseX, mouseY) ? Experium.colorManager.getColorWithAlpha(Experium.moduleManager.getModuleByClass(ClickGui.class).alpha.getValue()) : Experium.colorManager.getColorWithAlpha(Experium.moduleManager.getModuleByClass(ClickGui.class).hoverAlpha.getValue())) : (this.isHovering(mouseX, mouseY) ? -2007673515 : 290805077));
        }
        Experium.textManager.drawStringWithShadow(this.getName(), this.x + 2.3f, this.y - 2.0f - ExperiumGui.getClickGui().getTextOffset(), this.getState() ? ColorUtil.toRGBA(ClickGui.getInstance().textRed.getValue(), ClickGui.getInstance().textGreen.getValue(), ClickGui.getInstance().textBlue.getValue(), ClickGui.getInstance().textAlpha.getValue()) : ColorUtil.toRGBA(ClickGui.getInstance().textRed2.getValue(), ClickGui.getInstance().textGreen2.getValue(), ClickGui.getInstance().textBlue2.getValue(), ClickGui.getInstance().textAlpha2.getValue()));
    }
    
    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        if (mouseButton == 0 && this.isHovering(mouseX, mouseY)) {
            this.onMouseClick();
        }
    }
    
    public void onMouseClick() {
        this.state = !this.state;
        this.toggle();
        Button.mc.getSoundHandler().playSound((ISound)PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0f));
    }
    
    public void toggle() {
    }
    
    public boolean getState() {
        return this.state;
    }
    
    @Override
    public int getHeight() {
        return 14;
    }
    
    public boolean isHovering(final int mouseX, final int mouseY) {
        for (final Component component : ExperiumGui.getClickGui().getComponents()) {
            if (!component.drag) {
                continue;
            }
            return false;
        }
        return mouseX >= this.getX() && mouseX <= this.getX() + this.getWidth() && mouseY >= this.getY() && mouseY <= this.getY() + this.height;
    }
}
