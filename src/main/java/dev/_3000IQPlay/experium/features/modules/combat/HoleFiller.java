//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Luni\Documents\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package dev._3000IQPlay.experium.features.modules.combat;

import dev._3000IQPlay.experium.features.modules.player.Freecam;
import net.minecraft.block.BlockObsidian;
import net.minecraft.block.BlockWeb;
import dev._3000IQPlay.experium.features.Feature;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import java.util.Iterator;
import dev._3000IQPlay.experium.util.BlockUtil;
import dev._3000IQPlay.experium.util.MathUtil;
import java.util.Collection;
import java.util.ArrayList;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import org.lwjgl.input.Keyboard;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import dev._3000IQPlay.experium.event.events.UpdateWalkingPlayerEvent;
import net.minecraft.entity.Entity;
import dev._3000IQPlay.experium.util.EntityUtil;
import dev._3000IQPlay.experium.Experium;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketChatMessage;
import dev._3000IQPlay.experium.features.modules.client.ClickGui;
import dev._3000IQPlay.experium.features.modules.client.PingBypass;
import java.util.HashMap;
import dev._3000IQPlay.experium.features.setting.Bind;
import net.minecraft.util.math.BlockPos;
import java.util.Map;
import dev._3000IQPlay.experium.util.Timer;
import dev._3000IQPlay.experium.util.InventoryUtil;
import dev._3000IQPlay.experium.features.setting.Setting;
import dev._3000IQPlay.experium.features.modules.Module;

public class HoleFiller extends Module
{
    private static HoleFiller INSTANCE;
    private final Setting<Boolean> server;
    private final Setting<Double> range;
    private final Setting<Integer> delay;
    private final Setting<Integer> blocksPerTick;
    private final Setting<Boolean> rotate;
    private final Setting<Boolean> raytrace;
    private final Setting<Boolean> disable;
    private final Setting<Integer> disableTime;
    private final Setting<Boolean> offhand;
    private final Setting<InventoryUtil.Switch> switchMode;
    private final Setting<Boolean> onlySafe;
    private final Setting<Boolean> webSelf;
    private final Setting<Boolean> highWeb;
    private final Setting<Boolean> freecam;
    private final Setting<Boolean> midSafeHoles;
    private final Setting<Boolean> packet;
    private final Setting<Boolean> onGroundCheck;
    private final Timer offTimer;
    private final Timer timer;
    private final Map<BlockPos, Integer> retries;
    private final Timer retryTimer;
    public Setting<Mode> mode;
    public Setting<PlaceMode> placeMode;
    private final Setting<Double> smartRange;
    public Setting<Bind> obbyBind;
    public Setting<Bind> webBind;
    public Mode currentMode;
    private boolean accessedViaBind;
    private int targetSlot;
    private int blocksThisTick;
    private Offhand.Mode offhandMode;
    private Offhand.Mode2 offhandMode2;
    private boolean isSneaking;
    private boolean hasOffhand;
    private boolean placeHighWeb;
    private int lastHotbarSlot;
    private boolean switchedItem;
    
