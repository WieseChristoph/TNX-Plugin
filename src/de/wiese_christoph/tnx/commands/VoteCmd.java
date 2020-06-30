// 
// Decompiled by Procyon v0.5.36
// 

package de.wiese_christoph.tnx.commands;

import net.md_5.bungee.api.ChatColor;
import de.wiese_christoph.tnx.manager.VoteManager;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import de.wiese_christoph.tnx.Main;
import org.bukkit.event.Listener;
import org.bukkit.command.CommandExecutor;

public class VoteCmd implements CommandExecutor, Listener
{
    Main plugin;
    
    public VoteCmd(final Main plugin) {
        this.plugin = Main.getInstance();
    }
    
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (cmd.getName().equalsIgnoreCase("vote")) {
            if (!(sender instanceof Player)) {
                return false;
            }
            final Player p = (Player)sender;
            if (!p.hasPermission("tnx.vote")) {
                p.sendMessage(String.valueOf(Main.Name) + "§4No Permissions!");
                return false;
            }
            if (args.length <= 0) {
                p.sendMessage(String.valueOf(Main.Name) + "§4Ja was vote man?!");
                return false;
            }
            if (args[0].equalsIgnoreCase("day") && this.plugin.getConfig().getBoolean("TimeVote.enabled")) {
                return VoteManager.voteDay(p);
            }
            if (args[0].equalsIgnoreCase("night") && this.plugin.getConfig().getBoolean("TimeVote.enabled")) {
                return VoteManager.voteNight(p);
            }
            if (args[0].equalsIgnoreCase("clear")) {
                return VoteManager.voteClear(p);
            }
            if (args[0].equalsIgnoreCase("rain")) {
                return VoteManager.voteRain(p);
            }
            p.sendMessage(String.valueOf(Main.Name) + "§4Nope.");
            return false;
        }
        else {
            if (!cmd.getName().equalsIgnoreCase("tv") || !sender.hasPermission("tnx.tv")) {
                sender.sendMessage("&4No Permissions!");
                return false;
            }
            if (args.length != 1) {
                return false;
            }
            if (args[0].equalsIgnoreCase("true")) {
                this.plugin.getConfig().set("TimeVote.enabled", (Object)true);
                this.plugin.saveConfig();
                this.plugin.reloadConfig();
                sender.sendMessage(String.valueOf(Main.Name) + ChatColor.DARK_GREEN + "Time Vote enabled!");
                return true;
            }
            if (args[0].equalsIgnoreCase("false")) {
                this.plugin.getConfig().set("TimeVote.enabled", (Object)false);
                this.plugin.saveConfig();
                this.plugin.reloadConfig();
                sender.sendMessage(String.valueOf(Main.Name) + ChatColor.DARK_GREEN + "Time Vote disabled!");
                return true;
            }
            return false;
        }
    }
}
