package de.wiese_christoph.tnx.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import de.wiese_christoph.tnx.Main;
import de.wiese_christoph.tnx.manager.RocketManager;

public class RocketCmd implements CommandExecutor {
	
	Main plugin;
	
	public RocketCmd(Main plugin) {
		this.plugin = Main.getInstance();
	}
	
	RocketManager rocketMng = new RocketManager();
	

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String Label, String[] args) {
		// get command request and check for Permission and if the sender is a Player
		if(cmd.getName().equalsIgnoreCase("rocket")&&sender.hasPermission("tnx.rocket")&&sender instanceof Player) {
			Player p = (Player) sender;
			
			//if no argument is given, the executing player is chosen
			if(args.length == 0) {
				rocketMng.start(p);
				return true;
				
			//If a player is given, the player is used
			}else if(args.length == 1) {
				Player tp = Bukkit.getServer().getPlayer(args[0]);
				if(tp != null) {	
				rocketMng.start(tp);
				return true;
				
				}else {
					//if the argument isn't a player
					p.sendMessage("§4Argument needs to be a Player");
					return false;
				}
			}else {
				//if there is more than one argument
				p.sendMessage("§4Too many Arguments");
				return false;
			}	
		}else {
			sender.sendMessage("§4Error!");
			return false;
		}
		
	}

}
