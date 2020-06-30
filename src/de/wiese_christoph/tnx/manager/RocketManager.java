package de.wiese_christoph.tnx.manager;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import de.wiese_christoph.tnx.Main;

public class RocketManager {
	
	
	public void start(Player p) {
		Location pl = p.getLocation();
		// add glowing and show a title in the center of a screen
		p.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 1000, 1));
		p.sendTitle(ChatColor.DARK_RED + "RIP",ChatColor.GOLD + p.getDisplayName(), 2, 35, 2);
		
		// run every tick
		new BukkitRunnable() {
			@Override
			public void run() {
				// teleport player 1 block higher
				pl.add(0,1,0);
				p.teleport(pl);
				// spawn particles
				pl.getWorld().spawnParticle(Particle.DRAGON_BREATH, pl, 300);
				
				// if player reaches a specific height, end the timer
				if(pl.getY()>=110) {
					// create explosion and kill him
					pl.getWorld().createExplosion(pl, 6);
					p.setHealth(0);
					this.cancel();
				}
				
			}
		}.runTaskTimer(Main.getInstance(), 0, 0);
	}
}
