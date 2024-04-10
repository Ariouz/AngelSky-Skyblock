package fr.angelsky.skyblock.listeners.player.items.skytools;

import fr.angelsky.skyblock.SkyblockInstance;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class SkyToolBlockBreak implements Listener {

    private final SkyblockInstance skyblockInstance;

    public SkyToolBlockBreak(SkyblockInstance skyblockInstance){
        this.skyblockInstance = skyblockInstance;
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event)
    {
        if (event.isCancelled()) return ;
        ItemStack hand = event.getPlayer().getInventory().getItemInMainHand();
        boolean isSkyTool = skyblockInstance.getManagerLoader().getSkyToolsManager().isSkyTool(hand);
        SkyToolBlockBreakEvent breakEvent = new SkyToolBlockBreakEvent(hand, event.getPlayer(), event.getBlock(), skyblockInstance, isSkyTool, event);
        Bukkit.getPluginManager().callEvent(breakEvent);
        if (breakEvent.isCancelled()) event.setCancelled(true);
        if (!breakEvent.isDropItems()) event.setDropItems(false);
    }

}
