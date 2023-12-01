package fr.angelsky.skyblock.sql.player.level;

import fr.angelsky.skyblock.SkyblockInstance;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQLLevelTop {

    private final SkyblockInstance skyblockInstance;

    public SQLLevelTop(SkyblockInstance skyblockInstance){
        this.skyblockInstance = skyblockInstance;
    }

    public List<LevelTopPlayer> getLevelTop(){
        List<LevelTopPlayer> top = new ArrayList<>();
        String query = "SELECT * FROM skyblock_accounts ORDER BY levelRank DESC, level DESC, xp DESC LIMIT 10";
        skyblockInstance.getAngelSkyApiInstance().getApiManager().getSqlManager().getMySQL().query(query, resultSet -> {
            int i = 0;
            while (true){
                try {
                    if (!resultSet.next()) break;
                    i++;
                    LevelTopPlayer entry = new LevelTopPlayer(
                            resultSet.getString("player_name"),
                            resultSet.getInt("xp"),
                            resultSet.getInt("level"),
                            resultSet.getInt("levelRank"),
                            i
                    );
                    top.add(entry);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        return top;
    }

    public LevelTopPlayer getPlayer(String playerName){
        String query = "WITH PLAYER_POSITION AS " +
                "(" +
                "SELECT *, ROW_NUMBER() OVER(ORDER BY levelRank DESC, level DESC, xp DESC) as position FROM skyblock_accounts" +
                ")" +
                "SELECT * FROM PLAYER_POSITION WHERE player_name = '%s';";
        return (LevelTopPlayer) skyblockInstance.getAngelSkyApiInstance().getApiManager().getSqlManager().getMySQL().query(String.format(query, playerName), resultSet -> {
            try {
                if (resultSet.next()){
                    return new LevelTopPlayer(
                            playerName,
                            resultSet.getInt("xp"),
                            resultSet.getInt("level"),
                            resultSet.getInt("levelRank"),
                            resultSet.getInt("position")
                    );
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return null;
        });
    }

}
