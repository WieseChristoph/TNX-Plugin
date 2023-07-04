package de.wiese_christoph.tnxutils.listeners;

import de.wiese_christoph.tnxutils.managers.TimeWeatherVoteManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class TimeWeatherVoteListen implements Listener{
	TimeWeatherVoteManager timeWeatherVoteManager;

	public TimeWeatherVoteListen(TimeWeatherVoteManager timeWeatherVoteManager) {
		this.timeWeatherVoteManager = timeWeatherVoteManager;
	}
	
	// If a player leaves, he gets removed from the list
	 @EventHandler
	 public void onPlayerQuit(PlayerQuitEvent event) {
		 Player player = event.getPlayer();
		 timeWeatherVoteManager.removeVote(player);
	 }
}