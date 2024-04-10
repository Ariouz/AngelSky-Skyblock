package fr.angelsky.skyblock.managers.items.skytools.upgrades;

import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class SkyToolUpgradeReturnValues {

    private List<ItemStack> drops = new ArrayList<>();
    private List<Block> blocks = new ArrayList<>();

    public SkyToolUpgradeReturnValues(List<ItemStack> drops, List<Block> blocks)
    {
        this.drops = drops;
        this.blocks = blocks;
    }

    public List<ItemStack> getDrops() {
        return drops;
    }

    public void setDrops(List<ItemStack> drops) {
        this.drops = drops;
    }

    public List<Block> getBlocks() {
        return blocks;
    }

    public void setBlocks(List<Block> blocks) {
        this.blocks = blocks;
    }
}

