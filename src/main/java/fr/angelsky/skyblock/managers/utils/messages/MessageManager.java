package fr.angelsky.skyblock.managers.utils.messages;

import fr.angelsky.angelskyapi.api.utils.file.ConfigUtils;
import fr.angelsky.skyblock.SkyblockInstance;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class MessageManager {

    private final SkyblockInstance skyblockInstance;

    private final ConfigUtils messageConfig;


    public MessageManager(SkyblockInstance skyblockInstance){
        this.skyblockInstance = skyblockInstance;
        this.messageConfig = new ConfigUtils(skyblockInstance.getSkyblock(), "messages.yml");
    }

    public String getColorizedMessageConfig(String id){
        return skyblockInstance.getAngelSkyApiInstance().translateChatColorHex(ChatColor.translateAlternateColorCodes('&', messageConfig.getString(id)));
    }

    public String getColorizedMessage(String message){
        return skyblockInstance.getAngelSkyApiInstance().translateChatColorHex(ChatColor.translateAlternateColorCodes('&', message));
    }

    // NOTE
    // WORKS ON:
    // Scoreboard, player#sendMessage, actionbar, titles,
    // DOESN'T WORK ON:
    // TabList header/footer

    public List<String> getColorizedMessageList(String id){
        List<String> msgs = new ArrayList<>();
        messageConfig.getList(id).forEach(t -> {
            msgs.add(skyblockInstance.getAngelSkyApiInstance().translateChatColorHex(ChatColor.translateAlternateColorCodes('&', t.toString())));
        });
        return msgs;
    }

    public ConfigUtils getMessageConfig() {
        return messageConfig;
    }
}
