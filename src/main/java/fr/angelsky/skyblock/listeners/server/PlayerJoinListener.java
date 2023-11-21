package fr.angelsky.skyblock.listeners.server;

import fr.angelsky.skyblock.SkyblockInstance;
import fr.angelsky.skyblock.managers.player.level.reward.daily.DailyRewardManager;
import fr.angelsky.skyblockapi.accounts.TempPlayer;
import org.bukkit.*;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;

public class PlayerJoinListener implements Listener {

    private final SkyblockInstance skyblockInstance;

    public PlayerJoinListener(SkyblockInstance skyblockInstance){
        this.skyblockInstance = skyblockInstance;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();

        if(!skyblockInstance.getSkyBlockApiInstance().getManagerLoader().getSqlManager().getSkyblockAccount().accountExists(player.getUniqueId())){
            skyblockInstance.getSkyBlockApiInstance().getManagerLoader().getSqlManager().getSkyblockAccount().createAccount(player.getUniqueId(), player.getName(), 0);


            Firework firework = Objects.requireNonNull(Bukkit.getWorld(player.getWorld().getName())).spawn(player.getLocation(), Firework.class);
            FireworkMeta fireworkMeta = firework.getFireworkMeta();

            fireworkMeta.addEffect(FireworkEffect.builder()
                    .withColor(Color.RED, Color.WHITE, Color.ORANGE, Color.GRAY, Color.YELLOW)
                    .withFade(Color.RED, Color.WHITE, Color.ORANGE, Color.GRAY, Color.YELLOW)
                    .with(FireworkEffect.Type.BALL_LARGE)
                    .withFlicker()
                    .build());
            fireworkMeta.setPower(1);

            firework.setFireworkMeta(fireworkMeta);

            for(Player pls : Bukkit.getOnlinePlayers()){
                skyblockInstance.getManagerLoader().getMessageManager().getColorizedMessageList("first_join").forEach(l -> {
                    pls.sendMessage(skyblockInstance.getAngelSkyApiInstance().translateChatColorHex(ChatColor.translateAlternateColorCodes('&', l.replaceAll("%player%", player.getName()))));
                });
            }
        }

        new BukkitRunnable(){
            @Override
            public void run(){
                TempPlayer tempPlayer = new TempPlayer(skyblockInstance.getSkyBlockApiInstance(), player.getUniqueId(), player.getName());
                tempPlayer.getPlayerLevel().refreshLevels(skyblockInstance.getManagerLoader().getLevelManager().getLevelRankManager(), skyblockInstance.getManagerLoader().getLevelManager().getLevelColor());

                skyblockInstance.getTempAccounts().put(player.getName(), tempPlayer);
                skyblockInstance.getManagerLoader().getScoreboardManager().addPlayer(player);
            }
        }.runTaskLaterAsynchronously(skyblockInstance.getSkyblock(), 10L);

        player.teleport(new Location(Bukkit.getWorld("world"), -0.5, 87, -0.5, -180f, 0f));

        DailyRewardManager dailyRewardManager = skyblockInstance.getManagerLoader().getDailyRewardManager();
        if (dailyRewardManager.getSqlDailyRewards().playerExists(player.getUniqueId())){
            dailyRewardManager.getSqlDailyRewards().insertPlayerReward(player.getUniqueId());
            dailyRewardManager.loadPlayer(player.getUniqueId());
        }

    }

}
