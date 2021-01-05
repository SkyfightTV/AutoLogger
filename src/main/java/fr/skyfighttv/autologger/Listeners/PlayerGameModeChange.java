package fr.skyfighttv.autologger.Listeners;

import fr.skyfighttv.autologger.Utils.FileManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;

import java.text.SimpleDateFormat;

public class PlayerGameModeChange implements Listener {
    @EventHandler
    private void onPlayerGameModeChange(PlayerGameModeChangeEvent event) {
        String text = FileManager.getValues().get("config").getString("PlayerGameModeChange.Message")
                .replaceAll("%date%", new SimpleDateFormat("'['HH:mm:ss']'").format(System.currentTimeMillis())
                .replaceAll("%playername%", event.getPlayer().getName())
                .replaceAll("%worldname%", event.getPlayer().getWorld().getName())
                .replaceAll("%previous%", event.getPlayer().getGameMode().name())
                .replaceAll("%new%", event.getNewGameMode().name())
                .replaceAll("%x%", event.getPlayer().getLocation().getBlockX() + "")
                .replaceAll("%y%", event.getPlayer().getLocation().getBlockY() + "")
                .replaceAll("%z%", event.getPlayer().getLocation().getBlockZ() + ""));

        FileManager.writeInFile(FileManager.getFiles().get("PlayerGameModeChange"), text);
    }
}
