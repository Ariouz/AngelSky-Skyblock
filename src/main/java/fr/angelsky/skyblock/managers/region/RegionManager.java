package fr.angelsky.skyblock.managers.region;
import fr.angelsky.angelskyapi.api.utils.file.ConfigUtils;
import fr.angelsky.skyblock.SkyblockInstance;
import fr.angelsky.skyblock.managers.region.world.WorldManager;

public class RegionManager {

    private final SkyblockInstance skyblockInstance;

    private WorldManager worldManager;
    private ConfigUtils regionConfig;

    public RegionManager(SkyblockInstance skyblockInstance){
        this.skyblockInstance = skyblockInstance;
    }

    public void init(){
        this.regionConfig = new ConfigUtils(skyblockInstance.getSkyblock(), "regions.yml");
        this.worldManager = new WorldManager(skyblockInstance, regionConfig);
        this.worldManager.loadProtectedWorlds();
    }

    public ConfigUtils getRegionConfig() {
        return regionConfig;
    }

    public WorldManager getWorldManager() {
        return worldManager;
    }
}
