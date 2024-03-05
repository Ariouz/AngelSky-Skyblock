package fr.angelsky.skyblock.commands.player.misc;

import fr.angelsky.skyblock.SkyblockInstance;
import fr.angelsky.skyblock.menus.shop.ShopItem;
import fr.angelsky.skyblock.menus.shop.ShopManager;
import fr.angelsky.skyblockapi.accounts.TempPlayer;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SellCommand implements CommandExecutor, TabCompleter {

    private final SkyblockInstance skyblockInstance;

    public SellCommand(SkyblockInstance skyblockInstance){
        this.skyblockInstance = skyblockInstance;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Seul un joueur peut executer cette commande! ");
            return false;
        }

        Player player = (Player)sender;
        TempPlayer tempPlayer = skyblockInstance.getTempAccounts().get(player.getName());

        if (args.length < 1) {
            skyblockInstance.getManagerLoader().getShopManager().getShopMenu().mainMenu(player);
            return true;
        }

        ShopManager shopManager = skyblockInstance.getManagerLoader().getShopManager();
        ItemStack item = player.getInventory().getItemInMainHand();
        ShopItem shopItem = shopManager.getShopItem(item.getType());

        if (shopItem == null || !shopItem.isCanBeSold())
        {
            player.sendMessage(ChatColor.RED + "Cet item ne peut pas être vendu");
            return false;
        }
        switch (args[0]) {
            case "hand" -> {
                if (!player.hasPermission("angelsky.utils.sellhand")) {
                    player.sendMessage(ChatColor.RED + "Vous n'avez pas la permission d'executer cette commande");
                    return false;
                }
                shopManager.sellItem(shopItem, item.getAmount(), player, false);
                item.setAmount(0);
            }
            case "all" -> {
                if (!player.hasPermission("angelsky.utils.sellall")) {
                    player.sendMessage(ChatColor.RED + "Vous n'avez pas la permission d'executer cette commande");
                    return false;
                }
                // TODO COOLDOWN BASED ON PERMISISON
                shopManager.getShopMenu().sellAll(shopItem, player);
            }
            default -> {
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String args[]) {
        List<String> returns = new ArrayList<>();

        if (args.length < 1) return returns;
        returns.add("hand");
        returns.add("all");
        return returns;
    }
}
