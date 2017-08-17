package com.syrical.dsmshop;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;


public class PlayerData extends AbstractFile {

	public PlayerData (Plugin plugin) {
		super(plugin, "playerdata.yml");
	}
	
	public void setCredits (Player p, int i) {
		config.set(p.getUniqueId().toString() + ".credits", i);
		save();
	}
	
	public void addCredits (Player p, int i) {
		int currentAmount = (int) config.get(p.getUniqueId().toString() + ".credits");
		config.set(p.getUniqueId().toString()+ ".credits", currentAmount + i);
		save();
	}
	
	public void removeCredits (Player p, int i) {
		int currentAmount = (int) config.get(p.getUniqueId().toString() + ".credits");
		
		if(currentAmount < i) {
			config.set(p.getUniqueId().toString()+ ".credits", 0);
			save();
		} else {
			config.set(p.getUniqueId().toString()+ ".credits", currentAmount - i);
			save();
		}
		save();
	}
	
	public void getCredits (Player p) {
		p.sendMessage(ChatColor.GREEN + "Balance: " + config.get(p.getUniqueId().toString() + ".credits"));
		save();
	}
	
	public void payCredits (Player from, Player to, int i) {
		int fromBalance = (int) config.get(from.getUniqueId().toString() + ".credits");
		int toBalance = (int) config.get(to.getUniqueId().toString() + ".credits");
		
		if (fromBalance < i) {
			from.sendMessage(ChatColor.RED + "You don't have enough money for this");
		} else {
			config.set(from.getUniqueId().toString()+ ".credits", fromBalance - i);
			config.set(to.getUniqueId().toString()+ ".credits", toBalance + i);
			from.sendMessage(ChatColor.GREEN + "You have paid " + i + " to " + to.getName());
			to.sendMessage(ChatColor.GREEN + "You have recieved " + i + " from " + from.getName());
		}
		
		save();
		
	}
	
}
