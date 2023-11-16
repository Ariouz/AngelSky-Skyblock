package fr.angelsky.skyblock.commands.player;

import fr.angelsky.skyblock.SkyblockInstance;
import fr.angelsky.skyblockapi.accounts.TempPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SpawnCommand implements CommandExecutor {

    private final SkyblockInstance skyblockInstance;

    public SpawnCommand(SkyblockInstance skyblockInstance){
        this.skyblockInstance = skyblockInstance;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage(SkyblockInstance.PREFIX + "Seul un joueur peut executer cette commande! ");
            return false;
        }
        Player player = (Player)sender;
        player.teleport(new Location(Bukkit.getWorld("world"), -0.5, 87, -0.5, -180f, 0f));
        player.sendMessage(SkyblockInstance.PREFIX + "Téléportation au spawn...");

        return true;
    }

}
