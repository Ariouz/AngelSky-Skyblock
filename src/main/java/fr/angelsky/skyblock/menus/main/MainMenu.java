package fr.angelsky.skyblock.menus.main;

import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI;
import com.bgsoftware.superiorskyblock.api.island.SortingType;
import dev.dbassett.skullcreator.SkullCreator;
import fr.angelsky.angelskyapi.api.utils.math.NumbersSeparator;
import fr.angelsky.skyblock.SkyblockInstance;
import fr.angelsky.skyblockapi.accounts.TempPlayer;
import fr.mrmicky.fastinv.FastInv;
import fr.mrmicky.fastinv.ItemBuilder;
import net.kyori.adventure.text.Component;
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

    public void menu(Player player, TempPlayer tempPlayer){
        FastInv fastInv = new FastInv(9*6, "» Menu Principal");

        ItemStack islandPanel = new ItemBuilder(Material.GRASS_BLOCK)
                .name(ChatColor.translateAlternateColorCodes('&', "&2&lMenu d'Île"))
                .lore(
                        ChatColor.translateAlternateColorCodes('&', "&a"),
                        ChatColor.translateAlternateColorCodes('&', "&7» Accedez au menu de votre île"),
                        ChatColor.translateAlternateColorCodes('&', "&7» Et à ses paramètres"),
                        ChatColor.translateAlternateColorCodes('&', "&7")
                        )
                .build();

        ItemStack levels = new ItemBuilder(Material.NAME_TAG)
                .name(ChatColor.translateAlternateColorCodes('&', "&a&lNiveaux"))
                .lore(ChatColor.translateAlternateColorCodes('&', "&a"),
                        ChatColor.translateAlternateColorCodes('&', "&7» Découvrez les niveaux disponibles:"),
                        ChatColor.translateAlternateColorCodes('&', "&7»   &f• &7150 Niveaux par pallier"),
                        ChatColor.translateAlternateColorCodes('&', "&7»   &f• &730 Palliers"),
                        ChatColor.translateAlternateColorCodes('&', "&7»   &f• &7"+(150*30)+" Niveaux au total"),
                        ChatColor.translateAlternateColorCodes('&', "&7")
                )
                .build();

        ItemStack store = new ItemBuilder(Material.GOLD_INGOT)
                .name(ChatColor.translateAlternateColorCodes('&', "&6&lBoutique"))
                .lore(ChatColor.translateAlternateColorCodes('&', "&a"),
                        ChatColor.translateAlternateColorCodes('&', "&7» Ouvrez la boutique pour acheter:"),
                        ChatColor.translateAlternateColorCodes('&', "&7»   &f• &7Des grades"),
                        ChatColor.translateAlternateColorCodes('&', "&7»   &f• &7Des clés de box"),
                        ChatColor.translateAlternateColorCodes('&', "&7")
                )
                .enchant(Enchantment.DIG_SPEED)
                .flags(ItemFlag.HIDE_ENCHANTS)
                .build();

        ItemStack shop = new ItemBuilder(Material.CHEST_MINECART)
                .name(ChatColor.translateAlternateColorCodes('&', "&f&lShop"))
                .lore(ChatColor.translateAlternateColorCodes('&', "&a"),
                        ChatColor.translateAlternateColorCodes('&', "&7» Ouvrez le pour acheter et vendre:"),
                        ChatColor.translateAlternateColorCodes('&', "&7»   &f• &7Des blocs"),
                        ChatColor.translateAlternateColorCodes('&', "&7»   &f• &7Des oeufs de mobs"),
                        ChatColor.translateAlternateColorCodes('&', "&7»   &f• &7De la nourriture"),
                        ChatColor.translateAlternateColorCodes('&', "&7»   &f• &7De la redstone"),
                        ChatColor.translateAlternateColorCodes('&', "&7»   &f• &7De la décoration"),
                        ChatColor.translateAlternateColorCodes('&', "&7»   &f• &7Des loots"),
                        ChatColor.translateAlternateColorCodes('&', "&7»   &f• &7..."),
                        ChatColor.translateAlternateColorCodes('&', "&7")
                )
                .build();

        ItemStack islandTop = SkullCreator.itemFromUrl("https://textures.minecraft.net/texture/438cf3f8e54afc3b3f91d20a49f324dca1486007fe545399055524c17941f4dc");
        SkullMeta islandTopMeta = (SkullMeta) islandTop.getItemMeta();
        islandTopMeta.displayName(Component.text(ChatColor.translateAlternateColorCodes('&', "&e&lTop des Îles")));
        islandTopMeta.lore(
                Arrays.asList(
                        Component.text(ChatColor.translateAlternateColorCodes('&', "&a")),
                        Component.text(ChatColor.translateAlternateColorCodes('&', "&7» Accedez au classement des Îles")),
                        Component.text(ChatColor.translateAlternateColorCodes('&', "&7»   &f• &7Top niveau d'Île")),
                        Component.text(ChatColor.translateAlternateColorCodes('&', "&7»   &f• &7Top banque d'Île")),
                        Component.text(ChatColor.translateAlternateColorCodes('&', "&7»   &f• &7Top note d'Île")),
                        Component.text(ChatColor.translateAlternateColorCodes('&', "&7»   &f• &7Top nombre de membres")),
                        Component.text(ChatColor.translateAlternateColorCodes('&', "&7"))
                ));
        islandTop.setItemMeta(islandTopMeta);

        ItemStack enderchest = new ItemBuilder(Material.ENDER_CHEST)
                .name(ChatColor.translateAlternateColorCodes('&', "&5&lEnderChest"))
                .lore(
                        ChatColor.translateAlternateColorCodes('&', "&a"),
                        ChatColor.translateAlternateColorCodes('&', "&7» Accedez à votre enderchest"),
                        ChatColor.translateAlternateColorCodes('&', "&7» Stockez vos items où que vous soyez"),
                        ChatColor.translateAlternateColorCodes('&', "&7")
                )
                .build();

        ItemStack money = new ItemBuilder(Material.ENDER_CHEST)
                .name(ChatColor.translateAlternateColorCodes('&', "&6&lAngelCoins"))
                .lore(
                        ChatColor.translateAlternateColorCodes('&', "&a"),
                        ChatColor.translateAlternateColorCodes('&', "&7» Vous avez &e" + NumbersSeparator.LanguageFormatter.USA.convert((int) skyblockInstance.getSkyBlockApiInstance().getEconomy().getBalance(player), 3) + " &r&6" + SkyblockInstance.COIN + "&7 AngelCoins"),
                        ChatColor.translateAlternateColorCodes('&', "&7» Vous pouvez en gagner:"),
                        ChatColor.translateAlternateColorCodes('&', "&7»   &f• &7En vendant au shop"),
                        ChatColor.translateAlternateColorCodes('&', "&7»   &f• &7En terminant des quêtes"),
                        ChatColor.translateAlternateColorCodes('&', "&7»   &f• &7Lors d'évenements"),
                        ChatColor.translateAlternateColorCodes('&', "&7»   &f• &7..."),
                        ChatColor.translateAlternateColorCodes('&', "&7")
                )
                .build();

        ItemStack warps = new ItemBuilder(Material.ENDER_EYE)
                .name(ChatColor.translateAlternateColorCodes('&', "&2&lWarps"))
                .lore(
                        ChatColor.translateAlternateColorCodes('&', "&a"),
                        ChatColor.translateAlternateColorCodes('&', "&7» Téléportez vous aux différents warps:"),
                        ChatColor.translateAlternateColorCodes('&', "&7»   &f• &7Tutoriel"),
                        ChatColor.translateAlternateColorCodes('&', "&7»   &f• &7Enchantement"),
                        ChatColor.translateAlternateColorCodes('&', "&7»   &f• &7Crates"),
                        ChatColor.translateAlternateColorCodes('&', "&7»   &f• &7Marché"),
                        ChatColor.translateAlternateColorCodes('&', "&7»   &f• &7..."),
                        ChatColor.translateAlternateColorCodes('&', "&7")
                )
                .build();

        ItemStack quests = new ItemBuilder(Material.WRITABLE_BOOK)
                .name(ChatColor.translateAlternateColorCodes('&', "&7&lQuêtes"))
                .lore(
                        ChatColor.translateAlternateColorCodes('&', "&a"),
                        ChatColor.translateAlternateColorCodes('&', "&7» Observez vos quêtes en cours"),
                        ChatColor.translateAlternateColorCodes('&', "&7» Terminez les et gagnez:"),
                        ChatColor.translateAlternateColorCodes('&', "&7»   &f• &7Des points de quête"),
                        ChatColor.translateAlternateColorCodes('&', "&7»   &f• &7Des &fpoints de bonté"),
                        ChatColor.translateAlternateColorCodes('&', "&7»   &f• &7Des &4points de démon"),
                        ChatColor.translateAlternateColorCodes('&', "&7»   &f• &7Des récompenses"),
                        ChatColor.translateAlternateColorCodes('&', "&7»   &f• &7L'accès à de nouvelles quêtes"),
                        ChatColor.translateAlternateColorCodes('&', "&7»   &f• &7..."),
                        ChatColor.translateAlternateColorCodes('&', "&7")
                )
                .build();


        ItemStack discord = SkullCreator.itemFromUrl("https://textures.minecraft.net/texture/3664e54e76287e3fe2bd397c098ee7a4bd0f9c88f939fde8bab78c3271a4618f");
        SkullMeta discordMeta = (SkullMeta) discord.getItemMeta();
        discordMeta.displayName(Component.text(ChatColor.translateAlternateColorCodes('&', "&9&lServeur Discord")));
        discordMeta.lore(
                Arrays.asList(
                        Component.text(ChatColor.translateAlternateColorCodes('&', "&a")),
                        Component.text(ChatColor.translateAlternateColorCodes('&', "&7» Rejoignez notre serveur discord:")),
                        Component.text(ChatColor.translateAlternateColorCodes('&', "&7»   &f• &7Soyez notifié des annonces")),
                        Component.text(ChatColor.translateAlternateColorCodes('&', "&7»   &f• &7Obtenez les résultats des classements")),
                        Component.text(ChatColor.translateAlternateColorCodes('&', "&7»   &f• &7Discutez avec les autres joueurs")),
                        Component.text(ChatColor.translateAlternateColorCodes('&', "&7»   &f• &7Obtenez de l'aide")),
                        Component.text(ChatColor.translateAlternateColorCodes('&', "&7»   &f• &7...")),
                        Component.text(ChatColor.translateAlternateColorCodes('&', "&7"))
                ));
        discord.setItemMeta(discordMeta);

        ItemStack vote = new ItemBuilder(Material.TRIPWIRE_HOOK)
                .name(ChatColor.translateAlternateColorCodes('&', "&f&lVoter"))
                .lore(
                        ChatColor.translateAlternateColorCodes('&', "&a"),
                        ChatColor.translateAlternateColorCodes('&', "&7» Votez sur notre site:"),
                        ChatColor.translateAlternateColorCodes('&', "&7»   &f• &7Obtenez des points de vote"),
                        ChatColor.translateAlternateColorCodes('&', "&7»   &f• &7Gagnez des clés de box"),
                        ChatColor.translateAlternateColorCodes('&', "&7»   &f• &7Grimpez dans le classement"),
                        ChatColor.translateAlternateColorCodes('&', "&7»   &f• &7Soutenez le serveur"),
                        ChatColor.translateAlternateColorCodes('&', "&7»   &f• &7Obtenez des &fpoints de bonté"),
                        ChatColor.translateAlternateColorCodes('&', "&7»   &f• &7..."),
                        ChatColor.translateAlternateColorCodes('&', "&7")
                )
                .build();

        fastInv.setItem(22, islandPanel, event -> {
            if(tempPlayer.getIsland() == null){
                skyblockInstance.getManagerLoader().getMenuManager().getIslandCreationClassMenu().menu(player, tempPlayer);
                return;
            }
            SuperiorSkyblockAPI.getMenus().openControlPanel(tempPlayer.getSuperiorPlayer(), null, tempPlayer.getIsland());
            player.playSound(player.getLocation(), Sound.BLOCK_TRIPWIRE_CLICK_ON, 30, 30);
        });

        fastInv.setItem(27, levels, event -> {
            skyblockInstance.getManagerLoader().getMenuManager().getLevelsMenu().menu(player, tempPlayer, 0);
            player.playSound(player.getLocation(), Sound.BLOCK_TRIPWIRE_CLICK_ON, 30, 30);
        });

        fastInv.setItem(29, store, event -> {
            skyblockInstance.getManagerLoader().getMenuManager().getStoreMenu().openStoreMenu(player);
            player.playSound(player.getLocation(), Sound.BLOCK_TRIPWIRE_CLICK_ON, 30, 30);
        });

        fastInv.setItem(33, shop, event -> {
            skyblockInstance.getManagerLoader().getShopManager().getShopMenu().mainMenu(player);
            player.playSound(player.getLocation(), Sound.BLOCK_TRIPWIRE_CLICK_ON, 30, 30);
        });

        fastInv.setItem(16, islandTop, event -> {
            SuperiorSkyblockAPI.getMenus().openTopIslands(tempPlayer.getSuperiorPlayer(), null, SortingType.getByName(SuperiorSkyblockAPI.getSuperiorSkyblock().getSettings().getIslandTopOrder()));
            player.playSound(player.getLocation(), Sound.BLOCK_TRIPWIRE_CLICK_ON, 30, 30);
        });

        fastInv.setItem(53, enderchest, event -> {
            player.openInventory(player.getEnderChest());
            player.playSound(player.getLocation(), Sound.BLOCK_TRIPWIRE_CLICK_ON, 30, 30);
        });

        fastInv.setItem(45, money);

        fastInv.setItem(40, warps, event -> {
            player.sendMessage(ChatColor.RED + "Warps TODO");
            player.playSound(player.getLocation(), Sound.BLOCK_TRIPWIRE_CLICK_ON, 30, 30);
        });

        fastInv.setItem(10, quests, event -> {
            player.sendMessage(ChatColor.RED + "Quetes TODO");
            player.playSound(player.getLocation(), Sound.BLOCK_TRIPWIRE_CLICK_ON, 30, 30);
        });

        fastInv.setItem(48, vote, event -> {
            player.closeInventory();
            player.performCommand("vote");
            player.playSound(player.getLocation(), Sound.BLOCK_TRIPWIRE_CLICK_ON, 30, 30);
        });

        fastInv.setItem(50, discord, event -> {
            player.closeInventory();
            player.performCommand("discord");
            player.playSound(player.getLocation(), Sound.BLOCK_TRIPWIRE_CLICK_ON, 30, 30);
        });

        fastInv.open(player);
    }

}
