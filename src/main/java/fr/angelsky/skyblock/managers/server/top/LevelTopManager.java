package fr.angelsky.skyblock.managers.server.top;

import fr.angelsky.skyblock.SkyblockInstance;
import fr.angelsky.skyblock.sql.player.level.LevelTopPlayer;
import fr.angelsky.skyblock.sql.player.level.SQLLevelTop;
import org.bukkit.entity.Player;

import java.util.List;

public class LevelTopManager {

    private final SkyblockInstance skyblockInstance;
    private SQLLevelTop sqlLevelTop;

    public LevelTopManager(SkyblockInstance skyblockInstance){
        this.skyblockInstance = skyblockInstance;
    }

    public void init(){
        this.sqlLevelTop = new SQLLevelTop(skyblockInstance);
    }

    // TODO RELOAD EVERY 5 MINS

    public List<LevelTopPlayer> getTop(){
        return sqlLevelTop.getLevelTop();
    }

    public LevelTopPlayer getAtPosition(int position){
        return sqlLevelTop.getLevelTop().size() >= position ? sqlLevelTop.getLevelTop().get(position - 1) : new LevelTopPlayer("Vide", 0, 0, 0, -1);
    }

    public LevelTopPlayer get(Player player){
        return sqlLevelTop.getPlayer(player.getName());
    }

    public SkyblockInstance getSkyblockInstance() {
        return skyblockInstance;
    }

    public SQLLevelTop getSqlLevelTop() {
        return sqlLevelTop;
    }
}
