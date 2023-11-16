package fr.angelsky.skyblock.managers.utils.voteparty;

import fr.angelsky.angelskyapi.api.utils.file.ConfigUtils;
import fr.angelsky.skyblock.SkyblockInstance;

public class VotePartyManager {

    private final SkyblockInstance skyblockInstance;
    private ConfigUtils votePartyConfig;

    private VoteParty voteParty;

    public VotePartyManager(SkyblockInstance skyblockInstance){
        this.skyblockInstance = skyblockInstance;
    }

    public void init(){
        this.votePartyConfig = new ConfigUtils(skyblockInstance.getSkyblock(), "vote_party.yml");
        this.voteParty = new VoteParty(skyblockInstance, votePartyConfig);
        this.voteParty.init();
    }

    public ConfigUtils getVotePartyConfig() {
        return votePartyConfig;
    }

    public VoteParty getVoteParty() {
        return voteParty;
    }
}
