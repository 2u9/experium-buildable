//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Luni\Documents\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev._3000IQPlay.experium.features.modules.misc;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.math.Vec3i;
import net.minecraft.entity.player.EntityPlayer;
import dev._3000IQPlay.experium.features.modules.client.Managers;
import java.util.Date;
import java.text.SimpleDateFormat;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.network.play.server.SPacketChat;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.network.play.client.CPacketChatMessage;
import dev._3000IQPlay.experium.event.events.PacketEvent;
import dev._3000IQPlay.experium.util.TextUtil;
import dev._3000IQPlay.experium.features.setting.Setting;
import dev._3000IQPlay.experium.util.Timer;
import dev._3000IQPlay.experium.features.modules.Module;

public class ChatModifier extends Module
{
    private static ChatModifier INSTANCE;
    private final Timer timer;
    public Setting<Suffix> suffix;
    public Setting<TextColor> textcolor;
    public Setting<StiTextColor> stiTextColor;
    public Setting<Boolean> clean;
    public Setting<Boolean> infinite;
    public Setting<TextUtil.Color> timeStamps;
    public Setting<Boolean> rainbowTimeStamps;
    public Setting<TextUtil.Color> bracket;
    public Setting<Boolean> space;
    public Setting<Boolean> all;
    
    public ChatModifier() {
        super("ChatModifier", "Modifies your chat", Category.MISC, true, false, false);
        this.timer = new Timer();
        this.suffix = (Setting<Suffix>)this.register(new Setting("Suffix", (T)Suffix.NONE, "Your Suffix."));
        this.textcolor = (Setting<TextColor>)this.register(new Setting("TextColor", (T)TextColor.NONE, "Your text color."));
        this.stiTextColor = (Setting<StiTextColor>)this.register(new Setting("StiTextColor", (T)StiTextColor.NONE, "Your sti text color."));
        this.clean = (Setting<Boolean>)this.register(new Setting("CleanChat", (T)false, "Cleans your chat"));
        this.infinite = (Setting<Boolean>)this.register(new Setting("Infinite", (T)false, "Makes your chat infinite."));
        this.timeStamps = (Setting<TextUtil.Color>)this.register(new Setting("Time", (T)TextUtil.Color.NONE));
        this.rainbowTimeStamps = (Setting<Boolean>)this.register(new Setting("RainbowTimeStamps", (T)false, v -> this.timeStamps.getValue() != TextUtil.Color.NONE));
        this.bracket = (Setting<TextUtil.Color>)this.register(new Setting("Bracket", (T)TextUtil.Color.WHITE, v -> this.timeStamps.getValue() != TextUtil.Color.NONE));
        this.space = (Setting<Boolean>)this.register(new Setting("Space", (T)true, v -> this.timeStamps.getValue() != TextUtil.Color.NONE));
        this.all = (Setting<Boolean>)this.register(new Setting("All", (T)false, v -> this.timeStamps.getValue() != TextUtil.Color.NONE));
        this.setInstance();
    }
    
    public static ChatModifier getInstance() {
        if (ChatModifier.INSTANCE == null) {
            ChatModifier.INSTANCE = new ChatModifier();
        }
        return ChatModifier.INSTANCE;
    }
    
    private void setInstance() {
        ChatModifier.INSTANCE = this;
    }
    
