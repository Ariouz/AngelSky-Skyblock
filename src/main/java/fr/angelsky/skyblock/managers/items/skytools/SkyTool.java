package fr.angelsky.skyblock.managers.items.skytools;


import io.th0rgal.oraxen.api.OraxenItems;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SkyTool {

    private final String id;
    private final Component display;
    private final ItemStack baseItem;
    private List<Component> lore;

    public SkyTool(String id, Component display, String baseItemType, List<?> lore)
    {
        this.id = id;
        this.display = display.decoration(TextDecoration.ITALIC, false);
        this.baseItem = parseBaseItem(baseItemType);
        this.lore = parseLore(lore);
    }

    public ItemStack getItem(){
        ItemStack itemStack = new ItemStack(this.baseItem);
        ItemMeta meta = itemStack.getItemMeta();
        meta.displayName(this.display);
        meta.lore(this.lore);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    public Component getDisplay() {
        return display;
    }

    public String getId() {
        return id;
    }

    private List<Component> parseLore(List<?> lore)
    {
        List<Component> parsedLore = new ArrayList<>();
        lore.forEach(line ->
                parsedLore.add( MiniMessage.miniMessage().deserialize(line.toString()).decoration(TextDecoration.ITALIC, false)));
        return parsedLore;
    }

    private ItemStack parseBaseItem(String baseItemType)
    {
        if (baseItemType.startsWith("oraxen:"))
            return OraxenItems.getItemById(baseItemType.replaceFirst("oraxen:", "")).build();
        return new ItemStack(Objects.requireNonNull(Material.getMaterial(baseItemType)));
    }
}
