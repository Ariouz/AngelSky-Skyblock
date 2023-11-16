package fr.angelsky.skyblock.placeholders.server;

import fr.angelsky.skyblock.SkyblockInstance;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SkyBlockPrefixPlaceholder extends PlaceholderExpansion {

    private final SkyblockInstance skyblockInstance;

    public SkyBlockPrefixPlaceholder(SkyblockInstance skyblockInstance){
        this.skyblockInstance = skyblockInstance;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "angelsky-prefix";
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
        return SkyblockInstance.PREFIX;
    }

}
