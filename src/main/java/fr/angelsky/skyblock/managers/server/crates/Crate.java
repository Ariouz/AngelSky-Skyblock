package fr.angelsky.skyblock.managers.server.crates;

import fr.angelsky.angelskyapi.api.utils.file.ConfigUtils;
import fr.angelsky.skyblock.SkyblockInstance;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class Crate {

    private final SkyblockInstance skyblockInstance;
    private final String crateId, keyId;
    private final String id;
    private final Material particleBlock;

    private HashMap<Location, Integer> openTime = new HashMap<Location, Integer>();

    private final List<CrateLoot> loots = new ArrayList<>();
    private final List<CrateLoot> rawLoots = new ArrayList<>();
    private final ConfigUtils crateConfig;

    private Player opener = null;
    private ItemStack finalItem = null;

    public Crate(SkyblockInstance skyblockInstance, String keyId, String crateId, String id, Material particleBlock, ConfigUtils crateConfig){
        this.skyblockInstance = skyblockInstance;
        this.crateId = crateId;
        this.keyId = keyId;
        this.id = id;
        this.particleBlock = particleBlock;
        this.crateConfig = crateConfig;
        this.loadLoots();
    }

    public void loadLoots(){
        ConfigurationSection section = crateConfig.getYamlConfiguration().getConfigurationSection(this.id+".loots");
        if(section == null){
            skyblockInstance.getSkyblock().getLogger().warning("No crate loot section for " + this.id);
            return;
        }
        for(String lootId : section.getKeys(false)){
            skyblockInstance.getSkyblock().getLogger().info("Loading crate loot " + lootId + " for crate " + this.id);
            CrateLoot crateLoot = new CrateLoot(
                    lootId,
                    skyblockInstance.getManagerLoader().getCrateManager().getLootItemStack(Objects.requireNonNull(section.getConfigurationSection(lootId+".item"))),
                    section.getDouble("probability"),
                    section.getBoolean("give_on_win"),
                    skyblockInstance.getManagerLoader().getCrateManager().getLootCommands(Objects.requireNonNull(section.getConfigurationSection(lootId)))
            );
            this.loots.add(crateLoot);
            this.rawLoots.add(crateLoot);
        }
        initLoots();
    }

    public void initLoots(){
        for(CrateLoot loot : getLoots()){
            for(int i = 0; i < loot.getProbability()*100; i++){
                this.loots.add(loot);
            }
        }
    }
    public CrateLoot getRandomLoot(){
        int rand = new Random().nextInt(this.loots.size());
        if(loots.size() == 0) return null;
        return loots.get(rand);
    }

    public String getCrateId() {
        return crateId;
    }

    public String getKeyId() {
        return keyId;
    }

    public String getId() {
        return id;
    }

    public int getOpenTime(Location location) {
        return openTime.get(location);
    }

    public void setOpenTime(int openTime, Location location) {
        this.openTime.replace(location, openTime);
    }

    public Material getParticleBlock() {
        return particleBlock;
    }

    public List<CrateLoot> getLoots() {
        return loots;
    }

    public List<CrateLoot> getRawLoots() {
        return rawLoots;
    }

    public void decreaseOpenTime(Location location){this.openTime.replace(location, getOpenTime(location)-1);}

    public void addOpenTime(Location location, int time){
        this.openTime.put(location, time);
    }

    public void removeOpenTime(Location location){
        this.openTime.remove(location);
    }

    public SkyblockInstance getSkyblockInstance() {
        return skyblockInstance;
    }

    public HashMap<Location, Integer> getOpenTime() {
        return openTime;
    }

    public void setOpener(Player player) {
        this.opener = player;
    }

    public Player getOpener() {
        return opener;
    }

    public ItemStack getFinalItem() {
        if(this.finalItem == null) this.finalItem = getRandomLoot().getItem();
        return this.finalItem;
    }
}

