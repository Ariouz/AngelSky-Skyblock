package fr.angelsky.skyblock.menus.island;

import fr.angelsky.skyblock.SkyblockInstance;
import fr.angelsky.skyblock.managers.ItemManager;
import fr.angelsky.skyblockapi.accounts.TempPlayer;
import fr.mrmicky.fastinv.FastInv;
import fr.mrmicky.fastinv.ItemBuilder;
import io.th0rgal.oraxen.OraxenPlugin;
import io.th0rgal.oraxen.api.OraxenItems;
import io.th0rgal.oraxen.items.ItemUpdater;
import io.th0rgal.oraxen.utils.OraxenYaml;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class IslandTokensMenu {

    public SkyblockInstance skyblockInstance;

    public IslandTokensMenu(SkyblockInstance skyblockInstance){
        this.skyblockInstance = skyblockInstance;
    }

    public void open(Player player, TempPlayer tempPlayer){
        FastInv fastInv = new FastInv(45, "» Jetons d'Amélioration");

        for(int i = 0; i < fastInv.getBorders().length; i++){
            ItemStack border = new ItemBuilder(Material.ORANGE_STAINED_GLASS_PANE).name("§c").build();
            fastInv.setItem(fastInv.getBorders()[i], border, e->e.setCancelled(true));
        }

        ItemStack withdrawOne = new ItemBuilder(Material.RED_CONCRETE)
                .name(ChatColor.translateAlternateColorCodes('&', "&cRetirer 1 Jeton"))
                .build();

        ItemStack withdrawSixteen = new ItemBuilder(Material.RED_CONCRETE)
                .name(ChatColor.translateAlternateColorCodes('&', "&cRetirer 16 Jetons"))
                .build();

        ItemStack withdrawStack = new ItemBuilder(Material.RED_CONCRETE)
                .name(ChatColor.translateAlternateColorCodes('&', "&cRetirer 64 Jetons"))
                .build();

        ItemStack depositOne = new ItemBuilder(Material.LIME_CONCRETE)
                .name(ChatColor.translateAlternateColorCodes('&', "&aDéposer 1 Jeton"))
                .build();

        ItemStack depositSixteen = new ItemBuilder(Material.LIME_CONCRETE)
                .name(ChatColor.translateAlternateColorCodes('&', "&aDéposer 16 Jetons"))
                .build();

        ItemStack depositStack = new ItemBuilder(Material.LIME_CONCRETE)
                .name(ChatColor.translateAlternateColorCodes('&', "&aDéposer 64 Jetons"))
                .build();

        ItemStack currentPoints = new ItemBuilder(OraxenItems.getItemById("upgrade_token").build())
                .name(ChatColor.translateAlternateColorCodes('&', "&fVous avez &e&n"+tempPlayer.getUpgradeTokens() + "&r "+ (tempPlayer.getUpgradeTokens() > 1 ? "Jetons" : "Jeton") + " d'Amélioration")).build();


        fastInv.setItem(19, withdrawOne, e -> {
            e.setCancelled(true);
            withdraw(player, tempPlayer, 1);
        });

        fastInv.setItem(20, withdrawSixteen, e -> {
            e.setCancelled(true);
            withdraw(player, tempPlayer, 16);
        });

        fastInv.setItem(21, withdrawStack, e -> {
            e.setCancelled(true);
            withdraw(player, tempPlayer, 64);
        });


        fastInv.setItem(25, depositOne, e -> {
            deposit(player, tempPlayer, 1);
            e.setCancelled(true);
        });

        fastInv.setItem(24, depositSixteen, e -> {
            deposit(player, tempPlayer, 16);
            e.setCancelled(true);
        });

        fastInv.setItem(23, depositStack, e -> {
            deposit(player, tempPlayer, 64);
            e.setCancelled(true);
        });

        fastInv.setItem(40, currentPoints, e -> e.setCancelled(true));

        fastInv.open(player);
    }

    public void deposit(Player player, TempPlayer tempPlayer, int amount){
        ItemStack token = OraxenItems.getItemById("upgrade_token").setAmount(1).getReferenceClone();
        token = ItemUpdater.updateItem(token);

        if(player.getInventory().containsAtLeast(token, amount)){
            new ItemManager().removeItems(player, player.getInventory(), token, amount);

            tempPlayer.addUpgradeTokens(amount);
            player.sendMessage(SkyblockInstance.PREFIX + ChatColor.translateAlternateColorCodes('&', "&fVous avez déposé &e" + amount + " &fJeton" + (amount > 1 ?"s": "") + " d'Amélioration"));
            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 30, 30);

            open(player, tempPlayer);
        }else{
            player.sendMessage(SkyblockInstance.PREFIX + ChatColor.translateAlternateColorCodes('&', "&cVous n'avez pas assez de jetons."));
            player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 15, 10);
        }
    }

    public void withdraw(Player player, TempPlayer tempPlayer, int amount){
        if(tempPlayer.getUpgradeTokens() >= amount){
            ItemStack token = OraxenItems.getItemById("upgrade_token").build();
            token = ItemUpdater.updateItem(token);
            token.setAmount(amount);

            HashMap<Integer, ItemStack> toDrop = player.getInventory().addItem(token);
            if(!toDrop.isEmpty()){
                toDrop.values().forEach(item -> player.getWorld().dropItem(player.getLocation(), item));
                player.sendMessage(SkyblockInstance.PREFIX + ChatColor.translateAlternateColorCodes('&', "&cVotre inventaire est plein. Des items sont au sol"));
            }

            tempPlayer.removeUpgradeTokens(amount);
            player.sendMessage(SkyblockInstance.PREFIX + ChatColor.translateAlternateColorCodes('&', "&fVous avez retiré &e" + amount + " &fJeton" + (amount > 1 ?"s": "") + " d'Amélioration"));
            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 30, 30);

            open(player, tempPlayer);
        }else{
            player.sendMessage(SkyblockInstance.PREFIX + ChatColor.translateAlternateColorCodes('&', "&cVous n'avez pas assez de jetons."));
            player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 15, 10);
        }
    }

}
