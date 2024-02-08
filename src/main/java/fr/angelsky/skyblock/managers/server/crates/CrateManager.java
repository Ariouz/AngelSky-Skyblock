package fr.angelsky.skyblock.managers.server.crates;

import fr.angelsky.angelskyapi.api.utils.file.ConfigUtils;
import fr.angelsky.skyblock.SkyblockInstance;
import fr.angelsky.skyblock.managers.ItemManager;
import fr.mrmicky.fastinv.FastInv;
import fr.mrmicky.fastinv.ItemBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.*;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentWrapper;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.*;

public class CrateManager {
    private final SkyblockInstance skyblockInstance;

    private final ArrayList<Crate> crates = new ArrayList<>();

    private final HashMap<Location, Crate> openingCrates = new HashMap<>();

    private final HashMap<Location, HashMap<Item, Integer>> fountainAs = new HashMap<>();

    private final ConfigUtils crateConfig;

    private final ConfigUtils crateLootsConfig;

    public static final int CRATE_OPEN_TIME = 5*20;
    public static final int ITEM_LIFE_TIME = 2;

    private int task, fountainTask;

    public CrateManager(SkyblockInstance skyblockInstance) {
        this.skyblockInstance = skyblockInstance;
        this.crateConfig = new ConfigUtils(skyblockInstance.getSkyblock(), "crates.yml");
        this.crateLootsConfig = new ConfigUtils(skyblockInstance.getSkyblock(), "crate_loots.yml");
    }

    public void loadCrates() {
        for (String id : this.crateConfig.getYamlConfiguration().getKeys(false)) {
            this.crates.add(new Crate(this.skyblockInstance, this.crateConfig
                    .getString(id + ".oraxen_key_id"), this.crateConfig
                    .getString(id + ".oraxen_box_id"), id,

                    Material.getMaterial(this.crateConfig.getString(id + ".particle_material")), this.crateLootsConfig));
            this.skyblockInstance.getSkyblock().getLogger().info("Loaded crate " + id);
        }
        particleRun();
        fountainRun();
    }

    public void unloadCrates() {
        Bukkit.getScheduler().cancelTask(this.task);
        Bukkit.getScheduler().cancelTask(this.fountainTask);
    }

    public void particleRun() {
        /*ArrayList<ParticleType> particles = new ArrayList<>();
        particles.add(ParticleType.of("LAVA"));
        particles.add(ParticleType.of("BLOCK_CRACK")); // TODO ADD BLOCK MATERIAL*/
        this.task = new BukkitRunnable() {
            public void run() {
                ArrayList<Location> toRemove = new ArrayList<>();
                for (Map.Entry<Location, Crate> entries : openingCrates.entrySet()) {
                    entries.getValue().decreaseOpenTime(entries.getKey());
                    //particles.get(0).spawn(entries.getKey().getWorld(), entries.getKey(), 1, 2, 2, 2);
                    //particles.get(1).spawn(entries.getKey().getWorld(), entries.getKey().clone().add(0.0D, 0.6D, 0.0D), 5, 1, 1, 1);

                    if (entries.getValue().getOpenTime(entries.getKey()) <= 0){
                        endOpening(entries.getKey(), entries.getValue());
                        toRemove.add(entries.getKey());
                    }

                }
                toRemove.forEach(loc -> {
                    openingCrates.get(loc).removeOpenTime(loc);
                    openingCrates.remove(loc);
                });
            }
        }.runTaskTimer(this.skyblockInstance.getSkyblock(), 0L, 1L).getTaskId();
    }

    public void fountainRun(){
        this.fountainTask = new BukkitRunnable() {
            @Override
            public void run() {
                for(Map.Entry<Location, Crate> entries : openingCrates.entrySet()){
                    fountainAs.get(entries.getKey()).put(itemFountain(entries.getKey(), entries.getValue()), 0);
                }
                new HashMap<>(fountainAs).forEach((k,v) -> new HashMap<>(v).forEach((item, time) -> {
                    if(time >= ITEM_LIFE_TIME){
                        item.remove();
                        fountainAs.get(k).remove(item);
                    }else{ fountainAs.get(k).replace(item, time+1); }
                }));
            }
        }.runTaskTimer(skyblockInstance.getSkyblock(), 0, 3).getTaskId();
    }

