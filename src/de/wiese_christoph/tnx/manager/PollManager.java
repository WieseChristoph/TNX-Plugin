package de.wiese_christoph.tnx.manager;

import java.text.DecimalFormat;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import de.wiese_christoph.tnx.Main;
import net.md_5.bungee.api.ChatColor;

public class PollManager {
	
	public static boolean pollActive = false;
	public static HashMap<Player, Integer> votes = new HashMap<Player, Integer>();
	public static Player pollStarter = null;
	
	
	public static void startPoll(Player sender, float timeMinutes, String msg) {
		pollActive = true;
		pollStarter = (Player)sender;
		
		float voteTime = timeMinutes;
		
		BossBroadcastManager.broadcast(msg, (int)(1200*voteTime));
		
		new BukkitRunnable() {
			int timeLeft = Math.round(1200*voteTime);
			@Override
			public void run() {						
				int totalSeconds = timeLeft / 20;
				int seconds = totalSeconds % 60;
				int minutes = totalSeconds / 60;
				String time = minutes + ":" + seconds;
				
				for(Player p : Bukkit.getOnlinePlayers()) {
					p.spigot().sendMessage(net.md_5.bungee.api.ChatMessageType.ACTION_BAR, net.md_5.bungee.api.chat.TextComponent.fromLegacyText("§6Dauer der Volksabstimmung: §2" + time));
				}
				
				
				if(timeLeft <= 0) {
					pollEnd();
					this.cancel();
				}
				
				timeLeft -= 20;
			}
		}.runTaskTimerAsynchronously(Main.getInstance(), 0, 20);
	}

	
	private static void pollEnd() {
		pollActive = false;
		pollStarter = null;
		
		for(Player p : Bukkit.getOnlinePlayers()) {
			p.spigot().sendMessage(net.md_5.bungee.api.ChatMessageType.ACTION_BAR, net.md_5.bungee.api.chat.TextComponent.fromLegacyText(""));
		}
		
		if(votes.size() < 1) {
			Bukkit.broadcastMessage(Main.Name + ChatColor.GOLD + "Junge keiner hat abgestimmt...");
			votes.clear();
			return;
		}
		
		int dafür = 0;
		int dagegen = 0;
		int enthalten = 0;
		
		for(int vote : votes.values()) {
			if(vote == 1) dafür++;
			if(vote == 2) dagegen++;
			if(vote == 0) enthalten++;
		}
		
		DecimalFormat df = new DecimalFormat("##.##%");
		String dafürP = df.format((double)dafür/votes.size());
		String dagegenP = df.format((double)dagegen/votes.size());
		String enthaltenP = df.format((double)enthalten/votes.size());
		
		Bukkit.broadcastMessage(Main.Name + "§6Volksabstimmung ist beendet! Insgesamt: " + votes.size() + " Stimmen!");
		Bukkit.broadcastMessage(Main.Name + "§aDafür: " + dafür + " (" + dafürP + 
				") §cDagegen: " + dagegen + " (" + dagegenP + 
				") §8Enthalten: " + enthalten + " (" + enthaltenP +
				")");
		
		votes.clear();
	}
	
}
