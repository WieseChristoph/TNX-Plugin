package de.wiese_christoph.tnx.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import de.wiese_christoph.tnx.Main;
import net.md_5.bungee.api.ChatColor;

public class SleepCmd implements CommandExecutor{
	
	Main plugin;
	
	public SleepCmd(Main plugin) {
		this.plugin = Main.getInstance();
	}
	
	
	// Edit Config
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String Label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("bp") && sender.hasPermission("tnx.bp")) {
			if(args.length == 1) {
				if(args[0].equalsIgnoreCase("true")) {
					plugin.getConfig().set("BedPercentage.enabled", true);
					plugin.saveConfig();
					plugin.reloadConfig();
					sender.sendMessage(Main.Name + ChatColor.DARK_GREEN + "Bed Percentage night skip enabled!");
					
					return true;
				}else if(args[0].equalsIgnoreCase("false")) {
					plugin.getConfig().set("BedPercentage.enabled", false);
					plugin.saveConfig();
					plugin.reloadConfig();
					sender.sendMessage(Main.Name + ChatColor.DARK_GREEN + "Bed Percentage night skip disabled!");
					
					return true;
				}else {
					return false;
				}
			}else {
				return false;
			}
		}else {
			sender.sendMessage("&4No Permissions!");
			return false;
		}
		}
}
