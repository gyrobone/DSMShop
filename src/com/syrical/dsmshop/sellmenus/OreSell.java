package com.syrical.dsmshop.sellmenus;

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

public class OreSell extends AbstractFile implements Listener {
	
	private Inventory oreSell;
	private ItemStack diamond;
	//coal, charcoal, clay, flint, ironingot, goldingot, diamond, emerald, quartz
	public OreSell (Plugin plugin) {
		super(plugin, "shopdata.yml");
		oreSell = Bukkit.getServer().createInventory(null, 54, "Sell Ores");
		diamond = createItem("diamond");
		
		oreSell.setItem(0, diamond);
		
		Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	public void show (Player p) {
		p.openInventory(oreSell);
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
		String world = p.getWorld().getName();
		String item = e.getCurrentItem().getType().name().toLowerCase();
		if(!e.getInventory().getName().equalsIgnoreCase(oreSell.getName())) return;
		if(e.getCurrentItem().getType() == Material.DIAMOND) {
			if(e.isRightClick()) {
				Integer sellPrice = (int) config.get(world + "." + item.toLowerCase() + ".sellprice");
				e.setCancelled(true);
				e.getWhoClicked().closeInventory();
				p.chat("/addcredits " + (sellPrice * 64));
				p.chat("/balance");
				ItemStack diamonds = new ItemStack(Material.DIAMOND, 64);
				p.getInventory().removeItem(diamonds);
			} else if (e.isLeftClick()){
				Integer sellPrice = (int) config.get(world + "." + item.toLowerCase() + ".sellprice");
				e.setCancelled(true);
				e.getWhoClicked().closeInventory();
				p.chat("/addcredits " + sellPrice);
				p.chat("/balance");
				ItemStack diamonds = new ItemStack(Material.DIAMOND, 1);
				p.getInventory().removeItem(diamonds);
			}
			if(e.isShiftClick()) {
				p.getInventory().removeItem(new ItemStack(Material.DIAMOND,1));
			}
			p.updateInventory();
		}
	}
	
}
