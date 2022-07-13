//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Luni\Documents\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.util.math.BlockPos
 */
package dev._3000IQPlay.experium.manager;

import dev._3000IQPlay.experium.features.Feature;
import dev._3000IQPlay.experium.features.modules.client.Managers;
import dev._3000IQPlay.experium.util.BlockUtil;
import dev._3000IQPlay.experium.util.Timer;
import net.minecraft.util.math.BlockPos;

public class NoStopManager
extends Feature {
    private final Timer timer = new Timer();
    private String prefix;
    private boolean running;
    private boolean sentMessage;
    private BlockPos pos;
    private BlockPos lastPos;
    private boolean stopped;

    public void onUpdateWalkingPlayer() {
        if (NoStopManager.fullNullCheck()) {
            this.stop();
            return;
        }
        if (this.running && this.pos != null) {
            BlockPos currentPos = NoStopManager.mc.player.getPosition();
            if (currentPos.equals((Object)this.pos)) {
                BlockUtil.debugPos("<Baritone> Arrived at Position: ", this.pos);
                this.running = false;
                return;
            }
            if (currentPos.equals((Object)this.lastPos)) {
                if (this.stopped && this.timer.passedS(Managers.getInstance().baritoneTimeOut.getValue().intValue())) {
                    this.sendMessage();
                    this.stopped = false;
                    return;
                }
                if (!this.stopped) {
                    this.stopped = true;
                    this.timer.reset();
                }
            } else {
                this.lastPos = currentPos;
                this.stopped = false;
            }
            if (!this.sentMessage) {
                this.sendMessage();
                this.sentMessage = true;
            }
        }
    }

    public void sendMessage() {
        NoStopManager.mc.player.sendChatMessage(this.prefix + "goto " + this.pos.getX() + " " + this.pos.getY() + " " + this.pos.getZ());
    }

    public void start(int x, int y, int z) {
        this.pos = new BlockPos(x, y, z);
        this.sentMessage = false;
        this.running = true;
    }

    public void stop() {
        if (this.running) {
            if (NoStopManager.mc.player != null) {
                NoStopManager.mc.player.sendChatMessage(this.prefix + "stop");
            }
            this.running = false;
        }
    }

    public void setPrefix(String prefixIn) {
        this.prefix = prefixIn;
    }
}

