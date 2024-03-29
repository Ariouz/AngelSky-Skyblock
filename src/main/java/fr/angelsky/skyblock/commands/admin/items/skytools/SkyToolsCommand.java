package fr.angelsky.skyblock.commands.admin.items.skytools;

import fr.angelsky.angelskyapi.api.accounts.TempPlayerAccount;
import fr.angelsky.angelskyapi.api.enums.rank.Rank;
import fr.angelsky.skyblock.SkyblockInstance;
import fr.angelsky.skyblock.managers.items.skytools.SkyTool;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

public class SkyToolsCommand implements CommandExecutor {

    private final SkyblockInstance skyblockInstance;

    public SkyToolsCommand(SkyblockInstance skyblockInstance){
        this.skyblockInstance = skyblockInstance;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage(ChatColor.RED + "Seul un joueur peut executer cette commande!");
            return false;
        }

        Player player = (Player)sender;
        TempPlayerAccount account = skyblockInstance.getAngelSkyApiInstance().getApiManager().getAccountManager().getAccount(player.getUniqueId());
        if (account.getRank().getPower() < Rank.RESPONSIBLE.getPower()){
            sender.sendMessage(ChatColor.RED + "Vous n'avez pas la permission d'executer cette commande");
            return false;
        }

        Inventory inv = Bukkit.createInventory(null, 6*9);
        for (SkyTool skyTool : skyblockInstance.getManagerLoader().getSkyToolsManager().getTools())
        {
            inv.addItem(skyTool.getItem());
        }
        player.openInventory(inv);
        return true;
    }

}
