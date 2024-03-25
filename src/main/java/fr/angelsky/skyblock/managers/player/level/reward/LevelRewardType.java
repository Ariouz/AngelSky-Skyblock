package fr.angelsky.skyblock.managers.player.level.reward;

import java.util.Arrays;

public enum LevelRewardType {

    DISPLAY,
    ITEM,
    COMMAND,
    PERMISSION;

    public static LevelRewardType getType(String type)
    {
        return Arrays.stream(values()).filter(ttype -> ttype.toString().equalsIgnoreCase(type)).findFirst().orElse(null);
    }

}
