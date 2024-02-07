package fr.angelsky.skyblock.menus.island.levels;

import fr.angelsky.angelskyapi.api.utils.HexColors;
import fr.angelsky.skyblock.SkyblockInstance;
import fr.angelsky.skyblock.managers.player.level.experience.types.BlockPlayerExperience;
import fr.angelsky.skyblock.managers.player.level.experience.types.MobPlayerExperience;
import fr.angelsky.skyblock.managers.player.level.experience.types.PlayerXpTypes;
import fr.mrmicky.fastinv.FastInv;
import fr.mrmicky.fastinv.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class BlocksValueLevelSubMenu {

    private final SkyblockInstance skyblockInstance;

    public BlocksValueLevelSubMenu(SkyblockInstance skyblockInstance){
        this.skyblockInstance = skyblockInstance;
    }

    public void openMainBlocksValueMenu(Player player){
        FastInv inv = new FastInv(5 * 9, "Gains d'Xp");

        ItemStack borders = new ItemBuilder(Material.GREEN_STAINED_GLASS_PANE).name(ChatColor.RED + "").build();
        for (int i : inv.getBorders())
            inv.setItem(i, borders);

        int slot = 20;
        for (PlayerXpTypes type : PlayerXpTypes.values()){
            ItemStack item = new ItemBuilder(type.getIcon())
                    .flags(ItemFlag.HIDE_ATTRIBUTES)
                    .name(skyblockInstance.getManagerLoader().getMessageManager().getColorizedMessage(type.getDisplayName()))
                    .build();
            inv.setItem(slot, item, event -> {
                event.setCancelled(true);
                this.openCategory(player, type);
                player.playSound(player.getLocation(), Sound.BLOCK_TRIPWIRE_CLICK_ON, 30, 30);
            });
            slot++;
        }

        ItemStack backArrow = new ItemBuilder(Material.ARROW).name(ChatColor.RED + "Retour").build();
        inv.setItem(inv.getInventory().getSize() - 1, backArrow, event -> {
            event.setCancelled(true);
            skyblockInstance.getManagerLoader().getMenuManager().getMainMenu().menu(player, skyblockInstance.getTempAccounts().get(player.getName()));
            player.playSound(player.getLocation(), Sound.BLOCK_TRIPWIRE_CLICK_ON, 30, 30);
        });

        inv.open(player);
    }

    public void openCategory(Player player, PlayerXpTypes type){
        FastInv inv = new FastInv(5 * 9, "Gains d'Xp");

        ItemStack borders = new ItemBuilder(Material.GREEN_STAINED_GLASS_PANE).name(ChatColor.RED + "").build();
        for (int i : inv.getBorders())
            inv.setItem(i, borders);

        switch (type) {
            case FARMS -> fillFarmsMenu(inv);
            case MOBS -> fillMobsMenu(inv);
            /*case FISHING -> openFarmsMenu(player);
            case ENCHANT -> openFarmsMenu(player);
            */case BLOCKS -> fillBlocksMenu(inv);
        }

        ItemStack backArrow = new ItemBuilder(Material.ARROW).name(ChatColor.RED + "Retour").build();
        inv.setItem(inv.getInventory().getSize() - 1, backArrow, event -> {
            event.setCancelled(true);
            this.openMainBlocksValueMenu(player);
            player.playSound(player.getLocation(), Sound.BLOCK_TRIPWIRE_CLICK_ON, 30, 30);
        });

        inv.open(player);
    }

    public void fillFarmsMenu(FastInv inv){
        for (BlockPlayerExperience type : BlockPlayerExperience.values()){
            if (!type.isFarm()) continue;
            ItemStack item = new ItemBuilder(type.getIcon() == null ? type.getType() : type.getIcon())
                    .name(skyblockInstance.getManagerLoader().getMessageManager().getColorizedMessage(HexColors.LIGHT_GREEN + type.getDisplay()))
                    .lore(Arrays.asList(
                            skyblockInstance.getManagerLoader().getMessageManager().getColorizedMessage(ChatColor.GRAY + ""),
                            skyblockInstance.getManagerLoader().getMessageManager().getColorizedMessage("&7Gain d'XP: " + HexColors.SMOOTH_BLUE + type.getXp()),
                            skyblockInstance.getManagerLoader().getMessageManager().getColorizedMessage("&7Chance: " + HexColors.SMOOTH_GOLD + Math.round(type.getProbability() * 100) + HexColors.DARK_GOLD + "%")
                    ))
                    .build();
            inv.addItem(item, event -> event.setCancelled(true));
        }
    }

    public void fillBlocksMenu(FastInv inv){
       for (BlockPlayerExperience type : BlockPlayerExperience.values()){
            if (type.isFarm()) continue;
            ItemStack item = new ItemBuilder(type.getIcon() == null ? type.getType() : type.getIcon())
                    .name(skyblockInstance.getManagerLoader().getMessageManager().getColorizedMessage(HexColors.LIGHT_GREEN + type.getDisplay()))
                    .lore(Arrays.asList(
                            skyblockInstance.getManagerLoader().getMessageManager().getColorizedMessage(ChatColor.GRAY + ""),
                            skyblockInstance.getManagerLoader().getMessageManager().getColorizedMessage("&7Gain d'XP: " + HexColors.SMOOTH_BLUE + type.getXp()),
                            skyblockInstance.getManagerLoader().getMessageManager().getColorizedMessage("&7Chance: " + HexColors.SMOOTH_GOLD + Math.round(type.getProbability() * 100) + HexColors.DARK_GOLD + "%")
                    ))
                    .build();
            inv.addItem(item, event -> event.setCancelled(true));
        }
    }

    public void fillMobsMenu(FastInv inv){
        for (MobPlayerExperience type : MobPlayerExperience.values()){
            ItemStack item = new ItemBuilder(type.getIcon())
                    .name(skyblockInstance.getManagerLoader().getMessageManager().getColorizedMessage(HexColors.LIGHT_GREEN + type.getDisplay()))
                    .lore(Arrays.asList(
                            skyblockInstance.getManagerLoader().getMessageManager().getColorizedMessage(ChatColor.GRAY + ""),
                            skyblockInstance.getManagerLoader().getMessageManager().getColorizedMessage("&7Gain d'XP: " + HexColors.SMOOTH_BLUE + type.getXp()),
                            skyblockInstance.getManagerLoader().getMessageManager().getColorizedMessage("&7Chance: " + HexColors.SMOOTH_GOLD + Math.round(type.getProbability() * 100) + HexColors.DARK_GOLD + "%")
                    ))
                    .flags(ItemFlag.HIDE_POTION_EFFECTS)
                    .build();
            inv.addItem(item, event -> event.setCancelled(true));
        }
    }

}
