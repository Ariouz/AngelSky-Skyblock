package fr.angelsky.skyblock.managers.items.skytools.upgrades;

import fr.angelsky.skyblock.managers.items.skytools.SkyToolUpgrade;
import fr.angelsky.skyblock.managers.items.skytools.SkyToolUpgradeType;
import fr.angelsky.skyblock.managers.player.level.experience.types.BlockPlayerExperience;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SkyToolHarvestUpgrade extends SkyToolUpgrade {

    public SkyToolHarvestUpgrade(String id, String display, SkyToolUpgradeType type) {
        super(id, display, type, 1);
    }

    public List<ItemStack> apply(List<Block> blocks, List<ItemStack> drops, Material type) {
        HashMap<Material, Material> harvestableTypes = getHarvestableTypes();
        for (Block block : blocks) {
            if (!harvestableTypes.containsKey(type)){
                block.getWorld().playEffect(block.getLocation(), Effect.STEP_SOUND, block.getBlockData(), 10);
                block.setType(Material.AIR);
                continue;
            }
            if (drops.stream().map(ItemStack::getType).filter(drop -> drop == harvestableTypes.get(type)).findFirst().orElse(null) == null) continue;
            if (!checkCropFullyGrown(block)){
                block.getDrops().forEach(drops::remove);
                continue;
            }
            block.setType(type);
            setMinAge(block);
            for (ItemStack drop : new ArrayList<>(drops)) {
                if (drop.getType() == harvestableTypes.get(type))
                {
                    if (drop.getAmount() == 1)
                        drops.remove(drop);
                    else
                        drop.setAmount(drop.getAmount() - 1);
                    break;
                }
            }
        }
        return drops;
    }

    public void setMinAge(Block block)
    {
        BlockData blockData = block.getBlockData();
        BlockPlayerExperience blockXp = BlockPlayerExperience.getByType(block.getType());
        if (blockXp == null) return;
        if(blockData instanceof Ageable age && blockXp.isCheckAge()){
            age.setAge(0);
            block.setBlockData(blockData);
        }
    }

    public boolean checkCropFullyGrown(Block block)
    {
        BlockData blockData = block.getBlockData();
        BlockPlayerExperience blockXp = BlockPlayerExperience.getByType(block.getType());
        if (blockXp == null) return true;
        if(blockData instanceof Ageable age && blockXp.isCheckAge()){
            return age.getAge() == age.getMaximumAge();
        }
        return true;
    }

    public HashMap<Material, Material> getHarvestableTypes()
    {
        return new HashMap<>(Map.of(
                Material.WHEAT, Material.WHEAT_SEEDS
                ));
    }

}
