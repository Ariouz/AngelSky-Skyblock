package fr.angelsky.skyblock.managers.items.skytools;

import fr.angelsky.skyblock.SkyblockInstance;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

public class SkyToolDurabilityManager {

	private final SkyblockInstance skyblockInstance;
	private SkyToolsManager skyToolsManager;

	public SkyToolDurabilityManager(SkyblockInstance skyblockInstance)
	{
		this.skyblockInstance = skyblockInstance;
	}

	public void init()
	{
		this.skyToolsManager = skyblockInstance.getManagerLoader().getSkyToolsManager();
	}

	public ItemStack insertDurabilityLore(ItemStack item)
	{
		if (!item.hasItemMeta()) return item;
		String display = skyToolsManager.getToolsConfig().getString("durability_display");
		ItemMeta meta = item.getItemMeta();
		List<Component> lore = meta.lore();
		if (lore == null) return item;
		Component newLine = MiniMessage.miniMessage().deserialize(display.
										replaceAll("%durability%", String.valueOf(getDurability(meta)))
										.replaceAll("%max_durability%", String.valueOf(getMaxDurability(meta))))
								.decoration(TextDecoration.ITALIC, false);
		lore.add(lore.size(), newLine);
		meta.lore(lore);
		item.setItemMeta(meta);
		return item;
	}

	public ItemStack updateDurabilityLore(ItemStack item)
	{
		if (!item.hasItemMeta()) return item;
		String display = skyToolsManager.getToolsConfig().getString("durability_display");
		ItemMeta meta = item.getItemMeta();
		List<Component> lore = meta.lore();
		if (lore == null) return item;
		lore.remove(lore.size() - 1);
		Component newLine = MiniMessage.miniMessage().deserialize(display.
						replaceAll("%durability%", String.valueOf(getDurability(meta)))
						.replaceAll("%max_durability%", String.valueOf(getMaxDurability(meta))))
				.decoration(TextDecoration.ITALIC, false);
		lore.add(lore.size(), newLine);
		meta.lore(lore);
		item.setItemMeta(meta);
		return item;
	}

	public void setMaxDurability(ItemMeta meta, int value)
	{
		PersistentDataContainer dataContainer = meta.getPersistentDataContainer();
		dataContainer.set(skyblockInstance.getKeys().SKYTOOL_MAX_DURABILITY, PersistentDataType.INTEGER, value);
	}

	public int getMaxDurability(ItemMeta meta)
	{
		PersistentDataContainer dataContainer = meta.getPersistentDataContainer();
		Integer durability = dataContainer.get(skyblockInstance.getKeys().SKYTOOL_MAX_DURABILITY, PersistentDataType.INTEGER);
		if (durability == null) return 0;
		return durability;
	}

	public void setDurability(ItemMeta meta, int value)
	{
		PersistentDataContainer dataContainer = meta.getPersistentDataContainer();
		dataContainer.set(skyblockInstance.getKeys().SKYTOOL_DURABILITY, PersistentDataType.INTEGER, value);
	}

	public void addDurability(ItemMeta meta, int value)
	{
		setDurability(meta, Math.min(getDurability(meta) + value, getDurability(meta)));
	}

	public void removeDurability(ItemMeta meta, int value)
	{
		setDurability(meta, Math.max(getDurability(meta) - value, 0));
	}

	public int getDurability(ItemMeta meta)
	{
		PersistentDataContainer dataContainer = meta.getPersistentDataContainer();
		Integer durability = dataContainer.get(skyblockInstance.getKeys().SKYTOOL_DURABILITY, PersistentDataType.INTEGER);
		if (durability == null) return 0;
		return durability;
	}

}
