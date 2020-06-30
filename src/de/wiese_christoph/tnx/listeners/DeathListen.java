package de.wiese_christoph.tnx.listeners;


import org.bukkit.entity.Entity;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;

import de.wiese_christoph.tnx.Main;
import de.wiese_christoph.tnx.manager.DeathManager;

public class DeathListen implements Listener {
	
	Main plugin;
	DeathManager deathMng;
	
	public DeathListen(Main plugin) {
		this.plugin = Main.getInstance();
		this.deathMng = new DeathManager();
	}
	


	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		Entity pe = (Entity)e.getEntity();
		if(pe instanceof Player) {
			final Player p = (Player)pe;
			
			// Play death sound to everyone
			if(plugin.getConfig().getBoolean("Death.Sound") == true) {
				deathMng.deathSound();
			}
			
			// Write Coordinates of death in chat
			if(plugin.getConfig().getBoolean("Death.Coordinates") == true) {
				deathMng.deathCoords(p);
			}
			
			// Spawn firework on death point
			if(plugin.getConfig().getBoolean("Death.Firework") == true) {
				deathMng.deathRockets(p);
			}
			// save inventory
			if (this.plugin.getConfig().getBoolean("Death.Inventory")) {
                DeathManager.addInv(p);
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
	
	
	@EventHandler
    public void onInventoryClick(final InventoryClickEvent e) {
        if (!this.plugin.getConfig().getBoolean("Death.Inventory")) {
            return;
        }
        if (e.getView().getTitle() == DeathManager.invName && !e.getWhoClicked().isOp()) {
            e.setCancelled(true);
        }
    }
	
}
	
