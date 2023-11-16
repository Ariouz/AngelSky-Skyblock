package fr.angelsky.skyblock.managers.region.world;

import fr.angelsky.angelskyapi.api.utils.file.ConfigUtils;
import fr.angelsky.skyblock.SkyblockInstance;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;

public class WorldManager {

    private final SkyblockInstance skyblockInstance;
    private final ConfigUtils regionConfig;

    private final List<World> protectedWorlds = new ArrayList<>();

    public WorldManager(SkyblockInstance skyblockInstance, ConfigUtils regionConfig){
        this.skyblockInstance = skyblockInstance;
        this.regionConfig = regionConfig;
    }

    public void loadProtectedWorlds(){
        regionConfig.getList("protected_worlds").forEach(world -> this.protectedWorlds.add(Bukkit.getWorld(world.toString())));
    }

    public List<World> getProtectedWorlds() {
        return protectedWorlds;
    }
}
