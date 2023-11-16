package fr.angelsky.skyblock.listeners.server;

import fr.angelsky.skyblock.SkyblockInstance;
import fr.angelsky.skyblockapi.accounts.TempPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    private final SkyblockInstance skyblockInstance;

    public PlayerQuitListener(SkyblockInstance skyblockInstance){
        this.skyblockInstance = skyblockInstance;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();
        TempPlayer tempPlayer = skyblockInstance.getTempAccounts().get(player.getName());
        tempPlayer.saveAccount();
        skyblockInstance.getManagerLoader().getScoreboardManager().removePlayer(player);
        skyblockInstance.getTempAccounts().remove(player.getName());
    }

}
