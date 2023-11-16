package fr.angelsky.skyblock.managers;

import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI;
import fr.angelsky.skyblock.SkyblockInstance;
import fr.angelsky.skyblock.commands.admin.island.UpgradeTokenAdminCommand;
import fr.angelsky.skyblock.commands.admin.player.level.PlayerLevelCommand;
import fr.angelsky.skyblock.commands.admin.utils.CrateKeyCommand;
import fr.angelsky.skyblock.commands.admin.utils.VotePartyCommand;
import fr.angelsky.skyblock.commands.player.LobbyCommand;
import fr.angelsky.skyblock.commands.player.SpawnCommand;
import fr.angelsky.skyblock.commands.player.island.CreateIslandCommand;
import fr.angelsky.skyblock.commands.player.island.IslandTokensCommand;
import fr.angelsky.skyblock.commands.player.menus.MenuCommand;
import fr.angelsky.skyblock.commands.player.menus.NiveauxCommand;
import fr.angelsky.skyblock.commands.player.menus.ShopCommand;
import fr.angelsky.skyblock.commands.player.misc.EglowCommand;
import fr.angelsky.skyblock.commands.player.misc.TrashCommand;
import fr.angelsky.skyblock.commands.utils.social.BoutiqueCommand;
import fr.angelsky.skyblock.commands.utils.social.DiscordCommand;
import fr.angelsky.skyblock.commands.utils.social.VoteCommand;

import java.util.Objects;

public class CommandManager {

    private final SkyblockInstance skyblockInstance;

    public CommandManager(SkyblockInstance skyblockInstance){
        this.skyblockInstance = skyblockInstance;
        init();
    }

    public void init(){
        Objects.requireNonNull(skyblockInstance.getSkyblock().getCommand("lobby")).setExecutor(new LobbyCommand(skyblockInstance));
        Objects.requireNonNull(skyblockInstance.getSkyblock().getCommand("spawn")).setExecutor(new SpawnCommand(skyblockInstance));
        Objects.requireNonNull(skyblockInstance.getSkyblock().getCommand("menu")).setExecutor(new MenuCommand(skyblockInstance));
        Objects.requireNonNull(skyblockInstance.getSkyblock().getCommand("niveaux")).setExecutor(new NiveauxCommand(skyblockInstance));
        Objects.requireNonNull(skyblockInstance.getSkyblock().getCommand("shop")).setExecutor(new ShopCommand(skyblockInstance));

        // UTILS

        Objects.requireNonNull(skyblockInstance.getSkyblock().getCommand("discord")).setExecutor(new DiscordCommand());
        Objects.requireNonNull(skyblockInstance.getSkyblock().getCommand("vote")).setExecutor(new VoteCommand());
        Objects.requireNonNull(skyblockInstance.getSkyblock().getCommand("boutique")).setExecutor(new BoutiqueCommand());

        // MISC
        Objects.requireNonNull(skyblockInstance.getSkyblock().getCommand("eglow")).setExecutor(new EglowCommand(skyblockInstance));
        Objects.requireNonNull(skyblockInstance.getSkyblock().getCommand("poubelle")).setExecutor(new TrashCommand());

        // ADMIN

        Objects.requireNonNull(skyblockInstance.getSkyblock().getCommand("voteparty")).setExecutor(new VotePartyCommand(skyblockInstance));
        Objects.requireNonNull(skyblockInstance.getSkyblock().getCommand("playerlevel")).setExecutor(new PlayerLevelCommand(skyblockInstance));
        Objects.requireNonNull(skyblockInstance.getSkyblock().getCommand("upgradetokens")).setExecutor(new UpgradeTokenAdminCommand(skyblockInstance));
        Objects.requireNonNull(skyblockInstance.getSkyblock().getCommand("cratekey")).setExecutor(new CrateKeyCommand(skyblockInstance));

        // SSB COMMANDS
        SuperiorSkyblockAPI.registerCommand(new CreateIslandCommand(skyblockInstance));
        SuperiorSkyblockAPI.registerCommand(new IslandTokensCommand(skyblockInstance));
    }

}
