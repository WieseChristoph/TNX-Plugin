package de.wiese_christoph.tnx.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import de.wiese_christoph.tnx.manager.VoteManager;

public class VoteListen implements Listener{

	
	//if a player leaves, he gets removed from the list
	 @EventHandler
	 public void onPlayerQuit(PlayerQuitEvent event)
	 {
		Player lp = (Player)event.getPlayer();
		
		// if player was in one of the lists, remove him
		//Day
		if(VoteManager.onplD.contains(lp.getDisplayName())) {
			VoteManager.onplD.remove(lp.getDisplayName());
		
		//Night
		}else if(VoteManager.onplN.contains(lp.getDisplayName())) {
			VoteManager.onplN.remove(lp.getDisplayName());
		
		//Weather
		//Clear
		}else if(VoteManager.onplC.contains(lp.getDisplayName())) {
			VoteManager.onplC.remove(lp.getDisplayName());
			
		//Rain
		}else if(VoteManager.onplR.contains(lp.getDisplayName())) {
			VoteManager.onplR.remove(lp.getDisplayName());

		}
		
		VoteManager.checkVotePass();
		
	 }
	
}