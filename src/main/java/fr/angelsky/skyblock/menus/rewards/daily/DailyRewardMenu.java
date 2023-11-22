package fr.angelsky.skyblock.menus.rewards.daily;

import fr.angelsky.angelskyapi.api.utils.HexColors;
import fr.angelsky.angelskyapi.api.utils.time.TimeUnit;
import fr.angelsky.skyblock.SkyblockInstance;
import fr.angelsky.skyblock.managers.player.reward.daily.DailyReward;
import fr.angelsky.skyblock.managers.player.reward.daily.DailyRewardManager;
import fr.angelsky.skyblock.managers.player.reward.daily.PlayerTempDailyReward;
import fr.mrmicky.fastinv.FastInv;
import fr.mrmicky.fastinv.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DailyRewardMenu {

    private final SkyblockInstance skyblockInstance;

    public DailyRewardMenu(SkyblockInstance skyblockInstance){
        this.skyblockInstance = skyblockInstance;
    }

    public void openDailyRewardsMenu(Player player){
        DailyRewardManager dailyRewardManager = skyblockInstance.getManagerLoader().getDailyRewardManager();
        PlayerTempDailyReward playerTempDailyReward = dailyRewardManager.getPlayerTempDailyReward(player);
        int level = playerTempDailyReward.getRewardLevel();

        FastInv inv = new FastInv(5*9, "Récompense Journalière");
        for (int i : inv.getBorders()) {
            inv.setItem(i, new ItemBuilder(Material.BLUE_STAINED_GLASS_PANE).name(ChatColor.GREEN + "").build(), event -> event.setCancelled(true));
        }

        dailyRewardManager.checkMaxTime(playerTempDailyReward.getUuid());

        int[] slots = {10, 11, 20, 29, 30, 31, 22, 13, 14, 15, 24, 33, 34};
        for (DailyReward dailyReward : dailyRewardManager.getRewards().values()){
            ItemStack reward = new ItemBuilder(getRewardIcon(playerTempDailyReward, dailyReward.getLevel(), level))
                    .name(skyblockInstance.getManagerLoader().getMessageManager().getColorizedMessage("&6&lJour " +HexColors.DARK_GOLD + (dailyReward.getLevel()+1)))
                    .lore(getRewardLore(player, playerTempDailyReward, dailyReward))
                    .build();

            inv.setItem(slots[dailyReward.getLevel()], reward, event -> {
                if (dailyRewardManager.canGetReward(playerTempDailyReward.getUuid()) && dailyReward.getLevel() == level){
                    if (dailyReward.isNeedInventorySlot() && player.getInventory().firstEmpty() == -1){
                        player.sendMessage(skyblockInstance.getManagerLoader().getMessageManager().getColorizedMessage(SkyblockInstance.PREFIX + HexColors.SMOOTH_RED + "Vous avez besoin d'espace dans votre inventaire afin de recevoir " + HexColors.LIGHTER_GRAY + dailyReward.getDisplay()));
                        player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 30, 30);
                        return;
                    }
                    dailyReward.getCommands().forEach(command -> {
                        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command.replaceAll("%player%", player.getName()));
                    });
                    if (dailyReward.isSendMessage())
                        player.sendMessage(skyblockInstance.getManagerLoader().getMessageManager().getColorizedMessage(SkyblockInstance.PREFIX + "Vous avez reçu " + HexColors.SMOOTH_GOLD + dailyReward.getDisplay()));
                    dailyRewardManager.updatePlayerTemp(playerTempDailyReward, level + 1);
                    player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 30, 30);
                    this.openDailyRewardsMenu(player);
                }else{
                    player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 30, 30);
                }
            });
        }
        player.openInventory(inv.getInventory());
    }

    private Material getRewardIcon(PlayerTempDailyReward playerTempDailyReward, int rewardLevel, int level){
        DailyRewardManager dailyRewardManager = skyblockInstance.getManagerLoader().getDailyRewardManager();
        if (level < rewardLevel) return Material.CHEST_MINECART;
        else if(level == rewardLevel && !dailyRewardManager.canGetReward(playerTempDailyReward.getUuid())) return Material.CHEST_MINECART;
        else if(level == rewardLevel && dailyRewardManager.canGetReward(playerTempDailyReward.getUuid())) return Material.CHEST_MINECART;
        return Material.MINECART;
    }

    private List<String> getRewardLore(Player player, PlayerTempDailyReward playerTempDailyReward, DailyReward dailyReward){
        DailyRewardManager dailyRewardManager = skyblockInstance.getManagerLoader().getDailyRewardManager();
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.translateAlternateColorCodes('&', "&a"));
        lore.add(skyblockInstance.getManagerLoader().getMessageManager().getColorizedMessage("&7» "+HexColors.SMOOTH_GOLD+"&lRécompense:"));
        lore.add(skyblockInstance.getManagerLoader().getMessageManager().getColorizedMessage("&7»   &f• "+HexColors.LIGHTER_GRAY + dailyReward.getDisplay()));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7"));


        if (dailyRewardManager.canGetReward(playerTempDailyReward.getUuid()) && playerTempDailyReward.getRewardLevel() == dailyReward.getLevel()){
            lore.add(skyblockInstance.getManagerLoader().getMessageManager().getColorizedMessage("&7» " + HexColors.LIGHT_GREEN + "Vous pouvez récupérer cette récompense"));
            lore.add(skyblockInstance.getManagerLoader().getMessageManager().getColorizedMessage("&7» " + HexColors.LIGHT_GREEN + "Il vous reste " + TimeUnit.toShortDisplay(playerTempDailyReward.getNextRewardMax() - new Date().getTime(), ", ") + " avant expiration"));
        }
        else if (playerTempDailyReward.getRewardLevel() < dailyReward.getLevel()){
            lore.add(skyblockInstance.getManagerLoader().getMessageManager().getColorizedMessage("&7» " + HexColors.SMOOTH_RED + "Vous ne pouvez pas encore récupérer cette récompense"));
        }else if (playerTempDailyReward.getRewardLevel() == dailyReward.getLevel() && !dailyRewardManager.canGetReward(playerTempDailyReward.getUuid())){
            lore.add(skyblockInstance.getManagerLoader().getMessageManager().getColorizedMessage("&7» " + HexColors.SMOOTH_RED + "Vous ne pouvez pas encore récupérer cette récompense"));
            lore.add(skyblockInstance.getManagerLoader().getMessageManager().getColorizedMessage("&7» " + HexColors.SMOOTH_RED + "Vous devez attendre " + TimeUnit.toShortDisplay(playerTempDailyReward.getNextReward() - new Date().getTime(), ", ")));
        }else{
            lore.add(skyblockInstance.getManagerLoader().getMessageManager().getColorizedMessage("&7» " + HexColors.DARK_GOLD + "Vous avez déja récupéré cette récompense"));
        }
        lore.add(ChatColor.GRAY + "");
        return lore;
    }

}
