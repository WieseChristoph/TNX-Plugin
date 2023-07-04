package de.wiese_christoph.tnxutils.commands;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import de.wiese_christoph.tnxutils.TnxUtils;

public class CreeperExplosionProtectionCmd implements CommandExecutor {
	TnxUtils plugin;

	public CreeperExplosionProtectionCmd(TnxUtils plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String Label, String[] args) {
		if (!cmd.getName().equalsIgnoreCase("cep")) return true;
		if (args.length != 1) return false;

		boolean enable = Boolean.parseBoolean(args[0]);
		// Edit config to enable/disable Creeper explosion prevention
		plugin.getConfig().set("CreeperExplosionProtection.enabled", enable);
		plugin.saveConfig();
		plugin.reloadConfig();
		sender.sendMessage(TnxUtils.Name + ChatColor.DARK_GREEN + "Creeper Explosion Protection " + (enable ? "enabled" : "disabled") + "!");

		return true;
	}
}
