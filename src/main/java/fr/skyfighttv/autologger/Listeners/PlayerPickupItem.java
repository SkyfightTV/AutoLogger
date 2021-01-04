package fr.skyfighttv.autologger.Listeners;

import fr.skyfighttv.autologger.Utils.FileManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class PlayerPickupItem implements Listener {
    @EventHandler
    private void onPickup(PlayerPickupItemEvent event) {
        String text = event.getItem().getItemStack().getAmount()
                + "x "
                + event.getItem().getItemStack().getType().name()
                + " in "
                + event.getItem().getLocation().getWorld().getName()
                + " at "
                + (int) event.getItem().getLocation().getX()
                + " "
                + (int) event.getItem().getLocation().getY()
                + " "
                + (int) event.getItem().getLocation().getZ();

        FileManager.writeInFile(FileManager.pickupItemFile, "default", text, event.getPlayer().getName());
    }
}
