package fr.angelsky.skyblock.commands.player;

import fr.angelsky.skyblock.SkyblockInstance;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class LobbyCommand implements CommandExecutor {

    private final SkyblockInstance skyblockInstance;

    public LobbyCommand(SkyblockInstance skyblockInstance){
        this.skyblockInstance = skyblockInstance;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage(ChatColor.RED + "Seul un joueur peut executer cette commande! ");
            return false;
        }
        Player player = (Player)sender;
        this.skyblockInstance.getSkyBlockApiInstance().getAngelSkyApiInstance().getApiManager().getPluginMessageUtil().connectToServer("lobby", player);
        player.sendMessage(skyblockInstance.getManagerLoader().getMessageManager().getColorizedMessageConfig("back_to_lobby"));

        return true;
    }
}
