package fr.angelsky.skyblock.placeholders.global;

import fr.angelsky.skyblock.SkyblockInstance;
import fr.angelsky.skyblock.managers.player.level.LevelManager;
import fr.angelsky.skyblock.managers.server.top.LevelTopManager;
import fr.angelsky.skyblock.sql.player.level.LevelTopPlayer;
import fr.angelsky.skyblockapi.managers.level.LevelColor;
import fr.angelsky.skyblockapi.managers.level.LevelRankManager;
import fr.angelsky.skyblockapi.managers.level.PlayerLevel;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SkyblockLevelTopPlaceholder extends PlaceholderExpansion {

    private final LevelTopManager levelTopManager;
    private final SkyblockInstance skyblockInstance;

    public SkyblockLevelTopPlaceholder(SkyblockInstance skyblockInstance){
        this.skyblockInstance = skyblockInstance;
        this.levelTopManager = skyblockInstance.getManagerLoader().getLevelTopManager();
    }

    @Override
    public @NotNull String getIdentifier() {
        return "skyblock-leveltop";
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
        String[] args = params.split(":");
        if (player == null) return null;
        int position = args.length == 2 ? Integer.parseInt(args[1]) : -1;
        return switch (args[0]) {
            case "player_position" -> String.valueOf(levelTopManager.get(player).getPosition());
            case "player_xp" -> String.valueOf(levelTopManager.get(player).getXp());
            case "player_level" -> String.valueOf(levelTopManager.get(player).getLevel());
            case "player_rank" -> String.valueOf(levelTopManager.get(player).getRank());
            case "player_display" -> getRankDisplay(levelTopManager.get(player));
            case "position_player" -> String.valueOf(levelTopManager.getAtPosition(position).getPlayerName());
            case "position_xp" -> String.valueOf(levelTopManager.getAtPosition(position).getXp());
            case "position_level" -> String.valueOf(levelTopManager.getAtPosition(position).getLevel());
            case "position_rank" -> String.valueOf(levelTopManager.getAtPosition(position).getRank());
            case "position_display" -> getRankDisplay(levelTopManager.getAtPosition(position));

            default -> null;
        };
    }

    public String getRankDisplay(LevelTopPlayer levelTopPlayer){
        LevelManager levelManager = skyblockInstance.getManagerLoader().getLevelManager();
        LevelColor levelColor = levelManager.getLevelColor();

        PlayerLevel playerLevel = new PlayerLevel(skyblockInstance.getSkyBlockApiInstance(), null);
        playerLevel.init(levelTopPlayer.getXp(), levelTopPlayer.getLevel(), levelTopPlayer.getRank());
        playerLevel.refreshLevels(skyblockInstance.getManagerLoader().getLevelManager().getLevelRankManager(), levelColor);

        if(levelTopPlayer.getLevel() == 150)
            return LevelRankManager.getMaxRankTag();
        return playerLevel.getLevelRankObject().getDisplay() + "[" + playerLevel.getLevelColor() + levelTopPlayer.getLevel() + playerLevel.getLevelRankObject().getDisplay() + "]";
    }

}
