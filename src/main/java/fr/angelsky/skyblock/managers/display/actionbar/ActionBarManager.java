package fr.angelsky.skyblock.managers.display.actionbar;

import fr.angelsky.angelskyapi.api.utils.math.NumbersSeparator;
import fr.angelsky.skyblock.SkyblockInstance;
import fr.angelsky.skyblockapi.accounts.TempPlayer;
import fr.angelsky.skyblockapi.managers.level.LevelRankManager;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class ActionBarManager {

    private final SkyblockInstance skyblockInstance;

    private final ArrayList<String> waitForInfinite = new ArrayList<>(); // Make the infinite bar wait if another bar is sent

    public ActionBarManager(SkyblockInstance skyblockInstance){
        this.skyblockInstance = skyblockInstance;
    }

    public String getLevelString(TempPlayer player){
        String rankDisplay;
        if(player.getPlayerLevel().isMaxRank()){
            rankDisplay = LevelRankManager.getMaxRankTag() + " ";
        }else{
            rankDisplay = player.getPlayerLevel().getLevelRankObject().getDisplay() + "[" + player.getPlayerLevel().getLevelColor() + player.getPlayerLevel().getLevel() +
                    player.getPlayerLevel().getLevelRankObject().getDisplay() + "] ";
        }
        return ChatColor.GRAY + "Niveau " + rankDisplay + ChatColor.DARK_GRAY + "- " + (player.getPlayerLevel().isMaxRank() ? ChatColor.RED + "Niveau Max": ChatColor.GRAY + NumbersSeparator.LanguageFormatter.USA.convert((int) player.getPlayerLevel().getXp(), 3) +
                ChatColor.DARK_GRAY + "/" + ChatColor.GRAY + NumbersSeparator.LanguageFormatter.USA.convert(player.getPlayerLevel().getNeededXpForLevel(), 3) + " Xp");
    }

    public void sendInfiniteActionBarAll(){
        /*new BukkitRunnable() {
            @Override
            public void run() {
                for(Player player : Bukkit.getOnlinePlayers()){
                    if(player == null) continue;
                    TempPlayer tempPlayer = skyblockInstance.getTempAccounts().get(player.getName());
                    if(tempPlayer == null) continue;
                    if(!player.isOnline()) continue;
                    if(waitForInfinite.contains(player.getName())) continue;
                    player.sendActionBar(Component.text(ChatColor.translateAlternateColorCodes('&', getLevelString(tempPlayer))));
                }
            }
        }.runTaskTimerAsynchronously(skyblockInstance.getSkyblock(), 0, 20);*/
    }

    public void sendActionBar(Player player, String message, int duration){
        if(!waitForInfinite.contains(player.getName())) ActionBarManager.this.waitForInfinite.add(player.getName());
        new BukkitRunnable() {
            int time = 0;
            @Override
            public void run() {
                if(time >= duration){
                    this.cancel();
                    ActionBarManager.this.waitForInfinite.remove(player.getName());
                }
                player.sendActionBar(Component.text(ChatColor.translateAlternateColorCodes('&', message)));
                time++;
            }
        }.runTaskTimer(skyblockInstance.getSkyblock(), 0, 20);
    }

}
