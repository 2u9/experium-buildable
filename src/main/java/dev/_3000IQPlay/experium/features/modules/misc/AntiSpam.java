//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Luni\Documents\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev._3000IQPlay.experium.features.modules.misc;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import dev._3000IQPlay.experium.features.setting.Setting;
import dev._3000IQPlay.experium.features.modules.Module;

public class AntiSpam extends Module
{
    public Setting<Boolean> Badwords;
    public Setting<Boolean> Links;
    public Setting<Boolean> Shop;
    public Setting<Boolean> Kit;
    public Setting<Boolean> Nig;
    public Setting<Boolean> Discord;
    public Setting<Boolean> YouTube;
    
    public AntiSpam() {
        super("AntiSpam", "Prevents you from seeing 8yo kids in chat", Category.MISC, true, false, false);
        this.Badwords = (Setting<Boolean>)this.register(new Setting("BadWords", (T)true));
        this.Links = (Setting<Boolean>)this.register(new Setting("Links", (T)true));
        this.Shop = (Setting<Boolean>)this.register(new Setting("Shop", (T)true));
        this.Kit = (Setting<Boolean>)this.register(new Setting("Kit", (T)true));
        this.Nig = (Setting<Boolean>)this.register(new Setting("Racism", (T)true));
        this.Discord = (Setting<Boolean>)this.register(new Setting("Discord", (T)true));
        this.YouTube = (Setting<Boolean>)this.register(new Setting("YouTube", (T)true));
    }
    
