package de.wiese_christoph.tnx.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import de.wiese_christoph.tnx.Main;

public class CreeperExplosionCmd implements CommandExecutor {
	
	Main plugin;

	public CreeperExplosionCmd(Main main) {
		this.plugin = Main.getInstance();
	}
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String Label, String[] args) {
		// edit config to enable/disable Creeper Explosion prevention
		if(cmd.getName().equalsIgnoreCase("ce") && sender.hasPermission("tnx.ce")) {
			if(args.length == 1) {
				if(args[0].equalsIgnoreCase("true")) {
					plugin.getConfig().set("CreeperExplosion.enabled", true);
					plugin.saveConfig();
					plugin.reloadConfig();
					sender.sendMessage(Main.Name + ChatColor.DARK_GREEN + "Creeper Explosion Protection enabled!");
					
					return true;
				}else if(args[0].equalsIgnoreCase("false")) {
					plugin.getConfig().set("CreeperExplosion.enabled", false);
					plugin.saveConfig();
					plugin.reloadConfig();
					sender.sendMessage(Main.Name + ChatColor.DARK_GREEN + "Creeper Explosion Protection disabled!");
					
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
