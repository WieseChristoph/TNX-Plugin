package de.wiese_christoph.tnxutils.commands;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import de.wiese_christoph.tnxutils.TnxUtils;
import de.wiese_christoph.tnxutils.managers.RocketManager;

public class RocketCmd implements CommandExecutor {
	TnxUtils plugin;
	RocketManager rocketManager;
	
	public RocketCmd(TnxUtils plugin, RocketManager rocketManager) {
		this.plugin = plugin;
		this.rocketManager = rocketManager;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String Label, String[] args) {
		if (!cmd.getName().equalsIgnoreCase("rocket")) return true;

		if(args.length == 0 && sender instanceof Player) {
			rocketManager.start((Player)sender, 120);
			return true;
		} else if(args.length == 1) {
			Player targetPlayer = Bukkit.getPlayer(args[0]);
			if (targetPlayer == null) {
				sender.sendMessage(TnxUtils.Name + ChatColor.DARK_RED + "Player not found!");
				return true;
			}
			rocketManager.start(targetPlayer, 120);
		} else return false;

		return true;
	}
}
