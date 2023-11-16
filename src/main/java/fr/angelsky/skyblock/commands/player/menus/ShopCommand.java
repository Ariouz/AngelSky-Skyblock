package fr.angelsky.skyblock.commands.player.menus;

import fr.angelsky.skyblock.SkyblockInstance;
import fr.angelsky.skyblockapi.accounts.TempPlayer;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ShopCommand implements CommandExecutor {

    private final SkyblockInstance skyblockInstance;

    public ShopCommand(SkyblockInstance skyblockInstance){
        this.skyblockInstance = skyblockInstance;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage(ChatColor.RED + "Seul un joueur peut executer cette commande! ");
            return false;
        }

        Player player = (Player)sender;
        TempPlayer tempPlayer = skyblockInstance.getTempAccounts().get(player.getName());
        skyblockInstance.getManagerLoader().getShopManager().getShopMenu().mainMenu(player);

        return false;
    }
}
