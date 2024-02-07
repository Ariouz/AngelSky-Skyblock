package fr.angelsky.skyblock.managers.player.level.experience.types;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

import java.util.Arrays;

public enum MobPlayerExperience {

    ZOMBIE(EntityType.ZOMBIE, 1, 30 / 100f, Material.ROTTEN_FLESH, "Zombie"),
    SKELETON(EntityType.SKELETON, 1, 30 / 100f, Material.BONE, "Squelette"),
    SPIDER(EntityType.SPIDER, 1, 45 / 100f, Material.SPIDER_EYE, "Araignée"),
    CREEPER(EntityType.CREEPER, 1, 50 / 100f, Material.GUNPOWDER, "Creeper"),
    WITCH(EntityType.WITCH, 3, 60 / 100f, Material.SPLASH_POTION, "Sorcière"),
    PHANTOM(EntityType.PHANTOM, 5, 90 / 100f, Material.PHANTOM_MEMBRANE, "Phantome"),
    SLIME(EntityType.SLIME, 2, 65 / 100f, Material.SLIME_BALL, "Slime"),
    MAGMA_CUBE(EntityType.MAGMA_CUBE, 3, 70 / 100f, Material.MAGMA_CREAM, "Cube de Magma"),

    COW(EntityType.COW, 2, 50 / 100f, Material.MILK_BUCKET, "Vache"),
    CHICKEN(EntityType.CHICKEN, 1, 50 / 100f, Material.FEATHER, "Poulet"),
    SHEEP(EntityType.SHEEP, 2, 50 / 100f, Material.WHITE_WOOL, "Mouton"),
    HORSE(EntityType.HORSE, 4, 75 / 100f, Material.SADDLE, "Cheval"),
    PIG(EntityType.PIG, 1, 50 / 100f, Material.CARROT_ON_A_STICK, "Cochon"),
    RABBIT(EntityType.RABBIT, 5, 60 / 100f, Material.RABBIT_FOOT, "Lapin"),
    ;

    private final EntityType entityType;
    private final int xp;
    private final float probability;
    private final Material icon;
    private final String display;

    MobPlayerExperience(EntityType entityType, int xp, float probability, Material icon, String display){
        this.entityType = entityType;
        this.xp = xp;
        this.probability = probability;
        this.icon = icon;
        this.display = display;
    }

    public static MobPlayerExperience getByEntityType(EntityType type){
        return Arrays.stream(values()).filter(entry -> entry.getEntityType() == type).findFirst().orElse(null);
    }

    public Material getIcon() {
        return icon;
    }

    public String getDisplay() {
        return display;
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
