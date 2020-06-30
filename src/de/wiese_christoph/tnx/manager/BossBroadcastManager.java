// 
// Decompiled by Procyon v0.5.36
// 

package de.wiese_christoph.tnx.manager;

import org.bukkit.plugin.Plugin;
import de.wiese_christoph.tnx.Main;
import org.bukkit.boss.BossBar;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BarColor;
import org.bukkit.ChatColor;

public class BossBroadcastManager
{
    public static void broadcast(final String msg, final int timeTicks) {
        if (msg == null) {
            return;
        }
        final BossBar bar = Bukkit.createBossBar(new StringBuilder().append(ChatColor.GOLD).append(ChatColor.BOLD).append(msg).toString(), BarColor.RED, BarStyle.SOLID, new BarFlag[0]);
        bar.setProgress(1.0);
        for (final Player p : Bukkit.getOnlinePlayers()) {
            bar.addPlayer(p);
        }
        new BukkitRunnable() {
            int i = timeTicks;
            
            public void run() {
                this.i -= 20;
                bar.setProgress(this.i / (double)timeTicks);
                if (this.i <= 0) {
                    bar.removeAll();
                    this.cancel();
                }
            }
        }.runTaskTimerAsynchronously((Plugin)Main.getInstance(), 20L, 20L);
    }
}
