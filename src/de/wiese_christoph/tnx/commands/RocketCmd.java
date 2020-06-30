// 
// Decompiled by Procyon v0.5.36
// 

package de.wiese_christoph.tnx.commands;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import de.wiese_christoph.tnx.manager.RocketManager;
import de.wiese_christoph.tnx.Main;
import org.bukkit.command.CommandExecutor;

public class RocketCmd implements CommandExecutor
{
    Main plugin;
    RocketManager rocketMng;
    
    public RocketCmd(final Main plugin) {
        this.rocketMng = new RocketManager();
        this.plugin = Main.getInstance();
    }
    
    public boolean onCommand(final CommandSender sender, final Command cmd, final String Label, final String[] args) {
        if (!cmd.getName().equalsIgnoreCase("rocket") || !sender.hasPermission("tnx.rocket") || !(sender instanceof Player)) {
            sender.sendMessage("§4Error!");
            return false;
        }
        final Player p = (Player)sender;
        if (args.length == 0) {
            this.rocketMng.start(p);
            return true;
        }
        if (args.length != 1) {
            p.sendMessage("§4Too many Arguments");
            return false;
        }
        final Player tp = Bukkit.getPlayer(args[0]);
        if (tp != null) {
            this.rocketMng.start(tp);
            return true;
        }
        p.sendMessage("§4Argument needs to be a Player");
        return false;
    }
}
