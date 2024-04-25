package fr.angelsky.skyblock.managers.utils;

import fr.angelsky.skyblock.SkyblockInstance;
import org.bukkit.NamespacedKey;

public class Keys {

    private final SkyblockInstance skyblockInstance;

    public NamespacedKey SKYTOOL;
    public NamespacedKey SKYTOOL_RADIUS_UPGRADE;
    public NamespacedKey SKYTOOL_HARVEST_UPGRADE;
    public NamespacedKey SKYTOOL_MAGNET_UPGRADE;
    public NamespacedKey SKYTOOL_AUTOSELL_UPGRADE;

    public Keys(SkyblockInstance skyblockInstance)
    {
        this.skyblockInstance = skyblockInstance;
        this.init();
    }

    public void init()
    {
        SKYTOOL = new NamespacedKey(skyblockInstance.getSkyblock(), "skytool");
        SKYTOOL_RADIUS_UPGRADE = new NamespacedKey(skyblockInstance.getSkyblock(), "skytool_radius_upgrade");
        SKYTOOL_HARVEST_UPGRADE = new NamespacedKey(skyblockInstance.getSkyblock(), "skytool_harvest_upgrade");
        SKYTOOL_MAGNET_UPGRADE = new NamespacedKey(skyblockInstance.getSkyblock(), "skytool_magnet_upgrade");
        SKYTOOL_AUTOSELL_UPGRADE = new NamespacedKey(skyblockInstance.getSkyblock(), "skytool_autosell_upgrade");
    }

}
