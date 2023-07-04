package de.wiese_christoph.tnxutils.listeners;

import java.util.ArrayList;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Creeper;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.scheduler.BukkitRunnable;

import de.wiese_christoph.tnxutils.TnxUtils;

public class CreeperExplosionListen implements Listener {
	TnxUtils plugin;

	public CreeperExplosionListen(TnxUtils plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onCreeperExplode(EntityExplodeEvent e) {
		if (!(e.getEntity() instanceof Creeper) || !plugin.getConfig().getBoolean("CreeperExplosionProtection.enabled")) return;

		// Either regenerate blocks or cancel explosion of blocks
		if(plugin.getConfig().getBoolean("CreeperExplosionProtection.SlowRegen")) {
			ArrayList<BlockState> state = new ArrayList<>();

			// Remove interactable blocks, to prevent explosion of them
			e.blockList().removeIf(b -> b.getType().isInteractable());

			// Change the blocks to air and set their state into the list
			for(Block b : e.blockList().toArray(new Block[0])) {
				state.add(b.getState());
				b.setType(Material.AIR);
			}

			// Regenerate blocks
			new BukkitRunnable() {
				@Override
				public void run() {
					if (state.isEmpty()) this.cancel();
					else {
						state.get(0).update(true, false);
						state.remove(0);
					}
				}
			}.runTaskTimer(plugin, 100, 2);
		} else {
			e.blockList().clear();
		}
	}
}
