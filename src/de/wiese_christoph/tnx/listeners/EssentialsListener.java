// 
// Decompiled by Procyon v0.5.36
// 

package de.wiese_christoph.tnx.listeners;

import org.bukkit.event.EventHandler;
import de.wiese_christoph.tnx.manager.VoteManager;
import net.ess3.api.events.AfkStatusChangeEvent;
import org.bukkit.event.Listener;

public class EssentialsListener implements Listener
{
    @EventHandler
    public void onAfkStatusChange(final AfkStatusChangeEvent e) {
        if (e.getValue()) {
            ++VoteManager.afkPlayers;
        }
        if (!e.getValue()) {
            --VoteManager.afkPlayers;
        }
    }
}
