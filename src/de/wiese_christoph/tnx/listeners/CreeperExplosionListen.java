// 
// Decompiled by Procyon v0.5.36
// 

package de.wiese_christoph.tnx.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import java.util.ArrayList;
import org.bukkit.entity.Creeper;
import org.bukkit.event.entity.EntityExplodeEvent;
import de.wiese_christoph.tnx.Main;
import org.bukkit.event.Listener;

public class CreeperExplosionListen implements Listener
{
    Main plugin;
    
    public CreeperExplosionListen(final Main main) {
        this.plugin = Main.getInstance();
    }
    
    @EventHandler
    public void onCreeperExplode(final EntityExplodeEvent e) throws InterruptedException {
        if (e.getEntity() instanceof Creeper && this.plugin.getConfig().getBoolean("CreeperExplosion.enabled")) {
            if (this.plugin.getConfig().getBoolean("CreeperExplosion.SlowRegen")) {
                final ArrayList<BlockState> state = new ArrayList<BlockState>();
                Block[] array;
                for (int length = (array = e.blockList().toArray(new Block[e.blockList().size()])).length, i = 0; i < length; ++i) {
                    final Block b = array[i];
                    if (b.getType() == Material.CHEST || b.getType() == Material.FURNACE) {
                        e.blockList().remove(b);
                    }
                }
                Block[] array2;
                for (int length2 = (array2 = e.blockList().toArray(new Block[e.blockList().size()])).length, j = 0; j < length2; ++j) {
                    final Block b = array2[j];
                    state.add(b.getState());
                    b.setType(Material.AIR);
                }
                new BukkitRunnable() {
                    public void run() {
                        if (state.size() > 0) {
                            state.get(0).update(true, false);
                            state.remove(0);
                        }
                        else {
                            this.cancel();
                        }
                    }
                }.runTaskTimer((Plugin)this.plugin, 100L, 2L);
            }
            else {
                e.blockList().clear();
            }
        }
    }
}
