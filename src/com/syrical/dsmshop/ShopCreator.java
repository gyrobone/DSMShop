package com.syrical.dsmshop;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class ShopCreator implements Listener {

	public ShopCreator (DSMShop plugin) {
		
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onSignClick (PlayerInteractEvent e) {
		
		Player p = (Player) e.getPlayer();
		String pName = p.getName();
		Block b = e.getClickedBlock();
		
		if(b.getType() == Material.WALL_SIGN) {
			
			Sign s = (Sign) b.getState();
			String[] ln = s.getLines();
			
			if(Bukkit.getServer().getPlayer(pName).isOp() == true) {
				if(ln[0].toLowerCase().equalsIgnoreCase("[Shop]")) {
					s.setLine(2, ChatColor.DARK_GREEN + ""+ ChatColor.BOLD + "Active");
					s.setLine(3, "§kWorking");
					s.update();
					p.sendMessage(ChatColor.GREEN + "Shop built successfully!");
					HandlerList.unregisterAll(DSMShop.sCreate);
				}
			}
			
		}
		
		HandlerList.unregisterAll(DSMShop.sCreate);
		
	}
	
}
