package com.syrical.dsmshop.buymenus;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import com.syrical.dsmshop.AbstractFile;

public class OreBuy extends AbstractFile implements Listener {
	
	private Inventory oreBuy;
	private ItemStack diamond;
	//coal, charcoal, clay, flint, ironingot, goldingot, diamond, emerald, quartz
	public OreBuy (Plugin plugin) {
		super(plugin, "shopdata.yml");
		oreBuy = Bukkit.getServer().createInventory(null, 54, "Buy Ores");
		diamond = createItem("diamond");
		
		oreBuy.setItem(0, diamond);
		
		Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	public void show (Player p) {
		
		p.openInventory(oreBuy);
	}
	
	@SuppressWarnings("deprecation")
	private ItemStack createItem (String item) {
		Integer itemID = (Integer) config.get("default." + item + ".id");
		ItemStack i = new ItemStack(itemID);
		return i;
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onInventoryClick (InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		//String world = p.getWorld().getName();
		//String item = e.getCurrentItem().getType().name().toLowerCase();
		if(!e.getInventory().getName().equalsIgnoreCase(oreBuy.getName())) return;
		if(e.getCurrentItem().getType() == Material.DIAMOND) {
			if(e.isRightClick()) {
				//Integer buyPrice = (int) config.get(world + "." + item.toLowerCase() + ".buyprice");
				e.setCancelled(true);
				e.getWhoClicked().closeInventory();
				//pData.removeCredits(p, buyPrice * 64);
				ItemStack diamonds = new ItemStack(Material.DIAMOND, 65);
				p.getInventory().addItem(diamonds);
				
			} else if (e.isLeftClick()){
				//Integer buyPrice = (int) config.get(world + "." + item.toLowerCase() + ".buyprice");
				e.setCancelled(true);
				e.getWhoClicked().closeInventory();
				//pData.removeCredits(p, buyPrice);
				ItemStack diamonds = new ItemStack(Material.DIAMOND, 2);
				p.getInventory().addItem(diamonds);
			}
			if(e.isShiftClick()) {
				p.getInventory().removeItem(new ItemStack(Material.DIAMOND,1));
			}
			p.getInventory().removeItem(new ItemStack(Material.DIAMOND,1));
			p.updateInventory();
			
		}
	}
	
}
