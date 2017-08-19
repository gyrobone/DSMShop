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


public class OreSell extends AbstractFile implements Listener {
	
	private Inventory oreSell;
	private ItemStack coal, clay_ball, flint, redstone, iron_ingot, gold_ingot, diamond, emerald, quartz;
	public OreSell (Plugin plugin) {
		super(plugin, "shopdata.yml");
		oreSell = Bukkit.getServer().createInventory(null, 9, "Sell Ores");

		flint = createItem("flint");
		coal = createItem("coal");
		clay_ball = createItem("clay_ball");
		iron_ingot = createItem("iron_ingot");
		gold_ingot = createItem("gold_ingot");
		diamond = createItem("diamond");
		emerald = createItem("emerald");
		quartz = createItem("quartz");
		redstone = createItem("redstone");
		
		oreSell.setItem(0, flint);
		oreSell.setItem(1, coal);
		oreSell.setItem(2, clay_ball);
		oreSell.setItem(3, iron_ingot);
		oreSell.setItem(4, gold_ingot);
		oreSell.setItem(5, diamond);
		oreSell.setItem(6, emerald);
		oreSell.setItem(7, quartz);
		oreSell.setItem(8, redstone);
		
		Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	public void show (Player p) {
		p.openInventory(oreSell);
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
		if(!e.getInventory().getName().equalsIgnoreCase(oreSell.getName())) return;
		String item = e.getCurrentItem().getType().name().toLowerCase();
		if(config.get("default." + item.toLowerCase()) == null) {
			e.setCancelled(true);
			return;
		}
		Material itemMat = e.getCurrentItem().getType();
		if(!e.getInventory().getName().equalsIgnoreCase(oreSell.getName())) return;
		
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

