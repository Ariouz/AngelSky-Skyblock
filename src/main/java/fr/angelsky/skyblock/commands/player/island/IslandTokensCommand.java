package fr.angelsky.skyblock.commands.player.island;

import com.bgsoftware.superiorskyblock.api.SuperiorSkyblock;
import com.bgsoftware.superiorskyblock.api.commands.SuperiorCommand;
import fr.angelsky.skyblock.SkyblockInstance;
import fr.angelsky.skyblockapi.accounts.TempPlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class IslandTokensCommand implements SuperiorCommand {
    
    private final SkyblockInstance skyblockInstance;

    public IslandTokensCommand(SkyblockInstance skyblockInstance){
        this.skyblockInstance = skyblockInstance;
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("tokens", "token");
    }

    @Override
    public String getPermission() {
        return "";
    }

    @Override
    public String getUsage(Locale locale) {
        return "tokens";
    }

    @Override
    public String getDescription(Locale locale) {
        return "Voir vos points d'amélioration";
    }

    @Override
    public int getMinArgs() {
        return 1;
    }

    @Override
    public int getMaxArgs() {
        return 1;
    }

    @Override
    public boolean canBeExecutedByConsole() {
        return false;
    }

    @Override
    public boolean displayCommand() {
        return true;
    }

    @Override
    public void execute(SuperiorSkyblock superiorSkyblock, CommandSender commandSender, String[] strings) {
        Player player = (Player) commandSender;
        TempPlayer tempPlayer = skyblockInstance.getTempAccounts().get(player.getName());
        skyblockInstance.getManagerLoader().getMenuManager().getIslandTokensMenu().open(player, tempPlayer);
    }

    @Override
    public List<String> tabComplete(SuperiorSkyblock superiorSkyblock, CommandSender commandSender, String[] strings) {
        return null;
    }
}
