//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Luni\Documents\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev._3000IQPlay.experium.features.gui;

import java.io.IOException;
import org.lwjgl.input.Mouse;
import net.minecraft.client.gui.ScaledResolution;
import dev._3000IQPlay.experium.util.RenderUtil;
import java.awt.Color;
import dev._3000IQPlay.experium.features.modules.client.Colors;
import dev._3000IQPlay.experium.features.modules.client.ClickGui;
import dev._3000IQPlay.experium.features.gui.components.items.Item;
import java.util.Iterator;
import dev._3000IQPlay.experium.features.gui.components.items.buttons.Button;
import dev._3000IQPlay.experium.features.gui.components.items.buttons.ModuleButton;
import dev._3000IQPlay.experium.features.modules.Module;
import dev._3000IQPlay.experium.Experium;
import java.util.Random;
import dev._3000IQPlay.experium.features.gui.particle.Snow;
import dev._3000IQPlay.experium.features.gui.particle.ParticleSystem;
import dev._3000IQPlay.experium.features.gui.components.Component;
import java.util.ArrayList;
import dev._3000IQPlay.experium.features.gui.components.items.DescriptionDisplay;
import net.minecraft.client.gui.GuiScreen;

public class ExperiumGui extends GuiScreen
{
    private static ExperiumGui experiumGui;
    private static ExperiumGui INSTANCE;
    private static DescriptionDisplay descriptionDisplay;
    private final ArrayList<Component> components;
    public ParticleSystem particleSystem;
    private ArrayList<Snow> _snowList;
    private ExperiumGui ClickGuiMod;
    
    public ExperiumGui() {
        this.components = new ArrayList<Component>();
        this._snowList = new ArrayList<Snow>();
        this.setInstance();
        this.load();
    }
    
    public static ExperiumGui getInstance() {
        if (ExperiumGui.INSTANCE == null) {
            ExperiumGui.INSTANCE = new ExperiumGui();
        }
        return ExperiumGui.INSTANCE;
    }
    
    public static ExperiumGui getClickGui() {
        return getInstance();
    }
    
    private void setInstance() {
        ExperiumGui.INSTANCE = this;
    }
    
    private void load() {
        int x = -109;
        final Random random = new Random();
        for (int i = 0; i < 100; ++i) {
            for (int y = 0; y < 3; ++y) {
                final Snow snow = new Snow(25 * i, y * -50, random.nextInt(3) + 1, random.nextInt(2) + 1);
                this._snowList.add(snow);
            }
        }
        for (final Module.Category category : Experium.moduleManager.getCategories()) {
            final ArrayList<Component> components2 = this.components;
            final String name = category.getName();
            x += 115;
            components2.add(new Component(name, x, 4, true) {
                @Override
                public void setupItems() {
                    Experium.moduleManager.getModulesByCategory(category).forEach(module -> {
                        if (!module.hidden) {
                            this.addButton(new ModuleButton(module));
                        }
                    });
                }
            });
        }
        this.components.forEach(components -> components.getItems().sort((item1, item2) -> item1.getName().compareTo(item2.getName())));
    }
    
    public void updateModule(final Module module) {
        for (final Component component : this.components) {
            for (final Item item : component.getItems()) {
                if (!(item instanceof ModuleButton)) {
                    continue;
                }
                final ModuleButton button = (ModuleButton)item;
                final Module mod = button.getModule();
                if (module == null) {
                    continue;
                }
                if (!module.equals(mod)) {
                    continue;
                }
                button.initSettings();
                break;
            }
        }
    }
    
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        final ClickGui clickGui = ClickGui.getInstance();
        if (ClickGui.getInstance().guiBackground.getValue()) {
            RenderUtil.drawRect(0.0f, 0.0f, (float)this.width, (float)this.height, ((boolean)ClickGui.getInstance().colorSync.getValue()) ? Colors.INSTANCE.getCurrentColor().getRGB() : new Color(ClickGui.getInstance().gbRed.getValue(), ClickGui.getInstance().gbGreen.getValue(), ClickGui.getInstance().gbBlue.getValue(), ClickGui.getInstance().gbAlpha.getValue()).getRGB());
        }
        ExperiumGui.descriptionDisplay.setDraw(false);
        this.checkMouseWheel();
        this.components.forEach(components -> components.drawScreen(mouseX, mouseY, partialTicks));
        final ScaledResolution sr = new ScaledResolution(this.mc);
        if (ClickGui.getInstance().gradiant.getValue()) {
            this.drawGradientRect(0, 0, sr.getScaledWidth(), sr.getScaledHeight(), 0, ((boolean)ClickGui.getInstance().colorSync.getValue()) ? Colors.INSTANCE.getCurrentColor().getRGB() : new Color(ClickGui.getInstance().gradiantRed.getValue(), ClickGui.getInstance().gradiantGreen.getValue(), ClickGui.getInstance().gradiantBlue.getValue(), ClickGui.getInstance().gradiantAlpha.getValue() / 2).getRGB());
        }
        if (ExperiumGui.descriptionDisplay.shouldDraw() && clickGui.moduleDescription.getValue()) {
            ExperiumGui.descriptionDisplay.drawScreen(mouseX, mouseY, partialTicks);
        }
        final ScaledResolution res = new ScaledResolution(this.mc);
        if (!this._snowList.isEmpty() && ClickGui.getInstance().snowing.getValue()) {
            this._snowList.forEach(snow -> snow.Update(res));
        }
        if (this.particleSystem != null && ClickGui.getInstance().particles.getValue()) {
            this.particleSystem.render(mouseX, mouseY);
        }
        else {
            this.particleSystem = new ParticleSystem(new ScaledResolution(this.mc));
        }
    }
    
    public void mouseClicked(final int mouseX, final int mouseY, final int clickedButton) {
        this.components.forEach(components -> components.mouseClicked(mouseX, mouseY, clickedButton));
    }
    
    public void mouseReleased(final int mouseX, final int mouseY, final int releaseButton) {
        this.components.forEach(components -> components.mouseReleased(mouseX, mouseY, releaseButton));
    }
    
    public boolean doesGuiPauseGame() {
        return false;
    }
    
    public final ArrayList<Component> getComponents() {
        return this.components;
    }
    
    public void checkMouseWheel() {
        final int dWheel = Mouse.getDWheel();
        if (dWheel < 0) {
            if (ClickGui.getInstance().scroll.getValue()) {
                this.components.forEach(component -> component.setY(component.getY() - ClickGui.getInstance().scrollval.getValue()));
            }
        }
        else if (dWheel > 0 && ClickGui.getInstance().scroll.getValue()) {
            this.components.forEach(component -> component.setY(component.getY() + ClickGui.getInstance().scrollval.getValue()));
        }
    }
    
    public int getTextOffset() {
        return -6;
    }
    
    public DescriptionDisplay getDescriptionDisplay() {
        return ExperiumGui.descriptionDisplay;
    }
    
    public Component getComponentByName(final String name) {
        for (final Component component : this.components) {
            if (!component.getName().equalsIgnoreCase(name)) {
                continue;
            }
            return component;
        }
        return null;
    }
    
    public void keyTyped(final char typedChar, final int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
        this.components.forEach(component -> component.onKeyTyped(typedChar, keyCode));
    }
    
    public void updateScreen() {
        if (this.particleSystem != null) {
            this.particleSystem.update();
        }
    }
    
    static {
        ExperiumGui.INSTANCE = new ExperiumGui();
        ExperiumGui.descriptionDisplay = new DescriptionDisplay("", 0.0f, 0.0f);
    }
}
