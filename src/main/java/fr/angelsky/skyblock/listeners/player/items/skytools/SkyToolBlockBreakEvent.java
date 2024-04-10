package fr.angelsky.skyblock.listeners.player.items.skytools;

import fr.angelsky.skyblock.SkyblockInstance;
import fr.angelsky.skyblock.managers.items.skytools.SkyToolUpgrade;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SkyToolBlockBreakEvent extends Event implements Cancellable {

    private final static HandlerList handlers = new HandlerList();

    private final List<SkyToolUpgrade> upgrades;
    private final Block block;
    private final Player player;
    private final boolean isSkyTool;
    private boolean cancel = false;
    private boolean dropItems = true;
    private final BlockBreakEvent breakEvent;

    public SkyToolBlockBreakEvent(ItemStack item, Player player, Block block, SkyblockInstance skyblockInstance, boolean isSkyTool, BlockBreakEvent breakEvent)
    {
        this.isSkyTool = isSkyTool;
        this.upgrades = skyblockInstance.getManagerLoader().getSkyToolsManager().getUpgrades(item);
        this.player = player;
        this.block = block;
        this.breakEvent = breakEvent;
    }

    public Player getPlayer() {
        return player;
    }

    public Block getBlock() {
        return block;
    }

    public List<SkyToolUpgrade> getUpgrades() {
        return upgrades;
    }

    public boolean isSkyTool() {
        return isSkyTool;
    }

    public boolean isDropItems() {
        return dropItems;
    }

    public void setDropItems(boolean dropItems) {
        this.dropItems = dropItems;
    }

    public BlockBreakEvent getBreakEvent() {
        return breakEvent;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList(){
        return handlers;
    }

    @Override
    public boolean isCancelled() {
        return cancel;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }
}
