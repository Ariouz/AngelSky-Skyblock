package fr.angelsky.skyblock.listeners.world;

import com.destroystokyo.paper.event.player.PlayerElytraBoostEvent;
import fr.angelsky.angelskyapi.api.accounts.TempPlayerAccount;
import fr.angelsky.angelskyapi.api.enums.rank.Rank;
import fr.angelsky.skyblock.SkyblockInstance;
import io.papermc.paper.event.player.PlayerFlowerPotManipulateEvent;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.EntityBreedEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.ArrayList;
import java.util.List;

public class SpawnProtectionEventHandler implements Listener {

    private final SkyblockInstance skyblockInstance;
    private final List<World> protectedWorlds;

    public SpawnProtectionEventHandler(SkyblockInstance skyblockInstance){
        this.skyblockInstance = skyblockInstance;
        this.protectedWorlds = skyblockInstance.getManagerLoader().getRegionManager().getWorldManager().getProtectedWorlds();
    }

    @EventHandler
    public void onProtectedWorldBlockBreak(BlockBreakEvent event){
        TempPlayerAccount tempPlayerAccount = skyblockInstance.getAngelSkyApiInstance().getApiManager().getAccountManager().getAccount(event.getPlayer().getUniqueId());
        if(tempPlayerAccount.getRank().getPower() < Rank.ADMIN.getPower()){
            if(protectedWorlds.contains(event.getBlock().getWorld())){
                event.setCancelled(event.getPlayer().getGameMode() != GameMode.CREATIVE);
            }
        }
    }

    @EventHandler
    public void onProtectedWorldBlockPlace(BlockPlaceEvent event){
        TempPlayerAccount tempPlayerAccount = skyblockInstance.getAngelSkyApiInstance().getApiManager().getAccountManager().getAccount(event.getPlayer().getUniqueId());
        if(tempPlayerAccount.getRank().getPower() < Rank.ADMIN.getPower()){
            if(protectedWorlds.contains(event.getBlock().getWorld())){
                event.setCancelled(event.getPlayer().getGameMode() != GameMode.CREATIVE);
            }
        }
    }

    @EventHandler
    public void onProtectedWorldBreed(EntityBreedEvent event){
        if (!(event.getBreeder() instanceof Player player))
                return;
        TempPlayerAccount tempPlayerAccount = skyblockInstance.getAngelSkyApiInstance().getApiManager().getAccountManager().getAccount(player.getUniqueId());
        if(tempPlayerAccount.getRank().getPower() < Rank.ADMIN.getPower()){
            if(protectedWorlds.contains(player.getWorld())){
                event.setCancelled(player.getGameMode() != GameMode.CREATIVE);
            }
        }
    }

    @EventHandler
    public void onProtectedWorldSignEdit(SignChangeEvent event){
        TempPlayerAccount tempPlayerAccount = skyblockInstance.getAngelSkyApiInstance().getApiManager().getAccountManager().getAccount(event.getPlayer().getUniqueId());
        if(tempPlayerAccount.getRank().getPower() < Rank.ADMIN.getPower()){
            if(protectedWorlds.contains(event.getBlock().getWorld())){
                event.setCancelled(event.getPlayer().getGameMode() != GameMode.CREATIVE);
            }
        }
    }

    @EventHandler
    public void onProtectedWorldCauldronEdit(CauldronLevelChangeEvent event){
        if (!(event.getEntity() instanceof Player player)){
            event.setCancelled(true);
            return ;
        }
        TempPlayerAccount tempPlayerAccount = skyblockInstance.getAngelSkyApiInstance().getApiManager().getAccountManager().getAccount(player.getUniqueId());
        if(tempPlayerAccount.getRank().getPower() < Rank.ADMIN.getPower()){
            if(protectedWorlds.contains(event.getBlock().getWorld())){
                event.setCancelled(player.getGameMode() != GameMode.CREATIVE);
            }
        }
    }

    @EventHandler
    public void onProtectedWorldFlowerPotAction(PlayerFlowerPotManipulateEvent event){
        TempPlayerAccount tempPlayerAccount = skyblockInstance.getAngelSkyApiInstance().getApiManager().getAccountManager().getAccount(event.getPlayer().getUniqueId());
        if(tempPlayerAccount.getRank().getPower() < Rank.ADMIN.getPower()){
            if(protectedWorlds.contains(event.getFlowerpot().getWorld())){
                event.setCancelled(event.getPlayer().getGameMode() != GameMode.CREATIVE);
            }
        }
    }

