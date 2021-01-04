package fr.skyfighttv.autologger.Listeners;

import fr.skyfighttv.autologger.Utils.FileManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerMove implements Listener {
    @EventHandler
    private void onMove(PlayerMove event) {
        String text = "(" + event.getCause().name() + ") " + event.getFrom().getX() + " " + event.getFrom().getY() + " " + event.getFrom().getZ() + " -> " + event.getTo().getX() + " " + event.getTo().getY() + " " + event.getTo().getZ();

        FileManager.writeInFile(FileManager.teleportFile, "default", text, event.getPlayer().getName());
    }
}
