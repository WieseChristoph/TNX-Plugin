package de.wiese_christoph.tnx.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import de.wiese_christoph.tnx.Main;
import de.wiese_christoph.tnx.manager.VoteManager;
import net.md_5.bungee.api.ChatColor;

public class VoteCmd implements CommandExecutor, Listener{
	
	Main plugin;
	
	public VoteCmd(Main plugin) {
		this.plugin = Main.getInstance();
	} 
	
	
	//command executor
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(cmd.getName().equalsIgnoreCase("vote")) {
			
			if(sender instanceof Player) {
				Player p = (Player)sender;
				if(p.hasPermission("tnx.vote")) {
					if(args.length > 0) {
						
						//Time
						//Day
						if(args[0].equalsIgnoreCase("day") && plugin.getConfig().getBoolean("TimeVote.enabled")) {
							
							return VoteManager.voteDay(p);
							
						//Night
						}else if (args[0].equalsIgnoreCase("night") && plugin.getConfig().getBoolean("TimeVote.enabled")) {
							
							return VoteManager.voteNight(p);	
							
						//Weather
						//Clear
						}else if (args[0].equalsIgnoreCase("clear")) {
							
							return VoteManager.voteClear(p);
							
						//Rain with 2 min cooldown
						}else if (args[0].equalsIgnoreCase("rain")) {
							
							return VoteManager.voteRain(p);
							
						}else {
							p.sendMessage(Main.Name + "§4Nope.");
							return false;
						}
					}else {
						p.sendMessage(Main.Name + "§4Ja was vote man?!");
						return false;
					}
				}else {
					p.sendMessage(Main.Name + "§4No Permissions!");
					return false;
				}
			}

		}
		

		// Edit Config
		else if(cmd.getName().equalsIgnoreCase("tv") && sender.hasPermission("tnx.tv")) {
			if(args.length == 1) {
				if(args[0].equalsIgnoreCase("true")) {
					plugin.getConfig().set("TimeVote.enabled", true);
					plugin.saveConfig();
					plugin.reloadConfig();
					sender.sendMessage(Main.Name + ChatColor.DARK_GREEN + "Time Vote enabled!");
					
					return true;
				}else if(args[0].equalsIgnoreCase("false")) {
					plugin.getConfig().set("TimeVote.enabled", false);
					plugin.saveConfig();
					plugin.reloadConfig();
					sender.sendMessage(Main.Name + ChatColor.DARK_GREEN + "Time Vote disabled!");
					
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
		
		
		return false;
	}
	
}