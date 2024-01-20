package fr.angelsky.skyblock.managers.player.level.experience.types;

import org.bukkit.entity.EntityType;

import java.util.Arrays;

public enum MobPlayerExperience {

    ZOMBIE(EntityType.ZOMBIE, 1, 10 / 100f),
    SKELETON(EntityType.SKELETON, 1, 10 / 100f),
    SPIDER(EntityType.SPIDER, 1, 15 / 100f),
    CREEPER(EntityType.CREEPER, 1, 20 / 100f),
    WITCH(EntityType.WITCH, 3, 60 / 100f),
    PHANTOM(EntityType.PHANTOM, 5, 90 / 100f),
    SLIME(EntityType.SLIME, 2, 75 / 100f),
    MAGMA_CUBE(EntityType.MAGMA_CUBE, 3, 50 / 100f),

    COW(EntityType.COW, 2, 50 / 100f),
    CHICKEN(EntityType.CHICKEN, 1, 50 / 100f),
    SHEEP(EntityType.SHEEP, 2, 50 / 100f),
    HORSE(EntityType.HORSE, 4, 75 / 100f),
    PIG(EntityType.PIG, 1, 50 / 100f),
    RABBIT(EntityType.RABBIT, 5, 60 / 100f),
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
