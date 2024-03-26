package fr.angelsky.skyblock.managers.player.level.reward;

import fr.angelsky.angelskyapi.api.utils.file.ConfigUtils;
import fr.angelsky.skyblock.SkyblockInstance;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.Objects;

public class LevelRewardManager {

    private final ConfigUtils rewardConfig;
    private final SkyblockInstance skyblockInstance;

    private final ArrayList<LevelReward> levelRewards = new ArrayList<>();

    public LevelRewardManager(SkyblockInstance skyblockInstance)
    {
        this.skyblockInstance = skyblockInstance;
        this.rewardConfig = new ConfigUtils(skyblockInstance.getSkyblock(), "levels_rewards.yml");
    }

    public void loadRewards()
    {
        for(String rank : this.rewardConfig.getYamlConfiguration().getKeys(false))
        {
            ConfigurationSection rankSection = this.rewardConfig.getYamlConfiguration().getConfigurationSection(rank);
            if (rankSection == null) continue;
            for(String level : rankSection.getKeys(false))
            {
                ConfigurationSection levelSection = rankSection.getConfigurationSection(level);
                if (levelSection == null) continue;
                LevelReward levelReward = new LevelReward(
                        Integer.parseInt(rank),
                        Integer.parseInt(level),
                        LevelRewardType.getType(levelSection.getString("type")),
                        Material.getMaterial(levelSection.getString("material") == null ? "" : Objects.requireNonNull(levelSection.getString("material"))),
                        levelSection.getString("oraxen_item"),
                        levelSection.getInt("amount"),
                        levelSection.getString("data"),
                        levelSection.getString("message"),
                        levelSection.getString("display"),
                        Material.getMaterial(Objects.requireNonNull(levelSection.getString("icon")))
                        );
                this.levelRewards.add(levelReward);
            }
        }
    }

    public LevelReward getReward(int rank, int level)
    {
        for (LevelReward reward : getLevelRewards())
        {
            if (reward.getLevel() == level && reward.getRank() == rank)
                return reward;
        }
        return null;
    }

    public ArrayList<LevelReward> getLevelRewards() {
        return levelRewards;
    }
}
