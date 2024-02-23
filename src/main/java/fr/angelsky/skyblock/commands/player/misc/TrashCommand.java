package fr.angelsky.skyblock.commands.player.misc;

import fr.mrmicky.fastinv.FastInv;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TrashCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(ChatColor.RED + "Seul un joueur peut executer cette commande! ");
            return false;
        }

        FastInv inv = new FastInv(5*9, "Poubelle");
        /*for (int i : inv.getBorders()) {
            inv.setItem(i, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).name(ChatColor.GREEN + "").build(), event -> event.setCancelled(true));
        }*/
        player.openInventory(inv.getInventory());
        return true;
    }

}
