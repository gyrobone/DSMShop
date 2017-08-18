package com.syrical.dsmshop.sellmenus;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import com.syrical.dsmshop.AbstractFile;


public class FoodSell extends AbstractFile implements Listener {
	
	private Inventory FoodSell;
	private ItemStack cooked_beef, grilled_pork, bread, wheat, apple, cookie, carrot_item, potato_item, melon, pumpkin, sugar, seeds, melon_seeds, pumpkin_seeds, egg;
	public FoodSell (Plugin plugin) {
		super(plugin, "shopdata.yml");
		FoodSell = Bukkit.getServer().createInventory(null, 18, "Sell Food");

		cooked_beef = createItem("cooked_beef");
		grilled_pork = createItem("grilled_pork");
		bread = createItem("bread");
		wheat = createItem("wheat");
		apple = createItem("apple");
		cookie = createItem("cookie");
		carrot_item = createItem("carrot_item");
		potato_item = createItem("potato_item");
		melon = createItem("melon");
		pumpkin = createItem("pumpkin");
		sugar = createItem("sugar");
		seeds = createItem("seeds");
		melon_seeds = createItem("melon_seeds");
		pumpkin_seeds = createItem("pumpkin_seeds");
		egg = createItem("egg");
		
		FoodSell.setItem(0, cooked_beef);
		FoodSell.setItem(1, grilled_pork);
		FoodSell.setItem(2, bread);
		FoodSell.setItem(3, wheat);
		FoodSell.setItem(4, apple);
		FoodSell.setItem(5, cookie);
		FoodSell.setItem(6, carrot_item);
		FoodSell.setItem(7, potato_item);
		FoodSell.setItem(8, melon);
		FoodSell.setItem(9, pumpkin);
		FoodSell.setItem(10, sugar);
		FoodSell.setItem(11, seeds);
		FoodSell.setItem(12, melon_seeds);
		FoodSell.setItem(13, pumpkin_seeds);
		FoodSell.setItem(14, egg);
		
		Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	public void show (Player p) {
		p.openInventory(FoodSell);
	}
	
	@SuppressWarnings("deprecation")
	private ItemStack createItem (String item) {
		Integer itemID = (Integer) config.get("default." + item + ".id");
		ItemStack i = new ItemStack(itemID);
		ItemMeta im = i.getItemMeta();
		im.setLore(Arrays.asList("Left Click to Sell 1", "Right Click to Sell 32"));
		return i;
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onInventoryClick (InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		String world = p.getWorld().getName();
		if (e.getCurrentItem() == null) return;
		if (e.getCurrentItem().getType() == Material.AIR) return;
		if(!e.getInventory().getName().equalsIgnoreCase(FoodSell.getName())) return;
		String item = e.getCurrentItem().getType().name().toLowerCase();
		Material itemMat = e.getCurrentItem().getType();
		if(!e.getInventory().getName().equalsIgnoreCase(FoodSell.getName())) return;
		
		if(e.isRightClick()) {
			int count = 0;
				
			for (ItemStack stack : p.getInventory().getContents()) {
				if (stack != null && stack.getType() ==itemMat) {
					count += stack.getAmount();
				}
			}
			
			if (count >= 32) {
				Integer sellPrice = (int) config.get(world + "." + item.toLowerCase() + ".sellprice");
				e.setCancelled(true);
				p.chat("/addcredits " + (sellPrice * 32));
				p.chat("/balance");
				ItemStack items = new ItemStack(itemMat, 32);
				p.getInventory().removeItem(items);
			} else if (count < 32) {
				Integer sellPrice = (int) config.get(world + "." + item.toLowerCase() + ".sellprice");
				e.setCancelled(true);
				p.chat("/addcredits " + (sellPrice * count));
				p.chat("/balance");
				ItemStack items = new ItemStack(itemMat, count);
				p.getInventory().removeItem(items);
			} else if (count == 0) {
				e.setCancelled(true);
				p.sendMessage(ChatColor.RED + "You dont have any " + e.getCurrentItem().getType().name().toLowerCase());
			}
			
		} else if (e.isLeftClick()){
			int count = 0;
			
			for (ItemStack stack : p.getInventory().getContents()) {
				if (stack != null && stack.getType() == itemMat) {
					count += stack.getAmount();
				}
			}
				
			if (count >= 1) {
				Integer sellPrice = (int) config.get(world + "." + item.toLowerCase() + ".sellprice");
				e.setCancelled(true);
				p.chat("/addcredits " + (sellPrice));
				p.chat("/balance");
				ItemStack items = new ItemStack(itemMat, 1);
				p.getInventory().removeItem(items);
			} else if (count == 0) {
				e.setCancelled(true);
				p.sendMessage(ChatColor.RED + "You dont have any " + e.getCurrentItem().getType().name().toLowerCase());
			}
		}
		if(e.isShiftClick()) {
			p.getInventory().removeItem(new ItemStack(itemMat,1));
		}
		p.updateInventory();
	}
}

