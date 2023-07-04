package de.wiese_christoph.tnxutils.commands;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import de.wiese_christoph.tnxutils.managers.DeathManager;
import org.bukkit.entity.Player;
import de.wiese_christoph.tnxutils.TnxUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;

public class DeathInventory implements CommandExecutor {
    TnxUtils plugin;
    DeathManager deathManager;

    public DeathInventory(TnxUtils plugin, DeathManager deathManager) {
        this.plugin = plugin;
        this.deathManager = deathManager;
    }

    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (!cmd.getName().equalsIgnoreCase("di")) return true;
        if (!(sender instanceof Player)) return false;

        if (args.length == 0) {
            deathManager.showInv((Player)sender, (Player)sender);
        } else if (args.length == 1) {
            final Player targetPlayer = Bukkit.getPlayerExact(args[0]);
            if (targetPlayer == null) {
                sender.sendMessage(TnxUtils.Name + ChatColor.DARK_RED + "Player not found!");
                return true;
            }
            deathManager.showInv((Player)sender, targetPlayer);
        } else return false;

        return true;
    }
}
