package de.wiese_christoph.tnx.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class TabComplete implements TabCompleter {

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {

		if(cmd.getName().equalsIgnoreCase("vote")) {
			if(args.length == 1) {
				List<String> list = new ArrayList<>();
				list.add("day");
				list.add("night");
				list.add("clear");
				list.add("rain");
				
				Collections.sort(list);
				return list;
			}
		}
		
		if(cmd.getName().equalsIgnoreCase("ce")) {
			if(args.length == 1) {
				List<String> list = new ArrayList<>();
				list.add("true");
				list.add("false");
				
				Collections.sort(list);
				return list;
			}
		}
		
		return null;
	}

}
