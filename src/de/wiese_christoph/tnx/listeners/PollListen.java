package de.wiese_christoph.tnx.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import de.wiese_christoph.tnx.Main;
import de.wiese_christoph.tnx.manager.PollManager;
import net.md_5.bungee.api.ChatColor;

public class PollListen implements Listener{
	
	Main plugin;

	public PollListen(Main main) {
		this.plugin = Main.getInstance();
	}
	
	
	@EventHandler
	public void AsyncChatEvent(AsyncPlayerChatEvent e) {
		if(!PollManager.pollActive) return;
		if(plugin.getConfig().getBoolean("Poll.excludePollStarter") && e.getPlayer() == PollManager.pollStarter) return;
		
		if (e.getMessage().equalsIgnoreCase("1")) {
			if(PollManager.votes.containsKey(e.getPlayer())) {
				e.getPlayer().sendMessage(Main.Name + ChatColor.DARK_RED + "Du hast schon abgestimmt!");
				e.setCancelled(true);
				return;
			}
			PollManager.votes.put(e.getPlayer(), 1);
			e.getPlayer().sendMessage(Main.Name + ChatColor.DARK_GREEN + "Erfolgreich abgestimmt!");
			e.setCancelled(true);
		}else if(e.getMessage().equalsIgnoreCase("2")) {
			if(PollManager.votes.containsKey(e.getPlayer())) {
				e.getPlayer().sendMessage(Main.Name + ChatColor.DARK_RED + "Du hast schon abgestimmt!");
				e.setCancelled(true);
				return;
			}
			PollManager.votes.put(e.getPlayer(), 2);
			e.getPlayer().sendMessage(Main.Name + ChatColor.DARK_GREEN + "Erfolgreich abgestimmt!");
			e.setCancelled(true);
		}else if(e.getMessage().equalsIgnoreCase("0")) {
			if(PollManager.votes.containsKey(e.getPlayer())) {
				e.getPlayer().sendMessage(Main.Name + ChatColor.DARK_RED + "Du hast schon abgestimmt!");
				e.setCancelled(true);
				return;
			}
			PollManager.votes.put(e.getPlayer(), 0);
			e.getPlayer().sendMessage(Main.Name + ChatColor.DARK_GREEN + "Erfolgreich abgestimmt!");
			e.setCancelled(true);
		}
	}
	
}
