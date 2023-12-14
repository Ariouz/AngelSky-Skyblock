package fr.angelsky.skyblock.managers.player.level.experience.types;

import org.bukkit.entity.EntityType;

import java.util.Arrays;

public enum MobPlayerExperience {

    ZOMBIE(EntityType.ZOMBIE, 1, 10 / 100f),
    SKELETON(EntityType.SKELETON, 1, 10 / 100f),


    ;

    private final EntityType entityType;
    private final int xp;
    private final float probability;

    MobPlayerExperience(EntityType entityType, int xp, float probability){
        this.entityType = entityType;
        this.xp = xp;
        this.probability = probability;
    }

    public static MobPlayerExperience getByEntityType(EntityType type){
        return Arrays.stream(values()).filter(entry -> entry.getEntityType() == type).findFirst().orElse(null);
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public int getXp() {
        return xp;
    }

    public float getProbability() {
        return probability;
    }
}
