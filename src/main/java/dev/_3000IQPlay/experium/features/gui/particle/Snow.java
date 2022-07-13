//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Luni\Documents\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev._3000IQPlay.experium.features.gui.particle;

import java.util.Random;
import dev._3000IQPlay.experium.util.RenderUtil;
import net.minecraft.client.gui.ScaledResolution;

public class Snow
{
    private int _x;
    private int _y;
    private int _fallingSpeed;
    private int _size;
    
    public Snow(final int x, final int y, final int fallingSpeed, final int size) {
        this._x = x;
        this._y = y;
        this._fallingSpeed = fallingSpeed;
        this._size = size;
    }
    
    public int getX() {
        return this._x;
    }
    
    public void setX(final int x) {
        this._x = x;
    }
    
    public int getY() {
        return this._y;
    }
    
    public void setY(final int _y) {
        this._y = _y;
    }
    
    public void Update(final ScaledResolution res) {
        RenderUtil.drawRect((float)this.getX(), (float)this.getY(), (float)(this.getX() + this._size), (float)(this.getY() + this._size), -1714829883);
        this.setY(this.getY() + this._fallingSpeed);
        if (this.getY() > res.getScaledHeight() + 10 || this.getY() < -10) {
            this.setY(-10);
            final Random rand = new Random();
            this._fallingSpeed = rand.nextInt(10) + 1;
            this._size = rand.nextInt(4) + 1;
        }
    }
}
