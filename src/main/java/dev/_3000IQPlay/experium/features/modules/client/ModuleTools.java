/*
 * Decompiled with CFR 0.150.
 */
package dev._3000IQPlay.experium.features.modules.client;

import dev._3000IQPlay.experium.features.modules.Module;
import dev._3000IQPlay.experium.features.setting.Setting;

public class ModuleTools
extends Module {
    private static ModuleTools INSTANCE;
    public Setting<Notifier> notifier = this.register(new Setting<Notifier>("ModuleNotifier", Notifier.EXPERIUM));
    public Setting<PopNotifier> popNotifier = this.register(new Setting<PopNotifier>("PopNotifier", PopNotifier.NONE));

    public ModuleTools() {
        super("ModuleTools", "Change settings", Module.Category.CLIENT, true, false, false);
        INSTANCE = this;
    }

    public static ModuleTools getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ModuleTools();
        }
        return INSTANCE;
    }

    public static enum PopNotifier {
        PHOBOS,
        FUTURE,
        DOTGOD,
        NONE;

    }

    public static enum Notifier {
        EXPERIUM,
        FUTURE,
        DOTGOD,
        MUFFIN,
        WEATHER,
        SNOW,
        PYRO,
        CATALYST,
        KONAS,
        RUSHERHACK,
        LEGACY,
        EUROPA;

    }
}

