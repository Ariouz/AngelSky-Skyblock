package fr.angelsky.skyblock.sql.rewards.daily;

import fr.angelsky.angelskyapi.api.AngelSkyApiInstance;
import fr.angelsky.skyblock.SkyblockInstance;

import java.sql.SQLException;
import java.util.UUID;

public class SQLDailyRewards {

    private final AngelSkyApiInstance angelSkyApiInstance;

    private final String TABLE = "skyblock_daily_rewards";

    public SQLDailyRewards(SkyblockInstance skyblockInstance){
        this.angelSkyApiInstance = skyblockInstance.getAngelSkyApiInstance();
    }

    public boolean playerExists(UUID uuid){
        String query = "SELECT * FROM %s WHERE player_uuid = '%s'";
        return (Boolean) angelSkyApiInstance.getApiManager().getSqlManager().getMySQL().query(String.format(query, TABLE, uuid.toString()), resultSet -> {
            try {
                return resultSet.next();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return false;
        });
    }

    public void insertPlayerReward(UUID uuid, long next, long max){
        String query = "INSERT INTO %s (player_uuid, next_reward, next_reward_max) VALUES ('%s', '%s', '%s')";
        angelSkyApiInstance.getApiManager().getSqlManager().getMySQL().update(String.format(query, TABLE, uuid.toString(), next, max));
    }

    public void savePlayerReward(UUID uuid, int rewardLevel, long nextReward, long nextRewardMax){
        String query = "UPDATE %s SET reward_level = '%s', next_reward = '%s', next_reward_max = '%s' WHERE player_uuid = '%s'";
        angelSkyApiInstance.getApiManager().getSqlManager().getMySQL().update(String.format(query, TABLE, rewardLevel, nextReward, nextRewardMax, uuid.toString()));
    }

    public int getRewardLevel(UUID uuid){
        String query = "SELECT * FROM %s WHERE player_uuid = '%s'" ;
        return (int) angelSkyApiInstance.getApiManager().getSqlManager().getMySQL().query(String.format(query, TABLE, uuid.toString()), resultSet -> {
            try{
                if (resultSet.next()){
                    return resultSet.getInt("reward_level");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return 0;
        });
    }

    public long getNextRewardTimestamp(UUID uuid){
        String query = "SELECT * FROM %s WHERE player_uuid = '%s'" ;
        return (long) angelSkyApiInstance.getApiManager().getSqlManager().getMySQL().query(String.format(query, TABLE, uuid.toString()), resultSet -> {
            try{
                if (resultSet.next()){
                    return resultSet.getLong("next_reward");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return 0;
        });
    }

    public long getNextRewardMaxTimestamp(UUID uuid){
        String query = "SELECT * FROM %s WHERE player_uuid = '%s'" ;
        return (long) angelSkyApiInstance.getApiManager().getSqlManager().getMySQL().query(String.format(query, TABLE, uuid.toString()), resultSet -> {
            try{
                if (resultSet.next()){
                    return resultSet.getLong("next_reward_max");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return 0;
        });
    }

}
