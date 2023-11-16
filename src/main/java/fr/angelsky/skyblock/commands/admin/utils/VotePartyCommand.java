package fr.angelsky.skyblock.commands.admin.utils;

import fr.angelsky.skyblock.SkyblockInstance;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class VotePartyCommand implements CommandExecutor {

    private final SkyblockInstance skyblockInstance;

    public VotePartyCommand(SkyblockInstance skyblockInstance){
        this.skyblockInstance = skyblockInstance;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if(!sender.hasPermission("angelsky.voteparty")){
            sender.sendMessage(SkyblockInstance.PREFIX + "Vous n'avez pas la permission d'executer cette commande !");
            return false;
        }

        if(args.length != 2){
            sender.sendMessage(SkyblockInstance.PREFIX + "Syntaxe: /voteparty add <quantité>");
            return false;
        }

        if(args[0].equalsIgnoreCase("add")){
            if(!skyblockInstance.isInteger(args[1])){
                sender.sendMessage(SkyblockInstance.PREFIX + "La quantité doit être un nombre entier");
                return false;
            }

            int amount = Integer.parseInt(args[1]);
            this.skyblockInstance.getManagerLoader().getVotePartyManager().getVoteParty().addVote(amount);
            sender.sendMessage(SkyblockInstance.PREFIX + "La voteparty est désormais à " + this.skyblockInstance.getManagerLoader().getVotePartyManager().getVoteParty().getCurrentVotes() + " votes!");
        }else{
            sender.sendMessage(SkyblockInstance.PREFIX + "Syntaxe: /voteparty add <quantité>");
            return false;
        }

        return false;
    }
}
