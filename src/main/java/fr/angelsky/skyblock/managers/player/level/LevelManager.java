package fr.angelsky.skyblock.managers.player.level;

import fr.angelsky.angelskyapi.api.utils.file.ConfigUtils;
import fr.angelsky.skyblock.SkyblockInstance;
import fr.angelsky.skyblock.managers.player.level.reward.LevelRewardManager;
import fr.angelsky.skyblockapi.managers.level.LevelColor;
import fr.angelsky.skyblockapi.managers.level.LevelRankManager;

public class LevelManager {

    private final SkyblockInstance skyblockInstance;

    private LevelRankManager levelRankManager;
    private LevelColor levelColor;
    private LevelRewardManager levelRewardManager;

    public LevelManager(SkyblockInstance skyblockInstance) {
        this.skyblockInstance = skyblockInstance;
        init();
    }

    public void init(){
        this.levelRankManager = new LevelRankManager();
        this.levelColor = new LevelColor(new ConfigUtils(skyblockInstance.getSkyblock(), "levels_colors.yml"));
        this.levelRewardManager = new LevelRewardManager(skyblockInstance);
    }

    public void load(){
        this.levelRankManager.loadRanks(new ConfigUtils(skyblockInstance.getSkyblock(), "level_ranks.yml"));
        this.levelColor.loadColors();
        this.levelRewardManager.loadRewards();
    }

    public LevelRewardManager getLevelRewardManager() {
        return levelRewardManager;
    }

    public LevelRankManager getLevelRankManager() {
        return levelRankManager;
    }

    public LevelColor getLevelColor() {
        return levelColor;
    }
}
