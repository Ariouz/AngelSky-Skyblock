package fr.angelsky.skyblock.menus.store;

import fr.angelsky.angelskyapi.api.accounts.TempPlayerAccount;
import fr.angelsky.angelskyapi.api.enums.rank.Rank;
import fr.angelsky.angelskyapi.api.utils.builder.ItemBuilder;
import fr.angelsky.angelskyapi.api.utils.time.TimeUnit;
import fr.angelsky.skyblock.SkyblockInstance;
import fr.angelsky.skyblock.managers.server.crates.CrateKey;
import fr.mrmicky.fastinv.FastInv;
import io.th0rgal.oraxen.api.OraxenItems;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class StoreMenu {

    private final SkyblockInstance skyblockInstance;

    public StoreMenu(SkyblockInstance skyblockInstance){
        this.skyblockInstance = skyblockInstance;
    }

    public void openStoreMenu(Player player){
        FastInv fastInv = new FastInv(5*9, "» Boutique");

        ItemStack border = new ItemBuilder(Material.CYAN_STAINED_GLASS_PANE).setDisplayName(ChatColor.RED+"").build();
        fastInv.setItems(fastInv.getBorders(), border, event -> event.setCancelled(true));

        ItemStack ranks = new ItemBuilder(Material.NETHERITE_CHESTPLATE)
                .setDisplayName(ChatColor.translateAlternateColorCodes('&', "&e&lGrades"))
                .setLore(ChatColor.translateAlternateColorCodes('&', "&a"),
                        ChatColor.translateAlternateColorCodes('&', "&7» Découvrez les grades disponibles:"),
                        ChatColor.translateAlternateColorCodes('&', "&7»   &f• &7Obtenez des avantages"),
                        ChatColor.translateAlternateColorCodes('&', "&7»   &f• &7Accédez à de nouvelles commandes"),
                        ChatColor.translateAlternateColorCodes('&', "&7»   &f• &7Améliorez votre île"),
                        ChatColor.translateAlternateColorCodes('&', "&7"))
                .addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
                .build();

        ItemStack keys = new ItemBuilder(OraxenItems.getItemById("angelic_crate_key").build())
                .setDisplayName(ChatColor.translateAlternateColorCodes('&', "&f&lClés de box"))
                .setLore(Arrays.asList(ChatColor.translateAlternateColorCodes('&', "&a"),
                        ChatColor.translateAlternateColorCodes('&', "&7» Découvrez les clés de box:"),
                        ChatColor.translateAlternateColorCodes('&', "&7»   &f• &7Ouvrez les au spawn"),
                        ChatColor.translateAlternateColorCodes('&', "&7»   &f• &7Obtenez des items inédits"),
                        ChatColor.translateAlternateColorCodes('&', "&7»   &f• &7Revendez les ou améliorez votre île"),
                        ChatColor.translateAlternateColorCodes('&', "&7")))
                .build();

        fastInv.setItem(21, ranks, event -> {
            event.setCancelled(true);
            openRanksMenu(player);
            player.playSound(player.getLocation(), Sound.BLOCK_TRIPWIRE_CLICK_ON, 30, 30);
        });

        fastInv.setItem(23, keys, event -> {
            event.setCancelled(true);
            openCrateKeysMenu(player);
            player.playSound(player.getLocation(), Sound.BLOCK_TRIPWIRE_CLICK_ON, 30, 30);
        });

        ItemBuilder back = new ItemBuilder(Material.ARROW)
                .setDisplayName(ChatColor.translateAlternateColorCodes('&', "&cRetour"));
        fastInv.setItem(fastInv.getInventory().getSize()-1, back.build(), event -> skyblockInstance.getManagerLoader().getMenuManager().getMainMenu().menu(player, skyblockInstance.getTempAccounts().get(player.getName())));
        fastInv.open(player);
    }

    public void openRanksMenu(Player player){
        FastInv fastInv = new FastInv(5*9, "» Grades");

        ItemStack border = new ItemBuilder(Material.ORANGE_STAINED_GLASS_PANE).setDisplayName(ChatColor.RED+"").build();
        fastInv.setItems(fastInv.getBorders(), border, event -> event.setCancelled(true));

        ItemStack angel = new ItemBuilder(Material.DIAMOND_CHESTPLATE)
                .setDisplayName(skyblockInstance.getManagerLoader().getMessageManager().getColorizedMessage("&9&lAnge"))
                .setLore(
                        ChatColor.translateAlternateColorCodes('&', "&a"),
                        skyblockInstance.getManagerLoader().getMessageManager().getColorizedMessage("##094A85#» ##3D85C6#&lAvantages:"),
                        skyblockInstance.getManagerLoader().getMessageManager().getColorizedMessage("##094A85#»   &f• ##c8c8ca#Message en &9couleur ##c8c8ca#dans le chat"),
                        skyblockInstance.getManagerLoader().getMessageManager().getColorizedMessage("##094A85#»   &f• ##c8c8ca#Accès à la surbrillance &9bleue ##c8c8ca#(##FBCA62#/eglow##c8c8ca#)"),
                        skyblockInstance.getManagerLoader().getMessageManager().getColorizedMessage("##094A85#»   &f• ##c8c8ca#Accès à la commande ##FBCA62#/xpbottle ##c8c8ca#(##ccb294#Cooldown 1h##c8c8ca#)"),
                        skyblockInstance.getManagerLoader().getMessageManager().getColorizedMessage("##094A85#»   &f• ##c8c8ca#Accès à la commande ##FBCA62#/stonecutter"),
                        skyblockInstance.getManagerLoader().getMessageManager().getColorizedMessage("##094A85#»   &f• ##c8c8ca#Accès à la commande ##FBCA62#/craft"),
                        skyblockInstance.getManagerLoader().getMessageManager().getColorizedMessage("##094A85#»   &f• ##c8c8ca#Accès à la commande ##FBCA62#/sell all ##c8c8ca#(##ccb294#Cooldown 15m##c8c8ca#)"),
                        ChatColor.translateAlternateColorCodes('&', "&7"),
                        skyblockInstance.getManagerLoader().getMessageManager().getColorizedMessage("##094A85#» ##3D85C6#&lPrix##094A85#: ##c8c8ca#1000 Tokens"),
                        ChatColor.translateAlternateColorCodes('&', "&7")
                )
                .addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
                .build();

        ItemStack deamon = new ItemBuilder(Material.NETHERITE_CHESTPLATE)
                .setDisplayName(skyblockInstance.getManagerLoader().getMessageManager().getColorizedMessage("&c&lDémon"))
                .setLore(
                        ChatColor.translateAlternateColorCodes('&', "&a"),
                        skyblockInstance.getManagerLoader().getMessageManager().getColorizedMessage("##D11919#» ##D52F2F#&lAvantages:"),
                        skyblockInstance.getManagerLoader().getMessageManager().getColorizedMessage("##D11919#»   &f• ##c8c8ca#Message en &ccouleur ##c8c8ca#dans le chat"),
                        skyblockInstance.getManagerLoader().getMessageManager().getColorizedMessage("##D11919#»   &f• ##c8c8ca#Accès à la surbrillance &crouge ##c8c8ca#(##FBCA62#/eglow##c8c8ca#)"),
                        skyblockInstance.getManagerLoader().getMessageManager().getColorizedMessage("##D11919#»   &f• ##c8c8ca#Accès à la commande ##FBCA62#/furnace ##c8c8ca#(##ccb294#Cooldown 5m##c8c8ca#)"),
                        skyblockInstance.getManagerLoader().getMessageManager().getColorizedMessage("##D11919#»   &f• ##c8c8ca#Accès à la commande ##FBCA62#/xpbottle ##c8c8ca#(##ccb294#Cooldown 30m##c8c8ca#)"),
                        skyblockInstance.getManagerLoader().getMessageManager().getColorizedMessage("##D11919#»   &f• ##c8c8ca#Accès à la commande ##FBCA62#/stonecutter"),
                        skyblockInstance.getManagerLoader().getMessageManager().getColorizedMessage("##D11919#»   &f• ##c8c8ca#Accès à la commande ##FBCA62#/craft"),
                        skyblockInstance.getManagerLoader().getMessageManager().getColorizedMessage("##D11919#»   &f• ##c8c8ca#Accès à la commande ##FBCA62#/sell all ##c8c8ca#(##ccb294#Cooldown 10m##c8c8ca#)"),
                        ChatColor.translateAlternateColorCodes('&', "&7"),
                        skyblockInstance.getManagerLoader().getMessageManager().getColorizedMessage("##D11919#» ##D52F2F#&lPrix##D11919#: ##c8c8ca#2000 Tokens"),
                        ChatColor.translateAlternateColorCodes('&', "&7")
                )
                .addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
                .build();

        TempPlayerAccount account = skyblockInstance.getAngelSkyApiInstance().getApiManager().getAccountManager().getAccount(player.getUniqueId());
        long expire = account.getRankExpiration();
        String display = TimeUnit.toShortDisplay(expire - new Date().getTime(), " ");

        ItemStack legend = new ItemBuilder(Material.GOLDEN_CHESTPLATE)
                .setDisplayName(ChatColor.translateAlternateColorCodes('&', "&6&lLégende - 1 Mois"))
                .setLore(
                        ChatColor.translateAlternateColorCodes('&', "&a"),
                        skyblockInstance.getManagerLoader().getMessageManager().getColorizedMessage("##CF8232#» ##F1AA32#&lAvantages:"),
                        skyblockInstance.getManagerLoader().getMessageManager().getColorizedMessage("##CF8232#»   &f• ##1CE28D#Pas de perte ##c8c8ca#d'inventaire à la mort"),
                        skyblockInstance.getManagerLoader().getMessageManager().getColorizedMessage("##CF8232#»   &f• ##1CE28D#Pas de perte ##c8c8ca#d'expérience à la mort"),
                        skyblockInstance.getManagerLoader().getMessageManager().getColorizedMessage("##CF8232#»   &f• ##c8c8ca#Message en &6couleur ##c8c8ca#dans le chat"),
                        skyblockInstance.getManagerLoader().getMessageManager().getColorizedMessage("##CF8232#»   &f• ##c8c8ca#Accès à la surbrillance &6orange ##c8c8ca#(##FBCA62#/eglow##c8c8ca#)"),
                        skyblockInstance.getManagerLoader().getMessageManager().getColorizedMessage("##CF8232#»   &f• ##c8c8ca#Accès à la commande ##FBCA62#/fly"),
                        skyblockInstance.getManagerLoader().getMessageManager().getColorizedMessage("##CF8232#»   &f• ##c8c8ca#Accès à la commande ##FBCA62#/furnace"),
                        skyblockInstance.getManagerLoader().getMessageManager().getColorizedMessage("##CF8232#»   &f• ##c8c8ca#Accès à la commande ##FBCA62#/xpbottle ##c8c8ca#(##ccb294#Cooldown 15m##c8c8ca#)"),
                        skyblockInstance.getManagerLoader().getMessageManager().getColorizedMessage("##CF8232#»   &f• ##c8c8ca#Accès à la commande ##FBCA62#/stonecutter"),
                        skyblockInstance.getManagerLoader().getMessageManager().getColorizedMessage("##CF8232#»   &f• ##c8c8ca#Accès à la commande ##FBCA62#/craft"),
                        skyblockInstance.getManagerLoader().getMessageManager().getColorizedMessage("##CF8232#»   &f• ##c8c8ca#Accès à la commande ##FBCA62#/sell all"),
                        ChatColor.translateAlternateColorCodes('&', "&7"),
                        skyblockInstance.getManagerLoader().getMessageManager().getColorizedMessage("##CF8232#» ##F1AA32#&lPrix##CF8232#: ##c8c8ca#1500 Tokens"),
                        ChatColor.translateAlternateColorCodes('&', "&7")
                )
                .addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
                .build();

        List<String> lore = legend.getItemMeta().getLore();
        if(account.getRank() == Rank.LEGEND){
            assert lore != null;
            lore.add(skyblockInstance.getManagerLoader().getMessageManager().getColorizedMessage("##CF8232#» ##c8c8ca#Votre grade expire dans ##1CE28D#"+display));
        }
        legend = new ItemBuilder(legend).setLore(lore).build();

        fastInv.setItem(20, angel, event -> {
            event.setCancelled(true);
            buyRank(player, Rank.ANGEL, 1000, -1);
        });

        fastInv.setItem(22, deamon, event -> {
            event.setCancelled(true);
            buyRank(player, Rank.DEMON, 2000, -1);
        });

        fastInv.setItem(24, legend, event -> {
            event.setCancelled(true);
            buyRank(player, Rank.LEGEND, 1500, 1000L * 60 * 60 * 24 * 30);
        });

        ItemBuilder back = new ItemBuilder(Material.ARROW)
                .setDisplayName(ChatColor.translateAlternateColorCodes('&', "&cRetour"));
        fastInv.setItem(fastInv.getInventory().getSize()-1, back.build(), event -> this.openStoreMenu(player));
        fastInv.open(player);
    }

    public void openCrateKeysMenu(Player player){
        FastInv fastInv = new FastInv(5*9, "» Clés");

        ItemStack border = new ItemBuilder(Material.GREEN_STAINED_GLASS_PANE).setDisplayName(ChatColor.RED+"").build();
        fastInv.setItems(fastInv.getBorders(), border, event -> event.setCancelled(true));

        for(CrateKey crateKey : CrateKey.values()){
            if (!crateKey.isInStore()) continue;
            ItemStack item = new ItemBuilder(OraxenItems.getItemById(crateKey.getKeyId()).build())
                    .setLore(
                            ChatColor.translateAlternateColorCodes('&', "&a"),
                            skyblockInstance.getManagerLoader().getMessageManager().getColorizedMessage("##CF8232#»   &f• ##CF8232#Permet d'ouvrir une caisse ##1CE28D#"+crateKey.getDisplay()),
                            ChatColor.translateAlternateColorCodes('&', "&7"),
                            skyblockInstance.getManagerLoader().getMessageManager().getColorizedMessage("##CF8232#» ##F1AA32#&lPrix##CF8232#: ##c8c8ca#"+crateKey.getTokenPrice()+" Tokens"),
                            ChatColor.translateAlternateColorCodes('&', "&7")
                    )
                    .addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
                    .build();

            fastInv.setItem(crateKey.getStoreSlot(), item, event -> {
                event.setCancelled(true);
                buyCrateKey(player, crateKey, crateKey.getTokenPrice());
            });
        }

        ItemBuilder back = new ItemBuilder(Material.ARROW)
                .setDisplayName(ChatColor.translateAlternateColorCodes('&', "&cRetour"));
        fastInv.setItem(fastInv.getInventory().getSize()-1, back.build(), event -> this.openStoreMenu(player));
        fastInv.open(player);
    }

    public void buyRank(Player player, Rank rank, int price, long expiration){
        TempPlayerAccount tempPlayerAccount = skyblockInstance.getAngelSkyApiInstance().getApiManager().getAccountManager().getAccount(player.getUniqueId());

        if (tempPlayerAccount.getRank().getPower() > rank.getPower()){
            player.sendMessage(skyblockInstance.getManagerLoader().getMessageManager().getColorizedMessage(SkyblockInstance.PREFIX + "&cVous possédez déja un grade supérieur."));
            player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 30, 30);
            return;
        }

        if (tempPlayerAccount.getTokens() < price){
            player.sendMessage(skyblockInstance.getManagerLoader().getMessageManager().getColorizedMessage(SkyblockInstance.PREFIX + "&cVous n'avez pas suffisament de tokens pour acheter ce grade."));
            player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 30, 30);
            return;
        }

        if (tempPlayerAccount.getRank() == rank){
            if (tempPlayerAccount.getRankExpiration() != -1){
                int addedDays = (int) (expiration/1000) / 60 / 60 / 24;

                tempPlayerAccount.addRankExpiration(expiration);
                player.sendMessage(skyblockInstance.getManagerLoader().getMessageManager().getColorizedMessage(SkyblockInstance.PREFIX + addedDays +" jours ont été ajoutés à votre grade!"));
                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 30, 30);
            }else{
                player.sendMessage(skyblockInstance.getManagerLoader().getMessageManager().getColorizedMessage(SkyblockInstance.PREFIX + "&cVous possédez déja ce grade."));
                player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 30, 30);
            }
            return;
        }

        int addedDays = (int) (expiration/1000) / 60 / 60 / 24;
        if(addedDays == 0){
            tempPlayerAccount.setRankExpiration(-1);
            player.sendMessage(skyblockInstance.getManagerLoader().getMessageManager().getColorizedMessage(SkyblockInstance.PREFIX + "Vous avez acheté le grade "+rank.getDisplay()+"&f."));
            Bukkit.getOnlinePlayers().forEach(pls -> pls.sendMessage(skyblockInstance.getManagerLoader().getMessageManager().getColorizedMessage(SkyblockInstance.PREFIX + player.getName() + " a acheté le grade "+rank.getDisplay())));
        }else{
            tempPlayerAccount.setRankExpiration(new Date().getTime() + expiration);
            player.sendMessage(skyblockInstance.getManagerLoader().getMessageManager().getColorizedMessage(SkyblockInstance.PREFIX + "Vous avez acheté le grade "+rank.getDisplay()+"&fpendant &a"+addedDays+" &fjours."));
            Bukkit.getOnlinePlayers().forEach(pls -> pls.sendMessage(skyblockInstance.getManagerLoader().getMessageManager().getColorizedMessage(SkyblockInstance.PREFIX + player.getName() + " a acheté le grade "+rank.getDisplay()+"&fpendant &a"+addedDays+" &fjours.")));
        }

        tempPlayerAccount.setRank(rank);
        tempPlayerAccount.removeTokens(price);
        skyblockInstance.getAngelSkyApiInstance().getApiManager().getAccountManager().updatePlayer(player, rank);
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 30, 30);
    }

    public void buyCrateKey(Player player, CrateKey crateKey, int price){
        TempPlayerAccount tempPlayerAccount = skyblockInstance.getAngelSkyApiInstance().getApiManager().getAccountManager().getAccount(player.getUniqueId());

        if (tempPlayerAccount.getTokens() < price){
            player.sendMessage(skyblockInstance.getManagerLoader().getMessageManager().getColorizedMessage(SkyblockInstance.PREFIX + "&cVous n'avez pas suffisament de tokens pour acheter cette clé."));
            player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 30, 30);
            return;
        }

        player.sendMessage(skyblockInstance.getManagerLoader().getMessageManager().getColorizedMessage(SkyblockInstance.PREFIX + "Vous avez acheté une clé de caisse ##1CE28D#"+crateKey.getDisplay()+"&f."));
        Bukkit.getOnlinePlayers().forEach(pls -> pls.sendMessage(skyblockInstance.getManagerLoader().getMessageManager().getColorizedMessage(SkyblockInstance.PREFIX + player.getName() + " a acheté une clé de caisse ##1CE28D#"+crateKey.getDisplay())));

        boolean dropped = false;
        if(!player.getInventory().addItem(OraxenItems.getItemById(crateKey.getKeyId()).build()).isEmpty()){
            player.getWorld().dropItem(player.getLocation(), OraxenItems.getItemById(crateKey.getKeyId()).build());
            dropped = true;
        }

        if (dropped) player.sendMessage(skyblockInstance.getManagerLoader().getMessageManager().getColorizedMessage(SkyblockInstance.PREFIX + "Des items ont été jetés au sol car vous n'aviez pas suffisament de place dans votre inventaire."));

        tempPlayerAccount.removeTokens(price);
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 30, 30);
    }

}