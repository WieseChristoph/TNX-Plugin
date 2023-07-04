package de.wiese_christoph.tnxutils.managers;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import de.wiese_christoph.tnxutils.TnxUtils;

public class RocketManager {
	private TnxUtils plugin;

	public RocketManager(TnxUtils plugin) {
		this.plugin = plugin;
	}

	public void start(Player p, int height) {
		Location pl = p.getLocation();

		// Add glowing effect and show a title in the center of the screen
		p.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 1000, 1));
		p.sendTitle(ChatColor.DARK_RED + "RIP",ChatColor.GOLD + p.getDisplayName(), 2, 35, 2);

		new BukkitRunnable() {
			@Override
			public void run() {
				// Teleport player 1 block higher
				pl.add(0,1,0);
				p.teleport(pl);
				// Spawn particles
				pl.getWorld().spawnParticle(Particle.DRAGON_BREATH, pl, 300);
				
				// If player reaches a specific height, end the timer
				if(pl.getY() >= height) {
					// Create explosion and kill the player
					pl.getWorld().createExplosion(pl, 6);
					p.setHealth(0);
					this.cancel();
				}
				
			}
		}.runTaskTimer(plugin, 0, 0);
	}
}
