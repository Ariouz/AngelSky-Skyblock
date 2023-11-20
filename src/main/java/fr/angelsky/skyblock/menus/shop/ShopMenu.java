package fr.angelsky.skyblock.menus.shop;

import fr.angelsky.angelskyapi.api.utils.math.NumbersSeparator;
import fr.angelsky.skyblock.SkyblockInstance;
import fr.angelsky.skyblock.managers.ItemManager;
import fr.mrmicky.fastinv.FastInv;
import fr.mrmicky.fastinv.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.logging.Level;

public class ShopMenu {

    private final SkyblockInstance skyblockInstance;
    private final ShopManager shopManager;
    private final String display;
    private int size;

    public ShopMenu(SkyblockInstance skyblockInstance, ShopManager shopManager){
        this.skyblockInstance = skyblockInstance;
        this.shopManager = shopManager;
        this.display = ChatColor.translateAlternateColorCodes('&', shopManager.getShopConfig().getString("menu.categories.display"));
        this.size = shopManager.getShopConfig().getInt("menu.categories.size");
    }

    public void mainMenu(Player player){
        this.openCategoriesMenu(display, size, player);
    }

    private void openCategoriesMenu(String display, int size, Player player){
        FastInv inv = new FastInv(size, display);

        for(ShopCategory category : shopManager.getShopCategories().values()){
            ItemBuilder categoryItem = new ItemBuilder(category.getItem())
                    .name(category.getDisplay())
                    .lore(category.getLore());
            if (category.isEnchanted())
                categoryItem.enchant(Enchantment.ARROW_DAMAGE)
                    .flags(ItemFlag.HIDE_ENCHANTS);

            inv.setItem(category.getSlot(), categoryItem.build(), event -> {
                event.setCancelled(true);
                this.openCategoryMenu(this.display, 6*9, category, player, 0);
            });
        }

        ItemBuilder back = new ItemBuilder(Material.ARROW)
                .name(ChatColor.translateAlternateColorCodes('&', "&cRetour"));

        inv.setItem(inv.getInventory().getSize()-1, back.build(), event -> {
            event.setCancelled(true);
            skyblockInstance.getManagerLoader().getMenuManager().getMainMenu().menu(player, skyblockInstance.getTempAccounts().get(player.getName()));
            player.playSound(player.getLocation(), Sound.BLOCK_TRIPWIRE_CLICK_ON, 30, 30);
        });

        player.openInventory(inv.getInventory());
    }

    public void openCategoryMenu(String display, int size, ShopCategory category, Player player, int page){
        FastInv inv = new FastInv(size, display);

        ItemStack border = new fr.angelsky.angelskyapi.api.utils.builder.ItemBuilder(category.getBorderItem()).setDisplayName(ChatColor.RED+"").build();
        inv.setItems(inv.getBorders(), border, event -> event.setCancelled(true));

        int itemsPerPage = 4*7;
        int i = itemsPerPage*page; // START INDEX
        int j = -1;
        // CATEGORY'S ITEMS

        for(ShopItem item : this.shopManager.getCategoryItems(category)){
            j++;
            if(j == itemsPerPage*page+itemsPerPage) break; // STOP IF LAST INDEX IS REACHED
            if (j < itemsPerPage * page) continue; // SKIP UNTIL INDEX >= PAGE ITEMS
            if (item.getItem() == null)
            {
                skyblockInstance.getSkyblock().getLogger().log(Level.WARNING, "Material is null for item " + item.getIdStr());
                continue;
            }
            ItemBuilder shopItem = new ItemBuilder(item.getItem())
                    .name(item.getDisplay())
                    .lore(item.getLore());

            inv.addItem(shopItem.build(), event -> {
                event.setCancelled(true);
                this.openShopItemMenu(this.display + " " + item.getDisplay(), 5*9, item, player);
            });
        }

         // PAGINATION

        int maxPages = Math.floorDiv(this.shopManager.getCategoryItems(category).size(), itemsPerPage);
        boolean hasItemOnNextPage = (page + 1) * itemsPerPage < this.shopManager.getCategoryItems(category).size();

        ItemStack current = new ItemBuilder(Material.SUNFLOWER)
                .name(ChatColor.GOLD + "Page " + ChatColor.YELLOW + page)
                .build();
        ItemStack previous = new ItemBuilder(Material.ARROW)
                .name(ChatColor.GOLD + "Page " + ChatColor.YELLOW + (page - 1) + ChatColor.DARK_GRAY + " (-1)")
                .build();
        ItemStack next = new ItemBuilder(Material.ARROW)
                .name(ChatColor.GOLD + "Page " + ChatColor.YELLOW + (page + 1) + ChatColor.DARK_GRAY + " (+1)")
                .build();
        if (page != 0) inv.setItem(47, previous, event -> this.openCategoryMenu(this.display, 6*9, category, player, page - 1));
        inv.setItem(49, current);
        if(page+1 <= maxPages && hasItemOnNextPage) inv.setItem(51, next, event -> this.openCategoryMenu(this.display, 6*9, category, player, page + 1));

        // BACK ITEM

        ItemBuilder back = new ItemBuilder(Material.ARROW)
                .name(ChatColor.translateAlternateColorCodes('&', "&cRetour"));

        inv.setItem(inv.getInventory().getSize()-1, back.build(), event -> {
            event.setCancelled(true);
            player.playSound(player.getLocation(), Sound.BLOCK_TRIPWIRE_CLICK_ON, 30, 30);
            this.mainMenu(player);
        });

        player.openInventory(inv.getInventory());
    }

