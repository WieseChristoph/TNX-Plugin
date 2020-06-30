// 
// Decompiled by Procyon v0.5.36
// 

package de.wiese_christoph.tnx.commands;

import org.bukkit.Bukkit;
import de.wiese_christoph.tnx.manager.DeathManager;
import org.bukkit.entity.Player;
import de.wiese_christoph.tnx.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;

public class DeathInventory implements CommandExecutor
{
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (!cmd.getName().equalsIgnoreCase("di") || !sender.hasPermission("tnx.di")) {
            sender.sendMessage(String.valueOf(Main.Name) + "§4No Permission!");
            return false;
        }
        if (sender instanceof Player) {
            if (args.length == 0) {
                DeathManager.showInv((Player)sender, (Player)sender);
            }
            else {
                if (args.length != 1) {
                    return false;
                }
                final Player tp = Bukkit.getPlayerExact(args[0]);
                if (tp == null) {
                    sender.sendMessage(String.valueOf(Main.Name) + "§4Spieler nicht gefunden!");
                    return false;
                }
                DeathManager.showInv((Player)sender, tp);
            }
            return true;
        }
        return false;
    }
}
