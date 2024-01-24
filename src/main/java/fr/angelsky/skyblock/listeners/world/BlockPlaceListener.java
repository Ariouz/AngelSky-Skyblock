package fr.angelsky.skyblock.listeners.world;

import fr.angelsky.skyblock.SkyblockInstance;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.metadata.FixedMetadataValue;

public class BlockPlaceListener implements Listener {

    private final SkyblockInstance skyblockInstance;

    public BlockPlaceListener(SkyblockInstance skyblockInstance){
        this.skyblockInstance = skyblockInstance;
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event){
        Block block = event.getBlock();
        block.setMetadata("angelsky_player_placed", new FixedMetadataValue(skyblockInstance.getSkyblock(), "true"));
    }

}