    public void openShopItemMenu(String display, int size, ShopItem shopItem, Player player){
        FastInv inv = new FastInv(size, display);

        ItemBuilder item = new ItemBuilder(shopItem.getItem())
                .name(shopItem.getDisplay())
                .lore(shopItem.getLore());


        ItemBuilder buy = new ItemBuilder(Material.GREEN_TERRACOTTA)
                .name(ChatColor.translateAlternateColorCodes('&', shopItem.isCanBeBought() ? "&a&lAcheter" : "&cNe peut pas être acheté"));

        ItemBuilder sell = new ItemBuilder(Material.RED_TERRACOTTA)
                .name(ChatColor.translateAlternateColorCodes('&', shopItem.isCanBeSold() ? "&c&lVendre" : "&cNe peux pas être vendu"));

        ItemBuilder sellAll = new ItemBuilder(Material.SUNFLOWER)
                .name(ChatColor.translateAlternateColorCodes('&', shopItem.isCanBeSold() ? "&6&lVendre Inventaire" : "&cNe peut pas être vendu"));

        ItemBuilder back = new ItemBuilder(Material.ARROW)
                .name(ChatColor.translateAlternateColorCodes('&', "&cRetour"));

        inv.setItem(13, item.build(), event -> event.setCancelled(true));
        inv.setItem(29, buy.build(), event -> {
            event.setCancelled(true);
            if(shopItem.isCanBeBought()) this.openBuyMenu(display, size, shopItem, player);
        });

        inv.setItem(31, sell.build(), event -> {
            event.setCancelled(true);
            if(shopItem.isCanBeSold()) this.openSellMenu(display, size, shopItem, player);
        });

        inv.setItem(33, sellAll.build(), event -> {
            event.setCancelled(true);
            if(shopItem.isCanBeSold()) this.sellAll(shopItem, player);
        });

        inv.setItem(inv.getInventory().getSize()-1, back.build(), event -> {
            this.openCategoryMenu(this.display, 6*9, shopItem.getShopCategory(), player, 0);
            player.playSound(player.getLocation(), Sound.BLOCK_TRIPWIRE_CLICK_ON, 30, 30);
        });

        player.openInventory(inv.getInventory());
    }

