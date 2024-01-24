package fr.angelsky.skyblock.managers;

import fr.angelsky.skyblock.SkyblockInstance;
import fr.angelsky.skyblock.listeners.island.IslandDisbandListener;
import fr.angelsky.skyblock.listeners.player.chat.ChatListener;
import fr.angelsky.skyblock.listeners.player.damage.PlayerIslandDeathListener;
import fr.angelsky.skyblock.listeners.player.level.PlayerPassLevelListener;
import fr.angelsky.skyblock.listeners.player.level.PlayerPassRankListener;
import fr.angelsky.skyblock.listeners.player.level.PlayerXpEarningListener;
import fr.angelsky.skyblock.listeners.server.PlayerJoinListener;
import fr.angelsky.skyblock.listeners.server.PlayerQuitListener;
import fr.angelsky.skyblock.listeners.server.crates.CratesInteract;
import fr.angelsky.skyblock.listeners.world.BlockPlaceListener;
import fr.angelsky.skyblock.listeners.world.SpawnProtectionEventHandler;
import org.bukkit.Bukkit;

public class EventManager {

    private final SkyblockInstance skyblockInstance;

    public EventManager(SkyblockInstance skyblockInstance){
        this.skyblockInstance = skyblockInstance;
        init();
    }

    public void init(){
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(skyblockInstance), skyblockInstance.getSkyblock());
        Bukkit.getPluginManager().registerEvents(new PlayerQuitListener(skyblockInstance), skyblockInstance.getSkyblock());
        Bukkit.getPluginManager().registerEvents(new IslandDisbandListener(skyblockInstance), skyblockInstance.getSkyblock());
        Bukkit.getPluginManager().registerEvents(new PlayerIslandDeathListener(skyblockInstance), skyblockInstance.getSkyblock());

        Bukkit.getPluginManager().registerEvents(new PlayerPassRankListener(skyblockInstance), skyblockInstance.getSkyblock());
        Bukkit.getPluginManager().registerEvents(new PlayerPassLevelListener(skyblockInstance), skyblockInstance.getSkyblock());

        Bukkit.getPluginManager().registerEvents(new ChatListener(skyblockInstance), skyblockInstance.getSkyblock());

        Bukkit.getPluginManager().registerEvents(new CratesInteract(skyblockInstance), skyblockInstance.getSkyblock());
        Bukkit.getPluginManager().registerEvents(new SpawnProtectionEventHandler(skyblockInstance), skyblockInstance.getSkyblock());
        Bukkit.getPluginManager().registerEvents(new BlockPlaceListener(skyblockInstance), skyblockInstance.getSkyblock());

        Bukkit.getPluginManager().registerEvents(new PlayerXpEarningListener(skyblockInstance), skyblockInstance.getSkyblock());
    }

}
