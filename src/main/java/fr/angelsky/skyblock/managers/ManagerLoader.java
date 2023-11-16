package fr.angelsky.skyblock.managers;

import fr.angelsky.skyblock.SkyblockInstance;
import fr.angelsky.skyblock.kits.IslandClassKitGiver;
import fr.angelsky.skyblock.managers.display.actionbar.ActionBarManager;
import fr.angelsky.skyblock.managers.player.level.LevelManager;
import fr.angelsky.skyblock.managers.region.RegionManager;
import fr.angelsky.skyblock.managers.display.scoreboard.ScoreboardManager;
import fr.angelsky.skyblock.managers.server.crates.CrateManager;
import fr.angelsky.skyblock.managers.utils.menu.MenuManager;
import fr.angelsky.skyblock.managers.utils.messages.MessageManager;
import fr.angelsky.skyblock.managers.utils.voteparty.VotePartyManager;
import fr.angelsky.skyblock.menus.shop.ShopManager;
import fr.angelsky.skyblock.placeholders.island.upgrade.UpgradeTokenPlaceholder;
import fr.angelsky.skyblock.placeholders.server.SkyBlockPrefixPlaceholder;
import fr.angelsky.skyblockapi.accounts.TempPlayer;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import org.checkerframework.checker.units.qual.C;

import java.util.logging.Level;

public class ManagerLoader {

    private final SkyblockInstance skyblockInstance;

    private MessageManager messageManager;
    private MenuManager menuManager;
    private ScoreboardManager scoreboardManager;
    private IslandClassKitGiver islandClassKitGiver;
    private VotePartyManager votePartyManager;
    private RegionManager regionManager;
    private LevelManager levelManager;
    private ActionBarManager actionBarManager;
    private ShopManager shopManager;
    private CrateManager crateManager;

    public ManagerLoader(SkyblockInstance skyblockInstance){
        this.skyblockInstance = skyblockInstance;
        load();
    }

    public void load(){
        this.messageManager = new MessageManager(skyblockInstance);
        this.menuManager = new MenuManager(skyblockInstance);
        this.scoreboardManager = new ScoreboardManager(skyblockInstance);
        this.islandClassKitGiver = new IslandClassKitGiver(skyblockInstance);
        this.votePartyManager = new VotePartyManager(skyblockInstance);
        this.regionManager = new RegionManager(skyblockInstance);
        this.levelManager = new LevelManager(skyblockInstance);
        this.actionBarManager = new ActionBarManager(skyblockInstance);
        this.shopManager = new ShopManager(skyblockInstance);
        this.crateManager = new CrateManager(skyblockInstance);
    }

    public void init(){
        this.votePartyManager.init();
        this.regionManager.init();
        this.levelManager.load();
        this.crateManager.loadCrates();

        new CommandManager(skyblockInstance);
        new EventManager(skyblockInstance);

        Bukkit.unloadWorld("world_nether", false);
        Bukkit.unloadWorld("world_the_end", false);

        initPlaceholders();
    }

    public void unload(){
        Bukkit.getLogger().log(Level.INFO, "Sauvegarde des TempPlayers");
        for(TempPlayer tempPlayer : skyblockInstance.getTempAccounts().values()){
            tempPlayer.saveAccount();
        }
        this.crateManager.unloadCrates();
    }

    public void initPlaceholders(){
        new UpgradeTokenPlaceholder(skyblockInstance).register();
        new SkyBlockPrefixPlaceholder(skyblockInstance).register();
    }

    public SkyblockInstance getSkyblockInstance() {
        return skyblockInstance;
    }

    public MessageManager getMessageManager() {
        return messageManager;
    }

    public MenuManager getMenuManager() {
        return menuManager;
    }

    public ScoreboardManager getScoreboardManager() {
        return scoreboardManager;
    }

    public IslandClassKitGiver getIslandClassKitGiver() {
        return islandClassKitGiver;
    }

    public VotePartyManager getVotePartyManager() {
        return votePartyManager;
    }

    public RegionManager getRegionManager() {
        return regionManager;
    }

    public LevelManager getLevelManager() {
        return levelManager;
    }

    public ActionBarManager getActionBarManager() {
        return actionBarManager;
    }

    public ShopManager getShopManager() {
        return shopManager;
    }

    public CrateManager getCrateManager() {
        return crateManager;
    }
}
