package de.wiese_christoph.tnxutils.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import de.wiese_christoph.tnxutils.TnxUtils;
import de.wiese_christoph.tnxutils.managers.TimeWeatherVoteManager;
import net.md_5.bungee.api.ChatColor;

public class TimeWeatherVoteCmd implements CommandExecutor, Listener {
	TnxUtils plugin;
	TimeWeatherVoteManager timeWeatherVoteManager;
	
	public TimeWeatherVoteCmd(TnxUtils plugin, TimeWeatherVoteManager timeWeatherVoteManager) {
		this.plugin = plugin;
		this.timeWeatherVoteManager = timeWeatherVoteManager;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length != 1) return false;

		if (cmd.getName().equalsIgnoreCase("vote")) {
			if (!plugin.getConfig().getBoolean("TimeWeatherVote.enabled")) {
				sender.sendMessage(TnxUtils.Name + ChatColor.DARK_RED + "Time/Weather Vote is disabled!");
				return true;
			}

			if (!(sender instanceof Player p)) return false;

			switch (args[0].toLowerCase()) {
				case "day" -> timeWeatherVoteManager.vote(p, TimeWeatherVoteManager.Vote.DAY);
				case "night" -> timeWeatherVoteManager.vote(p, TimeWeatherVoteManager.Vote.NIGHT);
				case "clear" -> timeWeatherVoteManager.vote(p, TimeWeatherVoteManager.Vote.CLEAR);
				case "rain" -> timeWeatherVoteManager.vote(p, TimeWeatherVoteManager.Vote.RAIN);
				default -> {
					return false;
				}
			}
		} else if (cmd.getName().equalsIgnoreCase("twv")) {
			boolean enable = Boolean.parseBoolean(args[0]);
			// Edit config to enable/disable time vote
			plugin.getConfig().set("TimeWeatherVote.enabled", enable);
			plugin.saveConfig();
			plugin.reloadConfig();
			sender.sendMessage(TnxUtils.Name + ChatColor.DARK_GREEN + "Time/Weather Vote " + (enable ? "enabled" : "disabled") + "!");
		}

		return true;
	}
}