// 
// Decompiled by Procyon v0.5.36
// 

package dev._3000IQPlay.experium.features.gui.components.items;

import dev._3000IQPlay.experium.util.RenderUtil;
import dev._3000IQPlay.experium.Experium;

public class DescriptionDisplay extends Item
{
    private String description;
    private boolean draw;
    
    public DescriptionDisplay(final String description, final float x, final float y) {
        super("DescriptionDisplay");
        this.description = description;
        this.setLocation(x, y);
        this.width = Experium.textManager.getStringWidth(this.description) + 4;
        this.height = Experium.textManager.getFontHeight() + 4;
        this.draw = false;
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        this.width = Experium.textManager.getStringWidth(this.description) + 4;
        this.height = Experium.textManager.getFontHeight() + 4;
        RenderUtil.drawRect(this.x, this.y, this.x + this.width, this.y + this.height, -704643072);
        Experium.textManager.drawString(this.description, this.x + 2.0f, this.y + 2.0f, 16777215, true);
    }
    
    public boolean shouldDraw() {
        return this.draw;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(final String description) {
        this.description = description;
    }
    
    public void setDraw(final boolean draw) {
        this.draw = draw;
    }
}
