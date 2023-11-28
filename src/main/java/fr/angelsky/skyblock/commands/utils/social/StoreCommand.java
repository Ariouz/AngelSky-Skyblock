package fr.angelsky.skyblock.commands.utils.social;

import fr.angelsky.skyblock.SkyblockInstance;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class StoreCommand implements CommandExecutor {

    private final SkyblockInstance skyblockInstance;

    public StoreCommand(SkyblockInstance skyblockInstance){
        this.skyblockInstance = skyblockInstance;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(ChatColor.RED + "Seul un joueur peut executer cette commande! ");
            return false;
        }

        skyblockInstance.getManagerLoader().getMenuManager().getStoreMenu().openStoreMenu(player);
        return true;
    }
}
