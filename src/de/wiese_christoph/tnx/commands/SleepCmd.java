// 
// Decompiled by Procyon v0.5.36
// 

package de.wiese_christoph.tnx.commands;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import de.wiese_christoph.tnx.Main;
import org.bukkit.command.CommandExecutor;

public class SleepCmd implements CommandExecutor
{
    Main plugin;
    
    public SleepCmd(final Main plugin) {
        this.plugin = Main.getInstance();
    }
    
    public boolean onCommand(final CommandSender sender, final Command cmd, final String Label, final String[] args) {
        if (!cmd.getName().equalsIgnoreCase("bp") || !sender.hasPermission("tnx.bp")) {
            sender.sendMessage("&4No Permissions!");
            return false;
        }
        if (args.length != 1) {
            return false;
        }
        if (args[0].equalsIgnoreCase("true")) {
            this.plugin.getConfig().set("BedPercentage.enabled", (Object)true);
            this.plugin.saveConfig();
            this.plugin.reloadConfig();
            sender.sendMessage(String.valueOf(Main.Name) + ChatColor.DARK_GREEN + "Bed Percentage night skip enabled!");
            return true;
        }
        if (args[0].equalsIgnoreCase("false")) {
            this.plugin.getConfig().set("BedPercentage.enabled", (Object)false);
            this.plugin.saveConfig();
            this.plugin.reloadConfig();
            sender.sendMessage(String.valueOf(Main.Name) + ChatColor.DARK_GREEN + "Bed Percentage night skip disabled!");
            return true;
        }
        return false;
    }
}
