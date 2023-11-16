package fr.angelsky.skyblock.commands.player.island;

import com.bgsoftware.superiorskyblock.api.SuperiorSkyblock;
import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI;
import com.bgsoftware.superiorskyblock.api.commands.SuperiorCommand;
import fr.angelsky.skyblock.SkyblockInstance;
import fr.angelsky.skyblockapi.accounts.TempPlayer;
import org.bukkit.ChatColor;
import org.bukkit.block.Biome;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.math.BigDecimal;
import java.util.*;

public class CreateIslandCommand implements SuperiorCommand {

    private final SkyblockInstance skyblockInstance;

    public CreateIslandCommand(SkyblockInstance skyblockInstance){
        this.skyblockInstance = skyblockInstance;
    }

    @Override
    public List<String> getAliases() {
        return Collections.singletonList("create");
    }

    @Override
    public String getPermission() {
        return "";
    }

    @Override
    public String getUsage(Locale locale) {
        return "create";
    }

    @Override
    public String getDescription(Locale locale) {
        return "Créer une île";
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
        if(tempPlayer.getSuperiorPlayer().hasIsland()){
            player.sendMessage(SkyblockInstance.PREFIX + ChatColor.RED + "Vous avez déja une île !");
            return;
        }

        skyblockInstance.getManagerLoader().getMenuManager().getIslandCreationClassMenu().menu(player, tempPlayer);
    }

    @Override
    public List<String> tabComplete(SuperiorSkyblock superiorSkyblock, CommandSender commandSender, String[] strings) {
        return new ArrayList<>();
    }
}
