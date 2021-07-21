package fr.skyfighttv.autologger.commands;

import fr.skyfighttv.autologger.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AutoLoggerManager implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) return true;

        Player p = (Player)sender;

        if(Main.getInstance().loggers.containsKey(p)) {
            Main.getInstance().loggers.remove(p);
            p.sendMessage("§a§lAutoLogger §f§l>> §7Notifications désactivées !");
        } else {
            Main.getInstance().loggers.put(p, args.length == 0 ? "all" : (Bukkit.getPlayer(args[0]) == null ? "all" : Bukkit.getPlayer(args[0]).getName()));
            p.sendMessage("§a§lAutoLogger §f§l>> §7Notifications activées !");
        }
        return true;
    }
}
