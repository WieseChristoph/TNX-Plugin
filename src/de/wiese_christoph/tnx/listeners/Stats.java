package de.wiese_christoph.tnx.listeners;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;

import de.wiese_christoph.tnx.Main;
import de.wiese_christoph.tnx.objects.PlayerStats;
import de.wiese_christoph.tnx.utils.MysqlCon;

public class Stats implements Listener {

	Main plugin;
	
	public Stats(Main plugin) {
		this.plugin = plugin;
		updateTimer();
	}
	
	// hashmap to store the cache of Player stats to send to database
	private HashMap<String,PlayerStats> cache = new HashMap<String,PlayerStats>();
	
	// create Database Connection
	MysqlCon database = new MysqlCon("NextTryMc", "Q%up3LzHl30HdX1V", "NextTryMc");
	
	
	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		Entity pe = (Entity)e.getEntity();
		if(pe instanceof Player) {
			Player p = (Player)pe;
			// if player is in cache, update data
			if(cache.containsKey(p.getUniqueId().toString())){
				cache.get(p.getUniqueId().toString()).addDeath();
			}
			// if player is not in cache, create a object an put it in cache
			else {
				PlayerStats nPlayer = new PlayerStats(p.getDisplayName(), p.getUniqueId().toString());
				nPlayer.addDeath();
				cache.put(p.getUniqueId().toString(), nPlayer);
			}
			
			// add pvpKill to the killer
			if(p.getKiller() instanceof Player && p.getKiller() != p) {
				Player killer = p.getKiller();
				// if player is in cache, update data
				if(cache.containsKey(killer.getUniqueId().toString())){
					cache.get(killer.getUniqueId().toString()).addPvpKills();
				}
				// if player is not in cache, create a object an put it in cache
				else {
					PlayerStats nPlayer = new PlayerStats(killer.getDisplayName(), killer.getUniqueId().toString());
					nPlayer.addPvpKills();
					cache.put(killer.getUniqueId().toString(), nPlayer);
				}
			}
		}
	}
	
	
	@EventHandler
	public void onEntityDeath(EntityDeathEvent e) {
		
		// check that the entity isn't a player
		if(!(e.getEntity() instanceof Player)) {
			
			final LivingEntity entity =  e.getEntity();
			if(entity.getKiller() instanceof Player) {
				Player p = entity.getKiller();
				
				// if player is in cache, update data
				if(cache.containsKey(p.getUniqueId().toString())){
					cache.get(p.getUniqueId().toString()).addPveKills();
				}
				// if player is not in cache, create a object an put it in cache
				else {
					PlayerStats nPlayer = new PlayerStats(p.getDisplayName(), p.getUniqueId().toString());
					nPlayer.addPveKills();
					cache.put(p.getUniqueId().toString(), nPlayer);
				}
			}
		}
	}
	
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		Player p = e.getPlayer();
		
		// if player is in cache, update data
		if(cache.containsKey(p.getUniqueId().toString())){
			cache.get(p.getUniqueId().toString()).addBrokenBlocks();
		}
		// if player is not in cache, create a object an put it in cache
		else {
			PlayerStats nPlayer = new PlayerStats(p.getDisplayName(), p.getUniqueId().toString());
			nPlayer.addBrokenBlocks();
			cache.put(p.getUniqueId().toString(), nPlayer);
		}
	}
	
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {
		Player p = e.getPlayer();
		
		// if player is in cache, update data
		if(cache.containsKey(p.getUniqueId().toString())){
			cache.get(p.getUniqueId().toString()).addPlacedBlocks();
		}
		// if player is not in cache, create a object an put it in cache
		else {
			PlayerStats nPlayer = new PlayerStats(p.getDisplayName(), p.getUniqueId().toString());
			nPlayer.addPlacedBlocks();
			cache.put(p.getUniqueId().toString(), nPlayer);
		}
	}
	
	
	// update database every 5 minutes
	private void updateTimer() {
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
		}.runTaskTimerAsynchronously(plugin, 6000, 6000);
	}
	
}
