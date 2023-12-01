package fr.angelsky.skyblock.sql.player.level;

public class LevelTopPlayer {

    private final String playerName;
    private int xp;
    private final int level;
    private final int rank;
    private final int position;

    public LevelTopPlayer(String playerName, int xp, int level, int rank, int position){
        this.playerName = playerName;
        this.xp = xp;
        this.level = level;
        this.rank = rank;
        this.position = position;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getXp() {
        return xp;
    }

    public int getLevel() {
        return level;
    }

    public int getRank() {
        return rank;
    }

    public int getPosition() {
        return position;
    }
}