    public HoleFiller() {
        super("HoleFiller", "Fills holes around you.", Category.COMBAT, true, false, true);
        this.server = (Setting<Boolean>)this.register(new Setting("Server", (T)false));
        this.range = (Setting<Double>)this.register(new Setting("PlaceRange", (T)6.0, (T)0.0, (T)10.0));
        this.delay = (Setting<Integer>)this.register(new Setting("Delay/Place", (T)50, (T)0, (T)250));
        this.blocksPerTick = (Setting<Integer>)this.register(new Setting("Block/Place", (T)8, (T)1, (T)20));
        this.rotate = (Setting<Boolean>)this.register(new Setting("Rotate", (T)true));
        this.raytrace = (Setting<Boolean>)this.register(new Setting("Raytrace", (T)false));
        this.disable = (Setting<Boolean>)this.register(new Setting("Disable", (T)true));
        this.disableTime = (Setting<Integer>)this.register(new Setting("Ms/Disable", (T)200, (T)1, (T)250));
        this.offhand = (Setting<Boolean>)this.register(new Setting("OffHand", (T)true));
        this.switchMode = (Setting<InventoryUtil.Switch>)this.register(new Setting("Switch", (T)InventoryUtil.Switch.NORMAL));
        this.onlySafe = (Setting<Boolean>)this.register(new Setting("OnlySafe", (T)true, v -> this.offhand.getValue()));
        this.webSelf = (Setting<Boolean>)this.register(new Setting("SelfWeb", (T)false));
        this.highWeb = (Setting<Boolean>)this.register(new Setting("HighWeb", (T)false));
        this.freecam = (Setting<Boolean>)this.register(new Setting("Freecam", (T)false));
        this.midSafeHoles = (Setting<Boolean>)this.register(new Setting("MidSafe", (T)false));
        this.packet = (Setting<Boolean>)this.register(new Setting("Packet", (T)false));
        this.onGroundCheck = (Setting<Boolean>)this.register(new Setting("OnGroundCheck", (T)false));
        this.offTimer = new Timer();
        this.timer = new Timer();
        this.retries = new HashMap<BlockPos, Integer>();
        this.retryTimer = new Timer();
        this.mode = (Setting<Mode>)this.register(new Setting("Mode", (T)Mode.OBSIDIAN));
        this.placeMode = (Setting<PlaceMode>)this.register(new Setting("PlaceMode", (T)PlaceMode.ALL));
        this.smartRange = (Setting<Double>)this.register(new Setting("SmartRange", (T)6.0, (T)0.0, (T)10.0, v -> this.placeMode.getValue() == PlaceMode.SMART));
        this.obbyBind = (Setting<Bind>)this.register(new Setting("Obsidian", (T)new Bind(-1)));
        this.webBind = (Setting<Bind>)this.register(new Setting("Webs", (T)new Bind(-1)));
        this.currentMode = Mode.OBSIDIAN;
        this.accessedViaBind = false;
        this.targetSlot = -1;
        this.blocksThisTick = 0;
        this.offhandMode = Offhand.Mode.CRYSTALS;
        this.offhandMode2 = Offhand.Mode2.CRYSTALS;
        this.hasOffhand = false;
        this.placeHighWeb = false;
        this.lastHotbarSlot = -1;
        this.switchedItem = false;
        this.setInstance();
    }
    
    public static HoleFiller getInstance() {
        if (HoleFiller.INSTANCE == null) {
            HoleFiller.INSTANCE = new HoleFiller();
        }
        return HoleFiller.INSTANCE;
    }
    
    private void setInstance() {
        HoleFiller.INSTANCE = this;
    }
    
    private boolean shouldServer() {
        return PingBypass.getInstance().isConnected() && this.server.getValue();
    }
    
    @Override
    public void onEnable() {
        if (fullNullCheck()) {
            this.disable();
        }
        if (!HoleFiller.mc.player.onGround && this.onGroundCheck.getValue()) {
            return;
        }
        if (this.shouldServer()) {
            HoleFiller.mc.player.connection.sendPacket((Packet)new CPacketChatMessage("@Serverprefix" + ClickGui.getInstance().prefix.getValue()));
            HoleFiller.mc.player.connection.sendPacket((Packet)new CPacketChatMessage("@Server" + ClickGui.getInstance().prefix.getValue() + "module HoleFiller set Enabled true"));
            return;
        }
        this.lastHotbarSlot = HoleFiller.mc.player.inventory.currentItem;
        if (!this.accessedViaBind) {
            this.currentMode = this.mode.getValue();
        }
        final Offhand module = Experium.moduleManager.getModuleByClass(Offhand.class);
        this.offhandMode = module.mode;
        this.offhandMode2 = module.currentMode;
        if (this.offhand.getValue() && (EntityUtil.isSafe((Entity)HoleFiller.mc.player) || !this.onlySafe.getValue())) {
            if (module.type.getValue() == Offhand.Type.NEW) {
                if (this.currentMode == Mode.WEBS) {
                    module.setSwapToTotem(false);
                    module.setMode(Offhand.Mode.WEBS);
                }
                else {
                    module.setSwapToTotem(false);
                    module.setMode(Offhand.Mode.OBSIDIAN);
                }
            }
            else {
                if (this.currentMode == Mode.WEBS) {
                    module.setMode(Offhand.Mode2.WEBS);
                }
                else {
                    module.setMode(Offhand.Mode2.OBSIDIAN);
                }
                if (!module.didSwitchThisTick) {
                    module.doOffhand();
                }
            }
        }
        Experium.holeManager.update();
        this.offTimer.reset();
    }
    
