package de.wiese_christoph.tnxutils.managers;

import de.wiese_christoph.tnxutils.TnxUtils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.DecimalFormat;
import java.util.HashMap;

public class PollManager {
	private TnxUtils plugin;
	private BossBroadcastManager bossBroadcastManager;
	public boolean pollActive = false;
	private HashMap<Player, Integer> votes = new HashMap<>();
	public Player pollStarter = null;

	public PollManager(TnxUtils plugin, BossBroadcastManager bossBroadcastManager) {
		this.plugin = plugin;
		this.bossBroadcastManager = bossBroadcastManager;
	}
	
	public void startPoll(Player sender, int voteTimeSeconds, String msg) {
		if (pollActive) {
			sender.sendMessage(TnxUtils.Name + ChatColor.DARK_RED + "There is already a poll running!");
			return;
		}

		pollActive = true;
		pollStarter = sender;

		bossBroadcastManager.broadcast(msg, voteTimeSeconds);
		
		// Play sound for everyone
		for (Player player : Bukkit.getOnlinePlayers()) {
	  		player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_LAND, 2.0f, 0.25f);
		}
		
		// Update action bar every second
		new BukkitRunnable() {
			int timeLeft = voteTimeSeconds;

			@Override
			public void run() {
				// On timer end, end the poll
				if(timeLeft <= 0) {
					pollEnd();
					this.cancel();
				} else {
					// Format to readable time format
					int seconds = timeLeft % 60;
					int minutes = timeLeft / 60;
					String time = String.format("%02d:%02d", minutes, seconds);

					// Update action bar for every player
					for (Player p : Bukkit.getOnlinePlayers()) {
						p.spigot().sendMessage(
								net.md_5.bungee.api.ChatMessageType.ACTION_BAR,
								net.md_5.bungee.api.chat.TextComponent.fromLegacyText(ChatColor.GOLD + "Poll ends in " + ChatColor.DARK_GREEN + time)
						);
					}

					// Between 5 and 1 seconds left, play a sound
					if (timeLeft <= 5 && timeLeft > 0) {
						for (Player player : Bukkit.getOnlinePlayers()) {
							player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 3.0f, 0.5f);
						}
					}

					timeLeft -= 1;
				}
			}
		}.runTaskTimerAsynchronously(plugin, 0, 20);
	}

	public boolean hasVoted(Player player) {
		return votes.containsKey(player);
	}

	public void addVote(Player player, int vote) {
		votes.put(player, vote);
	}
	
	private void pollEnd() {
		if (!pollActive) return;

		pollActive = false;
		pollStarter = null;
		
		// Remove action bar message
		for (Player p : Bukkit.getOnlinePlayers()) {
			p.spigot().sendMessage(net.md_5.bungee.api.ChatMessageType.ACTION_BAR, net.md_5.bungee.api.chat.TextComponent.fromLegacyText(""));
		}
		
		// Play poll finished sound
		for (Player player : Bukkit.getOnlinePlayers()) {
			player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 3.0f, 0.890899f);
		}

		if (votes.size() == 0) {
			Bukkit.broadcastMessage(TnxUtils.Name + ChatColor.GOLD + "No one voted in the poll");
			votes.clear();
			return;
		}

		int yes = 0;
		int no = 0;
		
		// Count the votes
		for(int vote : votes.values()) {
			if(vote == 1) yes++;
			if(vote == 2) no++;
		}
		
		// Format to percentage
		DecimalFormat df = new DecimalFormat("##.##%");
		String yesPercentage = df.format(yes/votes.size());
		String noPercentage = df.format(no/votes.size());
		
		// Broadcast the vote results
		Bukkit.broadcastMessage(TnxUtils.Name + ChatColor.GOLD + "Poll has ended! There were: " + votes.size() + " votes!");
		Bukkit.broadcastMessage(TnxUtils.Name + ChatColor.GREEN + "Yes: " + yes + " (" + yesPercentage + ")" + ChatColor.RED + " No: " + no + " (" + noPercentage + ")");

		votes.clear();
	}
}
