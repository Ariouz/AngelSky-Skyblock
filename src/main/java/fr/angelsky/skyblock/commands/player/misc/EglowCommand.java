package fr.angelsky.skyblock.commands.player.misc;

import fr.angelsky.skyblock.SkyblockInstance;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public record EglowCommand(SkyblockInstance skyblockInstance) implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(ChatColor.RED + "Seul un joueur peut executer cette commande! ");
            return false;
        }

        if (!sender.hasPermission("angelsky.eglow")) {
            sender.sendMessage(SkyblockInstance.PREFIX + "Vous n'avez pas la permission d'executer cette commande !");
            return false;
        }

        boolean glowing = player.isGlowing();

        player.setGlowing(!glowing);
        player.sendMessage(skyblockInstance.getManagerLoader().getMessageManager().getColorizedMessage(SkyblockInstance.PREFIX + "Vous avez "+(glowing ? "désactivé" : "activé")+" la surbrillance"));

        return true;
    }
}
