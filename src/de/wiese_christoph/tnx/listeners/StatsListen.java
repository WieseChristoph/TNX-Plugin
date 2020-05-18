package de.wiese_christoph.tnx.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import de.wiese_christoph.tnx.Main;
import de.wiese_christoph.tnx.manager.StatsManager;
import de.wiese_christoph.tnx.objects.PlayerStats;

public class StatsListen implements Listener {

	Main plugin;
	
	public StatsListen(Main plugin) {
		this.plugin = Main.getInstance();
	}
	
	StatsManager statMng = new StatsManager();
	
	
	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		Entity pe = (Entity)e.getEntity();
		if(pe instanceof Player) {
			Player p = (Player)pe;
			// if player is in cache, update data
			if(statMng.cache.containsKey(p.getUniqueId().toString())){
				statMng.cache.get(p.getUniqueId().toString()).addDeath();
			}
			// if player is not in cache, create a object an put it in cache
			else {
				PlayerStats nPlayer = new PlayerStats(p.getDisplayName(), p.getUniqueId().toString());
				nPlayer.addDeath();
				statMng.cache.put(p.getUniqueId().toString(), nPlayer);
			}
			
			// add pvpKill to the killer
			if(p.getKiller() instanceof Player && p.getKiller() != p) {
				Player killer = p.getKiller();
				// if player is in cache, update data
				if(statMng.cache.containsKey(killer.getUniqueId().toString())){
					statMng.cache.get(killer.getUniqueId().toString()).addPvpKills();
				}
				// if player is not in cache, create a object an put it in cache
				else {
					PlayerStats nPlayer = new PlayerStats(killer.getDisplayName(), killer.getUniqueId().toString());
					nPlayer.addPvpKills();
					statMng.cache.put(killer.getUniqueId().toString(), nPlayer);
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
				if(statMng.cache.containsKey(p.getUniqueId().toString())){
					statMng.cache.get(p.getUniqueId().toString()).addPveKills();
				}
				// if player is not in cache, create a object an put it in cache
				else {
					PlayerStats nPlayer = new PlayerStats(p.getDisplayName(), p.getUniqueId().toString());
					nPlayer.addPveKills();
					statMng.cache.put(p.getUniqueId().toString(), nPlayer);
				}
			}
		}
	}
	
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		Player p = e.getPlayer();
		
		// if player is in cache, update data
		if(statMng.cache.containsKey(p.getUniqueId().toString())){
			statMng.cache.get(p.getUniqueId().toString()).addBrokenBlocks();
		}
		// if player is not in cache, create a object an put it in cache
		else {
			PlayerStats nPlayer = new PlayerStats(p.getDisplayName(), p.getUniqueId().toString());
			nPlayer.addBrokenBlocks();
			statMng.cache.put(p.getUniqueId().toString(), nPlayer);
		}
	}
	
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {
		Player p = e.getPlayer();
		
		// if player is in cache, update data
		if(statMng.cache.containsKey(p.getUniqueId().toString())){
			statMng.cache.get(p.getUniqueId().toString()).addPlacedBlocks();
		}
		// if player is not in cache, create a object an put it in cache
		else {
			PlayerStats nPlayer = new PlayerStats(p.getDisplayName(), p.getUniqueId().toString());
			nPlayer.addPlacedBlocks();
			statMng.cache.put(p.getUniqueId().toString(), nPlayer);
		}
	}
	
}
