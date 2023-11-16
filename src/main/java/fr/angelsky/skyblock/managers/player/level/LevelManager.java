package fr.angelsky.skyblock.managers.player.level;

import fr.angelsky.angelskyapi.api.utils.file.ConfigUtils;
import fr.angelsky.skyblock.SkyblockInstance;
import fr.angelsky.skyblockapi.managers.level.LevelColor;
import fr.angelsky.skyblockapi.managers.level.LevelRankManager;

public class LevelManager {

    private final SkyblockInstance skyblockInstance;

    private LevelRankManager levelRankManager;
    private LevelColor levelColor;

    public LevelManager(SkyblockInstance skyblockInstance) {
        this.skyblockInstance = skyblockInstance;
        init();
    }

    public void init(){
        this.levelRankManager = new LevelRankManager();
        this.levelColor = new LevelColor(new ConfigUtils(skyblockInstance.getSkyblock(), "levels_colors.yml"));
    }

    public void load(){
        this.levelRankManager.loadRanks(new ConfigUtils(skyblockInstance.getSkyblock(), "level_ranks.yml"));
        this.levelColor.loadColors();
    }

    public LevelRankManager getLevelRankManager() {
        return levelRankManager;
    }

    public LevelColor getLevelColor() {
        return levelColor;
    }
}
