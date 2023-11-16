package fr.angelsky.skyblock.commands.utils.social;

import fr.angelsky.skyblock.SkyblockInstance;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class VoteCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        sender.sendMessage(SkyblockInstance.PREFIX + "https://angelsky.fr/vote");
        return true;
    }
}
