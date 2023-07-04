package de.wiese_christoph.tnxutils.managers;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import de.wiese_christoph.tnxutils.TnxUtils;

public class BossBroadcastManager {
	private TnxUtils plugin;

	public BossBroadcastManager(TnxUtils plugin) {
		this.plugin = plugin;
	}
	
	public void broadcast(String msg, int seconds) {
		if(msg == null) return;
		
		// Create bar and set start percentage
		BossBar bar = Bukkit.createBossBar(ChatColor.GOLD + "" + ChatColor.BOLD + msg, BarColor.RED, BarStyle.SOLID);
		bar.setProgress(1.0);
		
		// Add each player to the bar
		for(Player p : Bukkit.getOnlinePlayers()) {
			bar.addPlayer(p);
		}
		
		// Update the bar every second
		new BukkitRunnable() {
			int i = seconds;
			
			@Override
			public void run() {
				if(i <= 0) {
					bar.removeAll();
					this.cancel();
				} else {
					bar.setProgress((double) i /seconds);
					i -= 1;
				}
			}
		}.runTaskTimerAsynchronously(plugin, 0, 20);
	}
}