    public void openBuyMenu(String display, int size, ShopItem shopItem, Player player){
        FastInv inv = new FastInv(size, display);

        ItemBuilder item = new ItemBuilder(shopItem.getItem())
                .name(shopItem.getDisplay())
                .lore(shopItem.getLore());

        inv.setItem(13, item.build());

        int[] amounts = {1, 4, 8, 16, 32, 64};
        int startSlot = 28;
        for(int i : amounts){
            ItemBuilder itemBuilder = new ItemBuilder(Material.LIME_STAINED_GLASS_PANE)
                    .name(ChatColor.translateAlternateColorCodes('&', "&a&lAcheter &8x&2" + i))
                    .lore(ChatColor.translateAlternateColorCodes('&', "&7Acheter pour &2" + (shopItem.getBuyPrice() * i > 1000 ? NumbersSeparator.LanguageFormatter.USA.convert((int) (shopItem.getBuyPrice()*i), 3) : shopItem.getBuyPrice() * i) + " &fAngelCoins " + SkyblockInstance.COIN));
            inv.setItem(startSlot, itemBuilder.build(), event -> {
                this.shopManager.buyItem(shopItem, i, player);
            });
            startSlot++;
        }

        ItemBuilder buyMore = new ItemBuilder(Material.GREEN_TERRACOTTA)
                .name(ChatColor.translateAlternateColorCodes('&', "&2&lAcheter Plus"));

        inv.setItem(startSlot, buyMore.build(), event -> {
            this.openBuyMoreMenu(display, size, shopItem, player);
        });

        ItemBuilder back = new ItemBuilder(Material.ARROW)
                .name(ChatColor.translateAlternateColorCodes('&', "&cRetour"));
        inv.setItem(inv.getInventory().getSize()-1, back.build(), event -> {
            this.openShopItemMenu(this.display, 5*9, shopItem, player);
            player.playSound(player.getLocation(), Sound.BLOCK_TRIPWIRE_CLICK_ON, 30, 30);
        });

        player.openInventory(inv.getInventory());
    }

    public void openBuyMoreMenu(String display, int size, ShopItem shopItem, Player player){
        FastInv inv = new FastInv(size, display);

        ItemBuilder item = new ItemBuilder(shopItem.getItem())
                .name(shopItem.getDisplay())
                .lore(shopItem.getLore());

        inv.setItem(13, item.build());

        int[] amounts = {2, 3, 4, 5, 6, 7, 8};
        int startSlot = 28;
        for(int i : amounts){
            ItemBuilder itemBuilder = new ItemBuilder(Material.GREEN_TERRACOTTA)
                    .name(ChatColor.translateAlternateColorCodes('&', "&2&lAcheter &8x&2" + i + " &a&lStacks"))
                    .lore(ChatColor.translateAlternateColorCodes('&', "&7Acheter pour &2" + (shopItem.getBuyPrice() * i*64 > 1000 ? NumbersSeparator.LanguageFormatter.USA.convert((int) (shopItem.getBuyPrice()*i*64), 3) : shopItem.getBuyPrice() * i*64) + " &fAngelCoins " + SkyblockInstance.COIN));
            inv.setItem(startSlot, itemBuilder.build(), event -> {
                this.shopManager.buyItem(shopItem, i*64, player);
            });
            startSlot++;
        }

        ItemBuilder back = new ItemBuilder(Material.ARROW)
                .name(ChatColor.translateAlternateColorCodes('&', "&cRetour"));
        inv.setItem(inv.getInventory().getSize()-1, back.build(), event -> {
            this.openBuyMenu(this.display, 5*9, shopItem, player);
            player.playSound(player.getLocation(), Sound.BLOCK_TRIPWIRE_CLICK_ON, 30, 30);
        });
        player.openInventory(inv.getInventory());
    }

