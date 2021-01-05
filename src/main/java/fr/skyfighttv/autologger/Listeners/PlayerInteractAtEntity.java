package fr.skyfighttv.autologger.Listeners;

import fr.skyfighttv.autologger.Main;
import fr.skyfighttv.autologger.Utils.FileManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

import java.text.SimpleDateFormat;
import java.util.Objects;

public class PlayerInteractAtEntity implements Listener {
    @EventHandler
    private void onPlayerInteractAtEntity(PlayerInteractAtEntityEvent event) {
        String text = FileManager.getValues().get("config").getString("PlayerInteractAtEntity.Message")
                .replaceAll("%date%", new SimpleDateFormat("HH:mm:ss").format(System.currentTimeMillis()))
                .replaceAll("%playername%", event.getPlayer().getName())
                .replaceAll("%worldname%", event.getPlayer().getWorld().getName())
                .replaceAll("%entity%", Objects.requireNonNull(event.getRightClicked()).getType().name())
                .replaceAll("%item%", Objects.requireNonNull(event.getPlayer()).getType().name())
                .replaceAll("%x%", event.getPlayer().getLocation().getBlockX() + "")
                .replaceAll("%y%", event.getPlayer().getLocation().getBlockY() + "")
                .replaceAll("%z%", event.getPlayer().getLocation().getBlockZ() + "");

        FileManager.writeInFile(FileManager.getFiles().get("PlayerInteractAtEntity"), text);

        Main.sendDebug("PlayerInteractAtEntity event was called");

        if (Main.getInstance().webhookClients.containsKey("PlayerInteractAtEntity"))
            Main.getInstance().webhookClients.get("PlayerInteractAtEntity").send(text);
    }
}
