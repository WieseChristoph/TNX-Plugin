package de.wiese_christoph.tnxutils.listeners;


import de.wiese_christoph.tnxutils.TnxUtils;
import de.wiese_christoph.tnxutils.managers.DeathManager;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;

public class DeathListen implements Listener {
	TnxUtils plugin;
	DeathManager deathManager;
	
	public DeathListen(TnxUtils plugin, DeathManager deathManager) {
		this.plugin = plugin;
		this.deathManager = deathManager;
	}

	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		Player player = e.getEntity();

		// Play death sound to everyone
		if(plugin.getConfig().getBoolean("Death.Sound")) {
			deathManager.playDeathSound();
		}

		// Write coordinates of death in chat
		if(plugin.getConfig().getBoolean("Death.Coordinates")) {
			deathManager.broadcastDeathCoords(player);
		}

		// Spawn firework on death point
		if(plugin.getConfig().getBoolean("Death.Firework")) {
			deathManager.deathRockets(player, 30);
		}
		// Save inventory
		if (plugin.getConfig().getBoolean("Death.Inventory")) {
			deathManager.addInv(player);
		}
	}
	
	@EventHandler
	public void onFireworkExplode(EntityExplodeEvent e) {
		// Check if explosion is from firework
		if(plugin.getConfig().getBoolean("Death.Firework") && e.getEntity() instanceof Firework) {
			// Cancel damage to items from fireworks
			e.blockList().clear();
		}
	}
	
	@EventHandler
	public void onEntityDamageEntity(EntityDamageByEntityEvent e) {
		// Check if explosion is from firework
		if(plugin.getConfig().getBoolean("Death.Firework") && e.getEntity() instanceof Firework) {
			// Cancel the damage
			e.setCancelled(true);
		}
	}
	
	@EventHandler
    public void onInventoryClick(final InventoryClickEvent e) {
        if (!plugin.getConfig().getBoolean("Death.Inventory")) return;

		// Cancel death inventory click if not op
        if (e.getView().getTitle().equals(deathManager.INV_NAME) && !e.getWhoClicked().isOp()) e.setCancelled(true);
    }
}
	
