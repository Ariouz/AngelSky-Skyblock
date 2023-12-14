package fr.angelsky.skyblock.managers.display.actionbar;

import fr.angelsky.skyblock.SkyblockInstance;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public class ActionBarManager {

    private final SkyblockInstance skyblockInstance;

    private final HashMap<String, BukkitRunnable> previousRunnable = new HashMap<>();

    public ActionBarManager(SkyblockInstance skyblockInstance){
        this.skyblockInstance = skyblockInstance;
    }

    @SuppressWarnings("deprecation")
    public void sendActionBar(Player player, String message, int durationInSeconds){
        new BukkitRunnable() {
            int time = 0;
            @Override
            public void run() {
                if (time > durationInSeconds * 20){
                    this.cancel();
                    previousRunnable.remove(player.getName());
                }

                if (previousRunnable.containsKey(player.getName())){
                    if (previousRunnable.get(player.getName()).getTaskId() != this.getTaskId()){
                        previousRunnable.get(player.getName()).cancel();
                        previousRunnable.remove(player.getName());
                    }else{
                        player.sendActionBar(skyblockInstance.getManagerLoader().getMessageManager().getColorizedMessage(message));
                    }
                }else previousRunnable.put(player.getName(), this);
                time++;
            }
        }.runTaskTimer(skyblockInstance.getSkyblock(), 0, 1);
    }

}
