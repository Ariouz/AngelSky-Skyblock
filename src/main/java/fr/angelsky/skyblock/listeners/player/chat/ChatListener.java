package fr.angelsky.skyblock.listeners.player.chat;

import fr.angelsky.angelskyapi.api.accounts.TempPlayerAccount;
import fr.angelsky.angelskyapi.api.enums.rank.Rank;
import fr.angelsky.angelskycoalitions.coalition.Coalition;
import fr.angelsky.angelskycoalitions.coalition.CoalitionType;
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
        String message = event.getMessage().replaceAll("%", "%%");
        TempPlayer tempPlayer = skyblockInstance.getTempAccounts().get(player.getName());
        TempPlayerAccount playerAccount = skyblockInstance.getAngelSkyApiInstance().getApiManager().getAccountManager().getAccount(player.getUniqueId());
        Coalition coalition = skyblockInstance.getAngelSkyCoalitions().getManagerLoader().getCoalitionManager().getCoalitionPlayer(player).getCoalition();

        String rankDisplay;
        if(tempPlayer.getPlayerLevel().isMaxRank()){
            rankDisplay = LevelRankManager.getMaxRankTag() + " ";
        }else{
            rankDisplay = tempPlayer.getPlayerLevel().getLevelRankObject().getDisplay() + "[" + tempPlayer.getPlayerLevel().getLevelColor() + tempPlayer.getPlayerLevel().getLevel() +
                    tempPlayer.getPlayerLevel().getLevelRankObject().getDisplay() + "] ";
        }

        event.setFormat((coalition.getCoalitionType() == CoalitionType.NONE ? "" : ChatColor.WHITE + coalition.getOraxenBannerChar() + " ") +
                rankDisplay +
                (playerAccount.getRank() != Rank.PLAYER ? playerAccount.getRank().getDisplay() : ChatColor.GRAY)
                + player.getName() + ChatColor.GRAY + " » " +
                (playerAccount.getRank().getPower() >= Rank.RESPONSIBLE.getPower() ? ChatColor.translateAlternateColorCodes('&', playerAccount.getRank().getChatMessageColor() + message) : playerAccount.getRank().getChatMessageColor()
                        + message));

    }

}
