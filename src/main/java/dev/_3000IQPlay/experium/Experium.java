//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Luni\Documents\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev._3000IQPlay.experium;

import org.apache.logging.log4j.LogManager;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import java.io.InputStream;
import org.lwjgl.opengl.Display;
import dev._3000IQPlay.experium.util.IconUtil;
import java.nio.ByteBuffer;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Util;
import dev._3000IQPlay.experium.features.modules.client.RPC;
import dev._3000IQPlay.experium.manager.NoStopManager;
import dev._3000IQPlay.experium.manager.SafetyManager;
import dev._3000IQPlay.experium.manager.NotificationManager;
import dev._3000IQPlay.experium.manager.HoleManager;
import dev._3000IQPlay.experium.manager.TotemPopManager;
import dev._3000IQPlay.experium.manager.ReloadManager;
import dev._3000IQPlay.experium.manager.PacketManager;
import dev._3000IQPlay.experium.manager.TimerManager;
import dev._3000IQPlay.experium.manager.InventoryManager;
import dev._3000IQPlay.experium.manager.PotionManager;
import dev._3000IQPlay.experium.manager.ServerManager;
import dev._3000IQPlay.experium.manager.ColorManager;
import dev._3000IQPlay.experium.manager.TextManager;
import dev._3000IQPlay.experium.manager.FriendManager;
import dev._3000IQPlay.experium.manager.FileManager;
import dev._3000IQPlay.experium.manager.ConfigManager;
import dev._3000IQPlay.experium.manager.EventManager;
import dev._3000IQPlay.experium.manager.CommandManager;
import dev._3000IQPlay.experium.manager.RotationManager;
import dev._3000IQPlay.experium.manager.PositionManager;
import dev._3000IQPlay.experium.manager.SpeedManager;
import dev._3000IQPlay.experium.manager.ModuleManager;
import me.zero.alpine.bus.EventBus;
import org.apache.logging.log4j.Logger;
import net.minecraftforge.fml.common.Mod;

@Mod(modid = "experium", name = "Experium", version = "1.0.1")
public class Experium
{
    public static final String MODID = "experium";
    public static final String MODNAME = "Experium";
    public static final String MODVER = "1.0.1";
    public static final Logger LOGGER;
    public static EventBus dispatcher;
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
        Experium.LOGGER.info("\n\nLoading Experium");
        Experium.unloaded = false;
        if (Experium.reloadManager != null) {
            Experium.reloadManager.unload();
            Experium.reloadManager = null;
        }
        Experium.dispatcher = new me.zero.alpine.bus.EventManager();
        Experium.baritoneManager = new NoStopManager();
        Experium.totemPopManager = new TotemPopManager();
        Experium.timerManager = new TimerManager();
        Experium.packetManager = new PacketManager();
        Experium.serverManager = new ServerManager();
        Experium.colorManager = new ColorManager();
        Experium.textManager = new TextManager();
        Experium.moduleManager = new ModuleManager();
        Experium.speedManager = new SpeedManager();
        Experium.rotationManager = new RotationManager();
        Experium.positionManager = new PositionManager();
        Experium.commandManager = new CommandManager();
        Experium.eventManager = new EventManager();
        Experium.configManager = new ConfigManager();
        Experium.fileManager = new FileManager();
        Experium.friendManager = new FriendManager();
        Experium.potionManager = new PotionManager();
        Experium.inventoryManager = new InventoryManager();
        Experium.holeManager = new HoleManager();
        Experium.notificationManager = new NotificationManager();
        Experium.safetyManager = new SafetyManager();
        Experium.LOGGER.info("Initialized Managers");
        Experium.moduleManager.init();
        Experium.LOGGER.info("Modules loaded.");
        Experium.configManager.init();
        Experium.eventManager.init();
        Experium.LOGGER.info("EventManager loaded.");
        Experium.textManager.init(true);
        Experium.moduleManager.onLoad();
        Experium.totemPopManager.init();
        Experium.timerManager.init();
        if (Experium.moduleManager.getModuleByClass(RPC.class).isEnabled()) {
            DiscordPresence.start();
        }
        Experium.LOGGER.info("Experium initialized!\n");
    }
    
    public static void unload(final boolean unload) {
        Experium.LOGGER.info("\n\nUnloading Experium");
        if (unload) {
            (Experium.reloadManager = new ReloadManager()).init((Experium.commandManager != null) ? Experium.commandManager.getPrefix() : ".");
        }
        if (Experium.baritoneManager != null) {
            Experium.baritoneManager.stop();
        }
        onUnload();
        Experium.dispatcher = null;
        Experium.eventManager = null;
        Experium.holeManager = null;
        Experium.timerManager = null;
        Experium.moduleManager = null;
        Experium.totemPopManager = null;
        Experium.serverManager = null;
        Experium.colorManager = null;
        Experium.textManager = null;
        Experium.speedManager = null;
        Experium.rotationManager = null;
        Experium.positionManager = null;
        Experium.commandManager = null;
        Experium.configManager = null;
        Experium.fileManager = null;
        Experium.friendManager = null;
        Experium.potionManager = null;
        Experium.inventoryManager = null;
        Experium.notificationManager = null;
        Experium.safetyManager = null;
        Experium.LOGGER.info("experium unloaded!\n");
    }
    
    public static void reload() {
        unload(false);
        load();
    }
    
    public static void onUnload() {
        if (!Experium.unloaded) {
            Experium.eventManager.onUnload();
            Experium.moduleManager.onUnload();
            Experium.configManager.saveConfig(Experium.configManager.config.replaceFirst("experium/", ""));
            Experium.moduleManager.onUnloadPost();
            Experium.timerManager.unload();
            Experium.unloaded = true;
        }
    }
    
    public static void setWindowIcon() {
        if (Util.getOSType() != Util.EnumOS.OSX) {
            try (final InputStream inputStream16x = Minecraft.class.getResourceAsStream("/assets/minecraft/textures/icons/icon-16x.png");
                 final InputStream inputStream32x = Minecraft.class.getResourceAsStream("/assets/minecraft/textures/icons/icon-32x.png")) {
                final ByteBuffer[] icons = { IconUtil.INSTANCE.readImageToBuffer(inputStream16x), IconUtil.INSTANCE.readImageToBuffer(inputStream32x) };
                Display.setIcon(icons);
            }
            catch (Exception e) {
                Experium.LOGGER.error("Couldn't set Windows Icon", (Throwable)e);
            }
        }
    }
    
    private void setWindowsIcon() {
        setWindowIcon();
    }
    
    @Mod.EventHandler
    public void preInit(final FMLPreInitializationEvent event) {
    }
    
    @Mod.EventHandler
    public void init(final FMLInitializationEvent event) {
        final Minecraft mc = Minecraft.getMinecraft();
        Display.setTitle("Experium 1.0.1  | " + mc.getSession().getUsername());
        this.setWindowsIcon();
        load();
    }
    
    static {
        LOGGER = LogManager.getLogger("Experium");
        Experium.unloaded = false;
    }
}
