//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Luni\Documents\1.12 stable mappings"!

/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.Minecraft
 *  net.minecraft.util.Util
 *  net.minecraft.util.Util$EnumOS
 *  net.minecraftforge.fml.common.Mod
 *  net.minecraftforge.fml.common.Mod$EventHandler
 *  net.minecraftforge.fml.common.Mod$Instance
 *  net.minecraftforge.fml.common.event.FMLInitializationEvent
 *  net.minecraftforge.fml.common.event.FMLPreInitializationEvent
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 *  org.lwjgl.opengl.Display
 */
package dev._3000IQPlay.experium;

import dev._3000IQPlay.experium.DiscordPresence;
import dev._3000IQPlay.experium.features.modules.client.RPC;
import dev._3000IQPlay.experium.manager.ColorManager;
import dev._3000IQPlay.experium.manager.CommandManager;
import dev._3000IQPlay.experium.manager.ConfigManager;
import dev._3000IQPlay.experium.manager.EventManager;
import dev._3000IQPlay.experium.manager.FileManager;
import dev._3000IQPlay.experium.manager.FriendManager;
import dev._3000IQPlay.experium.manager.HoleManager;
import dev._3000IQPlay.experium.manager.InventoryManager;
import dev._3000IQPlay.experium.manager.ModuleManager;
import dev._3000IQPlay.experium.manager.NoStopManager;
import dev._3000IQPlay.experium.manager.NotificationManager;
import dev._3000IQPlay.experium.manager.PacketManager;
import dev._3000IQPlay.experium.manager.PositionManager;
import dev._3000IQPlay.experium.manager.PotionManager;
import dev._3000IQPlay.experium.manager.ReloadManager;
import dev._3000IQPlay.experium.manager.RotationManager;
import dev._3000IQPlay.experium.manager.SafetyManager;
import dev._3000IQPlay.experium.manager.ServerManager;
import dev._3000IQPlay.experium.manager.SpeedManager;
import dev._3000IQPlay.experium.manager.TextManager;
import dev._3000IQPlay.experium.manager.TimerManager;
import dev._3000IQPlay.experium.manager.TotemPopManager;
import dev._3000IQPlay.experium.util.IconUtil;
import java.io.InputStream;
import java.nio.ByteBuffer;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Util;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.Display;

@Mod(modid="experium", name="Experium", version="1.0.1")
public class Experium {
    public static final String MODID = "experium";
    public static final String MODNAME = "Experium";
    public static final String MODVER = "1.0.1";
    public static final Logger LOGGER = LogManager.getLogger((String)"Experium");
    public static ModuleManager moduleManager;
    public static SpeedManager speedManager;
    public static PositionManager positionManager;
    public static RotationManager rotationManager;
    public static CommandManager commandManager;
    public static EventManager eventManager;
    public static ConfigManager configManager;
    public static FileManager fileManager;
    public static FriendManager friendManager;
    public static TextManager textManager;
    public static ColorManager colorManager;
    public static ServerManager serverManager;
    public static PotionManager potionManager;
    public static InventoryManager inventoryManager;
    public static TimerManager timerManager;
    public static PacketManager packetManager;
    public static ReloadManager reloadManager;
    public static TotemPopManager totemPopManager;
    public static HoleManager holeManager;
    public static NotificationManager notificationManager;
    public static SafetyManager safetyManager;
    public static NoStopManager baritoneManager;
    @Mod.Instance
    public static Experium INSTANCE;
    private static boolean unloaded;

    public static void load() {
        LOGGER.info("\n\nLoading Experium");
        unloaded = false;
        if (reloadManager != null) {
            reloadManager.unload();
            reloadManager = null;
        }
        baritoneManager = new NoStopManager();
        totemPopManager = new TotemPopManager();
        timerManager = new TimerManager();
        packetManager = new PacketManager();
        serverManager = new ServerManager();
        colorManager = new ColorManager();
        textManager = new TextManager();
        moduleManager = new ModuleManager();
        speedManager = new SpeedManager();
        rotationManager = new RotationManager();
        positionManager = new PositionManager();
        commandManager = new CommandManager();
        eventManager = new EventManager();
        configManager = new ConfigManager();
        fileManager = new FileManager();
        friendManager = new FriendManager();
        potionManager = new PotionManager();
        inventoryManager = new InventoryManager();
        holeManager = new HoleManager();
        notificationManager = new NotificationManager();
        safetyManager = new SafetyManager();
        LOGGER.info("Initialized Managers");
        moduleManager.init();
        LOGGER.info("Modules loaded.");
        configManager.init();
        eventManager.init();
        LOGGER.info("EventManager loaded.");
        textManager.init(true);
        moduleManager.onLoad();
        totemPopManager.init();
        timerManager.init();
        if (moduleManager.getModuleByClass(RPC.class).isEnabled()) {
            DiscordPresence.start();
        }
        LOGGER.info("Experium initialized!\n");
    }

    public static void unload(boolean unload) {
        LOGGER.info("\n\nUnloading Experium");
        if (unload) {
            reloadManager = new ReloadManager();
            reloadManager.init(commandManager != null ? commandManager.getPrefix() : ".");
        }
        if (baritoneManager != null) {
            baritoneManager.stop();
        }
        Experium.onUnload();
        eventManager = null;
        holeManager = null;
        timerManager = null;
        moduleManager = null;
        totemPopManager = null;
        serverManager = null;
        colorManager = null;
        textManager = null;
        speedManager = null;
        rotationManager = null;
        positionManager = null;
        commandManager = null;
        configManager = null;
        fileManager = null;
        friendManager = null;
        potionManager = null;
        inventoryManager = null;
        notificationManager = null;
        safetyManager = null;
        LOGGER.info("experium unloaded!\n");
    }

    public static void reload() {
        Experium.unload(false);
        Experium.load();
    }

    public static void onUnload() {
        if (!unloaded) {
            eventManager.onUnload();
            moduleManager.onUnload();
            configManager.saveConfig(Experium.configManager.config.replaceFirst("experium/", ""));
            moduleManager.onUnloadPost();
            timerManager.unload();
            unloaded = true;
        }
    }

    public static void setWindowIcon() {
        if (Util.getOSType() != Util.EnumOS.OSX) {
            try (InputStream inputStream16x = Minecraft.class.getResourceAsStream("/assets/minecraft/textures/icons/icon-16x.png");
                 InputStream inputStream32x = Minecraft.class.getResourceAsStream("/assets/minecraft/textures/icons/icon-32x.png");){
                ByteBuffer[] icons = new ByteBuffer[]{IconUtil.INSTANCE.readImageToBuffer(inputStream16x), IconUtil.INSTANCE.readImageToBuffer(inputStream32x)};
                Display.setIcon((ByteBuffer[])icons);
            }
            catch (Exception e) {
                LOGGER.error("Couldn't set Windows Icon", (Throwable)e);
            }
        }
    }

    private void setWindowsIcon() {
        Experium.setWindowIcon();
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        Minecraft mc = Minecraft.getMinecraft();
        Display.setTitle((String)("Experium 1.0.1  | " + mc.getSession().getUsername()));
        this.setWindowsIcon();
        Experium.load();
    }

    static {
        unloaded = false;
    }
}

