package fr.angelsky.skyblock.menus.island.levels;

import fr.angelsky.angelskyapi.api.utils.math.NumbersSeparator;
import fr.angelsky.skyblock.SkyblockInstance;
import fr.angelsky.skyblockapi.accounts.TempPlayer;
import fr.angelsky.skyblockapi.managers.level.LevelColor;
import fr.angelsky.skyblockapi.managers.level.LevelRankManager;
import fr.mrmicky.fastinv.FastInv;
import fr.mrmicky.fastinv.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LevelsMenu {

    private final SkyblockInstance skyblockInstance;

    // USED TO ADD X TO START INDEX DUE TO RANK UP ITEMS WHICH TAKE 1 SLOT PER RANK UP
    private final HashMap<Integer, Integer> pagesIndexIncrements = new HashMap<>();

    private final int maxLevel = 150;
    private final int maxRank = SkyblockInstance.MAX_RANK;
    private final int itemsPerPage = 5*9;


    public LevelsMenu(SkyblockInstance skyblockInstance) {
        this.skyblockInstance = skyblockInstance;
    }

    public void menu(Player player, TempPlayer tempPlayer, int page){
        FastInv fastInv = new FastInv(9*6, "» Niveaux");
        List<ItemStack> items = new ArrayList<>();


        LevelRankManager levelRank = skyblockInstance.getManagerLoader().getLevelManager().getLevelRankManager();
        LevelColor levelColor = skyblockInstance.getManagerLoader().getLevelManager().getLevelColor();

        int passedRanks = (page*itemsPerPage) / maxLevel; //  == StartIndex / maxLevel (150)
        int startIndex = (page * itemsPerPage) - passedRanks; // REMOVE RANK UP ITEMS INDEXES

        int startLevel = startIndex % maxLevel;
        int startRank = startIndex / maxLevel;

        for(int i = startRank; i <= maxRank; i++){
            if(items.size() >= itemsPerPage) break;
            for(int j = startLevel; j <= maxLevel; j++){

                if(items.size() >= itemsPerPage) break;

                if(j == maxLevel){
                    ItemBuilder nextStep = new ItemBuilder(Material.GOLD_BLOCK)
                            .enchant(Enchantment.ARROW_DAMAGE)
                            .flags(ItemFlag.HIDE_ENCHANTS);

                    if(i == maxRank){
                        nextStep.name(ChatColor.GREEN + "" + ChatColor.BOLD + "⇪" + ChatColor.GOLD + " Rank " + levelRank.getLevelRank(i).getDisplay() + i + ChatColor.DARK_GRAY + " » " + levelRank.getLevelRank(30).getDisplay() + ChatColor.DARK_GRAY + " (Max)");
                    }else{
                        nextStep.name(ChatColor.GREEN + "" + ChatColor.BOLD + "⇪" + ChatColor.GOLD + " Rank " + levelRank.getLevelRank(i).getDisplay() + i + ChatColor.DARK_GRAY + " »" + levelRank.getLevelRank(i+1).getDisplay() + " " + (i+1));
                    }

                    items.add(nextStep.build());
                    startLevel = 0;
                    continue;
                }

                boolean hasPassed = false;
                boolean current = false;

                if (tempPlayer.getPlayerLevel().getLevelRank() > i)
                    hasPassed = true;
                else if (tempPlayer.getPlayerLevel().getLevelRank() == i){
                    if (tempPlayer.getPlayerLevel().getLevel() > j) hasPassed = true;
                    else if(tempPlayer.getPlayerLevel().getLevel() == j) current = true;
                }

                int moneyLevelReward = (int) Math.round((Math.pow(j, 1.2) * i) + 0.7 * (j*500));
                String loreMessage = "";
                if(current) loreMessage = ChatColor.GOLD + "Niveau en cours";
                else if(hasPassed) loreMessage = ChatColor.GREEN + "Niveau terminé";
                else loreMessage = ChatColor.RED + "Niveau non atteint";

                String levelDisplay = levelRank.getLevelRank(i).getDisplay() + "[" + levelColor.getColorForLevel(j) + j +
                        levelRank.getLevelRank(i).getDisplay() + "]";

                ItemBuilder builder = new ItemBuilder(j % 50 == 0 && j != 0? Material.GOLD_NUGGET : (hasPassed ? Material.LIME_DYE : Material.GRAY_DYE))
                        .name(ChatColor.translateAlternateColorCodes('&', levelDisplay))
                        .lore(ChatColor.GRAY + "En atteignant ce niveau vous obtenez:",
                                ChatColor.GRAY + String.valueOf(NumbersSeparator.LanguageFormatter.USA.convert(moneyLevelReward, 3)) + " " + SkyblockInstance.COIN,
                                "",
                                loreMessage);
                if(current)
                    builder.enchant(Enchantment.DIG_SPEED)
                            .flags(ItemFlag.HIDE_ENCHANTS);

                items.add(builder.build());
            }
        }


        for(int i = 0; i < 5*9; i++){
            if(items.size() > i){
                fastInv.setItem(i, items.get(i), event -> {
                    event.setCancelled(true);
                });
            }
        }

        // INT DIVISION ROUNDS UP IF REMAINING LEVELS
        int maxPages = (maxLevel * (maxRank+1) + maxRank+1) / itemsPerPage;
        pageArrows(player, tempPlayer, fastInv, page, maxPages);

        ItemStack blocksValues = new ItemBuilder(Material.KNOWLEDGE_BOOK)
                .name(skyblockInstance.getManagerLoader().getMessageManager().getColorizedMessage("##37765D#&lGagner de l'XP"))
                .build();
        fastInv.setItem(50, blocksValues, event -> {
            event.setCancelled(true);
            skyblockInstance.getManagerLoader().getMenuManager().getXpValuesLevelSubMenu().openMainBlocksValueMenu(player);
            player.playSound(player.getLocation(), Sound.BLOCK_TRIPWIRE_CLICK_ON, 30, 30);
        });
        fastInv.open(player);
    }

    public void pageArrows(Player player, TempPlayer tempPlayer, FastInv fastInv, int currentPage, int maxPages) {
        ItemStack current = new ItemBuilder(Material.SUNFLOWER).name(ChatColor.GOLD + "Page " + ChatColor.YELLOW + String.valueOf(currentPage)).build();
        ItemStack previous = new ItemBuilder(Material.ARROW).name(ChatColor.GOLD + "Page " + ChatColor.YELLOW + String.valueOf(currentPage - 1) + ChatColor.DARK_GRAY + " (-1)").build();
        ItemStack next = new ItemBuilder(Material.ARROW).name(ChatColor.GOLD + "Page " + ChatColor.YELLOW + String.valueOf(currentPage + 1) + ChatColor.DARK_GRAY + " (+1)").build();

        ItemStack previous10 = new ItemBuilder(Material.ARROW).name(ChatColor.GOLD + "Page " + ChatColor.YELLOW + String.valueOf(currentPage - 10) + ChatColor.DARK_GRAY + " (-10)").build();
        ItemStack next10 = new ItemBuilder(Material.ARROW).name(ChatColor.GOLD + "Page " + ChatColor.YELLOW + String.valueOf(currentPage + 10) + ChatColor.DARK_GRAY + " (+10)").build();

        if (currentPage >= 10) fastInv.setItem(45, previous10, event -> menu(player, tempPlayer, currentPage - 10));
        if (currentPage != 0) fastInv.setItem(46, previous, event -> menu(player, tempPlayer, currentPage - 1));
        fastInv.setItem(49, current);
        if(currentPage+1 <= maxPages) fastInv.setItem(52, next, event -> menu(player, tempPlayer, currentPage+1));
        if(currentPage+10 <= maxPages) fastInv.setItem(53, next10, event -> menu(player, tempPlayer, currentPage+10));

    }


}