    @EventHandler
    public void onProjectileLaunch(ProjectileLaunchEvent event){
        if(!(event.getEntity().getShooter() instanceof Player) ||event.getEntity().getShooter() == null) return;

        Player player = (Player)event.getEntity().getShooter();
        TempPlayerAccount tempPlayerAccount = skyblockInstance.getAngelSkyApiInstance().getApiManager().getAccountManager().getAccount(player.getUniqueId());

        if(tempPlayerAccount.getRank().getPower() < Rank.ADMIN.getPower()){
            if(protectedWorlds.contains(event.getLocation().getWorld())){
               if(event.getEntity() instanceof Arrow || event.getEntity() instanceof EnderPearl){
                    event.getEntity().remove();
                    event.setCancelled(true);
               }
            }
        }
    }

    @EventHandler
    public void onPlayerUseElytra(PlayerElytraBoostEvent event){
        TempPlayerAccount tempPlayerAccount = skyblockInstance.getAngelSkyApiInstance().getApiManager().getAccountManager().getAccount(event.getPlayer().getUniqueId());
        if(tempPlayerAccount.getRank().getPower() < Rank.ADMIN.getPower()){
            if(protectedWorlds.contains(event.getPlayer().getWorld())){
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onSpawnInteract(PlayerInteractEvent event){
        TempPlayerAccount tempPlayerAccount = skyblockInstance.getAngelSkyApiInstance().getApiManager().getAccountManager().getAccount(event.getPlayer().getUniqueId());
        List<String> disabledItems = new ArrayList<>();
        disabledItems.add("BOAT"); // ALL BOATS
        disabledItems.add(Material.WATER_BUCKET.toString());
        disabledItems.add(Material.LAVA_BUCKET.toString());
        disabledItems.add(Material.BUCKET.toString());
        disabledItems.add(Material.FLINT_AND_STEEL.toString());
        disabledItems.add(Material.FIRE_CHARGE.toString());
        disabledItems.add(Material.END_CRYSTAL.toString());

        if(tempPlayerAccount.getRank().getPower() < Rank.ADMIN.getPower()){
            if(protectedWorlds.contains(event.getPlayer().getWorld())){
                if(event.getAction() == Action.RIGHT_CLICK_BLOCK){
                    disabledItems.forEach(item -> {
                        if(event.getItem() != null){
                            if (event.getItem().getType().toString().contains(item)){
                                event.setCancelled(true);
                            }
                        }
                    });
                    if(event.getClickedBlock() == null) return;
                    if(event.getClickedBlock().getType().toString().contains("TRAPDOOR")) event.setCancelled(true);
                    if(event.getClickedBlock().getType().toString().contains("DOOR")) event.setCancelled(true);
                    if(event.getClickedBlock().getType().toString().contains("ANVIL")) event.setCancelled(true);
                    if(event.getClickedBlock().getType().toString().contains("FENCE_GATE")) event.setCancelled(true);
                    if(event.getClickedBlock().getType().toString().contains("CAKE")) event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event){
        if(event.getEntity() instanceof Player){
            if(protectedWorlds.contains(event.getEntity().getWorld())){
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onCropBreak(PlayerInteractEvent event)  {
        if(protectedWorlds.contains(event.getPlayer().getWorld())) {
            if (event.getAction() == Action.PHYSICAL) {
                Block block = event.getClickedBlock();
                if (block == null) return;
                if (block.getType() == Material.FARMLAND) {
                    event.setUseInteractedBlock(Event.Result.DENY);
                    event.setCancelled(true);
                    block.setType(block.getType(), true);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerVoid(PlayerMoveEvent event)  {
        if(protectedWorlds.contains(event.getPlayer().getWorld())) {
            if(event.getFrom().getBlockY() != event.getTo().getBlockY()){
                if(event.getTo().getBlockY() <= 0){
                    event.getPlayer().performCommand("spawn");
                }
            }
        }
    }

}
