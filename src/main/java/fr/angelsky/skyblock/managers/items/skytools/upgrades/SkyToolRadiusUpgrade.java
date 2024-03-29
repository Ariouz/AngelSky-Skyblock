package fr.angelsky.skyblock.managers.items.skytools.upgrades;

import fr.angelsky.skyblock.managers.items.skytools.SkyTool;
import fr.angelsky.skyblock.managers.items.skytools.SkyToolUpgrade;
import fr.angelsky.skyblock.managers.items.skytools.SkyToolUpgradeType;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.inventory.ItemStack;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SkyToolRadiusUpgrade extends SkyToolUpgrade {

    private int radius;

    public SkyToolRadiusUpgrade(String id, Component display, SkyToolUpgradeType type, int radius) {
        super(id, display, type);
        this.radius = radius;
    }

    /*
        @params tool used, block broken
        @return List of items to drop (or to magnet / auto sell)
     */
    @Override
    public List<ItemStack> apply(SkyTool tool, Block block, BlockFace blockFace) {
        super.apply();
        List<ItemStack> returnList = new ArrayList<>();

        if (radius == 1)
        {
            returnList.addAll(block.getDrops());
            return returnList;
        }

        for (int xAxis = - (radius / 2); xAxis < radius / 2; xAxis++)
        {
            for (int yAxis = - (radius / 2); yAxis < radius / 2; yAxis++)
            {
                Block rBlock = block.getWorld().getBlockAt(block.getX() + xAxis, block.getY() + yAxis, block.getZ());
                returnList.addAll(block.getDrops());
                rBlock.setType(Material.AIR);
            }
        }
        return returnList;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public int getRadius() {
        return radius;
    }
}
