package fr.angelsky.skyblock.placeholders.island.upgrade;

import fr.angelsky.skyblock.SkyblockInstance;
import fr.angelsky.skyblockapi.accounts.TempPlayer;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class UpgradeTokenPlaceholder extends PlaceholderExpansion {

    private final SkyblockInstance skyblockInstance;

    public UpgradeTokenPlaceholder(SkyblockInstance skyblockInstance){
        this.skyblockInstance = skyblockInstance;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "angelsky-upgrade-tokens";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Ariouz";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    @Override
    public @Nullable String onPlaceholderRequest(Player player, @NotNull String params) {
        TempPlayer tempPlayer = skyblockInstance.getTempAccounts().get(player.getName());
        if(params.equals("display")){
            return ChatColor.translateAlternateColorCodes('&', "&e"+tempPlayer.getUpgradeTokens() +" Jeton"+(tempPlayer.getUpgradeTokens() > 1 ? "s" : "") +" d'Amélioration");
        }else {
            return String.valueOf(tempPlayer.getUpgradeTokens());
        }
    }
}
