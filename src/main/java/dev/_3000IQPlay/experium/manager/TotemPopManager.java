//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Luni\Documents\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.mojang.realmsclient.gui.ChatFormatting
 *  net.minecraft.entity.player.EntityPlayer
 */
package dev._3000IQPlay.experium.manager;

import com.mojang.realmsclient.gui.ChatFormatting;
import dev._3000IQPlay.experium.Experium;
import dev._3000IQPlay.experium.features.Feature;
import dev._3000IQPlay.experium.features.command.Command;
import dev._3000IQPlay.experium.features.modules.client.ModuleTools;
import dev._3000IQPlay.experium.features.modules.client.Notifications;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.entity.player.EntityPlayer;

public class TotemPopManager
extends Feature {
    private final Set<EntityPlayer> toAnnounce = new HashSet<EntityPlayer>();
    private Notifications notifications;
    private Map<EntityPlayer, Integer> poplist = new ConcurrentHashMap<EntityPlayer, Integer>();

    public void onUpdate() {
        if (this.notifications.totemAnnounce.passedMs(this.notifications.delay.getValue().intValue()) && this.notifications.isOn() && this.notifications.totemPops.getValue().booleanValue()) {
            for (EntityPlayer player : this.toAnnounce) {
                if (player == null) continue;
                int playerNumber = 0;
                for (char character : player.getName().toCharArray()) {
                    playerNumber += character;
                    playerNumber *= 10;
                }
                Command.sendOverwriteMessage(this.pop(player), playerNumber, this.notifications.totemNoti.getValue());
                this.toAnnounce.remove((Object)player);
                this.notifications.totemAnnounce.reset();
                break;
            }
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public String pop(EntityPlayer player) {
        if (this.getTotemPops(player) == 1) {
            if (!ModuleTools.getInstance().isEnabled()) return Experium.commandManager.getClientMessage() + " " + (Object)ChatFormatting.WHITE + player.getName() + " popped " + (Object)ChatFormatting.GREEN + this.getTotemPops(player) + (Object)ChatFormatting.WHITE + " Totem.";
            switch (ModuleTools.getInstance().popNotifier.getValue()) {
                case FUTURE: {
                    return (Object)ChatFormatting.RED + "[Future] " + (Object)ChatFormatting.GREEN + player.getName() + (Object)ChatFormatting.GRAY + " just popped " + (Object)ChatFormatting.GREEN + this.getTotemPops(player) + (Object)ChatFormatting.GRAY + " totem.";
                }
                case PHOBOS: {
                    return (Object)ChatFormatting.GOLD + player.getName() + (Object)ChatFormatting.RED + " popped " + (Object)ChatFormatting.GOLD + this.getTotemPops(player) + (Object)ChatFormatting.RED + " totem.";
                }
                case DOTGOD: {
                    return (Object)ChatFormatting.DARK_PURPLE + "[" + (Object)ChatFormatting.LIGHT_PURPLE + "DotGod.CC" + (Object)ChatFormatting.DARK_PURPLE + "] " + (Object)ChatFormatting.LIGHT_PURPLE + player.getName() + " has popped " + (Object)ChatFormatting.RED + this.getTotemPops(player) + (Object)ChatFormatting.LIGHT_PURPLE + " time in total!";
                }
                case NONE: {
                    return Experium.commandManager.getClientMessage() + " " + (Object)ChatFormatting.WHITE + player.getName() + " popped " + (Object)ChatFormatting.GREEN + this.getTotemPops(player) + (Object)ChatFormatting.WHITE + " Totem.";
                }
            }
            return "";
        } else {
            if (!ModuleTools.getInstance().isEnabled()) return Experium.commandManager.getClientMessage() + " " + (Object)ChatFormatting.WHITE + player.getName() + " popped " + (Object)ChatFormatting.GREEN + this.getTotemPops(player) + (Object)ChatFormatting.WHITE + " Totems.";
            switch (ModuleTools.getInstance().popNotifier.getValue()) {
                case FUTURE: {
                    return (Object)ChatFormatting.RED + "[Future] " + (Object)ChatFormatting.GREEN + player.getName() + (Object)ChatFormatting.GRAY + " just popped " + (Object)ChatFormatting.GREEN + this.getTotemPops(player) + (Object)ChatFormatting.GRAY + " totems.";
                }
                case PHOBOS: {
                    return (Object)ChatFormatting.GOLD + player.getName() + (Object)ChatFormatting.RED + " popped " + (Object)ChatFormatting.GOLD + this.getTotemPops(player) + (Object)ChatFormatting.RED + " totems.";
                }
                case DOTGOD: {
                    return (Object)ChatFormatting.DARK_PURPLE + "[" + (Object)ChatFormatting.LIGHT_PURPLE + "DotGod.CC" + (Object)ChatFormatting.DARK_PURPLE + "] " + (Object)ChatFormatting.LIGHT_PURPLE + player.getName() + " has popped " + (Object)ChatFormatting.RED + this.getTotemPops(player) + (Object)ChatFormatting.LIGHT_PURPLE + " times in total!";
                }
                case NONE: {
                    return Experium.commandManager.getClientMessage() + " " + (Object)ChatFormatting.WHITE + player.getName() + " popped " + (Object)ChatFormatting.GREEN + this.getTotemPops(player) + (Object)ChatFormatting.WHITE + " Totems.";
                }
            }
        }
        return "";
    }

    public void onLogout() {
        this.onOwnLogout(this.notifications.clearOnLogout.getValue());
    }

    public void init() {
        this.notifications = Experium.moduleManager.getModuleByClass(Notifications.class);
    }

    public void onTotemPop(EntityPlayer player) {
        this.popTotem(player);
        if (!player.equals((Object)TotemPopManager.mc.player)) {
            this.toAnnounce.add(player);
            this.notifications.totemAnnounce.reset();
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public String death1(EntityPlayer player) {
        if (this.getTotemPops(player) == 1) {
            if (!ModuleTools.getInstance().isEnabled()) return Experium.commandManager.getClientMessage() + (Object)ChatFormatting.WHITE + player.getName() + " died after popping " + (Object)ChatFormatting.GREEN + this.getTotemPops(player) + (Object)ChatFormatting.WHITE + " Totem!";
            switch (ModuleTools.getInstance().popNotifier.getValue()) {
                case FUTURE: {
                    return (Object)ChatFormatting.RED + "[Future] " + (Object)ChatFormatting.GREEN + player.getName() + (Object)ChatFormatting.GRAY + " died after popping " + (Object)ChatFormatting.GREEN + this.getTotemPops(player) + (Object)ChatFormatting.GRAY + " totem.";
                }
                case PHOBOS: {
                    return (Object)ChatFormatting.GOLD + player.getName() + (Object)ChatFormatting.RED + " died after popping " + (Object)ChatFormatting.GOLD + this.getTotemPops(player) + (Object)ChatFormatting.RED + " totem.";
                }
                case DOTGOD: {
                    return (Object)ChatFormatting.DARK_PURPLE + "[" + (Object)ChatFormatting.LIGHT_PURPLE + "DotGod.CC" + (Object)ChatFormatting.DARK_PURPLE + "] " + (Object)ChatFormatting.LIGHT_PURPLE + player.getName() + " died after popping " + (Object)ChatFormatting.GREEN + this.getTotemPops(player) + (Object)ChatFormatting.LIGHT_PURPLE + " time!";
                }
                case NONE: {
                    return Experium.commandManager.getClientMessage() + " " + (Object)ChatFormatting.WHITE + player.getName() + " died after popping " + (Object)ChatFormatting.GREEN + this.getTotemPops(player) + (Object)ChatFormatting.WHITE + " Totem!";
                }
            }
            return null;
        } else {
            if (!ModuleTools.getInstance().isEnabled()) return Experium.commandManager.getClientMessage() + " " + (Object)ChatFormatting.WHITE + player.getName() + " died after popping " + (Object)ChatFormatting.GREEN + this.getTotemPops(player) + (Object)ChatFormatting.WHITE + " Totems!";
            switch (ModuleTools.getInstance().popNotifier.getValue()) {
                case FUTURE: {
                    return (Object)ChatFormatting.RED + "[Future] " + (Object)ChatFormatting.GREEN + player.getName() + (Object)ChatFormatting.GRAY + " died after popping " + (Object)ChatFormatting.GREEN + this.getTotemPops(player) + (Object)ChatFormatting.GRAY + " totems.";
                }
                case PHOBOS: {
                    return (Object)ChatFormatting.GOLD + player.getName() + (Object)ChatFormatting.RED + " died after popping " + (Object)ChatFormatting.GOLD + this.getTotemPops(player) + (Object)ChatFormatting.RED + " totems.";
                }
                case DOTGOD: {
                    return (Object)ChatFormatting.DARK_PURPLE + "[" + (Object)ChatFormatting.LIGHT_PURPLE + "DotGod.CC" + (Object)ChatFormatting.DARK_PURPLE + "] " + (Object)ChatFormatting.LIGHT_PURPLE + player.getName() + " died after popping " + (Object)ChatFormatting.GREEN + this.getTotemPops(player) + (Object)ChatFormatting.LIGHT_PURPLE + " times!";
                }
                case NONE: {
                    return Experium.commandManager.getClientMessage() + " " + (Object)ChatFormatting.WHITE + player.getName() + " died after popping " + (Object)ChatFormatting.GREEN + this.getTotemPops(player) + (Object)ChatFormatting.WHITE + " Totems!";
                }
            }
        }
        return null;
    }

    public void onDeath(EntityPlayer player) {
        if (this.getTotemPops(player) != 0 && !player.equals((Object)TotemPopManager.mc.player) && this.notifications.isOn() && this.notifications.totemPops.getValue().booleanValue()) {
            int playerNumber = 0;
            for (char character : player.getName().toCharArray()) {
                playerNumber += character;
                playerNumber *= 10;
            }
            Command.sendOverwriteMessage(this.death1(player), playerNumber, this.notifications.totemNoti.getValue());
            this.toAnnounce.remove((Object)player);
        }
        this.resetPops(player);
    }

    public void onLogout(EntityPlayer player, boolean clearOnLogout) {
        if (clearOnLogout) {
            this.resetPops(player);
        }
    }

    public void onOwnLogout(boolean clearOnLogout) {
        if (clearOnLogout) {
            this.clearList();
        }
    }

    public void clearList() {
        this.poplist = new ConcurrentHashMap<EntityPlayer, Integer>();
    }

    public void resetPops(EntityPlayer player) {
        this.setTotemPops(player, 0);
    }

    public void popTotem(EntityPlayer player) {
        this.poplist.merge(player, 1, Integer::sum);
    }

    public void setTotemPops(EntityPlayer player, int amount) {
        this.poplist.put(player, amount);
    }

    public int getTotemPops(EntityPlayer player) {
        Integer pops = this.poplist.get((Object)player);
        if (pops == null) {
            return 0;
        }
        return pops;
    }

    public String getTotemPopString(EntityPlayer player) {
        return "\u00a7f" + (this.getTotemPops(player) <= 0 ? "" : "-" + this.getTotemPops(player) + " ");
    }
}

