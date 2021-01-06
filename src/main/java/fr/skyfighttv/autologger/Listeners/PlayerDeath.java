package fr.skyfighttv.autologger.Listeners;

import fr.skyfighttv.autologger.Main;
import fr.skyfighttv.autologger.Utils.FileManager;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PlayerDeath implements Listener {
    @EventHandler
    private void onPlayerDeath(PlayerDeathEvent event) {
        String text = FileManager.getValues().get("config").getString("PlayerDeath.Message");
        assert text != null;

        StringBuilder lootString = new StringBuilder();

        if (text.contains("%stuff%")) {
            for (ItemStack itemStack : event.getDrops()) {
                if (itemStack == null) continue;

                lootString.append(itemStack.getAmount()).append("x ").append(itemStack.getType());

                if (itemStack.hasItemMeta()
                        && itemStack.getItemMeta().hasEnchants()) {
                    lootString.append("(");
                    List<Enchantment> enchantments = new ArrayList<>(itemStack.getItemMeta().getEnchants().keySet());

                    for (int i = 0; i < enchantments.size(); i++) {
                        if (i >= enchantments.size() - 1)
                            lootString.append(enchantments.get(i).getName()).append("[").append(itemStack.getEnchantments().get(enchantments.get(i))).append("]");
                        else
                            lootString.append(enchantments.get(i).getName()).append("[").append(itemStack.getEnchantments().get(enchantments.get(i))).append("], ");
                    }
                    lootString.append(")");
                }
                lootString.append(", ");
            }
            lootString.deleteCharAt(lootString.lastIndexOf(", "));
        }

        text = text.replaceAll("%date%", new SimpleDateFormat("HH:mm:ss").format(System.currentTimeMillis()))
                .replaceAll("%playername%", event.getEntity().getName())
                .replaceAll("%worldname%", event.getEntity().getWorld().getName())
                .replaceAll("%stuff%", lootString.toString())
                .replaceAll("%x%", event.getEntity().getLocation().getBlockX() + "")
                .replaceAll("%y%", event.getEntity().getLocation().getBlockY() + "")
                .replaceAll("%z%", event.getEntity().getLocation().getBlockZ() + "")
                .replaceAll("%message%", Objects.requireNonNull(event.getDeathMessage()));

        FileManager.writeInFile(FileManager.getFiles().get("PlayerDeath"), text);

        Main.sendDebug("PlayerDeath event was called");

        if (Main.getInstance().webhookClients.containsKey("PlayerDeath"))
            Main.getInstance().webhookClients.get("PlayerDeath").send(text);
    }
}
