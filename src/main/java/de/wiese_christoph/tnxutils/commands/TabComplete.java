package de.wiese_christoph.tnxutils.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class TabComplete implements TabCompleter {
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		List<String> list = new ArrayList<>();

		switch (cmd.getName().toLowerCase()) {
			case "vote" -> {
				if (args.length == 1) {
					list.add("day");
					list.add("night");
					list.add("clear");
					list.add("rain");
				}
			}
			case "cep", "twv" -> {
				if (args.length == 1) {
					list.add("true");
					list.add("false");
				}
			}
		}

		Collections.sort(list);

		if (list.isEmpty()) return null;
		return list;
	}
}
