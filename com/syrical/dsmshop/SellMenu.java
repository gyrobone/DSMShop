package com.syrical.dsmshop;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Wool;
import org.bukkit.plugin.Plugin;

import com.syrical.dsmshop.sellmenus.BrewChantSell;
import com.syrical.dsmshop.sellmenus.FoodSell;
import com.syrical.dsmshop.sellmenus.OreSell;

public class SellMenu implements Listener {

	private Inventory sellInv;
	private ItemStack ores, food, brewchant;
	private OreSell oreSell;
	private FoodSell foodSell;
	private BrewChantSell brewchantSell;
	
	public SellMenu(Plugin plugin) {
		sellInv = Bukkit.getServer().createInventory(null, 9, "Sell Menu");
		ores = createItem(DyeColor.YELLOW, "Sell Ores");
		food = createItem(DyeColor.GREEN, "Sell Food");
		brewchant = createItem(DyeColor.PURPLE, "Sell Brewing and Enchanting Items");
		
		oreSell = new OreSell(plugin);
		foodSell = new FoodSell(plugin);
		brewchantSell = new BrewChantSell(plugin);
		
		sellInv.setItem(0, ores);
		sellInv.setItem(1, food);
		sellInv.setItem(2, brewchant);
		
		Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	private ItemStack createItem(DyeColor dc, String name) {
		ItemStack i = new Wool(dc).toItemStack(1);
		ItemMeta im = i.getItemMeta();
		im.setDisplayName(name);
		im.setLore(Arrays.asList("Open " + name + " Menu"));
		i.setItemMeta(im);
		return i;
		
	}
	
	public void show (Player p) {
		p.openInventory(sellInv);
	}
	
	@EventHandler
	public void onInventoryClick (InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		if (e.getCurrentItem() == null) return;
		if(!e.getInventory().getName().equalsIgnoreCase(sellInv.getName())) return;
		if(e.getCurrentItem().getItemMeta() == null) return;
		if(e.getCurrentItem().getItemMeta().getDisplayName().contains("Sell Ores")) {
			//Buy Menu
			e.setCancelled(true);
			oreSell.show(p);
		} else if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Sell Food")) {
			e.setCancelled(true);
			foodSell.show(p);
		} else if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Sell Brewing and Enchanting Items")) {
			e.setCancelled(true);
			brewchantSell.show(p);
		}
	}
	
}
