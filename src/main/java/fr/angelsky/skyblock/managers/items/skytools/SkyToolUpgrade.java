package fr.angelsky.skyblock.managers.items.skytools;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.inventory.ItemStack;

import java.awt.*;
import java.util.List;

public abstract class SkyToolUpgrade {

    private final String id;
    private final Component display;

    private final SkyToolUpgradeType type;

    public SkyToolUpgrade(String id, Component display, SkyToolUpgradeType type)
    {
        this.id = id;
        this.display = display;
        this.type = type;
    }

    public abstract List<ItemStack> apply(SkyTool tool, Block block, BlockFace blockFace);
    public void apply() {}

    public SkyToolUpgradeType getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public Component getDisplay() {
        return display;
    }

}
