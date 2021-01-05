package fr.skyfighttv.autologger.Listeners;

import fr.skyfighttv.autologger.Main;
import fr.skyfighttv.autologger.Utils.FileManager;
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

        if (Main.getInstance().webhookClients.containsKey("PlayerChatTabComplete"))
            Main.getInstance().webhookClients.get("PlayerChatTabComplete").send(text);
    }
}
