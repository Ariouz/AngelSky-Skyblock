package fr.angelsky.skyblock.listeners.player.level;

import fr.angelsky.skyblock.SkyblockInstance;
import fr.angelsky.skyblockapi.accounts.TempPlayer;
import fr.angelsky.skyblockapi.events.player.PlayerNextRankEvent;
import fr.angelsky.skyblockapi.managers.level.PlayerLevel;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerPassRankListener implements Listener {

    private final SkyblockInstance skyblockInstance;

    public PlayerPassRankListener(SkyblockInstance skyblockInstance){
        this.skyblockInstance = skyblockInstance;
    }

    @EventHandler
    public void onPassRank(PlayerNextRankEvent event){
        TempPlayer tempPlayer = event.getTempPlayer();
        tempPlayer.getPlayerLevel().refreshLevels(skyblockInstance.getManagerLoader().getLevelManager().getLevelRankManager(), skyblockInstance.getManagerLoader().getLevelManager().getLevelColor());

        skyblockInstance.getManagerLoader().getActionBarManager().sendActionBar(tempPlayer.getPlayer(), ChatColor.GOLD + "Nouveau palier !", 5);
        tempPlayer.getPlayer().playSound(tempPlayer.getPlayer().getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 30, 0);

        // TODO RANK REWARDS

        if(event.getTempPlayer().getPlayerLevel().isMaxRank()){
            // TODO MAX RANK (DIEU) ACTIONS, REWARDS, ...
            Bukkit.broadcast(Component.text(tempPlayer.getPlayer().displayName() + " est un Dieu"));
        }
    }

}