    @SubscribeEvent
    public void onPacketSend(final PacketEvent.Send event) {
        if (event.getStage() == 0 && event.getPacket() instanceof CPacketChatMessage) {
            final CPacketChatMessage packet = event.getPacket();
            String s = packet.getMessage();
            if (s.startsWith("/") || s.startsWith("!")) {
                return;
            }
            switch (this.suffix.getValue()) {
                case EXPERIUM: {
                    s += " | Experium";
                    break;
                }
            }
            switch (this.stiTextColor.getValue()) {
                case RANDOM: {
                    final int minNum = 1;
                    final int maxNum = 16;
                    final int randomNumber = (int)Math.floor(Math.random() * (maxNum - minNum + 1) + minNum);
                    if (randomNumber == 1) {
                        s = "!2" + s;
                    }
                    if (randomNumber == 2) {
                        s = "!3" + s;
                    }
                    if (randomNumber == 3) {
                        s = "!4" + s;
                    }
                    if (randomNumber == 4) {
                        s = "!5" + s;
                    }
                    if (randomNumber == 5) {
                        s = "!7" + s;
                    }
                    if (randomNumber == 6) {
                        s = "!8" + s;
                    }
                    if (randomNumber == 7) {
                        s = "!9" + s;
                    }
                    if (randomNumber == 8) {
                        s = "!g" + s;
                    }
                    if (randomNumber == 9) {
                        s = "!c" + s;
                    }
                    if (randomNumber == 10) {
                        s = "!d" + s;
                    }
                    if (randomNumber == 11) {
                        s = "!a" + s;
                    }
                    if (randomNumber == 12) {
                        s = "!e" + s;
                    }
                    if (randomNumber == 13) {
                        s = "!b" + s;
                    }
                    if (randomNumber == 14) {
                        s = "!f" + s;
                    }
                    if (randomNumber == 15) {
                        s = "!1" + s;
                    }
                    if (randomNumber == 16) {
                        s = "!6" + s;
                        break;
                    }
                    break;
                }
                case LIGHT_GREEN: {
                    s = "!2" + s;
                    break;
                }
                case LIGHT_BLUE: {
                    s = "!3" + s;
                    break;
                }
                case LIGHT_RED: {
                    s = "!4" + s;
                }
                case LIGHT_AQUA: {
                    s = "!5" + s;
                    break;
                }
                case YELLOW: {
                    s = "!7" + s;
                    break;
                }
                case LIGHT_GRAY: {
                    s = "!8" + s;
                    break;
                }
                case LIGHT_PURPLE: {
                    s = "!g" + s;
                    break;
                }
                case GRAY: {
                    s = "!c" + s;
                    break;
                }
                case BLACK: {
                    s = "!9" + s;
                    break;
                }
                case BLUE: {
                    s = "!d" + s;
                    break;
                }
                case GREEN: {
                    s = "!a" + s;
                    break;
                }
                case AQUA: {
                    s = "!e" + s;
                    break;
                }
                case RED: {
                    s = "!b" + s;
                    break;
                }
                case PURPLE: {
                    s = "!f" + s;
                    break;
                }
                case GOLD: {
                    s = "!6" + s;
                    break;
                }
                case WHITE: {
                    s = "!1" + s;
                    break;
                }
                case ITALIC: {
                    s = "!h" + s;
                    break;
                }
                case STRIKE: {
                    s = "!k" + s;
                    break;
                }
                case UNDERLINE: {
                    s = "!i" + s;
                    break;
                }
                case BOLD: {
                    s = "!j" + s;
                    break;
                }
            }
            switch (this.textcolor.getValue()) {
                case GREEN: {
                    s = "> " + s;
                    break;
                }
                case YELLOW: {
                    s = "# " + s;
                    break;
                }
                case GOLD: {
                    s = "$ " + s;
                }
                case BLUE: {
                    s = "! " + s;
                    break;
                }
                case AQUA: {
                    s = "`` " + s;
                    break;
                }
                case PURPLE: {
                    s = "? " + s;
                    break;
                }
                case RED: {
                    s = "& " + s;
                    break;
                }
                case DARKRED: {
                    s = "@ " + s;
                    break;
                }
                case GRAY: {
                    s = ". " + s;
                    break;
                }
            }
            if (s.length() >= 256) {
                s = s.substring(0, 256);
            }
            packet.message = s;
        }
    }
    
    @SubscribeEvent
    public void onChatPacketReceive(final PacketEvent.Receive event) {
        if (event.getStage() != 0 || event.getPacket() instanceof SPacketChat) {}
    }
    
    @SubscribeEvent
    public void onPacketReceive(final PacketEvent.Receive event) {
        if (event.getStage() == 0 && this.timeStamps.getValue() != TextUtil.Color.NONE && event.getPacket() instanceof SPacketChat) {
            if (!event.getPacket().isSystem()) {
                return;
            }
            final String originalMessage = event.getPacket().chatComponent.getFormattedText();
            final String message = this.getTimeString(originalMessage) + originalMessage;
            event.getPacket().chatComponent = (ITextComponent)new TextComponentString(message);
        }
    }
    
    public String getTimeString(final String message) {
        final String date = new SimpleDateFormat("k:mm").format(new Date());
        if (this.rainbowTimeStamps.getValue()) {
            final String timeString = "[" + date + "]" + (this.space.getValue() ? " " : "");
            final StringBuilder builder = new StringBuilder(timeString);
            builder.insert(0, "§+");
            if (!message.contains(Managers.getInstance().getRainbowCommandMessage())) {
                builder.append("§r");
            }
            return builder.toString();
        }
        return ((this.bracket.getValue() == TextUtil.Color.NONE) ? "" : TextUtil.coloredString("<", this.bracket.getValue())) + TextUtil.coloredString(date, this.timeStamps.getValue()) + ((this.bracket.getValue() == TextUtil.Color.NONE) ? "" : TextUtil.coloredString(">", this.bracket.getValue())) + (this.space.getValue() ? " " : "") + "§r";
    }
    
    private boolean shouldSendMessage(final EntityPlayer player) {
        return player.dimension == 1 && player.getPosition().equals((Object)new Vec3i(0, 240, 0));
    }
    
    static {
        ChatModifier.INSTANCE = new ChatModifier();
    }
    
    public enum Suffix
    {
        NONE, 
        EXPERIUM;
    }
    
    public enum TextColor
    {
        NONE, 
        GREEN, 
        YELLOW, 
        GOLD, 
        BLUE, 
        AQUA, 
        PURPLE, 
        RED, 
        DARKRED, 
        GRAY;
    }
    
    public enum StiTextColor
    {
        NONE, 
        RANDOM, 
        GREEN, 
        BLUE, 
        AQUA, 
        YELLOW, 
        GOLD, 
        RED, 
        PURPLE, 
        LIGHT_BLUE, 
        LIGHT_GREEN, 
        LIGHT_AQUA, 
        LIGHT_RED, 
        LIGHT_PURPLE, 
        LIGHT_GRAY, 
        GRAY, 
        BLACK, 
        WHITE, 
        BOLD, 
        STRIKE, 
        UNDERLINE, 
        ITALIC;
    }
}
