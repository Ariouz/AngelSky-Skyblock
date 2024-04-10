package fr.angelsky.skyblock.managers.items.skytools;

import java.util.Arrays;

public enum SkyToolUpgradeType {

    RADIUS("skytool_radius_upgrade", "Rayon", "radius"),
    HARVEST("skytool_harvest_upgrade", "Replantation", "harvest"),
    MAGNET("skytool_magnet_upgrade", "Magnet", "magnet"),

    ;

    private final String key;
    private final String display;
    private final String id;

    SkyToolUpgradeType(String key, String display, String id)
    {
        this.key = key;
        this.display = display;
        this.id = id;
    }

    public static SkyToolUpgradeType getByKey(String key)
    {
        return Arrays.stream(values()).filter(tool -> key.equals(tool.getKey())).findFirst().orElse(null);
    }

    public static SkyToolUpgradeType getById(String id)
    {
        return Arrays.stream(values()).filter(tool -> id.equals(tool.getId())).findFirst().orElse(null);
    }

    public String getDisplay() {
        return display;
    }

    public String getKey() {
        return key;
    }

    public String getId() {
        return id;
    }
}
