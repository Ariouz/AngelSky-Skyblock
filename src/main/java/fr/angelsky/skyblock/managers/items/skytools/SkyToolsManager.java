package fr.angelsky.skyblock.managers.items.skytools;

import fr.angelsky.angelskyapi.api.utils.file.ConfigUtils;
import fr.angelsky.skyblock.SkyblockInstance;
import fr.angelsky.skyblock.listeners.player.items.skytools.SkyToolBlockBreakEvent;
import fr.angelsky.skyblock.managers.items.skytools.upgrades.*;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class SkyToolsManager {

    private final SkyblockInstance skyblockInstance;
    private final SkyToolDurabilityManager skyToolDurabilityManager;
    private final ArrayList<SkyTool> tools = new ArrayList<>();
    private final ConfigUtils toolsConfig;

    public SkyToolsManager(SkyblockInstance skyblockInstance){
        this.skyblockInstance = skyblockInstance;
        this.toolsConfig = new ConfigUtils(skyblockInstance.getSkyblock(), "items", "sky_tools.yml");
        this.skyToolDurabilityManager = new SkyToolDurabilityManager(skyblockInstance);
    }

    public void loadTools()
    {
        skyToolDurabilityManager.init();
        for (String toolId : toolsConfig.getYamlConfiguration().getKeys(false))
        {
            ConfigurationSection toolSection = toolsConfig.getYamlConfiguration().getConfigurationSection(toolId);
            if (toolSection == null) continue;
            ConfigurationSection upgradesSection = toolSection.getConfigurationSection("upgrades");
            List<SkyToolUpgrade> upgrades = new ArrayList<>();
            if (upgradesSection != null)
                upgradesSection.getKeys(false).forEach(upgradeId ->
                    upgrades.add(getUpgrade(SkyToolUpgradeType.getById(upgradeId), upgradesSection.getInt(upgradeId))));
            this.tools.add(new SkyTool(
                    toolId,
                    MiniMessage.miniMessage().deserialize(Objects.requireNonNull(toolSection.getString("display"))),
                    toolSection.getString("material"),
                    toolSection.getList("lore"),
                    upgrades
            ));
        }
    }

    public SkyTool getTool(String toolId)
    {
        return this.tools.stream().filter(tool -> tool.getId().equalsIgnoreCase(toolId)).findFirst().orElse(null);
    }

    public List<SkyToolUpgrade> getUpgrades(ItemStack itemStack)
    {
        List<SkyToolUpgrade> upgrades = new ArrayList<>();

        List<String> valueUpgrades = Arrays.stream(SkyToolUpgradeType.values()).map(SkyToolUpgradeType::getKey).toList();
        if (!itemStack.hasItemMeta()) return upgrades;
        PersistentDataContainer dataContainer = itemStack.getItemMeta().getPersistentDataContainer();

        for (NamespacedKey key : dataContainer.getKeys()) {
            if (valueUpgrades.contains(key.getKey()))
            {
                SkyToolUpgradeType type = SkyToolUpgradeType.getByKey(key.getKey());
                upgrades.add(getUpgrade(key, type, dataContainer));
            }
        }
        return upgrades;
    }

    private SkyToolUpgrade getUpgrade(NamespacedKey key, SkyToolUpgradeType type, PersistentDataContainer dataContainer)
    {
        switch (type){
            case RADIUS -> {
                return new SkyToolRadiusUpgrade(key.getKey(), type.getDisplay(), type,
                        dataContainer.get(key, PersistentDataType.INTEGER));
            }
            case HARVEST -> {
                return new SkyToolHarvestUpgrade(key.getKey(), type.getDisplay(), type);
            }
            case MAGNET -> {
                return new SkyToolMagnetUpgrade(key.getKey(), type.getDisplay(), type);
            }
            case AUTOSELL -> {
                return new SkyToolAutoSellUpgrade(key.getKey(), type.getDisplay(), type);
            }
        }
        return null;
    }

    private SkyToolUpgrade getUpgrade(SkyToolUpgradeType type, int value)
    {
        switch (type){
            case RADIUS -> {
                return new SkyToolRadiusUpgrade(skyblockInstance.getKeys().SKYTOOL_RADIUS_UPGRADE.getKey(), type.getDisplay(), type, value);
            }
            case HARVEST -> {
                return new SkyToolHarvestUpgrade(skyblockInstance.getKeys().SKYTOOL_HARVEST_UPGRADE.getKey(), type.getDisplay(), type);
            }
            case MAGNET -> {
                return new SkyToolMagnetUpgrade(skyblockInstance.getKeys().SKYTOOL_MAGNET_UPGRADE.getKey(), type.getDisplay(), type);
            }
            case AUTOSELL -> {
                return new SkyToolAutoSellUpgrade(skyblockInstance.getKeys().SKYTOOL_AUTOSELL_UPGRADE.getKey(), type.getDisplay(), type);
            }
        }
        return null;
    }

    private SkyToolUpgrade getItemUpgrade(ItemStack item, SkyToolUpgradeType type)
    {
        return this.getUpgrades(item).stream().filter(up -> up.getType() == type).findFirst().orElse(null);
    }

    public void setUpgrade(ItemMeta meta, SkyToolUpgradeType type, int value)
    {
        PersistentDataContainer dataContainer = meta.getPersistentDataContainer();
        switch (type){
            case RADIUS ->
                dataContainer.set(skyblockInstance.getKeys().SKYTOOL_RADIUS_UPGRADE, PersistentDataType.INTEGER, value);
            case HARVEST ->
                dataContainer.set(skyblockInstance.getKeys().SKYTOOL_HARVEST_UPGRADE, PersistentDataType.INTEGER, value);
            case MAGNET ->
                dataContainer.set(skyblockInstance.getKeys().SKYTOOL_MAGNET_UPGRADE, PersistentDataType.INTEGER, value);
            case AUTOSELL ->
                dataContainer.set(skyblockInstance.getKeys().SKYTOOL_AUTOSELL_UPGRADE, PersistentDataType.INTEGER, value);
        }
    }

    public boolean hasUpgrade(ItemStack item, SkyToolUpgradeType type)
    {
        return this.getUpgrades(item).stream().filter(up -> up.getType() == type).findFirst().orElse(null) != null;
    }

    public boolean isSkyTool(ItemStack itemStack)
    {
        if (!itemStack.hasItemMeta()) return false;
        PersistentDataContainer dataContainer = itemStack.getItemMeta().getPersistentDataContainer();
        return dataContainer.has(skyblockInstance.getKeys().SKYTOOL) && Boolean.TRUE.equals(dataContainer.get(skyblockInstance.getKeys().SKYTOOL, PersistentDataType.BOOLEAN));
    }

    public ArrayList<SkyTool> getTools() {
        return tools;
    }

    public void applyUpgrades(Player player, Block block, ItemStack item, SkyToolBlockBreakEvent event) {
        SkyToolUpgradeReturnValues ret = null;
        Material baseType = block.getType(); // TYPE BACKUP

        if (skyToolDurabilityManager.getDurability(item.getItemMeta()) == 0)
        {
            player.sendActionBar(Component.text("Outil cassé").color(TextColor.color(ChatColor.RED.getColor().getRGB())));
            return ;
        }

        ItemMeta meta = item.getItemMeta();
        skyToolDurabilityManager.removeDurability(meta, 1);
        item.setItemMeta(meta);
        player.getInventory().setItemInMainHand(skyToolDurabilityManager.updateDurabilityLore(item));

        if (!hasUpgrade(item, SkyToolUpgradeType.RADIUS))
        {
            skyblockInstance.getManagerLoader().getPlayerExperienceManager().processBlockExperience(player, block, 0 , 0);
            ret = new SkyToolUpgradeReturnValues(new ArrayList<>(block.getDrops()), List.of(block));
            block.getWorld().playEffect(block.getLocation(), Effect.STEP_SOUND, block.getBlockData(), 10);
            block.setType(Material.AIR);
        }

        if (hasUpgrade(item, SkyToolUpgradeType.RADIUS)){
            SkyToolRadiusUpgrade radius = (SkyToolRadiusUpgrade) getItemUpgrade(item, SkyToolUpgradeType.RADIUS);
            ret = radius.apply(block, player.getTargetBlockFace(5), player, event, skyblockInstance);
        }

        if (hasUpgrade(item, SkyToolUpgradeType.HARVEST))
        {
            if (ret == null)
                ret = new SkyToolUpgradeReturnValues(new ArrayList<>(block.getDrops()), List.of(block));
            SkyToolHarvestUpgrade harvest = (SkyToolHarvestUpgrade) getItemUpgrade(item, SkyToolUpgradeType.HARVEST);
            ret.setDrops(harvest.apply(ret.getBlocks(), ret.getDrops(), baseType));
        }

        if (ret == null) ret = new SkyToolUpgradeReturnValues(new ArrayList<>(block.getDrops()), List.of(block));
        if (hasUpgrade(item, SkyToolUpgradeType.AUTOSELL))
        {
            SkyToolAutoSellUpgrade skyToolAutoSellUpgrade = (SkyToolAutoSellUpgrade) getItemUpgrade(item, SkyToolUpgradeType.AUTOSELL);
            ret.setDrops(skyToolAutoSellUpgrade.apply(ret.getDrops(), player, skyblockInstance));
        }

        if (hasUpgrade(item, SkyToolUpgradeType.MAGNET))
        {
            for (ItemStack drop : ret.getDrops()) {
                if (!player.getInventory().addItem(drop).isEmpty())
                    player.getWorld().dropItem(player.getLocation(), drop);
            }
        }
        else ret.getDrops().forEach(drop -> player.getWorld().dropItem(block.getLocation(), drop));
    }

    public ConfigUtils getToolsConfig() {
        return toolsConfig;
    }

    public SkyToolDurabilityManager getSkyToolDurabilityManager() {
        return skyToolDurabilityManager;
    }
}
