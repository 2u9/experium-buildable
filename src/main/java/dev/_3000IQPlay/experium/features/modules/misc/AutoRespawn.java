//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Luni\Documents\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev._3000IQPlay.experium.features.modules.misc;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import dev._3000IQPlay.experium.features.command.Command;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraftforge.client.event.GuiOpenEvent;
import dev._3000IQPlay.experium.features.setting.Setting;
import dev._3000IQPlay.experium.features.modules.Module;

public class AutoRespawn extends Module
{
    public Setting<Boolean> antiDeathScreen;
    public Setting<Boolean> deathCoords;
    public Setting<Boolean> respawn;
    
    public AutoRespawn() {
        super("AutoRespawn", "Respawns you when you die.", Category.MISC, true, false, false);
        this.antiDeathScreen = (Setting<Boolean>)this.register(new Setting("NoDeathScreen", (T)true));
        this.deathCoords = (Setting<Boolean>)this.register(new Setting("DeathCoords", (T)false));
        this.respawn = (Setting<Boolean>)this.register(new Setting("Respawn", (T)true));
    }
    
    @SubscribeEvent
    public void onDisplayDeathScreen(final GuiOpenEvent event) {
        if (event.getGui() instanceof GuiGameOver) {
            if (this.deathCoords.getValue() && event.getGui() instanceof GuiGameOver) {
                Command.sendMessage(String.format("You died at x %d y %d z %d", (int)AutoRespawn.mc.player.posX, (int)AutoRespawn.mc.player.posY, (int)AutoRespawn.mc.player.posZ));
            }
            if ((this.respawn.getValue() && AutoRespawn.mc.player.getHealth() <= 0.0f) || (this.antiDeathScreen.getValue() && AutoRespawn.mc.player.getHealth() > 0.0f)) {
                event.setCanceled(true);
                AutoRespawn.mc.player.respawnPlayer();
            }
        }
    }
}
