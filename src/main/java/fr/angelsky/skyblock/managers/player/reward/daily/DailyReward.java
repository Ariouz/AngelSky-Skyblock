package fr.angelsky.skyblock.managers.player.reward.daily;

import java.util.ArrayList;
import java.util.List;

public class DailyReward {

    private final int level;
    private final String display;
    private ArrayList<String> commands;
    private final boolean needInventorySlot;
    private final boolean sendMessage;

    public DailyReward(int level, String display, boolean needInventorySlot, List<?> commands, boolean sendMessage){
        this.level = level;
        this.display = display;
        this.needInventorySlot = needInventorySlot;
        this.sendMessage = sendMessage;
        initCommands(commands);
    }

    public void initCommands(List<?> commands){
        ArrayList<String> finalCommands = new ArrayList<>();
        commands.forEach(cmd -> finalCommands.add(cmd.toString()));
        this.commands = finalCommands;
    }

    public int getLevel() {
        return level;
    }

    public String getDisplay() {
        return display;
    }

    public ArrayList<String> getCommands() {
        return commands;
    }

    public boolean isNeedInventorySlot() {
        return needInventorySlot;
    }

    public boolean isSendMessage() {
        return sendMessage;
    }
}
