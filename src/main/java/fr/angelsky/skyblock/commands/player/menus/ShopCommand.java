package fr.angelsky.skyblock.commands.player.menus;

import fr.angelsky.skyblock.SkyblockInstance;
import fr.angelsky.skyblock.menus.shop.ShopCategory;
import fr.angelsky.skyblock.menus.shop.ShopMenu;
import fr.angelsky.skyblockapi.accounts.TempPlayer;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ShopCommand implements CommandExecutor, TabCompleter {

    private final SkyblockInstance skyblockInstance;

    public ShopCommand(SkyblockInstance skyblockInstance){
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

        ShopCategory cat = skyblockInstance.getManagerLoader().getShopManager().getCategory(args[0]);
        ShopMenu shopMenu = skyblockInstance.getManagerLoader().getShopManager().getShopMenu();
        if (cat == null)
        {
            player.sendMessage(ChatColor.RED + "Cette catégorie n'existe pas");
            return false;
        }
        shopMenu.openCategoryMenu(shopMenu.getDisplay(), 6*9, cat, player, 0);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String args[]) {
        List<String> returns = new ArrayList<>();

        if (args.length < 1) return returns;
        String arg = args[0];
        for (ShopCategory category : skyblockInstance.getManagerLoader().getShopManager().getShopCategories().values())
        {
            if (category.getId().startsWith(arg))
                returns.add(category.getId());
        }
        return returns;
    }
}
