package fr.angelsky.skyblock.kits;

import fr.angelsky.skyblock.SkyblockInstance;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class IslandClassKitGiver {

    private final SkyblockInstance skyblockInstance;

    private final HashMap<IslandClassKit, ArrayList<ItemStack>> kits = new HashMap<>();

    public IslandClassKitGiver(SkyblockInstance skyblockInstance){
        this.skyblockInstance = skyblockInstance;
        init();
    }

    public void give(Player player, IslandClassKit islandClassKit){
        for(ItemStack item : this.kits.get(islandClassKit)){
            player.getInventory().addItem(item);
        }
    }

    public void init(){
        // KINGSIZE KIT
        ArrayList<ItemStack> kingsizeItems = new ArrayList<>(Arrays.asList(
                new ItemStack(Material.STONE_PICKAXE),
                new ItemStack(Material.STONE_AXE),
                new ItemStack(Material.DIRT, 16),
                new ItemStack(Material.COBBLESTONE, 16),
                new ItemStack(Material.TORCH, 8),
                new ItemStack(Material.WATER_BUCKET, 1),
                new ItemStack(Material.COOKED_BEEF, 4)
        ));

        // FARMER KIT
        ArrayList<ItemStack> farmerItems = new ArrayList<>(Arrays.asList(
                new ItemStack(Material.STONE_HOE),
                new ItemStack(Material.WHEAT_SEEDS, 8),
                new ItemStack(Material.POTATO, 8),
                new ItemStack(Material.CARROT, 8),
                new ItemStack(Material.BROWN_MUSHROOM, 4),
                new ItemStack(Material.RED_MUSHROOM, 4),
                new ItemStack(Material.SUGAR_CANE, 8)
        ));

        this.kits.put(IslandClassKit.KINGSIZE, kingsizeItems);
        this.kits.put(IslandClassKit.FARMER, farmerItems);

    }

}
