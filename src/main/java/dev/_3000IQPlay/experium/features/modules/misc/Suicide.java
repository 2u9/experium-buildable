//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Luni\Documents\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 */
package dev._3000IQPlay.experium.features.modules.misc;

import dev._3000IQPlay.experium.features.modules.Module;

public class Suicide
extends Module {
    public Suicide() {
        super("Suicide", "Auto suicide.", Module.Category.MISC, true, false, false);
    }

    @Override
    public void onEnable() {
        Suicide.mc.player.sendChatMessage("/kill");
        this.toggle();
    }
}

