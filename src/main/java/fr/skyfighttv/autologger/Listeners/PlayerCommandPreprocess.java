package fr.skyfighttv.autologger.Listeners;

import fr.skyfighttv.autologger.Main;
import fr.skyfighttv.autologger.Utils.FileManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.text.SimpleDateFormat;

public class PlayerCommandPreprocess implements Listener {
    @EventHandler
    private void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        String text = FileManager.getValues().get("config").getString("PlayerCommandPreprocess.Message")
                .replaceAll("%date%", new SimpleDateFormat("'['HH:mm:ss']'").format(System.currentTimeMillis()))
                .replaceAll("%playername%", event.getPlayer().getName())
                .replaceAll("%worldname%", event.getPlayer().getWorld().getName())
                .replaceAll("%command%", event.getMessage());

        FileManager.writeInFile(FileManager.getFiles().get("PlayerCommandPreprocess"), text);

        Main.sendDebug("PlayerCommandPreprocess event was called");
    }
}
