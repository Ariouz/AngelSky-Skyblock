package fr.angelsky.skyblock.managers.items.skytools;

public abstract class SkyToolUpgrade {

    private final String id;
    private final String display;

    private final SkyToolUpgradeType type;
    private final int value;

    public SkyToolUpgrade(String id, String display, SkyToolUpgradeType type, int value)
    {
        this.id = id;
        this.display = display;
        this.type = type;
        this.value = value;
    }

    public void apply() {}

    public SkyToolUpgradeType getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public String getDisplay() {
        return display;
    }

    public int getValue() {
        return value;
    }
}
