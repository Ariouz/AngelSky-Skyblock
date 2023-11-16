package fr.angelsky.skyblock.placeholders.global;

import fr.angelsky.angelskyeconomy.AngelSkyEconomy;
import fr.angelsky.angelskyeconomy.eco.PlayerBalance;
import fr.angelsky.skyblock.SkyblockInstance;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SkyblockBaltopPlaceholder extends PlaceholderExpansion {

    private SkyblockInstance skyblockInstance;
    private final AngelSkyEconomy angelSkyEconomy;
    public SkyblockBaltopPlaceholder(SkyblockInstance skyblockInstance){
        this.skyblockInstance = skyblockInstance;
        this.angelSkyEconomy = this.skyblockInstance.getManagerLoader().getAngelSkyEconomy();
    }

    @Override
    public @NotNull String getIdentifier() {
        return "skyblock-baltop";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Ariouz";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
    }

    @Override
    public @Nullable String onPlaceholderRequest(Player player, @NotNull String params) {
        String[] args = params.split(":");
        return switch (args[0]) {
            case "player_rank" -> String.valueOf(getPlayerRank(player));
            case "player_balance" -> String.valueOf(getPlayerBalance(player));
            case "rank_balance" -> String.valueOf(getBalanceForRank(Integer.parseInt(args[1])));
            case "rank_player" -> getPlayerNameForRank(Integer.parseInt(args[1]));
            default -> null;
        };
    }

    public int getPlayerRank(Player player) {
        int index = 0;
        for (PlayerBalance playerBalance : angelSkyEconomy.getBalanceTopRunnable().getBalanceTop()) {
            if (playerBalance.getUUID().toString().equalsIgnoreCase(player.getUniqueId().toString())) { return index; }
            index++;
        }
        return index > 0 ? (index + 1) : -1;
    }

    public double getPlayerBalance(Player player){
        return angelSkyEconomy.getEco().getBalance(player.getUniqueId()).getBalance();
    }

    public double getBalanceForRank(int rank){
        if (angelSkyEconomy.getBalanceTopRunnable().getBalanceTop().size() <= rank)
            return 0.0;
        return angelSkyEconomy.getBalanceTopRunnable().getBalanceTop().get(rank).getBalance();
    }

    public String getPlayerNameForRank(int rank){
        OfflinePlayer player;
        if (angelSkyEconomy.getBalanceTopRunnable().getBalanceTop().size() <= rank)
            return "Vide";
        player = Bukkit.getOfflinePlayer(angelSkyEconomy.getBalanceTopRunnable().getBalanceTop().get(rank).getUUID());
        if (player.getName() == null)
            return "Null";
        return player.getName();
    }

}