    public void openSellMenu(String display, int size, ShopItem shopItem, Player player){
        FastInv inv = new FastInv(size, display);

        ItemBuilder item = new ItemBuilder(shopItem.getItem())
                .name(shopItem.getDisplay())
                .lore(shopItem.getLore());

        inv.setItem(13, item.build());

        int[] amounts = {1, 4, 8, 16, 32, 64};
        int startSlot = 28;
        for(int i : amounts){
            ItemBuilder itemBuilder = new ItemBuilder(Material.RED_STAINED_GLASS_PANE)
                    .name(ChatColor.translateAlternateColorCodes('&', "&c&lVendre &8x&4" + i))
                    .lore(ChatColor.translateAlternateColorCodes('&', "&7Vendre pour &4" + (shopItem.getSellPrice() * i > 1000 ? NumbersSeparator.LanguageFormatter.USA.convert((int) (shopItem.getSellPrice()*i), 3) : shopItem.getSellPrice() * i) + " &fAngelCoins " + SkyblockInstance.COIN));
            inv.setItem(startSlot, itemBuilder.build(), event -> {
                this.shopManager.sellItem(shopItem, i, player);
            });
            startSlot++;
        }

        ItemBuilder sellMore = new ItemBuilder(Material.RED_TERRACOTTA)
                .name(ChatColor.translateAlternateColorCodes('&', "&4&lVendre Plus"));

        inv.setItem(startSlot, sellMore.build(), event -> {
            this.openSellMoreMenu(display, size, shopItem, player);
        });

        ItemBuilder back = new ItemBuilder(Material.ARROW)
                .name(ChatColor.translateAlternateColorCodes('&', "&cRetour"));
        inv.setItem(inv.getInventory().getSize()-1, back.build(), event -> {
            this.openShopItemMenu(this.display, 5*9, shopItem, player);
            player.playSound(player.getLocation(), Sound.BLOCK_TRIPWIRE_CLICK_ON, 30, 30);
        });

        player.openInventory(inv.getInventory());
    }

    public void openSellMoreMenu(String display, int size, ShopItem shopItem, Player player){
        FastInv inv = new FastInv(size, display);

        ItemBuilder item = new ItemBuilder(shopItem.getItem())
                .name(shopItem.getDisplay())
                .lore(shopItem.getLore());

        inv.setItem(13, item.build());

        int[] amounts = {2, 3, 4, 5, 6, 7, 8};
        int startSlot = 28;
        for(int i : amounts){
            ItemBuilder itemBuilder = new ItemBuilder(Material.RED_TERRACOTTA)
                    .name(ChatColor.translateAlternateColorCodes('&', "&4&lVendre &8x&4" + i + " &4&lStacks"))
                    .lore(ChatColor.translateAlternateColorCodes('&', "&7Vendre pour &4" + (shopItem.getSellPrice() * i*64 > 1000 ? NumbersSeparator.LanguageFormatter.USA.convert((int) (shopItem.getSellPrice()*i*64), 3) : shopItem.getSellPrice() * i*64) + " &fAngelCoins " + SkyblockInstance.COIN));
            inv.setItem(startSlot, itemBuilder.build(), event -> {
                this.shopManager.sellItem(shopItem, i*64, player);
            });
            startSlot++;
        }

        ItemBuilder back = new ItemBuilder(Material.ARROW)
                .name(ChatColor.translateAlternateColorCodes('&', "&cRetour"));
        inv.setItem(inv.getInventory().getSize()-1, back.build(), event -> {
            this.openSellMenu(this.display, 5*9, shopItem, player);
            player.playSound(player.getLocation(), Sound.BLOCK_TRIPWIRE_CLICK_ON, 30, 30);
        });
        player.openInventory(inv.getInventory());
    }

    public void sellAll(ShopItem shopItem, Player player){
        int amount = new ItemManager().getAmount(player, new ItemStack(shopItem.getItem()));

        if(!player.getInventory().containsAtLeast(new ItemStack(shopItem.getItem()), amount) || amount == 0){
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', SkyblockInstance.PREFIX + "&cVous n'avez pas suffisamment de cet item pour vendre."));
            player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 15, 10);
            return;
        }

        new ItemManager().removeItems(player, player.getInventory(), new ItemStack(shopItem.getItem()), amount);
        skyblockInstance.getSkyBlockApiInstance().getEconomy().depositPlayer(player, amount*shopItem.getSellPrice());
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', SkyblockInstance.PREFIX + "Vous avez vendu &7x"+amount+" " + shopItem.getDisplay() + " &fpour &6"+(amount*shopItem.getSellPrice())+ " &fAngelCoins " + SkyblockInstance.COIN));
        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 15, 10);
    }
}
