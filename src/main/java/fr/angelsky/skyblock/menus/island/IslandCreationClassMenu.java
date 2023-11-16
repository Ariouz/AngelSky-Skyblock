package fr.angelsky.skyblock.menus.island;

import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI;
import com.bgsoftware.superiorskyblock.api.island.Island;
import com.bgsoftware.superiorskyblock.api.upgrades.Upgrade;
import fr.angelsky.skyblock.SkyblockInstance;
import fr.angelsky.skyblock.kits.IslandClassKit;
import fr.angelsky.skyblockapi.accounts.TempPlayer;
import fr.mrmicky.fastinv.FastInv;
import fr.mrmicky.fastinv.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;

import java.math.BigDecimal;

public class IslandCreationClassMenu {

    private final SkyblockInstance skyblockInstance;

    public IslandCreationClassMenu(SkyblockInstance skyblockInstance) {
        this.skyblockInstance = skyblockInstance;
    }

    public void menu(Player player, TempPlayer tempPlayer){
        FastInv inv = new FastInv(9*3, "» Classe d'Île");

        ItemBuilder kingSize = new ItemBuilder(Material.WOODEN_PICKAXE)
                .name(ChatColor.translateAlternateColorCodes('&', "&6KingSize"))
                .addLore(
                        ChatColor.translateAlternateColorCodes('&', "&7» Classe &6KingSize"),
                        ChatColor.translateAlternateColorCodes('&', "&a"),
                        ChatColor.translateAlternateColorCodes('&', "&7» Île de base"),
                        ChatColor.translateAlternateColorCodes('&', "&7» Classe:"),
                        ChatColor.translateAlternateColorCodes('&', "&7»   &f• &7Taille d'Île: &cniveau 2 (+25)"),
                        ChatColor.translateAlternateColorCodes('&', "&7»   &f• &7Kit &6KingSize&7:"),
                        ChatColor.translateAlternateColorCodes('&', "&7»     &f• &6x1 &ePioche en fer"),
                        ChatColor.translateAlternateColorCodes('&', "&7»     &f• &6x1 &eHache en pierre"),
                        ChatColor.translateAlternateColorCodes('&', "&7»     &f• &6x16 &eTerre"),
                        ChatColor.translateAlternateColorCodes('&', "&7»     &f• &6x16 &eCobblestone"),
                        ChatColor.translateAlternateColorCodes('&', "&7»     &f• &6x8 &eTorche"),
                        ChatColor.translateAlternateColorCodes('&', "&7»     &f• &6x1 &eSeau d'eau"),
                        ChatColor.translateAlternateColorCodes('&', "&7»     &f• &6x4 &eSteaks"),
                        ChatColor.translateAlternateColorCodes('&', "&7"),
                        ChatColor.translateAlternateColorCodes('&', "&7»   &f• &7Contenu du coffre de l'île")
                );

        ItemBuilder farmer = new ItemBuilder(Material.WHEAT)
                .name(ChatColor.translateAlternateColorCodes('&', "&6Farmer"))
                .addLore(
                        ChatColor.translateAlternateColorCodes('&', "&7» Classe &6Farmer"),
                        ChatColor.translateAlternateColorCodes('&', "&a"),
                        ChatColor.translateAlternateColorCodes('&', "&7» Île de base"),
                        ChatColor.translateAlternateColorCodes('&', "&7» Classe:"),
                        ChatColor.translateAlternateColorCodes('&', "&7»   &f• &7Boost Vitesse de Farm: &cniveau 2 (+5%)"),
                        ChatColor.translateAlternateColorCodes('&', "&7»   &f• &7Kit &6Fermier&7:"),
                        ChatColor.translateAlternateColorCodes('&', "&7»     &f• &6x1 &eHoue en fer"),
                        ChatColor.translateAlternateColorCodes('&', "&7»     &f• &6x8 &eGraine de blé"),
                        ChatColor.translateAlternateColorCodes('&', "&7»     &f• &6x8 &ePatates"),
                        ChatColor.translateAlternateColorCodes('&', "&7»     &f• &6x8 &eCarottes"),
                        ChatColor.translateAlternateColorCodes('&', "&7»     &f• &6x4 &eChampignon marron"),
                        ChatColor.translateAlternateColorCodes('&', "&7»     &f• &6x4 &eChampignon rouge"),
                        ChatColor.translateAlternateColorCodes('&', "&7»     &f• &6x8 &eCanne à sucre"),
                        ChatColor.translateAlternateColorCodes('&', "&7"),
                        ChatColor.translateAlternateColorCodes('&', "&7»   &f• &7Contenu du coffre de l'île")
                );

        inv.setItem(12, kingSize.build(), event -> {
            SuperiorSkyblockAPI.createIsland(tempPlayer.getSuperiorPlayer(), "island", BigDecimal.valueOf(0), Biome.PLAINS, player.getName());
            tempPlayer.getSuperiorPlayer().teleport(tempPlayer.getIsland());
            Island island = tempPlayer.getIsland();
            Upgrade upgrade = SuperiorSkyblockAPI.getUpgrades().getUpgrade("border-size");
            island.setUpgradeLevel(upgrade, 2);
            island.updateUpgrades();
            island.updateBorder();
            player.sendMessage(SkyblockInstance.PREFIX + "Votre île a été créée");
            player.sendMessage(SkyblockInstance.PREFIX + "Vous avez choisi la classe "+ChatColor.GOLD+"KingSize");
            player.closeInventory();

            skyblockInstance.getManagerLoader().getIslandClassKitGiver().give(player, IslandClassKit.KINGSIZE);
        });
        inv.setItem(14, farmer.build(), event -> {
            SuperiorSkyblockAPI.createIsland(tempPlayer.getSuperiorPlayer(), "island", BigDecimal.valueOf(0), Biome.PLAINS, player.getName());
            tempPlayer.getSuperiorPlayer().teleport(tempPlayer.getIsland());
            player.sendMessage(SkyblockInstance.PREFIX + "Votre île a été créée");
            Island island = tempPlayer.getIsland();
            Upgrade upgrade = SuperiorSkyblockAPI.getUpgrades().getUpgrade("crop-growth");
            island.setUpgradeLevel(upgrade, 2);
            island.updateUpgrades();
            island.updateBorder();
            player.sendMessage(SkyblockInstance.PREFIX + "Vous avez choisi la classe "+ChatColor.GOLD+"Farmer");
            player.closeInventory();

            skyblockInstance.getManagerLoader().getIslandClassKitGiver().give(player, IslandClassKit.FARMER);
        });


        inv.open(player);

    }

}
