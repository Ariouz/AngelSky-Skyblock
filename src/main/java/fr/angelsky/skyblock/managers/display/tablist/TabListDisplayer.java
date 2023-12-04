package fr.angelsky.skyblock.managers.display.tablist;

import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI;
import com.bgsoftware.superiorskyblock.api.island.Island;
import fr.angelsky.angelskyapi.api.utils.math.NumbersSeparator;
import fr.angelsky.skyblock.SkyblockInstance;
import fr.angelsky.skyblockapi.accounts.TempPlayer;
import fr.angelsky.skyblockapi.managers.level.PlayerLevel;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class TabListDisplayer {

    private final SkyblockInstance skyblockInstance;

    public TabListDisplayer(SkyblockInstance skyblockInstance){
        this.skyblockInstance = skyblockInstance;
    }

    public void run(){
        new BukkitRunnable() {
            @Override
            public void run() {
                Bukkit.getOnlinePlayers().forEach(TabListDisplayer.this::setTablist);
            }
        }.runTaskTimerAsynchronously(skyblockInstance.getSkyblock(), 20, 20);
    }

    @SuppressWarnings("deprecation")
    public void setTablist(Player player){
        TempPlayer tempPlayer = skyblockInstance.getTempAccounts().get(player.getName());
        if (tempPlayer == null) return;

        Island island = SuperiorSkyblockAPI.getPlayer(player).getIsland();
        String header = "\n\n\n\n\n\n" + PlaceholderAPI.setPlaceholders(player, "%oraxen_angelskylogo%") +"\n\n";

        String footer = skyblockInstance.getManagerLoader().getMessageManager().getColorizedMessage(
                "\n&f&lCompte &8» &cProgression&8: " + (tempPlayer.getPlayerLevel().isMaxRank() ?"&cNiveau Max" : "&c" + tempPlayer.getPlayerLevel().getLevel() + "&7/&4" + PlayerLevel.getMaxLevelPerRank())
                + " &8| &6AngelCoins&8: &e" + NumbersSeparator.LanguageFormatter.USA.convert((int) skyblockInstance.getSkyBlockApiInstance().getEconomy().getBalance(player), 3) + " " + SkyblockInstance.COIN
                + "\n&f&lÎle &8» &eNiveau&8: &7" + (island != null ? island.getIslandLevel().intValue() : "0")
                + " &8| &2Banque&8: &a" + NumbersSeparator.LanguageFormatter.USA.convert((island != null ? island.getIslandBank().getBalance().longValue() : 0), 2) + " " + SkyblockInstance.COIN
                +" &8| &9Membres&8: &3"+ (island != null ? island.getIslandMembers(true).size() : 0) + "&7/&b" + (island != null ? island.getTeamLimit() : 0)
                + "\n\n&fConnecté"+(Bukkit.getOnlinePlayers().size() > 1 ? "s":"")+"&8: &7" + Bukkit.getOnlinePlayers().size() + " &8| &fPing&8: &7" + player.getPing() +"ms"
        );
        player.setPlayerListHeaderFooter(header, footer);
    }

}
