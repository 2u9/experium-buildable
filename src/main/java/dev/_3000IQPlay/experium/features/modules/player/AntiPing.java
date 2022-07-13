// 
// Decompiled by Procyon v0.5.36
// 

package dev._3000IQPlay.experium.features.modules.player;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import dev._3000IQPlay.experium.features.command.Command;
import dev._3000IQPlay.experium.Experium;
import dev._3000IQPlay.experium.event.events.ClientEvent;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;
import dev._3000IQPlay.experium.features.setting.Setting;
import dev._3000IQPlay.experium.features.modules.Module;

public class AntiPing extends Module
{
    private static AntiPing instance;
    public final Setting<Boolean> full;
    private final Map<String, Setting> servers;
    public Setting<String> newIP;
    public Setting<Boolean> showServer;
    
    public AntiPing() {
        super("AntiPing", "Prevents DDoS attacks via multiplayer list.", Category.PLAYER, false, false, true);
        this.full = (Setting<Boolean>)this.register(new Setting("Full", (T)false));
        this.servers = new ConcurrentHashMap<String, Setting>();
        this.newIP = (Setting<String>)this.register(new Setting("NewServer", (T)"Add Server...", v -> !this.full.getValue()));
        this.showServer = (Setting<Boolean>)this.register(new Setting("ShowServers", (T)false, v -> !this.full.getValue()));
        AntiPing.instance = this;
    }
    
    public static AntiPing getInstance() {
        if (AntiPing.instance == null) {
            AntiPing.instance = new AntiPing();
        }
        return AntiPing.instance;
    }
    
    @SubscribeEvent
    public void onSettingChange(final ClientEvent event) {
        if (Experium.configManager.loadingConfig || Experium.configManager.savingConfig) {
            return;
        }
        if (event.getStage() == 2 && event.getSetting() != null && event.getSetting().getFeature() != null && event.getSetting().getFeature().equals(this)) {
            if (event.getSetting().equals(this.newIP) && !this.shouldntPing(this.newIP.getPlannedValue()) && !event.getSetting().getPlannedValue().equals(event.getSetting().getDefaultValue())) {
                final Setting setting = this.register(new Setting(this.newIP.getPlannedValue(), (T)true, v -> this.showServer.getValue() && !this.full.getValue()));
                this.registerServer(setting);
                Command.sendMessage("<AntiPing> Added new Server: " + this.newIP.getPlannedValue());
                event.setCanceled(true);
            }
            else {
                final Setting setting = event.getSetting();
                if (setting.equals(this.enabled) || setting.equals(this.drawn) || setting.equals(this.bind) || setting.equals(this.newIP) || setting.equals(this.showServer) || setting.equals(this.full)) {
                    return;
                }
                if (setting.getValue() instanceof Boolean && !setting.getPlannedValue()) {
                    this.servers.remove(setting.getName().toLowerCase());
                    this.unregister(setting);
                    event.setCanceled(true);
                }
            }
        }
    }
    
    public void registerServer(final Setting setting) {
        this.servers.put(setting.getName().toLowerCase(), setting);
    }
    
    public boolean shouldntPing(final String ip) {
        return !this.isOff() && (this.full.getValue() || this.servers.get(ip.toLowerCase()) != null);
    }
}