    @Override
    public void onTick() {
        if (this.isOn() && (this.blocksPerTick.getValue() != 1 || !this.rotate.getValue())) {
            this.doHoleFill();
        }
    }
    
    @SubscribeEvent
    public void onUpdateWalkingPlayer(final UpdateWalkingPlayerEvent event) {
        if (this.isOn() && event.getStage() == 0 && this.blocksPerTick.getValue() == 1 && this.rotate.getValue()) {
            this.doHoleFill();
        }
    }
    
    @Override
    public void onDisable() {
        if (this.offhand.getValue()) {
            Experium.moduleManager.getModuleByClass(Offhand.class).setMode(this.offhandMode);
            Experium.moduleManager.getModuleByClass(Offhand.class).setMode(this.offhandMode2);
        }
        this.switchItem(true);
        this.isSneaking = EntityUtil.stopSneaking(this.isSneaking);
        this.retries.clear();
        this.accessedViaBind = false;
        this.hasOffhand = false;
    }
    
    @SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
    public void onKeyInput(final InputEvent.KeyInputEvent event) {
        if (Keyboard.getEventKeyState()) {
            if (this.obbyBind.getValue().getKey() == Keyboard.getEventKey()) {
                this.accessedViaBind = true;
                this.currentMode = Mode.OBSIDIAN;
                this.toggle();
            }
            if (this.webBind.getValue().getKey() == Keyboard.getEventKey()) {
                this.accessedViaBind = true;
                this.currentMode = Mode.WEBS;
                this.toggle();
            }
        }
    }
    
    private void doHoleFill() {
        if (this.check()) {
            return;
        }
        if (this.placeHighWeb) {
            final BlockPos pos = new BlockPos(HoleFiller.mc.player.posX, HoleFiller.mc.player.posY + 1.0, HoleFiller.mc.player.posZ);
            this.placeBlock(pos);
            this.placeHighWeb = false;
        }
        if (this.midSafeHoles.getValue()) {
            final Object object = Experium.holeManager.getMidSafety();
            synchronized (object) {
                final ArrayList<BlockPos> targets = new ArrayList<BlockPos>(Experium.holeManager.getMidSafety());
            }
        }
        final Object object = Experium.holeManager.getHoles();
        ArrayList<BlockPos> targets;
        synchronized (object) {
            targets = new ArrayList<BlockPos>(Experium.holeManager.getHoles());
        }
        for (final BlockPos position : targets) {
            if (HoleFiller.mc.player.getDistanceSq(position) <= MathUtil.square(this.range.getValue())) {
                if (this.placeMode.getValue() == PlaceMode.SMART && !this.isPlayerInRange(position)) {
                    continue;
                }
                if (position.equals((Object)new BlockPos(HoleFiller.mc.player.getPositionVector()))) {
                    if (this.currentMode != Mode.WEBS) {
                        continue;
                    }
                    if (!this.webSelf.getValue()) {
                        continue;
                    }
                    if (this.highWeb.getValue()) {
                        this.placeHighWeb = true;
                    }
                }
                final int placeability;
                if ((placeability = BlockUtil.isPositionPlaceable(position, this.raytrace.getValue())) == 1 && (this.currentMode == Mode.WEBS || (this.switchMode.getValue() == InventoryUtil.Switch.SILENT && (this.currentMode == Mode.WEBS || this.retries.get(position) == null || this.retries.get(position) < 4)))) {
                    this.placeBlock(position);
                    if (this.currentMode == Mode.WEBS) {
                        continue;
                    }
                    this.retries.put(position, (this.retries.get(position) == null) ? 1 : (this.retries.get(position) + 1));
                }
                else {
                    if (placeability != 3) {
                        continue;
                    }
                    this.placeBlock(position);
                }
            }
        }
    }
    
