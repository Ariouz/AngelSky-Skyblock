package fr.angelsky.skyblock.listeners.player.level;

import fr.angelsky.angelskyapi.api.utils.math.NumbersSeparator;
import fr.angelsky.skyblock.SkyblockInstance;
import fr.angelsky.skyblockapi.accounts.TempPlayer;
import fr.angelsky.skyblockapi.events.player.PlayerNextLevelEvent;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerPassLevelListener implements Listener {

    private final SkyblockInstance skyblockInstance;

    public PlayerPassLevelListener(SkyblockInstance skyblockInstance){
        this.skyblockInstance = skyblockInstance;
    }

    @EventHandler
    public void onPassLevel(PlayerNextLevelEvent event){
        TempPlayer tempPlayer = event.getTempPlayer();
        tempPlayer.getPlayerLevel().refreshLevels(skyblockInstance.getManagerLoader().getLevelManager().getLevelRankManager(), skyblockInstance.getManagerLoader().getLevelManager().getLevelColor());

        skyblockInstance.getManagerLoader().getActionBarManager().sendActionBar(tempPlayer.getPlayer(), ChatColor.GOLD + "Nouveau Niveau !", 5);
        tempPlayer.getPlayer().playSound(tempPlayer.getPlayer().getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 30, 0);

        // MONEY GIVE CALCUL
        // ((level^1.2) * rank) + 0.7 * (level*500)
        int level = event.getNewLevel();
        int rank = tempPlayer.getPlayerLevel().getLevelRank();
        int money = (int) Math.round((Math.pow(level, 1.2) * rank) + 0.7 * (level*500));
        skyblockInstance.getSkyBlockApiInstance().getEconomy().depositPlayer(tempPlayer.getPlayer(), money);
        tempPlayer.getPlayer().sendMessage(SkyblockInstance.PREFIX + "Vous avez reçu " + ChatColor.YELLOW + NumbersSeparator.LanguageFormatter.USA.convert(money, 3) +" " + SkyblockInstance.COIN);
    }

}
