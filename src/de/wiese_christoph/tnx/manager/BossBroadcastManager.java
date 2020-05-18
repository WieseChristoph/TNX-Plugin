package de.wiese_christoph.tnx.manager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import de.wiese_christoph.tnx.Main;

public class BossBroadcastManager {
	
	public static void broadcast(String msg, int timeTicks) {
		BossBar bar = Bukkit.createBossBar(ChatColor.GOLD + "" + ChatColor.BOLD + msg, BarColor.RED, BarStyle.SOLID);
		bar.setProgress(1.0);
		
		for(Player p : Bukkit.getOnlinePlayers()) {
			bar.addPlayer(p);
		}
		
		new BukkitRunnable() {
			int i = timeTicks;
			
			@Override
			public void run() {
				i-=20;
				bar.setProgress((double)i/timeTicks);
				
				if(i <= 0) {
					bar.removeAll();
					cancel();
				}
				
			}
		}.runTaskTimerAsynchronously(Main.getInstance(), 20, 20);
	};
}
