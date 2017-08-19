package com.syrical.dsmshop;

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


public class SellMenu extends AbstractFile implements Listener {
	
	private Inventory SellMenu;
	private ItemStack coal, clay_ball, flint, redstone, iron_ingot, gold_ingot, diamond, emerald, quartz;
	private ItemStack cooked_beef, grilled_pork, bread, wheat, apple, cookie, carrot_item, potato_item, melon, pumpkin, sugar, seeds, melon_seeds, pumpkin_seeds, egg;
	private ItemStack obsidian, book, blaze_rod, glowstone_dust, sulphur, nether_stalk, ender_pearl, spider_eye, ghast_tear;
	public SellMenu (Plugin plugin) {
		super(plugin, "shopdata.yml");
		SellMenu = Bukkit.getServer().createInventory(null, 36, "Sell Menu");

		flint = createItem("flint");
		coal = createItem("coal");
		clay_ball = createItem("clay_ball");
		iron_ingot = createItem("iron_ingot");
		gold_ingot = createItem("gold_ingot");
		diamond = createItem("diamond");
		emerald = createItem("emerald");
		quartz = createItem("quartz");
		redstone = createItem("redstone");
		
		SellMenu.setItem(0, flint);
		SellMenu.setItem(1, coal);
		SellMenu.setItem(2, clay_ball);
		SellMenu.setItem(3, iron_ingot);
		SellMenu.setItem(4, gold_ingot);
		SellMenu.setItem(5, diamond);
		SellMenu.setItem(6, emerald);
		SellMenu.setItem(7, quartz);
		SellMenu.setItem(8, redstone);
		
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
		
		SellMenu.setItem(9, cooked_beef);
		SellMenu.setItem(10, grilled_pork);
		SellMenu.setItem(11, bread);
		SellMenu.setItem(12, wheat);
		SellMenu.setItem(13, apple);
		SellMenu.setItem(14, cookie);
		SellMenu.setItem(15, carrot_item);
		SellMenu.setItem(16, potato_item);
		SellMenu.setItem(17, melon);
		SellMenu.setItem(18, pumpkin);
		SellMenu.setItem(19, sugar);
		SellMenu.setItem(20, seeds);
		SellMenu.setItem(21, melon_seeds);
		SellMenu.setItem(22, pumpkin_seeds);
		SellMenu.setItem(23, egg);
		
		obsidian = createItem("obsidian");
		book = createItem("book");
		glowstone_dust = createItem("glowstone_dust");
		sulphur = createItem("sulphur");
		nether_stalk = createItem("nether_stalk");
		ender_pearl = createItem("ender_pearl");
		spider_eye = createItem("spider_eye");
		ghast_tear = createItem("ghast_tear");
		blaze_rod = createItem("blaze_rod");
		
		SellMenu.setItem(24, obsidian);
		SellMenu.setItem(25, book);
		SellMenu.setItem(26, glowstone_dust);
		SellMenu.setItem(27, sulphur);
		SellMenu.setItem(28, nether_stalk);
		SellMenu.setItem(29, ender_pearl);
		SellMenu.setItem(30, spider_eye);
		SellMenu.setItem(31, ghast_tear);
		SellMenu.setItem(32, blaze_rod);
		
		Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	public void show (Player p) {
		p.openInventory(SellMenu);
	}
	
	@SuppressWarnings("deprecation")
	private ItemStack createItem (String item) {
		Integer itemID = (Integer) config.get("default." + item + ".id");
		
		Integer priceCarinus = (int) config.get("world_carinus." + item.toLowerCase() + ".sellprice");
		Integer priceDamara = (int) config.get("world_damara." + item.toLowerCase() + ".sellprice");
		Integer priceOpia3 = (int) config.get("world_opia3." + item.toLowerCase() + ".sellprice");
		Integer priceSirona = (int) config.get("world_sirona." + item.toLowerCase() + ".sellprice");
		
		ItemStack i = new ItemStack(itemID);
		ItemMeta im = i.getItemMeta();
		im.setLore(Arrays.asList("Carinus: " + priceCarinus + " credits", "Damara: " + priceDamara + " credits", "Opia 3: " + priceOpia3 + " credits", "Sirona: " + priceSirona + " credits"));
		i.setItemMeta(im);
		return i;
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onInventoryClick (InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		String world = p.getWorld().getName();
		if (e.getCurrentItem() == null) return;
		if (e.getCurrentItem().getType() == Material.AIR) return;
		if(!e.getInventory().getName().equalsIgnoreCase(SellMenu.getName())) return;
		String item = e.getCurrentItem().getType().name().toLowerCase();
		if(config.get("default." + item.toLowerCase()) == null) {
			e.setCancelled(true);
			return;
		}
		Material itemMat = e.getCurrentItem().getType();
		if(!e.getInventory().getName().equalsIgnoreCase(SellMenu.getName())) return;
		
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