    public void endOpening(Location location, Crate crate){
        final Item[] item = new Item[1];
        final ItemStack[] finalItem = {crate.getFinalItem()};

        new BukkitRunnable() {
            @Override
            public void run() {
                finalItem[0] = crate.getRandomLoot().getItem();
                System.out.println(finalItem[0].getType());
                item[0] = (Item) location.getWorld().spawnEntity(location.toCenterLocation().add(0, 1, 0), EntityType.DROPPED_ITEM);
                item[0].setCanPlayerPickup(false);
                item[0].setItemStack(finalItem[0].clone());
                item[0].setCanMobPickup(false);
                item[0].setWillAge(false);
                item[0].getItemStack().setAmount(1);
                item[0].setGravity(false);
                item[0].setVelocity(new Vector(0, -0.01, 0));
                item[0].customName(Component.text(ChatColor.translateAlternateColorCodes('&', "&8x&7"+ finalItem[0].getAmount()+ " ")).append(Objects.requireNonNull(finalItem[0].getItemMeta().displayName())));
                item[0].setCustomNameVisible(true);

                location.getWorld().playSound(location, Sound.BLOCK_BEACON_POWER_SELECT, 35, 0);
                //ParticleType.of("PORTAL").spawn(location.getWorld(), location.toCenterLocation().add(0, 2, 0), 10);
            }
        }.runTaskLater(skyblockInstance.getSkyblock(), 15);

        new BukkitRunnable() {
            @Override
            public void run() {
                //ParticleType.of("EXPLOSION_HUGE").spawn(location.getWorld(), location.toCenterLocation().add(0, 2, 0), 1);
                location.getWorld().playSound(location, Sound.ENTITY_GENERIC_EXPLODE, 30, 15);
                item[0].remove();

                if(crate.getOpener() != null && crate.getOpener().isOnline()){
                    Player target = crate.getOpener();
                    HashMap<Integer, ItemStack> toDrop = target.getInventory().addItem(crate.getFinalItem());

                    String message = SkyblockInstance.PREFIX + "Vous avez reçu &8x&4"+ crate.getFinalItem().getAmount() + " " + LegacyComponentSerializer.legacySection().serialize(Objects.requireNonNull(crate.getFinalItem().getItemMeta().displayName())) +" &fdans la box " + crate.getId();
                    target.sendMessage(skyblockInstance.getManagerLoader().getMessageManager().getColorizedMessage(message));

                    if(!toDrop.isEmpty()){
                        target.sendMessage(SkyblockInstance.PREFIX + "Des items n'ont pas pu être ajoutés à votre inventaire, ils ont été jetés au sol.");
                        toDrop.forEach((i, it) -> {
                            target.getWorld().dropItem(target.getLocation(), it);
                        });
                    }

                }
                crate.setOpener(null);

            }
        }.runTaskLater(skyblockInstance.getSkyblock(), 60);

    }

    public Item itemFountain(Location loc, Crate crate) {
        ItemStack launchItem = crate.getRandomLoot().getItem();
        if(launchItem == null) return null;

        Item item = (Item) loc.getWorld().spawnEntity(loc.toCenterLocation().add(0, 0.5, 0), EntityType.DROPPED_ITEM);
        item.setCanPlayerPickup(false);
        item.setItemStack(launchItem);
        item.setCanMobPickup(false);
        item.setWillAge(false);
        item.getItemStack().setAmount(1);
        item.setVelocity(new Vector(0, 0.3, 0));

        loc.getWorld().playSound(loc, Sound.BLOCK_DISPENSER_DISPENSE, 30, 1);

        return item;
    }

    public ItemStack getLootItemStack(ConfigurationSection section) {
        ItemBuilder builder = new ItemBuilder(Material.STONE);
        builder.type(Material.getMaterial(Objects.requireNonNull(section.getString("material"))));
        builder.amount(section.getInt("amount"));
        builder.name(ChatColor.translateAlternateColorCodes('&', this.skyblockInstance.getAngelSkyApiInstance().translateChatColorHex(section.getString("display_name"))));
        List<String> lore = new ArrayList<>();
        Objects.requireNonNull(section.getList("lore")).forEach(t -> lore.add(ChatColor.translateAlternateColorCodes('&', this.skyblockInstance.getAngelSkyApiInstance().translateChatColorHex(t.toString()))));
        builder.lore(lore);
        Objects.requireNonNull(section.getList("enchantments")).forEach(e -> {
            String[] raw = e.toString().split(":");
            Enchantment ench = EnchantmentWrapper.getByKey(NamespacedKey.minecraft(raw[0].toLowerCase()));
            builder.enchant(ench, Integer.parseInt(raw[1]));
        });
        Objects.requireNonNull(section.getList("flags")).forEach(e -> {
            ItemFlag flag = ItemFlag.valueOf(e.toString());
            builder.flags(flag);
        });
        return builder.build();
    }

    public List<String> getLootCommands(ConfigurationSection section) {
        List<String> cmds = new ArrayList<>();
        Objects.requireNonNull(section.getList("commands")).forEach(e -> cmds.add(e.toString()));
        return cmds;
    }

    public Crate getCrateFromOraxenId(String oraxenId) {
        return this.crates.stream().filter(crate -> crate.getCrateId().equals(oraxenId)).findFirst().orElse(null);
    }

    public ArrayList<Crate> getCrates() {
        return this.crates;
    }

    public void openCrate(Crate crate, Player player, Location location) {
        location.add(0.0D, 0.5D, 0.0D).toCenterLocation();
        if (this.openingCrates.containsKey(location)){
            player.sendMessage(skyblockInstance.getManagerLoader().getMessageManager().getColorizedMessage(SkyblockInstance.PREFIX+ "&cCette caisse est déja en train d'être ouverte."));
            return;
        }
        new ItemManager().removeItems(player, player.getInventory(), player.getInventory().getItemInMainHand(), 1);
        crate.addOpenTime(location, CRATE_OPEN_TIME);
        this.fountainAs.put(location, new HashMap<>());
        crate.setOpener(player);
        this.openingCrates.put(location, crate);
    }

    public void openLootMenu(Crate crate, Player player){
        FastInv inv = new FastInv(3*9, ChatColor.GRAY+ "Loots de Crate");

        for(CrateLoot crateLoot : crate.getRawLoots()){
            inv.addItem(crateLoot.getItem(), e->{
                e.setCancelled(true);
            });
        }

        player.openInventory(inv.getInventory());
    }
}