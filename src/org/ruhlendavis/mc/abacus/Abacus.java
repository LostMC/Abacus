/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ruhlendavis.mc.abacus;

import java.util.logging.Level;
import org.bukkit.plugin.java.JavaPlugin;
import org.ruhlendavis.mc.utility.Log;

/**
 *
 * @author Feaelin
 */
public class Abacus extends JavaPlugin
{
	@SuppressWarnings("NonConstantLogger")
	public static Log log;
	
	/**
	 * onEnable triggered by Bukkit to handle any setup the plugin needs.
	 * 
	 */
	@Override
  public void onEnable()
	{
		log = new Log(this.getLogger());
		log.setLevel(Level.INFO);

		// This generates the default config.yml if the config.yml does not exist.
		saveDefaultConfig();
		readConfig();
		setupCommands();
  }
 
	/**
	 * onDisable triggered by Bukkit to handle any cleanup the plugin needs when
	 * disabling.
	 */
	@Override
	public void onDisable()
	{
	}
	
	/**
	 * readConfig handles reading in the data values from the config file and
	 * storing them conveniently.
	 */
	private void readConfig()
	{
		log.config("Reading configuration file");
		if (this.getConfig().getString("log-level") == null
			||this.getConfig().getString("log-level").isEmpty())
		{}
		else
		{
			log.setLevel(this.getConfig().getString("log-level"));
		}
	}
	
	/**
	 *  Notifies bukkit about our commands.
	 */
	private void setupCommands()
	{
		getCommand("abacus").setExecutor(new AbacusCommand());
	}
}
