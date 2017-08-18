package com.syrical.dsmshop;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class AbstractFile {

	protected Plugin plugin;
	private File file;
	public FileConfiguration config;
	
	public AbstractFile (Plugin plugin, String fileName) {
		this.plugin = plugin;
		this.file = new File(plugin.getDataFolder(), fileName);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e){
				e.printStackTrace();
			}
		}
		config = YamlConfiguration.loadConfiguration(file);
	}
	
	public void save() {
		
		try {
			config.save(file);
		} catch (IOException e){
			e.printStackTrace();
		}
		
	}
	
}
