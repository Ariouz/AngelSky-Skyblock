package fr.angelsky.skyblock.managers.player.level.experience.types;

import org.bukkit.Material;

import java.util.Arrays;

public enum BlockPlayerExperience {

    // MINERALS
    STONE(Material.STONE, 1, 15 / 100f),
    COAL_ORE(Material.COAL_ORE, 1, 20 / 100f),
    IRON_ORE(Material.IRON_ORE, 2, 25 / 100f),
    GOLD_ORE(Material.GOLD_ORE, 2, 25 / 100f),
    REDSTONE_ORE(Material.REDSTONE_ORE, 3, 30 / 100f),
    LAPIS_ORE(Material.LAPIS_ORE, 3, 30 / 100f),
    EMERALD_ORE(Material.EMERALD_ORE, 5, 50 / 100f),
    DIAMOND_ORE(Material.DIAMOND_ORE, 7, 70 / 100f),

    // LOGS
    OAK_LOG(Material.OAK_LOG, 1, 20 / 100f),
    DARK_OAK_LOG(Material.DARK_OAK_LOG, 1, 20 / 100f),
    SPRUCE_LOG(Material.SPRUCE_LOG, 1, 20 / 100f),
    BIRCH_LOG(Material.BIRCH_LOG, 1, 20 / 100f),
    JUNGLE_LOG(Material.JUNGLE_LOG, 1, 20 / 100f),
    ACACIA_LOG(Material.ACACIA_LOG, 1, 20 / 100f),

    // FARMS
    WHEAT(Material.WHEAT, 1, 20 / 100f),
    MELON_BLOCK(Material.MELON, 1, 20 / 100f),
    PUMPKIN(Material.PUMPKIN, 1, 20 / 100f),
    CARROTS(Material.CARROTS, 1, 20 / 100f),
    POTATOES(Material.POTATOES, 1, 20 / 100f),
    BEETROOTS(Material.BEETROOTS, 1, 20 / 100f),
    NETHER_WART(Material.NETHER_WART, 1, 20 / 100f),
    SUGAR_CANE(Material.SUGAR_CANE, 1, 20 / 100f, false),
    CACTUS(Material.CACTUS, 1, 20 / 100f, false),

    ;

    private final Material type;
    private final int xp;
    private final float probability;
    private boolean checkAge = true;

    BlockPlayerExperience(Material type, int xp, float probability){
        this.type = type;
        this.xp = xp;
        this.probability = probability;
    }

    BlockPlayerExperience(Material type, int xp, float probability, boolean checkAge){
        this.type = type;
        this.xp = xp;
        this.probability = probability;
        this.checkAge = checkAge;
    }

    public static BlockPlayerExperience getByEntityType(Material type){
        return Arrays.stream(values()).filter(entry -> entry.getType() == type).findFirst().orElse(null);
    }

    public boolean isCheckAge() {
        return checkAge;
    }

    public Material getType() {
        return type;
    }

    public int getXp() {
        return xp;
    }

    public float getProbability() {
        return probability;
    }
}
