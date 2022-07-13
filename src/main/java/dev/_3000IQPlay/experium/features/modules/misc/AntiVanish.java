//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Luni\Documents\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev._3000IQPlay.experium.features.modules.misc;

import dev._3000IQPlay.experium.features.command.Command;
import dev._3000IQPlay.experium.util.PlayerUtil;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import java.util.Iterator;
import net.minecraft.network.play.server.SPacketPlayerListItem;
import dev._3000IQPlay.experium.event.events.PacketEvent;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.UUID;
import java.util.Queue;
import dev._3000IQPlay.experium.features.modules.Module;

public class AntiVanish extends Module
{
    private final Queue<UUID> toLookUp;
    
    public AntiVanish() {
        super("AntiVanish", "Notifies you when players vanish", Category.MISC, true, false, false);
        this.toLookUp = new ConcurrentLinkedQueue<UUID>();
    }
    
    @SubscribeEvent
    public void onPacketReceive(final PacketEvent.Receive event) {
        final SPacketPlayerListItem sPacketPlayerListItem;
        if (event.getPacket() instanceof SPacketPlayerListItem && (sPacketPlayerListItem = event.getPacket()).getAction() == SPacketPlayerListItem.Action.UPDATE_LATENCY) {
            for (final SPacketPlayerListItem.AddPlayerData addPlayerData : sPacketPlayerListItem.getEntries()) {
                try {
                    if (AntiVanish.mc.getConnection().getPlayerInfo(addPlayerData.getProfile().getId()) != null) {
                        continue;
                    }
                    this.toLookUp.add(addPlayerData.getProfile().getId());
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    @Override
    public void onUpdate() {
        final UUID lookUp;
        if (PlayerUtil.timer.passedS(5.0) && (lookUp = this.toLookUp.poll()) != null) {
            try {
                final String name = PlayerUtil.getNameFromUUID(lookUp);
                if (name != null) {
                    Command.sendMessage("§c" + name + " has gone into vanish.");
                }
            }
            catch (Exception ex) {}
            PlayerUtil.timer.reset();
        }
    }
    
    @Override
    public void onLogout() {
        this.toLookUp.clear();
    }
}
