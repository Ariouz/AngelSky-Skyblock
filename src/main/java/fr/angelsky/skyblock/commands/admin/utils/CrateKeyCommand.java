package fr.angelsky.skyblock.commands.admin.utils;

import fr.angelsky.skyblock.SkyblockInstance;
import fr.angelsky.skyblock.managers.server.crates.CrateKey;
import io.th0rgal.oraxen.api.OraxenItems;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CrateKeyCommand implements CommandExecutor {

    private final SkyblockInstance skyblockInstance;

    public CrateKeyCommand(SkyblockInstance skyblockInstance){
        this.skyblockInstance = skyblockInstance;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!sender.hasPermission("angelsky.cratekey")){
            sender.sendMessage(SkyblockInstance.PREFIX + "Vous n'avez pas la permission d'executer cette commande !");
            return false;
        }

        if(args.length != 4){
            sender.sendMessage(SkyblockInstance.PREFIX + "Syntaxe: /cratekey <joueur/all> give <key_id> <quantité>");
            return false;
        }

        if(Bukkit.getPlayer(args[0]) == null && !args[0].equalsIgnoreCase("all")){
            sender.sendMessage(SkyblockInstance.PREFIX + "Le joueur est introuvable / doit être connécté");
            return false;
        }

        CrateKey key = CrateKey.getById(args[2]);
        if(key == null){
            sender.sendMessage(skyblockInstance.getManagerLoader().getMessageManager().getColorizedMessage(SkyblockInstance.PREFIX + "&fId de clé invalide. Voici les id disponibles."));
            for (CrateKey value : CrateKey.values()) {
                sender.sendMessage(ChatColor.GOLD + value.getKeyId() + ChatColor.WHITE + " » " + ChatColor.GRAY + "Clé de Caisse " + value.getDisplay());
            }
            return false;
        }

        if(!skyblockInstance.isInteger(args[3])){
            sender.sendMessage(SkyblockInstance.PREFIX + "La quantité doit être un nombre entier");
            return false;
        }

        int amount = Integer.parseInt(args[3]);
        if (args[0].equalsIgnoreCase("all")){
            Bukkit.getOnlinePlayers().forEach(player -> {
                player.sendMessage(skyblockInstance.getManagerLoader().getMessageManager().getColorizedMessage(SkyblockInstance.PREFIX + "Vous avez obtenu &6"+amount+" clé"+(amount > 1 ? "s" : "")+" de caisse " + key.getDisplay()));
                boolean dropped = false;
                for(int i = 0; i < amount; i++){
                    if(!player.getInventory().addItem(OraxenItems.getItemById(key.getKeyId()).build()).isEmpty()){
                        player.getWorld().dropItem(player.getLocation(), OraxenItems.getItemById(key.getKeyId()).build());
                        dropped = true;
                    }
                }

                if (dropped) player.sendMessage(skyblockInstance.getManagerLoader().getMessageManager().getColorizedMessage(skyblockInstance.getManagerLoader().getMessageManager().getColorizedMessage(SkyblockInstance.PREFIX + "Des items ont été jetés au sol car vous n'aviez pas suffisament de place dans votre inventaire.")));
            });

            sender.sendMessage(skyblockInstance.getManagerLoader().getMessageManager().getColorizedMessage(SkyblockInstance.PREFIX + Bukkit.getOnlinePlayers().size() + " joueur ont obtenu " + amount + " clé"+(amount > 1 ? "s" : "")+" de caisse " + key.getDisplay()));

        }else{
            Player target = Bukkit.getPlayer(args[0]);
            assert target != null;

            sender.sendMessage(skyblockInstance.getManagerLoader().getMessageManager().getColorizedMessage(SkyblockInstance.PREFIX + target.getName() + " a obtenu " + amount + " clé"+(amount > 1 ? "s" : "")+" de caisse " + key.getDisplay()));
            target.sendMessage(skyblockInstance.getManagerLoader().getMessageManager().getColorizedMessage(SkyblockInstance.PREFIX + "Vous avez obtenu &6"+amount+" clé"+(amount > 1 ? "s" : "")+" de caisse " + key.getDisplay()));

            boolean dropped = false;
            for(int i = 0; i < amount; i++) {
                if (!target.getInventory().addItem(OraxenItems.getItemById(key.getKeyId()).build()).isEmpty()) {
                    target.getWorld().dropItem(target.getLocation(), OraxenItems.getItemById(key.getKeyId()).build());
                    dropped = true;
                }
            }

            if (dropped) target.sendMessage(skyblockInstance.getManagerLoader().getMessageManager().getColorizedMessage(SkyblockInstance.PREFIX + "Des items ont été jetés au sol car vous n'aviez pas suffisament de place dans votre inventaire."));
        }

        return true;
    }

}
