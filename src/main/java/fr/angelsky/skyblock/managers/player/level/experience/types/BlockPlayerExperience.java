package fr.angelsky.skyblock.managers.player.level.experience.types;

import org.bukkit.Material;

import java.util.Arrays;

public enum BlockPlayerExperience {

    // MINERALS
    STONE(Material.STONE, false, 1, 15 / 100f, "Pierre"),
    COAL_ORE(Material.COAL_ORE, false, 1, 20 / 100f, "Minerai de Charbon"),
    IRON_ORE(Material.IRON_ORE, false, 2, 25 / 100f, "Minerai de Fer"),
    GOLD_ORE(Material.GOLD_ORE, false, 2, 25 / 100f, "Minerai d'Or"),
    REDSTONE_ORE(Material.REDSTONE_ORE, false, 3, 30 / 100f, "Minerai de Redstone"),
    LAPIS_ORE(Material.LAPIS_ORE, false, 3, 30 / 100f, "Minerai de Lapis"),
    EMERALD_ORE(Material.EMERALD_ORE, false, 5, 50 / 100f, "Minerai d'Emeraude"),
    DIAMOND_ORE(Material.DIAMOND_ORE, false, 7, 70 / 100f, "Minerai de Diamant"),

    // LOGS
    OAK_LOG(Material.OAK_LOG, false, 1, 20 / 100f, "Bûche de chêne"),
    DARK_OAK_LOG(Material.DARK_OAK_LOG, false, 1, 20 / 100f, "Bûche de chêne noir"),
    SPRUCE_LOG(Material.SPRUCE_LOG, false, 1, 20 / 100f, "Bûche de sapin"),
    BIRCH_LOG(Material.BIRCH_LOG, false, 1, 20 / 100f, "Bûche de bouleau"),
    JUNGLE_LOG(Material.JUNGLE_LOG, false, 1, 20 / 100f, "Bûche de bois de la jungle"),
    ACACIA_LOG(Material.ACACIA_LOG,false, 1, 20 / 100f, "Bûche d'acacia"),

    // FARMS
    WHEAT(Material.WHEAT,true, 1, 25 / 100f, "Blé"),
    MELON_BLOCK(Material.MELON,true, 1, 15 / 100f, "Melon"),
    PUMPKIN(Material.PUMPKIN,true, 1, 15 / 100f, "Citrouille"),
    CARROTS(Material.CARROTS, true, 1, 25 / 100f, Material.CARROT, "Carotte"),
    POTATOES(Material.POTATOES, true, 1, 25 / 100f, Material.POTATO, "Patate"),
    BEETROOTS(Material.BEETROOTS, true, 1, 30 / 100f, Material.BEETROOT, "Betterave"),
    NETHER_WART(Material.NETHER_WART,true, 1, 25 / 100f, "Verrue du Nether"),
    SUGAR_CANE(Material.SUGAR_CANE, true, 1, 20 / 100f, false, "Canne à sucre"),
    CACTUS(Material.CACTUS, true, 1, 50 / 100f, false, "Cactus"),

    ;

    private final Material type;
    private final boolean isFarm;
    private final int xp;
    private final float probability;
    private boolean checkAge = true;
    private Material icon;
    private final String display;

    BlockPlayerExperience(Material type, boolean isFarm, int xp, float probability, String display){
        this.type = type;
        this.isFarm = isFarm;
        this.xp = xp;
        this.probability = probability;
        this.display = display;
    }

    BlockPlayerExperience(Material type, boolean isFarm, int xp, float probability, Material icon, String display){
        this.type = type;
        this.isFarm = isFarm;
        this.xp = xp;
        this.probability = probability;
        this.icon = icon;
        this.display = display;
    }

    BlockPlayerExperience(Material type, boolean isFarm, int xp, float probability, boolean checkAge, String display){
        this.type = type;
        this.isFarm = isFarm;
        this.xp = xp;
        this.probability = probability;
        this.checkAge = checkAge;
        this.display = display;
    }

    public static BlockPlayerExperience getByType(Material type){
        return Arrays.stream(values()).filter(entry -> entry.getType() == type).findFirst().orElse(null);
    }

    public String getDisplay() {
        return display;
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
