//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Luni\Documents\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev._3000IQPlay.experium.features.modules.player;

import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketChatMessage;
import dev._3000IQPlay.experium.features.modules.client.ClickGui;
import dev._3000IQPlay.experium.features.modules.client.PingBypass;
import dev._3000IQPlay.experium.features.command.Command;
import dev._3000IQPlay.experium.Experium;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import dev._3000IQPlay.experium.features.gui.ExperiumGui;
import org.lwjgl.input.Keyboard;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Mouse;
import dev._3000IQPlay.experium.features.setting.Bind;
import dev._3000IQPlay.experium.features.setting.Setting;
import dev._3000IQPlay.experium.features.modules.Module;

public class MCF extends Module
{
    private final Setting<Boolean> middleClick;
    private final Setting<Boolean> keyboard;
    private final Setting<Boolean> server;
    private final Setting<Bind> key;
    private boolean clicked;
    
    public MCF() {
        super("MCF", "Middleclick Friends.", Category.PLAYER, true, false, false);
        this.middleClick = (Setting<Boolean>)this.register(new Setting("MiddleClick", (T)true));
        this.keyboard = (Setting<Boolean>)this.register(new Setting("Keyboard", (T)false));
        this.server = (Setting<Boolean>)this.register(new Setting("Server", (T)true));
        this.key = (Setting<Bind>)this.register(new Setting("KeyBind", (T)new Bind(-1), v -> this.keyboard.getValue()));
        this.clicked = false;
    }
    
    @Override
    public void onUpdate() {
        if (Mouse.isButtonDown(2)) {
            if (!this.clicked && this.middleClick.getValue() && MCF.mc.currentScreen == null) {
                this.onClick();
            }
            this.clicked = true;
        }
        else {
            this.clicked = false;
        }
    }
    
    @SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
    public void onKeyInput(final InputEvent.KeyInputEvent event) {
        if (this.keyboard.getValue() && Keyboard.getEventKeyState() && !(MCF.mc.currentScreen instanceof ExperiumGui) && this.key.getValue().getKey() == Keyboard.getEventKey()) {
            this.onClick();
        }
    }
    
    private void onClick() {
        final RayTraceResult result = MCF.mc.objectMouseOver;
        final Entity entity;
        if (result != null && result.typeOfHit == RayTraceResult.Type.ENTITY && (entity = result.entityHit) instanceof EntityPlayer) {
            if (Experium.friendManager.isFriend(entity.getName())) {
                Experium.friendManager.removeFriend(entity.getName());
                Command.sendMessage("§c" + entity.getName() + "§r unfriended.");
                if (this.server.getValue() && PingBypass.getInstance().isConnected()) {
                    MCF.mc.player.connection.sendPacket((Packet)new CPacketChatMessage("@Serverprefix" + ClickGui.getInstance().prefix.getValue()));
                    MCF.mc.player.connection.sendPacket((Packet)new CPacketChatMessage("@Server" + ClickGui.getInstance().prefix.getValue() + "friend del " + entity.getName()));
                }
            }
            else {
                Experium.friendManager.addFriend(entity.getName());
                Command.sendMessage("§b" + entity.getName() + "§r friended.");
                if (this.server.getValue() && PingBypass.getInstance().isConnected()) {
                    MCF.mc.player.connection.sendPacket((Packet)new CPacketChatMessage("@Serverprefix" + ClickGui.getInstance().prefix.getValue()));
                    MCF.mc.player.connection.sendPacket((Packet)new CPacketChatMessage("@Server" + ClickGui.getInstance().prefix.getValue() + "friend add " + entity.getName()));
                }
            }
        }
        this.clicked = true;
    }
}
