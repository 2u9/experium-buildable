//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Luni\Documents\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev._3000IQPlay.experium.util;

import net.minecraft.util.math.MathHelper;

public class Timer
{
    private long time;
    private long current;
    private long lastMS;
    
    public Timer() {
        this.time = -1L;
        this.lastMS = 0L;
    }
    
    public boolean passedS(final double s) {
        return this.passedMs((long)s * 1000L);
    }
    
    public boolean passedDms(final double dms) {
        return this.passedMs((long)dms * 10L);
    }
    
    public boolean passedDs(final double ds) {
        return this.passedMs((long)ds * 100L);
    }
    
    public boolean passedMs(final long ms) {
        return this.passedNS(this.convertToNS(ms));
    }
    
    public void setMs(final long ms) {
        this.time = System.nanoTime() - this.convertToNS(ms);
    }
    
    public boolean passedNS(final long ns) {
        return System.nanoTime() - this.time >= ns;
    }
    
    public long getPassedTimeMs() {
        return this.getMs(System.nanoTime() - this.time);
    }
    
    public Timer reset() {
        this.time = System.nanoTime();
        return this;
    }
    
    public long getCurrentMS() {
        return System.nanoTime() / 1000000L;
    }
    
    public boolean delay(final double var1) {
        return MathHelper.clamp((float)(this.getCurrentMS() - this.lastMS), 0.0f, (float)var1) >= var1;
    }
    
    public boolean hasReached(final long passedTime) {
        return System.currentTimeMillis() - this.current >= passedTime;
    }
    
    public boolean passed(final double ms) {
        return System.currentTimeMillis() - this.current >= ms;
    }
    
    public long getMs(final long time) {
        return time / 1000000L;
    }
    
    public long convertToNS(final long time) {
        return time * 1000000L;
    }
    
    public boolean passed(final long delay) {
        return System.currentTimeMillis() - this.current >= delay;
    }
}
