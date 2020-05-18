package de.wiese_christoph.tnx.listeners;


import org.bukkit.entity.Entity;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import de.wiese_christoph.tnx.Main;
import de.wiese_christoph.tnx.manager.DeathManager;

public class DeathListen implements Listener {
	
	Main plugin;
	
	public DeathListen(Main plugin) {
		this.plugin = Main.getInstance();
	}
	
	DeathManager deathMnd = new DeathManager();


	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		Entity pe = (Entity)e.getEntity();
		if(pe instanceof Player) {
			final Player p = (Player)pe;
			
			// Play death sound to everyone
			if(plugin.getConfig().getBoolean("Death.Sound") == true) {
				deathMnd.deathSound();
			}
			
			// Write Coordinates of death in chat
			if(plugin.getConfig().getBoolean("Death.Coordinates") == true) {
				deathMnd.deathCoords(p);
			}
			
			// Spawn firework on death point
			if(plugin.getConfig().getBoolean("Death.Firework") == true) {
				deathMnd.deathRockets(p);
			}
		}
	}
	
	
	@EventHandler
	public void onFireworkExplode(EntityExplodeEvent e) throws InterruptedException{
		
		//check if explosion is from firework
		if(plugin.getConfig().getBoolean("Death.Firework") == true && e.getEntity() instanceof Firework) {
			// cancel damage to items from fireworks
			e.blockList().clear();
		}
		
	}
	
	
	@EventHandler
	public void onEntityDamageEntity(EntityDamageByEntityEvent e) {
		
		//check if explosion is from firework
		if(plugin.getConfig().getBoolean("Death.Firework") == true && e.getEntity() instanceof Firework) {
			// cancel the damage
			e.setCancelled(true);
		}
	}
	
}
	
