package de.wiese_christoph.tnx.manager;

import java.text.DecimalFormat;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import de.wiese_christoph.tnx.Main;
import net.md_5.bungee.api.ChatColor;

public class PollManager {
	
	public static boolean pollActive = false;
	public static HashMap<Player, Integer> votes = new HashMap<Player, Integer>();
	public static Player pollStarter = null;
	
	
	public static void startPoll(Player sender, float timeMinutes, String msg) {
		// set poll to active and add the starting player
		pollActive = true;
		pollStarter = (Player)sender;
		
		// doesn't make sense, but i like it
		float voteTime = timeMinutes;
		
		// create bossbar
		BossBroadcastManager.broadcast(msg, (int)(1200*voteTime));
		
		// play sound for everyone
		for (Player player : Bukkit.getOnlinePlayers())
		{
		      player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 2.0f, 0.25f);
		}
		
		// update action bar every second
		new BukkitRunnable() {
			// convert to ticks
			int timeLeft = Math.round(1200*voteTime);
			@Override
			public void run() {	
				// format to readable time format
				int totalSeconds = timeLeft / 20;
				int seconds = totalSeconds % 60;
				int minutes = totalSeconds / 60;
				String time = minutes + ":" + seconds;
				
				// update action bar for every player
				for(Player p : Bukkit.getOnlinePlayers()) {
					p.spigot().sendMessage(net.md_5.bungee.api.ChatMessageType.ACTION_BAR, net.md_5.bungee.api.chat.TextComponent.fromLegacyText("§6Dauer der Volksabstimmung: §2" + time));
				}
				
				// between 5 and 1 seconds, play a sound
				if(timeLeft <= 100 && timeLeft > 0) {
					for (Player player : Bukkit.getOnlinePlayers())
					{
					      player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 3.0f, 0.5f);
					}
				}
				
				// if timer end, end the poll
				if(timeLeft <= 0) {
					pollEnd();
					this.cancel();
				}
				
				timeLeft -= 20;
			}
		}.runTaskTimerAsynchronously(Main.getInstance(), 0, 20);
	}

	
	private static void pollEnd() {
		// reset vars
		pollActive = false;
		pollStarter = null;
		
		// remove action bar message
		for(Player p : Bukkit.getOnlinePlayers()) {
			p.spigot().sendMessage(net.md_5.bungee.api.ChatMessageType.ACTION_BAR, net.md_5.bungee.api.chat.TextComponent.fromLegacyText(""));
		}
		
		// play poll finish sound
		for (Player player : Bukkit.getOnlinePlayers())
		{
		      player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 3.0f, 0.890899f);
		}
		
		// if vote list is empty
		if(votes.size() < 1) {
			Bukkit.broadcastMessage(Main.Name + ChatColor.GOLD + "Junge keiner hat abgestimmt...");
			votes.clear();
			return;
		}
		
		// init vars for count
		int dafür = 0;
		int dagegen = 0;
		int enthalten = 0;
		
		// count the votes
		for(int vote : votes.values()) {
			if(vote == 1) dafür++;
			if(vote == 2) dagegen++;
			if(vote == 0) enthalten++;
		}
		
		// format to percent
		DecimalFormat df = new DecimalFormat("##.##%");
		String dafürP = df.format((double)dafür/votes.size());
		String dagegenP = df.format((double)dagegen/votes.size());
		String enthaltenP = df.format((double)enthalten/votes.size());
		
		// broadcast the vote results
		Bukkit.broadcastMessage(Main.Name + "§6Volksabstimmung ist beendet! Insgesamt: " + votes.size() + " Stimmen!");
		Bukkit.broadcastMessage(Main.Name + "§aDafür: " + dafür + " (" + dafürP +
				") §cDagegen: " + dagegen + " (" + dagegenP + 
				") §8Enthalten: " + enthalten + " (" + enthaltenP +
				")");
		
		// clear the vote list
		votes.clear();
	}
	
}
