// 
// Decompiled by Procyon v0.5.36
// 

package de.wiese_christoph.tnx.utils;

import org.bukkit.Bukkit;
import java.util.Calendar;
import org.bukkit.plugin.Plugin;

public class Functions
{
    public static int scheduleRepeatAtTime(final Plugin plugin, final Runnable task, final int hour) {
        final Calendar cal = Calendar.getInstance();
        final long now = cal.getTimeInMillis();
        if (cal.get(11) >= hour) {
            cal.add(5, 1);
        }
        cal.set(11, hour);
        cal.set(12, 0);
        cal.set(13, 0);
        cal.set(14, 0);
        final long offset = cal.getTimeInMillis() - now;
        final long ticks = offset / 50L;
        return Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, task, ticks, 1728000L);
    }
}
