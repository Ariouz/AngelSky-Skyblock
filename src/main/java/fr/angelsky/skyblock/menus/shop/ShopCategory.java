package fr.angelsky.skyblock.menus.shop;

import org.bukkit.Material;

import java.util.ArrayList;

public class ShopCategory {

    private final String id;
    private final String display;
    private final ArrayList<String> lore;
    private final boolean enchanted;
    private final Material item;
    private final int slot;
    private final Material borderItem;

    public ShopCategory(String id, String display, ArrayList<String> lore, boolean enchanted, Material item, int slot, Material borderItem){
        this.id = id;
        this.display = display;
        this.lore = lore;
        this.enchanted = enchanted;
        this.item = item;
        this.slot = slot;
        this.borderItem = borderItem;
    }

    public String getId() {
        return id;
    }

    public String getDisplay() {
        return display;
    }

    public ArrayList<String> getLore() {
        return lore;
    }

    public boolean isEnchanted() {
        return enchanted;
    }

    public Material getItem() {
        return item;
    }

    public int getSlot() {
        return slot;
    }

    public Material getBorderItem() {
        return borderItem;
    }
}
