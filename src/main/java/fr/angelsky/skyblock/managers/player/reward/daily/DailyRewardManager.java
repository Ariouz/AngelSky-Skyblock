package fr.angelsky.skyblock.managers.player.reward.daily;

import fr.angelsky.angelskyapi.api.utils.HexColors;
import fr.angelsky.angelskyapi.api.utils.file.ConfigUtils;
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

    private final MessageManager messageManager;
    private final SkyblockInstance skyblockInstance;
    private final HashMap<UUID, PlayerTempDailyReward> rewardPlayers = new HashMap<>();
    private final HashMap<Integer, DailyReward> rewards = new HashMap<>();

    public DailyRewardManager(SkyblockInstance skyblockInstance, MessageManager messageManager){
        this.skyblockInstance = skyblockInstance;
        this.messageManager = messageManager;
        this.sqlDailyRewards = new SQLDailyRewards(skyblockInstance);
        loadRewards();
        init();
    }

    private void loadRewards(){
        ConfigUtils config = new ConfigUtils(skyblockInstance.getSkyblock(), "daily_rewards.yml");
        for(String dayId : config.getYamlConfiguration().getKeys(false)){
            this.rewards.put(Integer.parseInt(dayId), new DailyReward(
                    Integer.parseInt(dayId),
                    config.getString(dayId+".display"),
                    config.getBoolean(dayId+".need_inventory_slot"),
                    config.getList(dayId+".commands"),
                    config.getBoolean(dayId+".message")
            ));
        }
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
        if (!sqlDailyRewards.playerExists(uuid)){
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(new Date().getTime());
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            sqlDailyRewards.insertPlayerReward(uuid, calendar.getTimeInMillis(), calculateMaxTime(calendar.getTimeInMillis()));
        }
        this.rewardPlayers.put(uuid, new PlayerTempDailyReward(
                uuid,
                sqlDailyRewards.getRewardLevel(uuid),
                sqlDailyRewards.getNextRewardTimestamp(uuid),
                sqlDailyRewards.getNextRewardMaxTimestamp(uuid)
        ));
        checkMaxTime(uuid);
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

    public boolean checkMaxTime(UUID uuid){
        PlayerTempDailyReward playerTempDailyReward = this.rewardPlayers.get(uuid);
        if (playerTempDailyReward.getNextRewardMax() < new Date().getTime()){
            updatePlayerTemp(playerTempDailyReward, 0);
            return false;
        }
        return true;
    }

    public void updatePlayerTemp(PlayerTempDailyReward playerTempDailyReward, int rewardLevel){
        if (rewardLevel >= this.rewards.size()) rewardLevel = 0;
        playerTempDailyReward.setRewardLevel(rewardLevel);
        playerTempDailyReward.setNextReward(calculateNextTime());
        playerTempDailyReward.setNextRewardMax(calculateMaxTime(playerTempDailyReward.getNextReward()));
    }

    private long calculateNextTime(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.DAY_OF_WEEK, 1);
        return calendar.getTimeInMillis();
    }

    private long calculateMaxTime(long start){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(start);
        calendar.add(Calendar.HOUR_OF_DAY, 24);
        return calendar.getTimeInMillis();
    }

    public PlayerTempDailyReward getPlayerTempDailyReward(Player player){
        return this.rewardPlayers.get(player.getUniqueId());
    }

    public HashMap<Integer, DailyReward> getRewards() {
        return rewards;
    }
}
