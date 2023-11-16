package fr.angelsky.skyblock.managers.utils.voteparty;

import fr.angelsky.angelskyapi.api.utils.file.ConfigUtils;
import fr.angelsky.skyblock.SkyblockInstance;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;

public class VoteParty {

    private final SkyblockInstance skyblockInstance;
    private final ConfigUtils votePartyConfig;

    private int requiredVotes;
    private int currentVotes;
    private final List<String> messages = new ArrayList<>();
    private final List<String> commands_global = new ArrayList<>();
    private final List<String> commands_player = new ArrayList<>();

    public VoteParty(SkyblockInstance skyblockInstance, ConfigUtils votePartyConfig) {
        this.skyblockInstance = skyblockInstance;
        this.votePartyConfig = votePartyConfig;
    }

    public void init(){
        this.requiredVotes = votePartyConfig.getInt("required_votes");
        this.currentVotes = 0;
        votePartyConfig.getList("messages").forEach(t -> messages.add(skyblockInstance.getManagerLoader().getMessageManager().getColorizedMessage(t.toString())));
        votePartyConfig.getList("commands_global").forEach(t -> commands_global.add(t.toString()));
        votePartyConfig.getList("commands_player").forEach(t -> commands_player.add(t.toString()));
    }

    public void checkVotes(){
        if(this.currentVotes >= this.requiredVotes){
            this.complete();
        }
    }

    public void complete(){
        this.reset();
        messages.forEach(message -> {
            Bukkit.broadcast(Component.text(message));
        });

        commands_global.forEach(command -> {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
        });

        commands_player.forEach(command -> {
            Bukkit.getOnlinePlayers().forEach(player -> {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.replaceAll("%player%", player.getName()));
            });
        });
    }

    public void reset(){
        this.currentVotes = 0;
    }

    public void addVote(int amount){
        this.currentVotes += amount;
        checkVotes();
    }

    public int getCurrentVotes() {
        return currentVotes;
    }

    public int getRequiredVotes() {
        return requiredVotes;
    }
}
