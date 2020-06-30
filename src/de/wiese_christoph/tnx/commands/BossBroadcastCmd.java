// 
// Decompiled by Procyon v0.5.36
// 

package de.wiese_christoph.tnx.commands;

import de.wiese_christoph.tnx.manager.BossBroadcastManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import de.wiese_christoph.tnx.Main;
import org.bukkit.command.CommandExecutor;

public class BossBroadcastCmd implements CommandExecutor
{
    Main plugin;
    
    public BossBroadcastCmd(final Main main) {
        this.plugin = Main.getInstance();
    }
    
    public boolean onCommand(final CommandSender sender, final Command cmd, final String Label, final String[] args) {
        if (!cmd.getName().equalsIgnoreCase("bb") || !sender.hasPermission("tnx.bb")) {
            return false;
        }
        if (args.length != 0) {
            String msg = "";
            for (final String a : args) {
                msg = String.valueOf(msg) + " " + a;
            }
            BossBroadcastManager.broadcast(msg, 1200);
            return true;
        }
        return false;
    }
}
