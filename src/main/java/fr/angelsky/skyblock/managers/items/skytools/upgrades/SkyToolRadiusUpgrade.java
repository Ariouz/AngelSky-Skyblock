package fr.angelsky.skyblock.managers.items.skytools.upgrades;

import fr.angelsky.skyblock.SkyblockInstance;
import fr.angelsky.skyblock.listeners.player.items.skytools.SkyToolBlockBreakEvent;
import fr.angelsky.skyblock.managers.items.skytools.SkyToolUpgrade;
import fr.angelsky.skyblock.managers.items.skytools.SkyToolUpgradeType;
import fr.angelsky.skyblock.managers.player.level.experience.types.BlockPlayerExperience;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class SkyToolRadiusUpgrade extends SkyToolUpgrade {

    private int radius;

    public SkyToolRadiusUpgrade(String id, String display, SkyToolUpgradeType type, int radius) {
        super(id, display, type, radius);
        this.radius = radius;
    }

    /*
        @params block broken, block face
        @return List of items to drop
     */
    public SkyToolUpgradeReturnValues apply(Block block, BlockFace blockFace, Player player, SkyToolBlockBreakEvent event, SkyblockInstance skyblockInstance) {
        super.apply();
        List<Block> blocks = new ArrayList<>();

        List<ItemStack> drops = new ArrayList<>(block.getDrops());
        block.getDrops().clear();
        blocks.add(block);
        if (radius == 1) {
            skyblockInstance.getManagerLoader().getPlayerExperienceManager().processBlockExperience(player, block, 0, 0);
            if (!checkCropFullyGrown(block)) {
                drops.clear();
            }
            else block.setType(Material.AIR);
            return new SkyToolUpgradeReturnValues(drops, blocks);
        }

        int center = radius / 2;
        for (int fAxis = -center; fAxis < radius - center; fAxis++) {
            for (int sAxis = -center; sAxis < radius - center; sAxis++) {
                if (fAxis == 0 && sAxis == 0) continue;
                Vector vec = new Vector(0, 0, 0);
                switch (blockFace) {
                    case DOWN, UP -> vec.add(new Vector(fAxis, 0, sAxis));
                    case EAST, WEST -> vec.add(new Vector(0, fAxis, sAxis));
                    case NORTH, SOUTH -> vec.add(new Vector(fAxis, sAxis, 0));
                }
                Block rBlock = block.getWorld().getBlockAt(block.getLocation().add(vec));
                if (rBlock.getType() != block.getType()) continue;
                if (!checkCropFullyGrown(rBlock)) continue;
                skyblockInstance.getManagerLoader().getPlayerExperienceManager().processBlockExperience(player, rBlock, 0, 0);
                drops.addAll(rBlock.getDrops());
                blocks.add(rBlock);
                rBlock.getWorld().playEffect(block.getLocation(), Effect.STEP_SOUND, block.getBlockData(), 10);
                rBlock.setType(Material.AIR);
            }
        }
        block.setType(Material.AIR);
        return new SkyToolUpgradeReturnValues(drops, blocks);
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

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public int getRadius() {
        return radius;
    }

}
