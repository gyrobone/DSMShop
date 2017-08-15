package com.syrical.dsmshop;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class AbstractFile {

	protected DSMShop main;
	private File file;
	protected FileConfiguration config;
	
	public AbstractFile (DSMShop main, String fileName) {
		this.main = main;
		this.file = new File(main.getDataFolder(), fileName);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e){
				e.printStackTrace();
			}
		}
		this.config = YamlConfiguration.loadConfiguration(file);
	}
	
	public void save() {
		
		try {
			config.save(file);
		} catch (IOException e){
			e.printStackTrace();
		}
		
	}
	
}
