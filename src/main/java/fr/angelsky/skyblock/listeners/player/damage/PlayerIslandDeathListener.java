package fr.angelsky.skyblock.listeners.player.damage;

import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI;
import fr.angelsky.skyblock.SkyblockInstance;
import fr.angelsky.skyblockapi.accounts.TempPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerIslandDeathListener implements Listener {

    private final SkyblockInstance skyblockInstance;

    public PlayerIslandDeathListener(SkyblockInstance skyblockInstance){
        this.skyblockInstance = skyblockInstance;
    }

    @EventHandler
    public void onPlayerIslandDieEvent(PlayerDeathEvent event){
        Player player = event.getPlayer();
        TempPlayer tempPlayer = skyblockInstance.getTempAccounts().get(player.getName());
        if (SuperiorSkyblockAPI.getIslandAt(player.getLocation()) == tempPlayer.getSuperiorPlayer().getIsland()){
            if (player.hasPermission("angelsky.death.keep.inventory")) {
                event.setKeepInventory(true);
                event.getDrops().clear();
            }
            if (player.hasPermission("angelsky.death.keep.experience")) {
                event.setKeepLevel(true);
                event.setShouldDropExperience(false);
            }
        }
    }

}
