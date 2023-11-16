package fr.angelsky.skyblock.listeners.server.crates;

import fr.angelsky.skyblock.SkyblockInstance;
import fr.angelsky.skyblock.managers.ItemManager;
import fr.angelsky.skyblock.managers.server.crates.Crate;
import io.th0rgal.oraxen.api.OraxenItems;
import io.th0rgal.oraxen.api.events.furniture.OraxenFurnitureBreakEvent;
import io.th0rgal.oraxen.api.events.furniture.OraxenFurnitureDamageEvent;
import io.th0rgal.oraxen.api.events.furniture.OraxenFurnitureInteractEvent;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class CratesInteract implements Listener {

    private final SkyblockInstance skyblockInstance;

    public CratesInteract(SkyblockInstance skyblockInstance){
        this.skyblockInstance = skyblockInstance;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInteract(OraxenFurnitureInteractEvent event){
        boolean in = false;
        for (Crate crate : skyblockInstance.getManagerLoader().getCrateManager().getCrates()) { // CHECK IF FURNITURE IS CRATE
            if(crate.getCrateId().equals(event.getMechanic().getItemID())){
                in = true;
                break;
            }
        }

        if(!in) return;

        Crate crate = skyblockInstance.getManagerLoader().getCrateManager().getCrateFromOraxenId(event.getMechanic().getItemID());

        Player player = event.getPlayer();
        ItemStack keyItem = OraxenItems.getItemById(crate.getKeyId()).build();

        if(player.getInventory().getItemInMainHand().getType() == Material.AIR){
            player.sendMessage(SkyblockInstance.PREFIX + "Vous devez avoir une " + keyItem.getItemMeta().getDisplayName() + ChatColor.WHITE +" pour ouvrir cette caisse.");
            player.setVelocity(player.getLocation().getDirection().multiply(-1).add(new Vector(0, 0.3, 0)));
            player.playSound(player.getLocation(), Sound.BLOCK_FIRE_EXTINGUISH, 30, 15);
            return;
        }

        if(OraxenItems.getIdByItem(player.getInventory().getItemInMainHand()) == null){
            player.sendMessage(SkyblockInstance.PREFIX + "Vous devez avoir une " + keyItem.getItemMeta().getDisplayName() + ChatColor.WHITE +" pour ouvrir cette caisse.");
            player.setVelocity(player.getLocation().getDirection().multiply(-1).add(new Vector(0, 0.3, 0)));
            player.playSound(player.getLocation(), Sound.BLOCK_FIRE_EXTINGUISH, 30, 15);
            return;
        }


        if(OraxenItems.getIdByItem(player.getInventory().getItemInMainHand()).equals(crate.getKeyId())){ // HAS KEY
            skyblockInstance.getManagerLoader().getCrateManager().openCrate(crate, player, event.getBlock().getLocation());
            new ItemManager().removeItems(player, player.getInventory(), player.getInventory().getItemInMainHand(), 1);
        }else{
            player.sendMessage(SkyblockInstance.PREFIX + "Vous devez avoir une " + keyItem.getItemMeta().getDisplayName() + ChatColor.WHITE +" pour ouvrir cette caisse.");
            player.setVelocity(player.getLocation().getDirection().multiply(-1).add(new Vector(0, 0.3, 0)));
            player.playSound(player.getLocation(), Sound.BLOCK_FIRE_EXTINGUISH, 30, 15);
        }

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDamage(OraxenFurnitureDamageEvent event){
        boolean in = false;
        for (Crate crate : skyblockInstance.getManagerLoader().getCrateManager().getCrates()) { // CHECK IF FURNITURE IS CRATE
            if(crate.getCrateId().equals(event.getMechanic().getItemID())){
                in = true;
                break;
            }
        }

        if(!in) return;


        Crate crate = skyblockInstance.getManagerLoader().getCrateManager().getCrateFromOraxenId(event.getMechanic().getItemID());
        Player player = event.getPlayer();
        skyblockInstance.getManagerLoader().getCrateManager().openLootMenu(crate, player);

        if(player.isOp() && player.getGameMode() == GameMode.CREATIVE) return;
        event.setCancelled(true);

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBreak(OraxenFurnitureBreakEvent event){
        boolean in = false;
        for (Crate crate : skyblockInstance.getManagerLoader().getCrateManager().getCrates()) { // CHECK IF FURNITURE IS CRATE
            if(crate.getCrateId().equals(event.getMechanic().getItemID())){
                in = true;
                break;
            }
        }

        if(!in) return;

        if(event.getPlayer().isOp() && event.getPlayer().getGameMode() == GameMode.CREATIVE) return;
        event.setCancelled(true);
    }

}
