package fr.angelsky.skyblock.listeners.island;

import com.bgsoftware.superiorskyblock.api.events.IslandDisbandEvent;
import com.bgsoftware.superiorskyblock.api.wrappers.SuperiorPlayer;
import fr.angelsky.angelskyapi.api.managers.luckperms.LuckPermsIntegrationManager;
import fr.angelsky.skyblock.SkyblockInstance;
import fr.angelsky.skyblock.managers.player.level.reward.LevelReward;
import fr.angelsky.skyblock.managers.player.level.reward.LevelRewardType;
import fr.angelsky.skyblockapi.accounts.TempPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Objects;

public class IslandDisbandListener implements Listener {

    private final SkyblockInstance skyblockInstance;

    public IslandDisbandListener(SkyblockInstance skyblockInstance){
        this.skyblockInstance = skyblockInstance;
    }

    @EventHandler
    public void onDisband(IslandDisbandEvent event){
        SuperiorPlayer player = event.getPlayer();

        Objects.requireNonNull(player.asPlayer()).teleport(new Location(Bukkit.getWorld("world"), -0.5, 87, -0.5, -180f, 0f));
        Objects.requireNonNull(player.asPlayer()).getEnderChest().clear();
        Objects.requireNonNull(player.asPlayer()).getInventory().clear();

        TempPlayer tempPlayer = skyblockInstance.getTempAccounts().get(Objects.requireNonNull(event.getPlayer().asPlayer()).getName());
        skyblockInstance.getSkyBlockApiInstance().getEconomy().withdrawPlayer(tempPlayer.getPlayer(), skyblockInstance.getSkyBlockApiInstance().getEconomy().getBalance(tempPlayer.getPlayer().getName()));
        tempPlayer.getPlayerLevel().setXp(0);
        tempPlayer.getPlayerLevel().setLevel(0);
        tempPlayer.getPlayerLevel().setLevelRank(0);
        tempPlayer.saveAccount();

        LuckPermsIntegrationManager luckPermsIntegrationManager = skyblockInstance.getAngelSkyApiInstance().getApiManager().getLuckPermsIntegrationManager();
        for (LevelReward levelReward : skyblockInstance.getManagerLoader().getLevelManager().getLevelRewardManager().getLevelRewards())
        {
            if (levelReward.getType() != LevelRewardType.PERMISSION) continue;
            luckPermsIntegrationManager.removePermission(player.getUniqueId(), levelReward.getData());
        }
        luckPermsIntegrationManager.removePermission(player.getUniqueId(), "angelsky.coalitions.access");
    }

}
