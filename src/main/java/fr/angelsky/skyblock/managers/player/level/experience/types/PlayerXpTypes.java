package fr.angelsky.skyblock.managers.player.level.experience.types;

import org.bukkit.Material;

public enum PlayerXpTypes {

    FARMS (Material.WHEAT, "##528C52#&lAgriculture", 20),
    MOBS(Material.ROTTEN_FLESH, "##E5BD00#&lEntités", 21),
    FISHING(Material.FISHING_ROD, "##0A75AD#&lPêche", 22),
    ENCHANT(Material.BOOK, "##B176BB#&lEnchantements", 23),
    BLOCKS(Material.DEEPSLATE_DIAMOND_ORE, "##676E61#&lBlocs", 24),
    BREEDING(Material.BAMBOO, "##FF96CF#&lReproduction", 30)

    ;

    private final Material icon;
    private final String displayName;
    private final int slot;

    PlayerXpTypes(Material icon, String displayName, int slot){
        this.icon = icon;
        this.displayName = displayName;
        this.slot = slot;
    }

    public int getSlot() {
        return slot;
    }

    public Material getIcon() {
        return icon;
    }

    public String getDisplayName() {
        return displayName;
    }
}
