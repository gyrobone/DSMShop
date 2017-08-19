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


public class BrewChantSell extends AbstractFile implements Listener {
	
	private Inventory BrewChantSell;
	private ItemStack obsidian, book, blaze_rod, glowstone_dust, sulphur, nether_stalk, ender_pearl, spider_eye, ghast_tear;
	public BrewChantSell (Plugin plugin) {
		super(plugin, "shopdata.yml");
		BrewChantSell = Bukkit.getServer().createInventory(null, 9, "Sell Brewing and Enchanting Items");

		obsidian = createItem("obsidian");
		book = createItem("book");
		glowstone_dust = createItem("glowstone_dust");
		sulphur = createItem("sulphur");
		nether_stalk = createItem("nether_stalk");
		ender_pearl = createItem("ender_pearl");
		spider_eye = createItem("spider_eye");
		ghast_tear = createItem("ghast_tear");
		blaze_rod = createItem("blaze_rod");
		
		BrewChantSell.setItem(0, obsidian);
		BrewChantSell.setItem(1, book);
		BrewChantSell.setItem(2, glowstone_dust);
		BrewChantSell.setItem(3, sulphur);
		BrewChantSell.setItem(4, nether_stalk);
		BrewChantSell.setItem(5, ender_pearl);
		BrewChantSell.setItem(6, spider_eye);
		BrewChantSell.setItem(7, ghast_tear);
		BrewChantSell.setItem(8, blaze_rod);
		
		Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	public void show (Player p) {
		p.openInventory(BrewChantSell);
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
		if(!e.getInventory().getName().equalsIgnoreCase(BrewChantSell.getName())) return;
		String item = e.getCurrentItem().getType().name().toLowerCase();
		Material itemMat = e.getCurrentItem().getType();
		if(config.get("default." + item.toLowerCase()) == null) {
			e.setCancelled(true);
			return;
		}
		if(!e.getInventory().getName().equalsIgnoreCase(BrewChantSell.getName())) return;
		
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
				return;
			} else if (count < 32) {
				Integer sellPrice = (int) config.get(world + "." + item.toLowerCase() + ".sellprice");
				e.setCancelled(true);
				p.chat("/addcredits " + (sellPrice * count));
				p.chat("/balance");
				ItemStack items = new ItemStack(itemMat, count);
				p.getInventory().removeItem(items);
				return;
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
				return;
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

