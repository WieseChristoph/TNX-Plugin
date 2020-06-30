// 
// Decompiled by Procyon v0.5.36
// 

package de.wiese_christoph.tnx.manager;

import org.bukkit.plugin.Plugin;
import de.wiese_christoph.tnx.Main;
import java.util.Iterator;
import java.util.Map;
import org.bukkit.scheduler.BukkitRunnable;
import de.wiese_christoph.tnx.utils.MysqlCon;
import de.wiese_christoph.tnx.objects.PlayerStats;
import java.util.HashMap;

public class StatsManager
{
    public HashMap<String, PlayerStats> cache;
    private MysqlCon database;
    
    public StatsManager() {
        this.cache = new HashMap<String, PlayerStats>();
        this.database = new MysqlCon("NextTryMc", "Q%up3LzHl30HdX1V", "NextTryMc");
        this.updateTimer();
    }
    
    private void updateTimer() {
        new BukkitRunnable() {
            public void run() {
                for (final Map.Entry<String, PlayerStats> entry : StatsManager.this.cache.entrySet()) {
                    final PlayerStats val = entry.getValue();
                    StatsManager.this.database.updateData(val);
                }
                StatsManager.this.cache.clear();
            }
        }.runTaskTimerAsynchronously((Plugin)Main.getInstance(), 6000L, 6000L);
    }
}
