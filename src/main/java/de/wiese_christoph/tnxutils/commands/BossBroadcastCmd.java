package de.wiese_christoph.tnxutils.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import de.wiese_christoph.tnxutils.TnxUtils;
import de.wiese_christoph.tnxutils.managers.BossBroadcastManager;

public class BossBroadcastCmd implements CommandExecutor {
	TnxUtils plugin;
	BossBroadcastManager bossBroadcastManager;

	public BossBroadcastCmd(TnxUtils plugin, BossBroadcastManager bossBroadcastManager) {
		this.plugin = plugin;
		this.bossBroadcastManager = bossBroadcastManager;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String Label, String[] args) {
		if (!cmd.getName().equalsIgnoreCase("bb")) return true;
		if (args.length < 2) return false;

		int seconds = Integer.parseInt(args[0]);

		StringBuilder msg = new StringBuilder(args[1]);
		for(int i = 2; i < args.length; i++) {
			msg.append(" ").append(args[i]);
		}

		bossBroadcastManager.broadcast(msg.toString(), seconds);

		return true;
	}
}
