package fr.angelsky.skyblock;

import org.bukkit.plugin.java.JavaPlugin;

public final class Skyblock extends JavaPlugin {

    private SkyblockInstance skyblockInstance;

    @Override
    public void onEnable() {
        this.skyblockInstance = new SkyblockInstance(this);
        try {
            this.skyblockInstance.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        this.skyblockInstance.unload();
    }
}
