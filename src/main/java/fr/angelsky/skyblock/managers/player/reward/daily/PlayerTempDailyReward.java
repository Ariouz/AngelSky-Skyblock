package fr.angelsky.skyblock.managers.player.reward.daily;

import org.bukkit.Bukkit;

import java.util.Objects;
import java.util.UUID;

public class PlayerTempDailyReward {

    private final UUID uuid;
    private final String playerName;
    private int rewardLevel;
    private long nextReward;
    private long nextRewardMax;

    public PlayerTempDailyReward(UUID uuid, int rewardLevel, long nextReward, long nextRewardMax){
        this.uuid = uuid;
        this.playerName = Objects.requireNonNull(Bukkit.getPlayer(uuid)).getName();
        this.rewardLevel = rewardLevel;
        this.nextReward = nextReward;
        this.nextRewardMax = nextRewardMax;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getRewardLevel() {
        return rewardLevel;
    }

    public long getNextReward() {
        return nextReward;
    }

    public long getNextRewardMax() {
        return nextRewardMax;
    }

    public void setRewardLevel(int rewardLevel) {
        this.rewardLevel = rewardLevel;
    }

    public void setNextReward(long nextReward) {
        this.nextReward = nextReward;
    }

    public void setNextRewardMax(long nextRewardMax) {
        this.nextRewardMax = nextRewardMax;
    }
}
