package de.wiese_christoph.tnxutils.commands;

import de.wiese_christoph.tnxutils.TnxUtils;
import de.wiese_christoph.tnxutils.managers.PollManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class PollCmd implements CommandExecutor, Listener {
	TnxUtils plugin;
	PollManager pollManager;

	public PollCmd(TnxUtils plugin, PollManager pollManager) {
		this.plugin = plugin;
		this.pollManager = pollManager;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!cmd.getName().equalsIgnoreCase("poll")) return true;
		if (args.length < 2) return false;

		int seconds = Integer.parseInt(args[0]);

		StringBuilder msg = new StringBuilder(args[1]);
		for (int i = 2; i < args.length; i++) {
			msg.append(" ").append(args[i]);
		}

		pollManager.startPoll((Player) sender, seconds, msg.toString());

		return true;
	}
}



