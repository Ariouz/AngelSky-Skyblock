package fr.angelsky.skyblock.commands.admin.island;

import fr.angelsky.skyblock.SkyblockInstance;
import fr.angelsky.skyblockapi.accounts.TempPlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class UpgradeTokenAdminCommand implements CommandExecutor {

    private final SkyblockInstance skyblockInstance;

    public UpgradeTokenAdminCommand(SkyblockInstance skyblockInstance){
        this.skyblockInstance = skyblockInstance;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!sender.hasPermission("angelsky.upgradetokens")){
            sender.sendMessage(SkyblockInstance.PREFIX + "Vous n'avez pas la permission d'executer cette commande !");
            return false;
        }

        if(args.length != 3){
            sender.sendMessage(SkyblockInstance.PREFIX + "Syntaxe: /upgradetokens <joeueur> <add/set/remove> <quantité>");
            return false;
        }

        if(Bukkit.getPlayer(args[0]) == null){
            sender.sendMessage(SkyblockInstance.PREFIX + "Le joueur est introuvable / doit être connécté");
            return false;
        }

        if(!skyblockInstance.isInteger(args[2])){
            sender.sendMessage(SkyblockInstance.PREFIX + "La quantité doit être un nombre entier");
            return false;
        }

        Player target = Bukkit.getPlayer(args[0]);
        assert target != null;
        TempPlayer tempPlayer = skyblockInstance.getTempAccounts().get(target.getName());
        String action = args[1];
        int amount = Integer.parseInt(args[2]);

        switch (action){
            case "add":
                tempPlayer.addUpgradeTokens(amount);
                break;
            case "set":
                tempPlayer.setUpgradeTokens(amount);
                break;
            case "remove":
                tempPlayer.removeUpgradeTokens(amount);
                break;
            default:
                sender.sendMessage(SkyblockInstance.PREFIX + "Syntaxe: /upgradetokens <joeueur> <add/set/remove> <quantité>");
                return false;
        }

        sender.sendMessage(SkyblockInstance.PREFIX + target.getName() + " possède désormais " + tempPlayer.getUpgradeTokens()+" points d'amélioration.");
        return true;
    }
}
