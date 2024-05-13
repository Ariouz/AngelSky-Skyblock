package fr.angelsky.skyblock;

import fr.angelsky.angelskyapi.AngelskyApi;
import fr.angelsky.angelskyapi.api.AngelSkyApiInstance;
import fr.angelsky.angelskycoalitions.AngelSkyCoalitions;
import fr.angelsky.skyblock.managers.ManagerLoader;
import fr.angelsky.skyblock.managers.utils.Keys;
import fr.angelsky.skyblockapi.SkyblockApi;
import fr.angelsky.skyblockapi.accounts.TempPlayer;
import fr.angelsky.skyblockapi.api.SkyBlockApiInstance;
import fr.mrmicky.fastinv.FastInvManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class SkyblockInstance {

    public static final String PREFIX = translateChatColorHex(ChatColor.translateAlternateColorCodes('&', "##ff0000#&lS##f81604#&lk##f12b08#&ly##ea410c#&lb##e25610#&ll##db6c14#&lo##d48118#&lc##cd971c#&lk &f» &f"));
    public static final String COIN = translateChatColorHex(ChatColor.translateAlternateColorCodes('&', "##F3E50E#⛁"));
    public static final int MAX_RANK = 29;

    private final Skyblock skyblock;
    private SkyBlockApiInstance skyBlockApiInstance;
    private AngelSkyApiInstance angelSkyApiInstance;
    private AngelSkyCoalitions angelSkyCoalitions;

    private final HashMap<String, TempPlayer> tempAccounts = new HashMap<>();

    private ManagerLoader managerLoader;
    private Keys keys;

    public SkyblockInstance(Skyblock skyblockk){
        this.skyblock = skyblockk;
    }

    public void load() throws Exception {
        if(Bukkit.getPluginManager().getPlugin("AngelskyApi") != null){
            AngelskyApi angelskyApi = (AngelskyApi) Bukkit.getPluginManager().getPlugin("AngelskyApi");
            assert angelskyApi != null;
            this.angelSkyApiInstance = angelskyApi.getAngelSkyApiInstance();

            skyblock.getLogger().info("Instance API récupérée");
        }else{
            throw new Exception("L'API n'est pas détéctée !");
        }

        if(Bukkit.getPluginManager().getPlugin("SkyblockAPI") != null){
            SkyblockApi skyblockApi = (SkyblockApi) Bukkit.getPluginManager().getPlugin("SkyblockAPI");
            assert skyblockApi != null;
            this.skyBlockApiInstance = skyblockApi.getSkyBlockApiInstance();

            skyblock.getLogger().info("Instance SkyblockAPI récupérée");
        }else{
            throw new Exception("L'API Skyblock n'est pas détéctée !");
        }

        if(Bukkit.getPluginManager().getPlugin("AngelSkyCoalitions") != null){
            this.angelSkyCoalitions = (AngelSkyCoalitions) Bukkit.getPluginManager().getPlugin("AngelSkyCoalitions");
            assert angelSkyCoalitions != null;
            skyblock.getLogger().info("Instance AngelSkyCoalitions récupérée");
        }else{
            throw new Exception("AngelSkyCoalitions n'est pas détéctée !");
        }

        FastInvManager.register(this.skyblock);

        this.managerLoader = new ManagerLoader(this);
        this.keys = new Keys(this);
        this.managerLoader.init();

        for(Player players : Bukkit.getOnlinePlayers()){
            TempPlayer tempPlayer = new TempPlayer(this.getSkyBlockApiInstance(), players.getUniqueId(), players.getName());
            tempPlayer.getPlayerLevel().refreshLevels(managerLoader.getLevelManager().getLevelRankManager(), managerLoader.getLevelManager().getLevelColor());
            this.getTempAccounts().put(players.getName(), tempPlayer);
            this.getManagerLoader().getScoreboardManager().addPlayer(players);
        }

        //managerLoader.getActionBarManager().sendInfiniteActionBarAll();

    }

    public void unload(){
        this.managerLoader.unload();
        for(Player players : Bukkit.getOnlinePlayers()){
            this.getManagerLoader().getScoreboardManager().removePlayer(players);
            this.getTempAccounts().remove(players.getName());
        }
    }

    public boolean isInteger(String s){
        return isInteger(s, 10);
    }

    public boolean isInteger(String s, int radix) {
        if (s.isEmpty())
            return false;
        for (int i = 0; i < s.length(); i++) {
            if (i == 0 && s.charAt(i) == '-') {
                if (s.length() == 1)
                    return false;
            } else if (Character.digit(s.charAt(i), radix) < 0) {
                return false;
            }
        }
        return true;
    }

    public Skyblock getSkyblock() {
        return skyblock;
    }

    public AngelSkyApiInstance getAngelSkyApiInstance() {
        return angelSkyApiInstance;
    }

    public ManagerLoader getManagerLoader() {
        return managerLoader;
    }

    public SkyBlockApiInstance getSkyBlockApiInstance() {
        return this.skyBlockApiInstance;
    }

    public HashMap<String, TempPlayer> getTempAccounts() {
        return this.tempAccounts;
    }

    public AngelSkyCoalitions getAngelSkyCoalitions()
    {
        return this.angelSkyCoalitions;
    }

    public static String translateChatColorHex(String stringOfTranslate) {
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < stringOfTranslate.length(); ++i) {
            if (i >= stringOfTranslate.length() - 9) {
                sb.append(stringOfTranslate.charAt(i));
            } else {
                String temp = stringOfTranslate.substring(i, i + 9);
                if (temp.startsWith("##") && temp.endsWith("#")) {
                    try {
                        Integer.parseInt(temp.substring(2, 8), 16);
                        sb.append("§x");
                        char[] c = temp.toCharArray();

                        for(int i1 = 2; i1 < c.length - 1; ++i1) {
                            sb.append('§').append(c[i1]);
                        }

                        i += 8;
                    } catch (NumberFormatException var7) {
                    }
                } else {
                    sb.append(stringOfTranslate.charAt(i));
                }
            }
        }

        return sb.toString();
    }

    public Keys getKeys() {
        return keys;
    }
}
