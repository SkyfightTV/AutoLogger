package fr.skyfighttv.autologger.listeners;

import fr.skyfighttv.autologger.Main;
import fr.skyfighttv.autologger.utils.FileManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import java.text.SimpleDateFormat;
import java.util.Objects;

public class PlayerInteract implements Listener {
    @EventHandler
    private void onPlayerInteract(PlayerInteractEvent event) {
        String text = FileManager.getValues().get("config").getString("PlayerInteract.Message")
                .replaceAll("%date%", new SimpleDateFormat("HH:mm:ss").format(System.currentTimeMillis()))
                .replaceAll("%playername%", event.getPlayer().getName())
                .replaceAll("%worldname%", event.getPlayer().getWorld().getName())
                .replaceAll("%action%", event.getAction().name())
                .replaceAll("%block%", event.getClickedBlock() == null ? "null" : Objects.requireNonNull(event.getClickedBlock()).getType().name())
                .replaceAll("%item%", event.getItem() == null ? "null" : Objects.requireNonNull(event.getItem()).getType().name())
                .replaceAll("%x%", event.getPlayer().getLocation().getBlockX() + "")
                .replaceAll("%y%", event.getPlayer().getLocation().getBlockY() + "")
                .replaceAll("%z%", event.getPlayer().getLocation().getBlockZ() + "");

        FileManager.writeInFile(FileManager.getFiles().get("PlayerInteract"), text);

        Main.sendDebug("PlayerInteract event was called");
        Main.sendLogs("PlayerInteract event was called", event.getPlayer());

        if (Main.getInstance().webhookClients.containsKey("PlayerInteract"))
            Main.getInstance().webhookClients.get("PlayerInteract").send(text);
    }
}
