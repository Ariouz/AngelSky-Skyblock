package fr.angelsky.skyblock.managers.player.level.experience.types;

import org.bukkit.Material;

import java.util.Arrays;

public enum BlockPlayerExperience {

    // MINERALS
    STONE(Material.STONE, false, 1, 15 / 100f),
    COAL_ORE(Material.COAL_ORE, false, 1, 20 / 100f),
    IRON_ORE(Material.IRON_ORE, false, 2, 25 / 100f),
    GOLD_ORE(Material.GOLD_ORE, false, 2, 25 / 100f),
    REDSTONE_ORE(Material.REDSTONE_ORE, false, 3, 30 / 100f),
    LAPIS_ORE(Material.LAPIS_ORE, false, 3, 30 / 100f),
    EMERALD_ORE(Material.EMERALD_ORE, false, 5, 50 / 100f),
    DIAMOND_ORE(Material.DIAMOND_ORE, false, 7, 70 / 100f),

    // LOGS
    OAK_LOG(Material.OAK_LOG, false, 1, 20 / 100f),
    DARK_OAK_LOG(Material.DARK_OAK_LOG, false, 1, 20 / 100f),
    SPRUCE_LOG(Material.SPRUCE_LOG, false, 1, 20 / 100f),
    BIRCH_LOG(Material.BIRCH_LOG, false, 1, 20 / 100f),
    JUNGLE_LOG(Material.JUNGLE_LOG, false, 1, 20 / 100f),
    ACACIA_LOG(Material.ACACIA_LOG,false, 1, 20 / 100f),

    // FARMS
    WHEAT(Material.WHEAT,true, 1, 20 / 100f),
    MELON_BLOCK(Material.MELON,true, 1, 20 / 100f),
    PUMPKIN(Material.PUMPKIN,true, 1, 20 / 100f),
    CARROTS(Material.CARROTS, true, 1, 20 / 100f, Material.CARROT),
    POTATOES(Material.POTATOES, true, 1, 20 / 100f, Material.POTATO),
    BEETROOTS(Material.BEETROOTS, true, 1, 20 / 100f, Material.BEETROOT),
    NETHER_WART(Material.NETHER_WART,true, 1, 20 / 100f),
    SUGAR_CANE(Material.SUGAR_CANE, true, 1, 20 / 100f, false),
    CACTUS(Material.CACTUS, true, 1, 20 / 100f, false),

    ;

    private final Material type;
    private final boolean isFarm;
    private final int xp;
    private final float probability;
    private boolean checkAge = true;
    private Material icon;

    BlockPlayerExperience(Material type, boolean isFarm, int xp, float probability){
        this.type = type;
        this.isFarm = isFarm;
        this.xp = xp;
        this.probability = probability;
    }

    BlockPlayerExperience(Material type, boolean isFarm, int xp, float probability, Material icon){
        this.type = type;
        this.isFarm = isFarm;
        this.xp = xp;
        this.probability = probability;
        this.icon = icon;
    }

    BlockPlayerExperience(Material type, boolean isFarm, int xp, float probability, boolean checkAge){
        this.type = type;
        this.isFarm = isFarm;
        this.xp = xp;
        this.probability = probability;
        this.checkAge = checkAge;
    }

    public static BlockPlayerExperience getByType(Material type){
        return Arrays.stream(values()).filter(entry -> entry.getType() == type).findFirst().orElse(null);
    }

    public Material getIcon() {
        return icon;
    }

    public boolean isFarm() {
        return isFarm;
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
