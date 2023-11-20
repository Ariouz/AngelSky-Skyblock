package fr.angelsky.skyblock.menus.shop;

import fr.angelsky.angelskyapi.api.utils.file.ConfigUtils;
import fr.angelsky.skyblock.SkyblockInstance;
import fr.angelsky.skyblock.managers.ItemManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.stream.Collectors;

public class ShopManager {

    private final SkyblockInstance skyblockInstance;

    private ShopMenu shopMenu;
    private final HashMap<String, ShopCategory> shopCategories = new HashMap<>();
    private final ArrayList<ShopItem> shopItems = new ArrayList<>();

    private final ConfigUtils shopConfig;

    public ShopManager(SkyblockInstance skyblockInstance){
        this.skyblockInstance = skyblockInstance;
        this.shopConfig = new ConfigUtils(skyblockInstance.getSkyblock(), "shop.yml");
        load();
    }

    private void load(){
        this.loadMenu();
        this.loadCategories();
        this.loadItems();
    }

    private void loadMenu(){
        this.shopMenu = new ShopMenu(skyblockInstance, this);
    }

    private void loadCategories(){
        ConfigurationSection section = shopConfig.getYamlConfiguration().getConfigurationSection("categories");
        assert section != null;
        for(String categoryId : section.getKeys(false)){
            String display = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(section.getString(categoryId + ".display")));
            ArrayList<String> lore = new ArrayList<>();
            Objects.requireNonNull(section.getList(categoryId + ".lore")).forEach(line -> lore.add(ChatColor.translateAlternateColorCodes('&', line.toString())));
            boolean enchanted = section.getBoolean(categoryId+".enchanted");
            Material item = Material.getMaterial(Objects.requireNonNull(section.getString(categoryId + ".item_id")));
            int slot = section.getInt(categoryId+".slot");
            Material borderItem = Material.getMaterial(Objects.requireNonNull(section.getString(categoryId + ".border_item")));
            this.shopCategories.put(categoryId, new ShopCategory(categoryId, display, lore, enchanted, item, slot, borderItem));
        }
    }

    private void loadItems(){
        ConfigurationSection section = shopConfig.getYamlConfiguration().getConfigurationSection("items.shop");
        assert section != null;

        int id = 0;
        for(String categoryId : section.getKeys(false)){
            for(String itemId : section.getConfigurationSection(categoryId).getKeys(false)){
                id++;

                String display = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(section.getString(categoryId + "."+itemId+  ".display")));

                boolean canBeBought, canBeSold;
                double buyPrice, sellPrice;
                buyPrice = section.getDouble(categoryId+"."+itemId+".buy_price");
                sellPrice = section.getDouble(categoryId+"."+itemId+".sell_price");
                canBeBought = buyPrice >= 0;
                canBeSold = sellPrice >= 0;

                ArrayList<String> lore = new ArrayList<>();
                Objects.requireNonNull(section.getList(categoryId + "." +itemId+ ".lore")).forEach(line -> {
                    String fline = line.toString()
                            .replaceAll("%buy_price%", canBeBought ? String.valueOf(buyPrice) + " &7AngelCoins " + SkyblockInstance.COIN : "&cNe peut pas être acheté")
                            .replaceAll("%sell_price%", canBeSold ? String.valueOf(sellPrice) + " &7AngelCoins " + SkyblockInstance.COIN: "&cNe peut pas être vendu");
                    lore.add(ChatColor.translateAlternateColorCodes('&', fline));
                });
                Material item = Material.getMaterial(Objects.requireNonNull(section.getString(categoryId + "." + itemId+ ".item_id")).toUpperCase());
                ShopCategory cat = this.getCategory(categoryId);
                this.shopItems.add(new ShopItem(id, cat, itemId, display, lore, item, buyPrice, sellPrice, canBeBought, canBeSold));
            }
        }
    }

    public ShopCategory getCategory(String id){
        return shopCategories.get(id);
    }

    public List<ShopItem> getCategoryItems(ShopCategory shopCategory){
        return this.shopItems.stream()
                .filter(t -> t.getShopCategory() == shopCategory)
                .sorted(Comparator.comparingInt(ShopItem::getId))
                .collect(Collectors.toList());
    }

    public void buyItem(ShopItem item, int amount, Player player){
        boolean full = false;

        if(skyblockInstance.getSkyBlockApiInstance().getEconomy().getBalance(player) < amount*item.getBuyPrice()){
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', SkyblockInstance.PREFIX + "&cVous n'avez pas suffisamment d'AngelCoins " + SkyblockInstance.COIN + " &cpour acheter cet item."));
            player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 15, 10);
            return;
        }

        for(int i = 0; i < amount; i++){
            if(player.getInventory().firstEmpty() == -1){
                player.getWorld().dropItem(player.getLocation(), new ItemStack(item.getItem()));
                full=true;
            }else{ player.getInventory().addItem(new ItemStack(item.getItem())); }
        }
        if(full) player.sendMessage(ChatColor.translateAlternateColorCodes('&', SkyblockInstance.PREFIX + "&cDes items ont été jetés au sol car votre inventaire est plein."));
        skyblockInstance.getSkyBlockApiInstance().getEconomy().withdrawPlayer(player, amount*item.getBuyPrice());
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', SkyblockInstance.PREFIX + "Vous avez acheté &7x"+amount+" " + item.getDisplay() + " &fpour &6"+(amount*item.getBuyPrice())+ " &fAngelCoins " + SkyblockInstance.COIN));
        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 15, 10);
    }

    public void sellItem(ShopItem item, int amount, Player player){
        if(!player.getInventory().containsAtLeast(new ItemStack(item.getItem()), amount)){
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', SkyblockInstance.PREFIX + "&cVous n'avez pas suffisamment de cet item pour vendre."));
            player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 15, 10);
            return;
        }
        new ItemManager().removeItems(player, player.getInventory(), new ItemStack(item.getItem()), amount);
        skyblockInstance.getSkyBlockApiInstance().getEconomy().depositPlayer(player, amount*item.getSellPrice());
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', SkyblockInstance.PREFIX + "Vous avez vendu &7x"+amount+" " + item.getDisplay() + " &fpour &6"+(amount*item.getSellPrice())+ " &fAngelCoins " + SkyblockInstance.COIN));
        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 15, 10);
    }

    public HashMap<String, ShopCategory> getShopCategories() {
        return shopCategories;
    }

    public ArrayList<ShopItem> getShopItems() {
        return shopItems;
    }

    public ShopItem getShopItem(Material material){
        return this.shopItems.stream().filter(shopItem -> shopItem.getItem() == material).findFirst().orElse(null);
    }

    public ShopMenu getShopMenu() {
        return shopMenu;
    }

    public ConfigUtils getShopConfig() {
        return shopConfig;
    }
}
