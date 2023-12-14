package fr.angelsky.skyblock.listeners.player.level;

import fr.angelsky.skyblock.SkyblockInstance;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class PlayerXpEarningListener implements Listener {

    private final SkyblockInstance skyblockInstance;

    public PlayerXpEarningListener(SkyblockInstance skyblockInstance){
        this.skyblockInstance = skyblockInstance;
    }

    @EventHandler
    public void PlayerKillMobExperienceEvent(EntityDamageByEntityEvent event){
        if (!(event.getDamager() instanceof Player player)) return;
        if (!(event.getEntity() instanceof Damageable)) return;
        Entity damaged = event.getEntity();
        if (event.getFinalDamage() < ((Damageable) damaged).getHealth()) return;
        skyblockInstance.getManagerLoader().getPlayerExperienceManager().processEntityExperience(player, damaged.getType(), 0 , 0);
    }

}
