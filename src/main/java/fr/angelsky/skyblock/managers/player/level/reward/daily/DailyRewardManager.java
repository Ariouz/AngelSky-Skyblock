package fr.angelsky.skyblock.managers.player.level.reward.daily;

import fr.angelsky.angelskyapi.api.utils.HexColors;
import fr.angelsky.skyblock.SkyblockInstance;
import fr.angelsky.skyblock.managers.utils.messages.MessageManager;
import fr.angelsky.skyblock.sql.rewards.daily.SQLDailyRewards;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

public class DailyRewardManager {

    private final SQLDailyRewards sqlDailyRewards;

    private final SkyblockInstance skyblockInstance;

    private final MessageManager messageManager;
    private final HashMap<UUID, PlayerTempDailyReward> rewardPlayers = new HashMap<>();

    public DailyRewardManager(SkyblockInstance skyblockInstance, MessageManager messageManager){
        this.skyblockInstance = skyblockInstance;
        this.messageManager = messageManager;
        this.sqlDailyRewards = new SQLDailyRewards(skyblockInstance);
        init();
    }

    private void init(){
        Bukkit.getOnlinePlayers().forEach(player -> {
            loadPlayer(player.getUniqueId());
        });
    }

    public void unloadAll(){
        new HashMap<>(rewardPlayers).forEach((uuid, tmp) -> {
            unloadPlayer(uuid);
        });
    }

    public void loadPlayer(UUID uuid){
        if (!sqlDailyRewards.playerExists(uuid)) sqlDailyRewards.insertPlayerReward(uuid);
        this.rewardPlayers.put(uuid, new PlayerTempDailyReward(
                uuid,
                sqlDailyRewards.getRewardLevel(uuid),
                sqlDailyRewards.getNextRewardTimestamp(uuid),
                sqlDailyRewards.getNextRewardMaxTimestamp(uuid)
        ));
        checkForReward(uuid);
    }

    public void checkForReward(UUID uuid){
        Player player = Bukkit.getPlayer(uuid);
        assert player != null : "Le joueur n'est pas connecté ("+uuid+")";
        if (canGetReward(uuid)){
            player.sendMessage(messageManager.getColorizedMessage(SkyblockInstance.PREFIX + "Une "+ HexColors.LIGHT_GREEN +"récompense journalière &fest disponible! Vous pouvez la récupérer avec la commande &6/daily&f."));
        }
    }

    public void unloadPlayer(UUID uuid){
        PlayerTempDailyReward tmp = this.rewardPlayers.get(uuid);
        sqlDailyRewards.savePlayerReward(uuid, tmp.getRewardLevel(), tmp.getNextReward(), tmp.getNextRewardMax());
        this.rewardPlayers.remove(uuid);
    }

    public boolean canGetReward(UUID uuid){
        PlayerTempDailyReward playerTempDailyReward = this.rewardPlayers.get(uuid);
        long time = new Date().getTime();
        if (playerTempDailyReward.getNextReward() == 0) return true;
        return time >= playerTempDailyReward.getNextReward() && time <= playerTempDailyReward.getNextRewardMax();
    }

    public void checkMaxTime(UUID uuid){
        PlayerTempDailyReward playerTempDailyReward = this.rewardPlayers.get(uuid);
        if (playerTempDailyReward.getNextRewardMax() < new Date().getTime()) updatePlayerTemp(playerTempDailyReward, 0);
    }

    public void updatePlayerTemp(PlayerTempDailyReward playerTempDailyReward, int rewardLevel){
        playerTempDailyReward.setRewardLevel(rewardLevel);
        playerTempDailyReward.setNextReward(calculateNextTime());
        playerTempDailyReward.setNextRewardMax(playerTempDailyReward.getNextReward());
    }

    private long calculateNextTime(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.HOUR_OF_DAY, 24);
        return calendar.getTimeInMillis();
    }

    private long calculateMaxTime(long start){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(start);
        calendar.add(Calendar.HOUR_OF_DAY, 24);
        return calendar.getTimeInMillis();
    }

    public HashMap<UUID, PlayerTempDailyReward> getRewardPlayers() {
        return rewardPlayers;
    }

    public SQLDailyRewards getSqlDailyRewards() {
        return sqlDailyRewards;
    }
}
