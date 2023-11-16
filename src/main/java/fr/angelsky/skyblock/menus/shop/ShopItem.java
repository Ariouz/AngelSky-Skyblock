package fr.angelsky.skyblock.menus.shop;

import org.bukkit.Material;

import java.util.ArrayList;

public class ShopItem {


    private final int id;
    private final ShopCategory shopCategory;
    private final String idStr;
    private final String display;
    private final ArrayList<String> lore;
    private final Material item;
    private final double buyPrice, sellPrice;
    private final boolean canBeBought, canBeSold;

    public ShopItem(int id, ShopCategory shopCategory, String idStr, String display, ArrayList<String> lore, Material item, double buyPrice, double sellPrice, boolean canBeBought, boolean canBeSold) {
        this.id = id;
        this.shopCategory = shopCategory;
        this.idStr = idStr;
        this.display = display;
        this.lore = lore;
        this.item = item;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
        this.canBeBought = canBeBought;
        this.canBeSold = canBeSold;
    }

    public int getId() {
        return id;
    }

    public ShopCategory getShopCategory() {
        return shopCategory;
    }

    public String getIdStr() {
        return idStr;
    }

    public String getDisplay() {
        return display;
    }

    public ArrayList<String> getLore() {
        return lore;
    }

    public Material getItem() {
        return item;
    }

    public double getBuyPrice() {
        return buyPrice;
    }

    public double getSellPrice() {
        return sellPrice;
    }

    public boolean isCanBeBought() {
        return canBeBought;
    }

    public boolean isCanBeSold() {
        return canBeSold;
    }

}
