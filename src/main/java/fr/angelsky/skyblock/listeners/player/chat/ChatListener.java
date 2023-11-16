package fr.angelsky.skyblock.listeners.player.chat;

import fr.angelsky.angelskyapi.api.accounts.TempPlayerAccount;
import fr.angelsky.angelskyapi.api.enums.rank.Rank;
import fr.angelsky.skyblock.SkyblockInstance;
import fr.angelsky.skyblockapi.accounts.TempPlayer;
import fr.angelsky.skyblockapi.managers.level.LevelRankManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    private final SkyblockInstance skyblockInstance;

    public ChatListener(SkyblockInstance skyblockInstance){
        this.skyblockInstance = skyblockInstance;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event){
        Player player = event.getPlayer();
        String message = event.getMessage();
        TempPlayer tempPlayer = skyblockInstance.getTempAccounts().get(player.getName());
        TempPlayerAccount playerAccount = skyblockInstance.getAngelSkyApiInstance().getApiManager().getAccountManager().getAccount(player.getUniqueId());


        String rankDisplay;
        if(tempPlayer.getPlayerLevel().isMaxRank()){
            rankDisplay = LevelRankManager.getMaxRankTag() + " ";
        }else{
            rankDisplay = tempPlayer.getPlayerLevel().getLevelRankObject().getDisplay() + "[" + tempPlayer.getPlayerLevel().getLevelColor() + tempPlayer.getPlayerLevel().getLevel() +
                    tempPlayer.getPlayerLevel().getLevelRankObject().getDisplay() + "] ";
        }
        event.setFormat(rankDisplay + (playerAccount.getRank() != Rank.PLAYER ? playerAccount.getRank().getDisplay() : ChatColor.GRAY) + "" + player.getName() + ChatColor.GRAY + " » " + (playerAccount.getRank().getPower() >= Rank.RESPONSIBLE.getPower() ? ChatColor.translateAlternateColorCodes('&', playerAccount.getRank().getChatMessageColor() + message) : playerAccount.getRank().getChatMessageColor() + message));

    }

}
