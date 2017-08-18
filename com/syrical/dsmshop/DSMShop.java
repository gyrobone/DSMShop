package com.syrical.dsmshop;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class DSMShop extends JavaPlugin implements Listener {

	private ShopMenu shopMenu;
	private ShopData shopData;
	private PlayerData playerData;
	public static ShopCreator sCreate = new ShopCreator(null);
	
	@Override
	public void onEnable() {
		
		if(!getDataFolder().exists()) {
			getDataFolder().mkdirs();
		}
		
		getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "DSMShop has been enabled");
		this.shopMenu = new ShopMenu(this);
		this.shopData = new ShopData(this);
		this.playerData = new PlayerData(this);
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
	}
	
	@Override
	public void onDisable() {
		getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "DSMShop has been enabled");
	}
	
	@EventHandler
	public void onJoin (PlayerJoinEvent e) {
		
		Player p = e.getPlayer();
		if(!p.hasPlayedBefore())	{
			playerData.setCredits(p, 500);
		}
	 
		
	}
	
	@EventHandler
	public void onSignClick (PlayerInteractEvent e) {
		
		if ((e.getAction() == Action.RIGHT_CLICK_BLOCK) || (e.getAction() == Action.LEFT_CLICK_BLOCK)) {
			Player p = (Player) e.getPlayer();
			Block b = e.getClickedBlock();
			
			if (b.getType() == Material.WALL_SIGN) {
				Sign s = (Sign) b.getState();
				if(s.getLine(2).toLowerCase().equalsIgnoreCase(ChatColor.DARK_GREEN + ""+ ChatColor.BOLD + "Active")) {
					if(s.getLine(0).toLowerCase().equalsIgnoreCase("[Shop]")) {
						shopMenu.show(p);
					}
				}
			} else {
				return;
			}
		}
		
	}
	
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		
		if (sender instanceof Player) {
			
			Player player = (Player) sender;
			String pName = player.getName();
			String lowerCmd = cmd.getName().toLowerCase();
			
			switch (lowerCmd) {
			
				case "shopcreate":
					player.sendMessage(ChatColor.GREEN + "Click a sign to create a shop");
					Bukkit.getServer().getPluginManager().registerEvents(sCreate, this);
					return true;
			
				case "addcredits":
					int amount = Integer.parseInt(args[0]);
					playerData.addCredits(player, amount);
					player.sendMessage(ChatColor.GREEN + "You've added " + amount + " to your balance");
					return true;
				
				case "removecredits":
					int amountRemove = Integer.parseInt(args[0]);
					playerData.removeCredits(player, amountRemove);
					player.sendMessage(ChatColor.GREEN + "" + amountRemove + " credit(s) have been removed from your balance!" );
					return true;	
			
				case "balance":
					playerData.getCredits(player);
					return true;
			
				case "pay":
					Player toPlayer = this.getServer().getPlayer(args[0]);
				
					if((args.length == 0) || (args.length != 2)) {
						player.sendMessage(ChatColor.RED + "Invalid Arguments");
					} else {
						int payAmount = Integer.parseInt(args[1]);
							if (toPlayer == null) {
								player.sendMessage(ChatColor.RED + "Invalid Player");
							} else {
								playerData.payCredits(player, toPlayer, payAmount);
							}
					}
				
					return true;	
			
				case "shop":
					if(Bukkit.getServer().getPlayer(pName).isOp() == true) {
						shopMenu.show(player);
					} else {
						player.sendMessage(ChatColor.RED + "You cannot use this");
					}
					
					return true;
				
				case "checkprice":
					if(args.length == 0) {
						player.sendMessage("Invalid Arguments");
						return false;
					}
					String world = args[0];
					String item = args[1].toLowerCase();
					shopData.pullSellPrice(world, item, player);
					shopData.pullBuyPrice(world, item, player);
					return true;
					
				default:
					return true;
			}
			
		}
		return true;
	}
	
}
