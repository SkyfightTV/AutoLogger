package fr.skyfighttv.autologger.Listeners;

import fr.skyfighttv.autologger.Main;
import fr.skyfighttv.autologger.Utils.FileManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.text.SimpleDateFormat;

public class BlockBreak implements Listener {
    @EventHandler
    private void onBreak(BlockBreakEvent event) {
        String text = FileManager.getValues().get("config").getString("BlockBreak.Message")
                .replaceAll("%date%", new SimpleDateFormat("HH:mm:ss").format(System.currentTimeMillis()))
                .replaceAll("%playername%", event.getPlayer().getName())
                .replaceAll("%blockname%", event.getBlock().getType().name())
                .replaceAll("%worldname%", event.getBlock().getWorld().getName())
                .replaceAll("%x%", event.getBlock().getLocation().getBlockX() + "")
                .replaceAll("%y%", event.getBlock().getLocation().getBlockY() + "")
                .replaceAll("%z%", event.getBlock().getLocation().getBlockZ() + "")
                .replaceAll("%itemname%", event.getPlayer().getInventory().getItemInMainHand().getType().name());

        FileManager.writeInFile(FileManager.getFiles().get("BlockBreak"), text);

        Main.sendDebug("BlockBreak event was called");
    }
}
