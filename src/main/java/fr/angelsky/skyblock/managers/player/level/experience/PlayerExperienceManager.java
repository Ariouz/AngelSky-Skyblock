package fr.angelsky.skyblock.managers.player.level.experience;

import fr.angelsky.angelskyapi.api.utils.HexColors;
import fr.angelsky.skyblock.SkyblockInstance;
import fr.angelsky.skyblock.managers.player.level.experience.types.BlockPlayerExperience;
import fr.angelsky.skyblock.managers.player.level.experience.types.MobPlayerExperience;
import fr.angelsky.skyblockapi.accounts.TempPlayer;
import fr.angelsky.skyblockapi.managers.level.PlayerLevel;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.Random;

public class PlayerExperienceManager {

    private final SkyblockInstance skyblockInstance;

    public PlayerExperienceManager(SkyblockInstance skyblockInstance){
        this.skyblockInstance = skyblockInstance;
    }

    public void processEntityExperience(Player player, EntityType entityType, int probabilityBoost, int xpBoost){
        MobPlayerExperience mob = MobPlayerExperience.getByEntityType(entityType);
        if (mob == null) return;
        giveExperience(player, mob.getXp(), mob.getProbability(), probabilityBoost, xpBoost);
    }

    public void processBlockExperience(Player player, BlockPlayerExperience block, int probabilityBoost, int xpBoost){
        giveExperience(player, block.getXp(), block.getProbability(), probabilityBoost, xpBoost);
    }

    private void giveExperience(Player player, int xp, float probability, int probabilityBoost, int xpBoost){
        int earnedXp = getExperienceWithProbability(xp, probability, probabilityBoost, xpBoost);
        if (earnedXp == 0) return;

        TempPlayer tempPlayer = skyblockInstance.getTempAccounts().get(player.getName());
        PlayerLevel playerLevel = tempPlayer.getPlayerLevel();
        playerLevel.addXp(earnedXp);
        skyblockInstance.getManagerLoader().getActionBarManager().sendActionBar(player, skyblockInstance.getManagerLoader().getMessageManager().getColorizedMessage(
                "&2+" + HexColors.LIGHT_GREEN + earnedXp + " xp &8- &7" + (int) playerLevel.getXp() + "&f/&7" + playerLevel.getNeededXpForLevel()),
                2);
        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 15, 10);
    }

    private int getExperienceWithProbability(int xp, float probability, int probabilityBoost, int xpBoost){
        float randomValue = new Random().nextFloat();
        float totalProbability = probability * 1 + (probabilityBoost / 100f);

        System.out.println("Proba : " + totalProbability + " value: "+ randomValue);
        if (totalProbability > randomValue) return (int) (xp / (1 + (xpBoost / 100f)));
        return 0;
    }

}
