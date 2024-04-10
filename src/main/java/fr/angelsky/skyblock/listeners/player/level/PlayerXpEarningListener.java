package fr.angelsky.skyblock.listeners.player.level;

import fr.angelsky.skyblock.SkyblockInstance;
import fr.angelsky.skyblock.listeners.player.items.skytools.SkyToolBlockBreakEvent;
import fr.angelsky.skyblock.managers.items.skytools.SkyToolUpgrade;
import org.bukkit.block.Block;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.entity.EntityBreedEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class PlayerXpEarningListener implements Listener {

    private final SkyblockInstance skyblockInstance;

    public PlayerXpEarningListener(SkyblockInstance skyblockInstance){
        this.skyblockInstance = skyblockInstance;
    }

    @EventHandler
    public void playerKillMobExperienceEvent(EntityDamageByEntityEvent event){
        if (event.isCancelled()) return;
        if (!(event.getDamager() instanceof Player player)) return;
        if (!(event.getEntity() instanceof Damageable)) return;
        Entity damaged = event.getEntity();
        if (event.getFinalDamage() < ((Damageable) damaged).getHealth()) return;
        skyblockInstance.getManagerLoader().getPlayerExperienceManager().processEntityExperience(player, damaged.getType(), 0 , 0);
    }

    @EventHandler
    public void playerBreakBlockExperienceEvent(SkyToolBlockBreakEvent event){
        if (event.isCancelled()) return;
        Block block = event.getBlock();
        Player player = event.getPlayer();
        ItemStack hand = player.getInventory().getItemInMainHand();

        if (event.isSkyTool())
        {
            List<SkyToolUpgrade> upgrades = skyblockInstance.getManagerLoader().getSkyToolsManager().getUpgrades(hand);
            skyblockInstance.getManagerLoader().getSkyToolsManager().applyUpgrades(upgrades, player, block, hand, event);
            event.setCancelled(true);
            event.setDropItems(false);
        }
        else
            skyblockInstance.getManagerLoader().getPlayerExperienceManager().processBlockExperience(player, block, 0 , 0);
    }

    @EventHandler
    public void playerEnchantExperienceEvent(EnchantItemEvent event){
        if (event.isCancelled()) return;
        skyblockInstance.getManagerLoader().getPlayerExperienceManager().processEnchantExperience(event.getEnchanter(), event.whichButton() + 1, 0 , 0);
    }

    @EventHandler
    public void playerBreedExperienceEvent(EntityBreedEvent event){
        if (event.isCancelled()) return;
        if (!(event.getBreeder() instanceof Player)) return;
        skyblockInstance.getManagerLoader().getPlayerExperienceManager().processBreedExperience((Player) event.getBreeder(), 2, 0 , 0);
    }



}
