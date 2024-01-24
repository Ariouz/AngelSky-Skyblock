package fr.angelsky.skyblock.listeners.player.level;

import fr.angelsky.skyblock.SkyblockInstance;
import fr.angelsky.skyblock.managers.player.level.experience.types.BlockPlayerExperience;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class PlayerXpEarningListener implements Listener {

    private final SkyblockInstance skyblockInstance;

    public PlayerXpEarningListener(SkyblockInstance skyblockInstance){
        this.skyblockInstance = skyblockInstance;
    }

    @EventHandler
    public void playerKillMobExperienceEvent(EntityDamageByEntityEvent event){
        if (!(event.getDamager() instanceof Player player)) return;
        if (!(event.getEntity() instanceof Damageable)) return;
        Entity damaged = event.getEntity();
        if (event.getFinalDamage() < ((Damageable) damaged).getHealth()) return;
        skyblockInstance.getManagerLoader().getPlayerExperienceManager().processEntityExperience(player, damaged.getType(), 0 , 0);
    }

    @EventHandler
    public void playerBreakBlockExperienceEvent(BlockBreakEvent event){
        if (event.isCancelled()) return;
        Block block = event.getBlock();
        BlockData blockData = block.getBlockData();
        BlockPlayerExperience blockXp = BlockPlayerExperience.getByEntityType(block.getType());
        if (blockXp == null) {
            removeMetadata(block);
            return;
        }

        if(blockData instanceof Ageable age && blockXp.isCheckAge()){
            if (age.getAge() != age.getMaximumAge()) {
                removeMetadata(block);
                return;
            }
        } else if (block.hasMetadata("angelsky_player_placed")) {
            removeMetadata(block);
            return;
        }
        skyblockInstance.getManagerLoader().getPlayerExperienceManager().processBlockExperience(event.getPlayer(), blockXp, 0 , 0);
    }

    public void removeMetadata(Block block){
        if (block.hasMetadata("angelsky_player_placed")) block.removeMetadata("angelsky_player_placed", skyblockInstance.getSkyblock());
    }

}
