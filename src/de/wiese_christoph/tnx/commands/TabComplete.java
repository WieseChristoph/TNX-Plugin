// 
// Decompiled by Procyon v0.5.36
// 

package de.wiese_christoph.tnx.commands;

import java.util.Collections;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class TabComplete implements TabCompleter
{
    public List<String> onTabComplete(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (cmd.getName().equalsIgnoreCase("vote") && args.length == 1) {
            final List<String> list = new ArrayList<String>();
            list.add("day");
            list.add("night");
            list.add("clear");
            list.add("rain");
            Collections.sort(list);
            return list;
        }
        if (cmd.getName().equalsIgnoreCase("ce") && args.length == 1) {
            final List<String> list = new ArrayList<String>();
            list.add("true");
            list.add("false");
            Collections.sort(list);
            return list;
        }
        if (cmd.getName().equalsIgnoreCase("tv") && args.length == 1) {
            final List<String> list = new ArrayList<String>();
            list.add("true");
            list.add("false");
            Collections.sort(list);
            return list;
        }
        if (cmd.getName().equalsIgnoreCase("bp") && args.length == 1) {
            final List<String> list = new ArrayList<String>();
            list.add("true");
            list.add("false");
            Collections.sort(list);
            return list;
        }
        return null;
    }
}
