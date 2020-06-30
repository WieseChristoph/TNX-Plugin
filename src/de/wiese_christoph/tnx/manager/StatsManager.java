package de.wiese_christoph.tnx.manager;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.scheduler.BukkitRunnable;

import de.wiese_christoph.tnx.Main;
import de.wiese_christoph.tnx.objects.PlayerStats;
import de.wiese_christoph.tnx.utils.MysqlCon;

public class StatsManager{
	
	
	// hashmap to store the cache of Player stats to send to database
	public HashMap<String,PlayerStats> cache = new HashMap<String,PlayerStats>();
	
	// create Database Connection
	private MysqlCon database = new MysqlCon("NextTryMc", "Q%up3LzHl30HdX1V", "NextTryMc");
	
	public StatsManager() {
		// start updater
		updateTimer();
	}
	
	private void updateTimer() {
		// update database every 5 minutes
		new BukkitRunnable() {
			@Override
			public void run() {
				// update data for everyone in cache hashmap
				for(Map.Entry<String, PlayerStats> entry : cache.entrySet()) {
				    PlayerStats val=entry.getValue();
				    // send data to database
					database.updateData(val);
				}
				// clear cache
				cache.clear();
					
			}
		}.runTaskTimerAsynchronously(Main.getInstance(), 6000, 6000);
	}
}



