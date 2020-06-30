// 
// Decompiled by Procyon v0.5.36
// 

package de.wiese_christoph.tnx.commands;

import de.wiese_christoph.tnx.manager.PollManager;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import de.wiese_christoph.tnx.Main;
import org.bukkit.event.Listener;
import org.bukkit.command.CommandExecutor;

public class PollCmd implements CommandExecutor, Listener
{
    Main plugin;
    
    public PollCmd(final Main plugin) {
        this.plugin = Main.getInstance();
    }
    
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (!cmd.getName().equalsIgnoreCase("volksabstimmung")) {
            return true;
        }
        if (!sender.hasPermission("tnx.poll") || !(sender instanceof Player)) {
            sender.sendMessage(String.valueOf(Main.Name) + ChatColor.DARK_RED + "Du hast kein Recht dazu!");
            return true;
        }
        if (PollManager.pollActive) {
            sender.sendMessage(String.valueOf(Main.Name) + ChatColor.DARK_RED + "Es l\u00e4uft bereits eine Abstimmung!");
            return true;
        }
        if (args.length >= 2) {
            String msg = "";
            for (int i = 1; i < args.length; ++i) {
                if (i == 1) {
                    msg = String.valueOf(msg) + args[i];
                }
                else {
                    msg = String.valueOf(msg) + " " + args[i];
                }
            }
            PollManager.startPoll((Player)sender, Float.parseFloat(args[0]), msg);
            return true;
        }
        return false;
    }
}
