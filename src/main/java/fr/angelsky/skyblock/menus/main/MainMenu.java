package fr.angelsky.skyblock.menus.main;

import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI;
import dev.dbassett.skullcreator.SkullCreator;
import fr.angelsky.angelskyapi.api.utils.HexColors;
import fr.angelsky.skyblock.SkyblockInstance;
import fr.angelsky.skyblockapi.accounts.TempPlayer;
import fr.mrmicky.fastinv.FastInv;
import fr.mrmicky.fastinv.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;

public class MainMenu {

    private final SkyblockInstance skyblockInstance;

    public MainMenu(SkyblockInstance skyblockInstance) {
       this.skyblockInstance = skyblockInstance;
    }

    @SuppressWarnings("deprecation")
    public void menu(Player player, TempPlayer tempPlayer){
        FastInv fastInv = new FastInv(9*5, "» Menu Principal");

        for (int i : fastInv.getBorders()) {
            fastInv.setItem(i, new ItemBuilder(Material.BLUE_STAINED_GLASS_PANE).name(ChatColor.BLUE + "").build(), event -> event.setCancelled(true));
        }

        ItemStack islandPanel = new ItemBuilder(Material.GRASS_BLOCK)
                .name(skyblockInstance.getManagerLoader().getMessageManager().getColorizedMessage(HexColors.LIGHT_GREEN + "&lMenu d'Île"))
                .lore(
                        ChatColor.translateAlternateColorCodes('&', "&a"),
                        ChatColor.translateAlternateColorCodes('&', "&7» Ouvrez le menu de votre île"),
                        ChatColor.translateAlternateColorCodes('&', "&7»   &f• &7Pour accéder à toutes ses fonctionalités"),
                        ChatColor.translateAlternateColorCodes('&', "&7»   &f• &7Modifier ses paramètres"),
                        ChatColor.translateAlternateColorCodes('&', "&7")
                        )
                .build();

        ItemStack levels = new ItemBuilder(Material.EXPERIENCE_BOTTLE)
                .name(skyblockInstance.getManagerLoader().getMessageManager().getColorizedMessage(HexColors.SMOOTH_BLUE + "&lNiveaux"))
                .lore(ChatColor.translateAlternateColorCodes('&', "&a"),
                        ChatColor.translateAlternateColorCodes('&', "&7» Découvrez les niveaux disponibles:"),
                        ChatColor.translateAlternateColorCodes('&', "&7»   &f• &7150 Niveaux par palier"),
                        ChatColor.translateAlternateColorCodes('&', "&7»   &f• &730 Paliers"),
                        ChatColor.translateAlternateColorCodes('&', "&7»   &f• &7"+(150*30)+" Niveaux au total"),
                        ChatColor.translateAlternateColorCodes('&', "&7»   &f• &7Débloquez de nouveaux crafts et items"),
                        ChatColor.translateAlternateColorCodes('&', "&7»   &f• &7Découvrez des zones communes"),
                        ChatColor.translateAlternateColorCodes('&', "&7")
                )
                .build();

        ItemStack store = new ItemBuilder(Material.GOLD_INGOT)
                .name(skyblockInstance.getManagerLoader().getMessageManager().getColorizedMessage(HexColors.SMOOTH_GOLD + "&lBoutique"))
                .lore(ChatColor.translateAlternateColorCodes('&', "&a"),
                        ChatColor.translateAlternateColorCodes('&', "&7» Ouvrez la boutique pour acheter:"),
                        ChatColor.translateAlternateColorCodes('&', "&7»   &f• &7Des grades"),
                        ChatColor.translateAlternateColorCodes('&', "&7»   &f• &7Des clés de box"),
                        ChatColor.translateAlternateColorCodes('&', "&7» Et soutenir le serveur"),
                        ChatColor.translateAlternateColorCodes('&', "&7")
                )
                .enchant(Enchantment.DIG_SPEED)
                .flags(ItemFlag.HIDE_ENCHANTS)
                .build();

        ItemStack shop = new ItemBuilder(Material.CHEST_MINECART)
                .name(skyblockInstance.getManagerLoader().getMessageManager().getColorizedMessage(HexColors.LIGHTER_GRAY + "&lShop"))
                .lore(ChatColor.translateAlternateColorCodes('&', "&a"),
                        ChatColor.translateAlternateColorCodes('&', "&7» Ouvrez le pour acheter et vendre:"),
                        ChatColor.translateAlternateColorCodes('&', "&7»   &f• &7Des blocs de construction"),
                        ChatColor.translateAlternateColorCodes('&', "&7»   &f• &7De la nourriture"),
                        ChatColor.translateAlternateColorCodes('&', "&7»   &f• &7De la décoration"),
                        ChatColor.translateAlternateColorCodes('&', "&7»   &f• &7Des loots"),
                        ChatColor.translateAlternateColorCodes('&', "&7»   &f• &7Des outils"),
                        ChatColor.translateAlternateColorCodes('&', "&7")
                )
                .build();

        ItemStack coalitions = new ItemBuilder(Material.SHIELD)
                .name(skyblockInstance.getManagerLoader().getMessageManager().getColorizedMessage("##EE587A#&lCoalitions"))
                .lore(ChatColor.translateAlternateColorCodes('&', "&a"),
                        ChatColor.translateAlternateColorCodes('&', "&7» Ouvrez le menu des coalitions"),
                        ChatColor.translateAlternateColorCodes('&', "&7»   &f• &7Rejoignez votre cité"),
                        ChatColor.translateAlternateColorCodes('&', "&7»   &f• &7Participez aux events"),
                        ChatColor.translateAlternateColorCodes('&', "&7»   &f• &7Complétez des quêtes supplémentaires"),
                        ChatColor.translateAlternateColorCodes('&', "&7»   &f• &7Obtenez de nouveaux items"),
                        ChatColor.translateAlternateColorCodes('&', "&7»   &f• &7Accédez à de nouvelles zones"),
                        ChatColor.translateAlternateColorCodes('&', "&7")
                )
                .build();

        ItemStack enderchest = new ItemBuilder(Material.ENDER_CHEST)
                .name(ChatColor.translateAlternateColorCodes('&', "&5&lEnderChest"))
                .lore(
                        ChatColor.translateAlternateColorCodes('&', "&a"),
                        ChatColor.translateAlternateColorCodes('&', "&7» Accedez à votre enderchest"),
                        ChatColor.translateAlternateColorCodes('&', "&7» Stockez vos items où que vous soyez"),
                        ChatColor.translateAlternateColorCodes('&', "&7")
                )
                .build();

        ItemStack warps = new ItemBuilder(Material.ENDER_EYE)
                .name(ChatColor.translateAlternateColorCodes('&', "&2&lWarps"))
                .lore(
                        ChatColor.translateAlternateColorCodes('&', "&a"),
                        ChatColor.translateAlternateColorCodes('&', "&7» Téléportez vous aux différents warps:"),
                        ChatColor.translateAlternateColorCodes('&', "&7»   &f• &7Tutoriel"),
                        ChatColor.translateAlternateColorCodes('&', "&7»   &f• &7Caisses"),
                        ChatColor.translateAlternateColorCodes('&', "&7»   &f• &7HDV"),
                        ChatColor.translateAlternateColorCodes('&', "&7")
                )
                .build();

        ItemStack quests = new ItemBuilder(Material.WRITABLE_BOOK)
                .name(skyblockInstance.getManagerLoader().getMessageManager().getColorizedMessage(HexColors.SMOOTH_RED + "&lQuêtes"))
                .lore(
                        ChatColor.translateAlternateColorCodes('&', "&a"),
                        ChatColor.translateAlternateColorCodes('&', "&7» Observez vos quêtes en cours"),
                        ChatColor.translateAlternateColorCodes('&', "&7» Terminez les et gagnez:"),
                        ChatColor.translateAlternateColorCodes('&', "&7»   &f• &7Des items"),
                        ChatColor.translateAlternateColorCodes('&', "&7»   &f• &7Des AngelCoins"),
                        ChatColor.translateAlternateColorCodes('&', "&7»   &f• &7Des jetons d'amélioration"),
                        ChatColor.translateAlternateColorCodes('&', "&7»   &f• &7L'accès à de nouvelles quêtes"),
                        ChatColor.translateAlternateColorCodes('&', "&7»   &f• &7Des points de quête"),
                        ChatColor.translateAlternateColorCodes('&', "&7")
                )
                .build();

        ItemStack discord = SkullCreator.itemFromUrl("https://textures.minecraft.net/texture/3664e54e76287e3fe2bd397c098ee7a4bd0f9c88f939fde8bab78c3271a4618f");
        SkullMeta discordMeta = (SkullMeta) discord.getItemMeta();
        discordMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&9&lServeur Discord"));
        discordMeta.setLore(
                Arrays.asList(
                        ChatColor.translateAlternateColorCodes('&', "&a"),
                        ChatColor.translateAlternateColorCodes('&', "&7» Rejoignez notre serveur discord:"),
                        ChatColor.translateAlternateColorCodes('&', "&7»   &f• &7Soyez notifié des annonces"),
                        ChatColor.translateAlternateColorCodes('&', "&7»   &f• &7Obtenez les résultats des classements"),
                        ChatColor.translateAlternateColorCodes('&', "&7»   &f• &7Discutez avec les autres joueurs"),
                        ChatColor.translateAlternateColorCodes('&', "&7»   &f• &7Obtenez de l'aide"),
                        ChatColor.translateAlternateColorCodes('&', "&7»   &f• &7Tenez vous informé des nouveautés"),
                        ChatColor.translateAlternateColorCodes('&', "&7")
                ));
        discord.setItemMeta(discordMeta);

        ItemStack vote = new ItemBuilder(Material.TRIPWIRE_HOOK)
                .name(ChatColor.translateAlternateColorCodes('&', "&f&lVoter"))
                .lore(
                        ChatColor.translateAlternateColorCodes('&', "&a"),
                        ChatColor.translateAlternateColorCodes('&', "&7» Votez sur notre site:"),
                        ChatColor.translateAlternateColorCodes('&', "&7»   &f• &7Gagnez des clés de caisse"),
                        ChatColor.translateAlternateColorCodes('&', "&7»   &f• &7Obtenez des tokens"),
                        ChatColor.translateAlternateColorCodes('&', "&7»   &f• &7Grimpez dans le classement"),
                        ChatColor.translateAlternateColorCodes('&', "&7»   &f• &7Soutenez le serveur"),
                        ChatColor.translateAlternateColorCodes('&', "&7")
                )
                .build();

        fastInv.setItem(11, islandPanel, event -> {
            if(tempPlayer.getIsland() == null){
                skyblockInstance.getManagerLoader().getMenuManager().getIslandCreationClassMenu().menu(player, tempPlayer);
                return;
            }
            SuperiorSkyblockAPI.getMenus().openControlPanel(tempPlayer.getSuperiorPlayer(), null, tempPlayer.getIsland());
            player.playSound(player.getLocation(), Sound.BLOCK_TRIPWIRE_CLICK_ON, 30, 30);
        });

        fastInv.setItem(23, levels, event -> {
            skyblockInstance.getManagerLoader().getMenuManager().getLevelsMenu().menu(player, tempPlayer, -1);
            player.playSound(player.getLocation(), Sound.BLOCK_TRIPWIRE_CLICK_ON, 30, 30);
        });

        fastInv.setItem(15, store, event -> {
            skyblockInstance.getManagerLoader().getMenuManager().getStoreMenu().openStoreMenu(player);
            player.playSound(player.getLocation(), Sound.BLOCK_TRIPWIRE_CLICK_ON, 30, 30);
        });

        fastInv.setItem(13, shop, event -> {
            skyblockInstance.getManagerLoader().getShopManager().getShopMenu().mainMenu(player);
            player.playSound(player.getLocation(), Sound.BLOCK_TRIPWIRE_CLICK_ON, 30, 30);
        });

        fastInv.setItem(25, coalitions, event -> {
            Bukkit.dispatchCommand(player, "coalitions");
            player.playSound(player.getLocation(), Sound.BLOCK_TRIPWIRE_CLICK_ON, 30, 30);
        });

        fastInv.setItem(31, enderchest, event -> {
            player.openInventory(player.getEnderChest());
            player.playSound(player.getLocation(), Sound.BLOCK_TRIPWIRE_CLICK_ON, 30, 30);
        });

        fastInv.setItem(19, warps, event -> {
            player.sendMessage(ChatColor.RED + "Warps TODO");
            player.playSound(player.getLocation(), Sound.BLOCK_TRIPWIRE_CLICK_ON, 30, 30);
        });

        fastInv.setItem(21, quests, event -> {
            player.sendMessage(ChatColor.RED + "Quetes TODO");
            player.playSound(player.getLocation(), Sound.BLOCK_TRIPWIRE_CLICK_ON, 30, 30);
        });

        fastInv.setItem(33, vote, event -> {
            player.closeInventory();
            player.performCommand("vote");
            player.playSound(player.getLocation(), Sound.BLOCK_TRIPWIRE_CLICK_ON, 30, 30);
        });

        fastInv.setItem(29, discord, event -> {
            player.closeInventory();
            player.performCommand("discord");
            player.playSound(player.getLocation(), Sound.BLOCK_TRIPWIRE_CLICK_ON, 30, 30);
        });

        fastInv.open(player);
    }

}
