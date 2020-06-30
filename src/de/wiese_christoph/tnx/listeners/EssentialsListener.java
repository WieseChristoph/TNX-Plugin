package de.wiese_christoph.tnx.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import de.wiese_christoph.tnx.manager.VoteManager;
import net.ess3.api.events.AfkStatusChangeEvent;

public class EssentialsListener implements Listener{
	
	// notice if a player goes afk to remove them from vote system
	 @EventHandler
	 public void onAfkStatusChange(AfkStatusChangeEvent e)
	 {
		 if(e.getValue()) VoteManager.afkPlayers++;
		 if(!e.getValue()) VoteManager.afkPlayers--;
	 }
}
