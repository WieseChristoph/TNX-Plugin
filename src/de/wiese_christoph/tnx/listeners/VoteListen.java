package de.wiese_christoph.tnx.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import de.wiese_christoph.tnx.manager.VoteManager;

public class VoteListen implements Listener{
	
	int min = (int) Math.round(Bukkit.getOnlinePlayers().size()*VoteManager.minPlayerPercent);
	
	//if a player leaves, he gets removed from the list
	 @EventHandler
	 public void onPlayerQuit(PlayerQuitEvent event)
	 {
		Player lp = (Player)event.getPlayer();
		
		//Time
		//Day
		if(VoteManager.onplD.contains(lp.getDisplayName())) {
		VoteManager.onplD.remove(lp.getDisplayName());
		
		if(VoteManager.onplD.size() >= min && Bukkit.getOnlinePlayers().size() != 0) {
			VoteManager.setDay();
		}
		
		//Night
		}else if(VoteManager.onplN.contains(lp.getDisplayName())) {
			VoteManager.onplN.remove(lp.getDisplayName());
		
		if(VoteManager.onplN.size() >= min && Bukkit.getOnlinePlayers().size() != 0) {
			VoteManager.setNight();
		}
		
		//Weather
		//Clear
		}else if(VoteManager.onplC.contains(lp.getDisplayName())) {
			VoteManager.onplC.remove(lp.getDisplayName());
		
		if(VoteManager.onplC.size() >= min && Bukkit.getOnlinePlayers().size() != 0) {
			VoteManager.setClear();
		}
		//Rain
		}else if(VoteManager.onplR.contains(lp.getDisplayName())) {
			VoteManager.onplR.remove(lp.getDisplayName());
			
			if(VoteManager.onplR.size() >= min && Bukkit.getOnlinePlayers().size() != 0) {
				VoteManager.setRain();
			}
		}
		
	 }
}
