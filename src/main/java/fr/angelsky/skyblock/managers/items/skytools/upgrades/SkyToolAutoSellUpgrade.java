package fr.angelsky.skyblock.managers.items.skytools.upgrades;

import fr.angelsky.skyblock.SkyblockInstance;
import fr.angelsky.skyblock.managers.items.skytools.SkyToolUpgrade;
import fr.angelsky.skyblock.managers.items.skytools.SkyToolUpgradeType;
import fr.angelsky.skyblock.menus.shop.ShopItem;
import fr.angelsky.skyblock.menus.shop.ShopManager;
import fr.angelsky.skyblockapi.accounts.TempPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class SkyToolAutoSellUpgrade extends SkyToolUpgrade {

	public SkyToolAutoSellUpgrade(String id, String display, SkyToolUpgradeType type) {
		super(id, display, type, 1);
	}

	public List<ItemStack> apply(List<ItemStack> drops, Player player, SkyblockInstance skyblockInstance)
	{
		TempPlayer tempPlayer = skyblockInstance.getTempAccounts().get(player.getName());
		List<ItemStack> notSold = new ArrayList<>();
		ShopManager shopManager = skyblockInstance.getManagerLoader().getShopManager();

		for (ItemStack drop : drops)
		{
			if (shopManager.getShopItem(drop.getType()) == null)
			{
				notSold.add(drop);
				continue;
			}
			ShopItem shopItem = shopManager.getShopItem(drop.getType());
			if (!shopItem.isCanBeSold())
			{
				notSold.add(drop);
				continue;
			}
			skyblockInstance.getSkyBlockApiInstance().getEconomy().depositPlayer(player, drop.getAmount()*shopItem.getSellPrice());
		}
		return notSold;
	}

}
