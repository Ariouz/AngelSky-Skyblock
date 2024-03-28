package fr.angelsky.skyblock.managers.player.level.reward;

import fr.angelsky.skyblock.SkyblockInstance;
import io.th0rgal.oraxen.api.OraxenItems;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class LevelReward {

    private final LevelRewardType type;
    private final Material material;
    private final Material icon;
    private final String oraxenId;
    private final int itemAmount;
    private final String data;
    private final String message;
    private final String display;
    private final int rank;
    private final int level;

    public LevelReward(int rank, int level, LevelRewardType type, Material material,
                       String oraxenId, int itemAmount, String data, String message, String display, Material icon)
    {
        this.rank = rank;
        this.level = level;
        this.type = type;
        this.material = material;
        this.oraxenId = oraxenId;
        this.itemAmount = itemAmount;
        this.data = data;
        this.message = message;
        this.display = display;
        this.icon = icon;
    }

    public void give(Player player, SkyblockInstance skyblockInstance)
    {
        switch (type)
        {
            case PERMISSION -> {
                skyblockInstance.getAngelSkyApiInstance().getApiManager().getLuckPermsIntegrationManager().addPermission(player.getUniqueId(), data);
            }
            case COMMAND -> {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), data.replaceAll("%player%", player.getName()));
            }
            case ITEM -> {
                ItemStack item;
                if (material == null){
                    item = OraxenItems.getItemById(oraxenId).build();
                }
                else item = new ItemStack(material);
                for (int i = 0; i < itemAmount; i++)
                {
                    List<ItemStack> toDrop = player.getInventory().addItem(item).values().stream().toList();
                    if (!toDrop.isEmpty())
                        toDrop.forEach(items -> player.getWorld().dropItem(player.getLocation(), items));
                }
            }
        }

        if (message != null)
            player.sendMessage(SkyblockInstance.PREFIX + skyblockInstance.getManagerLoader().getMessageManager().getColorizedMessage(message));
    }

    public Material getIcon() {
        return icon;
    }

    public String getDisplay() {
        return display;
    }

    public int getRank() {
        return rank;
    }

    public int getLevel() {
        return level;
    }

    public LevelRewardType getType() {
        return type;
    }

    public Material getMaterial() {
        return material;
    }

    public String getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }
}
