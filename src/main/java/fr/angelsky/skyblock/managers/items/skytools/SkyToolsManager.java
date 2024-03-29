package fr.angelsky.skyblock.managers.items.skytools;

import fr.angelsky.angelskyapi.api.utils.file.ConfigUtils;
import fr.angelsky.skyblock.SkyblockInstance;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.Objects;

public class SkyToolsManager {

    private final SkyblockInstance skyblockInstance;
    private final ArrayList<SkyTool> tools = new ArrayList<>();
    private final ConfigUtils toolsConfig;

    public SkyToolsManager(SkyblockInstance skyblockInstance){
        this.skyblockInstance = skyblockInstance;
        this.toolsConfig = new ConfigUtils(skyblockInstance.getSkyblock(), "items", "sky_tools.yml");
    }

    public void loadTools()
    {
        for (String toolId : toolsConfig.getYamlConfiguration().getKeys(false))
        {
            ConfigurationSection toolSection = toolsConfig.getYamlConfiguration().getConfigurationSection(toolId);
            if (toolSection == null) continue;
            this.tools.add(new SkyTool(
                    toolId,
                    MiniMessage.miniMessage().deserialize(Objects.requireNonNull(toolSection.getString("display"))),
                    toolSection.getString("material"),
                    toolSection.getList("lore")
            ));
        }
    }

    public SkyTool getTool(String toolId)
    {
        return this.tools.stream().filter(tool -> tool.getId().equalsIgnoreCase(toolId)).findFirst().orElse(null);
    }

    public ArrayList<SkyTool> getTools() {
        return tools;
    }
}