    private void placeBlock(final BlockPos pos) {
        if (this.blocksThisTick < this.blocksPerTick.getValue() && this.switchItem(false)) {
            final boolean bl;
            final boolean smartRotate = bl = (this.blocksPerTick.getValue() == 1 && this.rotate.getValue());
            this.isSneaking = (smartRotate ? BlockUtil.placeBlockSmartRotate(pos, this.hasOffhand ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND, true, this.packet.getValue(), this.isSneaking) : BlockUtil.placeBlock(pos, this.hasOffhand ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND, this.rotate.getValue(), this.packet.getValue(), this.isSneaking));
            this.timer.reset();
            ++this.blocksThisTick;
        }
    }
    
    private boolean isPlayerInRange(final BlockPos pos) {
        for (final EntityPlayer player : HoleFiller.mc.world.playerEntities) {
            if (EntityUtil.isntValid((Entity)player, this.smartRange.getValue())) {
                continue;
            }
            return true;
        }
        return false;
    }
    
    private boolean check() {
        if (Feature.fullNullCheck() || (this.disable.getValue() && this.offTimer.passedMs(this.disableTime.getValue()))) {
            this.disable();
            return true;
        }
        if (HoleFiller.mc.player.inventory.currentItem != this.lastHotbarSlot && HoleFiller.mc.player.inventory.currentItem != InventoryUtil.findHotbarBlock((Class)((this.currentMode == Mode.WEBS) ? BlockWeb.class : BlockObsidian.class))) {
            this.lastHotbarSlot = HoleFiller.mc.player.inventory.currentItem;
        }
        this.switchItem(true);
        if (!this.freecam.getValue() && Experium.moduleManager.isModuleEnabled(Freecam.class)) {
            return true;
        }
        this.blocksThisTick = 0;
        this.isSneaking = EntityUtil.stopSneaking(this.isSneaking);
        if (this.retryTimer.passedMs(2000L)) {
            this.retries.clear();
            this.retryTimer.reset();
        }
        switch (this.currentMode) {
            case WEBS: {
                this.hasOffhand = InventoryUtil.isBlock(HoleFiller.mc.player.getHeldItemOffhand().getItem(), BlockWeb.class);
                this.targetSlot = InventoryUtil.findHotbarBlock(BlockWeb.class);
                break;
            }
            case OBSIDIAN: {
                this.hasOffhand = InventoryUtil.isBlock(HoleFiller.mc.player.getHeldItemOffhand().getItem(), BlockObsidian.class);
                this.targetSlot = InventoryUtil.findHotbarBlock(BlockObsidian.class);
                break;
            }
        }
        if (this.onlySafe.getValue() && !EntityUtil.isSafe((Entity)HoleFiller.mc.player)) {
            this.disable();
            return true;
        }
        return (!this.hasOffhand && this.targetSlot == -1 && (!this.offhand.getValue() || (!EntityUtil.isSafe((Entity)HoleFiller.mc.player) && this.onlySafe.getValue()))) || (this.offhand.getValue() && !this.hasOffhand) || !this.timer.passedMs(this.delay.getValue());
    }
    
    private boolean switchItem(final boolean back) {
        if (this.offhand.getValue()) {
            return true;
        }
        final boolean[] value = InventoryUtil.switchItem(back, this.lastHotbarSlot, this.switchedItem, this.switchMode.getValue(), (Class)((this.currentMode == Mode.WEBS) ? BlockWeb.class : BlockObsidian.class));
        this.switchedItem = value[0];
        return value[1];
    }
    
    static {
        HoleFiller.INSTANCE = new HoleFiller();
    }
    
    public enum PlaceMode
    {
        SMART, 
        ALL;
    }
    
    public enum Mode
    {
        WEBS, 
        OBSIDIAN;
    }
}
