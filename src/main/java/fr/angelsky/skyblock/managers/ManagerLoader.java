package fr.angelsky.skyblock.managers;

import fr.angelsky.angelskyeconomy.AngelSkyEconomy;
import fr.angelsky.skyblock.SkyblockInstance;
import fr.angelsky.skyblock.kits.IslandClassKitGiver;
import fr.angelsky.skyblock.managers.display.actionbar.ActionBarManager;
import fr.angelsky.skyblock.managers.display.scoreboard.ScoreboardManager;
import fr.angelsky.skyblock.managers.display.tablist.TabListDisplayer;
import fr.angelsky.skyblock.managers.items.skytools.SkyToolsManager;
import fr.angelsky.skyblock.managers.player.level.LevelManager;
import fr.angelsky.skyblock.managers.player.level.experience.PlayerExperienceManager;
import fr.angelsky.skyblock.managers.player.reward.daily.DailyRewardManager;
import fr.angelsky.skyblock.managers.region.RegionManager;
import fr.angelsky.skyblock.managers.server.crates.CrateManager;
import fr.angelsky.skyblock.managers.server.top.LevelTopManager;
import fr.angelsky.skyblock.managers.utils.menu.MenuManager;
import fr.angelsky.skyblock.managers.utils.messages.MessageManager;
import fr.angelsky.skyblock.managers.utils.voteparty.VotePartyManager;
import fr.angelsky.skyblock.menus.shop.ShopManager;
import fr.angelsky.skyblock.placeholders.global.SkyblockBaltopPlaceholder;
import fr.angelsky.skyblock.placeholders.global.SkyblockLevelTopPlaceholder;
import fr.angelsky.skyblock.placeholders.island.upgrade.UpgradeTokenPlaceholder;
import fr.angelsky.skyblock.placeholders.server.SkyBlockPrefixPlaceholder;
import fr.angelsky.skyblockapi.accounts.TempPlayer;
import org.bukkit.Bukkit;

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
    private AngelSkyEconomy angelSkyEconomy;
    private DailyRewardManager dailyRewardManager;
    private TabListDisplayer tabListDisplayer;
    private LevelTopManager levelTopManager;
    private PlayerExperienceManager playerExperienceManager;
    private SkyToolsManager skyToolsManager;

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
        this.dailyRewardManager = new DailyRewardManager(skyblockInstance, messageManager);
        this.tabListDisplayer = new TabListDisplayer(skyblockInstance);
        this.levelTopManager = new LevelTopManager(skyblockInstance);
        this.playerExperienceManager = new PlayerExperienceManager(skyblockInstance);
        this.skyToolsManager = new SkyToolsManager(skyblockInstance);

        if (skyblockInstance.getSkyblock().getServer().getPluginManager().getPlugin("AngelSkyEconomy") != null){
            this.skyblockInstance.getSkyblock().getLogger().log(Level.INFO, "Instance AngelSkyEconomy recuperee");
            this.angelSkyEconomy = (AngelSkyEconomy) skyblockInstance.getSkyblock().getServer().getPluginManager().getPlugin("AngelSkyEconomy");
        }
    }
    public void init(){
        this.votePartyManager.init();
        this.regionManager.init();
        this.levelManager.load();
        this.crateManager.loadCrates();
        this.tabListDisplayer.run();
        this.levelTopManager.init();
        this.skyToolsManager.loadTools();

        new CommandManager(skyblockInstance);
        new EventManager(skyblockInstance);

        Bukkit.unloadWorld("world_nether", false);
        Bukkit.unloadWorld("world_the_end", false);

        initPlaceholders();
    }

    public void unload(){
        Bukkit.getLogger().log(Level.INFO, "Sauvegarde des TempPlayers");
        skyblockInstance.getTempAccounts().values().forEach(TempPlayer::saveAccount);
        this.crateManager.unloadCrates();
        this.dailyRewardManager.unloadAll();
    }

    public void initPlaceholders(){
        new UpgradeTokenPlaceholder(skyblockInstance).register();
        new SkyBlockPrefixPlaceholder(skyblockInstance).register();
        new SkyblockBaltopPlaceholder(skyblockInstance).register();
        new SkyblockLevelTopPlaceholder(skyblockInstance).register();
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

    public AngelSkyEconomy getAngelSkyEconomy() {
        return angelSkyEconomy;
    }

    public DailyRewardManager getDailyRewardManager() {
        return dailyRewardManager;
    }

    public LevelTopManager getLevelTopManager() {
        return levelTopManager;
    }

    public PlayerExperienceManager getPlayerExperienceManager() {
        return playerExperienceManager;
    }

    public SkyToolsManager getSkyToolsManager() {
        return skyToolsManager;
    }
}
