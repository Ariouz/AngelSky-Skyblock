package fr.angelsky.skyblock.managers.player.level.experience.types;

import org.bukkit.Material;

public enum PlayerXpTypes {

    FARMS (Material.WHEAT, "##528C52#&lAgriculture"),
    MOBS(Material.ROTTEN_FLESH, "##E5BD00#&lEntités"),
    FISHING(Material.FISHING_ROD, "##0A75AD#&lPêche"),
    ENCHANT(Material.BOOK, "##B176BB#&lEnchantements"),
    BLOCKS(Material.DEEPSLATE_DIAMOND_ORE, "##676E61#&lBlocs")

    ;

    private final Material icon;
    private final String displayName;

    PlayerXpTypes(Material icon, String displayName){
        this.icon = icon;
        this.displayName = displayName;
    }

    public Material getIcon() {
        return icon;
    }

    public String getDisplayName() {
        return displayName;
    }
}