    @SubscribeEvent
    public void onClientChatReceive(final ClientChatReceivedEvent e) {
        if (this.Links.getValue() && e.getMessage().getUnformattedText().contains("http")) {
            e.setCanceled(true);
        }
        if ((this.Shop.getValue() && e.getMessage().getUnformattedText().contains("Shop")) || e.getMessage().getUnformattedText().contains("shop") || e.getMessage().getUnformattedText().contains("Sh0p") || e.getMessage().getUnformattedText().contains("sh0p") || e.getMessage().getUnformattedText().contains("Buy") || e.getMessage().getUnformattedText().contains("buy") || e.getMessage().getUnformattedText().contains("sell") || e.getMessage().getUnformattedText().contains("Sell") || e.getMessage().getUnformattedText().contains("\u0448\u043e\u043f") || e.getMessage().getUnformattedText().contains("\u0428\u043e\u043f") || e.getMessage().getUnformattedText().contains("\u043c\u0430\u0433") || e.getMessage().getUnformattedText().contains("\u0444\u0435\u0434") || e.getMessage().getUnformattedText().contains("\u0431\u0435\u0441")) {
            e.setCanceled(true);
        }
        if ((this.Kit.getValue() && e.getMessage().getUnformattedText().contains("Kit")) || e.getMessage().getUnformattedText().contains("kits") || e.getMessage().getUnformattedText().contains("\u043a\u0438\u0442") || e.getMessage().getUnformattedText().contains("\u041a\u0438\u0442") || e.getMessage().getUnformattedText().contains("\u041a1\u0442") || e.getMessage().getUnformattedText().contains("\u043a1\u0442")) {
            e.setCanceled(true);
        }
        if ((this.Nig.getValue() && e.getMessage().getUnformattedText().contains("Nig")) || e.getMessage().getUnformattedText().contains("nig") || e.getMessage().getUnformattedText().contains("n1g") || e.getMessage().getUnformattedText().contains("Nig") || e.getMessage().getUnformattedText().contains("N1g") || e.getMessage().getUnformattedText().contains("\u041d\u0435\u0433") || e.getMessage().getUnformattedText().contains("\u043d\u0435\u0433")) {
            e.setCanceled(true);
        }
        if ((this.Discord.getValue() && e.getMessage().getUnformattedText().contains("disc")) || e.getMessage().getUnformattedText().contains("Disc") || e.getMessage().getUnformattedText().contains("#") || e.getMessage().getUnformattedText().contains("\u0434\u0438\u0441") || e.getMessage().getUnformattedText().contains("\u0414\u0438\u0441")) {
            e.setCanceled(true);
        }
        if ((this.Badwords.getValue() && e.getMessage().getUnformattedText().contains("fag")) || e.getMessage().getUnformattedText().contains("ped") || e.getMessage().getUnformattedText().contains("dum") || e.getMessage().getUnformattedText().contains("ass") || e.getMessage().getUnformattedText().contains("fat") || e.getMessage().getUnformattedText().contains("bitch") || e.getMessage().getUnformattedText().contains("Bitch") || e.getMessage().getUnformattedText().contains("fuc") || e.getMessage().getUnformattedText().contains("dick") || e.getMessage().getUnformattedText().contains("Ped") || e.getMessage().getUnformattedText().contains("Dum") || e.getMessage().getUnformattedText().contains("Ass") || e.getMessage().getUnformattedText().contains("Fat") || e.getMessage().getUnformattedText().contains("Fuc") || e.getMessage().getUnformattedText().contains("Dick") || e.getMessage().getUnformattedText().contains("\u0445\u0443") || e.getMessage().getUnformattedText().contains("\u043f\u0438\u0437") || e.getMessage().getUnformattedText().contains("\u0431\u043b") || e.getMessage().getUnformattedText().contains("\u043c\u043e\u0447\u0430") || e.getMessage().getUnformattedText().contains("\u0448\u043b\u044e") || e.getMessage().getUnformattedText().contains("\u0445\u0443") || e.getMessage().getUnformattedText().contains("\u0435\u0431") || e.getMessage().getUnformattedText().contains("\u0451\u0431") || e.getMessage().getUnformattedText().contains("\u0432\u043f\u0438\u0437") || e.getMessage().getUnformattedText().contains("\u0436\u043e\u043f") || e.getMessage().getUnformattedText().contains("\u0432\u044b\u0431") || e.getMessage().getUnformattedText().contains("\u0434\u0435\u0440\u044c\u043c\u043e") || e.getMessage().getUnformattedText().contains("\u0441\u044b\u043d") || e.getMessage().getUnformattedText().contains("\u043c\u0430\u043c") || e.getMessage().getUnformattedText().contains("\u0434\u043e\u0435") || e.getMessage().getUnformattedText().contains("\u0436\u0438\u0440") || e.getMessage().getUnformattedText().contains("\u0436\u0438\u0434") || e.getMessage().getUnformattedText().contains("\u0437\u0430\u0435") || e.getMessage().getUnformattedText().contains("\u043d\u0430\u0435") || e.getMessage().getUnformattedText().contains("\u0443\u0435\u0431") || e.getMessage().getUnformattedText().contains("\u0443\u0451\u0431") || e.getMessage().getUnformattedText().contains("\u0425\u0443") || e.getMessage().getUnformattedText().contains("\u041f\u0438\u0437") || e.getMessage().getUnformattedText().contains("\u0411\u043b") || e.getMessage().getUnformattedText().contains("\u041c\u043e\u0447\u0430") || e.getMessage().getUnformattedText().contains("\u0428\u043b\u044e") || e.getMessage().getUnformattedText().contains("\u0425\u0443") || e.getMessage().getUnformattedText().contains("\u0415\u0431") || e.getMessage().getUnformattedText().contains("\u0401\u0431") || e.getMessage().getUnformattedText().contains("\u0412\u043f\u0438\u0437") || e.getMessage().getUnformattedText().contains("\u0416\u043e\u043f") || e.getMessage().getUnformattedText().contains("\u0412\u044b\u0431") || e.getMessage().getUnformattedText().contains("\u0414\u0435\u0440\u044c\u043c\u043e") || e.getMessage().getUnformattedText().contains("\u0421\u044b\u043d") || e.getMessage().getUnformattedText().contains("\u041c\u0430\u043c") || e.getMessage().getUnformattedText().contains("\u0414\u043e\u0435") || e.getMessage().getUnformattedText().contains("\u0416\u0438\u0440") || e.getMessage().getUnformattedText().contains("\u0416\u0438\u0434") || e.getMessage().getUnformattedText().contains("\u0417\u0430\u0435") || e.getMessage().getUnformattedText().contains("\u041d\u0430\u0435") || e.getMessage().getUnformattedText().contains("\u0423\u0435\u0431") || e.getMessage().getUnformattedText().contains("\u0423\u0451\u0431") || e.getMessage().getUnformattedText().contains("\u0410\u043d\u0430") || e.getMessage().getUnformattedText().contains("\u0430\u043d\u0430") || e.getMessage().getUnformattedText().contains("\u041e\u0447\u043a") || e.getMessage().getUnformattedText().contains("\u043e\u0447\u043a") || e.getMessage().getUnformattedText().contains("\u0421\u0443") || e.getMessage().getUnformattedText().contains("\u0441\u0443") || e.getMessage().getUnformattedText().contains("\u0445\u0443")) {
            e.setCanceled(true);
        }
        if ((this.YouTube.getValue() && e.getMessage().getUnformattedText().contains("yt")) || e.getMessage().getUnformattedText().contains("tub") || e.getMessage().getUnformattedText().contains("ash") || e.getMessage().getUnformattedText().contains("\u0437\u0430\u0445\u043e\u0434\u0438") || e.getMessage().getUnformattedText().contains("join") || e.getMessage().getUnformattedText().contains("ash") || e.getMessage().getUnformattedText().contains("video") || e.getMessage().getUnformattedText().contains("\u044e\u0442") || e.getMessage().getUnformattedText().contains("\u043a\u0430\u043d\u0430\u043b") || e.getMessage().getUnformattedText().contains("chan")) {
            e.setCanceled(true);
        }
    }
}
