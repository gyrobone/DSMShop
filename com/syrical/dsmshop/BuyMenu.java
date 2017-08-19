package com.syrical.dsmshop;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import com.syrical.dsmshop.buymenus.BrewChantBuy;
import com.syrical.dsmshop.buymenus.DyeBuy;
import com.syrical.dsmshop.buymenus.FoodBuy;
import com.syrical.dsmshop.buymenus.OreBuy;

public class BuyMenu implements Listener {

	private Inventory buyInv;
	private ItemStack ores, food, brewchant, dyes;
	private FoodBuy foodBuy;
	private OreBuy oreBuy;
	private BrewChantBuy brewchantBuy;
	private DyeBuy	dyeBuy;
	
	public BuyMenu(Plugin plugin) {
		buyInv = Bukkit.getServer().createInventory(null, 9, "Buy Menu");
		ores = createItem(Material.IRON_ORE, "Buy Ores");
		food = createItem(Material.COOKED_BEEF, "Buy Food");
		brewchant = createItem(Material.ENCHANTMENT_TABLE, "Buy Brewing and Enchanting Items");
		dyes = createItem(Material.WOOL, "Buy Dyes");
		
		oreBuy = new OreBuy(plugin);
		foodBuy = new FoodBuy(plugin);
		brewchantBuy = new BrewChantBuy(plugin);
		dyeBuy = new DyeBuy(plugin);
		
		buyInv.setItem(0, ores);
		buyInv.setItem(1, food);
		buyInv.setItem(2, brewchant);
		buyInv.setItem(3, dyes);

		Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	private ItemStack createItem (Material itemStack, String name) {
		ItemStack i = new ItemStack(itemStack);
		ItemMeta im = i.getItemMeta();
		im.setDisplayName(name);
		i.setItemMeta(im);
		return i;
	}
	
	public void show (Player p) {
		p.openInventory(buyInv);
	}
	
	@EventHandler
	public void onInventoryClick (InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		if (e.getCurrentItem() == null) return;
		if(!e.getInventory().getName().equalsIgnoreCase(buyInv.getName())) return;
		if(e.getCurrentItem().getItemMeta() == null) return;
		if(e.getCurrentItem().getItemMeta().getDisplayName().contains("Buy Ores")) {
			e.setCancelled(true);
			oreBuy.show(p);
		} else if(e.getCurrentItem().getItemMeta().getDisplayName().contains("Buy Food")) {
			e.setCancelled(true);
			foodBuy.show(p);
		} else if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Buy Brewing and Enchanting Items")) {
			e.setCancelled(true);
			brewchantBuy.show(p);
		} else if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Buy Dyes")) {
			e.setCancelled(true);
			dyeBuy.show(p);
		}
	}
	
}
