package de.wiese_christoph.tnx.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import de.wiese_christoph.tnx.Main;
import de.wiese_christoph.tnx.manager.PollManager;
import net.md_5.bungee.api.ChatColor;

public class PollCmd implements CommandExecutor, Listener{

	Main plugin;
	
	public PollCmd(Main plugin) {
		this.plugin = Main.getInstance();
	}
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("volksabstimmung")) {
			if(sender.hasPermission("tnx.poll")) {
				if(!PollManager.pollActive) {
					if(args[0] != null) {
						if(args[1] != null) {
							String msg = "";
							for(int i = 1; i < args.length; i++) {
								msg = msg + " " + args[i];
							}
							PollManager.startPoll((Player)sender, Float.parseFloat(args[0]), msg);
							
							return true;			
						}else {
							return false;
						}		
					}else {
						return false;
					}
				}else {
					sender.sendMessage(Main.Name + ChatColor.DARK_RED + "Es läuft bereits eine Abstimmung!");
					return true;
				}
			}else {
				sender.sendMessage(Main.Name + ChatColor.DARK_RED + "Du hast kein Recht dazu!");
				return true;
			}
		}
		return true;
	}

}



