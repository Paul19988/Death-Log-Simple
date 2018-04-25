package uk.co.paulcodes.dls;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

/**
 * Created by paulb on 25/04/2018.
 */
public class DeathListener implements Listener {

    @EventHandler
    private void onDeath(PlayerDeathEvent e) {
        Player p = e.getEntity();
        Player k = e.getEntity().getKiller();
        if(Core.logg) {
            Core.logDeath(p, k);
        }
    }

}
