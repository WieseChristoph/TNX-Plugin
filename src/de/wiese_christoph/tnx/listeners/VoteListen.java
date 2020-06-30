// 
// Decompiled by Procyon v0.5.36
// 

package de.wiese_christoph.tnx.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.entity.Player;
import de.wiese_christoph.tnx.manager.VoteManager;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.Listener;

public class VoteListen implements Listener
{
    @EventHandler
    public void onPlayerQuit(final PlayerQuitEvent event) {
        final Player lp = event.getPlayer();
        if (VoteManager.onplD.contains(lp.getDisplayName())) {
            VoteManager.onplD.remove(lp.getDisplayName());
        }
        else if (VoteManager.onplN.contains(lp.getDisplayName())) {
            VoteManager.onplN.remove(lp.getDisplayName());
        }
        else if (VoteManager.onplC.contains(lp.getDisplayName())) {
            VoteManager.onplC.remove(lp.getDisplayName());
        }
        else if (VoteManager.onplR.contains(lp.getDisplayName())) {
            VoteManager.onplR.remove(lp.getDisplayName());
        }
        VoteManager.checkVotePass();
    }
}
