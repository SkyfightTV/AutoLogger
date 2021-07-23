package fr.skyfighttv.autologger.listeners;

import fr.skyfighttv.autologger.Main;
import fr.skyfighttv.autologger.utils.FileManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatTabCompleteEvent;

import java.text.SimpleDateFormat;

public class PlayerChatTabComplete implements Listener {
    @EventHandler
    private void onPlayerChatTabComplete(PlayerChatTabCompleteEvent event) {
        String text = FileManager.getValues().get("config").getString("PlayerChatTabComplete.Message")
                .replaceAll("%date%", new SimpleDateFormat("HH:mm:ss").format(System.currentTimeMillis()))
                .replaceAll("%playername%", event.getPlayer().getName())
                .replaceAll("%worldname%", event.getPlayer().getWorld().getName())
                .replaceAll("%message%", event.getChatMessage());

        FileManager.writeInFile(FileManager.getFiles().get("PlayerChatTabComplete"), text);

        Main.sendDebug("PlayerChatTabComplete event was called");
        Main.sendLogs("PlayerChatTabComplete event was called", event.getPlayer());
        Main.sendWebHook(event.getEventName().toLowerCase(), text);
    }
}
