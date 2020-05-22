package de.wiese_christoph.tnx.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import de.wiese_christoph.tnx.Main;
import de.wiese_christoph.tnx.manager.BossBroadcastManager;

public class BossBroadcastCmd implements CommandExecutor {
	
	Main plugin;

	public BossBroadcastCmd(Main main) {
		this.plugin = Main.getInstance();
	}
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String Label, String[] args) {
		
		if(cmd.getName().equalsIgnoreCase("bb") && sender.hasPermission("tnx.bb")) {
			// check if message was added to command
			if(args.length != 0) {
				// add all args together to a string
				String msg = "";
				for(String a : args) {
					msg = msg + " " + a;
				}
				// create the bossbar
				BossBroadcastManager.broadcast(msg, 1200);
				
				return true;
			}else {
				return false;
			}
		}else {
			return false;
		}
	}

}
