package fr.angelsky.skyblock.managers.utils.menu;

import fr.angelsky.skyblock.SkyblockInstance;
import fr.angelsky.skyblock.menus.island.IslandCreationClassMenu;
import fr.angelsky.skyblock.menus.island.IslandTokensMenu;
import fr.angelsky.skyblock.menus.island.LevelsMenu;
import fr.angelsky.skyblock.menus.main.MainMenu;
import fr.angelsky.skyblock.menus.store.StoreMenu;

public class MenuManager {

    private final SkyblockInstance skyblockInstance;

    private IslandCreationClassMenu islandCreationClassMenu;
    private IslandTokensMenu islandTokensMenu;
    private MainMenu mainMenu;
    private LevelsMenu levelsMenu;
    private StoreMenu storeMenu;

    public MenuManager(SkyblockInstance skyblockInstance){
        this.skyblockInstance = skyblockInstance;
        init();
    }

    public void init(){
        this.islandCreationClassMenu = new IslandCreationClassMenu(skyblockInstance);
        this.mainMenu = new MainMenu(skyblockInstance);
        this.levelsMenu = new LevelsMenu(skyblockInstance);
        this.islandTokensMenu = new IslandTokensMenu(skyblockInstance);
        this.storeMenu = new StoreMenu(skyblockInstance);
    }

    public IslandCreationClassMenu getIslandCreationClassMenu() {
        return islandCreationClassMenu;
    }

    public MainMenu getMainMenu() {
        return mainMenu;
    }

    public LevelsMenu getLevelsMenu() {
        return levelsMenu;
    }

    public IslandTokensMenu getIslandTokensMenu() {
        return islandTokensMenu;
    }

    public StoreMenu getStoreMenu() {
        return storeMenu;
    }

}
