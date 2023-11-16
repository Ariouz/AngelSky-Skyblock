package fr.angelsky.skyblock.managers.display.scoreboard;

import fr.angelsky.angelskyapi.api.accounts.TempPlayerAccount;
import fr.angelsky.angelskyapi.api.enums.rank.Rank;
import fr.angelsky.angelskyapi.api.utils.math.NumbersSeparator;
import fr.angelsky.angelskyapi.fastboard.FastBoard;
import fr.angelsky.skyblock.SkyblockInstance;
import fr.angelsky.skyblock.managers.utils.voteparty.VoteParty;
import fr.angelsky.skyblockapi.accounts.TempPlayer;
import fr.angelsky.skyblockapi.managers.level.LevelRankManager;
import fr.angelsky.skyblockapi.managers.level.PlayerLevel;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class ScoreboardManager {

    private final SkyblockInstance skyblockInstance;

    private final HashMap<UUID, FastBoard> boards = new HashMap<>();

    public ScoreboardManager(SkyblockInstance skyblockInstance){
        this.skyblockInstance = skyblockInstance;
        run();
    }

    private void run(){
        new BukkitRunnable() {
            @Override
            public void run() {
                refreshAll();
            }
        }.runTaskTimerAsynchronously(skyblockInstance.getSkyblock(), 0, 20);
    }

    public List<String> getLines(Player player){
        List<String> lines = new ArrayList<>();
        TempPlayerAccount playerAccount = skyblockInstance.getAngelSkyApiInstance().getApiManager().getAccountManager().getAccount(player.getUniqueId());
        Rank rank = playerAccount.getRank();
        TempPlayer tempPlayer = skyblockInstance.getTempAccounts().get(player.getName());
        VoteParty voteParty = skyblockInstance.getManagerLoader().getVotePartyManager().getVoteParty();
        String playerLevel;

        if(tempPlayer.getPlayerLevel().isMaxRank()){
            playerLevel = LevelRankManager.getMaxRankTag();
        }
        else {
            playerLevel = tempPlayer.getPlayerLevel().getLevelRankObject().getDisplay() + "[" + tempPlayer.getPlayerLevel().getLevelColor() + tempPlayer.getPlayerLevel().getLevel() +
                    tempPlayer.getPlayerLevel().getLevelRankObject().getDisplay() + "]";
        }
        lines.add("");
        lines.add(ChatColor.WHITE + "" + ChatColor.BOLD + "Compte"+ChatColor.GRAY+":");
        lines.add(ChatColor.YELLOW + "" + "  »  "+ChatColor.WHITE+" Grade"+ChatColor.GRAY+": " + rank.getDisplay());
        lines.add(ChatColor.YELLOW + "" + "  »  "+ChatColor.WHITE+" Compte"+ChatColor.GRAY+": " + ChatColor.GRAY + player.getName());
        lines.add(ChatColor.YELLOW + "" + "  »  "+ChatColor.WHITE+" AngelCoins"+ChatColor.GRAY+": " + ChatColor.GRAY + NumbersSeparator.LanguageFormatter.USA.convert((int) skyblockInstance.getSkyBlockApiInstance().getEconomy().getBalance(player), 3) + " " + SkyblockInstance.COIN);
        lines.add(ChatColor.YELLOW + "" + "  »  "+ChatColor.WHITE+" Progression"+ChatColor.GRAY+": " + (tempPlayer.getPlayerLevel().isMaxRank() ? ChatColor.RED + "Niveau Max" : tempPlayer.getPlayerLevel().getLevel() + "" + ChatColor.WHITE + "/" + ChatColor.GRAY + PlayerLevel.getMaxLevelPerRank()));
        lines.add("       " + playerLevel + ChatColor.GRAY + " » " + (tempPlayer.getPlayerLevel().isMaxRank() ? ChatColor.RED + "Niveau Max" : tempPlayer.getBarProgress(30, '|', ChatColor.GREEN, ChatColor.RED)));
        lines.add("");
        lines.add(ChatColor.WHITE + "" + ChatColor.BOLD + "Île"+ChatColor.GRAY+":");
        lines.add(ChatColor.YELLOW + "" + "  »  "+ChatColor.WHITE+" Niveau"+ChatColor.GRAY+": " + (tempPlayer.getIsland() == null ? ChatColor.RED + "✘" : tempPlayer.getIsland().getIslandLevel().intValue()));
        lines.add(ChatColor.YELLOW + "" + "  »  "+ChatColor.WHITE+" Banque d'île"+ChatColor.GRAY+": " + (tempPlayer.getIsland() == null ? ChatColor.RED + "✘" : tempPlayer.getIsland().getIslandBank().getBalance().intValue()));
        lines.add("");
        lines.add(ChatColor.GOLD + "" + ChatColor.BOLD + "VoteParty"+ChatColor.GRAY+": " + ChatColor.YELLOW + voteParty.getCurrentVotes() +ChatColor.WHITE+"/"+ChatColor.GOLD+voteParty.getRequiredVotes());
        lines.add("");
        lines.add(ChatColor.GRAY + "       play.angelsky.fr");

        return lines;
    }

    public void addPlayer(Player player){
        FastBoard board = new FastBoard(player);
        board.updateTitle(PlaceholderAPI.setPlaceholders(player, "%oraxen_angelskylogo%"));
        this.boards.put(player.getUniqueId(), board);
    }

    public void removePlayer(Player player){
        this.boards.remove(player.getUniqueId());
    }

    public void refreshAll(){
        for (FastBoard board : boards.values()) {
            board.updateLines(getLines(board.getPlayer()));
        }
    }

}
