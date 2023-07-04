package de.wiese_christoph.tnxutils.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import de.wiese_christoph.tnxutils.TnxUtils;
import de.wiese_christoph.tnxutils.managers.PollManager;
import net.md_5.bungee.api.ChatColor;

public class PollListen implements Listener{
	TnxUtils plugin;
	PollManager pollManager;

	public PollListen(TnxUtils plugin, PollManager pollManager) {
		this.plugin = plugin;
		this.pollManager = pollManager;
	}
	
	@EventHandler
	public void AsyncChatEvent(AsyncPlayerChatEvent e) {
		if (!pollManager.pollActive) return;

		// If the config option is set, exclude the poll starter from the poll
		if (plugin.getConfig().getBoolean("Poll.excludePollStarter") && e.getPlayer() == pollManager.pollStarter) return;

		if (!e.getMessage().equalsIgnoreCase("1") && !e.getMessage().equalsIgnoreCase("2")) return;

		int vote = Integer.parseInt(e.getMessage());

		if (pollManager.hasVoted(e.getPlayer())) {
			e.getPlayer().sendMessage(TnxUtils.Name + ChatColor.DARK_RED + "You already voted!");
			e.setCancelled(true);
			return;
		}

		// Add player vote to the list and remove the message
		pollManager.addVote(e.getPlayer(), vote);
		e.getPlayer().sendMessage(TnxUtils.Name + ChatColor.DARK_GREEN + "Successfully voted!");
		e.setCancelled(true);
	}
}
