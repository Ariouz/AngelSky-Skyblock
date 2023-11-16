package fr.angelsky.skyblock.managers.server.crates;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class CrateLoot {

    private final String id;
    private final ItemStack item;
    private final double probability;
    private final List<String> commands;
    private final boolean giveOnWin;

    public CrateLoot(String id, ItemStack item, double probability, boolean giveOnWin, List<String> commands){
        this.id = id;
        this.item = item;
        this.probability = probability;
        this.commands = commands;
        this.giveOnWin = giveOnWin;
    }

    public String getId() {
        return id;
    }

    public ItemStack getItem() {
        return item;
    }

    public double getProbability() {
        return probability;
    }

    public List<String> getCommands() {
        return commands;
    }

    public boolean isGiveOnWin() {
        return giveOnWin;
    }
}
